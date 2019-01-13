package ai12.maven_ai12.data.interfaces;

import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.Shot;

public interface IInsideDataCliForGame {

	void forfeitGame();

	void sendMessage(String pMessage) throws IllegalArgumentException;

	void startGame();

	void timerExpired();

	void putFlag(Shot pShot);
	
	void toggleFlag(Shot pShot);

	void playShot(Coordinates pCoordinates);

	void saveGame();

	void quitGame();

	PlayerProfile getLocalPlayer();
}
