package com.su.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 *  类目表：采用驼峰模式，用于存储商品的类目信息，对应数据库中的product_category表
 * @Table(name="tablename") 就可以自定义更改表名与类名的映射关系
 * @DynamicUpdate 注解表示自动更新字段，常用于时间自动更新上
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /** 类目Id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    /** 类目名称 */
    private String categoryName;
    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间和更新时间：由数据库创建，而不用程序员显示指定 */
    private Date createTime;
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, int categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
