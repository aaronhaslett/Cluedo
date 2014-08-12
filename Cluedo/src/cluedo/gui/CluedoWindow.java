package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import cluedo.game.Game;
import cluedo.game.Player;
import cluedo.piece.CharacterPiece;


public class CluedoWindow extends JFrame{

	private CluedoBoard board;
	private PlayerUIPanel playerUI;
	private JMenuBar menuBar;
	private Game game; // TODO: reduce coupling

	private final String EXIT_CONFIRM = "Are you sure that you want to exit the best cluedo implementation known?";
	public static final Dimension WINDOW_SIZE = new Dimension(700,700);

	/**
	 * @param game - game of cluedo
	 * sets up menu, exit button and starts player select.
	 */
	public CluedoWindow(){
		super("Cluedo");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE);
		setLocation(500, 200); // location on screen that the game starts at.
		setLayout(new BorderLayout());

		board = new CluedoBoard();
		playerUI = new PlayerUIPanel();
		menuBar = new JMenuBar();
		//menuBar.add(menu);

		// Exit game button
		JMenuItem exit = new JMenuItem("Exit game");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int response = JOptionPane.showConfirmDialog(CluedoWindow.this, EXIT_CONFIRM, "Serious?", JOptionPane.YES_NO_OPTION);
				if (response == 0){
					System.exit(0);
				}
			}
		});
		menuBar.add(exit);

		setJMenuBar(menuBar);
		add(board, BorderLayout.CENTER);
		add(playerUI, BorderLayout.SOUTH);
		setVisible(true);

		List<Player> players = playerSelect();
		this.game = new Game(players);
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
			playerState = JOptionPane.showOptionDialog(this, panel,
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
}
