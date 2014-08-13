package cluedo.game;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.Main;

/**
 * - - - - - - - - - - - - - - - NOT IN USE CURRENTLY
 * @author hardwiwill
 *
 */
public class CluedoCharacter {

	private String name;
	private Color colour;
	private Image image;

	public CluedoCharacter(Game.Character character){
		name = character.name();

		// find image.
		try {
			image = ImageIO.read(new File(Main.RES_PATH+File.separator+name));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// decide colour
		switch (character) {
		case MissScarlet:	colour = Color.red; break;
		case ProfPlum:	colour = new Color(190, 60, 220); break;
		case MrsPeacock:	colour = Color.blue; break;
		case RevGreen:	colour = Color.green; break;
		case ColMustard:	colour = Color.yellow; break;
		case MrsWhite:	colour = Color.white; break;
		}
	}

	public String getName() {
		return name;
	}

}
