package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.card.Card;
import cluedo.card.MurderHypothesis;
import cluedo.game.Player;

/**
 * @author will
 * View element
 * Cycles through all players to see if any can refute a suggestion by a player
 */
public class SuggestDialogs {
	
	/**
	 * @param player
	 * @param suggestion
	 * @param matchingCards
	 * @return a card to refute a suggestion
	 * Asks a player to choose a card to refute a suggestion
	 */
	public static Card showCanRefuteDialog(Player player, MurderHypothesis suggestion, List<Card> matchingCards){
		
		final String REFUTE_TEXT = player.getCharacter().toString()+", you can refute this accusation";
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		
		JLabel text = new JLabel(REFUTE_TEXT);
		dialogPanel.add(text, BorderLayout.NORTH);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new FlowLayout());
		
		ButtonGroup buttongroup = new ButtonGroup();
		//List<JRadioButton> cardButtons = new ArrayList<JRadioButton>();

		// makes radio button for each character
		Set<Card> suggestionCards = suggestion.getCards();
		for (Card c : suggestionCards){
			JRadioButton cardButton = new JRadioButton(new ImageIcon(c.getBufferedImage()));
			cardButton.setSelected(true);
			cardButton.setActionCommand(matchingCards.indexOf(c)+"");
			cardsPanel.add(cardButton);
			buttongroup.add(cardButton);
			if (!matchingCards.contains(c)){
				cardButton.setEnabled(false);
			}
		}
		
		cardsPanel.validate();
		dialogPanel.add(cardsPanel, BorderLayout.CENTER);
		
		JOptionPane.showMessageDialog(null, dialogPanel, "Refute suggestion", JOptionPane.OK_OPTION);
		
		int cardChosenIndex = Integer.parseInt(buttongroup.getSelection().getActionCommand());
		return matchingCards.get(cardChosenIndex);
	}
	
	/**
	 * @param player
	 * @param suggestion
	 * shows a dialog box displaying a players suggestion and informing
	 * the current player that they cannot refute this suggestion.
	 */
	public static void showCantRefuteDialog(Player player, MurderHypothesis suggestion){

		final String REFUTE_TEXT = player.getCharacter().toString()+", you can't refute this accusation";
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		
		JLabel text = new JLabel(REFUTE_TEXT);
		dialogPanel.add(text, BorderLayout.NORTH);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new FlowLayout());
		
		JLabel characterLabel = new JLabel(new ImageIcon(suggestion.getCharacter().getBufferedImage()));
		JLabel weaponLabel = new JLabel(new ImageIcon(suggestion.getWeapon().getBufferedImage()));
		JLabel roomLabel = new JLabel(new ImageIcon(suggestion.getRoom().getBufferedImage()));
		cardsPanel.add(characterLabel);
		cardsPanel.add(weaponLabel);
		cardsPanel.add(roomLabel);
		
		dialogPanel.add(cardsPanel, BorderLayout.CENTER);
	}
	
	/**
	 * @param suggester: player who suggested
	 * @param refuter: player which has refuted the suggestion
	 * @param refuteCard: card used to refute suggestion
	 * inform a suggester that their suggestion has been refuted by refuter with refuteCard
	 */
	public static void showWasRefutedDialog(Player suggester, Player refuter, Card refuteCard){
		
		final String REFUTE_TEXT = suggester.getCharacter().toString()+", your suggestion was refuted by: "+refuter;
		
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		
		JLabel text = new JLabel(REFUTE_TEXT);
		dialogPanel.add(text, BorderLayout.NORTH);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new FlowLayout());
		
		cardsPanel.add(new JLabel(new ImageIcon(refuteCard.getBufferedImage())));
		
		dialogPanel.add(cardsPanel);
		
		JOptionPane.showMessageDialog(null, dialogPanel, "refuted", JOptionPane.OK_OPTION);
	}

	public static void showMustBeInRoomWarning() {
		final String WARNING = "You must be in a room to make a suggestion!";
		JOptionPane.showMessageDialog(null, WARNING);
	}
	
}
