package cluedo.board;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;

import cluedo.game.Game;

public class Room implements BoardObject{
	private Game.Room name;

	private Rectangle[] rectangles;
	private Door[] doors;
	private Warp warp;
	private int[][] playerSlots;
	private double[] centralPoint;

	public Room(Game.Room name, int[][][] geometry, Door[] doors){
		this.name = name;

		this.rectangles = new Rectangle[geometry.length];
		int[][] rect = new int[2][2];
		for(int i=0; i<geometry.length; i++){
			rect = geometry[i];
			rectangles[i] = new Rectangle(rect[0][0], 				rect[0][1],
										  rect[1][0]-rect[0][0]+1,  rect[1][1]-rect[0][1]+1);
		}

		double minX=2500d, maxX=0d, minY=2500d, maxY=0d;
		for(Rectangle r : rectangles){
			minX = Math.min(minX, r.getX());
			maxX = Math.max(maxX, r.getX() + r.getWidth());
			minY = Math.min(minY, r.getY());
			maxY = Math.max(maxY, r.getY() + r.getHeight());
		}

		//Central point of shape
		double px = minX + (maxX-minX)/2d, py = minY + (maxY-minY)/2d;
		centralPoint = new double[]{px, py};

		int x = (int)px, y = (int)py;

		playerSlots = new int[][]{{x-1,y-1},{x,y-1},
								{x-1,y},  {x,y},
								{x-1,y+1},{x,y+1}};

		this.setDoors(doors);
		for(Door door: doors){
			door.setRoom(this);
		}
	}

	public Game.Room getName(){
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

	public double[] getCentralPoint(){
		return centralPoint;
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
