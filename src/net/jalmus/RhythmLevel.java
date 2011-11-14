package net.jalmus;

import java.awt.Graphics;

/**
 * <p>Title: Java Lecture Musicale</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author non attribuable
 * @version 1.0
 */

public class RhythmLevel implements Level {
	int Id;
	String message;
  boolean whole;
  boolean half;
  boolean quarter;
  boolean eighth;
  boolean silence;
  boolean triplet;
  int timeSignNumerator;
  int timeSignDenominator;
  int timeDivision; // ratio between time signature numerator and denominator 
  int speed; // time sleep of thread = speed of the note
  

  public RhythmLevel() {
	    this.Id = 0;
	    this.message = "";
	    this.whole = true;
	    this.half = true;
	    this.quarter = true;
	    this.eighth = true;
	    this.silence = true;
	    this.triplet = false;
	    
	    this.timeSignNumerator = 4;
	    this.timeSignDenominator = 4;
	    this.timeDivision = 1;     
	    this.speed = 28;
    
  }

  public void init() {
	 this.Id = 0;
	    this.message = "";
    this.whole = true;
    this.half = true;
    this.quarter = true;
    this.eighth = true;
    this.silence = true;
    this.triplet = false;
    
    this.timeSignNumerator = 4;
    this.timeSignDenominator = 4;
    this.timeDivision = 1;     
    this.speed = 28;
  }

  public void copy(RhythmLevel n) {
	this.Id = n.Id;
    this.message =  n.message;
    this.whole = n.whole;
    this.half = n.half;
    this.quarter = n.quarter;
    this.eighth = n.eighth;
    this.silence = n.silence;
    this.triplet = n.triplet;
    
    this.timeSignNumerator = n.timeSignNumerator;
    this.timeSignDenominator = n.timeSignNumerator;
    
    this.timeDivision = n.timeDivision ; 
  }

  /********************************/

  public void setId(int i) {
    this.Id = i;
  }

  public int getId() {
    return this.Id;
}

  /********************************/
  
  /*******************************************/
  public void setMessage(String s) {
    this.message = s;
  }

  public String getMessage() {
    return this.message;
}

public boolean isMessageEmpty(){
  return this.message.equals("");
}

/*******************************************/

  public boolean getWholeNote() {
    return this.whole;
  }

  public boolean getHalfNote() {
    return this.half;
  }

  public boolean getQuarterNote() {
    return this.quarter;
  }

  public boolean getEighthNote() {
    return this.eighth;
  }

  public boolean getSilence() {
    return this.silence;
  }
  
  public boolean getTriplet() {
	    return this.triplet;
  }  

  /********************************/
  
  public int getTimeSignNumerator() {
	    return  this.timeSignNumerator ;
}
  
  public int getTimeSignDenominator() {
	    return  this.timeSignDenominator ;
}
  
  public void setTimeSignNumerator(int i) {
	    this.timeSignNumerator = i ;
}

public void setTimeSignDenominator(int i) {
	this.timeSignDenominator = i ;
}
  
public int getTimeDivision() {
    return  this.timeDivision ;
}

public void setTimeDivision(int i) {
	this.timeDivision = i ;
}

/********************************/

  public void adjustLevel(boolean r, boolean b, boolean n, boolean c, boolean s, boolean t) {
    this.whole = r;
    this.half = b;
    this.quarter = n;
    this.eighth = c;
    this.silence = s;
    this.triplet = t;
  }

  public void paint(Graphics g) {
    /*  g.setColor(c);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      String niv = "";
      if (this.whole) niv = niv + whole;
      if (this.half) niv = niv + half;
      g.drawString(niv, 125, 461);*/

  }
}
