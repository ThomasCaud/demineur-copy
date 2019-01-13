package ai12.maven_ai12.data.interfaces;

import java.sql.Timestamp;

import ai12.maven_ai12.beans.Action;
import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.data.DataClientEngine;

public class InsideDataCliForGameImpl implements IInsideDataCliForGame {

	private DataClientEngine dataClientEngine;

	public InsideDataCliForGameImpl(DataClientEngine pDataClientEngine) {
		this.dataClientEngine = pDataClientEngine;
	}

	@Override
	public void forfeitGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String pMessage) throws IllegalArgumentException {
		if (pMessage == null || pMessage.equals("")) {
			throw new IllegalArgumentException("Message cannot be empty.");
		}
		Message vMessage = new Message(new Timestamp(System.currentTimeMillis()), pMessage,
				dataClientEngine.getCurrentPlayer());
		dataClientEngine.getInsideComCliForDataCli().sendMessage(vMessage);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void timerExpired() {
		// TODO Auto-generated method stub

	}

	@Override
	public void putFlag(Shot pShot) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void toggleFlag(Shot pShot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playShot(Coordinates pCoordinates) throws IllegalArgumentException {
		if (pCoordinates == null) {
			throw new IllegalArgumentException("Coordinates are null.");
		}
		Shot vShot = new Shot();
		Box vBox = new Box();
		vBox.setCoordinates(pCoordinates);
		vShot.setBox(vBox);
		vShot.setPlayer(dataClientEngine.getCurrentPlayer());
		vShot.setAction(Action.ACTION);
		dataClientEngine.getInsideComCliForDataCli().sendShot(vShot);
	}

	@Override
	public void saveGame() {
		dataClientEngine.getInsideComCliForDataCli().askGameToSave(dataClientEngine.getCurrentPlayer());
	}

	@Override
	public void quitGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerProfile getLocalPlayer() {
		return dataClientEngine.getCurrentPlayer();
	}
}
