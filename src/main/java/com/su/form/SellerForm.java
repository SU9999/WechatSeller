package com.su.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;

/**
 * 封装用户信息的form表单的内容
 */
@Data
public class SellerForm {

    private String sellerId;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "密码不能为空")
    private String confirmPassword;

    @NotEmpty(message = "邮箱不能为空")
    private String email;
}
