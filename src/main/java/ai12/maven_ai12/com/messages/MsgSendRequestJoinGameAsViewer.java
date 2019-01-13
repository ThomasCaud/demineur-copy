package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSendRequestJoinGameAsViewer extends MessageCommunicationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftPlayer mSoftPlayer;
    private SoftGame mSoftGame;

    public MsgSendRequestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
        mSoftPlayer = pSoftPlayer;
        mSoftGame = pSoftGame;
	}

    @Override
    public void process() {
		mComSrvEngine.getInsideComSrvForDataSrv().spreadRequestJoinGameAsViewer(mSoftPlayer, mSoftGame);
    }
}
