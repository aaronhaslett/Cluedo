package cluedo.game;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderSolution;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;


public class Game {

	public static final String RES_PATH = "res"+File.separator;

	public static enum Character {MissScarlet, ProfPlum, MrsPeacock, RevGreen, ColMustard, MrsWhite};
	public static enum Weapon {CandleStick, Dagger, LeadPipe, Revolver, Rope, Spanner};
	public static enum Room {Kitchen, Ballroom, Conversatory, BilliardRoom, Library, Study, Hall, Lounge, DiningRoom};

	/*private Set<CluedoCharacter> characters;
	private Set<CluedoRoom> rooms;
	private Set<CluedoWeapon> weapons;*/ // SOON

	//private Board board; // not implemented yet
	private List<Player> players;
	private MurderSolution murder;
	private Player turn;

	public Game(List<Player> players){
		this.players = players;
		setUpCards();
		// sets the first player to be the 'turn owner'
		turn = players.get(0);
	}


	/**
	 * Sets up game solution (random murderer, weapon and room),
	 * Deals cards evenly to all players
	 */
	private void setUpCards(){

		// pick murderer, weapon and room
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

		CharacterCard murderer = characterCards.get((int) (characterCards.size()*Math.random()));
		WeaponCard murderWeapon = weaponCards.get((int) (weaponCards.size()*Math.random()));
		RoomCard murderRoom = roomCards.get((int) (roomCards.size()*Math.random()));
		characterCards.remove(murderer);
		weaponCards.remove(murderWeapon);
		roomCards.remove(murderRoom);

		murder = new MurderSolution(murderer, murderRoom, murderWeapon);

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
			    c = c + 1;
			}

			players.get(i%players.size()).giveCard(random);
			allCards.remove(random);
		}

	}

	public void makeSuggestion(Player p, MurderSolution m){

	}

	private Player getPlayerToLeft(Player p){
		return players.get((players.indexOf(p)+1)%players.size());
	}

	private boolean isAccusationCorrect(MurderSolution m){
		return murder.equals(m);
	}

	/**
	 * Sets the player to the lefts turn
	 */
	public void nextTurn(){
		turn = players.get((players.indexOf(turn)+1)%players.size());
	}

	public Player getWhosTurn(){
		return turn;
	}

	public int rollDice(){
		int d1 = (int)(Math.random()*6);
		int d2 = (int)(Math.random()*6);
		return d1 + d2;
	}

}
