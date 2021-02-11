package com.deviget.minesweeper.functional;

import com.deviget.minesweeper.infrastructure.controllers.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckAPITest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void whenPingHealthCheck_thenStatusOK() throws Exception {
		mvc.perform(get("/health")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
