package cluedo.board;
import java.awt.Color;

public class PathSquare implements BoardObject{
	public int[] coords;

	public PathSquare(int[] coords){
		this.coords = coords;
	}

	public Color getColour(){return Color.lightGray;}
}
