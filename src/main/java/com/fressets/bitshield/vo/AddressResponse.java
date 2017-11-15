package com.fressets.bitshield.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fressets.bitshield.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse implements Serializable {

	private Long id;

	private Long walletId;

	private String path;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss.SSS")
	private LocalDateTime updateDateTime;

	public static AddressResponse newAddressResponse(Address address) {
		return AddressResponse.builder()
				.id(address.getId())
				.walletId(address.getWalletId())
				.path(address.getPath())
				.updateDateTime(address.getUpdateDateTime())
				.build();
	}

	public static List<AddressResponse> newAddressesResponse(List<Address> addresses) {
		return addresses.stream().sorted(Comparator.comparing(Address::getId).reversed()).map(address -> {
			AddressResponse response = new AddressResponse();
			response.id = address.getId();
			response.walletId = address.getWalletId();
			response.path = address.getPath();
			return response;
		}).collect(Collectors.toList());
	}
}
