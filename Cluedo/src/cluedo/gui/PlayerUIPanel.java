package cluedo.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import cluedo.card.Card;
import cluedo.game.Player;

/**
 * UI for
 * 	- player's cards
 * 	- making suggestions/accusations
 * 	- ending turn
 *  - (maybe) rolling dice
 * @author hardwiwill
 *
 */
public class PlayerUIPanel extends JPanel{

	private Player currentPlayer;

	public PlayerUIPanel(){
		setPreferredSize(new Dimension(CluedoWindow.WINDOW_SIZE.width, 200));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (currentPlayer == null) return; // if no player is selected, don't draw anything

		final int CARD_REGION_LEFT = CluedoWindow.WINDOW_SIZE.width/2;

		// draws cards
		int cardNumber = 0;
		int cardWidth = 50;
		for (Card card : currentPlayer.getCards()){
			g.drawString(card.toString(), CARD_REGION_LEFT + cardNumber*cardWidth, 0);
		}
	}

	/**
	 * @param p: player whose turn it is.
	 */
	public void setCurrentPlayer(Player p){
		this.currentPlayer = p;
	}

	/**
	 *  depends on the player's character
	 */
	private void setBackgroundColour(){

	}
}
