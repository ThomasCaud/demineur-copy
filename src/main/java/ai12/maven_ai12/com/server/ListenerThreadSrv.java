package ai12.maven_ai12.com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenerThreadSrv extends Thread {

	private ComSrvEngine mEngine;
	private int mPortEcoute;
	private ServerSocket mServeurSocket;

	ListenerThreadSrv(ComSrvEngine pEngine, int pPortEcoute) throws IOException {
		mEngine = pEngine;
		mPortEcoute = pPortEcoute;
		mServeurSocket = new ServerSocket(mPortEcoute);
		this.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket socket = mServeurSocket.accept();
				new HandlerThreadSrv(mEngine, socket);
			} catch (IOException e) {
				System.err.println("Unable to accept socket request : " + e.getMessage());
			}
		}
	}
}