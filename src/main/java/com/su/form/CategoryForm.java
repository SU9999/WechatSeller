package com.su.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *  封装类目中的表单信息
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;

    @NotNull(message = "类目类型不同为null")
    @Min(value = 0, message = "类目类型不能为负数")
    private Integer categoryType;
}
