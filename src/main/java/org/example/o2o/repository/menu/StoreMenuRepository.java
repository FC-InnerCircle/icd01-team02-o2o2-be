package org.example.o2o.repository.menu;

import java.util.List;
import java.util.Optional;

import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
	Page<StoreMenu> findByStoreIdAndStatusIn(Long storeId, List<StoreMenuStatus> statuses, Pageable pageable);

	Optional<StoreMenu> findByIdAndStoreId(Long id, Long storeId);
}
