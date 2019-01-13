package ai12.maven_ai12.data.interfaces;

import java.util.List;
import java.util.UUID;

import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

public interface IInsideDataCliForMain {

	void login(String pLogin, String pPassword, String pIp, Integer pPort) throws InternalError, Exception;

	void register(PlayerProfile pPlayerProfile, String pPassword)
			throws InvalidFieldException, EmptyFieldException, InvalidLoginException;

	void sendAskCurrentGames();

	void sendAskCurrentUsers();

	List<SoftGame> getSavedGamesList();

	void sendAskPlayerProfile(UUID pIdPlayer);

	void modifyOwnPlayerProfile(PlayerProfile pPlayerProfile);

	void changeServerIp(String pIp);

	void createGame(SoftGame pGame) throws EmptyFieldException;

	void logout();

	void requestJoinGameAsPlayer(SoftGame pSoftGame);

	void requestJoinGameAsViewer(SoftGame pSoftGame);

	void denyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void acceptPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void joinGameAsPlayer(SoftGame pGame);

	void joinGameAsViewer(SoftGame pGame);

	void replaySavedGame(SoftGame pGame) throws SavedGameNotFoundException;

	PlayerProfile getOwnPlayerProfile();

	void startGame(SoftGame pGame);

	List<String> getLocalLogins();

	String getServerIp();

	Integer getServerPort();

	void abortGame();

	class EmptyFieldException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	class InvalidFieldException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	class InvalidLoginException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	class SavedGameNotFoundException extends Exception {
		private static final long serialVersionUID = 1L;
	}

}
