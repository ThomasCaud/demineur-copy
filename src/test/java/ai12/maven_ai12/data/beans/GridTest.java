package ai12.maven_ai12.data.beans;

import java.util.ArrayList;
import java.util.HashMap;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Grid;
import junit.framework.TestCase;

public class GridTest extends TestCase {
	public GridTest(String testName) {
		super(testName);
	}

	public void testHashmap() {
		HashMap<Coordinates, Box> boxes = new HashMap<Coordinates, Box>();
		Box box = new Box();
		boxes.put(new Coordinates(0, 0), box);

		Box box2 = boxes.get(new Coordinates(0, 0));
		assert (box == box2);
		Coordinates coordinates = new Coordinates(0, 0);
		assert (box == boxes.get(coordinates));
		assert (boxes.get(new Coordinates(1, 1)) == null);
	}

	public void testGetAdjacentBoxes() {

		HashMap<Coordinates, Box> boxes = new HashMap<Coordinates, Box>();

		Coordinates c00 = new Coordinates(0, 0);
		Box b00 = new Box(c00);
		boxes.put(c00, b00);

		Coordinates c01 = new Coordinates(0, 1);
		Box b01 = new Box(c01);
		boxes.put(c01, b01);

		Coordinates c02 = new Coordinates(0, 2);
		Box b02 = new Box(c02);
		boxes.put(c02, b02);

		Coordinates c10 = new Coordinates(1, 0);
		Box b10 = new Box(c10);
		boxes.put(c10, b10);

		Coordinates c11 = new Coordinates(1, 1);
		Box b11 = new Box(c11);
		boxes.put(c11, b11);

		Coordinates c12 = new Coordinates(1, 2);
		Box b12 = new Box(c12);
		boxes.put(c12, b12);

		Coordinates c20 = new Coordinates(2, 0);
		Box b20 = new Box(c20);
		boxes.put(c20, b20);

		Coordinates c21 = new Coordinates(2, 1);
		Box b21 = new Box(c21);
		boxes.put(c21, b21);

		Coordinates c22 = new Coordinates(2, 2);
		Box b22 = new Box(c22);
		boxes.put(c22, b22);

		Grid grid = new Grid(3, 3, boxes);
		ArrayList<Box> res = grid.getAdjacentBoxes(b00);
		assert (res.size() == 3);
		assert (res.contains(b01));
		assert (res.contains(b11));
		assert (res.contains(b10));

		res = grid.getAdjacentBoxes(b10);
		assert (res.size() == 5);
		assert (res.contains(b00));
		assert (res.contains(b01));
		assert (res.contains(b11));
		assert (res.contains(b21));
		assert (res.contains(b20));

		res = grid.getAdjacentBoxes(b20);
		assert (res.size() == 3);
		assert (res.contains(b10));
		assert (res.contains(b11));
		assert (res.contains(b21));

		res = grid.getAdjacentBoxes(b01);
		assert (res.size() == 5);
		assert (res.contains(b02));
		assert (res.contains(b12));
		assert (res.contains(b11));
		assert (res.contains(b10));
		assert (res.contains(b00));

		res = grid.getAdjacentBoxes(b11);
		assert (res.size() == 8);
		assert (res.contains(b02));
		assert (res.contains(b12));
		assert (res.contains(b22));
		assert (res.contains(b21));
		assert (res.contains(b20));
		assert (res.contains(b10));
		assert (res.contains(b00));
		assert (res.contains(b01));

		res = grid.getAdjacentBoxes(b21);
		assert (res.size() == 5);
		assert (res.contains(b22));
		assert (res.contains(b12));
		assert (res.contains(b11));
		assert (res.contains(b10));
		assert (res.contains(b20));

		res = grid.getAdjacentBoxes(b22);
		assert (res.size() == 3);
		assert (res.contains(b12));
		assert (res.contains(b11));
		assert (res.contains(b21));

		res = grid.getAdjacentBoxes(b12);
		assert (res.size() == 5);
		assert (res.contains(b02));
		assert (res.contains(b01));
		assert (res.contains(b11));
		assert (res.contains(b21));
		assert (res.contains(b22));

		res = grid.getAdjacentBoxes(b02);
		assert (res.size() == 3);
		assert (res.contains(b01));
		assert (res.contains(b11));
		assert (res.contains(b12));
	}

