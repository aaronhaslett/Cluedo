package cluedo.card;

import java.awt.Dimension;
import java.awt.image.BufferedImage;


public abstract class Card {

	/**
	 * width * HEIGHT_RATIO = height
	 */
	public static final float WIDTH_RATIO = 0.60f;
	public static final Dimension MAX_SCALE_SIZE = new Dimension(160, 220);

	/**
	 * @return a buffered image of the object represented by the card
	 */
	public abstract BufferedImage getBufferedImage();

}
