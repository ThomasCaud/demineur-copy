package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Box extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Coordinates coordinates;
	private boolean visible;
	private int value;
	private ArrayList<SoftPlayer> flags;

	public Box() {
		this.setCoordinates(new Coordinates());
		this.setVisible(false);
		this.setValue(0);
		this.setFlags(new ArrayList<SoftPlayer>());
	}

	public Box(Coordinates coordinates, boolean visible, int value) {
		this.setCoordinates(coordinates);
		this.setVisible(visible);
		this.setValue(value);
		this.setFlags(new ArrayList<SoftPlayer>());
	}

	public Box(Coordinates coordinates) {
		this.setCoordinates(coordinates);
		this.setVisible(false);
		this.setValue(0);
		this.setFlags(new ArrayList<SoftPlayer>());
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = (value > -2 && value < 9) ? value : 0;
	}

	public ArrayList<SoftPlayer> getFlags() {
		return flags;
	}

	public void setFlags(ArrayList<SoftPlayer> flags) {
		this.flags = flags;
	}

	public String toString() {
		return "[" + this.getValue() + "]";
	}
}
