package cn.edu.kmust.store.order.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orderitem")
public class OrderItem  implements Serializable{

    private static final long serialVersionUID = 8L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "oid")
    private Integer orderId;

    @Column(name = "pid")
    private Integer productId;

    @Column(name = "uid")
    private Integer userId;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", number=" + number +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", userId=" + userId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
