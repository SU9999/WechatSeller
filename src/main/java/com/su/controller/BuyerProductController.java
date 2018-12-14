package com.su.controller;

import com.su.model.ProductCategory;
import com.su.model.ProductInfo;
import com.su.service.CategoryService;
import com.su.service.ProductService;
import com.su.utils.ResultViewObjectUtil;
import com.su.viewobject.ProductInfoViewObject;
import com.su.viewobject.ProductViewObject;
import com.su.viewobject.ResultViewObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家相关的控制器
 *
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品步骤：
     * 1.查询所有上架的商品（product_info）
     * 2.查询所有的类目（category_info, 一次性查询）
     * 3.拼装数据（将查询数据组装成前端需要的json格式）
     * @return
     */
    @GetMapping("/list")
    public ResultViewObject list(){
        // 查询所有上架的商品
        List<ProductInfo> allUpProducts = productService.findUpAll();

        // 获取所有上架商品的类目列表: 采用Lambda表达式
        List<Integer> allCategoryTypeFind = allUpProducts.stream().
                map(item->item.getCategoryType()).collect(Collectors.toList());
        // 查询所有包含在类目列表中的类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(allCategoryTypeFind);

        // 拼装数据，获取ProductViewObject的List集合
        List<ProductViewObject> productViewObjectList = new ArrayList<>();
        // 遍历类目表
        for (ProductCategory productCategory: productCategoryList){
            ProductViewObject productVO = new ProductViewObject();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            // 遍历商品集合，最终得到返回结果集中的商品详细信息列表
            List<ProductInfoViewObject> productInfoViewObjectList = new ArrayList<>();
            for (ProductInfo productInfo: allUpProducts){
                if (productCategory.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoViewObject productInfoVO = new ProductInfoViewObject();

                    //利用spring提供的工具类做复制属性的操作
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoViewObjectList.add(productInfoVO);
                }
            }
            // 将商品信息添加到返回的结果集中（json中key为foods的字段）
            productVO.setProductInfoViewObjects(productInfoViewObjectList);
            // 将商品详细信息（包含类目）添加到返回的信息中（json中key为data的字段）
            productViewObjectList.add(productVO);
        }

        // 根据以上处理的结果，最终生成返回给视图的结果集：ResultViewObject对象
        return ResultViewObjectUtil.success(productViewObjectList);
    }
}
