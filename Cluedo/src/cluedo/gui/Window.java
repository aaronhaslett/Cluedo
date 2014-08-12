package cluedo.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import cluedo.game.Controller;


public class Window extends JFrame{

	private CluedoBoard board;
	private PlayerUIPanel playerUI;
	private JMenuBar menuBar;

	private final String EXIT_CONFIRM = "Are you sure that you want to exit the best cluedo implementation known?";
	public static final Dimension WINDOW_SIZE = new Dimension(700,700);

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

		board = new CluedoBoard();
		playerUI = new PlayerUIPanel();
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


}
