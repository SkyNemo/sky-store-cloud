package cn.edu.kmust.store.product.param;

import cn.edu.kmust.store.product.entity.ProductImage;
import cn.edu.kmust.store.product.entity.PropertyValue;
import cn.edu.kmust.store.product.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ProductDetailVo {

    /*
     * 基础 product 属性
     * */

    private Integer id;

    private String name;

    private String subTitle;

    private Float originalPrice;

    private Float promotePrice;

    private Integer stock;

    private Integer categoryId;

    private LocalDateTime createDate;


    /*
     * 关联的 productImage
     *
     * */
    private List<ProductImageVo> detailImages;

    private List<ProductImageVo> singleImages;

    private ProductImageVo firstProductImage;

    /*
     * 关联的 review
     *
     * */
    private List<Review> reviews;

    private Integer reviewCount; // 评论数目  冗余字段


    /*
     * 销量属性 由orderItem决定
     * */
    private Integer sale;


    /*
     * 分类属性
     * */

    private List<PropertyValue> propertyValues;

    private Map<String, String> propertyAndValueMap;


    @Override
    public String toString() {
        return "ProductDetailVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", originalPrice=" + originalPrice +
                ", promotePrice=" + promotePrice +
                ", stock=" + stock +
                ", categoryId=" + categoryId +
                ", createDate=" + createDate +
                ", detailImages=" + detailImages +
                ", singleImages=" + singleImages +
                ", firstProductImage=" + firstProductImage +
                ", reviews=" + reviews +
                ", reviewCount=" + reviewCount +
                ", sale=" + sale +
                ", propertyValues=" + propertyValues +
                ", propertyAndValueMap=" + propertyAndValueMap +
                '}';
    }

    public ProductImageVo getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImageVo firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public Map<String, String> getPropertyAndValueMap() {
        return propertyAndValueMap;
    }

    public void setPropertyAndValueMap(Map<String, String> propertyAndValueMap) {
        this.propertyAndValueMap = propertyAndValueMap;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }


    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
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

    public List<ProductImageVo> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<ProductImageVo> detailImages) {
        this.detailImages = detailImages;
    }

    public List<ProductImageVo> getSingleImages() {
        return singleImages;
    }

    public void setSingleImages(List<ProductImageVo> singleImages) {
        this.singleImages = singleImages;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        this.setReviewCount(reviews.size());
    }


    public Integer getReviewCount() {
        return reviewCount;
    }

    private void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }
}
