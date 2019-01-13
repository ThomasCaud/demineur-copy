package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.UUID;

import ai12.maven_ai12.beans.Game;

public class MsgSendAskGameToSave extends MessageCommunicationClient implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID mSoftPlayerID;

    public MsgSendAskGameToSave(UUID pSoftPlayerID) {
        this.mSoftPlayerID = pSoftPlayerID;
    }

    @Override
    public void process() {
       
		Game vGame = mComSrvEngine.getInsideDataSrvForComSrv().getGameToSave(mSoftPlayerID);

        // Créer le message à destination de l'utilisateur
        MsgSpreadGameToSave vMsgSpreadGameToSave = new MsgSpreadGameToSave(vGame);
        mComSrvEngine.spreadMessageCommunication(mSoftPlayerID, vMsgSpreadGameToSave);
    }

}
