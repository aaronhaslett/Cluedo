package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cluedo.card.Card;
import cluedo.game.Controller;
import cluedo.game.Player;
import cluedo.game.Util;

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

	public static final Dimension UI_PANEL_SIZE = new Dimension(Window.WINDOW_SIZE.width, 200);
	public static final Dimension CARD_PANEL_SIZE =
			new Dimension((int)(UI_PANEL_SIZE.width*0.8), UI_PANEL_SIZE.height);

	private Player currentPlayer;
	private JLabel playerLabel;
	private JPanel cardPanel;

	public PlayerUIPanel(Controller control){
		setPreferredSize(UI_PANEL_SIZE);
		setLayout(new BorderLayout());

		playerLabel = new JLabel();
		playerLabel.setOpaque(true);
		add(playerLabel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		JButton endTurnButton = new JButton("End turn");
		endTurnButton.addActionListener(control.new EndTurnButtonListener());
		buttonPanel.add(endTurnButton);
		JButton accusationButton = new JButton("Make accusation");
		accusationButton.addActionListener(control.new AccusationButtonListener());
		buttonPanel.add(accusationButton);
		add(buttonPanel, BorderLayout.WEST);

		cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout());
		cardPanel.setPreferredSize(CARD_PANEL_SIZE);
		add(cardPanel, BorderLayout.CENTER);

	}

	/**
	 * Goes to the next turn. Updates game state and refreshes UI
	 * @param p
	 */
	public void updatePlayerTurn(Player p) {
		currentPlayer = p;
		playerLabel.setText("Player: " + currentPlayer);


		// update cards
		cardPanel.removeAll();
		Set<Card> playerCards = p.getCards();

		final int CARD_WIDTH =  (UI_PANEL_SIZE.width -
				((FlowLayout)cardPanel.getLayout()).getHgap()*playerCards.size())
				/ playerCards.size(); // TODO: scale image
		final int CARD_HEIGHT = (int)(CARD_WIDTH*Card.HEIGHT_RATIO);

		for (Card card : p.getCards()){
			BufferedImage cardImage = card.getBufferedImage();
			BufferedImage resizedImage = Util.imageResize(cardImage, CARD_WIDTH, CARD_HEIGHT);
			cardPanel.add(new JLabel(new ImageIcon(resizedImage)));
		}
		playerLabel.setBackground(p.getColour());
		cardPanel.repaint();
	}
}
