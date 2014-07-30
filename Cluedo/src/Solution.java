
public class Solution {

	private Game.Character character;
	private Game.Room room;
	private Game.Weapon weapon;

	public Solution(Game.Character character, Game.Room room, Game.Weapon weapon) {
		super();
		this.character = character;
		this.room = room;
		this.weapon = weapon;
	}


	public Game.Character getCharacter() {
		return character;
	}
	public Game.Room getRoom() {
		return room;
	}
	public Game.Weapon getWeapon() {
		return weapon;
	}
}
