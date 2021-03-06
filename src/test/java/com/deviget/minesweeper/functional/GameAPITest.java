package com.deviget.minesweeper.functional;

import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.infrastructure.controllers.GameController;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameAPITest {
	@Autowired
	private MockMvc mvc;

	@Test
	public void givenAGrid_whenCreateAGame_thenReturnANewGame() throws Exception {
		final String json = "{ \"width\": 3, \"height\": 3, \"mines\": 1 }";
		mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("duration", is("0:00:00")));
	}

	@Test
	public void givenNoGrid_whenCreateAGame_thenBadRequest() throws Exception {
		mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenAGame_whenGetIt_thenReturnTheOnGoingGame() throws Exception {
		final String json = "{ \"width\": 3, \"height\": 3, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		TimeUnit.SECONDS.sleep(2);

		String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		mvc.perform(get("/v0/games/" + gameId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("duration", is("0:00:02")));
	}

	@Test
	public void givenANonExistentGame_whenGetIt_thenNotFound() throws Exception {
		mvc.perform(get("/v0/games/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenAnOngoingGame_whenPauseIt_thenGameIsPaused() throws Exception {
		final String json = "{ \"width\": 3, \"height\": 3, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		TimeUnit.SECONDS.sleep(2);

		String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		mvc.perform(post("/v0/games/" + gameId + "/pause").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("PAUSED")))
				.andExpect(jsonPath("duration", is("0:00:02")));
	}

	@Test
	public void givenAPausedGame_whenUnpauseIt_thenGameIsOngoing() throws Exception {
		final String json = "{ \"width\": 3, \"height\": 3, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		TimeUnit.SECONDS.sleep(2);

		String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		mvc.perform(post("/v0/games/" + gameId + "/pause").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("PAUSED")))
				.andExpect(jsonPath("duration", is("0:00:02")));

		mvc.perform(post("/v0/games/" + gameId + "/unpause").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("duration", is("0:00:02")));
	}

	@Test
	public void givenAGame_whenMarkACell_thenTheCellIsMarked() throws Exception {
		final String json = "{ \"width\": 2, \"height\": 2, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		final String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		final CellPosition position = new CellPosition(2, 1);
		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/mark", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("MARKED")));
	}

	@Test
	public void givenAGameWithAMarkedCell_whenUnmarkTheCell_thenTheCellIsCovered() throws Exception {
		final String json = "{ \"width\": 2, \"height\": 2, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		final String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		final CellPosition position = new CellPosition(2, 1);
		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/mark", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("MARKED")));

		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/unmark", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("COVERED")));
	}

	@Test
	public void givenAGame_whenFlagACell_thenTheCellIsFlagged() throws Exception {
		final String json = "{ \"width\": 2, \"height\": 2, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		final String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		final CellPosition position = new CellPosition(2, 1);
		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/flag", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("FLAGGED")));
	}

	@Test
	public void givenAGameWithAFlaggedCell_whenUnflagTheCell_thenTheCellIsCovered() throws Exception {
		final String json = "{ \"width\": 2, \"height\": 2, \"mines\": 1 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		final String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		final CellPosition position = new CellPosition(2, 1);
		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/flag", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("FLAGGED")));

		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/unflag", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("ONGOING")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", contains("COVERED")));
	}

	@Test
	public void givenAGame_whenUncoverACell_thenTheCellShowsItsValue() throws Exception {
		final String json = "{ \"width\": 2, \"height\": 2, \"mines\": 4 }";
		final MvcResult result = mvc.perform(post("/v0/games").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("game_id", notNullValue()))
				.andReturn();

		final String gameId = JsonPath.read(result.getResponse().getContentAsString(), "$.game_id");
		final CellPosition position = new CellPosition(2, 1);
		mvc.perform(post(String.format("/v0/games/%s/cells/%d/%d/uncover", gameId, position.getX(), position.getY())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("game_id", is(gameId)))
				.andExpect(jsonPath("status", is("LOST")))
				.andExpect(jsonPath("$.grid.cells[?(@.x=='" + position.getX() + "')][?(@.y=='" + position.getY() + "')].value", not("MINE")));
	}
}
