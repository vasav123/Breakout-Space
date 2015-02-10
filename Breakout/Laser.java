/**
 *This class keeps track of the Laser stats amd moves the laser
 */

import java.awt.Rectangle;
public class Laser {
	private int x,y;
    public Laser(int x, int y){
    	this.x=x;
    	this.y=y;
    }
    public void move(){		//-10 makes it go up
    	y-=10;
    }
    public boolean collide(Rectangle r){			//checks if the Laser collides into anything
    	if (r.intersects(new Rectangle(x,y,5,20))){
    		return true;
    	}
    	return false;
    }
    //getters and setters
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
}