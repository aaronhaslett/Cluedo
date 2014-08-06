package cluedo.piece;

import cluedo.game.Game;


public class WeaponPiece implements Piece{

	private Game.Weapon weapon;

	public WeaponPiece(Game.Weapon weapon){
		this.weapon = weapon;
	}

	public String toString(){
		return weapon.name();
	}

}
