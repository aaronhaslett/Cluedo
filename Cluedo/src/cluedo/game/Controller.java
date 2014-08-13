package cluedo.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.board.Board;
import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;
import cluedo.gui.Window;
import cluedo.piece.CharacterPiece;

public class Controller {

	private Game game;
	private Board board;
	private Window window;

	public Controller(){
		window = new Window(this);

		List<Player> players = playerSelect();

		board = new Board(players);
		window.updateBoard(board.getBoardTiles());
		window.repaint();

		game = new Game(players);

		window.updatePlayerTurn(game.getWhoseTurn());
		window.updateBoard(board.getBoardTiles());
		window.repaint();
	}

	/**
	 * The player to the left gets the turn.
	 * View elements are updated.
	 */
	private void nextTurn() {
		game.nextTurn();
		window.updatePlayerTurn(game.getWhoseTurn());
		window.repaint();
	}

	/**
	 *  Prompts the users to select their character
	 *  Stores the players in Game object
	 *  > 1 players must be selected before exiting
	 */
	private List<Player> playerSelect() {
		JPanel panel = new JPanel();
		JLabel chooseText = new JLabel();
		panel.add(chooseText);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		ButtonGroup buttongroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[6];


		List<Player> players = new ArrayList<Player>();

		// makes radio button for each character
		for (Game.Character c : Game.Character.values()){
			JRadioButton button = new JRadioButton(c.toString());
			// sets action command to the ordinal number of the character
			button.setActionCommand(c.name());
			buttongroup.add(button);
			buttons[c.ordinal()] = button;
			panel.add(button);
		}

		// initialise first button to be selected
		buttons[0].setSelected(true);

		final int STILL_SELECTING = 0;
		final int DONE_SELECTING = 1;
		int playerState = STILL_SELECTING;

		// each user picks their character
		while (playerState != DONE_SELECTING){
			chooseText.setText("Pick character for player: "+players.size());
			playerState = JOptionPane.showOptionDialog(null, panel,
				    "Radio Test", JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE, null, new String[]{"next", "done"} , null);
			if (playerState == DONE_SELECTING){

				// doesn't allow < 2 players
				if (players.size() < 2){
					displayErrorBox("Not enough players yet!", "Oi");
					playerState = STILL_SELECTING;
					continue;
				}
			}
			else if (playerState == STILL_SELECTING){
				ButtonModel buttonSelected = buttongroup.getSelection();

				// if no buttons are selected.
				if (buttonSelected == null){
					displayErrorBox("Hey! Select a character","Oi");
					continue;
				}

				// add player to the game
				Game.Character characterSelected = Game.Character.valueOf(buttonSelected.getActionCommand());
				CharacterPiece piece = new CharacterPiece(characterSelected);
				Player p = new Player(piece);
				players.add(p);

				// disable that character from being selected
				buttonSelected.setEnabled(false);
				// select next available character
				for (JRadioButton button : buttons){
					if (button.isEnabled()){
						button.setSelected(true);
					}
				}
			}
		}
		return players;
	}

