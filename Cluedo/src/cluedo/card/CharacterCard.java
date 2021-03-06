package cluedo.card;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.game.Game;
import cluedo.util.Util;


public class CharacterCard extends Card{
	private Game.Character character;

	public CharacterCard (Game.Character character){
		this.character = character;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName() + " \"" + character.name() + "\"";
	}
	
	public Game.Character getCharacter(){
		return character;
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof CharacterCard)){
			return false;
		}
		return this.getCharacter() == ((CharacterCard)o).getCharacter();
	}

	@Override
	public BufferedImage getBufferedImage(){
		String path = Util.CARD_IMAGE_PATH + "character" + File.separator + character.name()+".jpg";
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedImage scaled = Util.imageResize(image, Card.MAX_SCALE_SIZE.width, Card.MAX_SCALE_SIZE.height);
		return scaled;
	}

}
