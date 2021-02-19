# Minesweeper API

### Dashboard: [LINK](https://trello.com/b/stfoFttq/minesweeper)
### Deployed API: [LINK](https://minesweeper-1.herokuapp.com/swagger-ui.html)

## Decisions 
I decided to implement an hexagonal architecture using Java 11 and Spring boot 2.4.2 (last version),
Swagger for API documentation.

### Domain
In the domain package, you will find the whole business model and its behavior, including exceptions used in the model.
The domain implements a facade to not expose the business logic into the controller, working in this way as an immutable entity.
Each domain entity has its own validations of state to guarantee a functional instance.
Including if the state's entity is not valid for a specific action.
Cell is a game's sub resource, but I could have split the game's controller creating a cell's controller

### Application
In the application package, you will find the external dependencies(repositories), services,
and the exceptions that services and repositories use.
I could have implemented actions/use cases instead of services and test each action as C.A defines.

### Infrastructure
In the infrastructure package, you will find everything that depends on a framework.
The spring config, controllers, dtos, domain/api mappers, and repository implementations.
The implementation of game's repository is a memory repository, I could have implemented a database repository, but the assigment doesn't specify.

## Notes

### Steps to use this API

1. You should create a game
2. Now you can uncover/mark/flag a game's cell
3. If you marked/flagged a game's cell, and you want to revert it you can unmark/unflag the cell 
4. If during the game you never uncover a mined cell you'll win, otherwise you'll lose

In the deployed API link you will find the exposed endpoints for these steps,
also in this repo you will find a python api client.