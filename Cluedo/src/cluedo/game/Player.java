package cluedo.game;
import java.util.HashSet;
import java.util.Set;

import cluedo.card.Card;
import cluedo.piece.CharacterPiece;
import java.awt.Point;


public class Player {
	private CharacterPiece piece;
	private Set<Card> cards;
	public Point position;

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

}
