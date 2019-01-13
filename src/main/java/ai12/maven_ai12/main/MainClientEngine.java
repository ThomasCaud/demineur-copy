package ai12.maven_ai12.main;

import java.io.IOException;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain;
import ai12.maven_ai12.main.controllers.LobbyAdminController;
import ai12.maven_ai12.main.controllers.LobbyPlayerController;
import ai12.maven_ai12.main.controllers.MainController;
import ai12.maven_ai12.main.interfaces.IInsideMainForDataCli;
import ai12.maven_ai12.main.interfaces.InsideMainForDataCliImpl;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainClientEngine {

	private static int WIDTH = 800;

	private static int HEIGHT = 600;

	private IInsideDataCliForMain mInsideDataCliForMain;
	private IInsideMainForDataCli mInsideMainForDataCli;
	private Stage mPrimaryStage;

	private LobbyAdminController mLobbyAdminController;
	private LobbyPlayerController mLobbyPlayerController;
	private MainController mMainController;

	public MainClientEngine(Stage pPrimaryStage) {
		this.mInsideMainForDataCli = new InsideMainForDataCliImpl(this);
		this.mPrimaryStage = pPrimaryStage;
		this.mPrimaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				mInsideDataCliForMain.logout();
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public IInsideMainForDataCli getInsideMainForDataCli() {
		return this.mInsideMainForDataCli;
	}

	public IInsideDataCliForMain getInsideDataCliForMain() {
		return this.mInsideDataCliForMain;
	}

	public void start() throws IOException {
		changeScene(SceneEnum.CONNECTION.getResource());
	}

	public void changeScene(String pResource) {
		try {
			loadScene(findScene(pResource));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeToLobbyAdmin(SoftGame pGame) {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(SceneEnum.LOBBYADMIN.getResource()));
		mLobbyAdminController = new LobbyAdminController(pGame);
		mainLoader.setController(mLobbyAdminController);
		try {
			Pane pane = mainLoader.load();
			loadScene(new Scene(pane, WIDTH, HEIGHT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeToLobbyPlayer(SoftGame pGame) {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(SceneEnum.LOBBYPLAYER.getResource()));
		mLobbyPlayerController = new LobbyPlayerController(pGame);
		mainLoader.setController(mLobbyPlayerController);
		try {
			Pane pane = mainLoader.load();
			loadScene(new Scene(pane, WIDTH, HEIGHT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeToMain() {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(SceneEnum.MAIN.getResource()));
		if (mMainController == null) {
			mMainController = new MainController();
		}
		mainLoader.setController(mMainController);
		try {
			Pane pane = mainLoader.load();
			loadScene(new Scene(pane, WIDTH, HEIGHT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Scene findScene(String pResource) throws IOException {
		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(pResource));
		Pane pane = mainLoader.load();
		return new Scene(pane, WIDTH, HEIGHT);
	}

	private void loadScene(Scene pScene) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mPrimaryStage.setTitle("Démineur");
				mPrimaryStage.setScene(pScene);
				mPrimaryStage.show();
			}
		});
	}

	public void setInsideDataCliForMain(IInsideDataCliForMain pInsideDataCliForMain) {
		this.mInsideDataCliForMain = pInsideDataCliForMain;
	}

	private static MainClientEngine _singleton;

	public static MainClientEngine getMainClientEngine(Stage pPrimaryStage) {
		if (_singleton == null && pPrimaryStage != null) {
			_singleton = new MainClientEngine(pPrimaryStage);
		}
		return _singleton;
	}

	public Stage getPrimaryStage() {
		return this.mPrimaryStage;
	}

	public MainController getMMainController() {
		return mMainController;
	}

	public LobbyAdminController getMLobbyAdminController() {
		return mLobbyAdminController;
	}

	public LobbyPlayerController getMLobbyPlayerController() {
		return mLobbyPlayerController;
	}

}