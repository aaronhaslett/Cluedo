package cluedo.card;

import java.util.HashSet;
import java.util.Set;


public class MurderHypothesis {

	private CharacterCard character;
	private RoomCard room;
	private WeaponCard weapon;

	public MurderHypothesis(CharacterCard character, RoomCard room, WeaponCard weapon) {
		super();
		this.character = character;
		this.room = room;
		this.weapon = weapon;
	}


	public CharacterCard getCharacter() {
		return character;
	}
	public RoomCard getRoom() {
		return room;
	}
	public WeaponCard getWeapon() {
		return weapon;
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof MurderHypothesis)){
			return false;
		}
		MurderHypothesis s = (MurderHypothesis) o;

		return s.getCharacter().equals(this.getCharacter())
				&& s.getWeapon().equals(this.getWeapon())
				&& s.getRoom().equals(this.getRoom());
	}
	
	public String toString(){
		return "Murder solution: " + character + ", " + room + ", " + weapon;
	}
	
	public Set<Card> whichCardsMatch(Set<Card> cards){
		Set<Card> matching = new HashSet<Card>();
		for (Card c : cards){
			if (c.equals(character) || c.equals(room) || c.equals(weapon)){
				matching.add(c);
			}
		}
		return matching;
	}
}
