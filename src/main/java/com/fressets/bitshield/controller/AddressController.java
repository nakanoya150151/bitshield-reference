package com.fressets.bitshield.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fressets.bitshield.common.log.AuditLogger;
import com.fressets.bitshield.common.log.CustomerAuditLogCode;
import com.fressets.bitshield.domain.Address;
import com.fressets.bitshield.service.AddressService;
import com.fressets.bitshield.vo.AddressPostRequest;
import com.fressets.bitshield.vo.AddressPutRequest;
import com.fressets.bitshield.vo.AddressResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/addresses")
@AllArgsConstructor
public class AddressController {

	private final AddressService addressService;

	@GetMapping("/{id}")
	public ResponseEntity<AddressResponse> get(@PathVariable("id") Long id) {
		Address address = this.addressService.getAddress(id);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_READ.getCode(true));
		return new ResponseEntity<>(AddressResponse.newAddressResponse(address), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<AddressResponse>> list() {
		List<Address> addresses = this.addressService.listAddress();
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_READ.getCode(true));
		return new ResponseEntity<>(AddressResponse.newAddressesResponse(addresses), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<AddressResponse> create(@RequestBody @Validated AddressPostRequest req) {
		Address address = AddressPostRequest.newAddress(req);
		Address created = this.addressService.create(address);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_CREATE.getCode(true), created, req);
		return new ResponseEntity<>(AddressResponse.newAddressResponse(created), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressResponse> update(@PathVariable("id") Long id, @RequestBody @Validated AddressPutRequest req) {
		Address address = AddressPutRequest.newAddress(req);
		Address updated = this.addressService.update(id, address);
		AuditLogger.log(CustomerAuditLogCode.ADDRESS_UPDATE.getCode(true), updated, req);
		return new ResponseEntity<>(AddressResponse.newAddressResponse(updated), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return  this.addressService.findAddress(id).map(address -> {
			this.addressService.delete(address);
			AuditLogger.log(CustomerAuditLogCode.ADDRESS_DELETE.getCode(true), address, id);
			return new ResponseEntity<>(HttpStatus.OK);
		}).orElseGet(() -> {
			AuditLogger.log(CustomerAuditLogCode.ADDRESS_DELETE.getCode(false), id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		});
	}
}
