package cn.edu.kmust.store.product.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 2L;


    @Id
    @GeneratedValue
    private Integer id;


    @Column
    private String name;


    //  hibernate默认会以sub_title 来替代subTitle
    @Column(name = "subtitle")
    private String subTitle;

    @Column(name = "originalprice")
    private Float originalPrice;

    @Column(name = "promoteprice")
    private Float promotePrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(unique = true,name = "cid")
    private Integer categoryId;

    @Column(name = "createdate",columnDefinition = "datetime")
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", originalPrice=" + originalPrice +
                ", promotePrice=" + promotePrice +
                ", stock=" + stock +
                ", categoryId=" + categoryId +
                ", createDate=" + createDate +
                '}';
    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
