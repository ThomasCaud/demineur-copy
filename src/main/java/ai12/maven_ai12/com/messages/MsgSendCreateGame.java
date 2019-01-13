package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;

public class MsgSendCreateGame extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private SoftGame mSoftGame;

	public MsgSendCreateGame(SoftGame pSoftGame) {
		this.mSoftGame = pSoftGame;
	}

	@Override
	public void process() {
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().createGame(mSoftGame);
		} catch (GameAlreadyCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
