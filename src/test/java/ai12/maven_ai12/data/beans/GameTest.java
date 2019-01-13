package ai12.maven_ai12.data.beans;

import java.util.Map.Entry;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Difficulty;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.SoftGame;
import junit.framework.TestCase;

public class GameTest extends TestCase {
	public void testCorrectGridInitializationAllDifficulty() {

		SoftGame vSoftGame = new SoftGame();

		vSoftGame.getGameParameters().setDifficulty(Difficulty.EASY);
		Game vGameEasy = new Game(vSoftGame);
		testCorrectGridInitialization(vGameEasy);

		vSoftGame.getGameParameters().setDifficulty(Difficulty.MEDIUM);
		Game vGameMedium = new Game(vSoftGame);
		testCorrectGridInitialization(vGameMedium);

		vSoftGame.getGameParameters().setDifficulty(Difficulty.MEDIUM);
		Game vGameHard = new Game(vSoftGame);
		testCorrectGridInitialization(vGameHard);
	}

	public void testCorrectGridInitialization(Game pGame) {
		int nCol = pGame.getGameParameters().getNbCol();
		int nRow = pGame.getGameParameters().getNbRow();
		int vArea = nRow * nCol;
		assert (pGame.getGrid().getnCol() == nCol);
		assert (pGame.getGrid().getnRow() == nRow);
		assert (pGame.getGrid().getBoxes().size() == vArea);

		for (Entry<Coordinates, Box> vBox : pGame.getGrid().getBoxes().entrySet()) {
			assert (vBox != null);
			assert (vBox.getKey().getX() >= 0);
			assert (vBox.getKey().getX() < nCol);
			assert (vBox.getKey().getY() >= 0);
			assert (vBox.getKey().getY() < nRow);
		}
	}
}
