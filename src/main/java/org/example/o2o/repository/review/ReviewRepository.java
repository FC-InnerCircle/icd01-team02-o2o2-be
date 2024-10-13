package org.example.o2o.repository.review;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	boolean existsByOrder(OrderInfo order);
}
