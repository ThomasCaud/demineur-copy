package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Shot;

public class MsgSpreadFlag extends MessageCommunicationSrv implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Shot mShot;

	public MsgSpreadFlag(Shot pShot) {
		mShot = pShot;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().sendNewFlag(mShot);
	}

}
