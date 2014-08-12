package cluedo.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.board.Board;
import cluedo.gui.Window;
import cluedo.piece.CharacterPiece;

public class Controller {

	private Game game;
	private Board board;
	private Window window;

	public Controller(){
		window = new Window(this);
		board = new Board();
		window.updateBoard(board.getBoardTiles());
		window.repaint();

		game = new Game(playerSelect());

		window.updatePlayerTurn(game.getWhoseTurn());
		window.updateBoard(board.getBoardTiles());
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
			game.nextTurn();
			window.updatePlayerTurn(game.getWhoseTurn());
			window.repaint();
		}

	}
	
	public class AccusationButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// make accusation popup
			
		}

	}

}
