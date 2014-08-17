package cluedo.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import cluedo.card.Card;
import cluedo.card.MurderHypothesis;
import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;

/**
 * For testing the game class.
 * @author hardwiwill
 *
 */
public class GameTests {

	private static final int TOTAL_CARDS = 21;
	private static final int MURDER_CARDS = 3;

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

	/**
	 * can't have num players > num characters
	 */
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

	/**
	 * can't be < 3 players in game
	 */
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
			new Game(sameCharacters);
			fail("can't add multiple players with same character");
		} catch (IllegalArgumentException e){
			// good
		}
	}

	/**
	 * tests if there are the right number of cards and if they are all unique
	 * set ensures that they are unique
	 */
	@Test
	public void testAllCardsPresent(){
		Game g = makeGame(3);
		Set<Card> allCards = new HashSet<Card>();
		for (Player p : g.getPlayers()){
			allCards.addAll(p.getCards());
		}
		// 21 = number of cards in game, 3 = number of cards which are the solution (not in players hand)
		assertEquals(allCards.size(), TOTAL_CARDS - MURDER_CARDS);
	}

	/**
	 * if there are 18 cards to be distributed among 4 players,
	 * 4 players would have 4 cards, 1 player has 5 cards.
	 */
	@Test
	public void testCardsEvenlyDistributed(){
		Game g1 = makeGame(3);
		for (Player p : g1.getPlayers()){
			assertEquals(p.getCards().size(), (TOTAL_CARDS-MURDER_CARDS)/3);
		}

		Game g2 = makeGame(4);
		for (Player p : g2.getPlayers()){
			assertTrue(p.getCards().size() == (TOTAL_CARDS-MURDER_CARDS)/4
					|| p.getCards().size() == (TOTAL_CARDS-MURDER_CARDS)/4+1);
		}
	}

	/**
	 * none of the murder solution cards should be in a player's hand
	 */
	@Test
	public void testSolutionIsntInPlayersHand(){
		Game g = makeGame(4);
		MurderHypothesis solution = g.getMurderSolution();
		for (Player p : g.getPlayers()){
			Set<Card> cards = p.getCards();
			if (cards.contains(solution.getCharacter())
					|| cards.contains(solution.getRoom())
					|| cards.contains(solution.getWeapon()))
				fail("player shouldn't contain muder solution cards!");
		}
	}

	/**
	 * tests getNumberOfPlayers method
	 */
	@Test
	public void testNumberOfPlayers(){
		Game g = makeGame(3);
		if (g.getPlayers().size() != g.getNumberOfPlayers()){
			fail("number of players is not correct");
		}

		g = makeGame(5);
		if (g.getPlayers().size() != g.getNumberOfPlayers()){
			fail("number of players is not correct");
		}

		g = makeGame(Game.Character.values().length);
		if (g.getPlayers().size() != g.getNumberOfPlayers()){
			fail("number of players is not correct");
		}
	}

	/**
	 * Tests the getPlayerToLeft method
	 */
	@Test
	public void testPlayerToLeft(){
		Game g = makeGame(4);
		Player first = g.getWhoseTurn();
		int firstPlayerIndex = g.getPlayers().indexOf(first);

		assertEquals(g.getPlayerToLeft(first), g.getPlayers().get(firstPlayerIndex+1%4));
	}
}
