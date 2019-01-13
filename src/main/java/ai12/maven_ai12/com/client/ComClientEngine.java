package ai12.maven_ai12.com.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import ai12.maven_ai12.com.commons.SenderThread;
import ai12.maven_ai12.com.interfaces.IInsideComCliForDataCli;
import ai12.maven_ai12.com.interfaces.InsideComCliForDataCliImpl;
import ai12.maven_ai12.com.messages.MessageCommunication;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForComCli;

public class ComClientEngine {
	private static Logger logger = Logger.getLogger(ComClientEngine.class);
	private String mServerIP;
	private int mServerPortEcoute;
	private String mHostIp;
	private int mPortEcoute;
	private IInsideComCliForDataCli mInsideComCliForDataCli;
	private IInsideDataCliForComCli mInsideDataCliForComCli;
	private ListenerThreadClient mListenerThreadClient;

	public ComClientEngine() {
		try {
			setHostIp(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mInsideComCliForDataCli = new InsideComCliForDataCliImpl(this);
		logger.info("Initialisation du module ComClientEngine");
		logger.info("HostIp : " + mHostIp);
	}

	/**
	 * @return the mServerPortEcoute
	 */
	public int getmServerPortEcoute() {
		return mServerPortEcoute;
	}

	/**
	 * @param mServerPortEcoute the mServerPortEcoute to set
	 */
	public void setmServerPortEcoute(int mServerPortEcoute) {
		this.mServerPortEcoute = mServerPortEcoute;
	}

	/**
	 * @return the mHostIp
	 */
	public String getHostIp() {
		return mHostIp;
	}

	/**
	 * @param mHostIp the mHostIp to set
	 */
	public void setHostIp(String mHostIp) {
		this.mHostIp = mHostIp;
	}

	public IInsideComCliForDataCli getInsideComCliForDataCli() {
		return mInsideComCliForDataCli;
	}

	public IInsideDataCliForComCli getInsideDataCliForComCli() {
		return mInsideDataCliForComCli;
	}

	public void setServerIP(String pServerIP) {
		mServerIP = pServerIP;
	}

	public String getServerIP() {
		return mServerIP;
	}

	public void setPortEcoute(Integer pPortEcoute) {
		mPortEcoute = pPortEcoute;
	}

	public Integer getPortEcoute() {
		return mPortEcoute;
	}

	public void setInsideDataCliForComCli(IInsideDataCliForComCli pInsideDataCliForComCli) {
		mInsideDataCliForComCli = pInsideDataCliForComCli;
	}

	public void sendMessageCommunication(MessageCommunication pMessage) {
		new SenderThread(pMessage, mServerIP, mServerPortEcoute);
	}

	/**
	 * Starts de listening thread
	 */
	public void start() throws InternalError {
		logger.info("Starting client listening thread");
		mListenerThreadClient = new ListenerThreadClient(this);
	}

	/**
	 * Stops de listening thread
	 */
	public void stop() throws InternalError {
		logger.info("Interrupting client listening thread");
		mListenerThreadClient.interrupt();
	}

	/**
	 * Initialize parameters for the socket
	 *
	 * @param pServerIP   Server IP
	 * @param pPortEcoute Listening port
	 */
	public void initialize(String pServerIP, int pServerPort) {
		logger.info("Initialisation du module ComClientEngine pour le serveur " + pServerIP + ":" + pServerPort);

		mServerIP = pServerIP;
		mServerPortEcoute = pServerPort;
	}

}
