package ai12.maven_ai12.data.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.Action;
import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.beans.Status;
import ai12.maven_ai12.beans.TupleForAbortGame;
import ai12.maven_ai12.data.DataServerEngine;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;
import ai12.maven_ai12.data.exceptions.PlayerNotConnectedException;

public class InsideDataSrvForComSrvImpl implements IInsideDataSrvForComSrv {

	private static Logger logger = Logger.getLogger(InsideDataSrvForComSrvImpl.class);
	private DataServerEngine mDataServerEngine;

	public InsideDataSrvForComSrvImpl(DataServerEngine pDataServerEngine) {
		this.mDataServerEngine = pDataServerEngine;
	}

	@Override
	public void addUser(SoftPlayer pSoftPlayer) throws PlayerAlreadyConnectedException {
		if (mDataServerEngine != null) {
			this.mDataServerEngine.addNewUser(pSoftPlayer);
			logger.info("Requesting ComSrv to spread this connection to every connected players.");
			this.mDataServerEngine.getInsideComSrvForDataSrv().spreadConnection(pSoftPlayer, getCurrentPlayers());
		} else {
			logger.error("mDataServerEngine shouldn't be null");
		}
	}

	@Override
	public void deleteUser(SoftPlayer pSoftPlayer) throws PlayerNotConnectedException {
		if (mDataServerEngine != null) {
			this.mDataServerEngine.deleteUser(pSoftPlayer.getIdPlayer());
			logger.info("Requesting ComSrv to spread this logout to every connected players.");
			this.mDataServerEngine.getInsideComSrvForDataSrv().spreadLogout(pSoftPlayer);
		} else {
			logger.error("mDataServerEngine shouldn't be null");
		}

	}

	@Override
	public void createGame(SoftGame pSoftGame) throws GameAlreadyCreatedException {
		Game game = new Game(pSoftGame);
		this.mDataServerEngine.addNewGame(pSoftGame);
		this.mDataServerEngine.updateUserInGame(game.getCreator().getIdPlayer(), game.getIdGame());
		logger.info("Player " + game.getCreator().getLogin() + " created the game " + pSoftGame.getIdGame());
		logger.info("The grid is the following :");
		logger.info(game.getGrid());
		logger.info("Requesting ComSrv to spread this game creation to every connected players.");
		this.mDataServerEngine.getInsideComSrvForDataSrv().spreadCreateGame(pSoftGame);
	}

	@Override
	public void updateGame(SoftGame pSoftGame) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFlag(Shot pShot) throws SquareAlreadyDiscoveredException, AnotherActionExceptedException {
		SoftPlayer vPlayer = pShot.getPlayer();
		UUID pGameId = this.mDataServerEngine.getPlayersInGames().get(vPlayer.getIdPlayer()).getValue();
		Game vGame = this.mDataServerEngine.getGames().get(pGameId);
		if (pShot.getAction() != Action.FLAG) {
			throw new AnotherActionExceptedException();
		}

		if (pShot.getBox().getVisible()) {
			throw new SquareAlreadyDiscoveredException();
		}

		if (pShot.getBox().getFlags().contains(vPlayer)) {
			// the player already puts a flag: we remove it
			pShot.getBox().getFlags().remove(vPlayer);
		} else {
			// the player puts its flag
			pShot.getBox().getFlags().add(vPlayer);
		}

		// add the shot in the game (for the repeat)
		vGame.getShots().add(pShot);

		for (SoftPlayer vViewer : vGame.getViewers()) {
			this.mDataServerEngine.getInsideComSrvForDataSrv().spreadNewFlag(pShot, vViewer);
		}

		for (SoftPlayer vLoser : vGame.getLosers()) {
			this.mDataServerEngine.getInsideComSrvForDataSrv().spreadNewFlag(pShot, vLoser);
		}
	}

