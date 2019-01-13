package ai12.maven_ai12.data;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.beans.TupleForAbortGame;
import ai12.maven_ai12.com.interfaces.IInsideComSrvForDataSrv;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;
import ai12.maven_ai12.data.exceptions.PlayerNotConnectedException;
import ai12.maven_ai12.data.interfaces.IInsideDataSrvForComSrv;
import ai12.maven_ai12.data.interfaces.IInsideDataSrvForMainSrv;
import ai12.maven_ai12.data.interfaces.InsideDataSrvForComSrvImpl;
import ai12.maven_ai12.data.interfaces.InsideDataSrvForMainSrvImpl;

public class DataServerEngine {
	public final static UUID notConnectedToAGame = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
	private static Logger logger = Logger.getLogger(DataServerEngine.class);
	// The first UUID is the SoftPlayer UUID, the second one is the Game UUID
	private Map<UUID, Entry<SoftPlayer, UUID>> mPlayersInGames;
	private IInsideDataSrvForComSrv mInsideDataSrvForComSrv;
	private HashMap<UUID, Game> mGames;
	private IInsideDataSrvForMainSrv mInsideDataSrvForMainSrv;
	private IInsideComSrvForDataSrv mInsideComSrvForDataSrv;

	public DataServerEngine() {
		this.mGames = new HashMap<UUID, Game>();
		this.mPlayersInGames = new HashMap<UUID, Entry<SoftPlayer, UUID>>();
		this.setInsideDataSrvForComSrv(new InsideDataSrvForComSrvImpl(this));
		this.setInsideDataSrvForMainSrv(new InsideDataSrvForMainSrvImpl(this));
		logger.info("Instanciation du module DataServerEngine");
	}

	public Map<UUID, Entry<SoftPlayer, UUID>> getPlayersInGames() {
		return Collections.unmodifiableMap(mPlayersInGames);
	}

	public Map<UUID, Game> getGames() {
		return Collections.unmodifiableMap(mGames);
	}

	public void setGames(HashMap<UUID, Game> mGames) {
		this.mGames = mGames;
	}

	public IInsideDataSrvForComSrv getInsideDataSrvForComSrv() {
		return mInsideDataSrvForComSrv;
	}

	public void setInsideDataSrvForComSrv(IInsideDataSrvForComSrv mInsideDataSrvForComSrv) {
		this.mInsideDataSrvForComSrv = mInsideDataSrvForComSrv;
	}

	public IInsideComSrvForDataSrv getInsideComSrvForDataSrv() {
		return mInsideComSrvForDataSrv;
	}

	public void setInsideComSrvForDataSrv(IInsideComSrvForDataSrv mInsideComSrvForDataSrv) {
		this.mInsideComSrvForDataSrv = mInsideComSrvForDataSrv;
	}

	public void addNewUser(SoftPlayer pPlayer) throws PlayerAlreadyConnectedException {
		if (!this.mPlayersInGames.containsKey(pPlayer.getIdPlayer())) {
			this.mPlayersInGames.put(pPlayer.getIdPlayer(),
					new AbstractMap.SimpleEntry<SoftPlayer, UUID>(pPlayer, notConnectedToAGame));
			logger.info("[addNewUser] " + pPlayer.getLogin() + " [" + pPlayer.getIdPlayer() + "] joined the server.");
		} else {
			logger.error("[addNewUser] " + pPlayer.getLogin() + " [" + pPlayer.getIdPlayer()
					+ "] is already connected to the server.");
			throw new PlayerAlreadyConnectedException();
		}
	}

	public void addNewGame(SoftGame softGame) throws GameAlreadyCreatedException {
		if (!this.mGames.containsKey(softGame.getIdGame())) {
			Game game = new Game(softGame);
			this.mGames.put(game.getIdGame(), game);
			this.mPlayersInGames.replace(softGame.getCreator().getIdPlayer(),
					new AbstractMap.SimpleEntry<SoftPlayer, UUID>(softGame.getCreator(), game.getIdGame()));
			logger.info(
					"[addNewGame] " + softGame.getCreator().getLogin() + " created the game " + softGame.getIdGame());
		} else {
			logger.error("[addNewGame] Cannot create game " + softGame.getIdGame()
					+ ". This game has already been created.");
			throw new GameAlreadyCreatedException();
		}
	}

	public void updateUserInGame(UUID pPlayerId, UUID pGameId) {
		if (this.mPlayersInGames.containsKey(pPlayerId)) {
			SoftPlayer currentPlayer = this.mPlayersInGames.get(pPlayerId).getKey();
			this.mPlayersInGames.put(pPlayerId, new AbstractMap.SimpleEntry<SoftPlayer, UUID>(currentPlayer, pGameId));
			logger.debug(
					"[updateUserInGame] Set value " + pGameId + " to the key " + pPlayerId + " in mPlayersInGames");
		} else {
			logger.error("[updateUserInGame] Player " + pPlayerId + " is not in mPlayersInGames");
		}
	}

