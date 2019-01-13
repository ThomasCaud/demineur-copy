package ai12.maven_ai12.com.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

public class ListenerThreadClient extends Thread {

	private static Logger logger = Logger.getLogger(ComClientEngine.class);
	private ComClientEngine mEngine;
	private ServerSocket mServeurSocket;
	private Socket mSocket;
	private AtomicBoolean running = new AtomicBoolean(false);

	ListenerThreadClient(ComClientEngine pEngine) {
		try {
			mEngine = pEngine;
			mServeurSocket = new ServerSocket(0);
			mEngine.setPortEcoute(mServeurSocket.getLocalPort());
			this.start();
		} catch (IOException e) {
			logger.fatal(
					"Cannot create socket on port " + mEngine.getPortEcoute() + " of address " + mEngine.getHostIp());
			throw new InternalError();
		}
	}

	public void interrupt() {
		logger.info("Interrupting ListenerThreadClient...");
		running.set(false);
		try {
			if (mServeurSocket != null) {
				logger.info("Closing server socket...");
				mServeurSocket.close();
			}
		} catch (IOException e) {
			logger.fatal("Unable to close socket : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		running.set(true);
		while (running.get()) {
			try {
				mSocket = mServeurSocket.accept();
				new HandlerThreadClient(mEngine, mSocket);
			}catch(SocketException e){
				// if the server is not running, we can assume that it is the expected comportment
				// otherwwise we need to process the exception.
				if(running.get()){
					logger.fatal("Unable to accept socket request : " + e.getMessage());
				}else{
					logger.info("Server socket closed and Thread Interrupted");
				}
			} 
			catch (IOException e) {
				logger.fatal("Unable to accept socket request : " + e.getMessage());
			}
		}
	}
}