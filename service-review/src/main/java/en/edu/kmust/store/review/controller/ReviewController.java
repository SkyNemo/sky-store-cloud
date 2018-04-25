package en.edu.kmust.store.review.controller;


import en.edu.kmust.store.review.entity.Review;
import en.edu.kmust.store.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReviewController {



    @Autowired
    private ReviewService reviewService;


    @RequestMapping("/review/prodcutId/{id}")
    public List<Review> findReviewByProductId(@PathVariable Integer id){

        List<Review> reviews = reviewService.findReviewByProductId(id);

        return reviews;
    }


}
