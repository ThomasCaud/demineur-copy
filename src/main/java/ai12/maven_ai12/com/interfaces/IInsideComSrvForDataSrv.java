package ai12.maven_ai12.com.interfaces;

import java.io.IOException;
import java.util.List;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

/**
 *
 * IInsideComSrvForDataSrv.java Interface description
 *
 * @author COM
 * @since 11-07-2018
 */
public interface IInsideComSrvForDataSrv {

	void startEngine(Integer pPort) throws IOException;

	/**
	 * Methode description
	 *
	 * @param pSoftPlayers TBD - argument description
	 * @param pShot        TBD - argument description
	 */
	void spreadShot(List<SoftPlayer> pSoftPlayers, Shot pShot);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer  connected player
	 * @param pSoftPlayers already connected players
	 */
	void spreadConnection(SoftPlayer pSoftPlayer, List<SoftPlayer> pSoftPlayers);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayers TBD - argument description
	 */
	void spreadForfeitGame(List<SoftPlayer> pSoftPlayers);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer TBD - argument description
	 * @param pMessage    TBD - argument description
	 */
	void spreadMessage(SoftPlayer pSoftPlayer, Message pMessage);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayers TBD - argument description
	 */
	void spreadLeaveGame(List<SoftPlayer> pSoftPlayers);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer1 TBD - argument description
	 * @param pSoftPlayer2 TBD - argument description
	 * @param pGame        TBD - argument description
	 */
	void spreadStartGame(List<SoftPlayer> pSoftPlayers, Game pGame);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer TBD - argument description
	 * @param pSoftGame   TBD - argument description
	 */
	void spreadRequestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer TBD - argument description
	 * @param pSoftGame   TBD - argument description
	 */
	void spreadRequestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * Methode description
	 *
	 * @param pSoftGame          TBD - argument description
	 * @param pSoftPlayerJoining
	 * @param pSoftPlayers       TBD - argument description
	 */
	void spreadJoinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayerJoining, List<SoftPlayer> pSoftPlayers);

	/**
	 * Methode description
	 *
	 * @param pSoftGame    TBD - argument description
	 * @param pSoftPlayers TBD - argument description
	 */
	void spreadJoinGameAsViewer(SoftGame pSoftGame, List<SoftPlayer> pSoftPlayers);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer the disconnected player
	 */
	public void spreadLogout(SoftPlayer pSoftPlayer);

	/**
	 * Methode description
	 *
	 * @param pSoftGame TBD - argument description
	 */
	public void spreadCreateGame(SoftGame pSoftGame);

	@Deprecated
	void spreadConnection(SoftPlayer pSoftPlayer);

	/**
	 * Methode description
	 *
	 * @param pShot       the shot related to the flag
	 * @param pSoftPlayer the player to send the message to
	 */
	void spreadNewFlag(Shot pShot, SoftPlayer pSoftPlayer);

	/**
	 * Methode description
	 *
	 * @param pShot       the actual shot
	 * @param pBoxes      the collection of boxes in this result
	 * @param pSoftPlayer the player to send the message to
	 * @param pGameIsOver
	 * @param pNextPlayer the player who plays next
	 */
	void spreadResult(Shot pShot, List<Box> pBoxes, SoftPlayer pSoftPlayer, boolean pGameIsOver,
			SoftPlayer pNextPlayer, List<SoftPlayer> pWinners);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer1 the player to receive to notification
	 * @param pSoftPlayer2 the player who lost
	 */
	void spreadTimerExpired(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer TBD - argument description
	 * @param pSoftGame   TBD - argument description
	 */
	void spreadDenyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer1 TBD - argument description
	 * @param pSoftPlayer2 TBD - argument description
	 */
	void spreadNewObserver(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2);

	/**
	 * Methode description
	 *
	 * @param pGame TBD - argument description
	 */
	void sendGameJoin(Game pGame);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer1 TBD - argument description
	 * @param pSoftPlayer2 TBD - argument description
	 */
	void spreadUserLeft(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayer1 TBD - argument description
	 * @param pSoftPlayer2 TBD - argument description
	 */
	void spreadPlayerSurrendered(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2);

	/**
	 * Methode description
	 *
	 * @param pSoftGame             TBD - argument description
	 * @param pListSoftPlayerInGame TBD - argument description
	 * @param pListOtherSoftPlayer  TBD - argument description
	 */
	void spreadGameAborted(SoftGame pSoftGame, List<SoftPlayer> pListSoftPlayerInGame,
			List<SoftPlayer> pListOtherSoftPlayer);

	/**
	 * Methode description
	 *
	 * @param pSoftPlayerLeft           the player who left the lobby
	 * @param pListSoftPlayerInGame 	the players who are waiting for a game to start
	 * @param pSoftPlayerAdmin  		the admin
	 */
	void spreadPlayerLeft(SoftPlayer pSoftPlayerLeft, List<SoftPlayer> pListSoftPlayerInGame,
			SoftPlayer pSoftPlayerAdmin, SoftGame pSoftGame);
}
