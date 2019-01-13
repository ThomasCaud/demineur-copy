package ai12.maven_ai12.com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import ai12.maven_ai12.com.messages.MessageCommunicationClient;

public class HandlerThreadSrv extends Thread {

    private ComSrvEngine mEngine;
    private Socket mSocket;

    HandlerThreadSrv(ComSrvEngine pEngine, Socket pSocket) {
        mEngine = pEngine;
        mSocket = pSocket;
        this.start();
    }

    public void run() {
        try (
            ObjectInputStream vObjectInputStream = new ObjectInputStream(mSocket.getInputStream())
        ){
            MessageCommunicationClient vMessage = (MessageCommunicationClient) vObjectInputStream.readObject();
            vMessage.setEngine(mEngine);
            vMessage.process();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Unable to get message from socket : " + e.getMessage());
        } finally {
            try {
                mSocket.close();
            } catch (IOException e) {
                System.err.println("Unable to close socket : " + e.getMessage());
            }
        }
    }
}
