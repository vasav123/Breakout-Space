/**
 * 	VasavShah
 *	ISC4U
 *	Mr.Mackenzie
 *	
 *
 *
 * This is my main class. Switching between game and the rest of the game happens here.
 * Setting the Highscore also happens here since this is like the "centeral hub"
 */
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.Collections;

public class BreakoutMain extends JFrame implements ActionListener{
	GamePanel game;	//game
	MainScreen ms;	//main screen
	int level;		//keeps track of the level you're on currently
	int score;		// keeps track of the overall
	javax.swing.Timer myTimer;					
	PrintWriter outFile;					//the outputfile
	private ArrayList <Integer> highScores;	//holds the highscores
    public BreakoutMain(){
    	/*So here i load all the pictures that i need to play any level. 
    	 *Depending on the level the picture changes and my level variable keeps track of that
    	 **/
    	super ("Breakout");
    	setLayout(null);
    	setSize(1000,800);
    	ms=new MainScreen();			//the regular stuff to do this things
    	ms.setSize(1000,800);
    	ms.setLocation(0,0);
    	ms.setVisible(true);
    	add(ms);
    	level=0;									//this changes later on....
    	highScores=new ArrayList <Integer>();		
        game = new GamePanel("level"+level,0, new ImageIcon ("level1Background.png").getImage()); 		//the JPanel for my game is created here 
    	game.setSize(1000,800);
        game.setLocation(0,0);
        game.setVisible(false);
        add(game);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
    	setVisible(true);
    	myTimer=new javax.swing.Timer (10,this);
    	myTimer.start();
		readFile("highscores");																		//reads the files and put all the highscore in an arraylist
    }
    public void actionPerformed (ActionEvent e){
	/*This is like the main while loop. Eventhough i don't use ActionEvent I use it as a while Loop
	 *The only reason I didn't use any of the Jframe things like Jbutton, JtextFeild... etc is because i didn't like how i couldn't 
	 *customize it according to my needs
	 **/							
 		if (ms.goToGame()==false){//if you're on the main screen
 			ms.requestFocus();		//the focus switches to mains screen 
 			ms.repaint(); 			//draw everything on it
 			level=ms.getLevelSelected(); 	//intially it becomes one but if you go to level select it changes
 			game = new GamePanel("level"+level,0, new ImageIcon ("level"+level+"Background.png").getImage());	//incase i change the level i want to update my game right away
 			game.setSize(1000,800);
	        game.setLocation(0,0);
	        game.setVisible(false);
	        add(game);
 				
 		}
    	if (ms.goToGame()){		// if you're on the game 
    		if (level<=10){		//if you're less than level 10 (final level)
	    		ms.setVisible(false);
	    		game.setVisible(true);
	    		game.requestFocus();
		    	game.moveBox();			//moves the paddlle
		    	game.checkCollisions(); 	//checks for everything
		    	game.repaint(); 			//draws everything
		    	score=game.getScore();	//updates the score
		    	if (game.levelComplete()){	//if you're done the level moves on automatically
			    	changeLevel();
	    		}    			
    		}

    		else{	//if you're done the whole game it just goes back to the menu
    			game.setVisible(false);	
    			ms.requestFocus();
    			ms.setScreen("main");
    			game.reset("level"+level,0);
    			ms.setVisible(true);
    			highScores.add(score);
    			writeInFile();
		    }
    		if (game.checkRestartDueToDeath()){	//if you die 3 times you reset everything
    			game.setVisible(false);
    			ms.requestFocus();
    			ms.setScreen("main");
    			game.reset("level"+level,0);
    			ms.setVisible(true);
    			highScores.add(score);
    			writeInFile();
    			
    		}
    		
    	}
    	
    }
    public void changeLevel(){//changes the level
    	level++;
    	game=new GamePanel("level"+level,score,new ImageIcon ("level"+level+"Background.png").getImage());
    	game.setSize(1000,800);
    	game.setLocation(0,0);
    	add(game);//just makes a new game
    }
	public void readFile(String name){
		//reads the file to keep track of highscores
		Scanner inFile=null;
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));
    	}
    	catch(IOException ex){
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
    	while (inFile.hasNextLine()){
    			highScores.add(Integer.parseInt(inFile.nextLine()));
    	}
	}
	public void writeInFile(){	
		//writes the highscore
		Collections.sort(highScores);		//sorts the highscores lowest to highest
		Collections.reverse(highScores);	//makes it highest to lowest
		try{
			outFile=new PrintWriter(new BufferedWriter(new FileWriter("highscores.txt")));
			for(int i=0;i<10;i++){
				outFile.println(""+highScores.get(i));		//writes the highscore in the file
			}
			outFile.close();
		}
		catch(IOException ex){
			System.out.println("yooo stop noobing out");
		}

	}
    public static void main(String [] args) {
    	BreakoutMain gamepanel =new BreakoutMain();		//just starts the program
	}
}
