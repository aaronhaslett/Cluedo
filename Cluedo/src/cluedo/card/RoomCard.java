package cluedo.card;

import cluedo.game.Game;
import cluedo.game.Game.Room;

public class RoomCard extends Card{
	private Game.Room room;

	public RoomCard(Game.Room room){
		this.room = room;
	}

	public Game.Room getRoom(){
		return room;
	}

	public String toString(){
		return this.getClass().getSimpleName() + " \"" + room.name() + "\"";
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof RoomCard)){
			return false;
		}
		return this.room == ((RoomCard)o).getRoom();
	}


}
