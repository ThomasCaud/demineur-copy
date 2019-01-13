package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.log4j.Logger;

import ai12.maven_ai12.data.interfaces.InsideDataSrvForComSrvImpl;

public class SoftGame extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -267690900797298692L;
	private static Logger logger = Logger.getLogger(InsideDataSrvForComSrvImpl.class);

	private UUID idGame;
	private ArrayList<SoftPlayer> players;
	private ArrayList<SoftPlayer> viewers;
	private ArrayList<SoftPlayer> losers;
	private SoftPlayer creator;
	private GameParameters gameParameters;
	private Status status;
	private String name;

	public SoftGame() {
		super();
		this.setIdGame(UUID.randomUUID());
		this.setPlayers(new ArrayList<SoftPlayer>());
		this.setViewers(new ArrayList<SoftPlayer>());
		this.setLosers(new ArrayList<SoftPlayer>());
		this.setCreator(new SoftPlayer());
		this.setGameParameters(new GameParameters());
		this.setStatus(Status.CREATED);
		this.setName("Undefined soft game");
	}

	public SoftGame(Game game) {
		super();
		this.setIdGame(game.getIdGame());
		this.setPlayers(game.getPlayers());
		this.setViewers(game.getViewers());
		this.setLosers(game.getLosers());
		this.setCreator(game.getCreator());
		this.setGameParameters(game.getGameParameters());
		this.setStatus(game.getStatus());
		this.setName(game.getName());
	}

	public SoftGame(GameParameters gameParameters, String name) {
		super();
		this.setIdGame(UUID.randomUUID());
		this.setPlayers(new ArrayList<SoftPlayer>());
		this.setViewers(new ArrayList<SoftPlayer>());
		this.setLosers(new ArrayList<SoftPlayer>());
		this.setCreator(new SoftPlayer());
		this.setGameParameters(gameParameters);
		this.setStatus(Status.CREATED);
		this.setName(name);
	}

	public SoftGame(GameParameters gameParameters, SoftPlayer creator, String name) {
		super();
		this.setIdGame(UUID.randomUUID());
		this.setPlayers(new ArrayList<SoftPlayer>());
		this.setViewers(new ArrayList<SoftPlayer>());
		this.setCreator(creator);
		this.setGameParameters(gameParameters);
		this.setStatus(Status.CREATED);
		this.setName(name);
	}

	public UUID getIdGame() {
		return idGame;
	}

	public void setIdGame(UUID idGame) {
		this.idGame = idGame;
	}

	public ArrayList<SoftPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<SoftPlayer> players) {
		this.players = players;
	}

	public ArrayList<SoftPlayer> getViewers() {
		return viewers;
	}

	public void setViewers(ArrayList<SoftPlayer> viewers) {
		this.viewers = viewers;
	}

	public SoftPlayer getCreator() {
		return creator;
	}

	public void setCreator(SoftPlayer creator) {
		this.creator = creator;
	}

	public GameParameters getGameParameters() {
		return gameParameters;
	}

	public void setGameParameters(GameParameters gameParameters) {
		this.gameParameters = gameParameters;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SoftPlayer> getLosers() {
		return losers;
	}

	public void setLosers(ArrayList<SoftPlayer> losers) {
		this.losers = losers;
	}

	public SoftPlayer getPlayer(UUID pPlayerId) {
		for (SoftPlayer p : players) {
			if (p.getIdPlayer().equals(pPlayerId)) {
				return p;
			}
		}
		logger.error("There is no player with UUID " + pPlayerId);
		return null;
	}

	public SoftPlayer getSpectator(UUID pPlayerId) {
		for (SoftPlayer p : viewers) {
			if (p.getIdPlayer().equals(pPlayerId)) {
				return p;
			}
		}
		logger.error("There is no spectator with UUID " + pPlayerId);
		return null;
	}
}
