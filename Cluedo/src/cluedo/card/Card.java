package cluedo.card;

import java.awt.image.BufferedImage;


public abstract class Card {

	/**
	 * width * HEIGHT_RATIO = height
	 */
	public static final float WIDTH_RATIO = 0.60f;

	public abstract BufferedImage getBufferedImage();

}