	public void testDiscoveredSquares() {
		Grid vGrid;
		HashMap<Coordinates, Box> vBoxes;
		Coordinates c00 = new Coordinates(0, 0);
		Box b00;
		Coordinates c01 = new Coordinates(0, 1);
		Coordinates c02 = new Coordinates(0, 2);

		// If the checked box is a bomb, we do not change its visibility,
		// neither his neighbor
		vBoxes = new HashMap<Coordinates, Box>();
		b00 = new Box(c00, false, -1);
		vBoxes.put(c00, b00);
		vBoxes.put(c01, new Box(c01, false, 0));

		ArrayList<Box> newDiscoveredSquares = new ArrayList<Box>();
		vGrid = new Grid(1, 2, vBoxes);
		vGrid.discoveredSquares(b00, newDiscoveredSquares);
		assert (vGrid.getBoxes().get(c00).getVisible() == false);
		assert (vGrid.getBoxes().get(c01).getVisible() == false);
		assert (newDiscoveredSquares.size() == 0);

		// If the checked box is empty, and its neighboors too...
		vBoxes = new HashMap<Coordinates, Box>();
		b00 = new Box(c00, false, 0);
		vBoxes.put(c00, b00);
		vBoxes.put(c01, new Box(c01, false, 0));
		vBoxes.put(c02, new Box(c02, false, 0));

		newDiscoveredSquares.clear();
		vGrid = new Grid(1, 3, vBoxes);
		vGrid.discoveredSquares(b00, newDiscoveredSquares);
		// ... we change its visibility...
		assert (vGrid.getBoxes().get(c00).getVisible() == true);
		// ... the visibility of its first neihboor...
		assert (vGrid.getBoxes().get(c01).getVisible() == true);
		// ... and so on recursively
		assert (vGrid.getBoxes().get(c02).getVisible() == true);
		assert (newDiscoveredSquares.size() == 3);

		// If the checked box is empty, and its neighboors are not...
		vBoxes = new HashMap<Coordinates, Box>();
		b00 = new Box(c00, false, 0);
		vBoxes.put(c00, b00);
		vBoxes.put(c01, new Box(c01, false, 1));
		vBoxes.put(c02, new Box(c02, false, 0));

		newDiscoveredSquares.clear();
		vGrid = new Grid(1, 3, vBoxes);
		vGrid.discoveredSquares(b00, newDiscoveredSquares);
		// ... we change its visibility...
		assert (vGrid.getBoxes().get(c00).getVisible() == true);
		// ... the visibility of its first neihboor...
		assert (vGrid.getBoxes().get(c01).getVisible() == true);
		// ... but not the last one
		assert (vGrid.getBoxes().get(c02).getVisible() == false);
		assert (newDiscoveredSquares.size() == 2);
	}

