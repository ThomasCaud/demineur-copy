package ai12.maven_ai12.main.interfaces;

import java.util.ArrayList;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import javafx.stage.Stage;

/**
 *
 * IInsideMainForDataCli.java Interface class that has the following methods
 *
 * @author MainClient
 * @since 11-07-2018
 */
public interface IInsideMainForDataCli {

	void notifyCurrentGames(ArrayList<SoftGame> pSoftGames);

	void notifyNewGame(SoftGame pSoftGame);

	void notifyGameIsReady(SoftGame pSoftGame);

	void notifyGameJoinDenied();

	void notifyGameChange(SoftGame pSoftGame);

	void notifyGameChangeForAdmin(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	void notifyPlayerLeftLobby(SoftGame pSoftGame);

	void notifyPlayerLeftLobbyAdmin(SoftGame pSoftGame);

	void notifyLobbyLeaving();

	void notifyGameChangeForAdmin(SoftGame pSoftGame);

	void notifyCurrentUsers(ArrayList<SoftPlayer> pSoftPlayers);

	void notifyNewUser(SoftPlayer pSoftPlayer);

	void notifyQuitUser(SoftPlayer pSoftPlayer);

	void notifyRequestGameJoinAsPlayer(SoftPlayer pSoftPlayer);

	void notifyGameJoin(SoftGame pSoftGame);

	void notifyGameChangeForUsers(SoftGame pSoftGame);

	void notifyQuitPendingGame();

	void notifyGameQuit();

	Stage getStage();

	void notifyConnectionSuccess(ArrayList<SoftPlayer> pSoftPlayers);

	void notifyConnectionSuccessWithoutServer();

	void notifyGameDeleted(SoftGame pSoftGame);

	void notifyGameAborted();

}