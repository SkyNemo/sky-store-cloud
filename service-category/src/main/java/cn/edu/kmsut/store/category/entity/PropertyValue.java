package cn.edu.kmsut.store.category.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "propertyvalue")
public class PropertyValue implements Serializable {

    private static final long serialVersionUID = 6L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "value")
    private String value;


    @Column(name = "pid")
    private Integer productId;

    @Column(name = "ptid")
    private Integer propertyId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }
}
