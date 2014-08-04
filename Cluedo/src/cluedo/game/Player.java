package cluedo.game;
import java.util.HashSet;
import java.util.Set;

import cluedo.card.Card;
import cluedo.piece.CharacterPiece;



public class Player {
	private CharacterPiece piece;
	private Set<Card> cards;

	public Player(CharacterPiece piece){
		cards = new HashSet<Card>();
		this.piece = piece;
	}

	public void giveCard(Card card) {
		cards.add(card);
	}

	public Set<Card> getCards(){
		return cards;
	}

	public String toString(){
		return this.getClass().getName() + " Piece: " + piece;
	}
}
