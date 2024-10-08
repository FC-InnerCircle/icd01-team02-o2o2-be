package org.example.o2o.repository.menu;

import java.util.List;
import java.util.Optional;

import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {

	@Query("""
			SELECT sm
			FROM StoreMenu sm
			LEFT JOIN FETCH sm.menuOptionGroups
			LEFT JOIN FETCH sm.imageFileGroup
			WHERE sm.store.id = :storeId
				AND sm.status IN (
					org.example.o2o.domain.menu.StoreMenuStatus.SOLDOUT,
					org.example.o2o.domain.menu.StoreMenuStatus.ENABLED
				)
		""")
	List<StoreMenu> findActiveMenusByStoreId(@Param("storeId") Long storeId);

	@Query(
		value = "SELECT sm FROM StoreMenu sm"
			+ " LEFT JOIN FETCH sm.menuOptionGroups"
			+ " LEFT JOIN FETCH sm.imageFileGroup"
			+ " WHERE sm.store.id = :storeId AND sm.status IN :statuses",
		countQuery = "SELECT COUNT(sm) FROM StoreMenu sm"
			+ " WHERE sm.store.id = :storeId AND sm.status IN :statuses"
	)
	Page<StoreMenu> findByStoreIdAndStatusIn(@Param("storeId") Long storeId,
		@Param("statuses") List<StoreMenuStatus> statuses, Pageable pageable);

	@Query("SELECT sm FROM StoreMenu sm"
		+ " LEFT JOIN FETCH sm.menuOptionGroups mog"
		+ " LEFT JOIN FETCH sm.imageFileGroup fg"
		+ " WHERE sm.id = :id")
	Optional<StoreMenu> findStoreMenuWithDetails(@Param("id") Long id);

	@Query("SELECT sm FROM StoreMenu sm"
		+ " LEFT JOIN FETCH sm.menuOptionGroups mog"
		+ " LEFT JOIN FETCH sm.imageFileGroup fg"
		+ " WHERE sm.id IN :ids")
	List<StoreMenu> findStoreMenusWithDetails(@Param("ids") List<Long> ids);
}
