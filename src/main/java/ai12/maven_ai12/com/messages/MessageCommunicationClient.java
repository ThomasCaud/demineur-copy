package ai12.maven_ai12.com.messages;

import ai12.maven_ai12.com.server.ComSrvEngine;

public abstract class MessageCommunicationClient extends MessageCommunication {

    protected ComSrvEngine mComSrvEngine;

    public void setEngine(ComSrvEngine pComSrvEngine){
        mComSrvEngine = pComSrvEngine;
    }
}
