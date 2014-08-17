package cluedo.game;
import java.awt.Color;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import cluedo.card.Card;
import cluedo.piece.CharacterPiece;
import cluedo.board.*;


public class Player implements BoardObject {
	private CharacterPiece piece;
	private Set<Card> cards;
	public Point position;
	public Point draggingPosition;//The current point on the screen while being dragged
	public Room room;//The room the player is currently in.  Null by default.

	public Player(CharacterPiece piece){
		cards = new HashSet<Card>();
		this.piece = piece;
	}

	/**
	 * Gives a card to the player.
	 * @param card
	 */
	public void giveCard(Card card) {
		cards.add(card);
	}

	public Set<Card> getCards(){
		return cards;
	}

	public String toString(){
		return piece.toString();
	}

	public CharacterPiece getCharacter(){
		return piece;
	}

	public Color getColour() {
		return piece.getColour();
	}

	public Point getPosition(){
		return position;
	}

	public CharacterPiece getPiece(){
		return piece;
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof Player))
			return false;

		Player other = (Player)o;

		if (this.piece.equals(other.getPiece()))			
			return true;
		else return false;
	}

}
