package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSpreadPlayerLeftLobby extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;

	// the updated game
	private SoftGame mSoftGame;

	public MsgSpreadPlayerLeftLobby(SoftGame pSoftGame) {
		mSoftGame = pSoftGame;

	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyPlayerLeftLobby(mSoftGame);
	}
}