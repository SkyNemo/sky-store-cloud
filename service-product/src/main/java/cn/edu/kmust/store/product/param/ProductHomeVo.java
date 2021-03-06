package cn.edu.kmust.store.product.param;

import java.time.LocalDateTime;

public class ProductHomeVo {


    private Integer id;

    private String name;

    private Float promotePrice;

    private String subTitle;

    private Integer categoryId;

    private LocalDateTime createDate;


    /*
     * 关联的 firstProductImage
     * */
    private ProductImageVo firstProductImage;



    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public ProductImageVo getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImageVo firstProductImage) {
        this.firstProductImage = firstProductImage;
    }
}
