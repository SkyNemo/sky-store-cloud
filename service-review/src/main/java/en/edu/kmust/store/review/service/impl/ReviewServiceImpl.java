package en.edu.kmust.store.review.service.impl;

import en.edu.kmust.store.review.entity.Review;
import en.edu.kmust.store.review.repository.ReviewRepository;
import en.edu.kmust.store.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> findReviewByProductId(Integer id) {

        List<Review> reviewList = reviewRepository.findReviewByProductIdOrderByIdDesc(id);

        return reviewList;
    }
}
