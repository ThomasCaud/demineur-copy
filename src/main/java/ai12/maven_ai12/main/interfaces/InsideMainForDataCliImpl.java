package ai12.maven_ai12.main.interfaces;

import java.util.ArrayList;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.main.MainClientEngine;
import javafx.application.Platform;
import javafx.stage.Stage;

public class InsideMainForDataCliImpl implements IInsideMainForDataCli {

	private MainClientEngine mMainClientEngine;

	public InsideMainForDataCliImpl(MainClientEngine pMainClientEngine) {
		this.mMainClientEngine = pMainClientEngine;
	}

	@Override
	public void notifyCurrentGames(ArrayList<SoftGame> pSoftGames) {
		Platform.runLater(() -> this.mMainClientEngine.getMMainController().setCurrentGames(pSoftGames));
	}

	@Override
	public void notifyNewUser(SoftPlayer pSoftPlayer) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().newUser(pSoftPlayer);
			}
		});
	}

	@Override
	public void notifyQuitUser(SoftPlayer pSoftPlayer) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().removeUser(pSoftPlayer);
			}
		});
	}

	@Override
	public void notifyNewGame(SoftGame pSoftGame) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().newGame(pSoftGame);
			}
		});
	}

	@Override
	public void notifyGameIsReady(SoftGame pSoftGame) {

		Platform.runLater(() -> this.mMainClientEngine.changeToLobbyAdmin(pSoftGame));
	}

	@Override
	public void notifyRequestGameJoinAsPlayer(SoftPlayer pSoftPlayer) {
		Platform.runLater(() -> mMainClientEngine.getMLobbyAdminController().newPendingPlayer(pSoftPlayer));
	}

	@Override
	public void notifyGameJoin(SoftGame pSoftGame) {
		this.mMainClientEngine.changeToLobbyPlayer(pSoftGame);
	}

	@Override
	public void notifyGameChange(SoftGame pSoftGame) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMLobbyPlayerController().gameChange(pSoftGame);
			}
		});
	}

	@Override
	public void notifyGameChangeForAdmin(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMLobbyAdminController().gameChange(pSoftPlayer, pSoftGame);
			}
		});
	}

	@Override
	public void notifyQuitPendingGame() {
		this.mMainClientEngine.changeToMain();
	}

	@Override
	public void notifyGameQuit() {
		this.mMainClientEngine.changeToMain();
	}

	@Override
	public void notifyConnectionSuccess(ArrayList<SoftPlayer> pSoftPlayers) {
		this.mMainClientEngine.changeToMain();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().initalizeAsConnected();
				mMainClientEngine.getMMainController().setCurrentUsers(pSoftPlayers);
			}
		});
	}

	@Override
	public void notifyGameDeleted(SoftGame pSoftGame) {
		Platform.runLater(() -> this.mMainClientEngine.getMMainController().deleteGame(pSoftGame));
	}

	@Override
	public Stage getStage() {
		return mMainClientEngine.getPrimaryStage();
	}

	@Override
	public void notifyGameAborted() {
		this.mMainClientEngine.changeToMain();
	}

	@Override
	public void notifyPlayerLeftLobby(SoftGame pSoftGame) {
		Platform.runLater(() -> mMainClientEngine.getMLobbyPlayerController().gameChange(pSoftGame));
	}

	@Override
	public void notifyPlayerLeftLobbyAdmin(SoftGame pSoftGame) {
		Platform.runLater(() -> mMainClientEngine.getMLobbyAdminController().gameChange(pSoftGame));
	}

	@Override
	public void notifyLobbyLeaving() {
		this.mMainClientEngine.changeToMain();
	}

	@Override
	public void notifyCurrentUsers(ArrayList<SoftPlayer> pSoftPlayers) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().setCurrentUsers(pSoftPlayers);
			}
		});
	}

	@Override
	public void notifyGameJoinDenied() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().joinGameDenied();
			}
		});
	}

	public void notifyGameChangeForUsers(SoftGame pSoftGame) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMMainController().updateGame(pSoftGame);
			}
		});
	}

	@Override
	public void notifyGameChangeForAdmin(SoftGame pSoftGame) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mMainClientEngine.getMLobbyAdminController().gameChange(pSoftGame);
			}
		});
	}

	@Override
	public void notifyConnectionSuccessWithoutServer() {
		this.mMainClientEngine.changeToMain();
		Platform.runLater(() -> this.mMainClientEngine.getMMainController().initializeAsDisconnected());
	}

}