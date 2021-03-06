package en.edu.kmust.store.review.param;

import en.edu.kmust.store.review.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderReviewVo {


    private Integer id;



    private String orderCode;

    private String address;

    private String  post;

    private String receiver;

    private String mobile;



    private String userMessage;

    private String status;

    private LocalDateTime createDate;

    private LocalDateTime payDate;

    private LocalDateTime deliveryDate;



    private LocalDateTime confirmDate;

    private Integer userId;

    private Float total;

    public List<OrderItem> orderItems;

    public List<ReviewVo> reviews;

    public ProductVo product;

    public boolean showAddReview;

    public boolean isShowAddReview() {
        return showAddReview;
    }

    public void setShowAddReview(boolean showAddReview) {
        this.showAddReview = showAddReview;
    }

    public ProductVo getProduct() {
        return product;
    }

    public void setProduct(ProductVo product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDateTime confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<ReviewVo> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewVo> reviews) {
        this.reviews = reviews;
    }
}
