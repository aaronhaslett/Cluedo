package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cluedo.board.BoardObject;
import cluedo.game.Controller;
import cluedo.game.Player;
import cluedo.util.TwoDice;


public class Window extends JFrame{

	private BoardPanel board;
	private PlayerUIPanel playerUI;
	private JMenuBar menuBar;

	public static final Dimension WINDOW_SIZE = new Dimension(850,850);

	/**
	 * @param game - game of cluedo
	 * sets up menu, exit button and starts player select.
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

	private void setLookAndFeel(){

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// look and feel didn't work
			e.printStackTrace();
		}

		/*
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    for(Window window : this.getWindows()) {
	        SwingUtilities.updateComponentTreeUI(window);
	    }*/
	}

	public void updatePlayerTurn(Player p) {
		playerUI.updatePlayerTurn(p);
	}

	public void updateBoard(BoardObject[][] b){
		board.updateBoard(b);
	}

	public void updateDice(TwoDice dice) {
		playerUI.updateDice(dice);
	}



}
