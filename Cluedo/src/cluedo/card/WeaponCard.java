package cluedo.card;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.game.Game;
import cluedo.util.Util;

public class WeaponCard extends Card{

	private Game.Weapon weapon;

	public WeaponCard(Game.Weapon weapon){
		this.weapon = weapon;
	}

	public Game.Weapon getWeapon(){
		return weapon;
	}

	@Override
	public String toString(){
		return this.getClass().getSimpleName() + " \"" + weapon.name() + "\"";
	}
	
	@Override
	public boolean equals(Object o){
		if (!(o instanceof WeaponCard)){
			return false;
		}
		return this.getWeapon() == ((WeaponCard)o).getWeapon();
	}

	@Override
	public BufferedImage getBufferedImage(){
		String path = Util.CARD_IMAGE_PATH + "weapon" + File.separator + weapon.name()+".jpg";
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
