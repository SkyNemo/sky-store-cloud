package cn.edu.kmsut.store.category.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "property")
public class Property implements Serializable {

    private static final long serialVersionUID = 5L;


    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "cid")
    private Integer categoryId;

    @Column(name = "name")
    private String name;


    public Integer getId() {
        return id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
