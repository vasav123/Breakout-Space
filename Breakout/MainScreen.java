/**
 * This is my Main Screen. Over here i can switch between level Select, game, aHighScores, andHelp
 *I havemy own button made here
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MainScreen extends JPanel implements MouseListener,MouseMotionListener{		//Mouse Listener is used to get when it is clecked and MouseMotionListner is used to get the position
	Image main,s,h,hs;
	String screen;
	HighScores topScores;							
	private int mx,my,levelSelection=1;
	VButton play;													//play bttn
	VButton help;													//help btn
	VButton lvlSelect;												//level select bttn
	VButton hScores;												//highscore bttn
	ArrayList<VButton> bttnsMenu =new ArrayList <VButton> ();		//keeps track of all the bttns in the menu screen
	ArrayList<VButton> selectBttns=new ArrayList<VButton>();		//keeps track of all the levels on levelselect
	VButton back;													//back bttn
	VButton lvlPlay;												//level paly bttn
	boolean clicked=false;
    public MainScreen(){					//i create another Japnel and switch back and forth in the Breakout Main
    	super();		
    	setFocusable(true);	
		grabFocus();
		play=new VButton(314,252,"menuBar");				//creates all the bttns adn adds them to their lists
		lvlSelect=new VButton(314,419,"menuBarSelect");
		help= new VButton(314,586,"menuBarHelp");
		back= new VButton(0,600,"Back");
		lvlPlay=new VButton(750,650,"play");
		hScores=new VButton (800,625,"highscores");
		topScores=new HighScores();
		mx=0;
		my=0;
		screen="main";							///start out as main then switches between main..game..help...and etc
		bttnsMenu.add(play);
		bttnsMenu.add(help);
		bttnsMenu.add(lvlSelect);
		addMouseListener(this);
		addMouseMotionListener(this);
		main= new ImageIcon("Menu.png").getImage();			//background pics
		s=new ImageIcon ("Select.png").getImage();
		h=new ImageIcon ("Help.png").getImage();
		hs=new ImageIcon("HighScores.png").getImage();
		for(int i=1;i<=10;i++){			
			int x=i;
			int j=0;
			if (i>5){
				x-=5;
				j=1;
			}
			selectBttns.add(new VButton(x*200-225,j*200+200,"LevelSelect/"+i));		//makes it into the grid
		}
    }
    public void paintComponent(Graphics g){										//draws all the stuff on that screen
    	if (screen.equals("main")){
    	    g.drawImage(main,0,0,this);
    	    g.drawImage(hScores.getImg(mx,my,clicked),hScores.getX(),hScores.getY(),this);
	    	for (int i=0; i<bttnsMenu.size();i++){
	    		g.drawImage(bttnsMenu.get(i).getImg(mx,my,clicked),bttnsMenu.get(i).getX(),bttnsMenu.get(i).getY(),this);
	    	}	
    	}
    	else if (screen.equals("help")){			//draws things on help
    		g.drawImage(h,0,0,this);
    		g.drawImage(back.getImg(mx,my,clicked),back.getX(),back.getY(),this);
    	}
    	else if(screen.equals("select")){		//draws things on select
    		g.drawImage(s,0,0,this);
    		g.drawImage(back.getImg(mx,my,clicked),back.getX(),back.getY(),this);
    		g.drawImage(lvlPlay.getImg(mx,my,clicked),lvlPlay.getX(),lvlPlay.getY(),this);
    		for (VButton v:selectBttns){
    			g.drawImage(v.getImg(mx,my,clicked),v.getX()+40,v.getY(),this);
    		}
    	}
    	else if (screen.equals("highscores")){		//draws things on highscore
    		g.drawImage(hs,0,0,this);
    		g.drawImage(back.getImg(mx,my,clicked),back.getX(),back.getY(),this);
    		topScores.display(g);
    	}
    }
    public int getLevelSelected(){			//returns the selected level
		return levelSelection;
    }
    public boolean goToGame(){						//if this is true then i switch to the GamePanel instead of MAinScreen
    	if (screen.equals("game")){
    		return true;
    	}
    	return false;
    }
    public void setScreen(String s){					//sets the screen after i die or finish the game
    	screen=s;
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){clicked = false;}
    public void mousePressed(MouseEvent e){							//if i click on anything it changes my screen according to that
    	clicked = true;
    	if (play.getRect().contains(mx,my)&&screen.equals("main")){
    		screen = "game";
    	}
    	else if (help.getRect().contains(mx,my) && screen.equals("main")){
    		screen="help";
    	}
    	else if (lvlSelect.getRect().contains(mx,my) && screen.equals("main")){
    		screen="select";
    	}
    	else if (hScores.getRect().contains(mx,my) && screen.equals("main")){
    		screen="highscores";
    	}
    	else if (back.getRect().contains(mx,my) && screen.equals("help")){
    		screen="main";
    	}
    	else if (back.getRect().contains(mx,my)&& screen.equals("select")){
    		screen="main";
    	}
    	else if (back.getRect().contains(mx,my) && screen.equals("highscores")){
    		screen="main";
    	}
    	else if (screen.equals("select")){				//selects the level
			for(int i=0;i<selectBttns.size();i++){
	    		if (selectBttns.get(i).getRect().contains(mx,my)){
	    			levelSelection=i+1;
	    		}
	    	}
	    	if (lvlPlay.getRect().contains(mx,my)){
	    		screen="game";
	    	}    		
    	}
    	
    }
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseDragged(MouseEvent e){    		//gets the pos when dragge(clicked an moved)
      mx = e.getX();
      my = e.getY();
    }
    public void mouseMoved(MouseEvent e){	///gets the pos(not clicked and moved)
		mx=e.getX();
    	my=e.getY();
    }
}