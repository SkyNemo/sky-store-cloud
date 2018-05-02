package en.edu.kmust.store.review.service;

import en.edu.kmust.store.review.entity.Review;
import en.edu.kmust.store.review.param.OrderReviewVo;

import java.util.List;

public interface ReviewService {


    List<Review> findReviewByProductId(Integer id);

    OrderReviewVo getOrderReviewVoByOrderId(Integer id);

    OrderReviewVo saveReviewByOrderId(Integer orderId, String content);

}
