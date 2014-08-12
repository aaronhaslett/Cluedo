package cluedo.game;

import java.awt.Rectangle;

class BoardObject{}

class Warp extends BoardObject{

	public int[] coords;
	public Room destination;

	public Warp(int[] coords, Room destination){
		this.coords = coords;
		this.destination = destination;
	}
}

class Door extends BoardObject{
	public int[] coords;
	public Room room;
	public String facing;

	public Door(int[] coords, String facing){
		this.coords = coords;
		this.facing = facing;
	}
}

public class Room extends BoardObject{
	private String name;
	//private WeaponPiece[] weaponPieces;
	//private CharacterPiece[] characterPieces;

	private Rectangle[] rectangles;
	public Door[] doors;
	public Warp warp;
	public Player player;

	public Room(String name, int[][][] geometry, Door[] doors){
		this.name = name;

		this.rectangles = new Rectangle[geometry.length];
		int[][] rect = new int[2][2];
		for(int i=0; i<geometry.length; i++){
			rect = geometry[i];
			rectangles[i] = new Rectangle(rect[0][0], 				rect[0][1],
										  rect[1][0]-rect[0][0]+1,  rect[1][1]-rect[0][1]+1);
		}

		this.doors = doors;
		for(Door door: doors){
			door.room = this;
		}
	}

	public String getName(){
		return name;
	}

	public void setWarp(int[] coords, Room destination){
		this.warp = new Warp(coords, destination);
	}

	public boolean contains(int[] point){
		for(Rectangle rect : rectangles){
			if(rect.contains(point[0], point[1])){
				return true;
			}
		}
		return false;
	}

	public Rectangle[] getRectangles(){
		return rectangles;

	}
}