	/**
	 * Pops up a box with a title and displays a message.
	 * @param message
	 * @param title
	 */
	private void displayErrorBox(String message, String title){
		JOptionPane.showMessageDialog(null,
				message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Makes dialog boxes to go through murder suggestion process.
	 * @param p: player to make accusation
	 */
	/*private void makeSuggestion(Player player){
		Player currentPlayer = game.getWhoseTurn();

		//MurderSolution suggestion = selectHypothesis(ROOM_THE_PLAYER_IS_IN);

		if (suggestion == null){
			// player cancelled the accusation
			return;
		}
		// else proceed with accusation

		// test the hypothesis
		String message = null;
		if (testMurderHypothesis(currentPlayer, suggestion)){
			message = "Suggestion was correct! "+player+" has won!";
			// TODO: player wins the game, game ends.
		}
		else {
			message = "Suggestion was incorrect! "+player+" has won!";
		}

		JOptionPane.showMessageDialog(null, message);
	}*/

	/**
	 * Makes dialog boxes to go through accusation process.
	 * @param p: player to make accusation
	 */
	private void makeAccusation(Player player){

		MurderHypothesis accusation = selectHypothesis(null);

		if (accusation == null){
			// player cancelled the accusation
			return;
		}
		// else proceed with accusation

		// test the hypothesis
		String message = null;
		if (testMurderHypothesisValid(player, accusation)){
			message = "Accusation was correct! "+player+" has won!";
			// TODO: player wins the game, game ends.
		}
		else {
			message = "Accusation was wrong!" + player+" is out of the game!";
			// TODO: player is out of the game.
		}

		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * @param room: restriction to one room or null if no room restriction
	 * @return a MurderHypothesis containing the relevant cards
	 */
	private static MurderHypothesis selectHypothesis(Game.Room room){
		final String TITLE = "so how did the murder happen?";
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());

		JComboBox<Game.Character> murdererSelect = new JComboBox<Game.Character>(Game.Character.values());
		JComboBox<Game.Weapon> weaponSelect = new JComboBox<Game.Weapon>(Game.Weapon.values());
		JComboBox<Game.Room> roomSelect = new JComboBox<Game.Room>(Game.Room.values());

		if (room != null) {
			//roomSelect. //TODO: limit room select to room parameter
		}

		optionPanel.add(murdererSelect);
		optionPanel.add(weaponSelect);
		optionPanel.add(roomSelect);

		// popup box requesting murder details
		int confirmResponse = JOptionPane.showConfirmDialog(null, optionPanel, TITLE, JOptionPane.OK_CANCEL_OPTION);

		if (confirmResponse == JOptionPane.CANCEL_OPTION){
			return null; // no accusations will be made here today.
		}
		// TODO: (maybe) confirm again
		return new MurderHypothesis(new CharacterCard((Game.Character)murdererSelect.getSelectedItem()),
				new RoomCard((Game.Room)roomSelect.getSelectedItem()),
				new WeaponCard((Game.Weapon)weaponSelect.getSelectedItem()));
	}

	/**
	 * Goes through all players to see if they can refute the hypothesis
	 * @param hypothesiser
	 * @param hypothesis
	 * @return true if murder hypothesis is correct, false if not
	 */
	private boolean testMurderHypothesisValid(Player hypothesiser, MurderHypothesis hypothesis){

		for (int i = 0; i < game.getNumberOfPlayers(); i++){
			hypothesiser = game.getPlayerToLeft(hypothesiser);

			final String HYPOT_CONFIRM_TITLE = hypothesiser.getCharacter().toString()+", can you refute this accusation?";
			JPanel hypotPanel = new JPanel();
			hypotPanel.setLayout(new BorderLayout());

			JPanel cardsPanel = new JPanel();
			cardsPanel.setLayout(new FlowLayout());
			JLabel characterLabel = new JLabel(hypothesis.getCharacter().toString());
			JLabel weaponLabel = new JLabel(hypothesis.getWeapon().toString());
			JLabel roomLabel = new JLabel(hypothesis.getRoom().toString());
			cardsPanel.add(characterLabel);
			cardsPanel.add(weaponLabel);
			cardsPanel.add(roomLabel);
			hypotPanel.add(cardsPanel, BorderLayout.NORTH);

			// displays whether the user can refute or not
			JLabel result = new JLabel();
			hypotPanel.add(result);

			Set<Card> matchingCards = hypothesis.whichCardsMatch(hypothesiser.getCards());

			if (!matchingCards.isEmpty()){
				// player has card/s to refute the solution
				result.setText("You can refute this solution");
				// notify player of card/s they have to refute the solution
				for (Card match : matchingCards){
					JLabel toEdit = null;
					if (match instanceof CharacterCard){
						toEdit = characterLabel;
					}
					if (match instanceof WeaponCard){
						toEdit = weaponLabel;
					}
					if (match instanceof RoomCard){
						toEdit = roomLabel;
					}
					final String OWNERSHIP_NOTIFY = "\n<YOU HAVE>";
					toEdit.setText(toEdit.getText()+OWNERSHIP_NOTIFY);
				}
				JOptionPane.showMessageDialog(null, hypotPanel, HYPOT_CONFIRM_TITLE, JOptionPane.OK_OPTION);
				return false; // end
			}
			else {
				// player has no cards to refute the solution
				result.setText("You cannot refute this accusation");
				JOptionPane.showMessageDialog(null, hypotPanel, HYPOT_CONFIRM_TITLE, JOptionPane.OK_OPTION);
				// continue the accusation confirmation
			}
		}
		return true;
	}

	public class ExitButtonListener implements ActionListener{

		private final String EXIT_CONFIRM = "Are you sure that you want to exit the best cluedo implementation known?";
		private final String CONFIRM_TITLE = "Serious?";

		public void actionPerformed(ActionEvent e){
			int response = JOptionPane.showConfirmDialog(null, EXIT_CONFIRM, CONFIRM_TITLE, JOptionPane.YES_NO_OPTION);
			if (response == 0){
				System.exit(0);
			}
		}
	}

	public class BoardMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class EndTurnButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// give next player the turn, update graphics
			nextTurn();
		}

	}

	public class AccusationButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			makeAccusation(game.getWhoseTurn());
		}

	}

}
