package ai12.maven_ai12.com.commons;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import ai12.maven_ai12.com.client.ComClientEngine;
import ai12.maven_ai12.com.messages.MessageCommunication;

public class SenderThread extends Thread {

	private static Logger logger = Logger.getLogger(ComClientEngine.class);
	private MessageCommunication mMessage;
	private String mHost;
	private int mPortEcoute;

	public SenderThread(MessageCommunication pMessage, String pHost, int pPortEcoute) {
		mMessage = pMessage;
		mHost = pHost;
		mPortEcoute = pPortEcoute;
		this.start();
	}

	public void run() {
		Socket vSocket;
		try {
			vSocket = new Socket(mHost, mPortEcoute);
			ObjectOutputStream vObjectOutputStream = new ObjectOutputStream(vSocket.getOutputStream());
			vObjectOutputStream.writeObject(mMessage);
		} catch (IOException e) {
			logger.fatal("Cannot send the message to the server : " + e.getMessage());
		}

	}
}
