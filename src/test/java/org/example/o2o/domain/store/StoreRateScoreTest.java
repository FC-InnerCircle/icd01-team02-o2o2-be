package org.example.o2o.domain.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreRateScoreTest {

	@DisplayName("가게 평균 평점 조회")
	@Test
	void calStoreRateScore() {
		StoreRateScore storeRateScore = new StoreRateScore(null, 5f, 1, 5f, null);
		storeRateScore.updateStoreRateScore(1f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(3f);

		storeRateScore.updateStoreRateScore(2f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(2.7f);

		storeRateScore.updateStoreRateScore(2f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(2.5f);
	}
}
