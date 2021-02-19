import requests
import json


class MinesweeperAPIClient:

	def __init__(self, base_url, game_id=None):
		self.base_url = base_url
		self.game_id = game_id

	def _compose_url(self, path):
		return self.base_url + path

	def _validate_game_created(self):
		if not self.game_id:
			raise Exception("You should create a game first")

	def create_game(self, width, height, mines):
		"""
		Creates a new game with the game parameters
		:param width:
		:param height:
		:param mines:
		:return: a game as a json object
		"""
		payload = {"width": width, "height": height, "mines": mines}
		headers = {"Content-Type": "application/json"}
		response = requests.post(self._compose_url("/v0/games"), headers=headers, data=json.dumps(payload))
		if response.status_code is not 201:
			raise Exception("There was an error in the game's creation", response.text)
		game = response.json()
		self.game_id = game["game_id"]
		return game

	def get_game(self):
		"""
		Gets an existent game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.get(self._compose_url("/v0/games/{}".format(self.game_id)))
		if response.status_code is not 200:
			raise Exception("There was an error getting the game", response.text)
		return response.json()

	def pause_game(self):
		"""
		Pauses an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/pause".format(self.game_id)))
		if response.status_code is not 200:
			raise Exception("There was an error pausing the game", response.text)
		return response.json()

	def unpause_game(self):
		"""
		Unpauses a paused game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/unpause".format(self.game_id)))
		if response.status_code is not 200:
			raise Exception("There was an error unpausing the game", response.text)
		return response.json()

	def flag_cell(self, x, y):
		"""
		Flags a game's cell on an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/cells/{}/{}/flag".format(self.game_id, x, y)))
		if response.status_code is not 200:
			raise Exception("There was an error flagging the cell", response.text)
		return response.json()

	def unflag_cell(self, x, y):
		"""
		Un-flags a flagged game's cell on an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/cells/{}/{}/unflag".format(self.game_id, x, y)))
		if response.status_code is not 200:
			raise Exception("There was an error unflagging the cell", response.text)
		return response.json()

	def mark_cell(self, x, y):
		"""
		Marks a game's cell on an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/cells/{}/{}/mark".format(self.game_id, x, y)))
		if response.status_code is not 200:
			raise Exception("There was an error marking the cell", response.text)
		return response.json()

	def unmark_cell(self, x, y):
		"""
		Un-marks a marked game's cell on an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/cells/{}/{}/unmark".format(self.game_id, x, y)))
		if response.status_code is not 200:
			raise Exception("There was an error unmarking the cell", response.text)
		return response.json()

	def uncover_cell(self, x, y):
		"""
		Uncovers a game's cell on an ongoing game
		:return: a game as a json object
		"""
		self._validate_game_created()
		response = requests.post(self._compose_url("/v0/games/{}/cells/{}/{}/uncover".format(self.game_id, x, y)))
		if response.status_code is not 200:
			raise Exception("There was an error uncovering the cell", response.text)
		return response.json()
