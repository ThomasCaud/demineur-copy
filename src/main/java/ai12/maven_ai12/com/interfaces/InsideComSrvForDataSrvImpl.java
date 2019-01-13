package ai12.maven_ai12.com.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.com.messages.MsgSpreadConnection;
import ai12.maven_ai12.com.messages.MsgSpreadCreateGame;
import ai12.maven_ai12.com.messages.MsgSpreadDenyPlayerRequest;
import ai12.maven_ai12.com.messages.MsgSpreadFlag;
import ai12.maven_ai12.com.messages.MsgSpreadGameAborted;
import ai12.maven_ai12.com.messages.MsgSpreadGameDeleted;
import ai12.maven_ai12.com.messages.MsgSpreadJoinGameAsPlayer;
import ai12.maven_ai12.com.messages.MsgSpreadJoinGameAsViewer;
import ai12.maven_ai12.com.messages.MsgSpreadLeaveLobby;
import ai12.maven_ai12.com.messages.MsgSpreadLogout;
import ai12.maven_ai12.com.messages.MsgSpreadMessage;
import ai12.maven_ai12.com.messages.MsgSpreadNewConnection;
import ai12.maven_ai12.com.messages.MsgSpreadNewGame;
import ai12.maven_ai12.com.messages.MsgSpreadPlayerLeftLobby;
import ai12.maven_ai12.com.messages.MsgSpreadPlayerLeftLobbyAdmin;
import ai12.maven_ai12.com.messages.MsgSpreadRequestJoinGameAsPlayer;
import ai12.maven_ai12.com.messages.MsgSpreadRequestJoinGameAsViewer;
import ai12.maven_ai12.com.messages.MsgSpreadResult;
import ai12.maven_ai12.com.messages.MsgSpreadStartGame;
import ai12.maven_ai12.com.messages.MsgSpreadTimerExpired;
import ai12.maven_ai12.com.server.ComSrvEngine;

public class InsideComSrvForDataSrvImpl implements IInsideComSrvForDataSrv {

	private ComSrvEngine mComSrvEngine;

	public InsideComSrvForDataSrvImpl(ComSrvEngine pComSrvEngine) {
		this.mComSrvEngine = pComSrvEngine;
	}

