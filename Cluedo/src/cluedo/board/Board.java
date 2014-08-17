package cluedo.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.util.*;
import java.awt.Point;
import cluedo.game.Player;

public class Board{

	public static final int SQUARE_SIZE = 25;
	public static final int SIZE = 24;

	//The whole board.  Null is an empty square.
	private BoardObject[][] board = new BoardObject[SIZE][SIZE];

	//The rooms, the coordinates of rectangles making them up, and their doors.
	private static Room study = new Room("Study",
								new int[][][]{{{0,0},{5,3}}, {{6,1},{6,3}}},
								new Door[]{ new Door(new int[]{6,3}, "S")});//Doors have a facing direction.
	private static Room hall = new Room("Hall",
								new int[][][]{{{9,0},{14,6}}},
								new Door[]{ new Door(new int[]{9,4}, "W"),
											new Door(new int[]{11,6}, "S"),
										    new Door(new int[]{12,6}, "S")});
	private static Room lounge = new Room("Lounge",
								new int[][][]{{{17,0},{23,5}}},
								new Door[]{ new Door(new int[]{17,5}, "S")});
	private static Room library = new Room("Library",
								new int[][][]{{{0,6},{5,10}}, {{6,7},{6,9}}},
								new Door[]{ new Door(new int[]{3,10}, "S"),
											new Door(new int[]{6,8}, "E")});
	private static Room billiardRoom = new Room("Billiard Room",
								new int[][][]{{{0,12},{5,16}}},
								new Door[]{ new Door(new int[]{1,12}, "N"),
											new Door(new int[]{5,15}, "E")});
	private static Room diningRoom = new Room("Dining Room",
								new int[][][]{{{16,9},{18,14}}, {{19,9},{23,15}}},
								new Door[]{ new Door(new int[]{17,9}, "N"),
											new Door(new int[]{16,12}, "W")});
	private static Room conservatory = new Room("Conservatory",
								new int[][][]{{{0,20},{5,23}}, {{1,19},{4,19}}},
								new Door[]{ new Door(new int[]{4,19}, "E")});
	private static Room ballroom = new Room("Ballroom",
								new int[][][]{{{8,17},{15,22}}, {{10,23},{13,23}}},
								new Door[]{ new Door(new int[]{8,19}, "W"),
											new Door(new int[]{9,17}, "N"),
										    new Door(new int[]{14,17}, "N"),
											new Door(new int[]{15,19}, "E")});
	private static Room kitchen = new Room("Kitchen",
								new int[][][]{{{18,18},{23,23}}},
								new Door[]{ new Door(new int[]{19,18}, "N" )});
	public static Room[] rooms = {study, hall, lounge, library, billiardRoom, diningRoom, conservatory, ballroom, kitchen};

	private static List<Player> players;

	public Board(List<Player> players){
		//Add warps.
		study.setWarp(new int[]{0,3}, kitchen);
		kitchen.setWarp(new int[]{18,23}, study);
		conservatory.setWarp(new int[]{1,19}, lounge);
		lounge.setWarp(new int[]{23,5}, conservatory);

		//Each room is composed of rectangles declared above.  Add each square in each rectangle to the board.
		for(Room room: rooms){
			for(Rectangle rect: room.getRectangles()){//For each rectangle in each room
				for(int x=(int)rect.getX(); x<rect.getX()+rect.getWidth(); x++){
					for(int y=(int)rect.getY(); y<rect.getY()+rect.getHeight(); y++){
						board[y][x] = room;
					}
				}
			}
			for(Door door: room.doors){
				board[door.coords[1]][door.coords[0]] = door;
			}
			if(room.warp != null){
				board[room.warp.coords[1]][room.warp.coords[0]] = room.warp;
			}
		}

		for(Player player: players){
			int[] pos = player.getCharacter().getStartingPosition();
			board[pos[1]][pos[0]] = player;
			player.position = new Point(pos[0], pos[1]);
		}
	}

	public boolean move(Player p, Point to){
		int px = (int)p.position.getX(), py = (int)p.position.getY();
		int tx = (int)to.getX(), ty = (int)to.getY();

		BoardObject ob = board[ty][tx];
		boolean pathSquare = ob instanceof PathSquare;
		boolean highlightedDoor = ob instanceof Door && ((Door)ob).highlighted;

		if(!(pathSquare || highlightedDoor)){
			return false;
		}

		if(p.room != null){
			removePlayerFromRoom(p, p.room);
		}else{
			board[py][px] = null;
		}

		if(highlightedDoor){
			placePlayerInRoom(p, ((Door)ob).room);
		}
		else{
			board[ty][tx] = p;
		}
		p.position.setLocation(to.getX(), to.getY());
		return true;
	}

	public void showPaths(int diceRoll, Player player){
		Point p = player.getPosition();
		showPaths(diceRoll, new int[]{(int)p.getX(), (int)p.getY()});
	}

	private void showPaths(int diceRoll, int[] position){
		if(diceRoll == 0){return;}

		int px = position[0], py=position[1];
		int[][] surroundingSquares = new int[][]{{px-1, py},{px, py+1},{px,py-1},{px+1,py}};

		for(int[] sq : surroundingSquares){
			int x=sq[0], y=sq[1];
			if(x<0 || x>23 || y<0 || y>23){continue;}

			if(board[y][x] == null || board[y][x] instanceof PathSquare){
				board[y][x] = new PathSquare(sq);
				showPaths(diceRoll-1, sq);
			}
			else if(board[y][x] instanceof Door){
				((Door)board[y][x]).highlighted = true;
			}
		}
	}

	private void placePlayerInRoom(Player p, Room r){
		for(int[] slot: r.playerSlots){
			if(board[slot[1]][slot[0]] instanceof Room){
				board[slot[1]][slot[0]] = p;
				p.room = r;
				return;
			}
		}
	}

	private void removePlayerFromRoom(Player p, Room r){
		for(int[] slot: r.playerSlots){
			if(board[slot[1]][slot[0]] == p){
				board[slot[1]][slot[0]] = r;
				p.room = null;
				return;
			}
		}
	}

	public void clearPath(){
		int boardSize = board.length;

		for(int x=0; x<boardSize; x++){
			for(int y=0; y<boardSize; y++){
				if(board[y][x] instanceof Door){
					((Door)board[y][x]).highlighted = false;
				}else if(board[y][x] instanceof PathSquare){
					board[y][x] = null;
				}
			}
		}
	}

	public BoardObject[][] getBoardTiles(){
		return board;
	}
}
