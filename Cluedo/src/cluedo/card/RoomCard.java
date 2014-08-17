package cluedo.card;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.game.Game;
import cluedo.util.Util;

public class RoomCard extends Card{
	private Game.Room room;

	public RoomCard(Game.Room room){
		this.room = room;
	}

	public Game.Room getRoom(){
		return room;
	}

	public String toString(){
		return this.getClass().getSimpleName()+" \"" + room.name() + "\"";
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof RoomCard)){
			return false;
		}
		return this.room == ((RoomCard)o).getRoom();
	}

	@Override
	public BufferedImage getBufferedImage(){
		String path = Util.CARD_IMAGE_PATH + "room" + File.separator + room.name()+".jpg";
		BufferedImage image=null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
