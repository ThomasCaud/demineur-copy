package ai12.maven_ai12;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ai12.maven_ai12.com.client.ComClientEngine;
import ai12.maven_ai12.data.DataClientEngine;
import ai12.maven_ai12.main.MainClientEngine;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	private static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("target/log4j-client.properties");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MainClientEngine mainClientEngine = MainClientEngine.getMainClientEngine(primaryStage);
		DataClientEngine dataClientEngine = new DataClientEngine();
		ComClientEngine comClientEngine = new ComClientEngine();

		mainClientEngine.setInsideDataCliForMain(dataClientEngine.getInsideDataCliForMain());
		dataClientEngine.setInsideMainForDataCli(mainClientEngine.getInsideMainForDataCli());
		dataClientEngine.setInsideComCliForDataCli(comClientEngine.getInsideComCliForDataCli());
		comClientEngine.setInsideDataCliForComCli(dataClientEngine.getInsideDataCliForComCli());

		if (mainClientEngine.getInsideDataCliForMain() == null || comClientEngine.getInsideDataCliForComCli() == null
				|| dataClientEngine.getInsideMainForDataCli() == null
				|| dataClientEngine.getInsideComCliForDataCli() == null) {
			logger.fatal("Something went wrong...");
			System.exit(1);
		}

		mainClientEngine.start();
	}
}
