package com.su.controller;

import com.su.constant.CookieConstant;
import com.su.constant.RedisConstant;
import com.su.enums.ResultStatusEnum;
import com.su.form.SellerForm;
import com.su.model.SellerInfo;
import com.su.service.SellerService;
import com.su.utils.CookieUtil;
import com.su.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家身份验证控制器
 */
@Controller
@RequestMapping("/seller/info")
public class SellerInfoController {

    private Logger log = LoggerFactory.getLogger(SellerInfoController.class);

    @Autowired
    private SellerService sellerService;

    /** 使用redis */
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/toLogin")
    public ModelAndView toLogin(HttpServletRequest request, Map<String, Object> model){
        HttpSession session = request.getSession();
        model.put("email", session.getAttribute("email"));
        model.put("password", session.getAttribute("password"));

        return new ModelAndView("seller/login", model);
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpServletResponse response,
                              Map<String, Object> model){

        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            log.error("【用户登录】用户名或密码为空");
            model.put("msg", "用户名或密码为空");
            model.put("url", "/sell/seller/info/toLogin");
            return new ModelAndView("common/error", model);
        }
        SellerInfo result = sellerService.findSellerInfoByEmail(email);
         // 对输入的邮箱和密码进行校验
        if (result == null || false==password.trim().equals(result.getPassword())){
            log.error("【用户登录】用户名或密码错误");
            model.put("msg", "用户名或密码错误");
            model.put("url", "/sell/seller/info/toLogin");
            return new ModelAndView("common/error", model);
        }

        // 登录成功，将生成唯一的token，并存入redis中，同时设置过期时间2小时
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), email, expire, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("email", result.getEmail());
//        redisTemplate.opsForValue().set("username", result.getUsername());

        // 将email设置到cookie中
        CookieUtil.setCookie(response, CookieConstant.TOKEN, token, expire);

        model.put("msg", ResultStatusEnum.SELLER_LOGIN_SUCCESS.getMsg());
        model.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    @GetMapping("/toRegister")
    public ModelAndView toRegister(){
        return new ModelAndView("seller/register");
    }

    @PostMapping("register")
    public ModelAndView register(@Valid SellerForm sellerForm,
                                 BindingResult bindingResult,
                                 Map<String, Object> model){
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            log.error("【用户注册】用户名或密码错误,msg={}", msg);
            model.put("msg", msg);
            model.put("url", "/sell/seller/info/toRegister");
            return new ModelAndView("common/error", model);
        }

        try {
            SellerInfo sellerInfo = new SellerInfo();
            BeanUtils.copyProperties(sellerForm, sellerInfo);
            sellerInfo.setSellerId(KeyUtil.getUniqueKey());
            sellerService.register(sellerInfo);
        } catch (Exception e) {
            String msg = e.getMessage();
            log.error("【用户注册】提交注册信息错误,msg={}", msg);
            model.put("msg", msg);
            model.put("url", "/sell/seller/info/toRegister");
            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.SELLER_REGISTER_SUCCESS.getMsg());
        model.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 用户注销
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> model){

        // 从请求request中获取cookie
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        // Cookie存在时，需要删除redis缓存中的对应是值，同时删除该cookie
        if (cookie != null){
            // 删除redis中的缓存数据
            String key = String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue());
            redisTemplate.opsForValue().getOperations().delete(key);

            // 清除cookie
            CookieUtil.setCookie(response, CookieConstant.TOKEN, cookie.getValue(), 0);
        }

        model.put("msg", ResultStatusEnum.SELLER_LOGOUT_SUCCESS.getMsg());
        model.put("url", "/sell/seller/info/toLogin");
        return new ModelAndView("common/success", model);
    }
}