	public void deleteUserInGame(UUID pPlayerId) {
		if (this.mPlayersInGames.containsKey(pPlayerId)) {
			logger.debug("[deleteUserIngame] Set value null to the key " + pPlayerId + " in mPlayersInGames");
			SoftPlayer currentPlayer = this.mPlayersInGames.get(pPlayerId).getKey();
			this.mPlayersInGames.put(pPlayerId,
					new AbstractMap.SimpleEntry<SoftPlayer, UUID>(currentPlayer, notConnectedToAGame));
		} else {
			logger.error("[deleteUserInGame] Player " + pPlayerId + " is not in mPlayersInGames");
		}
	}

	public void deleteUser(UUID pPlayerId) throws PlayerNotConnectedException {
		if (this.mPlayersInGames.containsKey(pPlayerId)) {
			String login = mPlayersInGames.get(pPlayerId).getKey().getLogin();
			logger.info("[deleteUser] " + login + " [" + pPlayerId + "] left the server.");
			this.mPlayersInGames.remove(pPlayerId);
		} else {
			logger.error("[deleteUser] " + pPlayerId + " is not connected to the server.");
			throw new PlayerNotConnectedException();
		}

	}

	public void joinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		this.getGames().get(pSoftGame.getIdGame()).getPlayers().add(pSoftPlayer);
		pSoftGame.getPlayers().add(pSoftPlayer);
		this.updateUserInGame(pSoftPlayer.getIdPlayer(), pSoftGame.getIdGame());
	}

	public void joinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		this.getGames().get(pSoftGame.getIdGame()).getViewers().add(pSoftPlayer);
		pSoftGame.getViewers().add(pSoftPlayer);
		this.updateUserInGame(pSoftPlayer.getIdPlayer(), pSoftGame.getIdGame());
	}

	public IInsideDataSrvForMainSrv getInsideDataSrvForMainSrv() {
		return mInsideDataSrvForMainSrv;
	}

	public void setInsideDataSrvForMainSrv(IInsideDataSrvForMainSrv mInsideDataSrvForMainSrv) {
		this.mInsideDataSrvForMainSrv = mInsideDataSrvForMainSrv;
	}

	public TupleForAbortGame abortGame(Game pGame) {
		logger.info("[abortGame] The game creator is " + pGame.getCreator().getIdPlayer());

		Map<UUID, SoftPlayer> pPlayersAlreadyInGame = new HashMap<UUID, SoftPlayer>();
		for (SoftPlayer pPlayer : pGame.getPlayers())
			pPlayersAlreadyInGame.put(pPlayer.getIdPlayer(), pPlayer);
		logger.info("[abortGame] The players already in game are " + pPlayersAlreadyInGame.keySet());

		Map<UUID, SoftPlayer> pOtherUsersToNotify = new HashMap<UUID, SoftPlayer>();
		for (SoftPlayer pPlayer : getInsideDataSrvForComSrv().getCurrentPlayers()) {
			if (!pPlayersAlreadyInGame.containsKey(pPlayer.getIdPlayer()))
				pOtherUsersToNotify.put(pPlayer.getIdPlayer(), pPlayer);
		}
		logger.info("[abortGame] The other players to notify are " + pOtherUsersToNotify.keySet());

		// Update the state of player in game (dataServerEngine)
		for (Entry<UUID, SoftPlayer> pPlayerToUpdate : pPlayersAlreadyInGame.entrySet()) {
			logger.info("[abortGame] Player " + pPlayerToUpdate.getValue().getIdPlayer()
					+ " has been removed from the game.");
			deleteUserInGame(pPlayerToUpdate.getKey());
		}

		// Delete the game on the server
		logger.info("[abortGame] The game " + pGame.getIdGame() + " has been removed.");
		mGames.remove(pGame.getIdGame());

		return new TupleForAbortGame(pGame, pPlayersAlreadyInGame, pOtherUsersToNotify);
	}

	public SoftPlayer getNext(ArrayList<SoftPlayer> pUsers, SoftPlayer pNextTo) {
		if (pUsers.size() == 0)
			return null;
		if (pUsers.indexOf(pNextTo) + 1 < pUsers.size()) {
			return pUsers.get(pUsers.indexOf(pNextTo) + 1);
		} else {
			return pUsers.get(0);
		}
	}

	public SoftPlayer getNextPlayer(UUID lastPlayer) {
		// todo unit tests
		SoftPlayer vPlayer = mPlayersInGames.get(lastPlayer).getKey();
		UUID vServerUUID = mPlayersInGames.get(lastPlayer).getValue();
		Game vGame = mGames.get(vServerUUID);

		ArrayList<SoftPlayer> vPlayers = vGame.getPlayers();
		return getNext(vPlayers, vPlayer);
	}

	public ArrayList<SoftPlayer> getUsersInMainView() {
		ArrayList<SoftPlayer> vUsersInMainView = new ArrayList<SoftPlayer>();

		for (Map.Entry<UUID, Entry<SoftPlayer, UUID>> player : this.getPlayersInGames().entrySet()) {
			if (player.getValue().getValue().equals(notConnectedToAGame)) {
				vUsersInMainView.add(player.getValue().getKey());	
			}
		}
			
		return vUsersInMainView;
	}

}
