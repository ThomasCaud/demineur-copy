package ai12.maven_ai12.com.messages;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;

import java.io.Serializable;

public class MsgSpreadDenyPlayerRequest extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftPlayer mSoftPlayer;
    private SoftGame mSoftGame;

    public MsgSpreadDenyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame){
        mSoftPlayer = pSoftPlayer;
        mSoftGame = pSoftGame;
    }

    @Override
    public void process() {
        mComClientEngine.getInsideDataCliForComCli().joinGameDenied(mSoftPlayer, mSoftGame);
    }
}