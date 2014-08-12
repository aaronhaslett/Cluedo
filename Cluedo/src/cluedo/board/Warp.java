package cluedo.board;


public class Warp implements BoardObject{

	public int[] coords;
	public Room destination;

	public Warp(int[] coords, Room destination){
		this.coords = coords;
		this.destination = destination;
	}
}
