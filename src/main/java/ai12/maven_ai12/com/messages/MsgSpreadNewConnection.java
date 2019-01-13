package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadNewConnection extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private SoftPlayer mSoftPlayer;

    public MsgSpreadNewConnection(SoftPlayer pSoftPlayer){
        this.mSoftPlayer = pSoftPlayer;
    }

    @Override
    public void process() {
        // recu par le client
        mComClientEngine.getInsideDataCliForComCli().addNewConnection(mSoftPlayer);
    }
}