package cluedo;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cluedo.game.Controller;

public class Main {

	public static final String RES_PATH = "res"+File.separator;
	public static final String IMAGE_PATH = RES_PATH + "img"+File.separator;
	public static final String CARD_IMAGE_PATH = IMAGE_PATH+"cards"+File.separator;

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// look and feel didn't work
			e.printStackTrace();
		}
		new Controller();
	}

}
