package cluedo.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Board{

	public static final int SQUARE_SIZE = 20;
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
	public static Room library = new Room("Library",
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
								new int[][][]{{{0,19},{5,23}}, {{1,18},{4,18}}},
								new Door[]{ new Door(new int[]{4,18}, "E")});
	private static Room ballroom = new Room("Ballroom",
								new int[][][]{{{8,16},{15,21}}, {{10,22},{13,23}}},
								new Door[]{ new Door(new int[]{8,18}, "W"),
											new Door(new int[]{9,16}, "N"),
										    new Door(new int[]{14,16}, "N"),
											new Door(new int[]{15,18}, "W")});
	private static Room kitchen = new Room("Kitchen",
								new int[][][]{{{18,17},{23,23}}},
								new Door[]{ new Door(new int[]{19,17}, "N" )});
	public static Room[] rooms = {study, hall, lounge, library, billiardRoom, diningRoom, conservatory, ballroom, kitchen};

	public Board(){
		//Add warps.
		study.setWarp(new int[]{0,3}, kitchen);
		kitchen.setWarp(new int[]{18,23}, study);
		conservatory.setWarp(new int[]{1,18}, lounge);
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
	}
	public BoardObject[][] getBoardTiles(){
		return board;
	}

	//Some temporary GUI code for testing.
	public static void main(String[] args){
		final Board board = new Board();

		JComponent canvas = new JComponent(){
			protected void paintComponent(Graphics g){
				for(int y=0; y<SIZE; y++){
					for(int x=0; x<SIZE; x++){
						if(board.getBoardTiles()[y][x] == null){
							g.setColor(Color.BLACK);
							g.drawRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
						}else if(board.getBoardTiles()[y][x] instanceof Room){
							g.setColor(Color.RED);
							g.fillRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
						}else if(board.getBoardTiles()[y][x] instanceof Door){
							g.setColor(Color.BLUE);
							g.fillRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
						}else if(board.getBoardTiles()[y][x] instanceof Warp){
							g.setColor(Color.ORANGE);
							g.fillRect(x*SQUARE_SIZE, y*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
						}
					}
				}
			}
		};

		JFrame frame = new JFrame("Cluedo");
		int windowSize = SQUARE_SIZE*board.SIZE;
		frame.setSize(windowSize+5,windowSize+25);
		frame.add(canvas);
		frame.setVisible(true);
		canvas.repaint();
	}
}