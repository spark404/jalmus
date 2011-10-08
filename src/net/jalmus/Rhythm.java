package net.jalmus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

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

  int groupee; //0 non regroup�e 1 debut de regroupement 2 fin du regroupement

  public Rhythm(double val, int pos, int p, int np, boolean pt, boolean sl, int bm) {
    this.duration = val;
    this.position = pos;
    this.pitch = p;
    this.rowNumber = np;
    this.pointee = pt;
    this.silence = sl;
    this.groupee = bm;
  }

  public void init() {
    this.duration = 0;
    this.position = 0;
    this.rowNumber = 0;
    this.pitch = 71; //SI for rhythm game
    this.pointee = false;
    this.silence = false;
    this.groupee = 0;
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

  private int getYposFromPitch(Tonality ton, int pitch) {
	  int alteration = 0; //0 no alteration 1 sharp 2 flat
	  int height = 14;

	  if (pitch == 60) {height = 43; alteration = 0;} //DO
	  else if  (pitch == 61) {height = 43; alteration = 1;} //DO# REb
	  else if  (pitch == 62) {height = 38; alteration = 0;} //RE
	    
	  else if  (pitch == 63  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = 33; alteration = 2;} // MIb
	  else if  (pitch == 63) {height = 38; alteration = 1;} //RE# 
	  else if  (pitch == 64 && ton.isflat() && ton.getAlterationsNumber() >=  7) {height = 28; alteration = 2;} // fab
	  else if  (pitch == 64) {height = 33; alteration = 0;} //MI
	  else if  (pitch == 65 && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = 33; alteration = 1;} //MI#
	  else if  (pitch == 65) {height = 28; alteration = 0;} //FA
	  else if  (pitch == 66 && ton.issharp() && ton.getAlterationsNumber() >=  1) {height = 28; alteration = 1;} //FA# 
	  else if  (pitch == 66 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = 23; alteration = 2;} // SOLb
	  else if  (pitch == 67) {height = 23; alteration = 0;} //SOL
	  else if  (pitch == 68 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = 23; alteration = 1;} //SOL# 
	  else if  (pitch == 68 && ton.isflat() && ton.getAlterationsNumber() >=  3) {height = 18; alteration = 2;} // LAb
	  else if  (pitch == 69) {height = 18; alteration = 0;} //LA
	  else if  (pitch == 70 && ton.issharp() && ton.getAlterationsNumber() >=  5) {height = 18; alteration = 1;} //LA# 
	  else if  (pitch == 70 && ton.isflat() && ton.getAlterationsNumber() >=  1) {height = 13; alteration = 2;} // SIb
	    
	  else if  (pitch == 71 && ton.isflat() && ton.getAlterationsNumber() >=  6) {height = 8; alteration = 0;} //dob
	  else if  (pitch == 71) {height = 13; alteration = 0;} //SI
	  else if  (pitch == 72 && ton.issharp() && ton.getAlterationsNumber() >=  7) {height = 13; alteration = 1;} //si#
	  else if  (pitch == 72) {height = 8; alteration = 0;} //do
	  else if  (pitch == 73 && ton.issharp() && ton.getAlterationsNumber() >=  2) {height = 8; alteration = 1;} //do#
	  else if  (pitch == 73 && ton.isflat() && ton.getAlterationsNumber() >=  4) {height = 3; alteration = 2;} //reb
	  else if  (pitch == 74) {height = 3; alteration = 0;} //re
	  else if  (pitch == 75 && ton.issharp() && ton.getAlterationsNumber() >=  4) {height = 3; alteration = 1;} //re#
	  else if  (pitch == 75  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = -2; alteration = 2;} // MIb
	  else if  (pitch == 76) {height = -2; alteration = 0;} //mi
	  else if  (pitch == 77  && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = -2; alteration = 1;} //mi#
	  else if  (pitch == 77) {height = -7; alteration = 0;} //fa
	  
	  if (pitch == 27) {height = 43; alteration = 0;} //
	  else if  (pitch == 28) {height = 43; alteration = 1;} //
	    
	  else if  (pitch == 30 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = 33; alteration = 2;} // SOLb
	  else if  (pitch == 30) {height = 38; alteration = 0;} // fa
	  else if  (pitch == 31) {height = 33; alteration = 1;} //SOL
	  else if  (pitch == 32 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = 33; alteration = 1;} //SOL# 
	  else if  (pitch == 32 && ton.isflat() && ton.getAlterationsNumber() >=  3) {height = 28; alteration = 2;} // LAb     
	  else if  (pitch == 33) {height = 28; alteration = 0;} //LA
	  else if  (pitch == 34 && ton.issharp() && ton.getAlterationsNumber() >=  5) {height = 28; alteration = 1;} //LA# 
	  else if  (pitch == 34 && ton.isflat() && ton.getAlterationsNumber() >=  1) {height = 23; alteration = 2;} // SIb               
	  else if  (pitch == 35 && ton.isflat() && ton.getAlterationsNumber() >=  6) {height = 18; alteration = 2;} //dob 
	  else if  (pitch == 35) {height = 23; alteration = 0;} //SI
	  else if  (pitch == 36 && ton.issharp() && ton.getAlterationsNumber() >=  7) {height = 23; alteration = 1;} //si#        
	  else if  (pitch == 36) {height = 18; alteration = 1;} //do
	  else if  (pitch == 37 && ton.issharp() && ton.getAlterationsNumber() >=  2) {height = 18; alteration = 1;} //do#
	  else if  (pitch == 37 && ton.isflat() && ton.getAlterationsNumber() >=  4) {height = 13; alteration = 2;} //reb 
	  else if  (pitch == 38) {height = 13; alteration = 0;} //re
	  else if  (pitch == 39 && ton.issharp() && ton.getAlterationsNumber() >=  4) {height = 13; alteration = 1;} //re#
	  else if  (pitch == 39  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = 8; alteration = 2;} // MIb
	  else if  (pitch == 40 && ton.isflat() && ton.getAlterationsNumber() >=  7) {height = 3; alteration = 2;} //fab
	  else if  (pitch == 40) {height = 8; alteration = 0;} //mi
	  else if  (pitch == 41  && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = 8; alteration = 1;} //mi#  
	  else if  (pitch == 41) {height = 3; alteration = 0;} //fa
	  else if  (pitch == 42 && ton.issharp() && ton.getAlterationsNumber() >=  1) {height = 3; alteration = 1;} //FA# 
	  else if  (pitch == 42 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = -2; alteration = 2;} // SOLb 
	  else if  (pitch == 43) {height = -2; alteration = 0;} //sol
	  else if  (pitch == 44 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = -2; alteration = 1;} // SOL#
	  
	  return height;
  }
  
  public void paint(Graphics g, Font f, Tonality ton, int position, int rowsDistance, boolean courant, int scoreYpos, Component l) {

    int noteY = getYposFromPitch(ton, this.pitch);
    //@SuppressWarnings("unused")

    //  g.setColor(couleur);
    g.setFont(f.deriveFont(57f));
    if (courant)
    	g.setColor(Color.red);
    else
    	g.setColor(Color.black);

    if (this.duration == 4) {
      if (this.silence) {
    	g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+14, 12, 7);
      }

      else { // semibreve
    	  g.setFont(f.deriveFont(50f));
    	g.drawString("w", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13);
      }

    }
    else if (this.duration == 2) {
      if (this.silence) {
      	g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+10, 12, 7);
      }

      else { // minima
    	g.drawString("h", this.position, scoreYpos + this.rowNumber*rowsDistance + noteY +13);
      }

    }

    else if (this.duration == 1) {
      if (this.silence) { // pause
    	  g.drawString("Q", this.position, scoreYpos + this.rowNumber*rowsDistance +43);
      }

      else { // semiminima
    	String sm = "" + (char)0xF6;
    	int voffset = 53;
    	if (noteY > 18) {
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
    	g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
      }
    }
    
    else if (this.duration == 0.333) {
    	String sm = "" + (char)0xF6;
    	//boolean upward  = false;
    	int voffset = 53;
    	int ypos = scoreYpos + this.rowNumber*rowsDistance;
    	g.drawString(sm, this.position, ypos + noteY + voffset);
    	if (this.tripletValue != 0) {
    		int lowestYpos = 0;
    		if (this.tripletValue < 100) lowestYpos = getYposFromPitch(ton, this.tripletValue) + ypos;
    		else lowestYpos = getYposFromPitch(ton, this.tripletValue - 100) + ypos;
        	g.drawLine(this.position, ypos + noteY + 10, this.position, lowestYpos + 40);
    		if(this.tripletValue < 100) { // means this is the first note of the triplet. Draw horizontal bar
        		g.fillRect(this.position, lowestYpos + 37, 49, 5);
        		g.setFont(new Font("Arial", Font.BOLD, 15));
        		g.drawString("3", this.position + 20, lowestYpos + 54);
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
       	 if (noteY > 18) {
       		sm = "" + (char)0xF4;
       		voffset = 23;
       	 }
       	 g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
       	 if (this.groupee == 1) {
           g.setColor(Color.BLACK);
           g.fillRect(this.position, scoreYpos+ this.rowNumber*rowsDistance+noteY, 38, 3);
       	 }
       }

       else {
      	 String sm = "" + (char)0xCA;
       	 int voffset = 43;
       	 if (noteY > 18) {
       		sm = "" + (char)0xC8;
       		voffset = 13;
       	 }
       	 g.drawString(sm, this.position, scoreYpos + this.rowNumber*rowsDistance + noteY + voffset);
       }

      }                
    }

    g.setColor(Color.black);
   }

}
