package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadConnection extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<SoftPlayer> mSoftPlayers;

    public MsgSpreadConnection(List<SoftPlayer> pSoftPlayers){
        this.mSoftPlayers = new ArrayList<SoftPlayer>(pSoftPlayers);
    }

    @Override
    public void process() {
        // recu par le client
        mComClientEngine.getInsideDataCliForComCli().sendListPlayerConnectedToUser(mSoftPlayers);
    }
}