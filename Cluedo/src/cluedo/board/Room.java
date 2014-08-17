package cluedo.board;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Point;

import cluedo.game.Player;

public class Room implements BoardObject{
	private String name;
	//private WeaponPiece[] weaponPieces;
	//private CharacterPiece[] characterPieces;

	private Rectangle[] rectangles;
	public Door[] doors;
	public Warp warp;
	public Point centralPoint;

	public Room(String name, int[][][] geometry, Door[] doors){
		this.name = name;

		this.rectangles = new Rectangle[geometry.length];
		int[][] rect = new int[2][2];
		for(int i=0; i<geometry.length; i++){
			rect = geometry[i];
			rectangles[i] = new Rectangle(rect[0][0], 				rect[0][1],
										  rect[1][0]-rect[0][0]+1,  rect[1][1]-rect[0][1]+1);
		}

		int minX=25, maxX=0, minY=25, maxY=0;
		for(Rectangle r : rectangles){
			minX = Math.min(minX, (int)(r.getX()));
			maxX = Math.max(maxX, (int)(r.getX() + r.getWidth()));
			minY = Math.min(minY, (int)(r.getY()));
			maxY = Math.max(maxY, (int)(r.getY() + r.getHeight()));
		}
		centralPoint = new Point(minX + (maxX-minX)/2, minY + (maxY-minY)/2);

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

	public Color getColour(){
		return new Color(173, 120, 87);
	}
}
