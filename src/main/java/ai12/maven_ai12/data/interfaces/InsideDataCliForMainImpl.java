package ai12.maven_ai12.data.interfaces;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.*;
import ai12.maven_ai12.data.DataClientEngine;
import ai12.maven_ai12.data.managers.GameSaveManager;
import ai12.maven_ai12.data.managers.SavedPlayerManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public class InsideDataCliForMainImpl implements IInsideDataCliForMain {
    private static Logger logger = Logger.getLogger(InsideDataCliForMainImpl.class);
    private DataClientEngine dataClientEngine;

    public InsideDataCliForMainImpl(DataClientEngine pDataClientEngine) {
        this.dataClientEngine = pDataClientEngine;
    }

    @Override
    public void login(String pLogin, String pPassword, String pServerIp, Integer pPort)
            throws InternalError, Exception {
        if (pLogin == null || pLogin.isEmpty() || pPassword == null || pPassword.isEmpty()) {
            logger.error("[login] Login|Password cannot be null nor empty. Throw new EmptyFieldException");
            throw new EmptyFieldException();
        }

        SavedPlayerManager vSpm = SavedPlayerManager.getInstance();
        SavedPlayer vPlayerForData = vSpm.load(pLogin, pPassword);
        logger.info("[login] Local profile has been successfully loaded");
        dataClientEngine.setCurrentPlayer(vPlayerForData);
        SoftPlayer vPlayerForCom = new SoftPlayer(vPlayerForData);

        if (pServerIp != null && !pServerIp.isEmpty()) {
            dataClientEngine.setServerIp(pServerIp);
            logger.info("[login] Validating the provided server IP...");
            Config.validateIPAddress(pServerIp);
            logger.info("[login] Initializing the ComClientEngine...");
            dataClientEngine.getInsideComCliForDataCli().initializeComClientEngine(pServerIp, pPort);
            logger.info("[login] Starting the ComClientEngine...");
            dataClientEngine.getInsideComCliForDataCli().startComClientEngine();
            logger.info("[login] Requesting ComCli to establish the connection with the server...");
            dataClientEngine.getInsideComCliForDataCli().connection(vPlayerForCom);
        } else {
            this.dataClientEngine.getInsideMainForDataCli().notifyConnectionSuccessWithoutServer();
        }
    }

    @Override
    public void register(PlayerProfile pPlayerProfile, String pPassword)
            throws InvalidFieldException, EmptyFieldException, InvalidLoginException {
        this.checkPassword(pPassword);
        this.checkUser(pPlayerProfile);
        SavedPlayerManager vSavedGameManager = SavedPlayerManager.getInstance();
        vSavedGameManager.save(new SavedPlayer(pPlayerProfile), pPassword);
    }

    @Override
    public void sendAskCurrentGames() {
        logger.info("[sendAskCurrentGames] Requesting ComCli to ask the current games.");
        dataClientEngine.getInsideComCliForDataCli()
                .sendAskCurrentGames(dataClientEngine.getCurrentPlayer().getIdPlayer());
    }

    @Override
	public void sendAskCurrentUsers() {
		logger.info("[sendAskCurrentUsers] Requesting ComCli to ask the current games.");
		dataClientEngine.getInsideComCliForDataCli()
				.sendAskCurrentUsers(dataClientEngine.getCurrentPlayer().getIdPlayer());
	}

	@Override
    public void changeServerIp(String pIp) {
        // TODO Auto-generated method stub
    }

    @Override
    public void createGame(SoftGame pGame) throws EmptyFieldException {
        pGame.setCreator(this.dataClientEngine.getCurrentPlayer());
        pGame.setViewers(new ArrayList<SoftPlayer>());
        pGame.setPlayers(new ArrayList<SoftPlayer>());
        pGame.getPlayers().add(this.dataClientEngine.getCurrentPlayer());
        pGame.setStatus(Status.CREATED);

        if (pGame.getName() == null || pGame.getName().isEmpty()) {
            logger.error(
                    "[createGame] Cannot create the game because the name is null or empty. Throw new EmptyFieldException.");
            throw new EmptyFieldException();
        }

        logger.info("[createGame] Requesting ComCli to create a game with the provided SoftGame.");
        this.dataClientEngine.getInsideComCliForDataCli().createGame(pGame);
    }

    @Override
    public void joinGameAsPlayer(SoftGame pGame) {
        dataClientEngine.getInsideComCliForDataCli().joinGameAsPlayer(pGame, dataClientEngine.getCurrentPlayer());
    }

    @Override
    public void logout() {
        if (dataClientEngine.isConnectedToAServer()) {
            logger.info("[logout] Requesting ComCli for the logout of the player "
                    + dataClientEngine.getCurrentPlayer().getIdPlayer());
            dataClientEngine.getInsideComCliForDataCli().logout(dataClientEngine.getCurrentPlayer().getIdPlayer());
            logger.info("[login] Stopping the ComClientEngine...");
            dataClientEngine.getInsideComCliForDataCli().stopComClientEngine();
            dataClientEngine.setConnectedToAServer(false);
        } else {
            logger.info("[logout] Nothing to do. This player is already disconnected from the server.");
        }
    }

    @Override
    public void requestJoinGameAsPlayer(SoftGame pSoftGame) {
        dataClientEngine.getInsideComCliForDataCli()
                .requestJoinGameAsPlayer(new SoftPlayer(dataClientEngine.getCurrentPlayer()), pSoftGame);
    }

    @Override
	public void requestJoinGameAsViewer(SoftGame pSoftGame) {
		dataClientEngine.getInsideComCliForDataCli()
				.requestJoinGameAsViewer(new SoftPlayer(dataClientEngine.getCurrentPlayer()), pSoftGame);
	}

	@Override
    public void joinGameAsViewer(SoftGame pGame) {
        dataClientEngine.getInsideComCliForDataCli().joinGameAsViewer(pGame, dataClientEngine.getCurrentPlayer());
    }

    @Override
    public void startGame(SoftGame pGame) {
        this.dataClientEngine.getInsideComCliForDataCli().startGame(pGame);
    }

    @Override
    public ArrayList<SoftGame> getSavedGamesList() {
        GameSaveManager vGsm = GameSaveManager.getInstance();
		ArrayList<SoftGame> softGamesList = new ArrayList<SoftGame>();
		for (Game game : vGsm.getLocalGames()) {
			softGamesList.add((SoftGame) game);
		}
        return softGamesList;
    }

    @Override
    public void sendAskPlayerProfile(UUID pIdPlayer) {

    }

    @Override
    public void replaySavedGame(SoftGame pGame) throws SavedGameNotFoundException {
		GameSaveManager vGsm = GameSaveManager.getInstance();
		Game vGameToReplay = null;
		for(Game game : vGsm.getLocalGames()) {
			if(game.getIdGame().equals(pGame.getIdGame())){
        vGameToReplay = game;
			}
		}

		if(null == vGameToReplay) {
			throw new SavedGameNotFoundException();
		} else {
			dataClientEngine.getInsideGameForDataCli().replayGame(vGameToReplay);
		}
    }

    @Override
    public PlayerProfile getOwnPlayerProfile() {
        return dataClientEngine.getCurrentPlayer();
    }

    /**
     * Check password validity
     *
     * @param pPassword
     * @throws InvalidFieldException
     * @throws EmptyFieldException
     */
    private void checkPassword(String pPassword) throws InvalidFieldException, EmptyFieldException {
        if (pPassword == null || pPassword.length() == 0) {
            throw new EmptyFieldException();
        } else if (!pPassword.matches("[0-9A-Za-z]{6,}")) {
            throw new InvalidFieldException();
        }
    }

    /**
     * Check pPlayerProfile validity
     *
     * @param pPlayerProfile
     * @throws InvalidFieldException
     * @throws EmptyFieldException
     */
    private void checkUser(PlayerProfile pPlayerProfile)
            throws InvalidFieldException, EmptyFieldException, InvalidLoginException {
        if (pPlayerProfile != null) {
            if (!pPlayerProfile.getLogin().matches("[a-zA-Z0-9]{8,20}")) {
                throw new InvalidLoginException();
            }
            if (!pPlayerProfile.getFirstname().matches("[a-zA-Z -]*")) {
                throw new InvalidFieldException();
            }
            if (!pPlayerProfile.getLastname().matches("[a-zA-Z -]*")) {
                throw new InvalidFieldException();
            }
        } else {
            throw new EmptyFieldException();
        }
    }

    @Override
    public ArrayList<String> getLocalLogins() {
        ArrayList<String> results = new ArrayList<String>();
        SavedPlayerManager vSpm = SavedPlayerManager.getInstance();
        results = vSpm.getLocalLogins();
        return results;
    }

    @Override
    public void denyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
        dataClientEngine.getInsideComCliForDataCli().denyPlayerRequest(pSoftPlayer, pSoftGame);
    }

    @Override
    public void acceptPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
        if (pSoftGame.getGameParameters().getNbMaxPlayers() == pSoftGame.getPlayers().size()) // No more player accepted
            dataClientEngine.getInsideComCliForDataCli().denyPlayerRequest(pSoftPlayer, pSoftGame);
        else
            dataClientEngine.getInsideComCliForDataCli().acceptPlayerRequest(pSoftPlayer, pSoftGame);
    }

    @Override
    public String getServerIp() {
        if (this.dataClientEngine != null)
            return this.dataClientEngine.getServerIp();
        else {
            logger.fatal("[getServerIp] dataClientEngine shouldn't be null");
            return null;
        }
    }

    @Override
    public void modifyOwnPlayerProfile(PlayerProfile pPlayerProfile) {
        // TODO Auto-generated method stub

    }

    @Override
    public void abortGame() {
        SoftPlayer pSoftPlayer = dataClientEngine.getCurrentPlayer();
        logger.info("[abortGame] Requesting ComCli to abort a game with the provided SoftPlayer.");
        dataClientEngine.getInsideComCliForDataCli().sendAbortGame(pSoftPlayer);
    }

    @Override
    public Integer getServerPort() {
        return dataClientEngine.getInsideComCliForDataCli().getServerPort();
    }
}