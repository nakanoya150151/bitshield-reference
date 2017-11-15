package com.fressets.bitshield.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fressets.bitshield.domain.Address;
import com.fressets.bitshield.filter.ApiOncePreRequestFilter;
import com.fressets.bitshield.service.AddressService;
import com.fressets.bitshield.vo.AddressPostRequest;

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Mock
	private AddressService mockAddressService;


	@Before
	public void setupMockMvc() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(mockAddressService))
				.addFilter(new ApiOncePreRequestFilter())
				.build();
	}

	@Test
	public void testGet() throws Exception {

		Mockito.when(mockAddressService.getAddress(Mockito.anyLong()))
					.thenReturn(new Address(100L, 9999L, "999path"));

		mockMvc.perform(get("/addresses/1"))
			.andExpect(
					status().isOk()
			).andExpect(
					result -> assertThat(result.getResponse().getContentAsString(), is("{\"id\":100,\"walletId\":9999,\"path\":\"999path\",\"updateDateTime\":null}"))
			).andDo(
					print()
			);
	}

	@Test
	public void testList() throws Exception {

		Mockito.when(mockAddressService.listAddress())
					.thenReturn(List.of(new Address(200L, 8888L, "888path"), new Address(300L, 7777L, "777path")));

		mockMvc.perform(get("/addresses"))
			.andExpect(
					status().isOk()
			).andExpect(
					result -> assertThat(result.getResponse().getContentAsString(),
							is("[{\"id\":300,\"walletId\":7777,\"path\":\"777path\",\"updateDateTime\":null},{\"id\":200,\"walletId\":8888,\"path\":\"888path\",\"updateDateTime\":null}]"))
			).andDo(
					print()
			);
	}

	@Test
	public void testCreate() throws Exception {

		Mockito.when(mockAddressService.create(Mockito.any()))
					.thenReturn(new Address(100L, 9999L, "999path"));

		AddressPostRequest request = AddressPostRequest.builder()
				.walletId(9999L)
				.path("999path")
				.build();

		mockMvc.perform(
				post("/addresses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getObjectMapper().writeValueAsBytes(request)))
			.andExpect(
				status().isOk()
			).andExpect(
				result -> assertThat(result.getResponse().getContentAsString(), is("{\"id\":100,\"walletId\":9999,\"path\":\"999path\",\"updateDateTime\":null}"))
			).andDo(
				print()
			);
	}

	@Test
	public void testUpdate() throws Exception {

		Mockito.when(mockAddressService.update(Mockito.anyLong(), Mockito.any()))
			.thenReturn(new Address(100L, 9999L, "999path"));

		AddressPostRequest request = AddressPostRequest.builder()
				.walletId(9999L)
				.path("999path")
				.build();

		mockMvc.perform(
				put("/addresses/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getObjectMapper().writeValueAsBytes(request)))
			.andExpect(
					status().isOk()
			).andExpect(
					result -> assertThat(result.getResponse().getContentAsString(), is("{\"id\":100,\"walletId\":9999,\"path\":\"999path\",\"updateDateTime\":null}"))
			).andDo(
					print()
			);
	}

	@Test
	public void testDelete200() throws Exception {

		Mockito.when(mockAddressService.findAddress(Mockito.anyLong()))
					.thenReturn(Optional.ofNullable(new Address(100L, 9999L, "999path")));

		mockMvc.perform(
				delete("/addresses/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
					status().isOk()
			).andExpect(
					result -> assertThat(result.getResponse().getContentAsString(), is(""))
			).andDo(
					print()
			);
	}

	@Test
	public void testDelete404() throws Exception {

		Mockito.when(mockAddressService.findAddress(Mockito.anyLong()))
					.thenReturn(Optional.empty());

		mockMvc.perform(
				delete("/addresses/1")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(
					status().isNotFound()
			).andExpect(
					result -> assertThat(result.getResponse().getContentAsString(), is(""))
			).andDo(
					print()
			);
	}


	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			return new ObjectMapper();
		}
		return this.objectMapper;
	}
}