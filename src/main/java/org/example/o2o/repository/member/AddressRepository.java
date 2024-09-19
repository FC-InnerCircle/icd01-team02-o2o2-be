package org.example.o2o.repository.member;

import java.util.Optional;

import org.example.o2o.domain.member.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT a FROM Address a"
		+ " JOIN FETCH a.member m"
		+ " WHERE a.id = :id")
	Optional<Address> findAddressWithMember(@Param("id") Long id);
	
	@Query("""
			SELECT a
			FROM Address a
			INNER JOIN FETCH a.member
			WHERE a.id = :addressId
		""")
	Optional<Address> findAddressByAddressId(@Param("addressId") Long addressId);

}
