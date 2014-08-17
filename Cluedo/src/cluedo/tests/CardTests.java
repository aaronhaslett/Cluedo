package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;
import cluedo.game.Game;

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

}
