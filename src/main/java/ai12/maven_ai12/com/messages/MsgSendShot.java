package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.data.interfaces.IInsideDataSrvForComSrv.AnotherActionExceptedException;
import ai12.maven_ai12.data.interfaces.IInsideDataSrvForComSrv.SquareAlreadyDiscoveredException;

public class MsgSendShot extends MessageCommunicationClient implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private Shot mShot;

	public MsgSendShot(Shot pShot) {
		mShot = pShot;
	}

	@Override
	public void process() {
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().addShot(mShot);
		} catch (SquareAlreadyDiscoveredException e) {
			e.printStackTrace();
		} catch (AnotherActionExceptedException e) {
			e.printStackTrace();
		}
	}

}