	@Override
	public void spreadShot(List<SoftPlayer> pSoftPlayers, Shot pShot) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadConnection(SoftPlayer pSoftPlayer, List<SoftPlayer> pSoftPlayers) {

		// create msg to spread to last connected user
		MsgSpreadConnection vMsgSpreadConnection = new MsgSpreadConnection(pSoftPlayers);

		// create msg to spread to already connected users
		MsgSpreadNewConnection vMsgSpreadNewConnection = new MsgSpreadNewConnection(pSoftPlayer);
		ArrayList<UUID> vPlayerIDs = new ArrayList<UUID>();
		for (SoftPlayer softPlayer : pSoftPlayers) {
			if (softPlayer.getIdPlayer() != pSoftPlayer.getIdPlayer())
				vPlayerIDs.add(softPlayer.getIdPlayer());
		}

		// Dans un thread du serveur envoyer ces messages
		try {
			mComSrvEngine.spreadMessageCommunication(pSoftPlayer.getIdPlayer(), vMsgSpreadConnection);
			mComSrvEngine.spreadMessageCommunication(vPlayerIDs, vMsgSpreadNewConnection);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void spreadForfeitGame(List<SoftPlayer> pSoftPlayers) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadMessage(SoftPlayer pSoftPlayer, Message pMessage) {
		// create msg to spread to last connected user
		MsgSpreadMessage vMsgSpreadMessage = new MsgSpreadMessage(pMessage);

		mComSrvEngine.spreadMessageCommunication(pSoftPlayer.getIdPlayer(), vMsgSpreadMessage);
	}

	@Override
	public void spreadLeaveGame(List<SoftPlayer> pSoftPlayers) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadStartGame(List<SoftPlayer> pSoftPlayers, Game pGame) {
		for (SoftPlayer vPlayer : pSoftPlayers) {
			MsgSpreadStartGame vMsgSpreadStartGame = new MsgSpreadStartGame(pGame);
			mComSrvEngine.spreadMessageCommunication(vPlayer.getIdPlayer(), vMsgSpreadStartGame);
		}
	}

	@Override
	public void spreadRequestJoinGameAsPlayer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSpreadRequestJoinGameAsPlayer vMsgSpreadRequestJoinGameAsPlayer = new MsgSpreadRequestJoinGameAsPlayer(
				pSoftPlayer, pSoftGame);
		mComSrvEngine.spreadMessageCommunication(pSoftGame.getCreator().getIdPlayer(),
				vMsgSpreadRequestJoinGameAsPlayer);
	}

	@Override
	public void spreadRequestJoinGameAsViewer(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSpreadRequestJoinGameAsViewer vMsgSpreadRequestJoinGameAsViewer = new MsgSpreadRequestJoinGameAsViewer(
				pSoftPlayer, pSoftGame);
		mComSrvEngine.spreadMessageCommunication(pSoftGame.getCreator().getIdPlayer(),
				vMsgSpreadRequestJoinGameAsViewer);
	}

	@Override
	public void spreadJoinGameAsPlayer(SoftGame pSoftGame, SoftPlayer pSoftPlayerJoining,
			List<SoftPlayer> pSoftPlayers) {
		for (SoftPlayer vPlayer : pSoftPlayers) {
			MsgSpreadJoinGameAsPlayer vMsgSpreadJoinGameAsPlayer = new MsgSpreadJoinGameAsPlayer(pSoftGame,
					pSoftPlayerJoining);
			mComSrvEngine.spreadMessageCommunication(vPlayer.getIdPlayer(), vMsgSpreadJoinGameAsPlayer);
		}
	}

	@Override
	public void spreadJoinGameAsViewer(SoftGame pSoftGame, List<SoftPlayer> pSoftPlayers) {
		MsgSpreadJoinGameAsViewer vMsgSpreadJoinGameAsViewer = new MsgSpreadJoinGameAsViewer(pSoftGame);
		for (SoftPlayer vPlayer : pSoftPlayers) {
			mComSrvEngine.spreadMessageCommunication(vPlayer.getIdPlayer(), vMsgSpreadJoinGameAsViewer);
		}
	}

	@Override
	public void spreadLogout(SoftPlayer pSoftPlayer) {
		MsgSpreadLogout vMsgSpreadLogout = new MsgSpreadLogout(pSoftPlayer.getIdPlayer());
		try {
			List<UUID> connectedPlayers = mComSrvEngine.getUUIDConnectedPlayers();

			if (connectedPlayers != null && !connectedPlayers.isEmpty()) {
				mComSrvEngine.spreadMessageCommunication(connectedPlayers, vMsgSpreadLogout);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void spreadCreateGame(SoftGame pSoftGame) {

		// create msg to spread to the game creator
		MsgSpreadCreateGame vMsgSpreadCreateGame = new MsgSpreadCreateGame(pSoftGame);

		// create msg to spread to already connected users, except the creator
		MsgSpreadNewGame vMsgSpreadNewGame = new MsgSpreadNewGame(pSoftGame);
		List<UUID> connectedPlayers = mComSrvEngine.getUUIDConnectedPlayers();
		SoftPlayer gameCreator = pSoftGame.getCreator();
		connectedPlayers.remove(gameCreator.getIdPlayer());

		// Dans un thread du serveur envoyer ces messages
		try {
			mComSrvEngine.spreadMessageCommunication(gameCreator.getIdPlayer(), vMsgSpreadCreateGame);
			mComSrvEngine.spreadMessageCommunication(connectedPlayers, vMsgSpreadNewGame);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void spreadConnection(SoftPlayer pSoftPlayer) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadNewFlag(Shot pShot, SoftPlayer pSoftPlayer) {
		MsgSpreadFlag vMsgSpreadFlag = new MsgSpreadFlag(pShot);
		// On envoie à la personne pSoftPlayer
		// C'est un observer
		mComSrvEngine.spreadMessageCommunication(pSoftPlayer.getIdPlayer(), vMsgSpreadFlag);

	}

	@Override
	public void spreadResult(Shot pShot, List<Box> pBoxes, SoftPlayer pSoftPlayer, boolean pGameIsOver,
			SoftPlayer pNextPlayer, List<SoftPlayer> pWinners) {
		MsgSpreadResult vMsgSpreadResult = new MsgSpreadResult(pShot, pBoxes, pGameIsOver, pNextPlayer, pWinners);
		// pSoftPlayer : le joueur à qui l'on veut evoyer
		mComSrvEngine.spreadMessageCommunication(pSoftPlayer.getIdPlayer(), vMsgSpreadResult);
	}

	@Override
	public void spreadTimerExpired(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2) {
		MsgSpreadTimerExpired vMsgSpreadTimerExpired = new MsgSpreadTimerExpired(pSoftPlayer2);
		mComSrvEngine.spreadMessageCommunication(pSoftPlayer1.getIdPlayer(), vMsgSpreadTimerExpired);
	}

	@Override
	public void spreadDenyPlayerRequest(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		MsgSpreadDenyPlayerRequest vMsgSpreadDenyPlayerRequest = new MsgSpreadDenyPlayerRequest(pSoftPlayer, pSoftGame);
		mComSrvEngine.spreadMessageCommunication(pSoftPlayer.getIdPlayer(), vMsgSpreadDenyPlayerRequest);
	}

	@Override
	public void spreadNewObserver(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sendGameJoin(Game pGame) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadUserLeft(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void spreadPlayerSurrendered(SoftPlayer pSoftPlayer1, SoftPlayer pSoftPlayer2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void startEngine(Integer pPort) throws IOException {
		this.mComSrvEngine.setPortEcoute(pPort);
		this.mComSrvEngine.start();
	}

	@Override
	public void spreadGameAborted(SoftGame pSoftGame, List<SoftPlayer> pListSoftPlayerInGame,
			List<SoftPlayer> pListOtherSoftPlayer) {

		MsgSpreadGameAborted vMsgSpreadGameAborted = new MsgSpreadGameAborted(pSoftGame);

		for (SoftPlayer softPlayerInGame : pListSoftPlayerInGame) {
			mComSrvEngine.spreadMessageCommunication(softPlayerInGame.getIdPlayer(), vMsgSpreadGameAborted);
		}

		MsgSpreadGameDeleted vMsgSpreadGameDeleted = new MsgSpreadGameDeleted(pSoftGame);

		for (SoftPlayer otherSoftPlayer : pListOtherSoftPlayer) {
			mComSrvEngine.spreadMessageCommunication(otherSoftPlayer.getIdPlayer(), vMsgSpreadGameDeleted);
		}

	}

	@Override
	public void spreadPlayerLeft(SoftPlayer pSoftPlayerLeft, List<SoftPlayer> pListSoftPlayerInGame,
			SoftPlayer pSoftPlayerAdmin, SoftGame pSoftGame) {
		// To send to pListSoftPlayerInGame
		MsgSpreadPlayerLeftLobby vMsgSpreadPlayerLeftLobby = new MsgSpreadPlayerLeftLobby(pSoftGame);
		for (SoftPlayer vSoftPlayer : pListSoftPlayerInGame) {
			mComSrvEngine.spreadMessageCommunication(vSoftPlayer.getIdPlayer(), vMsgSpreadPlayerLeftLobby);
		}

		// to send to pSoftPlayerLeft
		MsgSpreadLeaveLobby vMsgSpreadLeaveLobby = new MsgSpreadLeaveLobby();
		mComSrvEngine.spreadMessageCommunication(pSoftPlayerLeft.getIdPlayer(), vMsgSpreadLeaveLobby);

		// to send to pSoftPlayerAdmin
		MsgSpreadPlayerLeftLobbyAdmin vMsgSpreadPlayerLeftLobbyAdmin = new MsgSpreadPlayerLeftLobbyAdmin(pSoftGame);
		mComSrvEngine.spreadMessageCommunication(pSoftPlayerAdmin.getIdPlayer(), vMsgSpreadPlayerLeftLobbyAdmin);
	}
}
