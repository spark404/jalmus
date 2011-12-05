package net.jalmus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * <p>Title: Jalmus</p>
 *
 * <p>Description: Rhythm class c</p>
 *
 * <p>Copyright: RICHARD Christophe Copyright (c) 2006</p>
 *
 *
 * @RICHARD Christophe
 * @version 1.0
 */

public class Rhythm {
  double duration; // 4 round, 2 white, 1 black, 0.5 cross, 0.333 triplet, 0.25 double cross
  int position;
  int pitch;
  int rowNumber; // 0 for the first row on the staff
  int tripletValue;
  boolean pointee;
  boolean silence;
  int ypos;

  int groupee; //0 non regroup�e 1 debut de regroupement 2 fin du regroupement

  private static int NOTEREADING = 1;
  private static int RHYTHMREADING = 2;
  private static int SCOREREADING = 3;
  
  public Rhythm(double val, int pos, int p, int np, boolean pt, boolean sl, int bm) {
    this.duration = val;
    this.position = pos;
    this.pitch = p;
    this.rowNumber = np;
    this.pointee = pt;
    this.silence = sl;
    this.groupee = bm;
    this.ypos = 0;
  }

  public void init() {
    this.duration = 0;
    this.position = 0;
    this.rowNumber = 0;
    this.pitch = 71; //B for rhythm game
    this.pointee = false;
    this.silence = false;
    this.groupee = 0;
    this.ypos = 0;
  }

  public void copy(Rhythm r) {
    this.duration = r.duration;
    this.position = r.position;
    this.pitch = r.pitch;
    this.rowNumber = r.rowNumber;
    this.silence = r.silence;
    this.pointee = r.pointee;
    this.groupee = r.groupee;
  }

  public void setDuration(int i) {
    this.duration = i;
  }

  public double getDuration() {
    return this.duration;
  }

  public void setPitch(int i) {
      this.pitch = i;
  }

  public int getPitch() {
      return this.pitch;
  }
    
  public void setGroupee(int i) {
    this.groupee = i;
  }
  
  public boolean isGroupee() {
     return this.groupee != 0;
    }

  public int getGroupee() {
    return this.groupee;
  }
  
  public int getPosition() {
      return this.position;
  }

  public void setSilence(boolean b) {
    this.silence = b;
  }

  public boolean isSilence() {
    return this.silence;
  }

  public boolean isnull() {
      return this.duration == 0;
  }

  public void setTrpletValue(int val) {
	  this.tripletValue = val;
  }

  public void paint(Graphics g, int Leveltype,  Font f, ScoreLevel sl, int position, int rowsDistance, boolean courant, int scoreYpos, Component l) {

	if (this.ypos == 0) 
		this.ypos = sl.getYpos(this.pitch);
	int noteY = this.ypos;
    //@SuppressWarnings("unused")

    //  g.setColor(couleur);
    g.setFont(f.deriveFont(57f));
    if (courant)
    	g.setColor(Color.red);
    else
    	g.setColor(Color.black);

    if (this.duration == 4) {
      if (this.silence) {
    	  if (Leveltype == RHYTHMREADING) {
    		  g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+20, 12, 7);
    	  }
    	  else g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+14, 12, 7);
      }

