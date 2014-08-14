package cluedo.card;

import java.awt.image.BufferedImage;


public abstract class Card {

	/**
	 * width * HEIGHT_RATIO = height
	 */
	public static final float HEIGHT_RATIO = 1.7f;

	public abstract BufferedImage getBufferedImage();

}
