/**
 * @(#)Game.java
 *
 *
 * @author 
 * @version 1.00 2014/1/23
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GamePanel extends JPanel implements KeyListener{
	private int boxx,boxy,boxx2,boxy2;
	int angle;
	public boolean [] keys;
	public GamePanel(){
		super();
		angle=270;
		boxx=0;
		boxy=-10;
		boxx2=(int)(300*Math.cos(Math.toRadians(angle+20)))+500;
		boxy2=(int)(300*Math.sin(Math.toRadians(angle+20)))+400;
		keys=new boolean[2000];
		setFocusable(true);
		grabFocus();
		addKeyListener(this);
	}
	public void paintComponent(Graphics g){
		g.setColor(new Color (222,222,222));
		g.fillRect(0,0,getWidth(),getHeight());
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color (0,0,222));
        g2.setStroke(new BasicStroke(10));
        g2.draw(new Line2D.Float(boxx, boxy, boxx2, boxy2));
		/*g.setColor(new Color (0,0,222));
		g.fillRoundRect(boxx,boxy,100,20,45,90);*/
	}
	public void moveBox(){
		if (keys[KeyEvent.VK_LEFT]){
			angle-=5;
			
		}
		else if (keys[KeyEvent.VK_RIGHT]){
			angle+=5;
		}
		boxx=(int)(300*Math.cos(Math.toRadians(angle)))+500;
		boxy=(int)(300*Math.sin(Math.toRadians(angle)))+400;
		boxx2=(int)(300*Math.cos(Math.toRadians(angle+20)))+500;
		boxy2=(int)(300*Math.sin(Math.toRadians(angle+20)))+400;
		repaint();
	}
	public void keyPressed(KeyEvent evt){
		int i=evt.getKeyCode();
		keys[i]=true;
	}
	public void keyReleased(KeyEvent evt){
		int i=evt.getKeyCode();
		keys[i]=false;
	}
	public void keyTyped(KeyEvent evt){
		
	}
}