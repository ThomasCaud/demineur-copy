package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Grid extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int nRow;
	private int nCol;
	private HashMap<Coordinates, Box> boxes;

	public Grid() {
		this.setnRow(0);
		this.setnCol(0);
		this.setBoxes(new HashMap<Coordinates, Box>());
	}

	public Grid(int pRow, int pCol, HashMap<Coordinates, Box> pBoxes) {
		this.setnRow(pRow);
		this.setnCol(pCol);
		this.setBoxes(pBoxes);
	}

	public Grid(int pRow, int pCol, final int pNbBombs) {
		this.setnRow(pRow);
		this.setnCol(pCol);
		this.setBoxes(getInitializedBoxes(pNbBombs));
	}

	public int getRandomNumber(final int min, final int max) {
		Random rn = new Random();
		return rn.nextInt(max - min + 1) + min;
	}

	private static void addHintIfCoordinatesExist(HashMap<Coordinates, Box> pBoxes, Coordinates pCoordinates) {
		Box pBox = pBoxes.get(pCoordinates);
		if (pBox != null && pBox.getValue() != -1) {
			pBox.setValue(pBox.getValue() + 1);
		}
	}

	/**
	 * @return HashMap corresponding of a grid well sized, with no bomb at all
	 */
	private HashMap<Coordinates, Box> getEmptyBoxes() {
		HashMap<Coordinates, Box> vBoxes = new HashMap<Coordinates, Box>();
		for (int i = 0; i < nRow; i++) {
			for (int j = 0; j < nCol; j++) {
				Coordinates vCoordinate = new Coordinates(j, i);
				Box vBox = new Box(vCoordinate, false, 0);
				vBoxes.put(vCoordinate, vBox);
			}
		}
		return vBoxes;
	}

	/**
	 * Add the bombs on the grid
	 * 
	 * @param pBoxes
	 * @param pNbBombs
	 * @return
	 */
	private ArrayList<Coordinates> addBombs(HashMap<Coordinates, Box> pBoxes, final int pNbBombs) {
		ArrayList<Coordinates> vCoordinateBombs = new ArrayList<Coordinates>();
		int vNbBombsOnGrid = 0;
		while (vNbBombsOnGrid < pNbBombs) {
			// add bombs on grid
			final int vRandomX = getRandomNumber(0, getnCol() - 1);
			final int vRandomY = getRandomNumber(0, getnRow() - 1);

			Coordinates vCoordinate = new Coordinates(vRandomX, vRandomY);
			Box vBox = pBoxes.get(vCoordinate);
			if (vBox.getValue() != -1) {
				// if box is empty, we add a bomb
				vBox.setValue(-1);
				vCoordinateBombs.add(vCoordinate);
				vNbBombsOnGrid++;
			}
		}
		return vCoordinateBombs;
	}

	public static void addHints(HashMap<Coordinates, Box> pBoxes, ArrayList<Coordinates> pCoordinatesBombs) {
		for (Coordinates vCoordinates : pCoordinatesBombs) {
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() - 1, vCoordinates.getY()));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() - 1, vCoordinates.getY() + 1));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX(), vCoordinates.getY() + 1));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() + 1, vCoordinates.getY() + 1));

			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() + 1, vCoordinates.getY()));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() + 1, vCoordinates.getY() - 1));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX(), vCoordinates.getY() - 1));
			addHintIfCoordinatesExist(pBoxes, new Coordinates(vCoordinates.getX() - 1, vCoordinates.getY() - 1));
		}
	}

	public HashMap<Coordinates, Box> getInitializedBoxes(final int pNbBombs) {
		HashMap<Coordinates, Box> vBoxes = getEmptyBoxes();
		ArrayList<Coordinates> vCoordinateBombs = addBombs(vBoxes, pNbBombs);
		addHints(vBoxes, vCoordinateBombs);

		return vBoxes;
	}

	public int getnRow() {
		return nRow;
	}

	public void setnRow(int nRow) {
		this.nRow = nRow;
	}

	public int getnCol() {
		return nCol;
	}

	public void setnCol(int nCol) {
		this.nCol = nCol;
	}

	public HashMap<Coordinates, Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(HashMap<Coordinates, Box> boxes) {
		this.boxes = boxes;
	}

	private Box getBox(Coordinates pCoordinates) {
		return boxes.get(pCoordinates);
	}

	private void addCoordinatesIfDefined(ArrayList<Box> pList, Coordinates pCoordinates) {
		if (this.getBox(pCoordinates) != null) {
			pList.add(this.getBox(pCoordinates));
		}
	}

	public ArrayList<Box> getAdjacentBoxes(Box pBoxe) {
		ArrayList<Box> adjacentes = new ArrayList<Box>();
		Coordinates vCoordinate = pBoxe.getCoordinates();

		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() - 1, vCoordinate.getY()));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() - 1, vCoordinate.getY() + 1));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX(), vCoordinate.getY() + 1));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() + 1, vCoordinate.getY() + 1));

		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() + 1, vCoordinate.getY()));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() + 1, vCoordinate.getY() - 1));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX(), vCoordinate.getY() - 1));
		addCoordinatesIfDefined(adjacentes, new Coordinates(vCoordinate.getX() - 1, vCoordinate.getY() - 1));

		return adjacentes;
	}

	/**
	 * Set visible squares which are not bombs, following the minesweeper rules
	 * 
	 * @param box
	 */
	public void discoveredSquares(Box box, ArrayList<Box> newDiscoveredSquares) {
		if (box.getValue() != -1 && box.getVisible() == false) {
			box.setVisible(true);
			newDiscoveredSquares.add(box);

			if (box.getValue() == 0) {
				ArrayList<Box> vAdjacents = getAdjacentBoxes(box);
				for (Box vBox : vAdjacents) {
					discoveredSquares(vBox, newDiscoveredSquares);
				}
			}
		}
	}

	public int getNumberOfBombsDiscovered() {
		int vNbBombsDiscovered = 0;

		for (Map.Entry<Coordinates, Box> pEntry : getBoxes().entrySet()) {
			if (pEntry.getValue().getValue() == -1 && pEntry.getValue().getVisible()) {
				vNbBombsDiscovered++;
			}
		}

		return vNbBombsDiscovered;
	}

	public int getNumberOfHiddenSquares() {
		int vNbHiddenSquares = 0;

		for (Map.Entry<Coordinates, Box> pEntry : getBoxes().entrySet()) {
			if (!pEntry.getValue().getVisible()) {
				vNbHiddenSquares++;
			}
		}

		return vNbHiddenSquares;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n");
		for (int i = 0; i < getnCol(); i++) {
			for (int j = 0; j < getnRow(); j++) {
				sb.append(this.getBoxes().get(new Coordinates(i, j)));
				sb.append("\t");
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
