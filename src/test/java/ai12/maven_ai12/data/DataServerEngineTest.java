package ai12.maven_ai12.data;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.UUID;

import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.beans.TupleForAbortGame;
import ai12.maven_ai12.data.exceptions.GameAlreadyCreatedException;
import ai12.maven_ai12.data.exceptions.PlayerAlreadyConnectedException;
import ai12.maven_ai12.data.exceptions.PlayerNotConnectedException;
import junit.framework.TestCase;

public class DataServerEngineTest extends TestCase {
	public DataServerEngineTest(String testName) {
		super(testName);
	}

	public void testAddNewUser() {
		DataServerEngine dataServerEngine = new DataServerEngine();
		assert (dataServerEngine.getPlayersInGames().size() == 0);

		SoftPlayer softPlayer1 = new SoftPlayer();
		boolean success = true;
		try {
			dataServerEngine.addNewUser(softPlayer1);
		} catch (PlayerAlreadyConnectedException e) {
			success = false;
		}
		assert (success == true);

		assert (dataServerEngine.getPlayersInGames().size() == 1);
		assert (dataServerEngine.getPlayersInGames().containsKey(softPlayer1.getIdPlayer()));

		Entry<SoftPlayer, UUID> user = dataServerEngine.getPlayersInGames().get(softPlayer1.getIdPlayer());
		assert (user.getValue() == DataServerEngine.notConnectedToAGame);
		assert (user.getKey().equals(softPlayer1));

		SoftPlayer softPlayer2 = new SoftPlayer();
		success = true;
		try {
			dataServerEngine.addNewUser(softPlayer2);
		} catch (PlayerAlreadyConnectedException e) {
			success = false;
		}
		assert (success == true);
		assert (dataServerEngine.getPlayersInGames().size() == 2);

		Entry<SoftPlayer, UUID> user2 = dataServerEngine.getPlayersInGames().get(softPlayer2.getIdPlayer());
		assert (user2.getValue() == DataServerEngine.notConnectedToAGame);
		assert (user2.getKey().equals(softPlayer2));

		success = true;
		try {
			dataServerEngine.addNewUser(softPlayer2);
		} catch (PlayerAlreadyConnectedException e) {
			success = false;
		}
		assert (success == false);
	}

	public void testDeleteUser() {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer1 = new SoftPlayer();
		try {
			dataServerEngine.addNewUser(softPlayer1);
		} catch (PlayerAlreadyConnectedException e) {
			e.printStackTrace();
		}
		assert (dataServerEngine.getPlayersInGames().size() == 1);

		boolean success = true;
		try {
			dataServerEngine.deleteUser(softPlayer1.getIdPlayer());
		} catch (PlayerNotConnectedException e) {
			success = false;
		}
		assert (success == true);

		assert (dataServerEngine.getPlayersInGames().size() == 0);
		assert (!dataServerEngine.getPlayersInGames().containsKey(softPlayer1.getIdPlayer()));

		success = true;
		try {
			dataServerEngine.deleteUser(softPlayer1.getIdPlayer());
		} catch (PlayerNotConnectedException e) {
			success = false;
		}
		assert (success == false);
	}

	public void testUpdateUserInGame() {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer = new SoftPlayer();
		SoftGame softGame = new SoftGame();

		try {
			dataServerEngine.addNewUser(softPlayer);
		} catch (PlayerAlreadyConnectedException e) {
			e.printStackTrace();
		}
		dataServerEngine.updateUserInGame(softPlayer.getIdPlayer(), softGame.getIdGame());

		assert (dataServerEngine.getPlayersInGames().size() == 1);
		assert (dataServerEngine.getPlayersInGames().containsKey(softPlayer.getIdPlayer()));

		Entry<SoftPlayer, UUID> user = dataServerEngine.getPlayersInGames().get(softPlayer.getIdPlayer());
		assert (user.getValue() == softGame.getIdGame());
		assert (user.getKey().equals(softPlayer));
	}

	public void testDeleteUserInGame() {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer = new SoftPlayer();
		SoftGame softGame = new SoftGame();

		try {
			dataServerEngine.addNewUser(softPlayer);
		} catch (PlayerAlreadyConnectedException e) {
			e.printStackTrace();
		}

		dataServerEngine.updateUserInGame(softPlayer.getIdPlayer(), softGame.getIdGame());
		dataServerEngine.deleteUserInGame(softPlayer.getIdPlayer());

		assert (dataServerEngine.getPlayersInGames().size() == 1);
		Entry<SoftPlayer, UUID> user = dataServerEngine.getPlayersInGames().get(softPlayer.getIdPlayer());
		assert (user.getValue() == DataServerEngine.notConnectedToAGame);
		assert (user.getKey().equals(softPlayer));
	}

	public void testAddNewGame() {
		DataServerEngine dataServerEngine = new DataServerEngine();
		assert (dataServerEngine.getGames().size() == 0);

		SoftGame softGame1 = new SoftGame();
		try {
			dataServerEngine.addNewGame(softGame1);
		} catch (GameAlreadyCreatedException e) {
			assert (false);
		}
		assert (dataServerEngine.getGames().size() == 1);
		assert (dataServerEngine.getGames().containsKey(softGame1.getIdGame()));

		Game game1 = dataServerEngine.getGames().get(softGame1.getIdGame());
		assert (game1.getIdGame().toString().equals(softGame1.getIdGame().toString()));

		SoftGame softGame2 = new SoftGame();
		try {
			dataServerEngine.addNewGame(softGame2);
		} catch (GameAlreadyCreatedException e) {
			assert (false);
		}
		assert (dataServerEngine.getGames().size() == 2);
		assert (dataServerEngine.getGames().containsKey(softGame2.getIdGame()));

		Game game2 = dataServerEngine.getGames().get(softGame2.getIdGame());
		assert (game2.getIdGame().toString().equals(softGame2.getIdGame().toString()));

		try {
			dataServerEngine.addNewGame(softGame2);
		} catch (GameAlreadyCreatedException e) {
			assert (true);
		}
	}

