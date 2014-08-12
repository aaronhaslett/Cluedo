package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import cluedo.card.Card;
import cluedo.game.Controller;
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

	public PlayerUIPanel(Controller control){
		setPreferredSize(new Dimension(Window.WINDOW_SIZE.width, 200));
		setLayout(new BorderLayout());
		setBackground(Color.MAGENTA);

		JPanel buttonPanel = new JPanel();
		JButton endTurnButton = new JButton("End turn");

		buttonPanel.setLayout(new FlowLayout());
		endTurnButton.addActionListener(control.new EndTurnButtonListener());
		buttonPanel.add(endTurnButton);
		add(buttonPanel, BorderLayout.WEST);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if (currentPlayer == null) return; // if no player is selected, don't draw anything

		final int CARD_REGION_LEFT = 10;

		// draws cards
		int cardNumber = 0;
		int cardWidth = 150;
		for (Card card : currentPlayer.getCards()){
			g.drawString(card.toString(), CARD_REGION_LEFT + cardNumber*cardWidth, 50);
			cardNumber++;
		}
	}

	public void updatePlayerTurn(Player p) {
		currentPlayer = p;
	}

	/**
	 *  depends on the player's character
	 */
	private void setBackgroundColour(){

	}
}
