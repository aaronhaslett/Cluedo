package cluedo.board;
import java.awt.Color;

public class Door implements BoardObject{
	public int[] coords;
	public Room room;
	public String facing;

	public Door(int[] coords, String facing){
		this.coords = coords;
		this.facing = facing;
	}

	public Color getColour(){return Color.blue;}
}
