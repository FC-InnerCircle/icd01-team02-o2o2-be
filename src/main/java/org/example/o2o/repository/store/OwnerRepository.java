package org.example.o2o.repository.store;

import org.example.o2o.domain.store.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
