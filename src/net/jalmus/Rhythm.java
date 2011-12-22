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
import java.util.ResourceBundle;

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
  boolean stemup; // true stem up false stem down
  boolean pointee;
  boolean silence;
  int ypos;

  int groupee; //0 non regroup�e 1 debut de regroupement 2 fin du regroupement

  private static int NOTEREADING = 1;
  private static int RHYTHMREADING = 2;
  private static int SCOREREADING = 3;
  
  public Rhythm(double val, int pos, int p, int np, boolean su, boolean pt, boolean sl, int bm) {
    this.duration = val;
    this.position = pos;
    this.pitch = p;
    this.rowNumber = np;
    this.stemup = su;
    this.pointee = pt;
    this.silence = sl;
    this.groupee = bm;
    this.ypos = 0;
  }

  public void init() {
    this.duration = 0;
    this.position = 0;
    this.rowNumber = 0;
    this.stemup = true;
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
    this.stemup = r.stemup;
    this.tripletValue = r.tripletValue;
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

  
  public boolean samenotePitch(int pitchbase){
      int x;
       boolean b = false;
       for (x=0;x<=9;x++){ 
         if ((this.pitch+x*12==pitchbase) | (this.pitch-x*12==pitchbase))  b = true;                                                   }
           return b;
           }

  public boolean samenotePitch(int pitchbase, int p){
      int x;
       boolean b = false;
       for (x=0;x<=9;x++){ 
         if ((p+x*12==pitchbase) | (p-x*12==pitchbase))  b = true;                                                   }
           return b;
           }

   
 int getYpos(ScoreLevel sl, int p) {
	 int noteY = 0;
	  int keyoffset = 0; //for bass key
      Tonality t = sl.getCurrentTonality();
      
	  if (sl.isCurrentKeyBass()) keyoffset = -60;

      if (this.samenotePitch(0,p)) {  // DO 
      			noteY = (60-p)*35/12+43 + keyoffset;
      	}
      
      else if (this.samenotePitch(1,p)) { // DO# REb
    	  if (t.isflat()) noteY = (61-p)*35/12 + 43 + keyoffset;
    	    else   noteY = (61-p)*35/12 + 38 + keyoffset; 
      	        		
      }	
      
      else   if (this.samenotePitch(2,p)) 	noteY = (62-p)*35/12 + 38 + keyoffset;
     
                 
      
      else if (this.samenotePitch(3,p)) { //RE# MIb 
    	  if (t.issharp()) noteY = (63-p)*35/12 + 38 + keyoffset;  
    	  else noteY = (63-p)*35/12 + 33 + keyoffset;  
    		
      }	
      
      else   if (this.samenotePitch(4,p)) noteY = (64-p)*35/12 + 33 + keyoffset; 
   
				
   
      else   if (this.samenotePitch(5,p)) noteY = (65-p)*35/12 + 28 + keyoffset; 
     
   
      else if (this.samenotePitch(6,p)) { //FA# SOLb
      	  if (t.issharp()) noteY = (66-p)*35/12 + 28 + keyoffset;  
          	  else noteY = (66-p)*35/12 + 23 + keyoffset;  
            }	
      
      else   if (this.samenotePitch(7,p)) 	noteY = (67-p)*35/12 + 23 + keyoffset; 
 
      else if (this.samenotePitch(8,p)) { //SOL# LAb
      	  if (t.issharp()) noteY = (68-p)*35/12 + 23 + keyoffset;  
        	  else noteY = (68-p)*35/12 + 18 + keyoffset;  
      		
          }	
     
      else   if (this.samenotePitch(9,p)) {  	noteY = (69-p)*35/12 + 18 + keyoffset; 
      }
      
      
      else if (this.samenotePitch(10,p)) { // LA# SIb
      	  if (t.issharp())      		noteY = (70-p)*35/12 + 18 + keyoffset;  
          	  else      		noteY = (70-p)*35/12 + 13 + keyoffset;  
      }	
      
      else   if (this.samenotePitch(11,p))  noteY = (71-p)*35/12 + 13 + keyoffset; 

	 
	 return noteY;
 }
     
 

      
  
  public void paint(Graphics g, int Leveltype,  Font f, ScoreLevel sl, int position, int rowsDistance, boolean courant, int scoreYpos, Component l) {

	//if (this.ypos == 0) 
//		this.ypos = sl.getYpos(this.pitch);
//	int noteY = this.ypos;

	
	
	  String alt="";
	  int noteY = 0;
	  int keyoffset = 0; //for bass key
      Tonality t = sl.getCurrentTonality();
      
      if (Leveltype  == SCOREREADING && sl.isCurrentKeyBass()) keyoffset = -60;

        if (this.samenotePitch(0)) {  // DO 
        	if ((t.issharp() && t.getAlterationsNumber() >=2) || (t.isflat() && t.getAlterationsNumber() >=6))
        			alt = "n"; else alt = ""; 
        			noteY = (60-this.pitch)*35/12+43 + keyoffset;
        	}
        
        else if (this.samenotePitch(1)) { // DO# REb
      	  if (t.isflat()) {
      		  	noteY = (61-this.pitch)*35/12 + 43 + keyoffset;
      	  		if (t.getAlterationsNumber() >=4) alt = ""; else alt = "b"; 
      	  		}
      	  else {
      		  noteY = (61-this.pitch)*35/12 + 38 + keyoffset; 
        	  if (t.getAlterationsNumber() >=2) alt = ""; else alt = "#"; 
        	}          		
        }	
        
        else   if (this.samenotePitch(2)) { // RE
        	if ((t.issharp() && t.getAlterationsNumber() >=4) || (t.isflat() && t.getAlterationsNumber() >=4))
    			alt = "n"; else alt = ""; 
    			noteY = (62-this.pitch)*35/12 + 38 + keyoffset;
        }
                   
        
        else if (this.samenotePitch(3)) { //RE# MIb 
      	  if (t.issharp()) {
      		noteY = (63-this.pitch)*35/12 + 38 + keyoffset;  
      	  	if (t.getAlterationsNumber() >=4) alt = ""; else alt = "#"; 
      	  	}
      	  else {
      		noteY = (63-this.pitch)*35/12 + 33 + keyoffset;  
        	if (t.getAlterationsNumber() >=2) alt = ""; else alt = "b"; }          		
        }	
        
        else   if (this.samenotePitch(4)) {  //MI
        	if ((t.issharp() && t.getAlterationsNumber() >=6) || (t.isflat() && t.getAlterationsNumber() >=2))
    			alt = "n"; else alt = ""; 
        	noteY = (64-this.pitch)*35/12 + 33 + keyoffset; 
        }
				
     
        else   if (this.samenotePitch(5)) { // FA
        	if ((t.issharp() && t.getAlterationsNumber() >=1) || (t.isflat() && t.getAlterationsNumber() >=7))
    			alt = "n"; else alt = ""; 
          	noteY = (65-this.pitch)*35/12 + 28 + keyoffset; 
        }
     
        else if (this.samenotePitch(6)) { //FA# SOLb
        	  if (t.issharp()) {
            		noteY = (66-this.pitch)*35/12 + 28 + keyoffset;  
            	  	if (t.getAlterationsNumber() >=1) alt = ""; else alt = "#"; 
            	  	}
            	  else {
            		noteY = (66-this.pitch)*35/12 + 23 + keyoffset;  
              	if (t.getAlterationsNumber() >=7) alt = ""; else alt = "b"; }          		
              }	
        
        else   if (this.samenotePitch(7)) { //SOL
        	if ((t.issharp() && t.getAlterationsNumber() >=3) || (t.isflat() && t.getAlterationsNumber() >=5))
			alt = "n"; else alt = ""; 
        	noteY = (67-this.pitch)*35/12 + 23 + keyoffset; 
    }
        else if (this.samenotePitch(8)) { //SOL# LAb
        	  if (t.issharp()) {
          		noteY = (68-this.pitch)*35/12 + 23 + keyoffset;  
          	  	if (t.getAlterationsNumber() >=3) alt = ""; else alt = "#"; 
          	  	}
          	  else {
          		noteY = (68-this.pitch)*35/12 + 18 + keyoffset;  
            	if (t.getAlterationsNumber() >=3) alt = ""; else alt = "b"; }          		
            }	
       
        else   if (this.samenotePitch(9)) { 	//LA
        if ((t.issharp() && t.getAlterationsNumber() >=5) || (t.isflat() && t.getAlterationsNumber() >=3))
			alt = "n"; else alt = ""; 
    	noteY = (69-this.pitch)*35/12 + 18 + keyoffset; 
        }
        
        
        else if (this.samenotePitch(10)) { // LA# SIb
        	  if (t.issharp()) {
            		noteY = (70-this.pitch)*35/12 + 18 + keyoffset;  
            	  	if (t.getAlterationsNumber() >=3) alt = ""; else alt = "#"; 
            	  	}
            	  else {
            		noteY = (70-this.pitch)*35/12 + 13 + keyoffset;  
              	if (t.getAlterationsNumber() >=3) alt = ""; else alt = "b"; }          		
              }	
        
        else   if (this.samenotePitch(11)) {  //SI
        	if ((t.issharp() && t.getAlterationsNumber() >=7) || (t.isflat() && t.getAlterationsNumber() >=1))
			alt = "n"; else alt = ""; 
    	noteY = (71-this.pitch)*35/12 + 13 + keyoffset; 
        }
        
   //  	System.out.println("pitch1" + this.pitch + "Notey" + noteY);
              	
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
  		// draw accidental
		if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
  		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
  		if (alt.equals("n")) {
  			String bq = "" + (char)0xBD;
  			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
  			}  
    	//draw note  
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
    		// draw accidental
  			if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
    		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
    		if (alt.equals("n")) {
    			String bq = "" + (char)0xBD;
    			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
    			}  
    		
    		if (this.stemup)     		  g.drawString("h", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13);  
    		else   g.drawString("r", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13 +41);
      }

    }

    else if (this.duration == 3) {
        if (this.silence) { //pause 3 beats only when time signature 3/4
        	  if (Leveltype == RHYTHMREADING) {
        		  g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+20, 12, 7);
        	  }
        	  else g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+14, 12, 7);
        }

        else { // minima
    		// draw accidental
  			if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
    		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
    		if (alt.equals("n")) {
    			String bq = "" + (char)0xBD;
    			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
    			} 
    		
    		if (this.stemup)    	  g.drawString("d", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13);	   
        	else   g.drawString("l", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13 +41);
          }

      }
    
    
    else if (this.duration == 1) {
      if (this.silence) { // pause
    	  g.drawString("Q", this.position, scoreYpos + this.rowNumber*rowsDistance +43);
      }

      else { // semiminima
    	  String sm = "" + (char)0xF6;
    	  int voffset = 53;
    	  if (Leveltype == RHYTHMREADING) { // always stem to up
    		  sm = "" + (char)0xF4;
    		  voffset = 23;
    	  }
    	  else {
    		// draw accidental
    		if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
      		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
      		if (alt.equals("n")) {
      			String bq = "" + (char)0xBD;
      			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
      			}  
      		// stem up
    		if (this.stemup) {    	
	    		sm = "" + (char)0xF4;
	    		voffset = 23;
    		}
    		
    	  }
    	  // draw note
    	  g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
    	
    	
    	
      }
    	  
    }
    
    else if (this.duration == 0.333) {
    	if (Leveltype == RHYTHMREADING ) { //stem upward
    		String sm = "" + (char)0xF4;
	    	//boolean upward  = false;
	    	int voffset = 23;
	 
    	
	    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
	    	g.drawString(sm, this.position, ypos + noteY + voffset);
	    	if (this.tripletValue != 0) {
	    		int lowestYpos = 0;
	    		if (this.tripletValue < 100) lowestYpos = getYpos(sl, this.tripletValue) + ypos;
	    		else lowestYpos = getYpos(sl, this.tripletValue - 100) + ypos;
	    		//g.drawLine(this.position, ypos + noteY + 10, this.position, lowestYpos + 40);
	    		if(this.tripletValue < 100) { // means this is the first note of the triplet. Draw horizontal bar
	        		g.fillRect(this.position+11, lowestYpos - 31, 49, 3);
	        		g.setFont(new Font("Arial", Font.BOLD, 15));
	        		g.drawString("3", this.position + 32, lowestYpos - 33);
	    		}
	    	}
    	}
    	
    	else if (Leveltype == SCOREREADING && this.stemup ) { //stem upward
    		String sm = "" + (char)0xF4;
	    	//boolean upward  = false;
	    	int voffset = 23;
	 
	    	if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
      		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
      		if (alt.equals("n")) {
      			String bq = "" + (char)0xBD;
      			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
      			}
      		
	    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
	    	g.drawString(sm, this.position, ypos + noteY + voffset);
	    	if (this.tripletValue != 0) {
	    		int lowestYpos = 0;
	    		if (this.tripletValue < 100) lowestYpos = getYpos(sl,this.tripletValue) + ypos;
	    		else lowestYpos = getYpos(sl, this.tripletValue - 100) + ypos;
	    	//	System.out.println("newYpos: " + getYpos(sl, this.tripletValue)+ "oldYpos: " + sl.getYpos(this.tripletValue));
	        	
	        	g.drawLine(this.position+11, ypos + noteY - 10, this.position+11, lowestYpos - 30);
	    		if(this.tripletValue < 100) { // means this is the first note of the triplet. Draw horizontal bar
	    			//System.out.println("pitch: "+this.pitch+"triplet: "+this.tripletValue+"position:"+this.position);
	        		g.fillRect(this.position+11, lowestYpos - 31, 49, 3);
	        		g.setFont(new Font("Arial", Font.BOLD, 15));
	        		g.drawString("3", this.position + 32, lowestYpos - 33);
	    		}
	    	}
    	}
    	else {
	    	String sm = "" + (char)0xF6;
	    	//boolean upward  = false;
	    	
	    	if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
      		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
      		if (alt.equals("n")) {
      			String bq = "" + (char)0xBD;
      			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
      			}
      		
	    	int voffset = 53;
	    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
	    	g.drawString(sm, this.position, ypos + noteY + voffset);
	    	if (this.tripletValue != 0) {
	    		int lowestYpos = 0;
	    		if (this.tripletValue < 100) lowestYpos = getYpos(sl, this.tripletValue) + ypos;
	    		else lowestYpos = getYpos(sl, this.tripletValue - 100) + ypos;
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
    	  if (alt.equals("#")) g.drawString("B", this.position -11, scoreYpos + this.rowNumber*rowsDistance + noteY + 13);
    		if (alt.equals("b")) g.drawString("b", this.position -9, scoreYpos + this.rowNumber*rowsDistance + noteY + 15);
    		if (alt.equals("n")) {
    			String bq = "" + (char)0xBD;
    			g.drawString(bq, this.position -8, scoreYpos + this.rowNumber*rowsDistance + noteY + 14);
    			}
    		
       if (this.groupee == 1 || this.groupee == 2) {
    	 String sm = "" + (char)0xF6;
       	 int voffset = 53;
       	 
     	if (Leveltype == RHYTHMREADING) { // always stem to up
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
     	else  if (this.stemup) {
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
     	if (Leveltype == RHYTHMREADING) { // always stem to up
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

    // DRAW LINE UNDER STAFF
    if (!this.silence) {
    	if (noteY < -22)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance - 20, this.position + 15, scoreYpos + this.rowNumber*rowsDistance - 20);
    	if (noteY < -12)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance - 10, this.position + 15, scoreYpos + this.rowNumber*rowsDistance - 10);
    	if (noteY > 38)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance + 50, this.position + 15, scoreYpos + this.rowNumber*rowsDistance + 50);
    	if (noteY > 48)
    		g.drawLine(this.position - 5, scoreYpos + this.rowNumber*rowsDistance + 60, this.position + 15, scoreYpos + this.rowNumber*rowsDistance + 60);
    
    }

    g.setColor(Color.black);
   }
  
  
  


}
