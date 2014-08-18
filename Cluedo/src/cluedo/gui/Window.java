package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import cluedo.board.BoardObject;
import cluedo.game.Controller;
import cluedo.game.Player;
import cluedo.util.TwoDice;
import cluedo.board.*;


/**
 * @author hardwiwill
 * Contains/controls all View/GUI elements
 */
public class Window extends JFrame{

	private BoardPanel board;
	private PlayerUIPanel playerUI;
	private JMenuBar menuBar;

	public static final int BOARD_SIZE = Board.SQUARE_SIZE * (Board.SIZE+1);
	public static final int MENU_PADDING = 25;
	public static final int WINDOW_WIDTH = Math.max(PlayerUIPanel.UI_PANEL_SIZE.width, BOARD_SIZE);
	public static final int WINDOW_HEIGHT = PlayerUIPanel.UI_PANEL_SIZE.height + BOARD_SIZE + MENU_PADDING;
	public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);

	/**
	 * @param game - game of cluedo
	 * initialises View/GUI elements.
	 */
	public Window(Controller control){
		super("Cluedo");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE);
		setLocation(500, 200); // location on screen that the game starts at.
		setLayout(new BorderLayout());
		setLookAndFeel();

		board = new BoardPanel(control);
		playerUI = new PlayerUIPanel(control);
		menuBar = new JMenuBar();
		//menuBar.add(menu);

		// Exit game button
		JMenuItem exit = new JMenuItem("Exit game");
		exit.addActionListener(control.new ExitButtonListener());
		menuBar.add(exit);

		setJMenuBar(menuBar);
		add(board, BorderLayout.CENTER);
		add(playerUI, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * sets the java look and feel for the windows in the game
	 */
	private void setLookAndFeel(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e){
			// look and feel didn't work
			e.printStackTrace();
		}
	}

	/**
	 * @param p
	 * updates gui elements which change with player turns
	 */
	public void updatePlayerTurn(Player p) {
		playerUI.updatePlayerTurn(p);
	}

	/**
	 * @param b
	 * updates/refreshes the board element
	 */
	public void updateBoard(Board b){
		board.updateBoard(b);
	}

	/**
	 * @param dice
	 * updates View/GUI elements which change with dice
	 */
	public void updateDice(TwoDice dice) {
		playerUI.updateDice(dice);
	}



}
