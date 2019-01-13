package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadShot extends MessageCommunicationSrv implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Shot mShot;
	private SoftPlayer mSoftPlayer;

	public MsgSpreadShot(Shot pShot, SoftPlayer pSoftPlayer) {
		mShot = pShot;
		mSoftPlayer = pSoftPlayer;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().sendShot(mSoftPlayer, mShot.getBox().getCoordinates());
	}

}
