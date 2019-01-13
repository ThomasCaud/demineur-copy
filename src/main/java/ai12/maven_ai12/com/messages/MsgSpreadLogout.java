package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.UUID;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadLogout extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID mSoftPlayerId;

	public MsgSpreadLogout(UUID pSoftPlayerId) {
		this.mSoftPlayerId = pSoftPlayerId;
	}

	@Override
	public void process() {
		// recu par le client
		SoftPlayer vSoftPlayer = new SoftPlayer();
		vSoftPlayer.setIdPlayer(mSoftPlayerId);
		mComClientEngine.getInsideDataCliForComCli().deleteUser(vSoftPlayer);
	}

}
