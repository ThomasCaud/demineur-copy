package ai12.maven_ai12.beans;

import java.util.ArrayList;
import java.util.Date;

public class Game extends SoftGame {

	private static final long serialVersionUID = 8964606322547742923L;

	private Date createdAt;
	private Grid grid;
	private ArrayList<Message> messages;
	private ArrayList<Shot> shots;

	public Game() {
		super();
		this.setCreatedAt(new Date());
		this.setGrid(getInitializedGrid());
		this.setMessages(new ArrayList<Message>());
		this.setShots(new ArrayList<Shot>());
	}

	public Game(SoftGame pGame) {
		super();
		this.setIdGame(pGame.getIdGame());
		this.setGameParameters(pGame.getGameParameters());
		this.setPlayers(pGame.getPlayers());
		this.setViewers(pGame.getViewers());
		this.setCreator(pGame.getCreator());
		this.setStatus(pGame.getStatus());
		this.setName(pGame.getName());
		this.setCreatedAt(new Date());
		this.setGrid(getInitializedGrid());
		this.setMessages(new ArrayList<Message>());
		this.setShots(new ArrayList<Shot>());
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public ArrayList<Shot> getShots() {
		return shots;
	}

	public void setShots(ArrayList<Shot> shots) {
		this.shots = shots;
	}

	public boolean bombsAreDiscovered() {
		return grid.getNumberOfBombsDiscovered() == getGameParameters().getNbMines();
	}

	public boolean allSafeSquaresAreDiscovered() {
		return getGrid().getNumberOfHiddenSquares() == getGameParameters().getNbMines();
	}

	public boolean isOver() {
		return bombsAreDiscovered() || allSafeSquaresAreDiscovered() || getPlayers().size() == 0;
	}

	public void addPlayerLost(SoftPlayer vPlayer) {
		this.getPlayers().remove(vPlayer);
		this.getLosers().add(vPlayer);
	}

	public Grid getInitializedGrid() {
		final int vRow = getGameParameters().getNbRow();
		final int vCol = getGameParameters().getNbCol();

		return new Grid(vRow, vCol, getGameParameters().getNbMines());
	}
}
