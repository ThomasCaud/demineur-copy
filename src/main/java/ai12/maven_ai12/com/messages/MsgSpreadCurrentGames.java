package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.List;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSpreadCurrentGames extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SoftGame> mSoftGames;

	public MsgSpreadCurrentGames(List<SoftGame> pSoftGames) {
		mSoftGames = pSoftGames;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyCurrentGames(mSoftGames);
	}
}