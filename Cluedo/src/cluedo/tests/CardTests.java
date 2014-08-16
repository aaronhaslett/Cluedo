package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.game.Game;

public class CardTests {

	@Test
	public void testEquals(){
		Card c1 = new CharacterCard(Game.Character.ColMustard);
		Card c2 = new CharacterCard(Game.Character.ColMustard);
		assertTrue(c1.equals(c2));
	}

	/*@Test
	public void setTest(){
		Set<Card>
	}*/

}
