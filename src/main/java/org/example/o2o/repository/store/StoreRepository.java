package org.example.o2o.repository.store;

import java.util.List;
import java.util.Optional;

import org.example.o2o.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Page<Store> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("""
			SELECT s
			FROM Store s
			LEFT JOIN FETCH s.storeRateScore src
			LEFT JOIN FETCH s.thumbnailFileGroup fg
			LEFT JOIN FETCH fg.details fgd
			WHERE s.status = org.example.o2o.domain.store.StoreStatus.ACTIVE
				AND s.id = :id
		""")
	Optional<Store> findStoreWithThumbnailAndById(@Param("id") Long id);

	@Query("""
			SELECT s
			FROM Store s
			LEFT JOIN FETCH s.storeRateScore src
			LEFT JOIN FETCH s.thumbnailFileGroup fg
			LEFT JOIN FETCH fg.details fgd
			WHERE s.status = org.example.o2o.domain.store.StoreStatus.ACTIVE
				AND s.id IN :ids
		""")
	List<Store> findStoreWithThumbnailAndByIds(@Param("ids") List<Long> ids);
}
