package ai12.maven_ai12.server;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ai12.maven_ai12.com.server.ComSrvEngine;
import ai12.maven_ai12.data.DataServerEngine;
import ai12.maven_ai12.server.main.MainServerEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppServer extends Application {

	private static Logger logger = Logger.getLogger(AppServer.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PropertyConfigurator.configure("target/log4j-server.properties");

		logger.info("Lancement de l'application serveur");

		MainServerEngine mainServerEngine = new MainServerEngine(primaryStage);
		DataServerEngine dataServerEngine = new DataServerEngine();
		ComSrvEngine comServerEngine = new ComSrvEngine();

		comServerEngine.setInsideDataSrvForComSrv(dataServerEngine.getInsideDataSrvForComSrv());
		dataServerEngine.setInsideComSrvForDataSrv(comServerEngine.getInsideComSrvForDataSrv());
		mainServerEngine.setInsideDataSrvForMainSrv(dataServerEngine.getInsideDataSrvForMainSrv());

		if (comServerEngine.getInsideDataSrvForComSrv() == null || dataServerEngine.getInsideComSrvForDataSrv() == null
				|| mainServerEngine.getInsideDataSrvForMainSrv() == null) {
			logger.fatal("Something went wrong...");
			System.exit(1);
		}

		mainServerEngine.init();
		mainServerEngine.show();
	}
}
