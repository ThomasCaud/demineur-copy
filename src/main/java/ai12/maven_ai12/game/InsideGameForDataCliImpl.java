package ai12.maven_ai12.game;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import ai12.maven_ai12.beans.Action;
import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Message;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForGame;
import javafx.stage.Stage;

public class InsideGameForDataCliImpl implements IInsideGameForDataCli {
	/**
	 * variables de test
	 */
	private boolean tTurn = true;

	private IInsideDataCliForGame mIInsideDataCliForGame;
	private GameClientEngine mGameManager;

	/*
	 * public InsideGameForDataCliImpl(GameController pGameController) {
	 * this.mGameController = pGameController; }
	 */
	public InsideGameForDataCliImpl(GameClientEngine pGameManager) {
		this.mGameManager = pGameManager;
	}

	public void testAllMethods() {
		SoftPlayer tSoftPlayer = new SoftPlayer();
		tSoftPlayer.setLogin("Stanley");

		Message tMessage = new Message();
		tMessage.setAuthor(tSoftPlayer);
		tMessage.setTimestamp(new Timestamp(new Date().getTime()));
		tMessage.setText("Bonjour Yassine, comment vas tu ?");
		ArrayList<SoftPlayer> vFlags = new ArrayList<SoftPlayer>();
		vFlags.add(tSoftPlayer);

		Box tBox = new Box();
		tBox.setCoordinates(new Coordinates(2, 2));
		tBox.setVisible(false);
		tBox.setFlags(vFlags);

		Shot tShot = new Shot();
		tShot.setAction(Action.FLAG);
		tShot.setBox(tBox);
		tShot.setPlayer(tSoftPlayer);

		this.notifyNewFlag(tShot);

		Box tBox2 = new Box();
		tBox2.setValue(-1);
		tBox2.setCoordinates(new Coordinates(2, 1));
		tBox2.setVisible(true);

		Shot tShot2 = new Shot();
		tShot2.setAction(Action.ACTION);
		tShot2.setBox(tBox2);
		tShot2.setPlayer(tSoftPlayer);

		this.notifyUncoverBomb(tShot2);

		Box tBox3 = new Box();
		tBox3.setValue(3);
		tBox3.setCoordinates(new Coordinates(1, 1));
		tBox3.setVisible(true);

		Shot tShot3 = new Shot();
		tShot3.setAction(Action.ACTION);
		tShot3.setBox(tBox3);
		tShot3.setPlayer(tSoftPlayer);

		this.notifyNewShot(tShot3);

		this.notifyObserverJoin(tSoftPlayer);
		this.notifyObserverLeft(tSoftPlayer);

		this.notifyPlayerForfeit(tSoftPlayer);
		this.notifyNewMessage(tMessage);
		this.notifyNextTurn(tSoftPlayer);
		this.notifyYourTurn();
		this.notifyGameOver(tSoftPlayer);
		this.notifyTimerExpired(tSoftPlayer);
		this.notifyObserverLeft(tSoftPlayer);
		ArrayList<SoftPlayer> a = new ArrayList<SoftPlayer>();
		a.add(tSoftPlayer);
		this.notifyGameEnd(a);

		if (tTurn)

		{
			this.notifyNextTurn(tSoftPlayer);
			this.tTurn = false;
		} else {
			this.notifyYourTurn();
			this.tTurn = true;
		}

		this.notifyGameOver(tSoftPlayer);
		this.notifyTimerExpired(tSoftPlayer);
		this.notifyObserverLeft(tSoftPlayer);
	}

	@Override
	public void notifyObserverJoin(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController()
				.printInChat("Notification : " + pSoftPlayer.getLogin() + " join the game as observer.\n");
	}

	@Override
	public void notifyGameStart(Game pGame, Stage pStage) {
		mGameManager.startAGame(pGame, pStage);
	}

	@Override
	public void notifyGameEnd(ArrayList<SoftPlayer> pSoftPlayers) {
		mGameManager.getGameController().endAGame(pSoftPlayers);
	}

	@Override
	public void notifyPlayerForfeit(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController().printInChat("Notification : " + pSoftPlayer.getLogin() + " forfeited.\n");
	}

	@Override
	public void notifyNewMessage(Message pMessage) {
		mGameManager.getGameController()
				.printInChat(pMessage.getAuthor().getLogin() + " : " + pMessage.getText() + "\n");
	}

	@Override
	public void notifyNextTurn(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController().displayNextTurn(pSoftPlayer);
	}

	@Override
	public void notifyYourTurn() {
		mGameManager.getGameController().displayOwnTurn();
	}

	@Override
	public void notifyNewShot(Shot pShot) {
		mGameManager.getGameController().newAction(pShot);
	}

	@Override
	public void notifyNewFlag(Shot pShot) {
		mGameManager.getGameController().newAction(pShot);
	}

	@Override
	public void notifyUncoverBomb(Shot pShot) {
		mGameManager.getGameController().newAction(pShot);
	}

	@Override
	public void notifyGameOver(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController().printInChat("Notification: " + pSoftPlayer.getLogin() + " lost the game.\n");
	}

	@Override
	public void notifyUncoverSquares(Box pBox) {
		mGameManager.getGameController().uncoverTiles(pBox);
	}

	@Override
	public void notifyUpdateChat() {
	}

	@Override
	public void notifyTimerExpired(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController()
				.printInChat("Notification: Timer expired, " + pSoftPlayer.getLogin() + " lost the game.\n");
	}

	@Override
	public void notifyObserverLeft(SoftPlayer pSoftPlayer) {
		mGameManager.getGameController()
				.printInChat("Notification: Observer " + pSoftPlayer.getLogin() + " left the game.\n");
	}

	@Override
	public void replayGame(Game pGame) {
	}

	public IInsideDataCliForGame getmIInsideDataCliForGame() {
		return mIInsideDataCliForGame;
	}

	public void setmIInsideDataCliForGame(IInsideDataCliForGame mIInsideDataCliForGame) {
		this.mIInsideDataCliForGame = mIInsideDataCliForGame;
	}

	public GameController getmGameController() {
		return mGameManager.getGameController();
	}

	public void setmGameManager(GameClientEngine pGameManager) {
		this.mGameManager = pGameManager;
	}
}
