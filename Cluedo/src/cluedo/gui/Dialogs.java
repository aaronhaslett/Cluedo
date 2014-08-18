package cluedo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;

/**
 * @author will
 * View elements
 */
public class Dialogs {

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

	/**
	 * informs a player that their piece must be in a room to make a suggestion
	 */
	public static void showMustBeInRoomWarning() {
		final String WARNING = "You must be in a room to make a suggestion!";
		JOptionPane.showMessageDialog(null, WARNING);
	}

	/**
	 * @param winner
	 * informs a player that they have won the game.
	 */
	public static void showGameWonMessage(Player winner){
		final String WIN_MESSAGE = "Congratulations "+winner.getName()+", you solved the mystery!";
		JOptionPane.showMessageDialog(null, WIN_MESSAGE);
	}

	/**
	 * Pops up a box with a title and displays a message.
	 * @param message
	 * @param title
	 */
	public static void showMessageBox(String title, String message) {
		JOptionPane.showMessageDialog(null,	message, title, JOptionPane.ERROR_MESSAGE);
	}


	/**
	 * @author hardwiwill
	 * View element for the character select interface
	 * Used to interface with all players to have them choose a character each
	 *
	 * This is a class, not a method, because the character select interface has more
	 * complicated user-input than the other showdialog methods in this class.
	 */
	public static class CharacterSelect {

		public static final int NEXT_CHARACTER = 0;
		public static final int FINISH_CHOOSING = 1;

		private List<Player> players;

		public CharacterSelect(){
			players = new ArrayList<Player>();
		}

		/**
		 * @param players
		 * @return the response from the player:
		 * 	0 = next character
		 * 	1 = done choosing
		 *
		 * shows a dialog which requests the player to choose a character
		 */
		public int showPlayerSelect() {
			JPanel panel = new JPanel();
			JLabel chooseText = new JLabel();
			panel.add(chooseText);
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			ButtonGroup buttongroup = new ButtonGroup();
			JRadioButton[] buttons = new JRadioButton[6];

			// makes radio button for each character
			for (Game.Character character : Game.Character.values()){
				JRadioButton button = new JRadioButton(character.toString());
				// sets action command to the ordinal number of the character
				button.setActionCommand(character.name());
				buttongroup.add(button);
				buttons[character.ordinal()] = button;
				if (players.contains(new Player(new CharacterPiece(character)))){
					// if there is already a player with that character, disable button
					button.setEnabled(false);
				}
				panel.add(button);
			}

			// initialise first button to be selected
			for (JRadioButton button : buttons){
				if (button.isEnabled()){
					button.setSelected(true);
					break;
				}
			}

			chooseText.setText("Pick character for player: "+players.size());

			int response = JOptionPane.showOptionDialog(null, panel, "Pick character",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, new String[]{"next", "done"} , null);

			ButtonModel buttonSelected = buttongroup.getSelection();

			// if no buttons are selected.
			if (buttonSelected == null){
				Dialogs.showMessageBox("Oi", "Not enough players yet!");
			}

			// add player to the game
			Game.Character characterSelected = Game.Character.valueOf(buttonSelected.getActionCommand());
			CharacterPiece piece = new CharacterPiece(characterSelected);
			players.add(new Player(piece));
			return response;
		}

		public List<Player> getPlayers(){
			return players;
		}

		public int getNumberOfPlayers() {
			return players.size();
		}

	}

}
