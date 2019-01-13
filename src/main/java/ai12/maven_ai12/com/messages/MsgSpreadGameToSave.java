package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Game;

public class MsgSpreadGameToSave extends MessageCommunicationSrv implements Serializable {

    private static final long serialVersionUID = 1L;

    private Game mGame;

    public MsgSpreadGameToSave(Game pGame) {
        this.mGame = pGame;
    }

    @Override
    public void process() {
        mComClientEngine.getInsideDataCliForComCli().gameToSaveReceived(mGame);
    }

}