	@Override
	public void addShot(Shot pShot) throws SquareAlreadyDiscoveredException, AnotherActionExceptedException {
		SoftPlayer vPlayer = pShot.getPlayer();
		UUID pGameId = this.mDataServerEngine.getPlayersInGames().get(vPlayer.getIdPlayer()).getValue();

		Game vGame = this.mDataServerEngine.getGames().get(pGameId);

		if (pShot.getAction() != Action.ACTION) {
			throw new AnotherActionExceptedException();
		}

		if (pShot.getBox().getVisible()) {
			throw new SquareAlreadyDiscoveredException();
		}

		SoftPlayer nextPlayer = this.mDataServerEngine.getNextPlayer(vPlayer.getIdPlayer());

		Box vNewBox = vGame.getGrid().getBoxes().get(pShot.getBox().getCoordinates());
		pShot.setBox(vNewBox);

		ArrayList<Box> newDiscoveredSquares = new ArrayList<Box>();
		switch (pShot.getBox().getValue()) {
		case -1:
			// There is a bomb
			pShot.getBox().setVisible(true);
			newDiscoveredSquares.add(pShot.getBox());
			vGame.addPlayerLost(vPlayer);
			break;
		case 0:
			// Uncovered empty squares
			vGame.getGrid().discoveredSquares(pShot.getBox(), newDiscoveredSquares);
			break;
		default:
			// Number on square
			pShot.getBox().setVisible(true);
			newDiscoveredSquares.add(pShot.getBox());
		}

		final boolean gameIsOver = vGame.isOver();

		ArrayList<SoftPlayer> users = new ArrayList<SoftPlayer>(vGame.getPlayers());
		users.addAll(vGame.getViewers());
		users.addAll(vGame.getLosers());

		ArrayList<SoftPlayer> winners = new ArrayList<SoftPlayer>();
		if (gameIsOver) {
			winners = vGame.getPlayers();
		}

		// spread shot to players, viewers and losers
		for (SoftPlayer vUser : users) {
			this.mDataServerEngine.getInsideComSrvForDataSrv().spreadResult(pShot, newDiscoveredSquares, vUser,
					gameIsOver, nextPlayer, winners);
		}
	}

	@Override
	public void saveSurrender(UUID pIdPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMessage(Message pMessage) throws Exception {
		Map<UUID, Game> vGames = mDataServerEngine.getGames();
		Map<UUID, Map.Entry<SoftPlayer, UUID>> vPlayersInGame = mDataServerEngine.getPlayersInGames();
		Map.Entry<SoftPlayer, UUID> vEntry;
		if ((vEntry = vPlayersInGame.get(pMessage.getAuthor().getIdPlayer())) == null) {
			throw new Exception("Author not found in any game.");
		}

		Game vGame = vGames.get(vEntry.getValue());
		vGame.getMessages().add(pMessage);
		for (SoftPlayer vPlayer : vGame.getPlayers()) {
			mDataServerEngine.getInsideComSrvForDataSrv().spreadMessage(vPlayer, pMessage);
		}
		for (SoftPlayer vViewer : vGame.getViewers()) {
			mDataServerEngine.getInsideComSrvForDataSrv().spreadMessage(vViewer, pMessage);
		}
	}

	@Override
	public void sendTimeExpiredUser(SoftPlayer pSoftPlayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Game getGameToSave(UUID pIdPlayer) {
		logger.info("Request [getGameToSave] from ComSrv to ComSrv (passing through DataSrv)");
		Map<UUID, Entry<SoftPlayer, UUID>> playersInGames = mDataServerEngine.getPlayersInGames();
		return mDataServerEngine.getGames().get(playersInGames.get(pIdPlayer).getValue());
	}

	@Override
	public void sendStartGame(SoftGame pSoftGame) {
		logger.info("Request [sendStartGame] from ComSrv to ComSrv (passing through DataSrv)");

		pSoftGame.setStatus(Status.RUN);

		List<SoftPlayer> vPlayersInGame = new ArrayList<SoftPlayer>(pSoftGame.getPlayers());
		vPlayersInGame.addAll(pSoftGame.getViewers());

		mDataServerEngine.getInsideComSrvForDataSrv().spreadStartGame(vPlayersInGame, new Game(pSoftGame));
	}

	@Override
	public void joinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		List<SoftPlayer> vPlayersInGame = new ArrayList<SoftPlayer>(pSoftGame.getPlayers());
		vPlayersInGame.addAll(pSoftGame.getViewers());
		vPlayersInGame.add(pSoftPlayer);
		vPlayersInGame.addAll(mDataServerEngine.getUsersInMainView());

		mDataServerEngine.joinGameAsPlayer(pSoftPlayer, pSoftGame);
		mDataServerEngine.getInsideComSrvForDataSrv().spreadJoinGameAsPlayer(pSoftGame, pSoftPlayer, vPlayersInGame);
	}

	@Override
	public void joinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		List<SoftPlayer> vPlayersInGame = new ArrayList<SoftPlayer>();
		vPlayersInGame.add(pSoftGame.getCreator());
		vPlayersInGame.add(pSoftPlayer);

		mDataServerEngine.joinGameAsViewer(pSoftPlayer, pSoftGame);
		mDataServerEngine.getInsideComSrvForDataSrv().spreadJoinGameAsViewer(pSoftGame, vPlayersInGame);
	}

