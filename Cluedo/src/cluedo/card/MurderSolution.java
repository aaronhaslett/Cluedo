package cluedo.card;


public class MurderSolution {

	private CharacterCard character;
	private RoomCard room;
	private WeaponCard weapon;

	public MurderSolution(CharacterCard character, RoomCard room, WeaponCard weapon) {
		super();
		this.character = character;
		this.room = room;
		this.weapon = weapon;
	}


	public CharacterCard getCharacter() {
		return character;
	}
	public RoomCard getRoom() {
		return room;
	}
	public WeaponCard getWeapon() {
		return weapon;
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof MurderSolution)){
			return false;
		}
		MurderSolution s = (MurderSolution) o;

		return s.getCharacter().equals(this.getCharacter())
				&& s.getWeapon().equals(this.getWeapon())
				&& s.getRoom().equals(this.getRoom());
	}
}
