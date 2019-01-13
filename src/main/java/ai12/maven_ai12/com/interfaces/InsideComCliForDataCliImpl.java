package ai12.maven_ai12.com.interfaces;

import java.util.UUID;

import org.apache.log4j.Logger;

import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.com.client.ComClientEngine;
import ai12.maven_ai12.com.messages.MsgSendAbortGame;
import ai12.maven_ai12.com.messages.MsgSendAcceptPlayerRequest;
import ai12.maven_ai12.com.messages.MsgSendAcceptViewerRequest;
import ai12.maven_ai12.com.messages.MsgSendAskCurrentGames;
import ai12.maven_ai12.com.messages.MsgSendAskCurrentUsers;
import ai12.maven_ai12.com.messages.MsgSendAskGameToSave;
import ai12.maven_ai12.com.messages.MsgSendConnection;
import ai12.maven_ai12.com.messages.MsgSendCreateGame;
import ai12.maven_ai12.com.messages.MsgSendDenyPlayerRequest;
import ai12.maven_ai12.com.messages.MsgSendFlag;
import ai12.maven_ai12.com.messages.MsgSendJoinGameAsPlayer;
import ai12.maven_ai12.com.messages.MsgSendJoinGameAsViewer;
import ai12.maven_ai12.com.messages.MsgSendLogout;
import ai12.maven_ai12.com.messages.MsgSendMessage;
import ai12.maven_ai12.com.messages.MsgSendRequestJoinGameAsPlayer;
import ai12.maven_ai12.com.messages.MsgSendRequestJoinGameAsViewer;
import ai12.maven_ai12.com.messages.MsgSendShot;
import ai12.maven_ai12.com.messages.MsgSendStartGame;
import ai12.maven_ai12.com.messages.MsgSendTimerExpired;

public class InsideComCliForDataCliImpl implements IInsideComCliForDataCli {
	private static Logger logger = Logger.getLogger(InsideComCliForDataCliImpl.class);
	private ComClientEngine mComClientEngine;

	public InsideComCliForDataCliImpl(ComClientEngine pComClientEngine) {
		this.mComClientEngine = pComClientEngine;
	}

	@Override
	public void connection(SoftPlayer pSoftPlayer) {
		// creation du message à envoyer
		MsgSendConnection vMsgSendConnection = new MsgSendConnection(pSoftPlayer, mComClientEngine.getHostIp(),
				mComClientEngine.getPortEcoute());

		// envoyer le message
		logger.info("Envoi d'une demande de connexion pour le player " + pSoftPlayer.getIdPlayer());
		mComClientEngine.sendMessageCommunication(vMsgSendConnection);
	}

	@Override
	public void forfeitGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendMessage(Message pMessage) {
		// creation du message à envoyer
		MsgSendMessage vMsgSendMessage = new MsgSendMessage(pMessage);

		// envoyer le message
		mComClientEngine.sendMessageCommunication(vMsgSendMessage);
	}

	@Override
	public void leaveGame(SoftPlayer pSoftPlayer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startGame(SoftGame pSoftGame) {
		MsgSendStartGame vMsgSendStartGame = new MsgSendStartGame(pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendStartGame);
	}

	@Override
	public void requestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSendRequestJoinGameAsPlayer vMsgSendJoinGame = new MsgSendRequestJoinGameAsPlayer(pSoftPlayer, pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendJoinGame);
	}

