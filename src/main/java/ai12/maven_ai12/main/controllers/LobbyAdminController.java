package ai12.maven_ai12.main.controllers;

import ai12.maven_ai12.beans.SoftGame;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.main.MainClientEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class LobbyAdminController {
	private MainClientEngine mMainClientEngine;
	private SoftGame mGame;

	@FXML
	private Label mNumberGameLabel, mCreatorNameLabel, mTimeLabel, mViewersLabel, mDifficultyLabel;

	@FXML
	private Label mPendingPlayersListLabel, mAcceptedPlayersListLabel, mViewersListLabel;

	@FXML
	private ListView<SoftPlayer> mAcceptedPlayersListView, mPendingPlayersListView, mViewersListView;

	@FXML
	private Button mAcceptButton, mRejectButton, mStartButton, mAbortButton;

	private ObservableList<SoftPlayer> mPendingPlayers;
	private ObservableList<SoftPlayer> mAcceptedPlayers;
	private ObservableList<SoftPlayer> mViewers;

	public LobbyAdminController(SoftGame pGame) {
		this.mGame = pGame;
	}

	public void initialize() {
		this.mMainClientEngine = MainClientEngine.getMainClientEngine(null);

		mNumberGameLabel.setText("Game : " + mGame.getName());
		mCreatorNameLabel.setText("Created by : " + mGame.getCreator().getLogin());
		mTimeLabel.setText("Time beetween each shot : " + mGame.getGameParameters().getTime() + " sec");
		if (mGame.getGameParameters().getAllowSpectators()) {
			mViewersLabel.setText("Viewers : allowed");
		} else {
			mViewersLabel.setText("Viewers : not allowed");
		}
		mDifficultyLabel.setText("Difficulty : " + mGame.getGameParameters().getDifficulty());
		mAcceptedPlayersListLabel.setText("Accepted players (" + mGame.getPlayers().size() + "/"
				+ mGame.getGameParameters().getNbMaxPlayers() + ")");
		mViewersListLabel.setText("Viewers (" + mGame.getViewers().size() + ")");

		// mPendingPlayers
		this.mPendingPlayers = FXCollections.observableArrayList();
		this.mPendingPlayersListView.setItems(this.mPendingPlayers);
		this.mPendingPlayersListView.setCellFactory((pListView) -> listCellFormat());

		// mAcceptedPlayers
		this.mAcceptedPlayers = FXCollections.observableArrayList();
		this.mAcceptedPlayersListView.setItems(this.mAcceptedPlayers);
		this.mAcceptedPlayersListView.setCellFactory((pListView) -> listCellFormat());

		// mViewers
		this.mViewers = FXCollections.observableArrayList();
		this.mViewersListView.setItems(this.mViewers);
		this.mViewersListView.setCellFactory((pListView) -> listCellFormat());

		updateLists();

		mAcceptButton.setDisable(true);
		mRejectButton.setDisable(true);
		this.mPendingPlayersListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue == null) {
						mAcceptButton.setDisable(true);
						mRejectButton.setDisable(true);
					} else {
						mAcceptButton.setDisable(false);
						mRejectButton.setDisable(false);
					}
				});

		mStartButton
				.setOnAction((eventConnectionButton) -> mMainClientEngine.getInsideDataCliForMain().startGame(mGame));

		mAbortButton.setOnAction((eventConnectionButton) -> {
			mMainClientEngine.getInsideDataCliForMain().abortGame();
		});

		mAcceptButton.setOnAction((eventConnectionButton) -> {
			mMainClientEngine.getInsideDataCliForMain()
					.acceptPlayerRequest(mPendingPlayersListView.getSelectionModel().getSelectedItem(), mGame);
		});

		mRejectButton.setOnAction((eventConnectionButton) -> {
			mMainClientEngine.getInsideDataCliForMain()
					.denyPlayerRequest(mPendingPlayersListView.getSelectionModel().getSelectedItem(), mGame);
			removePendingPlayer(mPendingPlayersListView.getSelectionModel().getSelectedItem());
		});
	}

	public void newPendingPlayer(SoftPlayer pSoftPlayer) {
		mPendingPlayers.add(pSoftPlayer);
		mPendingPlayersListLabel.setText("Pending players " + mPendingPlayers.size());
	}

	public void removePendingPlayer(SoftPlayer pSoftPlayer) {
		mPendingPlayers.remove(pSoftPlayer);
		mPendingPlayersListLabel.setText("Pending players " + mPendingPlayers.size());
	}

	public void gameChange(SoftGame pSoftGame) {
		this.mGame = pSoftGame;

		updateLists();

		this.mAcceptedPlayersListLabel.setText("Accepted players (" + mGame.getPlayers().size() + "/"
				+ mGame.getGameParameters().getNbMaxPlayers() + ")");

		this.mViewersListLabel.setText("Viewers (" + mGame.getViewers().size() + ")");
	}

	public void gameChange(SoftPlayer pSoftPlayer, SoftGame pSoftGame) {
		gameChange(pSoftGame);
		removePendingPlayer(pSoftPlayer);
	}

	private void updateLists() {
		this.mAcceptedPlayers.clear();
		this.mAcceptedPlayers.addAll(mGame.getPlayers());
		System.out.println(this.mAcceptedPlayers);

		this.mViewers.clear();
		this.mViewers.addAll(mGame.getViewers());
	}

	private ListCell<SoftPlayer> listCellFormat() {
		return new ListCell<SoftPlayer>() {
			@Override
			protected void updateItem(SoftPlayer pSoftPlayer, boolean pBool) {
				super.updateItem(pSoftPlayer, pBool);
				if (pSoftPlayer != null) {
					setText(pSoftPlayer.getLogin());
				} else {
					setText("");
				}
			}
		};
	}
}
