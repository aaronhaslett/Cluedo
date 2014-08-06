package cluedo.gui;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;


public class CluedoWindow extends JFrame{

	private CluedoBoard board;
	private JMenuBar menuBar;
	private JDialog selectCharacter;
	private Game game; // TODO: reduce coupling

	public CluedoWindow(Game game){
		super("Cluedo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLocation(500, 200); // location on screen that the game starts at.
		setLayout(new BorderLayout());

		this.game = game;
		board = new CluedoBoard();
		menuBar = new JMenuBar();
		menuBar.add(new JMenu("Menu"));
		selectCharacter = new JDialog(this);

		/*menuBar.add(new JMenuItem("Exit game"){

		});*/

		setJMenuBar(menuBar);
		add(board, BorderLayout.CENTER);
		setVisible(true);
		playerSelect();

	}

	/**
	 *  Prompts the users to select their character
	 *  Stores the players in Game object
	 *  > 1 players must be selected before exiting
	 */
	private void playerSelect() {
		JPanel panel = new JPanel();
		JLabel chooseText = new JLabel();
		panel.add(chooseText);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		ButtonGroup buttongroup = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[6];


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
			chooseText.setText("Pick character for player: "+game.getNoOfPlayers());
			playerState = JOptionPane.showOptionDialog(this, panel,
				    "Radio Test", JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE, null, new String[]{"next", "done"} , null);
			if (playerState == DONE_SELECTING){
				// doesn't allow < 2 players
				if (game.getNoOfPlayers() < 2){
					displayErrorBox("Not enough players yet!", "Oi");
					playerState = STILL_SELECTING;
					continue;
				}
			}
			if (playerState == STILL_SELECTING){
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
				game.addPlayer(p);

				// disable that character from being selected
				buttonSelected.setEnabled(false);
				// select next available character
				for (JRadioButton button : buttons){
					if (button.isEnabled()){
						button.setSelected(true);
					}
				}

				System.out.println(p);
			}
		}
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
}
