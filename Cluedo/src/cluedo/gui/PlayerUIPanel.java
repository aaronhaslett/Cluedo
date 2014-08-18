package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cluedo.card.Card;
import cluedo.game.Controller;
import cluedo.game.Player;
import cluedo.util.TwoDice;
import cluedo.util.Util;

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

	public static final int PANEL_HEIGHT = 220;
	public static final Dimension CARD_PANEL_SIZE = new Dimension(700, PANEL_HEIGHT);
	public static final Dimension DICE_SIZE = new Dimension(50,100);
	public static final Dimension UI_PANEL_SIZE = new Dimension(CARD_PANEL_SIZE.width + DICE_SIZE.width, PANEL_HEIGHT);

	private JLabel playerNameLabel;
	private JPanel cardPanel;
	private JPanel buttonPanel;
	private DiceIcon diceIcon;

	/**
	 * Initialised UI components
	 * @param control
	 */
	public PlayerUIPanel(Controller control){
		setPreferredSize(UI_PANEL_SIZE);
		setLayout(new BorderLayout());

		playerNameLabel = new JLabel("", SwingConstants.CENTER);
		playerNameLabel.setOpaque(true);
		playerNameLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
		add(playerNameLabel, BorderLayout.NORTH);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.white);

		JButton endTurnButton = new JButton("End turn");
		endTurnButton.addActionListener(control.new EndTurnButtonListener());
		buttonPanel.add(endTurnButton);

		JButton accusationButton = new JButton("Make accusation");
		accusationButton.addActionListener(control.new AccusationButtonListener());
		buttonPanel.add(accusationButton);


		JButton suggestionButton = new JButton("Make suggestion");
		suggestionButton.addActionListener(control.new SuggestionButtonListener());
		buttonPanel.add(suggestionButton);


		diceIcon = new DiceIcon(Util.DICE_IMAGE_PATH, DICE_SIZE);
		JButton diceButton = new JButton(diceIcon);
		diceButton.addActionListener(control.new DiceButtonListener());
		buttonPanel.add(diceButton);

		add(buttonPanel, BorderLayout.WEST);

		cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout());
		cardPanel.setPreferredSize(CARD_PANEL_SIZE);
		cardPanel.setBackground(Color.white);
		add(cardPanel, BorderLayout.CENTER);
	}

	/**
	 * Goes to the next turn. Updates UI
	 * @param p
	 */
	public void updatePlayerTurn(Player p) {
		playerNameLabel.setText("Player: " + p);
		playerNameLabel.setBackground(p.getColour());

		// update cards

		cardPanel.removeAll();

		final int CARD_VERT_BORDER_GAP = 30;
		final int CARD_HEIGHT = cardPanel.getSize().height - CARD_VERT_BORDER_GAP;
		final int CARD_WIDTH = (int)(CARD_HEIGHT*Card.WIDTH_RATIO);

		for (Card card : p.getCards()){
			BufferedImage cardImage = card.getBufferedImage();
			BufferedImage resizedImage = Util.imageResize(cardImage, CARD_WIDTH, CARD_HEIGHT);
			cardPanel.add(new JLabel(new ImageIcon(resizedImage)));
		}
		// must be here to draw cards first time
		validate();
	}

	/**
	 * Updates the dice images on the dice button
	 * @param dice
	 */
	public void updateDice(TwoDice dice) {
		diceIcon.updateDiceImages(dice.getDice1Value(), dice.getDice2Value());
	}

	/**
	 * @author hardwiwill
	 * an icon with 2 images in it
	 * each are a the face of one dice
	 */
	public class DiceIcon extends ImageIcon {

		private final String PATH;
		private final String IMG_EXTENSION = ".png";

		private final Dimension ICON_SIZE;

	    private ImageIcon dice1;
	    private ImageIcon dice2;


	    public DiceIcon(String path, Dimension size) {
	    	// set initial dice values
	    	ICON_SIZE = size;
	    	this.PATH = path+"dice";
	        setImages(6,6);
	    }

	    public void updateDiceImages(int d1, int d2){
	    	setImages(d1, d2);
	    }

	    private void setImages(int d1, int d2){
	    	BufferedImage d1Image=null;
	    	BufferedImage d2Image=null;
	    	try{
		    	d1Image = ImageIO.read(new File(PATH+d1+IMG_EXTENSION));
		    	d2Image = ImageIO.read(new File(PATH+d2+IMG_EXTENSION));
	    	} catch (IOException e){
	    		e.printStackTrace();
	    	}

	    	d1Image = Util.imageResize(d1Image, (int)(ICON_SIZE.getWidth()), (int)(ICON_SIZE.getHeight()/2));
	    	d2Image = Util.imageResize(d2Image, (int)(ICON_SIZE.getWidth()), (int)(ICON_SIZE.getHeight()/2));

	    	this.dice1 = new ImageIcon(d1Image);
	    	this.dice2 = new ImageIcon(d2Image);
	    }

	    public int getIconHeight() {
	        return dice1.getIconWidth()+dice2.getIconWidth();
	    }

	    public int getIconWidth() {
	        return dice1.getIconWidth();
	    }

	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	    	//System.out.println("PATH: "+PATH);
	        dice1.paintIcon(c, g, x, y);
	        dice2.paintIcon(c, g, x, y+dice1.getIconHeight());
	    }
	}
}
