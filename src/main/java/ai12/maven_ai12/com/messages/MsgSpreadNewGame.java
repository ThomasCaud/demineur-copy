package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSpreadNewGame extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;

	private SoftGame mSoftGame;

	public MsgSpreadNewGame(SoftGame pSoftGame) {
		this.mSoftGame = pSoftGame;
	}

	@Override
	public void process() {
		// recu par le client
		mComClientEngine.getInsideDataCliForComCli().notifyNewGame(mSoftGame);
	}
}