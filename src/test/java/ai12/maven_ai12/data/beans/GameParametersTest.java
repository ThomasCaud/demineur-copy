package ai12.maven_ai12.data.beans;

import ai12.maven_ai12.beans.Difficulty;
import ai12.maven_ai12.beans.GameParameters;
import junit.framework.TestCase;

public class GameParametersTest extends TestCase {
	public void testGetNbMinesInteger() {
		GameParameters vGameParameters = new GameParameters();

		vGameParameters.setDifficulty(Difficulty.EASY);
		assert (vGameParameters.getNbMines() == 10);

		vGameParameters.setDifficulty(Difficulty.MEDIUM);
		assert (vGameParameters.getNbMines() == 40);

		vGameParameters.setDifficulty(Difficulty.HARD);
		assert (vGameParameters.getNbMines() == 99);
	}

	public void testGetNbCol() {
		GameParameters vGameParameters = new GameParameters();

		vGameParameters.setDifficulty(Difficulty.EASY);
		assert (vGameParameters.getNbCol() == 9);

		vGameParameters.setDifficulty(Difficulty.MEDIUM);
		assert (vGameParameters.getNbCol() == 16);

		vGameParameters.setDifficulty(Difficulty.HARD);
		assert (vGameParameters.getNbCol() == 30);
	}

	public void testGetNbRow() {
		GameParameters vGameParameters = new GameParameters();

		vGameParameters.setDifficulty(Difficulty.EASY);
		assert (vGameParameters.getNbRow() == 9);

		vGameParameters.setDifficulty(Difficulty.MEDIUM);
		assert (vGameParameters.getNbRow() == 16);

		vGameParameters.setDifficulty(Difficulty.HARD);
		assert (vGameParameters.getNbRow() == 16);
	}
}
