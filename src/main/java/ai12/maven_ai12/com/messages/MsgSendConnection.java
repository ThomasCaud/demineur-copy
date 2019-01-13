package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;

public class MsgSendConnection extends MessageCommunicationClient implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MsgSendConnection.class);

	private SoftPlayer mSoftPlayer;
	private String mHostIP;
	private Integer mHostPort;

	public MsgSendConnection(SoftPlayer pSoftPlayer, String pHostIP, Integer pHostPort) {
		this.mSoftPlayer = pSoftPlayer;
		this.mHostIP = pHostIP;
		this.mHostPort = pHostPort;
	}

	@Override
	public void process() {
		logger.info("New connection from @IP " + mHostIP);
		// Ajout de l'utilisateur dans le serveur
		mComSrvEngine.putUser(mSoftPlayer.getIdPlayer(), mHostIP, mHostPort);
		// data add user
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().addUser(mSoftPlayer);
		} catch (PlayerAlreadyConnectedException e) {
			e.printStackTrace();
		}
	}
}
