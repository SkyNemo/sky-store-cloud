package cn.edu.kmust.store.product.param;

import cn.edu.kmust.store.product.entity.Product;

import java.util.List;

public class CategoryHomeVo {


    private Integer id;

    private String name;


    /*
    * 关联的product
    * */
    private List<ProductHomeVo> productList;


    /*
    * 推荐产品集合
    * */
    private List<List<ProductHomeVo>> productsByRow;


    @Override
    public String toString() {
        return "CategoryHomeVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productList=" + productList +
                ", productByRow=" + productsByRow +
                '}';
    }

    public List<List<ProductHomeVo>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<ProductHomeVo>> productsByRow) {
        this.productsByRow = productsByRow;
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

    public List<ProductHomeVo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductHomeVo> productList) {
        this.productList = productList;
    }
}
