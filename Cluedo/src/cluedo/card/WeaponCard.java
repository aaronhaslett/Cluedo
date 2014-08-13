package cluedo.card;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.Main;
import cluedo.game.Game;

public class WeaponCard extends Card{

	private Game.Weapon weapon;

	public WeaponCard(Game.Weapon weapon){
		this.weapon = weapon;
	}

	public Game.Weapon getWeapon(){
		return weapon;
	}

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
	public String getImagePath(){
		return Main.CARD_IMAGE_PATH + "weapon" + File.separator + weapon.name()+".jpg";
	}
}
