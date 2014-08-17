package cluedo.game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import javax.swing.event.MouseInputAdapter;

import cluedo.board.Board;
import cluedo.board.BoardObject;
import cluedo.card.Card;
import cluedo.card.CharacterCard;
import cluedo.card.MurderHypothesis;
import cluedo.card.RoomCard;
import cluedo.card.WeaponCard;
import cluedo.gui.Window;
import cluedo.piece.CharacterPiece;

/**
 * @author hardwiwill
 * This is an attempt to make the 'Controller' section of the MVC design pattern.
 * Handles all user-input
 * Contains all Model and View elements
 */
public class Controller {

	private static final int MIN_PLAYERS = 3;

	private Game game;
	private Board board;
	private Window window;

	/**
	 * initialises model and view
	 */
	public Controller(){
		window = new Window(this);

		List<Player> players = playerSelect();
		board = new Board(players);
		game = new Game(players);

		window.updatePlayerTurn(game.getWhoseTurn());
		window.updateBoard(board.getBoardTiles());
		window.repaint();
	}

	/**
	 * The player to the left gets the turn.
	 * View elements are updated.
	 */
	public void nextTurn() {
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

				// doesn't allow < 3 players
				if (players.size() < MIN_PLAYERS){
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
	private void makeSuggestion(Player player){
		Player currentPlayer = game.getWhoseTurn();

		MurderHypothesis suggestion = selectSuggestion(Game.Room.BallRoom);

		if (suggestion == null){
			// player cancelled the accusation
			return;
		}
		// else proceed with accusation

		// test the hypothesis
		String message = null;
		if (testMurderSuggestion(currentPlayer, suggestion)){
			message = "Suggestion was correct! "+player+" has won!";
			// TODO: player wins the game, game ends.
		}
		else {
			message = "The suggestion has been refuted";
		}

		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Makes dialog boxes to go through accusation process.
	 * @param p: player to make accusation
	 */
	private void makeAccusation(Player player){

		MurderHypothesis accusation = selectAccusation();

		if (accusation == null){
			// player cancelled the accusation
			return;
		}
		// else proceed with accusation

		// test the hypothesis
		String message = null;
		if (game.isAccusationCorrect(accusation)){
			message = "Accusation was correct! "+player+" has won!";
			// TODO: player wins the game, game ends.
		}
		else {
			message = "Accusation was wrong!" + player+" is out of the game!";
			// TODO: player is out of the game.
		}

		JOptionPane.showMessageDialog(null, message);
	}

	private MurderHypothesis selectSuggestion(Game.Room currentRoom) {
		final String TITLE = "so how did the murder happen?";
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());

		JComboBox<Game.Character> murdererSelect = new JComboBox<Game.Character>(Game.Character.values());
		JComboBox<Game.Weapon> weaponSelect = new JComboBox<Game.Weapon>(Game.Weapon.values());


		optionPanel.add(murdererSelect);
		optionPanel.add(weaponSelect);

		// popup box requesting murder details
		int confirmResponse = JOptionPane.showConfirmDialog(null, optionPanel, TITLE, JOptionPane.OK_CANCEL_OPTION);

		if (confirmResponse == JOptionPane.CANCEL_OPTION){
			return null; // no accusations will be made here today.
		}
		// TODO: (maybe) confirm again
		return new MurderHypothesis(new CharacterCard((Game.Character)murdererSelect.getSelectedItem()),
				new RoomCard(currentRoom),
				new WeaponCard((Game.Weapon)weaponSelect.getSelectedItem()));
	}

	/**
	 * @param room: restriction to one room or null if no room restriction
	 * @return a MurderHypothesis containing the relevant cards
	 */
	private static MurderHypothesis selectAccusation(){
		final String TITLE = "so how did the murder happen?";
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new FlowLayout());

		JComboBox<Game.Character> murdererSelect = new JComboBox<Game.Character>(Game.Character.values());
		JComboBox<Game.Weapon> weaponSelect = new JComboBox<Game.Weapon>(Game.Weapon.values());
		JComboBox<Game.Room> roomSelect = new JComboBox<Game.Room>(Game.Room.values());


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
	private boolean testMurderSuggestion(Player hypothesiser, MurderHypothesis hypothesis){

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

	public Game getGame(){
		return game;
	}

	/**
	 * PRE: Dice has not been rolled this turn
	 * POST: Dice will be rolled
	 */
	public void rollDice() {
		if(game.hasRolled()){return;}

		game.rollDice();
		window.updateDice(game.getDice());

		board.showPaths(game.getDiceValue(), game.getWhoseTurn());
		game.setRolled(true);
		window.repaint();
	}

	/**
	 * @author hardwiwill
	 * Listener for the exit button on the top menu
	 */
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

	/**
	 * @author hardwiwill
	 * Listens for mouse events on the board. Allows user to click squares or drag their character
	 * pieces to positions on the board.
	 */
	public class BoardMouseListener extends MouseInputAdapter{
		public Player dragging;

		public void mousePressed(MouseEvent e){
			if(board.getBoardTiles() == null){return;}

			int boardSize = board.getBoardTiles().length+1;
			int squareX = (int)(e.getX()/boardSize);
			int squareY = (int)(e.getY()/boardSize);

			BoardObject clicked = board.getBoardTiles()[squareY][squareX];

			//Is the user dragging a player and is that player the one whose turn it is?
			dragging = clicked instanceof Player && (Player)clicked == game.getWhoseTurn() ? (Player)clicked : null;

			if(dragging!=null){
				dragging.setDraggingPosition(new Point(e.getX(), e.getY()));
				int x = (int)dragging.getPosition().getX(), y = (int)dragging.getPosition().getY();
				if(dragging.getRoom() == null){
					board.getBoardTiles()[y][x] = null;
				}else{
					board.getBoardTiles()[y][x] = dragging.getRoom();
				}
			}
		}

		public void mouseDragged(MouseEvent e){
			if(dragging==null){return;}

			dragging.getDraggingPosition().setLocation(e.getX(), e.getY());
			window.repaint();
		}

		public void mouseReleased(MouseEvent e){

			int boardSize = board.getBoardTiles().length+1;
			int squareX = (int)(e.getX()/boardSize);
			int squareY = (int)(e.getY()/boardSize);

			if((board.move(game.getWhoseTurn(), new Point(squareX, squareY)))){
				board.clearPath();
			}
			else if (dragging != null){
				int x = (int)dragging.getPosition().getX(), y = (int)dragging.getPosition().getY();
				board.getBoardTiles()[y][x] = dragging;
			}
			dragging = null;

			window.repaint();
		}
	};

	/**
	 * @author hardwiwill
	 * Listener for the button which user clicks to end their turn.
	 */
	public class EndTurnButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// give next player the turn, update graphics
			nextTurn();
			game.setRolled(false);
			board.clearPath();
		}
	}

	/**
	 * @author hardwiwill
	 * Listener for 'accuse' button. Causes an accusation to start.
	 */
	public class AccusationButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			makeAccusation(game.getWhoseTurn());
		}
	}

	/**
	 * @author hardwiwill
	 * Listener for the dice button. Causes the dice to roll.
	 */
	public class DiceButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			rollDice();
		}
	}

	/**
	 * @author hardwiwill
	 * listener for the suggestion button. This begins a suggestion.
	 */
	public class SuggestionButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			makeSuggestion(game.getWhoseTurn());
		}

	}

}
