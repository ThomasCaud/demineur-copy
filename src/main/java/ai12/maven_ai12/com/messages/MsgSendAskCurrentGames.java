package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai12.maven_ai12.beans.SoftGame;

public class MsgSendAskCurrentGames extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID mSoftPlayerID;

	public MsgSendAskCurrentGames(UUID pSoftPlayerID) {
		this.mSoftPlayerID = pSoftPlayerID;
	}

	@Override
	public void process() {
		// recu par le serveur
		// Obtenir les currentGames depuis data
		List<SoftGame> vSoftGames = new ArrayList<SoftGame>();

		vSoftGames = mComSrvEngine.getInsideDataSrvForComSrv().getCurrentGames();

		// Créer le message à destination de l'utilisateur
		MsgSpreadCurrentGames vMsgSpreadCurrentGames = new MsgSpreadCurrentGames(vSoftGames);
		mComSrvEngine.spreadMessageCommunication(mSoftPlayerID, vMsgSpreadCurrentGames);
	}

}
