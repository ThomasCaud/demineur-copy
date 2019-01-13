package ai12.maven_ai12.com.interfaces;

import java.util.UUID;

import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

/**
 *
 * IInsideComCliForDataCli.java Interface class that has the following methods
 *
 * @author Com
 * @since 11-07-2018
 */
public interface IInsideComCliForDataCli {
	/**
	 * Initialize client engine
	 * 
	 * @param pServerIP   server IP
	 * @param pPortEcoute Listening port
	 */
	void initializeComClientEngine(String pServerIP, int pPortEcoute);

	/**
	 * Starts client engine
	 * 
	 * @throws Exception
	 * 
	 */
	void startComClientEngine() throws InternalError;

	/**
	 * Stops client engine
	 * 
	 * @throws Exception
	 * 
	 */
	void stopComClientEngine();

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftPlayer TBD - Parameter description
	 */
	void connection(SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 */
	void forfeitGame();

	/**
	 * TBD - Method description
	 * 
	 * @param pMessage TBD - Parameter description
	 */
	void sendMessage(Message pMessage);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftPlayer TBD - Parameter description
	 */
	void leaveGame(SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftGame TBD - Parameter description
	 */
	void startGame(SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftGame   TBD - Parameter description
	 * @param pSoftPlayer TBD - Parameter description
	 */
	void joinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftGame   TBD - Parameter description
	 * @param pSoftPlayer TBD - Parameter description
	 */
	void joinGameAsViewer(SoftGame pSoftGame, SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 * 
	 * @param pPlayerProfile TBD - Parameter description
	 */
	void modifyPlayerProfile(PlayerProfile pPlayerProfile);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftPlayerId TBD - Parameter description
	 */
	void logout(UUID pSoftPlayerId);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftGame TBD - Parameter description
	 */
	void createGame(SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 * 
	 * @param pShot the shot related to the flag
	 */
	void sendNewFlag(Shot pShot);

	/**
	 * TBD - Method description
	 * 
	 * @param pShot the actual shot
	 */
	void sendShot(Shot pShot);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftPlayer the player whose timer expired
	 */
	void sendTimerExpired(SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 * 
	 * @return TBD - Return description
	 * @param pSoftPlayer TBD - Parameter description
	 * @param pSoftGame   TBD - Parameter description
	 */
	void requestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 * 
	 * @return TBD - Return description
	 * @param pSoftPlayer TBD - Parameter description
	 * @param pSoftGame   TBD - Parameter description
	 */
	void requestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 * 
	 * @param pSoftPlayer TBD - Parameter description
	 * @param pSoftGame   TBD - Parameter description
	 */
	void denyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * ask for current users connected to server
	 * 
	 */
	void askCurrentUsers();

	/**
	 * ask for specific player profile
	 * 
	 */
	void askPlayerProfile(SoftPlayer pSoftPlayer);

	/**
	 * ask to save the current game
	 * 
	 */
	void askGameToSave(SoftPlayer pSoftPlayer);

	/**
	 * TBD - Method description
	 *
	 * @param pSoftPlayer TBD - Parameter description
	 * @param pSoftGame   TBD - Parameter description
	 */
	void acceptPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 *
	 * @param pSoftPlayer TBD - Parameter description
	 * @param pSoftGame   TBD - Parameter description
	 */
	void acceptViewerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * TBD - Method description
	 */
	void quitGame();

	/**
	 * TBD - Method description
	 */
	void sendAskCurrentGames(UUID pRequestor);

	/**
	 * TBD - Method description
	 */
	void sendAskCurrentUsers(UUID pRequestor);

	/**
	 * TBD - Method description
	 */
	void sendAbortGame(SoftPlayer pSoftPlayer);

	/**
	 * get the server port
	 * 
	 * @return the server port
	 */
	Integer getServerPort();

	/**
	 * get the server IP
	 * 
	 * @return the server IP
	 */
	String getServerIP();
}