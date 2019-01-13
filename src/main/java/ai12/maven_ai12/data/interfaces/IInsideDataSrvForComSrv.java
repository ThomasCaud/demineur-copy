package ai12.maven_ai12.data.interfaces;

import java.util.List;
import java.util.UUID;

import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;
import ai12.maven_ai12.data.exceptions.PlayerNotConnectedException;

public interface IInsideDataSrvForComSrv {

	void addUser(SoftPlayer pSoftPlayer) throws PlayerAlreadyConnectedException;

	void deleteUser(SoftPlayer pSoftPlayer) throws PlayerNotConnectedException;

	void createGame(SoftGame pSoftGame) throws GameAlreadyCreatedException;

	void updateGame(SoftGame pSoftGame);

	void addFlag(Shot pShot) throws SquareAlreadyDiscoveredException, AnotherActionExceptedException;

	void addShot(Shot pShot) throws SquareAlreadyDiscoveredException, AnotherActionExceptedException;

	void saveSurrender(UUID pIdPlayer);

	void addMessage(Message pMessage) throws Exception;

	void sendTimeExpiredUser(SoftPlayer pSoftPlayer);

	Game getGameToSave(UUID pIdPlayer);

	void sendStartGame(SoftGame pSoftGame);

	void joinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void joinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void sendQuitGame(SoftPlayer pSoftPlayer);

	void playerSurrendered(SoftPlayer pSoftPlayer);

	void abortGame(SoftPlayer pSoftPlayer);

	List<SoftGame> getCurrentGames();

	List<SoftPlayer> getCurrentPlayers();

	class SquareAlreadyDiscoveredException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	class AnotherActionExceptedException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
