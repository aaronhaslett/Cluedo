package cluedo.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

public class Util {

	public static final String RES_PATH = "res"+File.separator;
	public static final String IMAGE_PATH = RES_PATH + "img"+File.separator;
	public static final String CARD_IMAGE_PATH = IMAGE_PATH+"cards"+File.separator;
	public static final String DICE_IMAGE_PATH = IMAGE_PATH+"dice"+File.separator;

	/**
	 * Code taken from a stackoverflow answer http://stackoverflow.com/a/14550112/1696114
	 * @param image
	 * @param width: new width for the image.
	 * @param height: new height for the image.
	 * @return a resized bufferedimage
	 */
	public static BufferedImage imageResize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}

}
