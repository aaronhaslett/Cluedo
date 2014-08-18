package cluedo.board;
import java.awt.Color;

public class PathSquare implements BoardObject{
	private int[] coords;

	public PathSquare(int[] coords){
		this.setCoords(coords);
	}

	public Color getColour(){return new Color(120,180,148);}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}
}
