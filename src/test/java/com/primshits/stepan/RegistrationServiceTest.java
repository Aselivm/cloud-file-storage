package com.primshits.stepan;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class RegistrationServiceTest {

	//todo написать гет запросы на страницы

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Container
	private static final MySQLContainer<?> container = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url",container::getJdbcUrl);
		registry.add("spring.datasource.username",container::getUsername);
		registry.add("spring.datasource.password",container::getPassword);
		registry.add("spring.jpa.generate-ddl",()->true);
	}
	@Transactional
	@Test
	void registrationAndLogin() throws Exception {
		String username = "exampleUser";
		String password = "examplePassword";

		registerUser(username, password);
		login(username, password);

		assertUserExistsInDatabase(username);
	}

	private void registerUser(String username, String password) throws Exception {
		MultiValueMap<String, String> registrationData = new LinkedMultiValueMap<>();
		registrationData.add("name", username);
		registrationData.add("password", password);

		mockMvc.perform(
				post("/sign-up")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.params(registrationData)
		).andExpect(status().isFound());
	}

	private void login(String username, String password) throws Exception {
		MultiValueMap<String, String> loginData = new LinkedMultiValueMap<>();
		loginData.add("username", username);
		loginData.add("password", password);

		mockMvc.perform(
						post("/login")
								.contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.params(loginData)
				).andExpect(status().isFound())
				.andExpect(redirectedUrl("/home"));
	}

	private void assertUserExistsInDatabase(String username) {
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE name = ?", Integer.class, username);
		assertEquals(1, count);
	}
}

