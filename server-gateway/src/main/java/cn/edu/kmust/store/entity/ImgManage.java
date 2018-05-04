package cn.edu.kmust.store.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "imagemanage")
public class ImgManage implements Serializable {

    private static final long serialVersionUID = 10L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "imgid")
    private Integer imageId;


    @Column(name = "type")
    private Integer type;


    public Integer getId() {
        return id;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
