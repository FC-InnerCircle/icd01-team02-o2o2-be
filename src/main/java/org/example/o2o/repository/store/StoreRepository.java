package org.example.o2o.repository.store;

import org.example.o2o.domain.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Page<Store> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
