package en.edu.kmust.store.review.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review implements Serializable{

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String content;

    @Column(name = "cid")
    private Integer categoryId;

    @Column(name = "pid")
    private Integer productId;

    @Column(name = "uid")
    private Integer userId;

    @Column(name = "createdate")
    private LocalDateTime createDate;


    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
