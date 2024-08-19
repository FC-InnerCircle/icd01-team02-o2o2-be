package org.example.o2o.repository.member;

import org.example.o2o.domain.member.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
