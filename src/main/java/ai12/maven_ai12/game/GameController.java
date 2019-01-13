package ai12.maven_ai12.game;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import ai12.maven_ai12.beans.Action;
import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.Grid;
import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForGame;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author lsachs
 *
 */
public class GameController implements Initializable {

	@FXML
	private StackPane GamePane;

	@FXML
	private Button leaveButton;

	@FXML
	private TextArea messageListInput;

	@FXML
	private TextArea messageInput;

	@FXML
	private Text timerText;

	@FXML
	private Button sendButton;

	@FXML
	private GridPane usersList;

	@FXML
	private ScrollPane observersList;
	// private ListView<SoftPlayer> observersList;

	@FXML
	private ScrollPane playersList;
	// private ListView<SoftPlayer> playersList;

	@FXML
	private AnchorPane userProfile;

	@FXML
	private Button closeProfileButton;

	@FXML
	private Text profilePseudo;

	@FXML
	private Text profileFirstname;

	@FXML
	private Text profileName;

	@FXML
	private Text profileAge;

	@FXML
	private Text profileNbGamePlayed;

	@FXML
	private Text profileNbGameWon;

	@FXML
	private Text currentPlayerName;

	@FXML
	private AnchorPane abandonPane;

	@FXML
	private AnchorPane endGamePane;

	@FXML
	private Text playerResult;

	@FXML
	private Text winnersList;

	@FXML
	private ImageView previousIcon;

	@FXML
	private ImageView pauseIcon;

	@FXML
	private ImageView nextIcon;

	@FXML
	private GridPane gameBand;

	@FXML
	private GridPane replayBand;

	@FXML
	private Text currentPlayerNameReplay;

	@FXML
	private Button leaveButtonReplay;

	private GameClientEngine mManager;

	private Game mGame;

	private GameGrid mGameGrid;

	private SoftPlayerList mObserversList;

	private SoftPlayerList mPlayersList;

	private ObservableList<SoftPlayer> mDeadPlayers;

	private ObservableList<SoftPlayer> mPlayersFlagVisible;

	private InsideGameForDataCliImpl mInsideGameForDataCliImpl;

	private IInsideDataCliForGame mIInsideDataCliForGame;

	private Timeline mTimer;

	private boolean mCurrentPlayerTurn = false;

	private int mTimerSeconds;

	public void timerStart() {
		this.mTimer.play();
	}

	public void timerStop() {
		this.mTimer.stop();
	}

	public void timerOut() {
		this.mIInsideDataCliForGame.timerExpired();
		this.timerStop();
	}

