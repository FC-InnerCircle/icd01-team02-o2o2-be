package org.example.o2o.domain.order;

import java.time.LocalDateTime;

import org.example.o2o.domain.AbstractEntity;
import org.example.o2o.domain.menu.StoreMenu;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderInfoDetail extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private StoreMenu menu;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderInfo order;

	private Integer quantity;
	private Integer price;
	private Integer totalPrice;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
