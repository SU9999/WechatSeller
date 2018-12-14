package com.su.controller;

import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * 微信相关控制器
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    private Logger log = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        // 配置
        // 调用方法
        String url = "http://localhost:8080/sell/wechat/userInfo";
        // TODO 还需要一些工作：网页验证
        // 该方法的第三个参数会附加到返回值的后面
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));

        log.info("【微信网页授权】获取code，redirectUrl={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【微信网页授权】{}", e);
            throw new SellException(ResultStatusEnum.WECHAT_MP_ERROR.getCode(),
                    e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;
    }
}
