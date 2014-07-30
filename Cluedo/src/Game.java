import java.util.Set;


public class Game {

	public static enum Weapon {CandleStick, Dagger, LeadPipe, Revolver, Rope, Spanner};
	public static enum Character {MissScarlet, ProfPlum, MrsPeacock, RevGreen, ColMustard, MrsWhite};
	public static enum Room {Kitchen, Ballroom, Conversatory, BilliardRoom, Library, Study, Hall, Lounge, DiningRoom, Cellar };

	//private Board board;
	private Set<Player> players;
	private Solution solution;

	
	/**
	 * Sets up game solution (murderer, weapon and room),
	 * deals cards
	 */
	private void setupGame(){
		Weapon murderWeapon = Weapon.values()[(int)(Math.random()*Weapon.values().length)];
		Character murderer = Character.values()[(int)(Math.random()*Character.values().length)];
		Room murderRoom = Room.values()[(int)(Math.random()*Room.values().length)];
		
		solution = new Solution(murderer, murderRoom, murderWeapon);
		
		
	}
}