	@Override
	public void sendQuitGame(SoftPlayer pSoftPlayer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void abortGame(SoftPlayer pSoftPlayer) {
		logger.info("[abortGame] Request received from ComSrv");

		UUID pGameToAbortID = mDataServerEngine.getPlayersInGames().get(pSoftPlayer.getIdPlayer()).getValue();
		Game pGame = mDataServerEngine.getGames().get(pGameToAbortID);

		if (pGame != null) {
			if (pGame.getCreator().getIdPlayer().toString().equals(pSoftPlayer.getIdPlayer().toString())) {
				// This is the game creator that ask for the game abort
				TupleForAbortGame vResult = mDataServerEngine.abortGame(pGame);
				logger.info("[abortGame] The aborted game is " + vResult.getGame().getIdGame());
				logger.info("[abortGame] The players in game are " + vResult.getPlayersAlreadyInGame().keySet());
				logger.info("[abortGame] The other players to notify are " + vResult.getOtherConnectedUsers().keySet());

				// Initialisation des variables pour l'appel à ComSrv
				SoftGame vSoftGame = new SoftGame(vResult.getGame());
				List<SoftPlayer> vPlayersInGame = new ArrayList<SoftPlayer>();
				List<SoftPlayer> vOtherUsersToNotify = new ArrayList<SoftPlayer>();

				for (Entry<UUID, SoftPlayer> player : vResult.getPlayersAlreadyInGame().entrySet())
					vPlayersInGame.add(player.getValue());

				for (Entry<UUID, SoftPlayer> player : vResult.getOtherConnectedUsers().entrySet())
					vOtherUsersToNotify.add(player.getValue());

				// Appel ComSrv
				logger.info("Requesting ComSrv to spread this game deletion to every connected players.");
				mDataServerEngine.getInsideComSrvForDataSrv().spreadGameAborted(vSoftGame, vPlayersInGame,
						vOtherUsersToNotify);
			} else {
				// A player wants to leave the lobby
				logger.info("[abortGame] The player " + pSoftPlayer.getLogin() + " wants to leave the lobby.");

				// Mise à jour des données stockées sur le serveur
				mDataServerEngine.deleteUserInGame(pSoftPlayer.getIdPlayer());

				// Mise à jour de l'état de la partie
				SoftPlayer vPlayer = pGame.getPlayer(pSoftPlayer.getIdPlayer());
				SoftPlayer vSpectator = pGame.getSpectator(pSoftPlayer.getIdPlayer());
				if (vPlayer != null)
					pGame.getPlayers().remove(vPlayer);
				else if (vSpectator != null)
					pGame.getViewers().remove(vSpectator);
				else
					logger.fatal("The player " + pSoftPlayer.getIdPlayer() + " is not a player, nor a viewer.");

				// Initialisation des variables pour l'appel à ComSrv
				List<SoftPlayer> vPlayersInTheLobby = Stream
						.concat(pGame.getPlayers().stream(), pGame.getViewers().stream()).collect(Collectors.toList());
				vPlayersInTheLobby.remove(pGame.getCreator());

				// Appel ComSrv
				logger.info("Requesting ComSrv to spread this lobby leaving to others.");
				mDataServerEngine.getInsideComSrvForDataSrv().spreadPlayerLeft(pSoftPlayer, vPlayersInTheLobby,
						pGame.getCreator(), pGame);
			}
		} else {
			logger.fatal("[abortGame] The game shouldn't be null");
			// mDataServerEngine.getInsideComSrvForDataSrv().spreadServerException(pSoftPlayer,"An
			// internal error occured. Please consult the server logs.");
		}

	}

	@Override
	public void playerSurrendered(SoftPlayer pSoftPlayer) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<SoftGame> getCurrentGames() {
		logger.info("[getCurrentGames] Request received from ComSrv");
		ArrayList<SoftGame> softGames = new ArrayList<SoftGame>();
		for (Map.Entry<UUID, Game> game : mDataServerEngine.getGames().entrySet()) {
			softGames.add(new SoftGame(game.getValue()));
		}
		return softGames;
	}

	@Override
	public List<SoftPlayer> getCurrentPlayers() {
		List<SoftPlayer> currentPlayers = new ArrayList<SoftPlayer>();
		for (Map.Entry<UUID, Entry<SoftPlayer, UUID>> player : mDataServerEngine.getPlayersInGames().entrySet())
			currentPlayers.add(player.getValue().getKey());
		return currentPlayers;
	}
}
