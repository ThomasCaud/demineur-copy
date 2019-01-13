package ai12.maven_ai12.com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ai12.maven_ai12.com.messages.MessageCommunicationSrv;

public class HandlerThreadClient extends Thread {

	private ComClientEngine mEngine;
	private Socket mSocket;

	HandlerThreadClient(ComClientEngine pEngine, Socket pSocket) {
		mEngine = pEngine;
		mSocket = pSocket;
		this.start();
	}

	public void run() {
		try (ObjectInputStream vObjectInputStream = new ObjectInputStream(mSocket.getInputStream())) {
			MessageCommunicationSrv message = (MessageCommunicationSrv) vObjectInputStream.readObject();
			message.setEngine(mEngine);
			message.process();
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
