package cn.edu.kmust.store.product.util.comparator;

import cn.edu.kmust.store.product.param.ProductHomeVo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductComparator {


    public static final String ALL = "all";
    //public static final String REVIEW = "review";
    public static final String DATE = "date";
    //public static final String SALECOUNT = "saleCount";
    public static final String PRICE = "price";


    private static final String URI = "cn.edu.kmust.store.product.util.comparator.product.";
    private static final String PREFIX = "Product";
    private static final String POSTFIX = "Comparator";


    public static void comparator(List<ProductHomeVo> productHomeVos, String method) {

        if (method.equals(ALL)) {
            return;
        } else {

            String name = ProductComparator.toUpperCaseFirstOne(method);

            StringBuffer clazzName = new StringBuffer();

            clazzName.append(URI).append(PREFIX).append(name).append(POSTFIX);

            try {

                Class<?> clazz = Class.forName(clazzName.toString());

                Comparator comparator = (Comparator) clazz.newInstance();

                Collections.sort(productHomeVos, comparator);

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

        }
    }


    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {

        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


}
