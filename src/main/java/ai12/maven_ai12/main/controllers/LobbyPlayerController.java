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

public class LobbyPlayerController {
	private MainClientEngine mMainClientEngine;
	private SoftGame mGame;

	@FXML
	private Label mNumberGameLabel, mCreatorNameLabel, mTimeLabel, mViewersLabel, mDifficultyLabel;

	@FXML
	private ListView<SoftPlayer> mPlayersListView;

	@FXML
	private Button mQuitButton;

	private ObservableList<SoftPlayer> mPlayers;

	public LobbyPlayerController(SoftGame pGame) {
		this.mGame = pGame;
	}

	public void initialize() {
		this.mMainClientEngine = MainClientEngine.getMainClientEngine(null);

		// TODO remplacer par les valeurs de la partie
		mNumberGameLabel.setText("Game " + mGame.getName());
		mCreatorNameLabel.setText("Created by : " + mGame.getCreator().getLogin());
		mTimeLabel.setText("Time beetween each shot : " + mGame.getGameParameters().getTime() + " sec");
		if (mGame.getGameParameters().getAllowSpectators()) {
			mViewersLabel.setText("Viewers : allowed");
		} else {
			mViewersLabel.setText("Viewers : not allowed");
		}
		mDifficultyLabel.setText("Difficulty : " + mGame.getGameParameters().getDifficulty());

		this.mPlayers = FXCollections.observableArrayList();
		this.mPlayersListView.setItems(this.mPlayers);

		this.mPlayersListView.setCellFactory((pListView) -> listCellFormat());

		updateLists();

		mQuitButton.setOnAction((eventConnectionButton) -> {
			mMainClientEngine.getInsideDataCliForMain().abortGame();
		});
	}

	public void gameChange(SoftGame pSoftGame) {
		this.mGame = pSoftGame;
		this.updateLists();
	}

	private void updateLists() {
		this.mPlayers.clear();
		this.mPlayers.addAll(mGame.getPlayers());
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
