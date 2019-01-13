package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Message;

public class MsgSendMessage extends MessageCommunicationClient implements Serializable{

	private static final long serialVersionUID = 1L;
	private Message mMessage;

	public MsgSendMessage(Message pMessage) {
		this.mMessage = pMessage;
	}

	@Override
	public void process() {
		try {
			mComSrvEngine.getInsideDataSrvForComSrv().addMessage(mMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
