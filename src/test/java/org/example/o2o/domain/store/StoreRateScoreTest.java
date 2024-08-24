package org.example.o2o.domain.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StoreRateScoreTest {

	@Test
	void 평균점수_성공() {
		StoreRateScore storeRateScore = new StoreRateScore(null, 5f, 1, 5f, null);
		storeRateScore.updateStoreRateScore(1f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(3f);

		storeRateScore.updateStoreRateScore(2f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(2.7f);

		storeRateScore.updateStoreRateScore(2f);
		Assertions.assertThat(storeRateScore.getRating()).isEqualTo(2.5f);
	}
}