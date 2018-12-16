package com.su.model.mapper;

import com.su.model.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface ProductCategoryMapper {

    /**
     * 使用MyBatis进行插入数据
     * @param map
     */
    @Insert("insert into product_category(category_name, category_type) " +
            "values(#{categoryName, jdbcType=VARCHAR}, #{categoryType,jdbcType=INTEGER})")
    public int insert(Map<String, Object> map);

    @Select("select * from product_category where category_type=#{categoryType}")
//    @ResultType(ProductCategory.class)
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_Name", property = "categoryName"),
    })
    public ProductCategory findByType(Integer categoryType);
}
