package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.game.Game;
import cluedo.piece.CharacterPiece;

public class PieceTests {

	@Test
	public void testEquals(){
		CharacterPiece c1 = new CharacterPiece(Game.Character.ColMustard);
		CharacterPiece c2 = new CharacterPiece(Game.Character.ColMustard);
		
		assertFalse(c1.equals(c2));
	}
	
	@Test
	public void testNotEquals(){
		CharacterPiece c1 = new CharacterPiece(Game.Character.ColMustard);
		CharacterPiece c2 = new CharacterPiece(Game.Character.MissScarlet);
		
		assertFalse(c1.equals(c2));
	}
}
