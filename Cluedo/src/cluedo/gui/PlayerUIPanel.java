package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.Main;
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
	private JLabel playerLabel;
	private JPanel cardPanel;

	public PlayerUIPanel(Controller control){
		setPreferredSize(new Dimension(Window.WINDOW_SIZE.width, 200));
		setLayout(new BorderLayout());
		setBackground(Color.MAGENTA);

		playerLabel = new JLabel();
		add(playerLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton endTurnButton = new JButton("End turn");
		endTurnButton.addActionListener(control.new EndTurnButtonListener());
		buttonPanel.add(endTurnButton);
		JButton accusationButton = new JButton("Make accusation");
		accusationButton.addActionListener(control.new AccusationButtonListener());
		buttonPanel.add(accusationButton);
		add(buttonPanel, BorderLayout.WEST);

		cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout());
		add(cardPanel, BorderLayout.EAST);

	}

	/*public void paintComponent(Graphics g){
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
	}*/

	public void updatePlayerTurn(Player p) {
		currentPlayer = p;
		playerLabel.setText("Player: " + currentPlayer);

		// update cards
		cardPanel.removeAll();
		Set<Card> playerCards = p.getCards();
		final int CARD_WIDTH =  playerCards.size(); // TODO: scale image
		for (Card card : p.getCards()){
			cardPanel.add(new JLabel(new ImageIcon("Spanner.jpg")));
			//cardPanel.add(new JLabel(new ImageIcon(card.getImage())));
		}
	}

	/**
	 *  depends on the player's character
	 */
	private void setBackgroundColour(){

	}
}
