package com.fressets.bitshield.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fressets.bitshield.common.exception.ApplicationException;
import com.fressets.bitshield.common.exception.ApplicationHttpErrors;
import com.fressets.bitshield.domain.Address;
import com.fressets.bitshield.repository.AddressRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;

	public Address getAddress(Long id) {
		return this.addressRepository.findById(id).orElseThrow(() -> new ApplicationException(ApplicationHttpErrors.UNEXPECTED));
	}

	public Optional<Address> findAddress(Long id) {
		return this.addressRepository.findById(id);
	}

	public List<Address> listAddress() {
		return this.addressRepository.findAll();
	}

	public Address create(Address address) {
		return this.addressRepository.save(address);
	}

	public Address update(Long id, Address address) {
		Address old = this.getAddress(id);
		return old.update(address);
	}

	public void delete(Address address) {
		this.addressRepository.logicalDelete(address);
	}
}
