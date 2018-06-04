package cn.edu.kmust.store.product.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class VoUtils<T, E> {

    public static <T, E> List<E> copyList(List<T> sourceList, Class<E> targetClass) {

        List<E> voList = new ArrayList<>();

        if (sourceList != null && !sourceList.isEmpty()) {

            for (T t : sourceList) {
                try {

                    E e = targetClass.newInstance();

                    BeanUtils.copyProperties(t, e);

                    voList.add(e);

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        }

        return voList;
    }


}
