package ai12.maven_ai12.data.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.beans.Game;

public class GameSaveManager {
	private static GameSaveManager INSTANCE = null;
	private static Logger logger = Logger.getLogger(GameSaveManager.class);

	public static GameSaveManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GameSaveManager();
		}
		return INSTANCE;
	}

	public String save(Game game) {
		logger.info("Game save launched");
		createSavesFolders(Config.PATH_DATA_GAMES);
		logger.info("Saving folder created");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		createSerializedFile(Config.PATH_DATA_GAMES + game.getName() + "-" + format.format(game.getCreatedAt()), game);
		logger.info("Game saved");
		return Config.PATH_DATA_GAMES + game.getName() + "-" + format.format(game.getCreatedAt());
	}

	private void createSavesFolders(String pDataDirectoryName) {
		try {
			// Create Saves, Data directories if needed
			File vDirectory = new File(pDataDirectoryName);
			if (!vDirectory.exists()) {
				vDirectory.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSerializedFile(String pFilePath, Game game) {
		File vSerFilePath = new File(pFilePath + ".ser");
		try {
			// Create serialized file
			ObjectOutputStream vOos = new ObjectOutputStream(new FileOutputStream(vSerFilePath));
			// Insert serialized game object in .ser file
			vOos.writeObject(game);
			vOos.flush();
			vOos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Game> getLocalGames() {
		ArrayList<Game> result = new ArrayList<Game>();
		File vGamesDirectory = new File(Config.PATH_DATA_GAMES);

		if (vGamesDirectory.exists() && vGamesDirectory.isDirectory()) {
			File[] vGamesFiles = vGamesDirectory.listFiles();

			for (File vGamesFile : vGamesFiles)
				if (!vGamesFile.getName().equals(".DS_Store"))
					result.add(this.createGameFromSerializedFile(vGamesFile.getPath()));
		}
		return result;
	}
	
	private Game createGameFromSerializedFile(String pFilePath) throws InternalError {
		try {
			ObjectInputStream vOis;
			vOis = new ObjectInputStream(new FileInputStream(pFilePath));
			Game game = (Game) vOis.readObject();
			vOis.close();
			return game;
		} catch (IOException | ClassNotFoundException e) {
			throw new InternalError(e.getMessage());
		}
	}
}
