package cluedo.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;

/**
 * For testing the game class.
 * @author hardwiwill
 *
 */
public class GameTests {

	/**
	 * helper method to make generic start game.
	 * @return
	 */
	public Game makeGame(int numPlayers){

		return new Game(getPlayers(numPlayers));
	}

	public List<Player> getPlayers(int numPlayers){
		List<Player> players = new ArrayList<Player>();
		for (int i=0; i < numPlayers; i++){
			Game.Character character = Game.Character.values()[i];
			players.add(new Player(new CharacterPiece(character)));
		}
		return players;
	}

	@Test
	public void testAddingCorrectPlayers1(){
		Game g = makeGame(3);
		assertEquals(g.getNumberOfPlayers(), 3);
	}

	@Test
	public void testAddingCorrectPlayers2(){
		Game g = makeGame(Game.Character.values().length);
		assertEquals(g.getNumberOfPlayers(), Game.Character.values().length);
	}

	@Test
	public void testAddingIncorrectPlayers1(){
		try{
			List<Player> players = getPlayers(Game.Character.values().length);
			// extra duplicate character
			players.add(new Player(new CharacterPiece(Game.Character.MissScarlet)));
			new Game(players);
			fail("can't add more players than characters");
		} catch (IllegalArgumentException e){
			// good
		}
	}

	@Test
	public void testAddingIncorrectPlayers2(){
		try{
			makeGame(2);
			fail("can't have < 3 players in game");
		} catch (IllegalArgumentException e){
			// good
		}
	}

	/**
	 * adds players with the same character
	 */
	@Test
	public void testAddingIncorrectPlayers3(){
		try{
			List<Player> sameCharacters = new ArrayList<Player>();
			sameCharacters.add(new Player(new CharacterPiece(Game.Character.MrsPeacock)));
			sameCharacters.add(new Player(new CharacterPiece(Game.Character.MrsPeacock)));
			fail("can't add multiple players with same character");
		} catch (IllegalArgumentException e){
			// good
		}
	}



}
