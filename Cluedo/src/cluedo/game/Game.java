package cluedo.game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.RoomCard;
import cluedo.card.MurderSolution;
import cluedo.card.WeaponCard;
import cluedo.gui.CluedoWindow;


public class Game {

	public static enum Character {MissScarlet, ProfPlum, MrsPeacock, RevGreen, ColMustard, MrsWhite};
	public static enum Weapon {CandleStick, Dagger, LeadPipe, Revolver, Rope, Spanner};
	public static enum Room {Kitchen, Ballroom, Conversatory, BilliardRoom, Library, Study, Hall, Lounge, DiningRoom};

	//private Board board; // not implemented yet
	private Set<Player> players;
	private MurderSolution murder;
	private CluedoWindow gameWindow;

	public Game(){
		players = new HashSet<Player>();
		gameWindow = new CluedoWindow(this);
		System.out.println(Arrays.toString(players.toArray()));
		setUpCards();
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

		// deal cards!

		for (Iterator<Player> it = players.iterator(); !allCards.isEmpty();){
			// way of getting random elements from a set
			int item = new Random().nextInt(allCards.size());

			int i = 0;
			Card random = null;
			for(Card card : allCards){
			    if (i == item){
			        random = card;
			    }
			    i = i + 1;
			}

			//players.get(c%players.size()).giveCard(random);
			// THIS MAY NOT WORK
			if (!it.hasNext()){
				it = players.iterator();
			}
			it.next().giveCard(random);
			allCards.remove(random);
		}

		for (Player p : players){
			System.out.println(Arrays.toString(p.getCards().toArray()));
		}

	}

	private boolean isAccusationCorrect(MurderSolution s){
		return murder.equals(s);
	}

	/**
	 * Adds a player to the game.
	 * @param p
	 */
	public void addPlayer(Player p){
		this.players.add(p);
	}

	public int getNoOfPlayers(){
		return players.size();
	}
}
