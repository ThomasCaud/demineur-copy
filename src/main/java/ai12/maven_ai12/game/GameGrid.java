package ai12.maven_ai12.game;

import java.util.HashMap;
import java.util.Map;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Grid;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

public class GameGrid extends GridPane {
	private Tile[][] mTiles;
	private Grid mGrid;
	private GameController mGameController;
	
	public static final int SIZE_TINY_COL = 9;
	public static final int SIZE_TINY_ROW = 9;
	public static final int SIZE_MEDIUM_COL = 16;
	public static final int SIZE_MEDIUM_ROW = 16;
	public static final int SIZE_BIG_COL = 30;
	public static final int SIZE_BIG_ROW = 16;
	
	public GameGrid(Grid pGrid) {
		super();
		this.setGrid(pGrid);
	}

	/**
	 * @param mGrid the mGrid to set
	 */
	public void setGrid(Grid pGrid) {
		this.mGrid = pGrid;
		this.refreshGrid();
	}
	
	public void refreshGrid() {
		try {
			HashMap<Coordinates, Box> vBoxes = this.mGrid.getBoxes();
			
			for(Map.Entry<Coordinates, Box> tEntry : vBoxes.entrySet()) {
				Coordinates tCoordinates = tEntry.getKey();
				Box tBox = tEntry.getValue();
				Tile vTile = new Tile(tBox);
				this.add(vTile, tCoordinates.getX(), tCoordinates.getY());
			}
			
		} catch(NullPointerException e) {
			System.err.println("ERROR: the grid has not been instanciated yet.");
		}
	}
	
	public void setBoxTile(Box pBox) {
		Tile vTile = this.getTile(pBox);
		vTile.setBox(pBox);
	}
	
	private Tile getTile(final Box pBox) {
		final int row = pBox.getCoordinates().getY();
		final int column = pBox.getCoordinates().getX();
	    Tile result = null;
	    ObservableList<javafx.scene.Node> children = this.getChildren();

	    for (javafx.scene.Node vTile : children) {
	        if(GridPane.getRowIndex(vTile) == row && GridPane.getColumnIndex(vTile) == column) {
	            result = (Tile)vTile;
	            break;
	        }
	    }

	    return result;
	}

	public GameController getGameController() {
		return mGameController;
		
	}

	public void setGameController(GameController pGameController) {
		this.mGameController = pGameController;
	    ObservableList<javafx.scene.Node> children = this.getChildren();
		for (javafx.scene.Node vTile : children) {
			((Tile)vTile).setGameController(this.mGameController);
		}
	}
}
