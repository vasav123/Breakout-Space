/**
 * Just used to display the highscores...so i have to read the fil and write on the Panel
 *All of this is straight forward so i didn't comment too much here
 */
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;

public class HighScores {
	private ArrayList<String> highScores=new ArrayList<String>();
	private Font scoreFont;
	public void readFile(String name){			//reads the file and puts everything in the highscore list
		Scanner inFile=null;
		highScores.clear();
    	try{
    		inFile=new Scanner (new BufferedReader (new FileReader(name+".txt")));
    	}
    	catch(IOException ex){
    		System.out.println("Did you forget to make the"+name+".txt file?");
    	}
    	while (inFile.hasNextLine()){
    			highScores.add(inFile.nextLine());
    	}
	}
	
    public HighScores() {
		readFile("highscores");
		try{
			scoreFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Starjedi.ttf"))).deriveFont(0,72);
			
		}
		catch(IOException ioe){
			System.out.println("error loading StarJedi.tff");
		}
		catch(FontFormatException ffe){
			System.out.println("Something went wrong with the font :( .");
		}
    }
    public void display(Graphics g){
    	readFile("highscores");		//reads the file
    	int x=0;		
    	int y=0;
    	g.setColor(new Color(0,0,0));
    	g.setFont(scoreFont);
    	for (int i=0;i<10;i++){
    		if (i>=5){
    			x=450;
    			y=350;
    		}
    		g.drawString(i+1+": "+highScores.get(i),100+x,(i*70)+275-y);	//writes the file
    	}
    }
}