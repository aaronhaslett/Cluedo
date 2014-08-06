package cluedo.piece;

import cluedo.game.Game;
import cluedo.game.Game.Character;

public class CharacterPiece implements Piece{

	private Game.Character character;

	public CharacterPiece(Game.Character character){
		this.character = character;
	}

	public String toString(){
		return character.name();
	}

}
