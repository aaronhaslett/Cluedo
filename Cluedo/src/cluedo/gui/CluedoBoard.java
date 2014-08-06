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


	public CluedoBoard(){

	}

	public void paintComponent(Graphics g){
		g.drawString("Board", 200, 300);
	}
}
