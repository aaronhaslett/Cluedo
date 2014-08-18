package cluedo.gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Font;

import cluedo.board.*;
import cluedo.game.*;
import cluedo.piece.*;

/**
 * @author aaron
 * View element for the cluedo game board
 *
 */
public class BoardPanel extends JPanel{

	private BoardObject[][] boardTiles;
	private Board board;
	private Controller.BoardMouseListener bml;

	public BoardPanel(Controller control){
		bml = control.new BoardMouseListener();
        addMouseWheelListener(bml);
        addMouseMotionListener(bml);
        addMouseListener(bml);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if (boardTiles == null) return; // before board is made ...
		// draws stuff

		for(int y=0; y<Board.SIZE; y++){
			for(int x=0; x<Board.SIZE; x++){
				if(boardTiles[y][x] != null){
					g.setColor(boardTiles[y][x].getColour());
					if(boardTiles[y][x] instanceof Player){
						g.fillOval(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					}else{
						g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					}
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

		g.setFont(new Font("Times New Roman", Font.BOLD, 15));

		for(Room r: board.rooms){
			int x = (int)r.getCentralPoint()[0] * Board.SQUARE_SIZE, y=(int)r.getCentralPoint()[1] * Board.SQUARE_SIZE;
			String label = r.getName().name();

			x -= g.getFontMetrics().stringWidth(label)/2;
			y += g.getFontMetrics().getHeight();
			g.drawString(label, x, y); 
		}
	}

	public void updateBoard(Board board){
		this.board = board;
		this.boardTiles = board.getBoardTiles();
	}
}
