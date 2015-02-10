/**
 * V stands for vasav ;)
 *I didn't like how i wasn't able to customize the boeses according to my needs so i have to create this
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.Rectangle;

public class VButton {
	private Image pic1,pic2,pic3;
	private int x,y;
    public VButton(int x, int y,String name){
    	this.x=x;
    	this.y=y;
    	pic1=new ImageIcon(name+"Normal.png").getImage();			//all the files have been made to fit this
    	pic2=new ImageIcon(name+"Hover.png").getImage();
    	pic3=new ImageIcon(name+"Selected.png").getImage();
    }
    public Image getImg(int mx,int my, boolean clicked){//if you hove over it there is a diffrent picture 
    	Rectangle r=new Rectangle(x,y,pic1.getWidth(null),pic1.getHeight(null));
    	if (r.contains(mx,my) && clicked){
    		return pic3;										//pic 2 is when you click it
    	}
    	else if (r.contains(mx,my)){						//when you hove over it
    		return pic2;
    	} 
    	else{
    		return pic1;								//normal
    	}	
    }
    //getters and sett methods
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public Rectangle getRect(){
    	return new Rectangle(x,y,pic1.getWidth(null),pic1.getHeight(null));
    } 
}