	public void testGetNumberOfBombsDiscovered() {
		Grid vGrid;
		HashMap<Coordinates, Box> vBoxes;
		Coordinates c00 = new Coordinates(0, 0);
		Coordinates c01 = new Coordinates(0, 1);
		Coordinates c02 = new Coordinates(0, 2);

		// If there are no bombs at all
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, false, 0));

		vGrid = new Grid(1, 1, vBoxes);
		assert (vGrid.getNumberOfBombsDiscovered() == 0);

		// If there is one hidden bomb
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, false, -1));

		vGrid = new Grid(1, 1, vBoxes);
		assert (vGrid.getNumberOfBombsDiscovered() == 0);

		// If there are one discovered bomb
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, true, -1));

		vGrid = new Grid(1, 1, vBoxes);
		assert (vGrid.getNumberOfBombsDiscovered() == 1);

		// If there are two discovered bomb and one hidden
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, true, -1));
		vBoxes.put(c01, new Box(c01, false, -1));
		vBoxes.put(c02, new Box(c00, true, -1));

		vGrid = new Grid(1, 3, vBoxes);
		assert (vGrid.getNumberOfBombsDiscovered() == 2);
	}

	public void testGetNumberOfHiddenSquares() {
		Grid vGrid;
		HashMap<Coordinates, Box> vBoxes;
		Coordinates c00 = new Coordinates(0, 0);
		Coordinates c01 = new Coordinates(0, 1);
		Coordinates c02 = new Coordinates(0, 2);

		// If there is only discovered square
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, true, 0));

		vGrid = new Grid(1, 1, vBoxes);
		assert (vGrid.getNumberOfHiddenSquares() == 0);

		// If there is one covered square
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, false, -1));

		vGrid = new Grid(1, 1, vBoxes);
		assert (vGrid.getNumberOfHiddenSquares() == 1);

		// If there are one discovered, one cover, and another discovered
		vBoxes = new HashMap<Coordinates, Box>();
		vBoxes.put(c00, new Box(c00, true, -1));
		vBoxes.put(c01, new Box(c01, false, -1));
		vBoxes.put(c02, new Box(c00, true, -1));

		vGrid = new Grid(1, 3, vBoxes);
		assert (vGrid.getNumberOfBombsDiscovered() == 2);
	}

	public void testGetRandomNumber() {
		Grid grid = new Grid();
		final int min = -5;
		final int max = 10;

		for (int i = 0; i < 100; i++) {
			int randomNumber = grid.getRandomNumber(min, max);
			if (randomNumber < min || randomNumber > max) {
				assert (false);
			}
		}
	}

	public void testGetInitializedBoxes() {
		final int nbBombsExpected = 4;
		Grid grid = new Grid(3, 3, nbBombsExpected);
		HashMap<Coordinates, Box> boxes = grid.getBoxes();

		int nbBombsOnGrid = 0;
		for (Box box : boxes.values()) {
			if (box.getValue() == -1) {
				nbBombsOnGrid++;
			}
		}

		assert (nbBombsOnGrid == nbBombsExpected);
	}

	public void testAddHints() {
		// With only 1 bomb
		HashMap<Coordinates, Box> boxes = new HashMap<Coordinates, Box>();
		boxes.put(new Coordinates(0, 0), new Box(new Coordinates(0, 0), false, -1));
		boxes.put(new Coordinates(0, 1), new Box(new Coordinates(0, 1), false, 0));

		ArrayList<Coordinates> coordinatesBombs = new ArrayList<Coordinates>();
		coordinatesBombs.add(new Coordinates(0, 0));

		assert (boxes.get(new Coordinates(0, 1)).getValue() == 0);
		Grid.addHints(boxes, coordinatesBombs);
		assert (boxes.get(new Coordinates(0, 0)).getValue() == -1);
		assert (boxes.get(new Coordinates(0, 1)).getValue() == 1);

		// With BOMB-EMPTY \BOMB-EMPTY
		boxes = new HashMap<Coordinates, Box>();
		boxes.put(new Coordinates(0, 0), new Box(new Coordinates(0, 0), false, -1));
		boxes.put(new Coordinates(0, 1), new Box(new Coordinates(0, 1), false, 0));
		boxes.put(new Coordinates(1, 0), new Box(new Coordinates(1, 0), false, 0));
		boxes.put(new Coordinates(1, 1), new Box(new Coordinates(1, 1), false, -1));

		coordinatesBombs = new ArrayList<Coordinates>();
		coordinatesBombs.add(new Coordinates(0, 0));
		coordinatesBombs.add(new Coordinates(1, 1));

		assert (boxes.get(new Coordinates(0, 1)).getValue() == 0);
		assert (boxes.get(new Coordinates(1, 0)).getValue() == 0);
		Grid.addHints(boxes, coordinatesBombs);
		assert (boxes.get(new Coordinates(0, 0)).getValue() == -1);
		assert (boxes.get(new Coordinates(0, 1)).getValue() == 2);
		assert (boxes.get(new Coordinates(1, 0)).getValue() == 2);
		assert (boxes.get(new Coordinates(1, 1)).getValue() == -1);

		// With EMPTY-EMPTY-BOMB
		boxes = new HashMap<Coordinates, Box>();
		boxes.put(new Coordinates(0, 0), new Box(new Coordinates(0, 0), false, 0));
		boxes.put(new Coordinates(0, 1), new Box(new Coordinates(0, 1), false, 0));
		boxes.put(new Coordinates(0, 2), new Box(new Coordinates(0, 2), false, -1));

		coordinatesBombs = new ArrayList<Coordinates>();
		coordinatesBombs.add(new Coordinates(0, 2));

		assert (boxes.get(new Coordinates(0, 0)).getValue() == 0);
		assert (boxes.get(new Coordinates(0, 1)).getValue() == 0);
		Grid.addHints(boxes, coordinatesBombs);
		assert (boxes.get(new Coordinates(0, 0)).getValue() == 0);
		assert (boxes.get(new Coordinates(0, 1)).getValue() == 1);
		assert (boxes.get(new Coordinates(0, 2)).getValue() == -1);
	}

}