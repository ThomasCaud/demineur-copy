package ai12.maven_ai12.com.messages;

import java.io.Serializable;

public class MsgSpreadLeaveLobby extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    public MsgSpreadLeaveLobby(){}

    @Override
    public void process() {
        mComClientEngine.getInsideDataCliForComCli().notifyLobbyLeaving();
    }
}