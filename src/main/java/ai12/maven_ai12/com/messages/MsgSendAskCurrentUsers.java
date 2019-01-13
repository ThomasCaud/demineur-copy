package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSendAskCurrentUsers extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID mSoftPlayerID;

	public MsgSendAskCurrentUsers(UUID pSoftPlayerID) {
		this.mSoftPlayerID = pSoftPlayerID;
	}

	@Override
	public void process() {
		List<SoftPlayer> vSoftUsers = mComSrvEngine.getInsideDataSrvForComSrv().getCurrentPlayers();

		MsgSpreadCurrentUsers vMsgSpreadCurrentUsers = new MsgSpreadCurrentUsers(vSoftUsers);
		mComSrvEngine.spreadMessageCommunication(mSoftPlayerID, vMsgSpreadCurrentUsers);
	}

}
