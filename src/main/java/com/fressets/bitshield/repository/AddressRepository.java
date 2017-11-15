package com.fressets.bitshield.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fressets.bitshield.domain.Address;

public interface AddressRepository extends JpaRepositoryBase<Address, Long>, JpaSpecificationExecutor<Address> {

}
