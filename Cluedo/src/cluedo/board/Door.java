package cluedo.board;
import java.awt.Color;

public class Door implements BoardObject{
	public int[] coords;
	private Room room;
	private String facing;
	private boolean highlighted = false;

	public Door(int[] coords, String facing){
		this.coords = coords;
		this.setFacing(facing);
	}

	public Color getColour(){
		if(isHighlighted()){
			return new Color(237, 242, 201);
		}else{
			return new Color(159, 169, 85);
		}
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getFacing() {
		return facing;
	}

	public void setFacing(String facing) {
		this.facing = facing;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
