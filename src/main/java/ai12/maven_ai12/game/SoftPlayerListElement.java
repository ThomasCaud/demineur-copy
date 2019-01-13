package ai12.maven_ai12.game;

import java.net.URL;
import java.nio.file.FileSystems;

import ai12.maven_ai12.beans.SoftPlayer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class SoftPlayerListElement extends AnchorPane {

	private GameController mGameController;
	private SoftPlayer mElement;
	private boolean misPlayerList;
	// private int mIndice;

	private Text text;
	private CheckBox cb;
	private ImageView iv;

	SoftPlayerListElement(SoftPlayer pElement, boolean isPlayerList/* , int pIndice */) {
		super();
		mElement = pElement;
		misPlayerList = isPlayerList;
		// setmIndice(pIndice);
		/*
		 * FXMLLoader loader = new FXMLLoader(); URL url =
		 * getClass().getResource("views" + FileSystems.getDefault().getSeparator() +
		 * "row.fxml"); loader.setLocation(url);
		 * 
		 * try { AnchorPane root = (AnchorPane) loader.load(); //TODO } catch(Exception
		 * e){
		 * 
		 * }
		 */
		text = new Text("toto " + mElement.getLogin());

		AnchorPane.setLeftAnchor(text, 0.0);
		AnchorPane.setTopAnchor(text, 0.0);
		this.getChildren().add(text);
		// System.out.println(text.getText());

		// this.getChildren().get(0);
		if (misPlayerList) {
			cb = new CheckBox("");
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					if (new_val) {
						mGameController.getmPlayersFlagVisible().add(mElement);
						System.out.println(mGameController.getmPlayersFlagVisible());
					} else {
						mGameController.getmPlayersFlagVisible().remove(mElement);
						System.out.println(mGameController.getmPlayersFlagVisible());
					}
				}
			});
			AnchorPane.setRightAnchor(cb, 5.0);
			this.getChildren().add(cb);
			iv = new ImageView();
			iv.setImage(null);
			AnchorPane.setRightAnchor(iv, 45.0);
			AnchorPane.setTopAnchor(iv, 5.0);
			this.getChildren().add(iv);
		}

	}

	public SoftPlayer getmElement() {
		return mElement;
	}

	public GameController getGameController() {
		return mGameController;

	}

	public void setGameController(GameController pGameController) {
		this.mGameController = pGameController;
		// System.out.println("toto " + this.mGameController.getmDeadPlayers());
		if (misPlayerList) {
			this.mGameController.getmDeadPlayers().addListener((ListChangeListener<SoftPlayer>) change -> {
				while (change.next()) {
					if (change.wasRemoved()) {
						// TODO
					}
					if (change.wasAdded()) {
						for (SoftPlayer current : change.getAddedSubList()) {
							/*
							 * this.mPlayersList.getChildren().forEach(child -> { if
							 * (((SoftPlayerListElement) child).getmElement().equals(current)){
							 * Platform.runLater(new Runnable() {
							 * 
							 * @Override public void run() {
							 * ((SoftPlayerListElement)child).getChildren().add(new Text("dead"));
							 * //vTile.getChildren().addAll(vTile.mText); } }); } });
							 */
							if (current.equals(mElement)) {
								System.out.println("current (" + current.getLogin() + ") = mElement ("
										+ mElement.getLogin() + ")");
								URL vPath = getClass()
										.getResource("views" + FileSystems.getDefault().getSeparator() + "bomb.png");
								Image vImage = new Image(vPath.toString(), 15.0, 15.0, false, false);
								iv.setImage(vImage);
								System.out.println(this.mGameController.getmDeadPlayers());
							}
						}
					}
				}
			});
		}
	}

	public CheckBox getCb() {
		return cb;
	}

	public void setCb(CheckBox cb) {
		this.cb = cb;
	}

	public ImageView getIv() {
		return iv;
	}

	public void setIv(ImageView iv) {
		this.iv = iv;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	/*
	 * public int getmIndice() { return mIndice; }
	 * 
	 * public void setmIndice(int mIndice) { this.mIndice = mIndice; }
	 */
}
