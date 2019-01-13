package ai12.maven_ai12.game;

import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import ai12.maven_ai12.beans.Action;
import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Tile extends StackPane {

	public static int TILE_SIZE = 25;

	private Box mBox;
	private Text mText;
	private ImageView mBomb;
	private Flag mFlag;
	private boolean mIsVisible;

	private GameController mGameController;

	public Tile() {
		// TODO Auto-generated constructor stub
	}

	public Tile(Box pBox) {
		this.mBox = pBox;
		this.mText = new Text();
		this.mFlag = new Flag(Tile.TILE_SIZE);
		this.mIsVisible = false;

		this.setTileSize(Tile.TILE_SIZE);
		this.setNonVisible();
		this.getChildren().addAll(this.mText);

		this.refreshTile();

		this.setOnMouseClicked((MouseEvent event) -> {
			if (((MouseEvent) event).getButton().equals(MouseButton.SECONDARY)) {

				SoftPlayer tSoftPlayer = new SoftPlayer();
				tSoftPlayer.setLogin("Stanley");

				ArrayList<SoftPlayer> vFlags = new ArrayList<SoftPlayer>();
				vFlags.add(tSoftPlayer);
				Box tBox = this.mBox;
				if (tBox == null) {
					tBox = pBox;
				}

				tBox.setVisible(false);
				tBox.setFlags(vFlags);

				Shot vShot = new Shot();
				vShot.setAction(Action.FLAG);
				vShot.setBox(tBox);
				vShot.setPlayer(tSoftPlayer);

				this.mGameController.toggleFlag(vShot);
				this.toggleFlag();

			} else {
				if (this.mGameController.isCurrentPlayerTurn()) {
					if (((MouseEvent) event).getClickCount() >= 2) {
						// this.setNumber(((MouseEvent) event).getClickCount() - 1);
					} else {
						this.mGameController.playNewShot(this.mBox.getCoordinates());
					}
				}
			}
		});
	}

	public Tile(Node... children) {
		super(children);
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}

	public boolean isTileVisible() {
		return this.mIsVisible;
	}

	public void setTileSize(int pTileSize) {
		String vSizeStyle = String.format("-fx-min-width: %d; -fx-min-height: %d;", pTileSize, pTileSize);
		this.setStyle(vSizeStyle);
	}

	public void setNonVisible() {
		Tile vTile = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vTile.getStyleClass().clear();
				vTile.getStyleClass().add("GameTile");
				vTile.getStyleClass().add("GameTileNonVisible");
			}
		});
	}

	public void setVisible() {
		this.mIsVisible = true;
		Tile vTile = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vTile.getStyleClass().clear();
				vTile.getStyleClass().add("GameTile");
				vTile.getStyleClass().add("GameTileVisible");
			}
		});
	}

	public void toggleFlag() {
		if(!this.isTileVisible()) {
			SoftPlayer vSoftPlayer = this.mGameController.getIInsideDataCliForGame().getLocalPlayer();

			Tile vTile = this;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					vTile.getChildren().clear();
					vTile.mFlag.toggleSoftPlayer(vSoftPlayer);
					vTile.getChildren().addAll(vTile.mFlag.getFlags());
				}
			});
		}
	}
	
	public void displayBoxFlags() {
		if(!this.isTileVisible()) {
			Tile vTile = this;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					vTile.getChildren().clear();
					vTile.mFlag.setSoftPlayers(vTile.mBox.getFlags());
					vTile.getChildren().addAll(vTile.mFlag.getFlags());
				}
			});
		}
	}

	public void setBomb() {
		this.setVisible();
		URL vPath = getClass().getResource("views" + FileSystems.getDefault().getSeparator() + "bomb.png");
		Image vImage = new Image(vPath.toString(), TILE_SIZE - 8.0, TILE_SIZE - 8.0, false, false);
		this.mBomb = new ImageView(vImage);

		Tile vTile = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vTile.getChildren().clear();
				vTile.getChildren().addAll(vTile.mBomb);
			}
		});
	}

	public void setNumber(int pNumber) {
		this.setVisible();
		String vValue = new String("");
		if (pNumber > 0 && pNumber < 9)
			vValue = String.valueOf(pNumber);
		this.mText.setText(vValue);

		Tile vTile = this;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				vTile.getChildren().clear();
				vTile.getChildren().addAll(vTile.mText);
			}
		});
	}

	/**
	 * @return the mBox
	 */
	public Box getBox() {
		return mBox;
	}

	/**
	 * @param mBox the mBox to set
	 */
	public void setBox(Box pBox) {
		this.mBox = pBox;
		this.refreshTile();
	}

	private void refreshTile() {
		if (this.mBox.getVisible()) {
			this.setVisible();
			if (this.mBox.getValue() == -1) {
				this.setBomb();
			} else {
				this.setNumber(this.mBox.getValue());
			}
		} else {
			this.setNonVisible();
			if (!this.mBox.getFlags().isEmpty()) {
				this.displayBoxFlags();
			}
		}
	}

	public GameController getGameController() {
		return this.mGameController;
	}

	public void setGameController(GameController pGameController) {
		this.mGameController = pGameController;
	}
}
