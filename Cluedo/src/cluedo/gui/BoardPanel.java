package cluedo.gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Point;

import cluedo.board.*;
import cluedo.game.*;
import cluedo.piece.*;

/**
 * @author hardwiwill
 * View for the cluedo game board
 *
 */
public class BoardPanel extends JPanel{

	private BoardObject[][] boardTiles;
	private Controller.BoardMouseListener bml;

	public BoardPanel(Controller control){
		bml = control.new BoardMouseListener();
        addMouseWheelListener(bml);
        addMouseMotionListener(bml);
        addMouseListener(bml);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawString("Board", 200, 300);

		if (boardTiles == null) return; // before board is made ...
		// draws stuff

		for(int y=0; y<Board.SIZE; y++){
			for(int x=0; x<Board.SIZE; x++){
				if(boardTiles[y][x] != null){
					g.setColor(boardTiles[y][x].getColour());
					g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
				}
				g.setColor(Color.BLACK);
				g.drawRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
			}
		}

		if(bml.dragging != null){
			Point p = bml.dragging.getDraggingPosition();
			g.setColor(bml.dragging.getColour());
			g.fillRect((int)p.getX()-(Board.SQUARE_SIZE/2), (int)p.getY()-(Board.SQUARE_SIZE/2),
						Board.SQUARE_SIZE, Board.SQUARE_SIZE);
		}
	}

	public void updateBoard(BoardObject[][] board){
		this.boardTiles = board;
	}
}
