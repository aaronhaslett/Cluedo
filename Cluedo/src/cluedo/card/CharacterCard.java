package cluedo.card;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.Main;
import cluedo.game.Game;


public class CharacterCard extends Card{
	private Game.Character character;

	public CharacterCard (Game.Character character){
		this.character = character;
	}

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
	public Image getImage(){
		BufferedImage image=null;
		try{
			image = ImageIO.read(new File(Main.CARD_IMAGE_PATH + character.name()+".jpg"));
		} catch (IOException e){
			//System.out.println(Main.CARD_IMAGE_PATH + "character" + File.separator + character.name()+".jpg");
			e.printStackTrace();
		}
		return image;
	}

}
