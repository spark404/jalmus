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
  boolean whole;
  boolean half;
  boolean quarter;
  boolean eighth;
  boolean silence;
  boolean triplet;

  public RhythmLevel(boolean r, boolean b, boolean n, boolean c, boolean s, boolean t) {
    this.whole = r;
    this.half = b;
    this.quarter = n;
    this.eighth = c;
    this.silence = s;
    this.triplet = t;
  }

  public void init() {
    this.whole = true;
    this.half = true;
    this.quarter = true;
    this.eighth = true;
    this.silence = true;
    this.triplet = false;
  }

  public void copy(RhythmLevel n) {
    this.whole = n.whole;
    this.half = n.half;
    this.quarter = n.quarter;
    this.eighth = n.eighth;
    this.silence = n.silence;
    this.triplet = n.triplet;
  }

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
