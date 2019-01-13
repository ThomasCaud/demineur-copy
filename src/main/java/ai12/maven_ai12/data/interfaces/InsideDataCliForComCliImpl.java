package ai12.maven_ai12.data.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.DataClientEngine;
import ai12.maven_ai12.data.managers.GameSaveManager;
import javafx.stage.Stage;

public class InsideDataCliForComCliImpl implements IInsideDataCliForComCli {

	private static Logger logger = Logger.getLogger(InsideDataCliForComCliImpl.class);
	private DataClientEngine dataClientEngine;

	public InsideDataCliForComCliImpl(DataClientEngine pDataClientEngine) {
		this.dataClientEngine = pDataClientEngine;
	}

	@Override
	public void sendListPlayerConnectedToUser(ArrayList<SoftPlayer> pPlayers) {
		logger.info("[sendListPlayerConnectedToUser] Request received from ComCli");
		logger.info("[sendListPlayerConnectedToUser] Call 'notifyConnectionSuccess' inside Main");
		dataClientEngine.setConnectedToAServer(true);
		dataClientEngine.getInsideMainForDataCli().notifyConnectionSuccess(pPlayers);
	}

	@Override
	public void sendNewFlag(Shot pShot) {
		// TODO

	}

	@Override
	public void sendShot(SoftPlayer pPlayer, Coordinates pCoordinates) {
		// TODO
	}

	@Override
	public void sendChat(Message pMessage) {
		dataClientEngine.getInsideGameForDataCli().notifyNewMessage(pMessage);
	}

	@Override
	public void sendTimerExpired(SoftPlayer pPlayer) {
		// TODO

	}

	@Override
	public void sendNewResult(Shot pShot, ArrayList<Box> pBoxes, boolean pGameIsOver, SoftPlayer pNextPlayer,
			ArrayList<SoftPlayer> winners) {
		if (pGameIsOver) {
			dataClientEngine.getInsideGameForDataCli().notifyGameEnd(winners);
		} else {
			if (pShot.getBox().getValue() == -1) {
				dataClientEngine.getInsideGameForDataCli().notifyUncoverBomb(pShot);
			} else {
				dataClientEngine.getInsideGameForDataCli().notifyNewShot(pShot);
				for (Box vBox : pBoxes) {
					dataClientEngine.getInsideGameForDataCli().notifyUncoverSquares(vBox);
				}
			}
			if (dataClientEngine.getCurrentPlayer().getIdPlayer().toString()
					.equals(pNextPlayer.getIdPlayer().toString())) {
				dataClientEngine.getInsideGameForDataCli().notifyYourTurn();
			} else {
				dataClientEngine.getInsideGameForDataCli().notifyNextTurn(pNextPlayer);
			}
		}
	}

