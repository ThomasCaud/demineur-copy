package ai12.maven_ai12.beans;

import java.util.Map;
import java.util.UUID;

public class TupleForAbortGame {
	Game game;
	Map<UUID, SoftPlayer> playersAlreadyInGame;
	Map<UUID, SoftPlayer> otherConnectedUsers;

	public TupleForAbortGame(Game pGame, Map<UUID, SoftPlayer> pPlayersAlreadyInGame,
			Map<UUID, SoftPlayer> pOtherConnectedUsers) {
		this.game = pGame;
		this.playersAlreadyInGame = pPlayersAlreadyInGame;
		this.otherConnectedUsers = pOtherConnectedUsers;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game softGame) {
		this.game = softGame;
	}

	public Map<UUID, SoftPlayer> getPlayersAlreadyInGame() {
		return playersAlreadyInGame;
	}

	public void setPlayersAlreadyInGame(Map<UUID, SoftPlayer> playersAlreadyInGame) {
		this.playersAlreadyInGame = playersAlreadyInGame;
	}

	public Map<UUID, SoftPlayer> getOtherConnectedUsers() {
		return otherConnectedUsers;
	}

	public void setOtherConnectedUsers(Map<UUID, SoftPlayer> otherConnectedUsers) {
		this.otherConnectedUsers = otherConnectedUsers;
	}

}
