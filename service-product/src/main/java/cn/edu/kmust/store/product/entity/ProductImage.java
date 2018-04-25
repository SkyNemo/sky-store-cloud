package cn.edu.kmust.store.product.entity;


import javax.persistence.*;

@Entity
@Table(name = "productimage")
public class ProductImage {


    @Id
    @GeneratedValue
    private Integer id;


    @Column(name = "pid")
    private Integer productId;


    @Column(name = "type")
    private String type;

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", productId=" + productId +
                ", type=" + type +
                '}';
    }


    public Integer getId() {
        return id;
    }


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
