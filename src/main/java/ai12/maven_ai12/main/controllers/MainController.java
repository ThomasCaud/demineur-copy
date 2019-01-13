package ai12.maven_ai12.main.controllers;

import ai12.maven_ai12.beans.*;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain;
import ai12.maven_ai12.main.MainClientEngine;
import ai12.maven_ai12.main.SceneEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainController {

	private enum SwitchValue { ON, OFF, VICE_VERSA}

	private MainClientEngine mMainClientEngine;

	private ObservableList<SoftPlayer> mCurrentUsers;

	private ObservableList<SoftGame> mCurrentGames;

	// Play tab - Main section

	@FXML
	private BorderPane mPlayBorderPane;

	@FXML
	private BorderPane mDisconnectBorderPane;

	@FXML
	private Text mDisconnectedText;

	// Play tab – Main section – Left

	@FXML
	private ListView<SoftGame> mGamesListView;

	@FXML
	private Button mJoinAsViewerButton, mJoinAsPlayerButton;

	@FXML
	private Pane mGameInfoPane;

	@FXML
    private Pane mInfoDeniedPane;

	@FXML
	private Text mGameInfoNameText, mGameInfoNumberOfPlayersText, mGameInfoTimeText, mGameInfoViewersText,
			mGameInfoDifficultyText;

	// Play tab – Main section – Right

	@FXML
	private ListView<SoftPlayer> mConnectedUsersListView;

	@FXML
	private Button mCreateGameButton;

	// Profile tab – Main section – Own profile

	@FXML
	private Pane mOwnProfilePane;

	@FXML
	private Label mOwnProfileLoginLabel, mOwnProfileGamePlayedLabel, mOwnProfileGameWinLabel,
			mOwnProfileGameForfeitLabel, mOwnProfileGameDefeatLabel, mOwnProfileNamesLabel, mOwnProfileAgeLabel;

	@FXML
	private Button mOwnProfileUpateProfileButton;

	// Play tab – Overall section – Background

	@FXML
	private Pane mBackgroundPane;

	// Play tab – Overall section – Create Game

	@FXML
	private Pane mCreateGamePane;

	@FXML
	private TextField mGameNameTextField, mPlayersNumberTextField, mShotTimeTextField;

	@FXML
	private ToggleGroup difficultyGroup;

	@FXML
	private Label mDifficultyInfoLabel;

	@FXML
	private CheckBox mAllowViewersCheckbox;

	@FXML
	private Button mStartGameButton;

	@FXML
	private Text mCreateGameErrorText;

	// Play tab – Overall section – User profil

	@FXML
	private Pane mUserProfilPane;

	@FXML
	private Label mUserLoginLabel, mGamePlayedLabel, mGameWinLabel, mGameDefeatLabel, mGameForfeitLabel, mNamesLabel,
			mAgeLabel;

	// Profile tab - Overall section - Update profile

	// Play tab - Overall section - Waiting
	@FXML
	private Pane mUpdateProfilePane;

	@FXML
	private Label mUpdateProfileUsernameLabel;
	@FXML
	private Pane mWaitingEntryGamePane;

	@FXML
	private PasswordField mUpdateProfileOldPasswordField, mUpdateProfileNewPasswordField,
			mUpdateProfileRepeatPasswordField;

	// Play tab – Overall section – User profil

	@FXML
	private TextField mUpdateProfileLastNameField, mUpdateProfileFirstNameField;

	@FXML
	private DatePicker mUpdateProfileDateOfBirthPicker;

	@FXML
	private Button mUpdateProfileUpdateButton;

	// Bottom section – Left

	@FXML
	private Button mLogOutButton;

	@FXML
	private Label mLogInLabel;

	// Bottom section – Connection

	@FXML
	private FlowPane mDisconnectedFlowPane;

	@FXML
	private TextField mIpTextField, mPortTextField;

	@FXML
	private Button mConnectButton;

	// Bottom section – Disconnection

	@FXML
	private FlowPane mConnectedFlowPane;

	@FXML
	private Label mIpPortConnectedLabel;

	@FXML
	private Button mDisconnectButton;

	public void initialize() {
		this.mMainClientEngine = MainClientEngine.getMainClientEngine(null);

		initializeCurrentUsers();
		initializeCurrentGames();
		initializeLabels();
		initializeButtonActions();
		initializeCreateGame();
		initializeProfileTab();
		initializeSwitches();
	}

	private void initializeUpdateProfilePane() {
		PlayerProfile vOwnProfile = mMainClientEngine.getInsideDataCliForMain().getOwnPlayerProfile();
		mUpdateProfileUsernameLabel.setText(vOwnProfile.getLogin());
		mUpdateProfileFirstNameField.setText(vOwnProfile.getFirstname());
		mUpdateProfileLastNameField.setText(vOwnProfile.getLastname());
		// TODO
		// mUpdateProfileDateOfBirthPicker.setValue(vOwnProfile.getDateOfBirth().toLocalDate(0));
	}

	private void initializeProfileTab() {
		PlayerProfile vOwnProfile = mMainClientEngine.getInsideDataCliForMain().getOwnPlayerProfile();
		mOwnProfileNamesLabel.setText(vOwnProfile.getFirstname() + " " + vOwnProfile.getLastname());
		mOwnProfileAgeLabel.setText(vOwnProfile.getDateOfBirth().toString());
		mOwnProfileGamePlayedLabel.setText(Integer.toString(vOwnProfile.getNbPlay()));
		mOwnProfileGameWinLabel.setText(Integer.toString(vOwnProfile.getNbWin()));
		mOwnProfileGameForfeitLabel.setText(Integer.toString(vOwnProfile.getNbForfeit()));
		mOwnProfileGameDefeatLabel.setText(Integer.toString(vOwnProfile.getNbPlay() - vOwnProfile.getNbWin()));
		mOwnProfileLoginLabel.setText(vOwnProfile.getLogin());
	}

	private void initializeCurrentUsers() {

		mMainClientEngine.getInsideDataCliForMain().sendAskCurrentUsers();

		this.mConnectedUsersListView.setCellFactory((pListView) -> new ListCell<SoftPlayer>() {
			@Override
			protected void updateItem(SoftPlayer pSoftPlayer, boolean pBool) {
				super.updateItem(pSoftPlayer, pBool);
				if (pBool || pSoftPlayer == null) {
					setGraphic(null);
				} else {
					Text t = new Text(pSoftPlayer.getLogin());
					setGraphic(t);
				}
			}
		});

	}

	private void initializeCurrentGames() {

		mMainClientEngine.getInsideDataCliForMain().sendAskCurrentGames();

		this.mGamesListView.setCellFactory((pListView) -> new ListCell<SoftGame>() {
			@Override
			protected void updateItem(SoftGame pSoftGame, boolean pBool) {
				super.updateItem(pSoftGame, pBool);
				if (pSoftGame != null) {
					setText(pSoftGame.getName());
				}
			}
		});

		this.mGamesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			changeGameDetails(newValue);
		});

	}

	private void initializeLabels() {
		this.mLogInLabel.setText(
				"Connected as : " + mMainClientEngine.getInsideDataCliForMain().getOwnPlayerProfile().getLogin());
		this.mIpPortConnectedLabel.setText(mMainClientEngine.getInsideDataCliForMain().getServerIp() + " : "
				+ mMainClientEngine.getInsideDataCliForMain().getServerPort());
	}

	public void initalizeAsConnected(){
		switchPlayBorderPane(SwitchValue.ON);
		switchDisconnectBorderPane(SwitchValue.OFF);
		switchConnectedFlowPane(SwitchValue.ON);
		switchDisconnectedFlowPane(SwitchValue.OFF);
	}

	public void initializeAsDisconnected(){
		switchPlayBorderPane(SwitchValue.OFF);
		switchDisconnectBorderPane(SwitchValue.ON);
		switchConnectedFlowPane(SwitchValue.OFF);
		switchDisconnectedFlowPane(SwitchValue.ON);
	}

	private void initializeButtonActions() {
		this.mJoinAsPlayerButton.setOnAction(eventJoinAsPlayerButton -> {
			if (mGamesListView.getSelectionModel().getSelectedItem() != null)
				joinAsPlayer();
		});

		this.mJoinAsViewerButton.setOnAction(eventJoinAsViewerButton -> {
			SoftGame pSelectedGame = mGamesListView.getSelectionModel().getSelectedItem();
			if (pSelectedGame != null && pSelectedGame.getGameParameters().getAllowSpectators())
				joinAsViewer();
		});

		this.mLogOutButton.setOnAction(eventLogOut -> {
			mMainClientEngine.getInsideDataCliForMain().logout();
			mMainClientEngine.changeScene(SceneEnum.CONNECTION.getResource());
		});

		this.mDisconnectButton.setOnAction(eventDisconnect -> {
			//TODO uncomment mMainClientEngine.getInsideDataCliForMain().disconnect();
			initializeAsDisconnected();
		});

		this.mConnectButton.setOnAction(eventConnect -> {
			//TODO uncomment mMainClientEngine.getInsideDataCliForMain().connect(mIpTextField.getText(), Integer.parseInt(mPortTextField.getText()));
		});

		this.mUpdateProfileUpdateButton.setOnAction(eventUpdateProfile -> {
			// TODO Appel de la méthode de Data
			switchBackgroundPane(SwitchValue.ON);
			switchUpdateProfilePane(SwitchValue.ON);
		});

		this.mConnectedUsersListView.setOnMouseClicked(eventDoubleClick -> {
			if (eventDoubleClick.getClickCount() == 2) {
				switchBackgroundPane(SwitchValue.ON);
				switchUserProfilPane(SwitchValue.ON);

				SoftPlayer vSelectedUser = mConnectedUsersListView.getSelectionModel().getSelectedItem();

				mUserLoginLabel.setText(vSelectedUser.getLogin());

				// TODO ask player profil to data and get infos of the user
			}
		});

		this.mGamesListView.setOnMouseClicked(eventClick -> {
			mJoinAsPlayerButton.setDisable(false);
			mJoinAsViewerButton.setDisable(false);
			SoftGame vSoftGame = mGamesListView.getSelectionModel().getSelectedItem();
			if (vSoftGame.getPlayers().size() == vSoftGame.getGameParameters().getNbMaxPlayers()) {
				mJoinAsPlayerButton.setDisable(true);
			}
			if (!vSoftGame.getGameParameters().getAllowSpectators()) {
				mJoinAsViewerButton.setDisable(true);
			}
			mGameInfoNameText.setText(vSoftGame.getName());
			mGameInfoNumberOfPlayersText.setText(
					vSoftGame.getPlayers().size() + "/" + vSoftGame.getGameParameters().getNbMaxPlayers() + " players");
			mGameInfoTimeText.setText(vSoftGame.getGameParameters().getTime() + " sec");
			if (vSoftGame.getGameParameters().getAllowSpectators()) {
				mGameInfoViewersText.setText("allowed");
			} else {
				mGameInfoViewersText.setText("not allowed");
			}
			if (Difficulty.EASY.equals(vSoftGame.getGameParameters().getDifficulty())) {
				mGameInfoDifficultyText.setText("9x9, 10 BOMBS");
			} else if (Difficulty.MEDIUM.equals(vSoftGame.getGameParameters().getDifficulty())) {
				mGameInfoDifficultyText.setText("16x16, 40 BOMBS");
			} else if (Difficulty.HARD.equals(vSoftGame.getGameParameters().getDifficulty())) {
				mGameInfoDifficultyText.setText("16x30, 99 BOMBS");
			}
		});

		this.mGamesListView.focusedProperty().addListener(eventFocus -> {
			switchGameInfoPane(SwitchValue.VICE_VERSA);
			if (!mGamesListView.isFocused()) {
				mJoinAsPlayerButton.setDisable(false);
				mJoinAsViewerButton.setDisable(false);
			}
		});
	}

	private void initializeCreateGame() {

		mStartGameButton.setOnAction(eventStartGameButton -> {

			RadioButton vSelectedRadioButton = (RadioButton) difficultyGroup.getSelectedToggle();
			String vDifficultyValue = vSelectedRadioButton.getText();

			Map<String, Difficulty> vDifficultyMap = new HashMap<>();
			vDifficultyMap.put("EASY", Difficulty.EASY);
			vDifficultyMap.put("MEDIUM", Difficulty.MEDIUM);
			vDifficultyMap.put("HARD", Difficulty.HARD);

			try {
				GameParameters vGameParameters = new GameParameters(mAllowViewersCheckbox.isSelected(),
						Integer.parseInt(mPlayersNumberTextField.getText()), vDifficultyMap.get(vDifficultyValue),
						Integer.parseInt(mShotTimeTextField.getText()));
				SoftGame softGame = new SoftGame(vGameParameters,
						mMainClientEngine.getInsideDataCliForMain().getOwnPlayerProfile(),
						mGameNameTextField.getText());

				mMainClientEngine.getInsideDataCliForMain().createGame(softGame);
			} catch (IInsideDataCliForMain.EmptyFieldException e) {
				mCreateGameErrorText.setText("The fields cannot be empty.");
			} catch (NumberFormatException e) {
				mCreateGameErrorText.setText("Number of players & time between each shot must be numbers.");
			}
		});

		difficultyGroup.selectedToggleProperty().addListener(eventChangeRadioButton -> {
			RadioButton vSelectedRadioButton = (RadioButton) difficultyGroup.getSelectedToggle();

			String vDifficultyValue = vSelectedRadioButton.getText();

			Map<String, String> vDifficultyMap = new HashMap<>();
			vDifficultyMap.put("EASY", "9x9, 10 BOMBS");
			vDifficultyMap.put("MEDIUM", "16x16, 40 BOMBS");
			vDifficultyMap.put("HARD", "16x30, 99 BOMBS");

			mDifficultyInfoLabel.setText(vDifficultyMap.get(vDifficultyValue));
		});
	}

	public void setCurrentUsers(ArrayList<SoftPlayer> pSoftPlayers) {
		this.mCurrentUsers = FXCollections.observableArrayList(pSoftPlayers);
		this.mConnectedUsersListView.setItems(this.mCurrentUsers);
	}

	public void setCurrentGames(ArrayList<SoftGame> pSoftGames) {
		this.mCurrentGames = FXCollections.observableArrayList(pSoftGames);
		this.mGamesListView.setItems(this.mCurrentGames);
	}

	public void newGame(SoftGame pSoftGame) {
		this.mCurrentGames.add(pSoftGame);
	}

	public void updateGame(SoftGame pSoftGame) {
		for (SoftGame vCurrentGame : mCurrentGames) {
			if(vCurrentGame.getIdGame().toString().equals(pSoftGame.getIdGame().toString()))
				vCurrentGame = pSoftGame;
		}

		// p-e besoin de update les listes


	}

	public void newUser(SoftPlayer pSoftPlayer) {
		this.mCurrentUsers.add(pSoftPlayer);
	}

	public void removeUser(SoftPlayer pSoftPlayer) {
		SoftPlayer vPlayerToRemove = null;
		for (SoftPlayer vCurrentPlayer : mCurrentUsers) {
			if (vCurrentPlayer.getIdPlayer().toString().equals(pSoftPlayer.getIdPlayer().toString()))
				vPlayerToRemove = vCurrentPlayer;
		}

		if (vPlayerToRemove != null) {
			mCurrentUsers.remove(vPlayerToRemove);
		}
	}

	public void joinAsViewer() {
		mMainClientEngine.getInsideDataCliForMain()
				.requestJoinGameAsViewer(mGamesListView.getSelectionModel().getSelectedItem());
	}

	public void joinAsPlayer() {
		mMainClientEngine.getInsideDataCliForMain()
				.requestJoinGameAsPlayer(mGamesListView.getSelectionModel().getSelectedItem());
		switchBackgroundPane(SwitchValue.ON);
		switchWaitingEntryGamePane(SwitchValue.ON);
	}

	public void joinGameDenied() {
		switchBackgroundPane(SwitchValue.OFF);
		switchWaitingEntryGamePane(SwitchValue.OFF);

		// Afficher le message de refus
        switchInfoDeniedPane(SwitchValue.ON);
        // Retirer le message de refus après 5 secondes
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> switchInfoDeniedPane(SwitchValue.OFF));
        new Thread(sleeper).start();
    }

	private void changeGameDetails(SoftGame pSoftGame) {

	}

    private void switchPane(SwitchValue vSwitchValue, Pane pPane) {
        if(vSwitchValue.equals(SwitchValue.ON)){
            pPane.setDisable(false);
            pPane.setVisible(true);
        } else if(vSwitchValue.equals(SwitchValue.OFF)){
            pPane.setDisable(true);
            pPane.setVisible(false);
        } else {
            pPane.setDisable(!pPane.isDisable());
            pPane.setVisible(!pPane.isVisible());
        }
    }

	private void switchBackgroundPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mBackgroundPane);
    }


    private void switchCreateGamePane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mCreateGamePane);
    }

	private void switchWaitingEntryGamePane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mWaitingEntryGamePane);
    }

	private void switchUserProfilPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mUserProfilPane);
    }

	private void switchDisconnectedFlowPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mDisconnectedFlowPane);
        mIpTextField.setText("");
		mPortTextField.setText("");
	}

	private void switchConnectedFlowPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mConnectedFlowPane);
    }

	private void switchPlayBorderPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mPlayBorderPane);
    }

	private void switchDisconnectBorderPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mDisconnectBorderPane);
    }

	private void switchUpdateProfilePane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mUpdateProfilePane);
    }

	private void switchGameInfoPane(SwitchValue vSwitchValue) {
        switchPane(vSwitchValue, mGameInfoPane);
    }

    private void switchInfoDeniedPane(SwitchValue vSwitchValue) {
	    switchPane(vSwitchValue,  mInfoDeniedPane);
    }

	public void initializeSwitches() {

		this.mCreateGameButton.setOnAction(eventCreateGameButton -> {
			switchBackgroundPane(SwitchValue.ON);
			switchCreateGamePane(SwitchValue.ON);
		});

		this.mOwnProfileUpateProfileButton.setOnAction(eventOwnProfileUpdateButton -> {
			initializeUpdateProfilePane();
			switchBackgroundPane(SwitchValue.ON);
			switchUpdateProfilePane(SwitchValue.ON);
		});

		mBackgroundPane.setOnMousePressed(eventClickOut -> {
			if (mWaitingEntryGamePane.isDisable()) {
				switchBackgroundPane(SwitchValue.VICE_VERSA);
				if (!mCreateGamePane.isDisable())
					switchCreateGamePane(SwitchValue.VICE_VERSA);
				else if (!mUserProfilPane.isDisable())
					switchUserProfilPane(SwitchValue.VICE_VERSA);
				else if (!mUpdateProfilePane.isDisable())
					switchUpdateProfilePane(SwitchValue.VICE_VERSA);
			}
		});

	}

	public void deleteGame(SoftGame pSoftGame) {
		SoftGame vSoftGameToRemove = null;
		for (SoftGame vGame : mCurrentGames) {
			if (vGame.getIdGame().toString().equals(pSoftGame.getIdGame().toString()))
				vSoftGameToRemove = vGame;
		}

		if (vSoftGameToRemove != null) {
			mCurrentGames.remove(vSoftGameToRemove);
		}
	}

}
