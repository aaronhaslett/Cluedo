package cluedo.card;
import cluedo.game.Game;


public class CharacterCard extends Card{
	private Game.Character character;

	public CharacterCard (Game.Character character){
		this.character = character;
	}

	public String toString(){
		return this.getClass().getName() + " \"" + character.name() + "\"";
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

}
