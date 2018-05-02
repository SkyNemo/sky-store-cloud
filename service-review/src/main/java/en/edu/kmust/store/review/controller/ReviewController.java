package en.edu.kmust.store.review.controller;


import en.edu.kmust.store.review.entity.Review;
import en.edu.kmust.store.review.param.OrderReviewVo;
import en.edu.kmust.store.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ReviewController {


    @Autowired
    private ReviewService reviewService;


    @RequestMapping("/review/prodcutId/{id}")
    @ResponseBody
    public List<Review> findReviewByProductId(@PathVariable Integer id) {

        List<Review> reviews = reviewService.findReviewByProductId(id);

        return reviews;
    }


    @RequestMapping("/toReview")
    public String review(HttpServletRequest request, Model model) {

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderReviewVo orderReviewVo = reviewService.getOrderReviewVoByOrderId(orderId);

        orderReviewVo.setShowAddReview(true);

        model.addAttribute("orderReview", orderReviewVo);

        return "review";
    }


    @RequestMapping("/createReview")
    public String createReview(HttpServletRequest request, Model model) {

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        String reviewContent = request.getParameter("reviewContent");


        OrderReviewVo orderReviewVo = reviewService.saveReviewByOrderId(orderId, reviewContent);

        orderReviewVo.setShowAddReview(false);

        model.addAttribute("orderReview", orderReviewVo);

        return "review";
    }
}
