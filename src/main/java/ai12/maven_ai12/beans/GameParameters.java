package ai12.maven_ai12.beans;

import java.io.Serializable;

public class GameParameters extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -2547149308245782791L;

	private boolean allowSpectators;
	private int nbMaxPlayers;
	private Difficulty difficulty;
	private int time;

	public GameParameters() {
		super();
		this.setAllowSpectators(false);
		this.setNbMaxPlayers(-1);
		this.setDifficulty(Difficulty.EASY);
		this.setTime(0);
	}

	public GameParameters(boolean allowSpectators, int nbMaxPlayers, Difficulty difficulty, int time) {
		super();
		this.setAllowSpectators(allowSpectators);
		this.setNbMaxPlayers(nbMaxPlayers);
		this.setDifficulty(difficulty);
		this.setTime(time);
	}

	public boolean getAllowSpectators() {
		return allowSpectators;
	}

	public void setAllowSpectators(boolean allowSpectators) {
		this.allowSpectators = allowSpectators;
	}

	public int getNbMaxPlayers() {
		return nbMaxPlayers;
	}

	public void setNbMaxPlayers(int nbMaxPlayers) {
		this.nbMaxPlayers = nbMaxPlayers;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getNbMines() {
		switch (getDifficulty()) {
		case EASY:
			return 10;
		case MEDIUM:
			return 40;
		default:
			return 99;
		}
	}

	public int getNbRow() {
		switch (getDifficulty()) {
		case EASY:
			return 9;
		default:
			return 16;
		}
	}

	public int getNbCol() {
		switch (getDifficulty()) {
		case EASY:
			return 9;
		case MEDIUM:
			return 16;
		default:
			return 30;
		}
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