	@Override
	public void requestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSendRequestJoinGameAsViewer vMsgSendJoinGame = new MsgSendRequestJoinGameAsViewer(pSoftPlayer, pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendJoinGame);
	}

	@Override
	public void joinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayer) {
		MsgSendJoinGameAsPlayer vMsgSendJoinGame = new MsgSendJoinGameAsPlayer(pSoftGame, pSoftPlayer);
		mComClientEngine.sendMessageCommunication(vMsgSendJoinGame);
	}

	@Override
	public void joinGameAsViewer(SoftGame pSoftGame, SoftPlayer pSoftPlayer) {
		MsgSendJoinGameAsViewer vMsgSendJoinGame = new MsgSendJoinGameAsViewer(pSoftGame, pSoftPlayer);
		mComClientEngine.sendMessageCommunication(vMsgSendJoinGame);
	}

	@Override
	public void modifyPlayerProfile(PlayerProfile pPlayerProfile) {
		// TODO Auto-generated method stub
	}

	@Override
	public void logout(UUID pSoftPlayerId) {
		MsgSendLogout vMsgSendLogout = new MsgSendLogout(pSoftPlayerId);
		mComClientEngine.sendMessageCommunication(vMsgSendLogout);
	}

	@Override
	public void createGame(SoftGame pSoftGame) {
		MsgSendCreateGame vMsgSendCreateGame = new MsgSendCreateGame(pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendCreateGame);
	}

	@Override
	public void sendNewFlag(Shot pShot) {
		MsgSendFlag vMsgSendFlag = new MsgSendFlag(pShot);
		mComClientEngine.sendMessageCommunication(vMsgSendFlag);
	}

	@Override
	public void sendShot(Shot pShot) {
		MsgSendShot vMsgSendShot = new MsgSendShot(pShot);
		mComClientEngine.sendMessageCommunication(vMsgSendShot);
	}

	@Override
	public void sendTimerExpired(SoftPlayer pSoftPlayer) {
		MsgSendTimerExpired vMsgSendTimerExpired = new MsgSendTimerExpired(pSoftPlayer);
		mComClientEngine.sendMessageCommunication(vMsgSendTimerExpired);
	}

	@Override
	public void denyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSendDenyPlayerRequest vMsgSendDenyPlayerRequest = new MsgSendDenyPlayerRequest(pSoftPlayer, pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendDenyPlayerRequest);
	}

	@Override
	public void acceptPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSendAcceptPlayerRequest vMsgSendAcceptPlayerRequest = new MsgSendAcceptPlayerRequest(pSoftPlayer, pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendAcceptPlayerRequest);
	}

	@Override
	public void acceptViewerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSendAcceptViewerRequest vMsgSendAcceptViewerRequest = new MsgSendAcceptViewerRequest(pSoftPlayer, pSoftGame);
		mComClientEngine.sendMessageCommunication(vMsgSendAcceptViewerRequest);
	}

	@Override
	public void quitGame() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeComClientEngine(String pServerIP, int pPortEcoute) {
		mComClientEngine.initialize(pServerIP, pPortEcoute);
	}

	@Override
	public void startComClientEngine() throws InternalError {
		mComClientEngine.start();
	}

	@Override
	public void askCurrentUsers() {

	}

	@Override
	public void askPlayerProfile(SoftPlayer pSoftPlayer) {

	}

	@Override
	public void askGameToSave(SoftPlayer pSoftPlayer) {
		MsgSendAskGameToSave vMsgSendAskGameToSave = new MsgSendAskGameToSave(pSoftPlayer.getIdPlayer());
		mComClientEngine.sendMessageCommunication(vMsgSendAskGameToSave);
	}

	@Override
	public void sendAskCurrentGames(UUID pRequestor) {
		MsgSendAskCurrentGames vMsgSendAskCurrentGames = new MsgSendAskCurrentGames(pRequestor);
		mComClientEngine.sendMessageCommunication(vMsgSendAskCurrentGames);
	}

	@Override
	public void sendAskCurrentUsers(UUID pRequestor) {
		MsgSendAskCurrentUsers vMsgSendAskCurrentUsers = new MsgSendAskCurrentUsers(pRequestor);
		mComClientEngine.sendMessageCommunication(vMsgSendAskCurrentUsers);
	}

	@Override
	public void stopComClientEngine() {
		mComClientEngine.stop();
	}

	@Override
	public void sendAbortGame(SoftPlayer pSoftPlayer) {
		MsgSendAbortGame vMsgSendAbortGame = new MsgSendAbortGame(pSoftPlayer);
		mComClientEngine.sendMessageCommunication(vMsgSendAbortGame);

	}

	@Override
	public Integer getServerPort() {
		return mComClientEngine.getmServerPortEcoute();
	}

	@Override
	public String getServerIP() {
		return mComClientEngine.getServerIP();
	}

}