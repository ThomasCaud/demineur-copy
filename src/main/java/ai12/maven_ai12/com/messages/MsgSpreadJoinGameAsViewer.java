package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSpreadJoinGameAsViewer extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftGame mSoftGame;

	public MsgSpreadJoinGameAsViewer(SoftGame pSoftGame) {
        mSoftGame = pSoftGame;
    }

    @Override
    public void process() {
		mComClientEngine.getInsideDataCliForComCli().notifyGameJoinAsViewer(mSoftGame);
    }
}