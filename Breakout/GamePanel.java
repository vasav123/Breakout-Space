/**
 * Most of my Game Logic happens here 
 *This keeps track of my paddle..i didn't create a new paddle class because theres only one paddle so no point in creating a new one
 *This program requires to take in a file that contains the brick info and stuff...
 *I implement KEyListener for multiple things like shooting, launching and moving
 *I have my ball objects here and my rect objects in here
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.awt.Rectangle;
public class GamePanel extends JPanel implements KeyListener{
	private int boxx,boxy,bwidth,score,life;		
	private ArrayList<Laser>laser=new ArrayList<Laser>();			//keeps track of laser...later on i made it so that you can only haave 2 at a time    	
	private boolean launching;										//keeps track of whether you're launching the or not
	private ArrayList <ball> b=new ArrayList<ball>();				//a list of ball objects so that i can have multiple balls..even though i only have 2 lol
	private ArrayList <String> file=new ArrayList<String>();		//laods in the level info		
	private ArrayList <Rects> rects=new ArrayList <Rects>();		//a list of all the rectangles on the screen
	private ArrayList <String> currentPups=new ArrayList<String>();	//keeps track of all the powers that i have right now
	private ArrayList <Image> displayPup=new ArrayList<Image>();	//keeps track of all the images i have to show that i have
	private int puptimer=0;											//once this reaches 1000 i remove all the powerups however i reset it everytime i get a new powerup
	public boolean [] keys;											//keeps track of what keys are pressed
	private Font scoreFont;											//just the custom Font
	private Image background,paddle,lasers,bigPaddle,smallPaddle,ball,brick1,brick2,brick3,displayAid,laserPup,fastPup,slowPup,doublePup,longPup,smallPup;//all the images i need
	
	
	public GamePanel(String l,int s,Image back){
		super();
		boxx=450;							//most of this things is straight forward
		boxy=600;
		bwidth=100;							//keeps track of the width
		b.add(new ball());
		keys=new boolean[2000];				//keeps track of the keys pressed
		setFocusable(true);
		grabFocus();
		addKeyListener(this);
		readFile(l);						//reads the file and sets up the rectanles aswell
		launching=true;							
		score=s;							//score carry forwards
		life=3;								
		background=back;					//background picture changes each time
		paddle=new ImageIcon("paddle.png").getImage();						//loads all the files
		lasers=new ImageIcon("laser.png").getImage();
		bigPaddle=new ImageIcon ("paddleLong.png").getImage();
		smallPaddle=new ImageIcon("smallpaddle.png").getImage();
		ball=new ImageIcon("ball.png").getImage();
		brick1=new ImageIcon("brick1.png").getImage();
		brick2=new ImageIcon("brick2.png").getImage();
		brick3=new ImageIcon("brick3.png").getImage();
		displayAid=new ImageIcon ("Displayaid.png").getImage();
		laserPup=new ImageIcon("laserPup.png").getImage();
		fastPup=new ImageIcon ("fastPup.png").getImage();
		slowPup =new ImageIcon("slowPup.png").getImage();
		doublePup=new ImageIcon("doublePup.png").getImage();
		longPup=new ImageIcon("longPup.png").getImage();
		smallPup=new ImageIcon("shrinkPup.png").getImage();
		try{		//loads the font
			scoreFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Starjedi.ttf"))).deriveFont(0,32);
			
		}
		catch(IOException ioe){
			System.out.println("error loading StarJedi.tff");
		}
		catch(FontFormatException ffe){
			System.out.println("Something went wrong with the font :( .");
		}
	}
	public void paintComponent(Graphics g){	//draw everything in the game
		g.drawImage(background,0,0,this);
		g.drawImage(displayAid,0,620,this);
		g.setFont(scoreFont); 
		g.setColor(new Color (153,0,153));	
		g.drawString("Score: "+score,650,660);		//shows the score and amount of lives
		g.drawString("Lives:"+life,650,710);		
		for(int i=0;i<rects.size();i++){			//the image of the picture changes according to howmany hits it needs
			if (rects.get(i).getHits()==1){
				g.drawImage(brick1,rects.get(i).getX(),rects.get(i).getY(),this);
			}
			else if (rects.get(i).getHits()==2){
				g.drawImage(brick2,rects.get(i).getX(),rects.get(i).getY(),this);
			}
			else if (rects.get(i).getHits()==3){
				g.drawImage(brick3,rects.get(i).getX(),rects.get(i).getY(),this);
			}
			else{										//if the hit is 0 meaning its destroyed i need to check for any powerups
				if (!rects.get(i).getPowerup().equals("")){
					checkPowerUpType(rects.get(i));	//if i hit the birck and its not blank i have to add it
					puptimer=0;
				}
				rects.remove(i);						//removes the brick from the list also adds teh flashy effect idk how to fix that
			}
			
		}
		for (int i=0;i<laser.size();i++){				//if there are any lasers itdraws them
			g.drawImage(lasers,laser.get(i).getX(),laser.get(i).getY(),this);
		}
		for (int i=0;i<displayPup.size();i++){		///draw anypowerups that i have currenttly
			g.drawImage(displayPup.get(i),i*75,660,this);
		}
		if (bwidth==100){				//regular paddle
			g.drawImage(paddle,boxx,boxy,this);
		}
		else if(bwidth==60){		//small paddle
			g.drawImage(smallPaddle,boxx,boxy,this);
		}
		else if (bwidth==150){//big paddle
			g.drawImage(bigPaddle,boxx,boxy,this);
		}
		for (int i=0; i<b.size();i++){		//draws all the balls there will always be 1 unsless if you die
			g.drawImage(ball,b.get(i).getX(),b.get(i).getY(),this);	
		}
				
	}	
	
	public void readFile(String name){	//reads the file and makes rects
		Scanner inFile=null;
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));
    	}
    	catch(IOException ex){
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
    	while (inFile.hasNextLine()){
    			file.add(inFile.nextLine());
    	}
    	makeRect();	//makes the rect objects
	}
	public void makeRect(){
		for (int i=0;i<file.size();i++){
			rects.add(new Rects(file.get(i)));	//the file contains all the info i pass it as a string and change it to Integer in the rect class
		}
		addPowerUp();	//once i make the objects i need to add powerups 
	}	
	public void addPowerUp(){		//adds the power ups to random blocks only if there are more than 10 blocks... which there will always be unless i feel like making it easier
		String [] pups={"dball","fball","sball","spaddle","bpaddle","laser"};
		boolean [] possible= new boolean [rects.size()]; //keeps track if i've used the powerup already
		int pos=0;	//keeps track of the position im at in the pups
		int num;
		for (int i=0;i<possible.length;i++){
			possible[i]=true;
		}
		while (true){
			if (possible.length<10){
				break;
			}
			if (pos>=pups.length){	//once i reach the end break
				break;
			}
			num=(int)(Math.random()*((possible.length)-1));	//randomly generate the number and if that rect is valud then set that to have a powerup
			if (possible[num]){
				rects.get(num).setPowerup(pups[pos]);		//a String is set to have as a power up
				pos++;										//next element in the pups
				possible[num]=false;						//can't put anything here anymore
			}
		}
	}
	public void checkPowerUp(){//does the powerup stuff and also adds the power up to be displayed in the screen to show what power up the user has
		displayPup.clear();		//reset it eachtime cuz this get called everytime so don't want to repeat anything
		for (int i=0;i<currentPups.size();i++){
			if (currentPups.contains("fball") && displayPup.contains(fastPup)==false){		//makes sure the same thing is not rpeat
				displayPup.add(fastPup);		
				if (displayPup.contains(slowPup)){										//can't have fast ball and slow ball so remove slow ball
					displayPup.remove(slowPup);
				}
			}
			if (currentPups.contains("sball")&& displayPup.contains(slowPup)==false){
				displayPup.add(slowPup);
				if (displayPup.contains(fastPup)){								//can't have slow ball and fast ball so remove fast ball
					displayPup.remove(fastPup);
				}
			}
			if (currentPups.contains("laser")&& displayPup.contains(laserPup)==false){		//adds the laser to the display thing
				displayPup.add(laserPup);
			}
			if (currentPups.contains("bpaddle")&& displayPup.contains(longPup)==false){
				displayPup.add(longPup);
				if (displayPup.contains(smallPup)){				//can't have big paddle and small paddle
					displayPup.remove(smallPup);
				}
			}
			if (currentPups.contains("spaddle")&& displayPup.contains(smallPup)==false){
				displayPup.add(smallPup);
				if (displayPup.contains(longPup)){			//can't have small paddle and big paddle
					displayPup.remove(longPup);
				}
			}

		}
		if (currentPups.contains("fball")){				//if i have fast ball then make the ball move faster
			for (int i=0; i<b.size();i++){
				b.get(i).setVelocity(10);			//makes it move faster
			}
		}
		if (currentPups.contains("sball")){			//makes it move slower
			for (int i=0; i<b.size();i++){
				b.get(i).setVelocity(3);
			}
		}
		if (currentPups.contains("bpaddle")){		//makes the paddle bigger
			bwidth=150;
		}
		if (currentPups.contains("spaddle")){	//makes it smaller
			bwidth=60;
		}
		if (currentPups.contains("dball")){		//adds another ball
			b.add(new ball());
			currentPups.remove("dball");		//keeps add ing so i have to remove it
		}
		if (currentPups.contains("laser")){		//shoots the laser
			if (keys[KeyEvent.VK_SPACE]){
				shootBullet();				// i have 2 laeser objects one right and other left so i have to move both of them here
			}
		}

	}
	public void moveLaser(){
		if (laser.size()>0){
			for (int i=0; i<laser.size();i++){
				laser.get(i).move();		//a move function in the laser that actually moves it
				for (int j=0;j<rects.size();j++){
					if (laser.get(i).getY()<0){		//if i go off screen then remove it so i can have 2
						laser.remove(i);
						break;
					}
					if(laser.get(i).collide(rects.get(j).transformToRect())){	//if i hit anything then i have to remove it and set the rectangle to have 1 less hit required
						rects.get(j).setHits();
						laser.remove(i);
						break;
					}
				}
			}			
		}

	}
	public void shootBullet(){
		if (laser.size()==0){	///can't shoot more than 2 at a time
			laser.add(new Laser(boxx,boxy));		
			laser.add(new Laser(boxx+bwidth,boxy));	
		}
	}
	public void collision(){			//checks the collision with the bricks and the ball
		for (int i=0;i<rects.size();i++){
			for (int j=0; j<b.size();j++){	//for more than 1 ball
				if (rects.get(i).getHits()!=0){	//if i have some damage required atleast
					if (b.get(j).collideRect(rects.get(i).transformToRect())){
						score+=50;				//increase the overall score
						rects.get(i).setHits(); 			//actually sets the hits to have 1 less required
					}
				}
			}			
		}
	}
	public void launchAngle(ball sb){		//makes it so i can carry the ball and chose where i want to launch always launches at 45 degrees
		sb.setX(boxx+(int)(bwidth/2));
		sb.setY(580);
    	if (keys[KeyEvent.VK_S]){
    		sb.Reset();
    		launching=false;
    	}
    }

	public void checkPowerUpType(Rects r){		//adds the current powerup to the list
		currentPups.add(r.getPowerup());
	
	}
	public void checkTimer(){					//if the power up timer is over than i need to clear the whole list
		if (currentPups.size()>0){
			puptimer++;
			if (puptimer>1000){
				currentPups.clear();					//removes everything from the list
				bwidth=100;
				if (b.size()>1){
					b.remove(1);						//removes teh extra ball
				}
				b.get(0).setVelocity(5);				//sets the things back to normal for the ball
			}
		}
	}
	public void checkCollisions(){			//checks for all the collsions and also does ball falling down
		checkPowerUp();						
		moveLaser();						
		checkTimer();
		if (b.size()>1 && displayPup.contains(doublePup)==false){		//i have to manually add this because i have to remove inorder to not keep on making more
			displayPup.add(doublePup);
		}
		for (int i=0;i<b.size();i++){
			b.get(i).checkEdges();					//checks if it hits the edges
			b.get(i).collidieBox(new Rectangle (boxx,boxy,bwidth,1));			//checks if it collides with the rects
			collision();
			if (b.size()<=1){										
				if (b.get(i).checkFall() && launching==false){					//if i have less than 1 ball and it falls then i reset it orelse i don't
					launching=true;
					currentPups.clear();										//removes all the powerups
					bwidth=100;													
					life--;														//takes of life
				}
				if (launching==false){
					b.get(i).move();					//if not i can move the ball/s
				}
				else{
					launchAngle(b.get(i));
				}	
			}
			else{
				if (b.get(i).checkFall()){				//i remove the one that falls first
					b.remove(i);
				}
				else{
					b.get(i).move();
				}
			}
		}
	}

	public int checkBoxEdge(int x,int length){			//makes sure the paddle doesn't go off screen
		if (x+length>1000){								
			x=1000-length;
		}
		if (x<0){
			x=0;
		} 
		return x;
	}
	public void moveBox(){			//moves the box
		if (keys[KeyEvent.VK_LEFT]){
			boxx-=5;	
		}
		else if (keys[KeyEvent.VK_RIGHT]){
			boxx+=5;
		}
		boxx=checkBoxEdge(boxx,bwidth);//makes sure ideosn't go out of bounds
		
	}
	public void keyPressed(KeyEvent evt){			//if i pressed an keydown then set it to equal true
		int i=evt.getKeyCode();
		keys[i]=true;
	}
	public void keyReleased(KeyEvent evt){		//one i let go set it to equal false
		int i=evt.getKeyCode();
		keys[i]=false;
	}
	public void keyTyped(KeyEvent evt){	
	}
	public boolean levelComplete(){			//if the level is complete meaning the size of rects is 0 then return true so i can more to the next level
		if (rects.size()==0){
			return true;
		}
		return false;
	}
	public int getScore(){
		return score;
	}
	public boolean checkRestartDueToDeath(){	//if i die three times i go to the menu and start again
		if (life<0){
			return true;
		}
		return false;
	}
	public void reset(String l,int s){			//resets all the neccarry stats for the main code
		b=new ArrayList<ball>();
		file=new ArrayList<String>();
		rects=new ArrayList <Rects>();
		currentPups=new ArrayList<String>();
		displayPup=new ArrayList<Image>();
		boxx=450;
		boxy=600;
		bwidth=100;
		b.add(new ball());
		setFocusable(true);
		grabFocus();
		readFile(l);
		launching=false;
		score=s;
		life=3;
	}
}