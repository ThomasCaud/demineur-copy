
package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer x;
	private Integer y;

	public Coordinates() {
		this.setX(0);
		this.setY(0);
	}

	public Coordinates(int pX, int pY) {
		this.setX(pX);
		this.setY(pY);
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public void setX(Integer i) {
		this.x = i;
	}

	public void setY(Integer j) {
		this.y = j;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Coordinates)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		Coordinates pCoordinates = (Coordinates) obj;
		return pCoordinates.getX().equals(getX()) && pCoordinates.getY().equals(getY());
	}

	@Override
	public String toString() {
		return "Coordinates [x=" + x + ", y=" + y + "]";
	}
}
