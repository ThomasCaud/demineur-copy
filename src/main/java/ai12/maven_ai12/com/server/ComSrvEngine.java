package ai12.maven_ai12.com.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.com.commons.SenderThread;
import ai12.maven_ai12.com.interfaces.IInsideComSrvForDataSrv;
import ai12.maven_ai12.com.interfaces.InsideComSrvForDataSrvImpl;
import ai12.maven_ai12.com.messages.MessageCommunication;
import ai12.maven_ai12.data.interfaces.IInsideDataSrvForComSrv;

public class ComSrvEngine {

	private static Logger logger = Logger.getLogger(ComSrvEngine.class);
	private int mPortEcoute;
	private HashMap<UUID, Entry<String, Integer>> mMapIPUser;
	private IInsideComSrvForDataSrv mInsideComSrvForDataSrv;
	private IInsideDataSrvForComSrv mInsideDataSrvForComSrv;

	public ComSrvEngine() {
		mInsideComSrvForDataSrv = new InsideComSrvForDataSrvImpl(this);
		mMapIPUser = new HashMap<UUID, Entry<String, Integer>>();
		logger.info("Initialisation du module ComServerEngine");
	}

	/**
	 * Starts de listening thread
	 */
	public void start() throws IOException {
		logger.info("Lancement du module ComServerEngine sur le port " + mPortEcoute + " de l'IP "
				+ InetAddress.getLocalHost().getHostAddress());
		new ListenerThreadSrv(this, mPortEcoute);
	}

	public IInsideComSrvForDataSrv getInsideComSrvForDataSrv() {
		return mInsideComSrvForDataSrv;
	}

	public IInsideDataSrvForComSrv getInsideDataSrvForComSrv() {
		return mInsideDataSrvForComSrv;
	}

	public void setInsideDataSrvForComSrv(IInsideDataSrvForComSrv pInsideDataSrvForComSrv) {
		mInsideDataSrvForComSrv = pInsideDataSrvForComSrv;
	}

	public void setPortEcoute(int pPortEcoute) {
		mPortEcoute = pPortEcoute;
	}

	public Integer getPortEcoute() {
		return mPortEcoute;
	}

	public void putUser(UUID pUserId, String pUserIP, Integer pUserPort) {
		mMapIPUser.put(pUserId, new AbstractMap.SimpleEntry<String, Integer>(pUserIP, pUserPort));
	}

	public void removeUser(UUID pUserId) {
		mMapIPUser.remove(pUserId);
	}

	public List<UUID> getUUIDConnectedPlayers() {
		List<UUID> vPlayerIDs = new ArrayList<UUID>();
		vPlayerIDs.addAll(mMapIPUser.keySet());
		return vPlayerIDs;
	}

	public void spreadMessageCommunication(List<UUID> pPlayerIDs, MessageCommunication pMessage) throws Exception {
		for (UUID vID : pPlayerIDs) {
			assert (mMapIPUser.containsKey(vID));
			spreadMessageCommunication(vID, pMessage);
		}
	}

	public void spreadMessageCommunication(UUID pPlayerID, MessageCommunication pMessage) {
		if (!mMapIPUser.containsKey(pPlayerID)) {
			throw new RuntimeException("User not found");
		} else {
			Entry<String, Integer> connectionInfo = mMapIPUser.get(pPlayerID);
			new SenderThread(pMessage, connectionInfo.getKey(), connectionInfo.getValue());
		}
	}

	public void spreadMessageCommunication(String pPlayerIP, MessageCommunication pMessage) {
		new SenderThread(pMessage, pPlayerIP, Config.LISTENING_PORT_CLIENT);
	}

}
