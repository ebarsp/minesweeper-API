package com.deviget.minesweeper.infrastructure.controllers;

import com.deviget.minesweeper.domain.CellPosition;
import com.deviget.minesweeper.domain.IGame;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GameDTO;
import com.deviget.minesweeper.infrastructure.controllers.dtos.GridPayloadDTO;
import com.deviget.minesweeper.infrastructure.controllers.mappers.GameMapper;
import com.deviget.minesweeper.application.GameService;
import com.deviget.minesweeper.infrastructure.controllers.mappers.GridMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v0/games")
public class GameController {
	private final GameService gameService;
	private final GameMapper gameMapper;
	private final GridMapper gridMapper;

	public GameController(GameService gameService, GameMapper gameMapper, GridMapper gridMapper) {
		this.gameService = gameService;
		this.gameMapper = gameMapper;
		this.gridMapper = gridMapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GameDTO create(@RequestBody GridPayloadDTO grid) {
		final IGame game = gameService.create(gridMapper.apiToDomain(grid));
		return gameMapper.domainToApi(game);
	}

	@GetMapping("/{id}")
	public GameDTO get(@PathVariable UUID id) {
		return gameMapper.domainToApi(gameService.get(id));
	}

	@PostMapping("/{id}/pause")
	public GameDTO pause(@PathVariable UUID id) {
		return gameMapper.domainToApi(gameService.pause(id));
	}

	@PostMapping("/{id}/unpause")
	public GameDTO unpause(@PathVariable UUID id) {
		return gameMapper.domainToApi(gameService.unpause(id));
	}

	@PostMapping("/{id}/cells/{x}/{y}/uncover")
	public GameDTO uncoverACell(@PathVariable UUID id, @PathVariable Integer x, @PathVariable Integer y) {
		return gameMapper.domainToApi(gameService.uncoverACell(id, new CellPosition(x, y)));
	}

	@PostMapping("/{id}/cells/{x}/{y}/mark")
	public GameDTO markACell(@PathVariable UUID id, @PathVariable Integer x, @PathVariable Integer y) {
		return gameMapper.domainToApi(gameService.markACell(id, new CellPosition(x, y)));
	}

	@PostMapping("/{id}/cells/{x}/{y}/unmark")
	public GameDTO unmarkACell(@PathVariable UUID id, @PathVariable Integer x, @PathVariable Integer y) {
		return gameMapper.domainToApi(gameService.unmarkACell(id, new CellPosition(x, y)));
	}

	@PostMapping("/{id}/cells/{x}/{y}/flag")
	public GameDTO flagACell(@PathVariable UUID id, @PathVariable Integer x, @PathVariable Integer y) {
		return gameMapper.domainToApi(gameService.flagACell(id, new CellPosition(x, y)));
	}

	@PostMapping("/{id}/cells/{x}/{y}/unflag")
	public GameDTO unflagACell(@PathVariable UUID id, @PathVariable Integer x, @PathVariable Integer y) {
		return gameMapper.domainToApi(gameService.unflagACell(id, new CellPosition(x, y)));
	}
}
