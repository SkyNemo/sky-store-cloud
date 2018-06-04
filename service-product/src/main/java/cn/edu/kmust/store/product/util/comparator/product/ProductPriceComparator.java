package cn.edu.kmust.store.product.util.comparator.product;

import cn.edu.kmust.store.product.param.ProductHomeVo;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<ProductHomeVo> {

    @Override
    public int compare(ProductHomeVo p1, ProductHomeVo p2) {

        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }
}
