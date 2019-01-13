package ai12.maven_ai12.beans;

import java.util.ArrayList;

public class SavedPlayer extends PlayerProfile {

	private static final long serialVersionUID = 1L;

	private ArrayList<Game> savedGames;

	public SavedPlayer() {
		super();
		this.setSavedGames(new ArrayList<Game>());
	}

	public SavedPlayer(PlayerProfile pPlayerProfile) {
		super();
		this.setSavedGames(new ArrayList<Game>());
		this.setFirstname(pPlayerProfile.getFirstname());
		this.setLastname(pPlayerProfile.getLastname());
		this.setDateOfBirth(pPlayerProfile.getDateOfBirth());
		this.setNbForfeit(pPlayerProfile.getNbForfeit());
		this.setNbPlay(pPlayerProfile.getNbPlay());
		this.setNbWin(pPlayerProfile.getNbWin());
		this.setAvatar(pPlayerProfile.getAvatar());
		this.setIdPlayer(pPlayerProfile.getIdPlayer());
		this.setLogin(pPlayerProfile.getLogin());
	}

	public ArrayList<Game> getSavedGames() {
		return savedGames;
	}

	public void setSavedGames(ArrayList<Game> savedGames) {
		this.savedGames = savedGames;
	}
}
