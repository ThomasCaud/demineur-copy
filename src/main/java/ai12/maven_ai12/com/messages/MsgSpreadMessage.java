package ai12.maven_ai12.com.messages;

import java.io.Serializable;

import ai12.maven_ai12.beans.Message;

public class MsgSpreadMessage extends MessageCommunicationSrv implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Message mMessage;

	public MsgSpreadMessage(Message pMessage) {
		this.mMessage = pMessage;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().sendChat(mMessage);
		
	}

}