	public void resetTimer() {
		int vNbMinTimer = this.mGame.getGameParameters().getTime() / 60;
		int vNbSecTimer = this.mGame.getGameParameters().getTime() % 60;
		this.timerText.setText(String.format("%02d", vNbMinTimer) + ":" + String.format("%02d", vNbSecTimer));
		this.mTimerSeconds = 0;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		sendButton.setOnAction(this::handleSendMessage);

		/*
		 * observersList.getItems().addListener((ListChangeListener<SoftPlayer>) change
		 * -> { while (change.next()) { if (change.wasRemoved()) { //
		 * tilePane.getChildren().clear(); // observersList.getItems().remove(index); }
		 * if (change.wasAdded()) { // tilePane.getChildren().add(new
		 * CardShape(change.getElementAdded())); } } });
		 * 
		 * playersList.getItems().addListener((ListChangeListener<SoftPlayer>) change ->
		 * { while (change.next()) { if (change.wasRemoved()) { //
		 * tilePane.getChildren().clear(); // observersList.getItems().remove(index); }
		 * if (change.wasAdded()) { // tilePane.getChildren().add(new
		 * CardShape(change.getElementAdded())); } } });
		 */
		leaveButton.setOnAction(this::leaveAGame);
		leaveButtonReplay.setOnAction(this::leaveAGame);

		previousIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Previous !");
				// TODO : Previous shot
			}
		});

		pauseIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Pause !");
				// TODO : Pause
			}
		});

		nextIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Next !");
				// TODO : Next shot
			}
		});

		// Tests d'affichage
		// gameBand.setVisible(false);
		// replayBand.setVisible(true);

	}

	public GameController() {
	}

	public void saveAGame(Game pGame) {

		try {
			mIInsideDataCliForGame.saveGame();
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  saveAGame(Game pGame)");
		}
	}

	public void leaveAGame(ActionEvent event) {
		GamePane.getChildren().get(2).setVisible(false); // Hide grid
		abandonPane.setVisible(true); // Show Abandon message
	}

	@FXML
	public void cancelAbandon(ActionEvent event) {
		abandonPane.setVisible(false); // Hide abandon message
		GamePane.getChildren().get(2).setVisible(true); // Show grid
	}

	@FXML
	public void confirmAbandon(ActionEvent event) {
		try {
			mIInsideDataCliForGame.forfeitGame();
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  startAGame(Game pGame)");
		}

		// this.exitApplication();
	}

	/*
	 * @FXML public void displayProfile(MouseEvent event) { ListView<SoftPlayer>
	 * listClicked = (ListView<SoftPlayer>) event.getSource();
	 * 
	 * SoftPlayer player = (SoftPlayer)
	 * listClicked.getSelectionModel().getSelectedItem(); // TODO : Uncomment next
	 * line to Get playerProfile from Data // PlayerProfile profile = //
	 * mIInsideDataCliForGame.getPlayerProfile(player.getIdPlayer());
	 * 
	 * // TODO : Remove next line used only for test PlayerProfile profile = new
	 * PlayerProfile(); profilePseudo.setText(player.getLogin());
	 * profileFirstname.setText(profile.getFirstname());
	 * profileName.setText(profile.getLastname()); LocalDate currentDate =
	 * LocalDate.now(); LocalDate birthday =
	 * profile.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).
	 * toLocalDate(); Period p = Period.between(birthday, currentDate);
	 * profileAge.setText(Integer.toString(p.getYears()));
	 * profileNbGamePlayed.setText(Integer.toString(profile.getNbPlay()));
	 * profileNbGameWon.setText(Integer.toString(profile.getNbWin()));
	 * usersList.setVisible(false); userProfile.setVisible(true);
	 * System.out.println("clicked on " + player.getIdPlayer()); }
	 */

	@FXML
	public void closeProfile(ActionEvent event) {
		userProfile.setVisible(false);
		usersList.setVisible(true);
	}

	@FXML
	public void saveAndCloseGame(ActionEvent event) {
		try {
			mIInsideDataCliForGame.saveGame();
			mIInsideDataCliForGame.quitGame();
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  saveAGame(Game pGame)");
		}
	}

	@FXML
	public void closeGame(ActionEvent event) {
		mIInsideDataCliForGame.quitGame();
	}

	@FXML
	public void exitApplication() {
		Platform.exit();
	}

	public Game getmGame() {
		return mGame;
	}

	public void createGameGrid() {
		SoftGame vSoftGame = (SoftGame) this.mGame;
		int vCol = vSoftGame.getGameParameters().getNbCol();
		int vRow = vSoftGame.getGameParameters().getNbRow();

		if (vCol == 0 || vRow == 0) {
			vCol = GameGrid.SIZE_BIG_COL;
			vRow = GameGrid.SIZE_BIG_ROW;
		}

		HashMap<Coordinates, Box> vBoxes = new HashMap<Coordinates, Box>();
		Box vBox;
		for (int tRow = 0; tRow < vRow; tRow++) {
			for (int tCol = 0; tCol < vCol; tCol++) {
				vBox = new Box();
				vBox.setCoordinates(new Coordinates(tCol, tRow));
				vBox.setVisible(false);
				vBoxes.put(new Coordinates(tCol, tRow), vBox);
			}
		}

		// Instantiating a grid, then adding it to GamePane
		Grid vGrid = new Grid(vRow, vCol, vBoxes);
		this.mGameGrid = new GameGrid(vGrid);
		this.mGameGrid.getStyleClass().add("GameGrid");
		this.mGameGrid.setGameController(this);
		GamePane.getChildren().add(this.mGameGrid);
	}

	public void createObserversList() {
		this.mObserversList = new SoftPlayerList(mGame.getViewers(), this, false);
		// this.mObserversList.setGameController(this);
		this.observersList.setContent(this.mObserversList);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				mObserversList.getChildren().forEach(child -> {
					child.setOnMouseClicked(event -> {
						SoftPlayer player = ((SoftPlayerListElement) child).getmElement();
						PlayerProfile profile = new PlayerProfile();
						profilePseudo.setText(player.getLogin());
						profileFirstname.setText(profile.getFirstname());
						profileName.setText(profile.getLastname());
						LocalDate currentDate = LocalDate.now();
						LocalDate birthday = profile.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate();
						Period p = Period.between(birthday, currentDate);
						profileAge.setText(Integer.toString(p.getYears()));
						profileNbGamePlayed.setText(Integer.toString(profile.getNbPlay()));
						profileNbGameWon.setText(Integer.toString(profile.getNbWin()));
						usersList.setVisible(false);
						userProfile.setVisible(true);
						System.out.println("clicked on " + player.getIdPlayer());
					});
				});
			}
		});

		this.observersList.setContent(this.mObserversList);
	}

	public void createPlayersList() {
		this.mPlayersList = new SoftPlayerList(mGame.getPlayers(), this, true);
		this.playersList.setContent(this.mPlayersList);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				mPlayersList.getChildren().forEach(child -> {
					child.setOnMouseClicked(event -> {
						SoftPlayer player = ((SoftPlayerListElement) child).getmElement();
						PlayerProfile profile = new PlayerProfile();
						profilePseudo.setText(player.getLogin());
						profileFirstname.setText(profile.getFirstname());
						profileName.setText(profile.getLastname());
						LocalDate currentDate = LocalDate.now();
						LocalDate birthday = profile.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault())
								.toLocalDate();
						Period p = Period.between(birthday, currentDate);
						profileAge.setText(Integer.toString(p.getYears()));
						profileNbGamePlayed.setText(Integer.toString(profile.getNbPlay()));
						profileNbGameWon.setText(Integer.toString(profile.getNbWin()));
						usersList.setVisible(false);
						userProfile.setVisible(true);
						System.out.println("clicked on " + player.getIdPlayer());
					});
				});
			}
		});

	}

	public void setmGame(Game mGame) {
		this.mGame = mGame;
		// gameController already initialized
		/*
		 * observersList.getItems().addAll(mGame.getViewers());
		 * observersList.setCellFactory(new SoftPlayerCellFactory());
		 * playersList.getItems().addAll(mGame.getPlayers());
		 * playersList.setCellFactory(new SoftPlayerCellFactory());
		 */
		// TODO
		// this.resetTimer();
		this.mPlayersFlagVisible = FXCollections.observableArrayList(new ArrayList<SoftPlayer>());
		System.out.println(mGame.getLosers());
		if (mGame.getLosers() != null) {
			// System.out.println("coucou");
			this.mDeadPlayers = FXCollections.observableArrayList(mGame.getLosers());
		} else {
			// System.out.println("tata");
			this.mDeadPlayers = FXCollections.observableArrayList(new ArrayList<SoftPlayer>());
		}
		System.out.println(mDeadPlayers);
		/*
		 * mDeadPlayers.addListener((ListChangeListener<SoftPlayer>)change -> { while
		 * (change.next()) { if (change.wasRemoved()) { } if (change.wasAdded()) {
		 * System.out.println("aled?"); } } });
		 */
		this.createGameGrid();

		this.createObserversList();
		this.createPlayersList();

		// this.mDeadPlayers = FXCollections.observableList(new
		// ArrayList<SoftPlayer>());
		/*
		 * this.mDeadPlayers.addListener((ListChangeListener<SoftPlayer>)change -> {
		 * while (change.next()) { if (change.wasRemoved()) { // TODO } if
		 * (change.wasAdded()) { for(SoftPlayer current : change.getAddedSubList()){
		 * this.mPlayersList.getChildren().forEach(child -> { if
		 * (((SoftPlayerListElement) child).getmElement().equals(current)){
		 * Platform.runLater(new Runnable() {
		 * 
		 * @Override public void run() {
		 * ((SoftPlayerListElement)child).getChildren().add(new Text("dead"));
		 * //vTile.getChildren().addAll(vTile.mText); } });
		 * 
		 * } }); } } } });
		 */

		this.mTimer = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
			this.mTimerSeconds++;
			int vTimerDiff = this.mGame.getGameParameters().getTime() - this.mTimerSeconds;
			int vNbMinTimer = vTimerDiff / 60;
			int vNbSecTimer = vTimerDiff % 60;
			this.timerText.setText(String.format("%02d", vNbMinTimer) + ":" + String.format("%02d", vNbSecTimer));
			if (vTimerDiff == 0) {
				this.timerOut();
			}
		}));

		mTimer.setCycleCount(Animation.INDEFINITE);
		this.mTimer.play();

	}

	/**
	 * Display current player infos in game window
	 * 
	 * @param pSoftPlayer
	 */
	public void displayNextTurn(SoftPlayer pSoftPlayer) {
		if (this.mIInsideDataCliForGame.getLocalPlayer().getIdPlayer().toString()
				.equals(pSoftPlayer.getIdPlayer().toString())) {
			this.displayOwnTurn();
		} else {
			currentPlayerName.setText("Current player : " + pSoftPlayer.getLogin());
			currentPlayerNameReplay.setText("Current player : " + pSoftPlayer.getLogin());
			this.printInChat("It is " + pSoftPlayer.getLogin() + "'s turn.\n");
			this.resetTimer();
			this.timerStart();
			this.mCurrentPlayerTurn = false;
		}

	}

	/**
	 * Display current player (self) infos in game window
	 */
	public void displayOwnTurn() {
		currentPlayerName
				.setText("Current player : You (" + this.mIInsideDataCliForGame.getLocalPlayer().getLogin() + ")");
		currentPlayerNameReplay
				.setText("Current player : You (" + this.mIInsideDataCliForGame.getLocalPlayer().getLogin() + ")");
		this.printInChat("It is your turn.\n");
		this.resetTimer();
		this.timerStart();
		this.mCurrentPlayerTurn = true;
	}

	public boolean isCurrentPlayerTurn() {
		return this.mCurrentPlayerTurn;
	}

	public InsideGameForDataCliImpl getmInsideGameForDataCliImpl() {
		return mInsideGameForDataCliImpl;
	}

	public void setmInsideGameForDataCliImpl(InsideGameForDataCliImpl mInsideGameForDataCliImpl) {
		this.mInsideGameForDataCliImpl = mInsideGameForDataCliImpl;
	}

	public IInsideDataCliForGame getIInsideDataCliForGame() {
		return mIInsideDataCliForGame;
	}

	public void setIInsideDataCliForGame(IInsideDataCliForGame mIInsideDataCliForGame) {
		this.mIInsideDataCliForGame = mIInsideDataCliForGame;
	}

	private void handleSendMessage(ActionEvent event) {

		try {
			mIInsideDataCliForGame.sendMessage(messageInput.getText());
			messageInput.clear();
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  handleSendMessage(ActionEvent event)");
		}
	}

	public void printInChat(String pString) {
		messageListInput.appendText(pString);
	}

	public void newAction(Shot pShot) {
		if (pShot.getAction() == Action.FLAG) {
			this.addFlag(pShot);
		} else if (pShot.getAction() == Action.ACTION) {
			this.uncoverTiles(pShot);
		}
	}

	private void addFlag(Shot pShot) {
		this.mGameGrid.setBoxTile(pShot.getBox());
	}

	private void uncoverTiles(Shot pShot) {
		this.mGameGrid.setBoxTile(pShot.getBox());
	}

	public void uncoverTiles(Box pBox) {
		this.mGameGrid.setBoxTile(pBox);
	}

	public void toggleFlag(Shot pShot) {
		try {
			mIInsideDataCliForGame.toggleFlag(pShot);
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  addFlag(Shot pShot)");
		}
	}

	public void playNewShot(Coordinates pCoordinates) {
		try {
			mIInsideDataCliForGame.playShot(pCoordinates);
		} catch (NullPointerException e) {
			System.err.println("ERROR : mIInsideDataCliForGame est null, pensez à récupérer l'interface de DATA");
			System.err.println("see class GameController -->  uncoverTiles(Coordinates pCoordinates)");
		}
	}

	public IInsideGameForDataCli getIInsideGameForDataCli() {
		return mInsideGameForDataCliImpl;
	}

	public void setManager(GameClientEngine pGameClientEngine) {
		this.mManager = pGameClientEngine;
	}

	public void endAGame(ArrayList<SoftPlayer> pWinners) {
		leaveButton.setDisable(true);
		if (pWinners.contains(this.mIInsideDataCliForGame.getLocalPlayer())) {
			playerResult.setText("You win !");
		} else {
			playerResult.setText("You loose !");
		}

		for (int i = 0; i < pWinners.size(); i++) {
			winnersList.setText(winnersList.getText() + pWinners.get(i).getLogin() + '\n');
		}

		GamePane.getChildren().get(2).setVisible(false); // Hide grid
		endGamePane.setVisible(true);
	}

	public ObservableList<SoftPlayer> getmDeadPlayers() {
		return mDeadPlayers;
	}

	public SoftPlayerList getmObserversList() {
		return mObserversList;
	}

	public SoftPlayerList getmPlayersList() {
		return mPlayersList;
	}

	public ObservableList<SoftPlayer> getmPlayersFlagVisible() {
		return mPlayersFlagVisible;
	}

	public void setmPlayersFlagVisible(ObservableList<SoftPlayer> mPlayersFlagVisible) {
		this.mPlayersFlagVisible = mPlayersFlagVisible;
	}
}
