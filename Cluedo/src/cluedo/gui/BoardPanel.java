package cluedo.gui;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Font;

import cluedo.board.*;
import cluedo.game.*;
import cluedo.piece.*;
import cluedo.util.Point;

/**
 * @author aaron
 * View element for the cluedo game board
 *
 */
public class BoardPanel extends JPanel{
	private Board board;
	private Controller.BoardMouseListener bml;

	public BoardPanel(Controller control, Board board){
		this.board = board;

		bml = control.new BoardMouseListener();
        addMouseWheelListener(bml);
        addMouseMotionListener(bml);
        addMouseListener(bml);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BoardObject[][] tiles = board.getBoardTiles();

		if (tiles == null) return; // before board is made ...
		// draws stuff

		//Draw each tile on the board.
		for(int y=0; y<Board.SIZE; y++){
			for(int x=0; x<Board.SIZE; x++){
				if(tiles[y][x] != null){
					g.setColor(tiles[y][x].getColour());//Each object knows its own colour
					if(tiles[y][x] instanceof Player){//Players are circles
						g.fillOval(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					}else{//Everything else is a square
						g.fillRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					}
				}
				//Draw the black outline for each square.
				g.setColor(Color.BLACK);
				g.drawRect(x*Board.SQUARE_SIZE, y*Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
			}
		}

		//If we are dragging a player token, draw the current dragging position.
		if(bml.dragging != null){
			Point p = bml.dragging.getDraggingPosition();
			g.setColor(bml.dragging.getColour());
			g.fillOval(p.getX()-(Board.SQUARE_SIZE/2), p.getY()-(Board.SQUARE_SIZE/2),
						Board.SQUARE_SIZE, Board.SQUARE_SIZE);
		}

		//Now we draw the labels on each room.
		g.setFont(new Font("Times New Roman", Font.BOLD, 15));
		g.setColor(new Color(7,18,58));

		for(Room r: board.rooms){
			//Get each room's central point.
			int x = (int)(r.getCentralPoint()[0] * Board.SQUARE_SIZE), y=(int)(r.getCentralPoint()[1] * Board.SQUARE_SIZE);
			String label = r.getName().name();

			//Offset based on the string's width and height.
			x -= g.getFontMetrics().stringWidth(label)/2;
			y += Board.SQUARE_SIZE/4;
			g.drawString(label, x, y);//Draw it. 
		}
	}
}
