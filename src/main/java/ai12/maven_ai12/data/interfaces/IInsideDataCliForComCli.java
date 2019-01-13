package ai12.maven_ai12.data.interfaces;

import java.util.ArrayList;
import java.util.List;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

public interface IInsideDataCliForComCli {

	void sendListPlayerConnectedToUser(ArrayList<SoftPlayer> pPlayers);

	void sendNewFlag(Shot pShot);

	void sendShot(SoftPlayer pPlayer, Coordinates pCoordinates);

	void sendChat(Message pMessage);

	void sendTimerExpired(SoftPlayer pPlayer);

	void sendNewResult(Shot pShot, ArrayList<Box> pBoxes, boolean pGameIsOver, SoftPlayer pNextPlayer,
			ArrayList<SoftPlayer> winners);

	void addNewConnection(SoftPlayer pPlayer);

	void deleteUser(SoftPlayer pPlayer);

	@Deprecated
	void sendNewTurn(SoftPlayer pPlayer);

	void sendStartGame(Game pGame);

	void sendNewObserver(SoftPlayer pPlayer);

	void sendUserLeft(SoftPlayer pPlayer);

	void notifyRequestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void notifyRequestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void joinGameDenied(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void notifyGameJoinAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayerJoining);

	void notifyGameJoinAsViewer(SoftGame pSoftGame);

	void notifyCreateGame(SoftGame pSoftGame);

	void opponentHasSurrendered(SoftPlayer pPlayer);

	void gameToSaveReceived(Game pGame);

	void notifyPlayerProfile(SoftPlayer pPlayer);

	void notifyCurrentGames(List<SoftGame> pSoftGames);

	void notifyCurrentUsers(List<SoftPlayer> pSoftPlayers);

	void notifySavedGamesList(List<SoftGame> pSavedGames);

	void notifyNewGame(SoftGame pSoftGame);

	void notifyGameAborted(SoftGame pSoftGame);

	void notifyGameDeleted(SoftGame pSoftGame);

	void notifyPlayerLeftLobby(SoftGame pSoftGame);

	void notifyPlayerLeftLobbyAdmin(SoftGame pSoftGame);

	void notifyLobbyLeaving();
}
