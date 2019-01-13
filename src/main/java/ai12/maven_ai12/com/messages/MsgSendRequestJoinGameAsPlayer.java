package ai12.maven_ai12.com.messages;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

import java.io.Serializable;

public class MsgSendRequestJoinGameAsPlayer extends MessageCommunicationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftPlayer mSoftPlayer;
    private SoftGame mSoftGame;

    public MsgSendRequestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
        mSoftPlayer = pSoftPlayer;
        mSoftGame = pSoftGame;
	}

    @Override
    public void process() {
        mComSrvEngine.getInsideComSrvForDataSrv().spreadRequestJoinGameAsPlayer(mSoftPlayer, mSoftGame);
    }
}
