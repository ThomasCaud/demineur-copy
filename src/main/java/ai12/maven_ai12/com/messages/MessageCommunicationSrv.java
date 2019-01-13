package ai12.maven_ai12.com.messages;

import ai12.maven_ai12.com.client.ComClientEngine;

public abstract class MessageCommunicationSrv extends MessageCommunication{

    protected ComClientEngine mComClientEngine;

    public void setEngine(ComClientEngine pComClientEngine){
        mComClientEngine = pComClientEngine;
    }
}
