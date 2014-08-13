package cluedo.piece;

import cluedo.game.Game;
import cluedo.game.Game.Character;
import cluedo.board.BoardObject;
import java.awt.Color;

public class CharacterPiece implements Piece, BoardObject{

	private Game.Character character;
	private int[] startingPosition = {5,6};
	private Color colour;

	public CharacterPiece(Game.Character character){
		this.character = character;

		// decide colour
		switch (character) {
			case MissScarlet:
				startingPosition = new int[]{16,0};
				colour = Color.red; break;
			case ProfPlum:
				startingPosition = new int[]{0,5};
				colour = new Color(190, 60, 220); break;
			case MrsPeacock:
				startingPosition = new int[]{0,18};
				colour = Color.blue; break;
			case RevGreen:	
				startingPosition = new int[]{9,23};
				colour = Color.green; break;
			case ColMustard:	
				startingPosition = new int[]{23,7};
				colour = Color.yellow; break;
			case MrsWhite:	
				startingPosition = new int[]{14,23};
				colour = Color.white; break;
		}
	}

	public int[] getStartingPosition(){
		return startingPosition;
	}

	public Color getColour(){
		return colour;
	}

	public String toString(){
		return character.name();
	}
}
