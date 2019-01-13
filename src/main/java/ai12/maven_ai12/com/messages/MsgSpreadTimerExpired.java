package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadTimerExpired extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;
    private SoftPlayer mSoftPlayer;

    public MsgSpreadTimerExpired(SoftPlayer pSoftPlayer) {
        mSoftPlayer = pSoftPlayer;
    }

    @Override
    public void process() {
        mComClientEngine.getInsideDataCliForComCli().sendTimerExpired(mSoftPlayer);
    }

}