      else { // semibreve
    	  g.setFont(f.deriveFont(50f));
    	g.drawString("w", this.position - 3, scoreYpos + this.rowNumber*rowsDistance + noteY +13);
      }

    }
    else if (this.duration == 2) {
      if (this.silence) {
    	  if (Leveltype == RHYTHMREADING) {
    		  g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+14, 12, 7);
    	  }
    	  else g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+10, 12, 7);
      }

      else { // minima
    	g.drawString("h", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +12);
      }

    }

    else if (this.duration == 3) {
        if (this.silence) { //pause only when time signature 3/4
        	  if (Leveltype == RHYTHMREADING) {
        		  g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+20, 12, 7);
        	  }
        	  else g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+14, 12, 7);
        }

        else { // minima
      	g.drawString("d", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +12);
        }

      }
    
    
    else if (this.duration == 1) {
      if (this.silence) { // pause
    	  g.drawString("Q", this.position, scoreYpos + this.rowNumber*rowsDistance +43);
      }

      else { // semiminima
  	String sm = "" + (char)0xF6;
    	int voffset = 53;
    	if (Leveltype == RHYTHMREADING) { // always beam to up
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
    	else if (noteY > 18) {
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
    	g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
    	
      }
    	  
    }
    
    else if (this.duration == 0.333) {
    	if (Leveltype == RHYTHMREADING) { //beam upward
    		String sm = "" + (char)0xF4;
	    	//boolean upward  = false;
	    	int voffset = 23;
	 
	    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
	    	g.drawString(sm, this.position, ypos + noteY + voffset);
	    	if (this.tripletValue != 0) {
	    		int lowestYpos = 0;
	    		if (this.tripletValue < 100) lowestYpos = sl.getYpos(this.tripletValue) + ypos;
	    		else lowestYpos = sl.getYpos(this.tripletValue - 100) + ypos;
	        	//g.drawLine(this.position, ypos + noteY + 10, this.position, lowestYpos + 40);
	    		if(this.tripletValue < 100) { // means this is the first note of the triplet. Draw horizontal bar
	        		g.fillRect(this.position+11, lowestYpos - 31, 49, 3);
	        		g.setFont(new Font("Arial", Font.BOLD, 15));
	        		g.drawString("3", this.position + 32, lowestYpos - 33);
	    		}
	    	}
    	}
    	else {
	    	String sm = "" + (char)0xF6;
	    	//boolean upward  = false;
	    	int voffset = 53;
	    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
	    	g.drawString(sm, this.position, ypos + noteY + voffset);
	    	if (this.tripletValue != 0) {
	    		int lowestYpos = 0;
	    		if (this.tripletValue < 100) lowestYpos = sl.getYpos(this.tripletValue) + ypos;
	    		else lowestYpos = sl.getYpos(this.tripletValue - 100) + ypos;
	        	g.drawLine(this.position, ypos + noteY + 10, this.position, lowestYpos + 40);
	    		if(this.tripletValue < 100) { // means this is the first note of the triplet. Draw horizontal bar
	        		g.fillRect(this.position, lowestYpos + 37, 49, 5);
	        		g.setFont(new Font("Arial", Font.BOLD, 15));
	        		g.drawString("3", this.position + 20, lowestYpos + 54);
	    		}
	    	}
    	}
    }

    else if (this.duration == 0.5) {
      if (this.silence) { // pause
    	  g.setFont(f.deriveFont(48f));
	      g.drawString("E", this.position, scoreYpos + this.rowNumber*rowsDistance + 40);
      }
      else {

       if (this.groupee == 1 || this.groupee == 2) {
    	 String sm = "" + (char)0xF6;
       	 int voffset = 53;
       	 
     	if (Leveltype == RHYTHMREADING) { // always beam to up
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
     	else  if (noteY > 18) {
       		sm = "" + (char)0xF4;
       		voffset = 23;
       	 }
       	 g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
    	   
        	
       	 if (this.groupee == 1) {
           g.setColor(Color.BLACK);
           g.fillRect(this.position+11, scoreYpos+ this.rowNumber*rowsDistance+noteY-30, 37, 3);
       	 }
       }

       else {
      	 String sm = "" + (char)0xCA;
       	 int voffset = 43;
     	if (Leveltype == RHYTHMREADING) { // always beam to up
     		sm = "" + (char)0xC8;
       		voffset = 13;
    	}
     	else if (noteY > 18) {
       		sm = "" + (char)0xC8;
       		voffset = 13;
       	 }
       	 g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
       }

      }                
    }

    if (!this.silence) {
    	if (noteY < -22)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance - 20, this.position + 15, scoreYpos + this.rowNumber*rowsDistance - 20);
    	if (noteY < -12)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance - 10, this.position + 15, scoreYpos + this.rowNumber*rowsDistance - 10);
    	if (noteY > 38)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance + 50, this.position + 15, scoreYpos + this.rowNumber*rowsDistance + 50);
    }

    g.setColor(Color.black);
   }
  
  
  


}
