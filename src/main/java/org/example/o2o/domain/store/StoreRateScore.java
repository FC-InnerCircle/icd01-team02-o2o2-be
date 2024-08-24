package org.example.o2o.domain.store;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StoreRateScore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Float rating;
	private Integer count;
	private Float sum;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "store_id")
	private Store store;

	public void updateStoreRateScore(Float ratingScore) {
		this.count++;
		this.sum += ratingScore;

		BigDecimal sumDecimal = new BigDecimal(Float.toString(sum));
		BigDecimal countDecimal = new BigDecimal(count);
		BigDecimal average = sumDecimal.divide(countDecimal, 1, RoundingMode.HALF_UP);

		this.rating = average.floatValue();
	}
}
