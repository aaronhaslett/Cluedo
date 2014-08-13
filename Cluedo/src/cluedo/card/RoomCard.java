package cluedo.card;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cluedo.Main;
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
		return " \"" + room.name() + "\"";
	}

	@Override
	public boolean equals(Object o){
		if (!(o instanceof RoomCard)){
			return false;
		}
		return this.room == ((RoomCard)o).getRoom();
	}


	@Override
	public Image getImage(){
		BufferedImage image=null;
		try{
			image = ImageIO.read(new File(Main.CARD_IMAGE_PATH + room.name()+".jpg"));

		} catch (IOException e){
			System.out.println(Main.CARD_IMAGE_PATH + "room" + File.separator + room.name()+".jpg");
			e.printStackTrace();
		}
		return image;
	}

}
