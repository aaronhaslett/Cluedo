package cluedo.card;

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
		return this.getClass().getName() + " \"" + weapon.name() + "\"";
	}

	@Override
	public boolean equals(Object o){
		if ((o instanceof WeaponCard)){
			return false;
		}
		return this.getWeapon() == ((WeaponCard)o).getWeapon();
	}
}
