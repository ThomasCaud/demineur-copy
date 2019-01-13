package ai12.maven_ai12.server.main;

import java.io.IOException;

import org.apache.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

	private static Logger logger = Logger.getLogger(MainController.class);
	private MainServerEngine mEngine;

	@FXML
	private TextArea mTextArea;

	@FXML
	private TextField mPort;

	@FXML
	private Button mLaunchServer;

	public TextArea getTextArea() {
		return mTextArea;
	}

	public void clickLaunchServer() {
		String portString = mPort.getText();
		if (portString != null && !portString.isEmpty()) {
			try {
				Integer port = Integer.parseInt(portString);
				this.mEngine.startServer(port);
				mLaunchServer.setDisable(true);
			} catch (NumberFormatException e) {
				logger.error("The listening port must be an integer value !");
			} catch (IOException e) {
				logger.error("Cannot launch the server on the port " + portString);
			}
		} else {
			logger.error("The listening port cannot be empty !");
		}
	}

	public void setEngine(MainServerEngine pEngine) {
		this.mEngine = pEngine;
	}

}
