package cluedo.board;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import cluedo.game.Game;
import cluedo.game.Player;

public class Board{

	public static final int SQUARE_SIZE = 25;
	public static final int SIZE = 24;

	//The whole board.  Null is an empty square.
	private BoardObject[][] board = new BoardObject[SIZE][SIZE];

	//The rooms, the coordinates of rectangles making them up, and their doors.
	private static Room study = new Room(Game.Room.Study,
								new int[][][]{{{0,0},{5,3}}, {{6,1},{6,3}}},
								new Door[]{ new Door(new int[]{6,3}, "S")});//Doors have a facing direction.
	private static Room hall = new Room(Game.Room.Hall,
								new int[][][]{{{9,0},{14,6}}},
								new Door[]{ new Door(new int[]{9,4}, "W"),
											new Door(new int[]{11,6}, "S"),
										    new Door(new int[]{12,6}, "S")});
	private static Room lounge = new Room(Game.Room.Lounge,
								new int[][][]{{{17,0},{23,5}}},
								new Door[]{ new Door(new int[]{17,5}, "S")});
	private static Room library = new Room(Game.Room.Library,
								new int[][][]{{{0,6},{5,10}}, {{6,7},{6,9}}},
								new Door[]{ new Door(new int[]{3,10}, "S"),
											new Door(new int[]{6,8}, "E")});
	private static Room billiardRoom = new Room(Game.Room.BilliardRoom,
								new int[][][]{{{0,12},{5,16}}},
								new Door[]{ new Door(new int[]{1,12}, "N"),
											new Door(new int[]{5,15}, "E")});
	private static Room diningRoom = new Room(Game.Room.DiningRoom,
								new int[][][]{{{16,9},{18,14}}, {{19,9},{23,15}}},
								new Door[]{ new Door(new int[]{17,9}, "N"),
											new Door(new int[]{16,12}, "W")});
	private static Room conservatory = new Room(Game.Room.Conservatory,
								new int[][][]{{{0,20},{5,23}}, {{1,19},{4,19}}},
								new Door[]{ new Door(new int[]{4,19}, "E")});
	private static Room ballroom = new Room(Game.Room.BallRoom,
								new int[][][]{{{8,17},{15,22}}, {{10,23},{13,23}}},
								new Door[]{ new Door(new int[]{8,19}, "W"),
											new Door(new int[]{9,17}, "N"),
										    new Door(new int[]{14,17}, "N"),
											new Door(new int[]{15,19}, "E")});
	private static Room kitchen = new Room(Game.Room.Kitchen,
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
			for(Door door: room.getDoors()){
				board[door.coords[1]][door.coords[0]] = door;
			}
			if(room.getWarp() != null){
				board[room.getWarp().getCoords()[1]][room.getWarp().getCoords()[0]] = room.getWarp();
			}
		}

		for(Player player: players){
			int[] pos = player.getCharacter().getStartingPosition();
			board[pos[1]][pos[0]] = player;
			player.setPosition(new Point(pos[0], pos[1]));
		}
	}

	public boolean move(Player p, Point to){
		int px = (int)p.getPosition().getX(), py = (int)p.getPosition().getY();
		int tx = (int)to.getX(), ty = (int)to.getY();
		if(tx < 0 ||tx > 23 || ty < 0 || ty > 23){return false;}

		BoardObject ob = board[ty][tx];
		boolean pathSquare = ob instanceof PathSquare;
		boolean highlightedDoor = ob instanceof Door && ((Door)ob).isHighlighted();
		boolean warp = ob instanceof Warp && (Warp)ob == p.getRoom().getWarp();

		if(!(pathSquare || highlightedDoor || warp)){
			return false;
		}

		if(p.getRoom() != null){
			removePlayerFromRoom(p, p.getRoom());
		}else{
			board[py][px] = null;
		}

		if(highlightedDoor || warp){
			Room destination = warp ? ((Warp)ob).getDestination() : ((Door)ob).getRoom();
			placePlayerInRoom(p, destination);
		}else{
			board[ty][tx] = p;
			p.getPosition().setLocation(to.getX(), to.getY());
		}

		return true;
	}

	public void showPaths(int diceRoll, Player player){
		Point p = player.getPosition();
		if(player.getRoom() == null){
			showPaths(diceRoll, new int[]{(int)p.getX(), (int)p.getY()});
		}else{
			for(Door door: player.getRoom().getDoors()){
				showPaths(diceRoll, door.coords);
			}
		}
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
				((Door)board[y][x]).setHighlighted(true);
			}
		}
	}

	private void placePlayerInRoom(Player p, Room r){
		for(int[] slot: r.getPlayerSlots()){
			if(board[slot[1]][slot[0]] instanceof Room){
				board[slot[1]][slot[0]] = p;
				p.setRoom(r);
				p.getPosition().setLocation(slot[0], slot[1]);
				return;
			}
		}
	}

	private void removePlayerFromRoom(Player p, Room r){
		for(int[] slot: r.getPlayerSlots()){
			if(board[slot[1]][slot[0]] == p){
				board[slot[1]][slot[0]] = r;
				p.setRoom(null);
				return;
			}
		}
	}

	public void clearPath(){
		int boardSize = board.length;

		for(int x=0; x<boardSize; x++){
			for(int y=0; y<boardSize; y++){
				if(board[y][x] instanceof Door){
					((Door)board[y][x]).setHighlighted(false);
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
