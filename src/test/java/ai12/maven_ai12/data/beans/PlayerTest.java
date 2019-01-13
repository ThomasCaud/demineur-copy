package ai12.maven_ai12.data.beans;

import ai12.maven_ai12.beans.Picture;
import ai12.maven_ai12.beans.SavedPlayer;
import ai12.maven_ai12.beans.SoftPlayer;
import junit.framework.TestCase;

public class PlayerTest extends TestCase {
	public PlayerTest(String testName) {
		super(testName);
	}

	public void testSoftPlayerFromSavedPlayer() {
		SavedPlayer savedPlayer = new SavedPlayer();
		savedPlayer.setLogin("login");
		savedPlayer.setAvatar(new Picture());

		SoftPlayer softPlayer = new SoftPlayer(savedPlayer);
		assert (softPlayer.getLogin().equals(savedPlayer.getLogin()));
		assert (softPlayer.getIdPlayer().equals(savedPlayer.getIdPlayer()));
	}
}