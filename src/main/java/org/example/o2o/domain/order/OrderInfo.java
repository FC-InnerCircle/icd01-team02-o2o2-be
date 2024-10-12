package org.example.o2o.domain.order;

import java.util.List;

import org.example.o2o.api.v1.dto.order.request.OrderMenuCreateRequestDto;
import org.example.o2o.domain.AbstractEntity;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.store.Store;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class OrderInfo extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private Integer price; // 주문 금액

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "menu_detail", columnDefinition = "json")
	private List<OrderMenuCreateRequestDto> menuDetail; // 메뉴 상세 정보

	private String deliveryContactNumber; // 배달 연락처

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	private Address address; // 배달 주문 주소

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus status; // 주문 상태

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type")
	private OrderPaymentType payment;
}
