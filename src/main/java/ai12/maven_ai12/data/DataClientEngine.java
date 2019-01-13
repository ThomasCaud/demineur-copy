package ai12.maven_ai12.data;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.SavedPlayer;
import ai12.maven_ai12.com.interfaces.IInsideComCliForDataCli;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForComCli;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForGame;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain;
import ai12.maven_ai12.data.interfaces.InsideDataCliForComCliImpl;
import ai12.maven_ai12.data.interfaces.InsideDataCliForGameImpl;
import ai12.maven_ai12.data.interfaces.InsideDataCliForMainImpl;
import ai12.maven_ai12.game.GameClientEngine;
import ai12.maven_ai12.game.IInsideGameForDataCli;
import ai12.maven_ai12.main.interfaces.IInsideMainForDataCli;

public class DataClientEngine {
	private static Logger logger = Logger.getLogger(DataClientEngine.class);
	private SavedPlayer mCurrentPlayer;
	private boolean isConnectedToAServer;
	private String mServerIp;
	private IInsideMainForDataCli mInsideMainForDataCli;
	private IInsideDataCliForMain mInsideDataCliForMain;
	private IInsideGameForDataCli mInsideGameForDataCli;
	private IInsideDataCliForGame mInsideDataCliForGame;
	private IInsideComCliForDataCli mInsideComCliForDataCli;
	private IInsideDataCliForComCli mInsideDataCliForComCli;

	public DataClientEngine() {
		this.mInsideDataCliForMain = new InsideDataCliForMainImpl(this);
		this.mInsideDataCliForGame = new InsideDataCliForGameImpl(this);
		this.mInsideDataCliForComCli = new InsideDataCliForComCliImpl(this);

		GameClientEngine vIHMGame = new GameClientEngine();
		this.mInsideGameForDataCli = vIHMGame.getIInsideGameForDataCli();
		vIHMGame.setIInsideDataCliForGame(this.mInsideDataCliForGame);

		this.mCurrentPlayer = null;
		this.mServerIp = null;
		this.setConnectedToAServer(false);
		logger.info("Initialisation du module DataClientEngine");
	}

	public IInsideMainForDataCli getInsideMainForDataCli() {
		return mInsideMainForDataCli;
	}

	public void setInsideMainForDataCli(IInsideMainForDataCli pInsideMainForDataCli) {
		this.mInsideMainForDataCli = pInsideMainForDataCli;
	}

	public IInsideDataCliForMain getInsideDataCliForMain() {
		return mInsideDataCliForMain;
	}

	public void setInsideDataCliForMain(IInsideDataCliForMain pInsideDataCliForMain) {
		this.mInsideDataCliForMain = pInsideDataCliForMain;
	}

	public IInsideGameForDataCli getInsideGameForDataCli() {
		return mInsideGameForDataCli;
	}

	public void setInsideGameForDataCli(IInsideGameForDataCli pInsideGameForDataCli) {
		this.mInsideGameForDataCli = pInsideGameForDataCli;
	}

	public IInsideDataCliForGame getInsideDataCliForGame() {
		return mInsideDataCliForGame;
	}

	public void setInsideDataCliForGame(IInsideDataCliForGame pInsideDataCliForGame) {
		this.mInsideDataCliForGame = pInsideDataCliForGame;
	}

	public IInsideComCliForDataCli getInsideComCliForDataCli() {
		return mInsideComCliForDataCli;
	}

	public void setInsideComCliForDataCli(IInsideComCliForDataCli pInsideComCliForDataCli) {
		this.mInsideComCliForDataCli = pInsideComCliForDataCli;
	}

	public IInsideDataCliForComCli getInsideDataCliForComCli() {
		return mInsideDataCliForComCli;
	}

	public void setInsideDataCliForComCli(IInsideDataCliForComCli pInsideDataCliForComCli) {
		this.mInsideDataCliForComCli = pInsideDataCliForComCli;
	}

	public SavedPlayer getCurrentPlayer() {
		return mCurrentPlayer;
	}

	public void setCurrentPlayer(SavedPlayer pCurrentPlayer) {
		this.mCurrentPlayer = pCurrentPlayer;
	}

	public void setServerIp(String pServerIp) {
		this.mServerIp = pServerIp;
	}

	public String getServerIp() {
		return mServerIp;
	}

	public boolean isConnectedToAServer() {
		return isConnectedToAServer;
	}

	public void setConnectedToAServer(boolean isConnectedToAServer) {
		this.isConnectedToAServer = isConnectedToAServer;
	}
}
