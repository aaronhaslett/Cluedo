package cluedo;

import java.io.File;

import cluedo.game.Controller;
import cluedo.game.*;
import cluedo.board.*;
import cluedo.gui.*;

public class Main {

	public static final String RES_PATH = File.separator+"res"+File.separator;
	public static final String IMAGE_PATH = RES_PATH + "img"+File.separator;
	public static final String CARD_IMAGE_PATH = IMAGE_PATH+"cards"+File.separator;

	public static void main(String[] args){
		new Controller();
	}

}
