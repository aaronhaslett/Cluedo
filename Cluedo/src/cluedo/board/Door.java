package cluedo.board;


public class Door implements BoardObject{
	public int[] coords;
	public Room room;
	public String facing;

	public Door(int[] coords, String facing){
		this.coords = coords;
		this.facing = facing;
	}
}