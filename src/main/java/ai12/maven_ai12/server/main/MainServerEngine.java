package ai12.maven_ai12.server.main;

import java.io.IOException;

import ai12.maven_ai12.data.interfaces.IInsideDataSrvForMainSrv;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainServerEngine {

	private Stage mMainStage;
	private BorderPane mMainContainer;
	private MainController mMainController;
	private IInsideDataSrvForMainSrv mInsideDataSrvForMainSrv;

	public MainServerEngine(Stage pStage) {
		this.mMainStage = pStage;
		this.mMainStage.setTitle("Démineur - AppServer");
		this.mMainStage.setResizable(false);

		this.mMainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public void init() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainServerEngine.class.getResource("MainContainer.fxml"));
		try {
			mMainContainer = (BorderPane) loader.load();
			Scene scene = new Scene(mMainContainer);
			mMainController = loader.getController();
			mMainController.setEngine(this);

			TextAreaAppender.setTextArea(mMainController.getTextArea());

			mMainStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setInsideDataSrvForMainSrv(IInsideDataSrvForMainSrv pInsideDataSrvForMainSrv) {
		this.mInsideDataSrvForMainSrv = pInsideDataSrvForMainSrv;
	}

	public IInsideDataSrvForMainSrv getInsideDataSrvForMainSrv() {
		return this.mInsideDataSrvForMainSrv;
	}

	public void show() {
		this.mMainStage.show();
	}

	public void startServer(Integer pPort) throws IOException {
		this.getInsideDataSrvForMainSrv().startComServerEngine(pPort);
	}
}