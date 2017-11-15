package com.fressets.bitshield.vo;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fressets.bitshield.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressPostRequest implements Serializable {

	@Digits(integer = 10, fraction = 0)
	private Long walletId;

	@Size(max = 32)
	private String path;

	public static Address newAddress(AddressPostRequest req) {
		Address address = new Address();
		address.setWalletId(req.getWalletId());
		address.setPath(req.getPath());
		return address;
	}
}
