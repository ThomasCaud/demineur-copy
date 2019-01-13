package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadJoinGameAsPlayer extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftGame mSoftGame;
	private SoftPlayer mSoftPlayerJoining;

	public MsgSpreadJoinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayerJoining) {
        mSoftGame = pSoftGame;
		mSoftPlayerJoining = pSoftPlayerJoining;
    }

    @Override
    public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyGameJoinAsPlayer(mSoftGame, mSoftPlayerJoining);
    }
}