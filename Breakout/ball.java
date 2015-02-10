/**
 *Ball class is used too keep all the info of the ball and check where it collides and if it collides
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Rectangle;
public class ball {
	private int x,y,radius,angle,velocity,dx,dy;
	private Rectangle ballRect;		
    public ball(){
    	x=500;		//i always make a new ball at the 500 580 possition
    	y=580;
    	radius=10;
    	angle=45;
    	velocity=5;
    	dx=1;			//direction of x it is go 1 means right and -1 means left
    	dy=1;			//dy keeps track of the direction of up and down 1 mean up and -1 means down
    }
    public void move(){
    	x+=(velocity*Math.cos(Math.toRadians(angle)))*dx;			//changes the x and y coordinate
    	y-=(velocity*Math.sin(Math.toRadians(angle)))*dy;			//its minus because the y increase as they go down
    }
    public void checkEdges(){
    	if (x>=980){			//if it hits the side then switch the x direction
    		dx=-1;
    	}
    	if (x<=0){			//left side
    		dx=1;
    	}
    	if (y<=0){			//up wall
    		dy=-1;
    	}
    }
    public boolean collidieBox(Rectangle box){
		if(new Rectangle((int)(box.getX()),(int)(box.getY()),1,20).intersects(new Rectangle (x,y,20,20))){//using ball as a rectangle and the recatngle i check for collison
			if (dx==1){							// this is if i hit the left of the paddlle thats why the thickness is 1...reduces glitches
				dx*=-1;							//gives it the look that hitting the cure allows you to bounce it ack in the same direction
			}
			dy*=-1;
			return true;
		}
		else if(new Rectangle((int)(box.getX()+box.getWidth()-1),(int)(box.getY()),1,20).intersects(new Rectangle (x,y,20,20))){		//right side same purpose as above
			if (dx==-1){
				dx*=-1;
			}
			dy*=-1;
			return true;
		}
		else if (box.intersects(new Rectangle (x,y,20,20))){//checks if it hits the box overall after 
			dy*=-1;
			return true;
		}
		return false;				//if it didn't hit anything return false
    }
    public boolean collideRect(Rectangle r){
    	if (new Rectangle((int)(r.getX())-1,(int)(r.getY()-1),82,1).intersects(new Rectangle (x,y,20,20))){//to reduce the amount of glitches i made it so that the square is surronded by 1 pixel
    		dy*=-1;																							// boxes so that i can keep track of how to bounce the ball		
    		move();
    		return true;																					//this one is the top
    	}
    	else if (new Rectangle((int)(r.getX()+81),(int)(r.getY()),1,18).intersects(new Rectangle (x,y,20,20))){//this ine is right side
    		dx*=-1;
    		move();
    		return true;
    	}
    	else if (new Rectangle((int)(r.getX()-1),(int)(r.getY()),1,18).intersects(new Rectangle (x,y,20,20))){//this one is the one on left
    		dx*=-1;
    		move();
    		return true;
    	}
    	else if (new Rectangle((int)(r.getX()-1),(int)(r.getY()+20),82,1).intersects(new Rectangle (x,y,20,20))){//this one is the on the bottom
    		dy*=-1;
    		move();
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    //all fo these are mosetly getters and setter methodes to make life easier in the Gamepanel
    public int getRadius(){
    	return radius;
    }
    public int getAngle(){
    	return angle;
    }
    public int getX(){
    	return x;
    }
    public void setX(int x){
    	this.x=x;
    }
    public int getY(){
    	return y;
    }
    public void setY(int y){
    	this.y=y;
    }
    public int getVelocity(){
    	return velocity;
    }
    public void setVelocity(int nv){
    	velocity=nv;
    }
    public void setAngle(int a){
    	angle=a;    	
    }
    public boolean checkFall(){
    	if (y<800){
    		return false;	
    	}
    	return true;	
    }
    public void Reset(){//reset all the ball's stats that are neseccary
    	angle=45;
    	velocity=5;
    	dx=1;
    	dy=1;
    } 
}