package cluedo.board;

import java.awt.Rectangle;

import cluedo.game.Player;

public class Room implements BoardObject{
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
