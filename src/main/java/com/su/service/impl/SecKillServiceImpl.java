package com.su.service.impl;

import com.su.exception.SellException;
import com.su.service.RedisLock;
import com.su.service.SecKillService;
import com.su.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.SocketHandler;

@Service
public class SecKillServiceImpl implements SecKillService {

    private final Long TIMEOUT = 10 * 1000L;    //表示10s
    @Autowired
    private RedisLock redisLock;

    /**
     * 定义三个map， 模拟数据操作
     */
    static Map<String, Integer> products;
    static Map<String, String> orders;
    static Map<String, Integer> stocks;
    static {
        products = new HashMap<>();
        orders = new HashMap<>();
        stocks = new HashMap<>();

        products.put("abc", 1000000);
        stocks.put("abc", 1000000);
    }

    @Override
    public String query(String productId) {

        return "秒杀商品的总数为 " + products.get(productId) +
                ", 库存为：" + stocks.get(productId) +
                ", 已经卖出了" + orders.size() + "件";
    }

    @Override
    public void secKill(String productId) {

        //获取redis锁，如果获取不到，抛出异常
        if (!redisLock.lock(productId, System.currentTimeMillis()+TIMEOUT+"")){
            throw new SellException(101, "访问量太大，无法购买该商品");
        }

        int stock = stocks.get(productId);

        if (stock <= 0){
            throw new SellException(100, "活动结束");
        } else {
            // 下单
            orders.put(KeyUtil.getUniqueKey(), productId);

            //减库存: 由于会进行数据库的操作，所以用sleep表示休眠一段时间
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            stocks.put(productId, stock-1);
        }

        //解锁
        redisLock.unlock(productId);
    }
}
