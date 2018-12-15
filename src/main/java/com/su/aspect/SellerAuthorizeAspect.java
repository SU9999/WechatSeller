package com.su.aspect;

import com.su.constant.CookieConstant;
import com.su.constant.RedisConstant;
import com.su.exception.SellerAuthorizeException;
import com.su.utils.CookieUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 定义一个切面：使用AOP的方式实现登录校验
 */
@Aspect
@Component
public class SellerAuthorizeAspect {

    private Logger log = LoggerFactory.getLogger(SellerAuthorizeAspect.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     *  定义一个切入点（空方法） : 切入点指定对卖家的所有controller请求进行拦截，并且排除卖家登录，注销，注册等请求
     *  切入点的配置格式一定要注意："*"表示任意类，任意方法，".."表示任意参数，如果不加".."，则表示没有参数的方法
     * */
    @Pointcut("execution(public * com.su.controller.Seller*.*(..))" +
            "&& !execution(public * com.su.controller.SellerInfoController.*(..))")
    public void verify(){}

    /**
     * 表示在切入点之前执行该方法
     */
    @Before("verify()")
    public void doVerify(){
        // 获取request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 从request中获取cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【卖家信息校验】Cookie中查询不到该token");
            throw new SellerAuthorizeException();
        }

        // 从redis中查询该token
        String tokenValue = redisTemplate.opsForValue().
                get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【卖家信息校验】Redis中查询不到该token");
            throw new SellerAuthorizeException();
        }
    }
}
