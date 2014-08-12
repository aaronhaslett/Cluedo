package cluedo.game;

public class Room{
	private String name;
	//private WeaponPiece[] weaponPieces;
	//private CharacterPiece[] characterPieces;

	public int[][][] geometry;
	public int[][] doors;
	public Warp warp;

	public Room(String name, int[][][] geometry, int[][] doors){
		this.name = name;
		this.geometry = geometry;
		this.doors = doors;
	}

	public String getName(){
		return name;
	}

	public void setWarp(int[] coords, Room destination){
		this.warp = new Warp(coords, destination);
	}

	public class Warp{
		int[] coords;
		Room destination;

		public Warp(int[] coords, Room destination){
			this.coords = coords;
			this.destination = destination;
		}
	}
}
