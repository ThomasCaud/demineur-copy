package ai12.maven_ai12.game;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;

import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForGame;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

public class GameClientEngine {

	private InsideGameForDataCliImpl mInsideGameForDataCliImpl;

	private IInsideDataCliForGame mIInsideDataCliForGame;

	private GameController mGameController;

	public GameController getGameController() {
		return mGameController;
	}

	public void setGameController(GameController mGameController) {
		this.mGameController = mGameController;
	}

	public IInsideGameForDataCli getIInsideGameForDataCli() {
		return mInsideGameForDataCliImpl;
	}

	public GameClientEngine() {
		// TODO envoyer donner après chargement
		this.mInsideGameForDataCliImpl = new InsideGameForDataCliImpl(this);
	}

	public void startAGame(Game pGame, Stage pStage) {
		FXMLLoader loader = new FXMLLoader();
		URL url = getClass().getResource("views" + FileSystems.getDefault().getSeparator() + "IHMGame.fxml");
		loader.setLocation(url);

		try {
			SplitPane root = (SplitPane) loader.load();
			this.mGameController = ((GameController) loader.getController());
			this.mGameController.setManager(this);
			this.mGameController.setmGame(pGame);
			this.mGameController.setmInsideGameForDataCliImpl(mInsideGameForDataCliImpl);
			this.mGameController.setIInsideDataCliForGame(this.mIInsideDataCliForGame);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Scene scene = new Scene(root, 800, 800);
					// Next line need to be commented if MacOS X
					scene.getStylesheets().add(getClass().getResource("views/IHMGame.css").toExternalForm());
					pStage.setTitle("Démineur");
					pStage.setScene(scene);
					pStage.show();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IInsideDataCliForGame getIInsideDataCliForGame() {
		return mIInsideDataCliForGame;
	}

	public void setIInsideDataCliForGame(IInsideDataCliForGame pIInsideDataCliForGame) {
		this.mIInsideDataCliForGame = pIInsideDataCliForGame;
	}

}
