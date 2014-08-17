package cluedo.board;

import java.awt.Color;
import java.awt.Rectangle;

public class Room implements BoardObject{
	private String name;

	private Rectangle[] rectangles;
	private Door[] doors;
	private Warp warp;
	private int[][] playerSlots;

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

		//Central point of shape
		int x = minX + (maxX-minX)/2, y = minY + (maxY-minY)/2;
		playerSlots = new int[][]{{x-1,y-1},{x,y-1},
								{x-1,y},  {x,y},
								{x-1,y+1},{x,y+1}};

		this.setDoors(doors);
		for(Door door: doors){
			door.setRoom(this);
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

	public Warp getWarp() {
		return warp;
	}

	public void setWarp(Warp warp) {
		this.warp = warp;
	}

	public Door[] getDoors() {
		return doors;
	}

	public void setDoors(Door[] doors) {
		this.doors = doors;
	}

	public int[][] getPlayerSlots() {
		return playerSlots;
	}
}
