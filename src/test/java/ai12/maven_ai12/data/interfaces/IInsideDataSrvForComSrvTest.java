package ai12.maven_ai12.data.interfaces;

import java.util.List;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.DataServerEngine;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;
import junit.framework.TestCase;

public class IInsideDataSrvForComSrvTest extends TestCase {
	private IInsideDataSrvForComSrv mInterface;
	private DataServerEngine mEngine;

	public IInsideDataSrvForComSrvTest(String testName) {
		super(testName);
		this.mEngine = new DataServerEngine();
		this.mInterface = new InsideDataSrvForComSrvImpl(mEngine);
	}

	public void testGetCurrentPlayersWithNoCurrentPlayer() {
		List<SoftPlayer> currentPlayers = this.mInterface.getCurrentPlayers();
		assert (currentPlayers.size() == 0);
	}

	public void testGetCurrentPlayersWithOneCurrentPlayer() {
		SoftPlayer player = new SoftPlayer();
		player.setLogin("testGetCurrentPlayersWithOneCurrentPlayer");

		try {
			mEngine.addNewUser(player);
		} catch (PlayerAlreadyConnectedException e) {
			assert (false);
		}

		List<SoftPlayer> currentPlayers = this.mInterface.getCurrentPlayers();
		assert (currentPlayers.size() == 1);
		assert (currentPlayers.get(0).getLogin().equals("testGetCurrentPlayersWithOneCurrentPlayer"));
	}

	public void testGetCurrentPlayersWithTwoCurrentPlayers() {
		SoftPlayer player1 = new SoftPlayer();
		player1.setLogin("testGetCurrentPlayersWithTwoCurrentPlayers_1");
		SoftPlayer player2 = new SoftPlayer();
		player2.setLogin("testGetCurrentPlayersWithTwoCurrentPlayers_2");

		try {
			mEngine.addNewUser(player1);
			mEngine.addNewUser(player2);
		} catch (PlayerAlreadyConnectedException e) {
			assert (false);
		}

		List<SoftPlayer> currentPlayers = mInterface.getCurrentPlayers();
		assert (currentPlayers.size() == 2);
	}

	public void testGetCurrentGamesWithNoCurrentGames() {
		List<SoftGame> currentGames = this.mInterface.getCurrentGames();
		assert (currentGames.size() == 0);
	}

	public void testGetCurrentGamesWithOneCurrentGame() {
		SoftGame game = new SoftGame();
		game.setName("testGetCurrentGamesWithOneCurrentGame");

		try {
			mEngine.addNewGame(game);
		} catch (GameAlreadyCreatedException e) {
			assert (false);
		}

		List<SoftGame> currentGames = this.mInterface.getCurrentGames();
		assert (currentGames.size() == 1);
		assert (currentGames.get(0).getName().equals("testGetCurrentGamesWithOneCurrentGame"));
	}

	public void testGetCurrentGamesWithTwoCurrentGames() {
		SoftGame game1 = new SoftGame();
		game1.setName("testGetCurrentGamesWithOneCurrentGame_1");
		SoftGame game2 = new SoftGame();
		game2.setName("testGetCurrentGamesWithOneCurrentGame_2");

		try {
			mEngine.addNewGame(game1);
			mEngine.addNewGame(game2);
		} catch (GameAlreadyCreatedException e) {
			assert (false);
		}

		List<SoftGame> currentGames = mInterface.getCurrentGames();
		assert (currentGames.size() == 2);
	}
}