package com.deviget.minesweeper.functional;

import com.deviget.minesweeper.infrastructure.controllers.MinesweeperController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MinesweeperController.class)
public class MinesweeperAPITest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void whenCreateAGame_thenReturnANewGame() throws Exception {
		final String json = "{ \"grid_x\": 3, \"grid_y\": 3, \"mines\": 1 }";
		mvc.perform(post("/v0/minesweeper").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()));
	}

	@Test
	public void whenCreateAGameWithoutArguments_thenBadRequest() throws Exception {
		mvc.perform(post("/v0/minesweeper").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest());
	}
}
