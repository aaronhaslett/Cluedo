package cluedo.gui;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;


/**
 * @author hardwiwill
 * GUI for the cluedo game board
 *
 */
public class CluedoBoard extends Canvas{

	private Dimension size = new Dimension(500, 400);

	public CluedoBoard(){
		setPreferredSize(size);
	}

	public void paintComponent(Graphics g){
		g.drawString("Board", size.width/2, size.height/2);
	}
}
