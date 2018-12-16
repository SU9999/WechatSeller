package com.su.controller;

import com.oracle.tools.packager.mac.MacAppBundler;
import com.su.converter.ProductForm2ProductInfoConverter;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.form.ProductForm;
import com.su.model.ProductCategory;
import com.su.model.ProductInfo;
import com.su.service.CategoryService;
import com.su.service.ProductService;
import com.su.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.POST;
import java.util.List;
import java.util.Map;

/**
 *  卖家端商品控制器
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    private Logger log = LoggerFactory.getLogger(SellerProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> model){
        PageRequest pageRequest = new PageRequest(page-1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);

        model.put("productInfoPage", productInfoPage);
        model.put("currentPage", page);
        model.put("currentSize", size);

        return new ModelAndView("product/list", model);
    }

    /**
     *  将下架商品上架
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> model){

        try {
            productService.onSale(productId);
        } catch (SellException e){
            log.error("【卖家商品上架】发生异常，e={}", e);

            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.ON_SALE_SUCCCESS.getMsg());
        model.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 将上架商品下架
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> model){
        try {
            productService.offSale(productId);
        } catch (SellException e){
            log.error("【卖家商品下架】发生异常，e={}", e);

            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.DOWN_SALE_SUCCCESS.getMsg());
        model.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }

    /**
     * 修改商品信息或新增商品
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> model){
        // 如果productId不存在，则表示新增一件商品。
        // 如果存在，则表示修改商品信息，则需要从数据库中查询出该商品的详细信息
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            model.put("productInfo", productInfo);
        }

        // 查询出所有的类目信息
        List<ProductCategory> categoryList = categoryService.findAll();
        model.put("categoryList", categoryList);

        return new ModelAndView("product/index", model);
    }


    /**
     *  提交保存新增或更该商品的信息
     * @CachePut 注解表示每次都会把返回的对象存储到redis缓存中 ，但要求返回的对象必须可序列化
     * @CacheEvict 注解：表示每次执行都会清除redis缓存中的数据
     */
    @PostMapping("/save")
//    @CachePut(cacheNames = "product", key = "123")
    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> model){
        //对表单中的输入数据进行校验
        if (bindingResult.hasErrors()){
            log.error("【卖家新增或修改商品】参数错误，msg={}", bindingResult.getFieldError().getDefaultMessage());

            model.put("msg", bindingResult.getFieldError().getDefaultMessage());
            model.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", model);
        }

        try {
            // 如果form的productId为空，则表示新增商品
            ProductInfo productInfo = new ProductInfo();
            if (StringUtils.isEmpty(form.getProductId())){
                form.setProductId(KeyUtil.getUniqueKey());
            } else {
                productInfo = productService.findOne(form.getProductId());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (Exception e){
            log.error("【买家保存商品】发生异常，e={}", e);
            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.PRODUCT_UPDATE_SUCCESS.getMsg());
        model.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", model);
    }
}
