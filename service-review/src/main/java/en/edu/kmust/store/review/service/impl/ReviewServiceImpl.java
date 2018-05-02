package en.edu.kmust.store.review.service.impl;

import en.edu.kmust.store.review.client.OrderFeignClient;
import en.edu.kmust.store.review.client.OrderItemFeignClient;
import en.edu.kmust.store.review.client.UserFeignClient;
import en.edu.kmust.store.review.entity.Order;
import en.edu.kmust.store.review.entity.Product;
import en.edu.kmust.store.review.entity.Review;
import en.edu.kmust.store.review.entity.User;
import en.edu.kmust.store.review.param.OrderReviewVo;
import en.edu.kmust.store.review.param.ProductVo;
import en.edu.kmust.store.review.param.ReviewVo;
import en.edu.kmust.store.review.repository.ReviewRepository;
import en.edu.kmust.store.review.service.ReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.NonUniqueResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {


    @Autowired
    private ReviewRepository reviewRepository;


    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private OrderItemFeignClient orderItemFeignClient;

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public List<Review> findReviewByProductId(Integer id) {

        List<Review> reviewList = reviewRepository.findReviewByProductIdOrderByIdDesc(id);

        return reviewList;
    }

    @Override
    public OrderReviewVo getOrderReviewVoByOrderId(Integer id) {

        Order order = orderFeignClient.findOrderById(id);

        OrderReviewVo orderReviewVo = null;

        if (order != null) {

            orderReviewVo = new OrderReviewVo();

            BeanUtils.copyProperties(order, orderReviewVo);


            Product product = order.getOrderItems().get(0).getProduct();

            ProductVo productVo = new ProductVo();

            BeanUtils.copyProperties(product, productVo);


            //设置商品
            orderReviewVo.setProduct(productVo);


            Integer productId = order.getOrderItems().get(0).getProductId();

            //设置销量
            Integer sale = orderItemFeignClient.countProductSale(productId);

            productVo.setSaleCount(sale);


            //设置已有评论
            List<Review> reviewList = reviewRepository.findReviewByProductIdOrderByIdDesc(productId);

            List<ReviewVo> reviewVoList = new ArrayList<>();

            if (reviewList != null && !reviewList.isEmpty()) {
                for (Review review : reviewList) {

                    User user = userFeignClient.findUserById(review.getUserId());

                    ReviewVo reviewVo = new ReviewVo();

                    BeanUtils.copyProperties(review, reviewVo);

                    reviewVo.setUser(user);

                    reviewVo.setProduct(productVo);

                    reviewVoList.add(reviewVo);

                }
            }

            orderReviewVo.setReviews(reviewVoList);

        }


        return orderReviewVo;
    }

    @Override
    public OrderReviewVo saveReviewByOrderId(Integer orderId, String content) {

        Order order = orderFeignClient.findOrderById(orderId);

        Integer productId = order.getOrderItems().get(0).getProductId();

        Review review = null;

        try {

             review = reviewRepository.findByProductIdAndAndUserId(productId,order.getUserId());

        }catch (Exception e){

            review = new Review();

            e.printStackTrace();
        }



        if (review == null){

            Review newReview = new Review();

            newReview.setContent(content);

            boolean isFinish = orderFeignClient.finishOrderById(orderId);

            if (isFinish) {

                newReview.setUserId(order.getUserId());

                newReview.setProductId(order.getOrderItems().get(0).getProductId());

                newReview.setCreateDate(LocalDateTime.now());

                reviewRepository.save(newReview);

            }

        }

        OrderReviewVo orderReviewVo = this.getOrderReviewVoByOrderId(orderId);

        return orderReviewVo;

    }
}
