package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.game.Controller;
import cluedo.game.Game;
import cluedo.game.Player;

public class ControllerTests {

	/**
	 * checks if the turn is passed on to the next player correctly
	 */
	@Test
	public void testNextTurn(){
		Controller c = new Controller();
		Game game = c.getGame();
		Player currentPlayer = game.getWhoseTurn();
		int firstPlayerIndex = game.getPlayers().indexOf(currentPlayer);

		for (int i = firstPlayerIndex; i < firstPlayerIndex+game.getNumberOfPlayers(); i++){
			c.nextTurn();
			Player nextPlayer = game.getWhoseTurn();
			if (currentPlayer.equals(nextPlayer)){
				fail("turn moved to same player");
			}
			if (!game.getPlayerToLeft(currentPlayer).equals(nextPlayer)){
				fail("turn didn't move to the left");
			}
			currentPlayer = nextPlayer;
		}
		if (!game.getPlayers().get(firstPlayerIndex).equals(currentPlayer)){
			fail("doesn't cycle back to start");
		}
	}

}
