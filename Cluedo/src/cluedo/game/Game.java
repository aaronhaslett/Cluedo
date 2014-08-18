package cluedo.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;
import cluedo.util.TwoDice;


/**
 * @author hardwiwill
 * Model for a cluedo game
 * Contains the game state and all Model elements for a game of cluedo
 */
public class Game {

	public static enum Character {MissScarlet, ProfPlum, MrsPeacock, RevGreen, ColMustard, MrsWhite};
	public static enum Weapon {CandleStick, Dagger, LeadPipe, Revolver, Rope, Spanner};
	public static enum Room {Kitchen, BallRoom, Conservatory, BilliardRoom, Library, Study, Hall, Lounge, DiningRoom, Cellar};

	/*private Set<CluedoCharacter> characters;
	private Set<CluedoRoom> rooms;
	private Set<CluedoWeapon> weapons;*/ // SOON

	/**
	 * preserves initial ordering (in order of choosing) of players
	 */
	private List<Player> players;
	private MurderHypothesis murderSolution;
	private Player turn;
	/**
	 * if the dice has been rolled this turn or not
	 */
	private boolean diceRolled = false;

	private int dice1Value;
	private int dice2Value;
	private TwoDice dice;

	/**
	 * Initialises the game state and model elements.
	 * PRE: must be 3 <= number of players <= number of characters
	 * PRE: all characters are unique
	 * @param players
	 */
	public Game(List<Player> players){
		if (players.size() < 3 || players.size() > Character.values().length){
			throw new IllegalArgumentException("incorrect number of players");
		}
		if (new HashSet<Player>(players).size() < players.size()){
			throw new IllegalArgumentException("can't have two players with the same character");
		}

		this.players = players;
		setUpCards();
		dice = new TwoDice();
		// sets the first player to be the 'turn owner'
		turn = players.get(0);
	}


	/**
	 * Adds all cards to the game,
	 * Sets up game solution (random murderer, weapon and room),
	 * Deals cards evenly to all players
	 */
	private void setUpCards(){

		// set up card collections
		List<CharacterCard> characterCards = new ArrayList<CharacterCard>();
		List<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
		List<RoomCard> roomCards = new ArrayList<RoomCard>();

		for (int i = 0; i < Character.values().length; i++){
			characterCards.add(new CharacterCard(Character.values()[i]));
		}
		for (int i = 0; i < Weapon.values().length; i++){
			weaponCards.add(new WeaponCard(Weapon.values()[i]));
		}
		for (int i = 0; i < Room.values().length; i++){
			roomCards.add(new RoomCard(Room.values()[i]));
		}

		// pick murderer, weapon and room
		CharacterCard murderer = characterCards.get((int) (characterCards.size()*Math.random()));
		WeaponCard murderWeapon = weaponCards.get((int) (weaponCards.size()*Math.random()));
		RoomCard murderRoom = roomCards.get((int) (roomCards.size()*Math.random()));

		murderSolution = new MurderHypothesis(murderer, murderRoom, murderWeapon);

		// remove murder cards from deck
		characterCards.remove(murderer);
		weaponCards.remove(murderWeapon);
		roomCards.remove(murderRoom);

		// distribute cards
		Set<Card> allCards = new HashSet<Card>();

		allCards.addAll(characterCards);
		allCards.addAll(weaponCards);
		allCards.addAll(roomCards);

		assert allCards.size() == 21-3; // all cards minus 3 for solution.

		// deal cards to all players!
		for (int i = 0; !allCards.isEmpty(); i++){

			// way of getting random elements from a set
			int item = new Random().nextInt(allCards.size());
			int c = 0;
			Card random = null;
			for(Card card : allCards){
			    if (c == item){
			        random = card;
			    }
			    c++;
			}

			players.get(i%players.size()).giveCard(random);
			allCards.remove(random);
		}
	}

	/**
	 * @param m
	 * @return whether the murder hypothesis is the same as the game solution
	 */
	public boolean isAccusationCorrect(MurderHypothesis m){
		return murderSolution.equals(m);
	}

	/**
	 * Sets the player to the lefts' turn
	 */
	public void nextTurn(){
		turn = getPlayerToLeft(turn);
	}

	public Player getPlayerToLeft(Player p) {
		return players.get((players.indexOf(p)+1)%players.size());
	}


	public Player getWhoseTurn(){
		return turn;
	}

	public void removePlayerFromGame(Player p){
		players.remove(p);
	}

	/**
	 * generates a value with the same range and distribution as
	 * the sum of the result of two random dice values
	 */
	public void rollDice(){
		dice.roll();
	}

	public int getDiceValue(){
		return dice.getTotal();
	}

	public TwoDice getDice(){
		return dice;
	}

	public int getNumberOfPlayers(){
		return players.size();
	}

	public List<Player> getPlayers(){
		return players;
	}

	public MurderHypothesis getMurderSolution(){
		return murderSolution;
	}

	/**
	 * @return has the dice rolled?
	 */
	public boolean hasRolled() {
		return diceRolled;
	}


	/**
	 * set whether the dice has rolled
	 * @param rolled
	 */
	public void setRolled(boolean rolled) {
		diceRolled = rolled;
	}

}
