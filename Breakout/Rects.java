/**
 * @(#)Rects.java
 *
 *
 * @author 
 * @version 1.00 2014/1/30
 */
import java.awt.Rectangle;
public class Rects {
	private int x,y,hits;
	private String powerup="";
    public Rects(String stats){
    	String [] rectstats=stats.split(",");
    	x=Integer.parseInt(rectstats[0]);
    	y=Integer.parseInt(rectstats[1]);
    	hits=Integer.parseInt(rectstats[2]);
    }
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getHits(){
    	return hits;
    }
    public void setHits(){
    	hits-=1;
    }
    public Rectangle transformToRect(){
    	return(new Rectangle (x,y,80,20));
    }
    public void setPowerup(String p){
    	powerup=p;
    }
    public String getPowerup(){
    	return powerup;
    }
}