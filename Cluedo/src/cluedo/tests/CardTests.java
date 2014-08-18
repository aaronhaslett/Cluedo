package cluedo.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;
import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;

public class CardTests {

	@Test
	public void testEquals(){
		Card c1 = new CharacterCard(Game.Character.ColMustard);
		Card c2 = new CharacterCard(Game.Character.ColMustard);
		assertTrue(c1.equals(c2));

		Card r1 = new RoomCard(Game.Room.BilliardRoom);
		Card r2 = new RoomCard(Game.Room.BilliardRoom);
		assertTrue(r1.equals(r2));

		Card w1 = new WeaponCard(Game.Weapon.CandleStick);
		Card w2 = new WeaponCard(Game.Weapon.CandleStick);
		assertTrue(w1.equals(w2));
	}

	@Test
	public void testNotEquals(){
		Card c1 = new CharacterCard(Game.Character.ColMustard);
		Card c2 = new CharacterCard(Game.Character.MissScarlet);
		assertFalse(c1.equals(c2));

		Card r1 = new RoomCard(Game.Room.BilliardRoom);
		Card r2 = new RoomCard(Game.Room.DiningRoom);
		assertFalse(r1.equals(r2));

		Card w1 = new WeaponCard(Game.Weapon.CandleStick);
		Card w2 = new WeaponCard(Game.Weapon.Spanner);
		assertFalse(w1.equals(w2));
	}

	@Test
	public void testMurderHypothesisEquals(){
		MurderHypothesis s1 = new MurderHypothesis(new CharacterCard(Game.Character.ColMustard),
				new RoomCard(Game.Room.BilliardRoom),
				new WeaponCard(Game.Weapon.Dagger));

		MurderHypothesis s2 = new MurderHypothesis(new CharacterCard(Game.Character.ColMustard),
				new RoomCard(Game.Room.BilliardRoom),
				new WeaponCard(Game.Weapon.Dagger));
		assertTrue(s1.equals(s2));
	}

	@Test
	public void testMurderHypothesisNotEqual(){
		MurderHypothesis s1 = new MurderHypothesis(new CharacterCard(Game.Character.ColMustard),
				new RoomCard(Game.Room.BilliardRoom),
				new WeaponCard(Game.Weapon.Dagger));

		MurderHypothesis s2 = new MurderHypothesis(new CharacterCard(Game.Character.ColMustard),
				new RoomCard(Game.Room.BilliardRoom),
				new WeaponCard(Game.Weapon.Revolver));
		assertFalse(s1.equals(s2));
	}

	/**
	 * checks if all the card images
	 * testing actually crashes if this doesn't pass
	 */
	@Test
	public void testImagesReadCorrectly(){

		try{
			for (Game.Character character : Game.Character.values()){
				new CharacterCard(character).getBufferedImage();
			}
			for (Game.Room room : Game.Room.values()){
				if (room == Game.Room.Cellar){
					// cellar does not have an image
					continue;
				}
				new RoomCard(room).getBufferedImage();
			}
			for (Game.Weapon weapon : Game.Weapon.values()){
				new WeaponCard(weapon).getBufferedImage();
			}
		} catch (Exception e){
			fail("card file reading failed");
		}
	}

}

