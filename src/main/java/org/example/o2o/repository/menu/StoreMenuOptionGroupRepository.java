package org.example.o2o.repository.menu;

import java.util.Optional;

import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreMenuOptionGroupRepository extends JpaRepository<StoreMenuOptionGroup, Long> {
	Optional<StoreMenuOptionGroup> findByIdAndIsDeletedFalse(Long id);
}
