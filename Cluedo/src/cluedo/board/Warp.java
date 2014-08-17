package cluedo.board;
import java.awt.Color;

public class Warp implements BoardObject{

	private int[] coords;
	private Room destination;

	public Warp(int[] coords, Room destination){
		this.setCoords(coords);
		this.setDestination(destination);
	}

	public Color getColour(){
		return Color.pink;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public Room getDestination() {
		return destination;
	}

	public void setDestination(Room destination) {
		this.destination = destination;
	}
}
