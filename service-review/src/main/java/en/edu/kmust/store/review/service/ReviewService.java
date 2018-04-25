package en.edu.kmust.store.review.service;

import en.edu.kmust.store.review.entity.Review;

import java.util.List;

public interface ReviewService {


    List<Review> findReviewByProductId(Integer id);

}