	@Override
	public void addNewConnection(SoftPlayer pPlayer) {
		logger.info("[addNewConnection] Request received from ComCli");
		logger.info("[addNewConnection] Call 'notifyNewUser' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyNewUser(pPlayer);
	}

	@Override
	public void deleteUser(SoftPlayer pPlayer) {
		logger.info("[deleteUser] Request received from ComCli");
		logger.info("[deleteUser] Call 'notifyQuitUser' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyQuitUser(pPlayer);
	}

	@Deprecated
	@Override
	public void sendNewTurn(SoftPlayer pPlayer) {
		// TODO
	}

	@Override
	public void sendStartGame(Game pGame) {
		logger.info("[sendStartGame] Request received from ComCli");
		logger.info("[sendStartGame] Fetching the stage from Main...");
		Stage stage = dataClientEngine.getInsideMainForDataCli().getStage();
		logger.info("[sendStartGame] Notifying Game of this event, by giving the Stage reference...");
		dataClientEngine.getInsideGameForDataCli().notifyGameStart(pGame, stage);

		SoftPlayer vCreator = pGame.getCreator();
		SoftPlayer vCurrentPlayer = dataClientEngine.getCurrentPlayer();

		if (vCreator.getIdPlayer().equals(vCurrentPlayer.getIdPlayer())) {
			dataClientEngine.getInsideGameForDataCli().notifyYourTurn();
		} else {
			dataClientEngine.getInsideGameForDataCli().notifyNextTurn(pGame.getCreator());
		}
	}

	@Override
	public void sendNewObserver(SoftPlayer pPlayer) {
		// TODO
	}

	@Override
	public void sendUserLeft(SoftPlayer pPlayer) {
		// TODO

	}

	@Override
	public void notifyRequestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		logger.info("[notifyRequestJoinGameAsPlayer] Request received from ComCli");
		logger.info("[notifyRequestJoinGameAsPlayer] Call 'notifyRequestGameJoinAsPlayer' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyRequestGameJoinAsPlayer(pSoftPlayer);

	}

	@Override
	public void notifyGameJoinAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayerJoining) {
		if (dataClientEngine.getCurrentPlayer().getIdPlayer().equals(pSoftGame.getCreator().getIdPlayer())) {
			// pSoftPlayer is the creator
			logger.info("Request [notifyGameChangeForAdmin] from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameChangeForAdmin(pSoftPlayerJoining, pSoftGame);
		} else if (dataClientEngine.getCurrentPlayer().getIdPlayer().equals(pSoftPlayerJoining.getIdPlayer())) {
			// pSoftPlayer is joining the game
			logger.info("Request [notifyGameJoin] from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameJoin(pSoftGame);
		} else if (pSoftGame.getPlayers().contains(dataClientEngine.getCurrentPlayer())) {
			// pSoftPlayer is a player
			logger.info("Request [notifyGameChange] from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameChange(pSoftGame);
		} else {
			// pSoftPlayer is in main view
			logger.info("Request [notifyGameChangeForUsers] from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameChangeForUsers(pSoftGame);
		}
	}

	@Override
	public void notifyGameJoinAsViewer(SoftGame pSoftGame) {
		if (dataClientEngine.getCurrentPlayer().getIdPlayer().equals(pSoftGame.getCreator().getIdPlayer())) {
			// pSoftPlayer is the creator
			logger.info("Request [notifyGameChangeForAdmin] in notifyGameJoinAsViewer from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameChangeForAdmin(pSoftGame);
		} else {
			// pSoftPlayer is the viewer
			logger.info("Request [notifyGameJoin] in notifyGameJoinAsViewer from ComCli to Main");
			dataClientEngine.getInsideMainForDataCli().notifyGameJoin(pSoftGame);
		}
	}

	@Override
	public void notifyCreateGame(SoftGame pSoftGame) {
		logger.info("[notifyCreateGame] Request received from ComCli");
		logger.info("[notifyCreateGame] Call 'notifyGameIsReady' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyGameIsReady(pSoftGame);
	}

	@Override
	public void opponentHasSurrendered(SoftPlayer pPlayer) {
		// TODO

	}

	@Override
	public void notifyCurrentGames(List<SoftGame> pSoftGames) {
		logger.info("[notifyCurrentGames] Request received from ComCli");
		logger.info("[notifyCurrentGames] Call 'notifyCurrentGames' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyCurrentGames(new ArrayList<SoftGame>(pSoftGames));
	}

	@Override
	public void notifySavedGamesList(List<SoftGame> pSavedGames) {
	}

	@Override
	public void notifyPlayerProfile(SoftPlayer pPlayer) {

	}

	@Override
	public void gameToSaveReceived(Game pGame) {
		GameSaveManager gsManager = GameSaveManager.getInstance();
		gsManager.save(pGame);
		// Need interface game for data cli here :
		// dataClientEngine.getInsideGameForDataCli().notifyGameSaved();
	}

	@Override
	public void joinGameDenied(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		logger.info("[joinGameDenied] Request received from ComCli");
		logger.info("[joinGameDenied] Call 'notifyGameJoinDenied' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyGameJoinDenied();
	}

	@Override
	public void notifyNewGame(SoftGame pSoftGame) {
		logger.info("[notifyNewGame] Request received from ComCli");
		logger.info("[notifyNewGame] Call 'notifyNewGame' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyNewGame(pSoftGame);
	}

	@Override
	public void notifyGameAborted(SoftGame pSoftGame) {
		logger.info("[notifyGameAborted] Request received from ComCli");
		logger.info("[notifyGameAborted] Call 'notifyGameAborted' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyGameAborted();
	}

	@Override
	public void notifyGameDeleted(SoftGame pSoftGame) {
		logger.info("[notifyGameDeleted] Request received from ComCli");
		logger.info("[notifyGameDeleted] Call 'notifyGameDeleted' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyGameDeleted(pSoftGame);
	}

	@Override
	public void notifyPlayerLeftLobby(SoftGame pSoftGame) {
		logger.info("[notifyPlayerLeftLobby] Request received from ComCli");
		logger.info("[notifyPlayerLeftLobby] Call 'notifyPlayerLeftLobby' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyPlayerLeftLobby(pSoftGame);
	}

	@Override
	public void notifyPlayerLeftLobbyAdmin(SoftGame pSoftGame) {
		logger.info("[notifyPlayerLeftLobbyAdmin] Request received from ComCli");
		logger.info("[notifyPlayerLeftLobbyAdmin] Call 'notifyPlayerLeftLobbyAdmin' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyPlayerLeftLobbyAdmin(pSoftGame);
	}

	@Override
	public void notifyLobbyLeaving() {
		logger.info("[notifyLobbyLeaving] Request received from ComCli");
		logger.info("[notifyLobbyLeaving] Call 'notifyLobbyLeaving' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyLobbyLeaving();
	}

	@Override
	public void notifyRequestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		logger.info("[notifyRequestJoinGameAsViewer] Request received from ComCli");
		if (pSoftGame.getGameParameters().getAllowSpectators()) {
			// Allow viewers
			dataClientEngine.getInsideComCliForDataCli().acceptViewerRequest(pSoftPlayer, pSoftGame);
		} else {
			dataClientEngine.getInsideComCliForDataCli().denyPlayerRequest(pSoftPlayer, pSoftGame);
		}
	}

	@Override
	public void notifyCurrentUsers(List<SoftPlayer> pSoftPlayers) {
		logger.info("[notifyCurrentUsers] Request received from ComCli");
		logger.info("[notifyCurrentUsers] Call 'notifyCurrentUsers' inside Main");
		dataClientEngine.getInsideMainForDataCli().notifyCurrentUsers(new ArrayList<SoftPlayer>(pSoftPlayers));
	}
}