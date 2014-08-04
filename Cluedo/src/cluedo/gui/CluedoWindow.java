package cluedo.gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import cluedo.game.Game;


public class CluedoWindow extends JFrame{

	private CluedoBoard board;

	public CluedoWindow(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLayout(new BorderLayout());

		board = new CluedoBoard();


		add(board, BorderLayout.CENTER);
		setVisible(true);
		playerSelect();
	}

	/**
	 *  Prompts the users to enter character info
	 */
	private void playerSelect() {


	}
}
