package com.su.controller;

import com.su.enums.ResultStatusEnum;
import com.su.form.CategoryForm;
import com.su.model.ProductCategory;
import com.su.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 买家类目前端控制器
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    private Logger log = LoggerFactory.getLogger(SellerCategoryController.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> model){
        List<ProductCategory> categoryList = categoryService.findAll();
        model.put("categoryList", categoryList);
        return new ModelAndView("category/list", model);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                Map<String, Object> model){
        // 判断是修改操作还是新增操作，大于0表示修改操作
        if (categoryId!=null && categoryId>0){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            model.put("category", productCategory);
            return new ModelAndView("category/index", model);
        }

        return new ModelAndView("category/index");
    }

    /**
     *  提交保存修改或新增的类目
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                              BindingResult bindingResult,
                              Map<String, Object> model){
        // 首先对参数进行校验
        if (bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
            log.error("【提交类目】参数校验发生错误，msg={}", msg);
            model.put("msg", msg);
            model.put("url", "/sell/seller/category/list");

            return new ModelAndView("common/error", model);
        }

        // 判断是否为新增类目的操作，或者修改类目
        try {
            ProductCategory productCategory = new ProductCategory();
            // categoryId!=null&&categoryForm.getCategoryId()>-1，说明是修改操作，则需要从数据库中读取数据后更新
            // 否则应直接新增数据
            if (categoryForm.getCategoryId() != null && categoryForm.getCategoryId()>-1){
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
        } catch (Exception e){
            String msg = e.getMessage();
            log.error("【提交保存类目】保存数据时发生错误，msg={}", msg);
            model.put("msg", msg);
            model.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.CATEGORY_INDEX_SUCCESS.getMsg());
        model.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", model);
    }


}
