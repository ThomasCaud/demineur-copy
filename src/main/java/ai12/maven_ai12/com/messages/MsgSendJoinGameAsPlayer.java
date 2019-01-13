package ai12.maven_ai12.com.messages;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import java.io.Serializable;

public class MsgSendJoinGameAsPlayer extends MessageCommunicationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftGame mSoftGame;
    private SoftPlayer mSoftPlayer;

    public MsgSendJoinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayer) {
        mSoftGame = pSoftGame;
        mSoftPlayer = pSoftPlayer;
	}

    @Override
    public void process() {
        mComSrvEngine.getInsideDataSrvForComSrv().joinGameAsPlayer(mSoftPlayer, mSoftGame);
    }
}
