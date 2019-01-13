package ai12.maven_ai12.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import ai12.maven_ai12.beans.SoftPlayer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

public class SoftPlayerList extends VBox {
	
	private ObservableList<SoftPlayer> mList;
	private boolean misPlayerList;
	private HashMap<SoftPlayer,Integer> tableIndice;
	
	public ObservableList<SoftPlayer> getmList() {
		return mList;
	}

	//public void setmList(ObservableList<SoftPlayer> mList) {
	//	this.mList = mList;
	//}

	private GameController mGameController;
	
	SoftPlayerList(ArrayList<SoftPlayer> pList, GameController pGameController, boolean isPlayerList){
		super();
		misPlayerList = isPlayerList;
		this.setSpacing(5.0);
		tableIndice = new HashMap<SoftPlayer,Integer>();
		mList = FXCollections.observableList(new ArrayList<SoftPlayer>());
		mList.addListener((ListChangeListener<SoftPlayer>)change -> {
			while (change.next()) {
				if (change.wasRemoved()) {
					for(SoftPlayer current :change.getRemoved()){
						int indice = tableIndice.get(current);
						this.getChildren().remove(indice);
						for(Entry<SoftPlayer, Integer> entry : tableIndice.entrySet()){
							if (entry.getValue()>indice) entry.setValue(entry.getValue() - 1);
						}
						tableIndice.remove(current);
					}
					// TODO
					// tilePane.getChildren().clear();
					// observersList.getItems().remove(index);
				}
				if (change.wasAdded()) {
					// TODO
					for(SoftPlayer current :  change.getAddedSubList()){
						tableIndice.put(current, this.getChildren().size());
						SoftPlayerListElement nouveau = new SoftPlayerListElement(current,misPlayerList);
						nouveau.setGameController(pGameController);
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								getChildren().add(nouveau);
							}
						});
						//this.getChildren().add(nouveau);
					}
				}
			}
		});
		mList.addAll(pList);
	}
	
	public GameController getGameController() {
		return mGameController;
		
	}

	public void setGameController(GameController pGameController) {
		this.mGameController = pGameController;
	    ObservableList<javafx.scene.Node> children = this.getChildren();
		for (javafx.scene.Node vElement : children) {
			((SoftPlayerListElement)vElement).setGameController(this.mGameController);
		}
	}

	public HashMap<SoftPlayer,Integer> getTableIndice() {
		return tableIndice;
	}

	public void setTableIndice(HashMap<SoftPlayer,Integer> tableIndice) {
		this.tableIndice = tableIndice;
	}
}
