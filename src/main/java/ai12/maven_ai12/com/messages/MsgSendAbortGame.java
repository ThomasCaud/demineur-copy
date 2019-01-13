package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSendAbortGame extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;
	SoftPlayer mSoftPlayer;

	public MsgSendAbortGame(SoftPlayer pSoftPlayer) {
		this.mSoftPlayer = pSoftPlayer;
	}

	@Override
	public void process() {
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().abortGame(mSoftPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
