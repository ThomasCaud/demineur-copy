package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSendStartGame extends MessageCommunicationClient implements Serializable {

    
    private static final long serialVersionUID = 1L;
    private SoftGame mSoftGame;

    public MsgSendStartGame(SoftGame pSoftGame) {
       this.mSoftGame = pSoftGame;
    }

    @Override
    public void process() {
		mComSrvEngine.getInsideDataSrvForComSrv().sendStartGame(mSoftGame);
    }
}