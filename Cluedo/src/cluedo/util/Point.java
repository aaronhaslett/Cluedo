package cluedo.util;

/**
 * @author hardwiwill
 * Point in java SE was inadequate for this project.
 */
public class Point{
	private int x, y;

	public Point(int x, int y){
		setLocation(x,y);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
