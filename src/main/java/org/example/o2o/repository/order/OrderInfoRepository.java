package org.example.o2o.repository.order;

import java.time.LocalDateTime;
import java.util.List;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

	@Query(
		value = "SELECT o FROM OrderInfo o"
			+ " LEFT JOIN FETCH o.member"
			+ " LEFT JOIN FETCH o.store"
			+ " WHERE o.store.id = :storeId"
			+ " AND o.status IN :statuses"
			+ " AND o.createdAt BETWEEN :startDate AND :endDate",
		countQuery = "SELECT (o) FROM OrderInfo o"
			+ " WHERE o.store.id = :storeId"
			+ " AND o.status IN :statuses"
	)
	Page<OrderInfo> findByStoreIdAndStatusIn(@Param("storeId") Long storeId,
		@Param("statuses") List<OrderStatus> statuses, Pageable pageable,
		@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDate);

	@Query("SELECT o FROM OrderInfo o"
		+ "	LEFT JOIN FETCH o.member"
		+ " LEFT JOIN FETCH o.store"
		+ "	WHERE o.id = :orderId")
	OrderInfo findByIdWithDetail(@Param("orderId") Long orderId);
}
