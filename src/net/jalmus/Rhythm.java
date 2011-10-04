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
  int valeur; // 1 round, 2 white, 4 black, 8 cross, 16 double cross
  int position;
  int pitch;
  int nportee; // 0 pour la premi�re ligne de port�e ...
  boolean pointee;
  boolean silence;

  int groupee; //0 non regroup�e 1 debut de regroupement 2 fin du regroupement

  public Rhythm(int val, int pos, int p, int np, boolean pt, boolean sl, int bm) {
    this.valeur = val;
    this.position = pos;
    this.pitch = p;
    this.nportee = np;
    this.pointee = pt;
    this.silence = sl;
    this.groupee = bm;
  }

  public void init() {
    this.valeur = 0;
    this.position = 0;
    this.nportee = 0;
    this.pitch = 71; //SI for rhythm game
        this.pointee = false;
        this.silence = false;
        this.groupee = 0;
  }

  public void copy(Rhythm r) {
    this.valeur = r.valeur;
    this.position = r.position;
    this.pitch = r.pitch;
    this.nportee = r.nportee;
    this.silence = r.silence;
    this.pointee = r.pointee;
    this.groupee = r.groupee;
  }

  public void setValeur(int i) {
    this.valeur = i;
  }

  public int getValeur() {
    return this.valeur;
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
      return this.valeur == 0;
 }


 public void paint(Graphics g, Font f, Tonality ton, int position, boolean courant, int dportee, Component l) {

    int height = 14;
    @SuppressWarnings("unused")
	int alteration = 0; //0 no alteration 1 sharp 2 flat

    if (this.pitch == 60) {height = 43; alteration = 0;} //DO
    else if  (this.pitch == 61) {height = 43; alteration = 1;} //DO# REb
    else if  (this.pitch == 62) {height = 38; alteration = 0;} //RE
    
    else if  (this.pitch == 63  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = 33; alteration = 2;} // MIb
    else if  (this.pitch == 63) {height = 38; alteration = 1;} //RE# 
    else if  (this.pitch == 64 && ton.isflat() && ton.getAlterationsNumber() >=  7) {height = 28; alteration = 2;} // fab
    else if  (this.pitch == 64) {height = 33; alteration = 0;} //MI
    else if  (this.pitch == 65 && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = 33; alteration = 1;} //MI#
    else if  (this.pitch == 65) {height = 28; alteration = 0;} //FA
    else if  (this.pitch == 66 && ton.issharp() && ton.getAlterationsNumber() >=  1) {height = 28; alteration = 1;} //FA# 
    else if  (this.pitch == 66 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = 23; alteration = 2;} // SOLb
    else if  (this.pitch == 67) {height = 23; alteration = 0;} //SOL
    else if  (this.pitch == 68 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = 23; alteration = 1;} //SOL# 
    else if  (this.pitch == 68 && ton.isflat() && ton.getAlterationsNumber() >=  3) {height = 18; alteration = 2;} // LAb
    else if  (this.pitch == 69) {height = 18; alteration = 0;} //LA
    else if  (this.pitch == 70 && ton.issharp() && ton.getAlterationsNumber() >=  5) {height = 18; alteration = 1;} //LA# 
    else if  (this.pitch == 70 && ton.isflat() && ton.getAlterationsNumber() >=  1) {height = 13; alteration = 2;} // SIb
    
    else if  (this.pitch == 71 && ton.isflat() && ton.getAlterationsNumber() >=  6) {height = 8; alteration = 0;} //dob
    else if  (this.pitch == 71) {height = 13; alteration = 0;} //SI
    else if  (this.pitch == 72 && ton.issharp() && ton.getAlterationsNumber() >=  7) {height = 13; alteration = 1;} //si#
    else if  (this.pitch == 72) {height = 8; alteration = 0;} //do
    else if  (this.pitch == 73 && ton.issharp() && ton.getAlterationsNumber() >=  2) {height = 8; alteration = 1;} //do#
    else if  (this.pitch == 73 && ton.isflat() && ton.getAlterationsNumber() >=  4) {height = 3; alteration = 2;} //reb
    else if  (this.pitch == 74) {height = 3; alteration = 0;} //re
    else if  (this.pitch == 75 && ton.issharp() && ton.getAlterationsNumber() >=  4) {height = 3; alteration = 1;} //re#
    else if  (this.pitch == 75  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = -2; alteration = 2;} // MIb
    else if  (this.pitch == 76) {height = -2; alteration = 0;} //mi
    else if  (this.pitch == 77  && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = -2; alteration = 1;} //mi#
    else if  (this.pitch == 77) {height = -7; alteration = 0;} //fa
  
    if (this.pitch == 27) {height = 43; alteration = 0;} //
    else if  (this.pitch == 28) {height = 43; alteration = 1;} //
    
    else if  (this.pitch == 30 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = 33; alteration = 2;} // SOLb
    else if  (this.pitch == 30) {height = 38; alteration = 0;} // fa
    else if  (this.pitch == 31) {height = 33; alteration = 1;} //SOL
    else if  (this.pitch == 32 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = 33; alteration = 1;} //SOL# 
    else if  (this.pitch == 32 && ton.isflat() && ton.getAlterationsNumber() >=  3) {height = 28; alteration = 2;} // LAb     
    else if  (this.pitch == 33) {height = 28; alteration = 0;} //LA
    else if  (this.pitch == 34 && ton.issharp() && ton.getAlterationsNumber() >=  5) {height = 28; alteration = 1;} //LA# 
    else if  (this.pitch == 34 && ton.isflat() && ton.getAlterationsNumber() >=  1) {height = 23; alteration = 2;} // SIb               
    else if  (this.pitch == 35 && ton.isflat() && ton.getAlterationsNumber() >=  6) {height = 18; alteration = 2;} //dob 
    else if  (this.pitch == 35) {height = 23; alteration = 0;} //SI
    else if  (this.pitch == 36 && ton.issharp() && ton.getAlterationsNumber() >=  7) {height = 23; alteration = 1;} //si#        
    else if  (this.pitch == 36) {height = 18; alteration = 1;} //do
    else if  (this.pitch == 37 && ton.issharp() && ton.getAlterationsNumber() >=  2) {height = 18; alteration = 1;} //do#
    else if  (this.pitch == 37 && ton.isflat() && ton.getAlterationsNumber() >=  4) {height = 13; alteration = 2;} //reb 
    else if  (this.pitch == 38) {height = 13; alteration = 0;} //re
    else if  (this.pitch == 39 && ton.issharp() && ton.getAlterationsNumber() >=  4) {height = 13; alteration = 1;} //re#
    else if  (this.pitch == 39  && ton.isflat() && ton.getAlterationsNumber() >=  2) {height = 8; alteration = 2;} // MIb
    else if  (this.pitch == 40 && ton.isflat() && ton.getAlterationsNumber() >=  7) {height = 3; alteration = 2;} //fab
    else if  (this.pitch == 40) {height = 8; alteration = 0;} //mi
    else if  (this.pitch == 41  && ton.issharp() && ton.getAlterationsNumber() >=  6) {height = 8; alteration = 1;} //mi#  
    else if  (this.pitch == 41) {height = 3; alteration = 0;} //fa
    else if  (this.pitch == 42 && ton.issharp() && ton.getAlterationsNumber() >=  1) {height = 3; alteration = 1;} //FA# 
    else if  (this.pitch == 42 && ton.isflat() && ton.getAlterationsNumber() >=  5) {height = -2; alteration = 2;} // SOLb 
    else if  (this.pitch == 43) {height = -2; alteration = 0;} //sol
    else if  (this.pitch == 44 && ton.issharp() && ton.getAlterationsNumber() >=  3) {height = -2; alteration = 1;} // SOL#         
   
    //  g.setColor(couleur);
    g.setFont(f.deriveFont(57f));
    if (courant)
    	g.setColor(Color.red);
    else
    	g.setColor(Color.black);

    if (this.valeur == 1) {
      if (this.silence) {
    	g.fillRect(this.position, dportee+ this.nportee*100+14, 12, 7);
      }

      else { // semibreve
    	  g.setFont(f.deriveFont(50f));
    	g.drawString("w", this.position, dportee + this.nportee*100 + height +13);
      }

    }
    else if (this.valeur == 2) {
      if (this.silence) {
      	g.fillRect(this.position, dportee+ this.nportee*100+10, 12, 7);
      }

      else { // minima
    	g.drawString("h", this.position, dportee + this.nportee*100 + height +13);
      }

    }

    else if (this.valeur == 4) {
      if (this.silence) { // pause
    	  g.drawString("Q", this.position, dportee + this.nportee*100 +43);
      }

      else { // semiminima
    	String sm = "" + (char)0xF6;
    	int voffset = 53;
    	if (height > 18) {
    		sm = "" + (char)0xF4;
    		voffset = 23;
    	}
    	g.drawString(sm, this.position, dportee + this.nportee*100 + height + voffset);
      }
    }

    else if (this.valeur == 8) {
      if (this.silence) { // pause
    	  g.setFont(f.deriveFont(48f));
	      g.drawString("E", this.position, dportee + this.nportee*100 + 40);
      }
      else {

       if (this.groupee == 1 || this.groupee == 2) {
    	 String sm = "" + (char)0xF6;
       	 int voffset = 53;
       	 if (height > 18) {
       		sm = "" + (char)0xF4;
       		voffset = 23;
       	 }
       	 g.drawString(sm, this.position, dportee + this.nportee*100 + height + voffset);
       	 if (this.groupee == 1) {
           g.setColor(Color.BLACK);
           g.fillRect(this.position, dportee+ this.nportee*100+height, 38, 3);
       	 }
       }

       else {
      	 String sm = "" + (char)0xCA;
       	 int voffset = 43;
       	 if (height > 18) {
       		sm = "" + (char)0xC8;
       		voffset = 13;
       	 }
       	 g.drawString(sm, this.position, dportee + this.nportee*100 + height + voffset);
       }

      }                
    }

    g.setColor(Color.black);
   }

}
