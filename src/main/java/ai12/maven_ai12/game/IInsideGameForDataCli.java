package ai12.maven_ai12.game;

import java.util.ArrayList;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;
import javafx.stage.Stage;

/**
 * 
 * IInsideGameForDataCli.java Interface description
 * 
 * @author IHM-GAME
 * @since 11-07-2018
 */
public interface IInsideGameForDataCli {

	/**
	 * Notify when a new observer joins the game
	 * 
	 * @param pSoftPlayer New observer
	 */
	void notifyObserverJoin(SoftPlayer pSoftPlayer);

	/**
	 * Notify when a user leaves the game
	 * 
	 * @param pSoftPlayer User leaving the game
	 */
	void notifyObserverLeft(SoftPlayer pSoftPlayer);

	/**
	 * Notify when the game starts
	 * 
	 * @param pGame  Game to be started
	 * @param pStage Current stage to be used
	 */
	void notifyGameStart(Game pGame, Stage pStage);

	/**
	 * Notify when a game is finished
	 */
	void notifyGameEnd(ArrayList<SoftPlayer> pSoftPlayers);

	/**
	 * Notify when a player abandons the game
	 * 
	 * @param pSoftPlayer Leaving player
	 */
	void notifyPlayerForfeit(SoftPlayer pSoftPlayer);

	/**
	 * Notify when a new message is sent in chat
	 * 
	 * @param pMessage New message
	 */
	void notifyNewMessage(Message pMessage);

	/**
	 * Notify when current player changes
	 * 
	 * @param pSoftPlayer New current player
	 */
	void notifyNextTurn(SoftPlayer pSoftPlayer);

	/**
	 * Notify when current player is self client
	 */
	void notifyYourTurn();

	/**
	 * Notify when a new shot is played
	 * 
	 * @param pShot New shot played
	 */
	void notifyNewShot(Shot pShot);

	/**
	 * Notify when a new flag is put
	 * 
	 * @param pShot Shot played
	 */
	void notifyNewFlag(Shot pShot);

	/**
	 * Notify when a bomb is uncovered
	 * 
	 * @param pShot Shot played
	 */
	void notifyUncoverBomb(Shot pShot);

	/**
	 * Notify when game is over for a player
	 * 
	 * @param pSoftPlayer Loser
	 */
	void notifyGameOver(SoftPlayer pSoftPlayer);

	/**
	 * Notify when a square is uncovered
	 * 
	 * @param pBox Uncovered box
	 */
	void notifyUncoverSquares(Box pBox);

	/**
	 * Get all chat for new observer
	 */
	void notifyUpdateChat();

	/**
	 * Notify when timer expires
	 * 
	 * @param pSoftPlayer Loser
	 */
	void notifyTimerExpired(SoftPlayer pSoftPlayer);

	/**
	 * Notify when a game is replayed
	 * 
	 * @param pGame
	 *            Replayed game
	 */
	void replayGame(Game pGame);
}