	public void testGetNext() {
		DataServerEngine dataServerEngine = new DataServerEngine();

		ArrayList<SoftPlayer> players = new ArrayList<SoftPlayer>();
		SoftPlayer p1 = new SoftPlayer();
		SoftPlayer p2 = new SoftPlayer();
		SoftPlayer p3 = new SoftPlayer();

		players.add(p1);
		assert (dataServerEngine.getNext(players, p1) == p1);

		players.add(p2);
		assert (dataServerEngine.getNext(players, p1) == p2);
		assert (dataServerEngine.getNext(players, p2) == p1);

		players.add(p3);
		assert (dataServerEngine.getNext(players, p1) == p2);
		assert (dataServerEngine.getNext(players, p2) == p3);
		assert (dataServerEngine.getNext(players, p3) == p1);

		players.remove(p3);
		assert (dataServerEngine.getNext(players, p1) == p2);
		assert (dataServerEngine.getNext(players, p2) == p1);
	}

	public void testAbortGameButNoPlayerInGame() throws PlayerAlreadyConnectedException, GameAlreadyCreatedException {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer1 = new SoftPlayer();
		dataServerEngine.addNewUser(softPlayer1);

		SoftGame softGame = new SoftGame();
		softGame.setCreator(softPlayer1);
		softGame.getPlayers().add(softPlayer1);

		dataServerEngine.addNewGame(softGame);
		dataServerEngine.updateUserInGame(softGame.getCreator().getIdPlayer(), softGame.getIdGame());

		try {
			TupleForAbortGame result = dataServerEngine
					.abortGame(dataServerEngine.getGames().get(softGame.getIdGame()));
			assert (result.getPlayersAlreadyInGame().size() == 1);
			assert (result.getOtherConnectedUsers().size() == 0);
			assert (result.getPlayersAlreadyInGame().containsKey(softPlayer1.getIdPlayer()));
		} catch (InternalError ie) {
			assert (false);
		}
	}

	public void testAbortGameButNoPlayerInGameAndOneOtherUser()
			throws PlayerAlreadyConnectedException, GameAlreadyCreatedException {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer1 = new SoftPlayer();
		SoftPlayer softPlayer2 = new SoftPlayer();
		dataServerEngine.addNewUser(softPlayer1);
		dataServerEngine.addNewUser(softPlayer2);

		SoftGame softGame = new SoftGame();
		softGame.setCreator(softPlayer1);
		softGame.getPlayers().add(softPlayer1);

		dataServerEngine.addNewGame(softGame);
		dataServerEngine.updateUserInGame(softGame.getCreator().getIdPlayer(), softGame.getIdGame());

		try {
			TupleForAbortGame result = dataServerEngine
					.abortGame(dataServerEngine.getGames().get(softGame.getIdGame()));
			assert (result.getPlayersAlreadyInGame().size() == 1);
			assert (result.getPlayersAlreadyInGame().containsKey(softPlayer1.getIdPlayer()));
			assert (result.getOtherConnectedUsers().size() == 1);
			assert (result.getOtherConnectedUsers().containsKey(softPlayer2.getIdPlayer()));
		} catch (InternalError ie) {
			assert (false);
		}
	}

	public void testAbortGameOnePlayerInGameAndOneOtherUser()
			throws PlayerAlreadyConnectedException, GameAlreadyCreatedException {
		DataServerEngine dataServerEngine = new DataServerEngine();

		SoftPlayer softPlayer1 = new SoftPlayer();
		SoftPlayer softPlayer2 = new SoftPlayer();
		SoftPlayer softPlayer3 = new SoftPlayer();
		dataServerEngine.addNewUser(softPlayer1);
		dataServerEngine.addNewUser(softPlayer2);
		dataServerEngine.addNewUser(softPlayer3);

		SoftGame softGame = new SoftGame();
		softGame.setCreator(softPlayer1);
		softGame.getPlayers().add(softPlayer1);
		softGame.getPlayers().add(softPlayer2);

		dataServerEngine.addNewGame(softGame);
		dataServerEngine.updateUserInGame(softPlayer1.getIdPlayer(), softGame.getIdGame());
		dataServerEngine.updateUserInGame(softPlayer2.getIdPlayer(), softGame.getIdGame());

		try {
			TupleForAbortGame result = dataServerEngine
					.abortGame(dataServerEngine.getGames().get(softGame.getIdGame()));
			assert (result.getPlayersAlreadyInGame().size() == 2);
			assert (result.getPlayersAlreadyInGame().containsKey(softPlayer1.getIdPlayer()));
			assert (result.getPlayersAlreadyInGame().containsKey(softPlayer2.getIdPlayer()));
			assert (result.getOtherConnectedUsers().size() == 1);
			assert (result.getOtherConnectedUsers().containsKey(softPlayer3.getIdPlayer()));
		} catch (InternalError ie) {
			assert (false);
		}
	}
}
