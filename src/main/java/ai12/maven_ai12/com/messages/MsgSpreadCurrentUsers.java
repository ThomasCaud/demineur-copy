package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.List;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadCurrentUsers extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SoftPlayer> mSoftPlayers;

	public MsgSpreadCurrentUsers(List<SoftPlayer> pSoftPlayers) {
		mSoftPlayers = pSoftPlayers;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyCurrentUsers(mSoftPlayers);
	}
}