package cn.edu.kmust.store.product.util.comparator.product;

import cn.edu.kmust.store.product.param.ProductHomeVo;

import java.util.Comparator;

public class ProductDateComparator implements Comparator<ProductHomeVo> {

    @Override
    public int compare(ProductHomeVo p1, ProductHomeVo p2) {

        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
