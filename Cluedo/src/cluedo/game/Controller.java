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
import cluedo.gui.SuggestDialogs;
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
		window.updateBoard(board);
		window.repaint();
	}

	/**
	 * The player to the left gets the turn.
	 * View elements are updated.
	 */
	public void nextTurn() {
		game.nextTurn();
		game.setRolled(false);
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
						break;
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
		JOptionPane.showMessageDialog(null,	message, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Makes dialog boxes to go through murder suggestion process.
	 * @param p: player to make suggestion
	 */
	private void makeSuggestion(Player suggester){

		Game.Room room = suggester.getRoom().getName();
		if (room == null){
			// player is not in a room
			SuggestDialogs.showMustBeInRoomWarning();
		}
		MurderHypothesis suggestion = selectSuggestion(room);

		if (suggestion == null){
			// player cancelled the suggestion
			return;
		}
		// else proceed with suggestion
		Player currentPlayer = game.getPlayerToLeft(suggester);
		for (int playerCount = 0; playerCount < game.getNumberOfPlayers()-1; playerCount++){
			
			List<Card> matchingCards = new ArrayList<Card>();
			matchingCards.addAll(suggestion.whichCardsMatch(currentPlayer.getCards()));
			if (matchingCards.isEmpty()){
				SuggestDialogs.showCantRefuteDialog(currentPlayer, suggestion);
			}
			else {
				// player can refute, show dialog asking them to choose which card to refute with
				Card refuteCard = SuggestDialogs.showCanRefuteDialog(currentPlayer, suggestion, matchingCards);
				SuggestDialogs.showWasRefutedDialog(suggester, currentPlayer, refuteCard);
				// move onto the next turn
				nextTurn();
				return;
			}
			currentPlayer = game.getPlayerToLeft(currentPlayer);
		}
		
		// cycled through all players which means the suggestion must be correct!
		
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
			if(squareX < 0 || squareX > 23 || squareY < 0 || squareY > 23){return;}

			BoardObject clicked = board.getBoardTiles()[squareY][squareX];

			//Is the user dragging a player and is it that player's turn?
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
