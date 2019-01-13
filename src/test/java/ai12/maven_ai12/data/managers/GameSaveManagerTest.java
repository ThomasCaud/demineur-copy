package ai12.maven_ai12.data.managers;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.beans.Game;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSaveManagerTest {
	public static final String name = "gameNameForTesting";

	@Test
	public void testGetInstance() {
		GameSaveManager spm1 = GameSaveManager.getInstance();
		assert (spm1 != null);
		GameSaveManager spm2 = GameSaveManager.getInstance();
		assert (spm1 == spm2);
	}

	private Game getSavedGame() {
		Game game = new Game();
		game.setName(name);
		return game;
	}

	@Test
	public void testIsSerializable() {
		boolean success = false;
		try {
			new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(getSavedGame());
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert (success);
	}

	@Test
	public void testSaveWithoutErrorCaught() {
		boolean success = false;
		GameSaveManager manager = GameSaveManager.getInstance();
		try {
			manager.save(getSavedGame());
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		assert (success);
		// TODO: Delete the created file
	}

	@Test
	public void testFoldersAndFilesAreCreated() {
		GameSaveManager manager = GameSaveManager.getInstance();
		Game game = getSavedGame();
		String path = manager.save(game);

		File savesFolder = new File("Saves");
		assert (savesFolder.exists() && savesFolder.isDirectory());

		File dataFolder = new File(Config.PATH_DATA);
		assert (dataFolder.exists() && dataFolder.isDirectory());

		File dataGamesFolder = new File(Config.PATH_DATA_GAMES);
		assert (dataGamesFolder.exists() && dataGamesFolder.isDirectory());

		// Check data file have been created
		File dataFile = new File(path + ".ser");
		assert (dataFile.exists() && dataFile.isFile());

		// TODO: Delete the created file
	}

	@Test
	public void testGetGame() {
		GameSaveManager manager = GameSaveManager.getInstance();
		Game game = new Game();
		game.setName("gameName2");
		manager.save(game);

		boolean success = false;

		for (Game localGame : manager.getLocalGames()) {
			if (localGame.getIdGame().equals(game.getIdGame()) && localGame.getName().equals("gameName2")) {
				success = true;
			}
		}

		assert (success);
		// TODO : delete created file
	}
	
	@Test
	public void testGetGameNoExist() {
		GameSaveManager manager = GameSaveManager.getInstance();
		Game game = new Game();
		game.setName("gameName2");
		// We forget to save this game

		boolean success = false;

		for (Game localGame : manager.getLocalGames()) {
			if (localGame.getIdGame().equals(game.getIdGame()) && localGame.getName().equals("gameName2")) {
				success = true;
			}
		}

		assert (!success);
		// TODO : delete created file
	}
}
