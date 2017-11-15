package com.fressets.bitshield.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.fressets.bitshield.domain.Address;
import com.fressets.bitshield.repository.AddressRepository;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

	private AddressService addressService;

	@Mock
	private AddressRepository mockAddressRepository;

	@Before
	public void setUp() throws Exception {
		addressService = new AddressService(mockAddressRepository);
	}

	@Test
	public void testGetAddress() throws Exception {

		Mockito.when(mockAddressRepository.findById(100L))
			.thenReturn(Optional.of(new Address(100L, 9999L, "999path")));

		Address address = this.addressService.findAddress(100L).get();
		assertThat(address.getId(), is(100L));
		assertThat(address.getWalletId(), is(9999L));
		assertThat(address.getPath(), is("999path"));
	}

	@Test
	public void testListAddress() throws Exception {

		Mockito.when(mockAddressRepository.findAll())
			.thenReturn(List.of(new Address(200L, 8888L, "888path"), new Address(300L, 7777L, "777path")));

		List<Address> addresses = this.addressService.listAddress();

		assertThat(addresses.size(), is(2));
		addresses.stream().filter(address -> address.getId().longValue() == 200L).forEach(address -> {
			assertThat(address.getId(), is(200L));
			assertThat(address.getWalletId(), is(8888L));
			assertThat(address.getPath(), is("888path"));
		});
		addresses.stream().filter(address -> address.getId().longValue() == 300L).forEach(address -> {
			assertThat(address.getId(), is(300L));
			assertThat(address.getWalletId(), is(7777L));
			assertThat(address.getPath(), is("777path"));
		});
	}

	@Test
	public void testCreate() throws Exception {

		Mockito.when(mockAddressRepository.save(Mockito.any()))
			.thenReturn(new Address(100L, 9999L, "999path"));

		Address address = new Address(null, 9999L, "999path");
		Address created = this.addressService.create(address);

		assertThat(created.getId(), is(100L));
		assertThat(address.getWalletId(), is(9999L));
		assertThat(address.getPath(), is("999path"));
	}
}
