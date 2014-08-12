package cluedo.gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import cluedo.board.*;
import cluedo.game.*;


/**
 * @author hardwiwill
 * View for the cluedo game board
 *
 */
public class BoardPanel extends JPanel{

	private BoardObject[][] boardTiles;

	public BoardPanel(Controller control){
		addMouseListener(control.new BoardMouseListener());
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Board", 200, 300);

		if (boardTiles == null) return; // before board is made ...
		// draws stuff

		for(int y=0; y<Board.SIZE; y++){
			for(int x=0; x<Board.SIZE; x++){
				if(boardTiles[y][x] == null){
					g.setColor(Color.BLACK);
					g.drawRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				}else if(boardTiles[y][x] instanceof Room){
					g.setColor(Color.RED);
					g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				}else if(boardTiles[y][x] instanceof Door){
					g.setColor(Color.BLUE);
					g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				}else if(boardTiles[y][x] instanceof Warp){
					g.setColor(Color.ORANGE);
					g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				}
			}
		}

	}

	public void updateBoard(BoardObject[][] board){
		this.boardTiles = board;
	}
}
