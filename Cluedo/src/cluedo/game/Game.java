package cluedo.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.RoomCard;
import cluedo.card.Solution;
import cluedo.card.WeaponCard;
import cluedo.gui.CluedoWindow;
import cluedo.piece.CharacterPiece;


public class Game {

	public static enum Character {MissScarlet, ProfPlum, MrsPeacock, RevGreen, ColMustard, MrsWhite};
	public static enum Weapon {CandleStick, Dagger, LeadPipe, Revolver, Rope, Spanner};
	public static enum Room {Kitchen, Ballroom, Conversatory, BilliardRoom, Library, Study, Hall, Lounge, DiningRoom};

	//private Board board; // not implemented yet
	private List<Player> players;
	private Solution murderInfo;

	public Game(){
		players = new ArrayList<Player>();
		players.add(new Player(new CharacterPiece(Character.MissScarlet)));
		players.add(new Player(new CharacterPiece(Character.ProfPlum)));
		setUpCards();
		new CluedoWindow();
	}


	/**
	 * Sets up game solution (random murderer, weapon and room),
	 * Deals cards evenly to all players
	 */
	private void setUpCards(){

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

		murderInfo = new Solution(murderer, murderRoom, murderWeapon);


		Set<Card> allCards = new HashSet<Card>();

		allCards.addAll(characterCards);
		allCards.addAll(weaponCards);
		allCards.addAll(roomCards);

		assert allCards.size() == 21-3; // all cards minus 3 for solution.

		// deal cards!
		for (int c = 0; !allCards.isEmpty(); c++){
			// way of getting random elements from a set
			int item = new Random().nextInt(allCards.size());

			int i = 0;
			Card random = null;
			for(Card card : allCards){
			    if (i == item)
			        random = card;
			    i = i + 1;
			}

			players.get(c%players.size()).dealCard(random);
			allCards.remove(random);
		}

	}

	private boolean isSolutionCorrect(Solution s){
		return murderInfo.equals(s);
	}
}
