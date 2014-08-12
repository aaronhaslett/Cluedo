package cluedo.gui;
import java.awt.Graphics;

import javax.swing.JPanel;


/**
 * @author hardwiwill
 * GUI for the cluedo game board
 *
 */
public class CluedoBoard extends JPanel{


	public CluedoBoard(){

	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Board", 200, 300);
	}
}
