package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSpreadGameAborted extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;
	SoftGame mSoftGame;

	public MsgSpreadGameAborted(SoftGame pSoftGame) {
		this.mSoftGame = pSoftGame;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyGameAborted(mSoftGame);

	}

}
