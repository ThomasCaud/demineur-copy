package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.UUID;

import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.exceptions.PlayerNotConnectedException;

public class MsgSendLogout extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;
	private UUID mSoftPlayerId;

	public MsgSendLogout(UUID pSoftPlayerId) {
		this.mSoftPlayerId = pSoftPlayerId;
	}

	@Override
	public void process() {
		// recu par le serveur
		mComSrvEngine.removeUser(mSoftPlayerId);
		// data add user
		SoftPlayer vSoftPlayer = new SoftPlayer();
		vSoftPlayer.setIdPlayer(mSoftPlayerId);
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().deleteUser(vSoftPlayer);
		} catch (PlayerNotConnectedException e) {
			e.printStackTrace();
		}
	}

}
