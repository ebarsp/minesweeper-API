package com.deviget.minesweeper.infrastructure.configurations;

import com.deviget.minesweeper.application.GameRepository;
import com.deviget.minesweeper.application.MinesweeperService;
import com.deviget.minesweeper.infrastructure.repositories.MemoryGameRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinesweeperConfig {

	@Bean
	public GameRepository gameRepository() {
		return new MemoryGameRepository();
	}

	@Bean
	public MinesweeperService minesweeperService() {
		return new MinesweeperService(gameRepository());
	}
}
