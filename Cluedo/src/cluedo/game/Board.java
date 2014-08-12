package cluedo.game;

public class Board{

	public static int boardSize = 24;
	private int[][] board = new int[boardSize][boardSize];

	public static Room study = new Room("Study",
								new int[][][]{{{0,0},{5,3}}, {{6,1},{6,3}}},
								new int[][]{{6,3}});
	public static Room hall = new Room("Hall",
								new int[][][]{{{9,0},{14,6}}},
								new int[][]{{9,4},{11,6},{12,6}});
	public static Room lounge = new Room("Lounge",
								new int[][][]{{{17,0},{24,5}}},
								new int[][]{{17,5}});
	public static Room library = new Room("Library",
								new int[][][]{{{0,6},{5,10}}, {{6,7},{6,9}}},
								new int[][]{{2,10},{6,8}});
	public static Room billiardRoom = new Room("Billiard Room",
								new int[][][]{{{0,12},{5,16}}},
								new int[][]{{1,12},{5,15}});
	public static Room diningRoom = new Room("Dining Room",
								new int[][][]{{{16,9},{18,14}}, {{19,9},{23,15}}},
								new int[][]{{17,9},{16,12}});
	public static Room conservatory = new Room("Conservatory",
								new int[][][]{{{0,19},{5,23}}, {{1,18},{4,18}}},
								new int[][]{{4,18}});
	public static Room ballroom = new Room("Ballroom",
								new int[][][]{{{8,16},{15,21}}, {{10,22},{13,23}}},
								new int[][]{{8,18},{9,16},{14,16},{15,18}});
	public static Room kitchen = new Room("Kitchen",
								new int[][][]{{{18,17},{23,23}}},
								new int[][]{{19,17}});
	public static Room[] rooms = {study, hall, lounge, library, billiardRoom, diningRoom, conservatory, ballroom, kitchen};

	public Board(){
		study.setWarp(new int[]{0,3}, kitchen);
		kitchen.setWarp(new int[]{18,23}, study);
		conservatory.setWarp(new int[]{1,18}, lounge);
		lounge.setWarp(new int[]{23,5}, conservatory);
	}

}
