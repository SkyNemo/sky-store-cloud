package en.edu.kmust.store.review.repository;

import en.edu.kmust.store.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewByProductIdOrderByIdDesc(Integer id);

}
