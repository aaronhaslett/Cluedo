package cluedo.board;
import java.awt.Color;

public class Door implements BoardObject{
	public int[] coords;
	public Room room;
	public String facing;
	public boolean highlighted = false;

	public Door(int[] coords, String facing){
		this.coords = coords;
		this.facing = facing;
	}

	public Color getColour(){
		if(highlighted){
			return new Color(170,240,65);
		}else{
			return Color.blue;
		}
	}
}
