package net.jalmus;

import java.awt.Color;
import java.awt.Graphics;


/**
 * <p>Title: Jalmus</p>
 *
 * <p>Description: Free software for sight reading</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author RICHARD Christophe
 * @version 1.0
 */
public class Score {
  int nbtrue;
  int nbfalse;
  int points;
  int win; /* 0 no result 1 win 2 lost */
  static int maxpoints = 500;

  public Score() {
    this.nbtrue = 0;
    this.nbfalse  = 0;
    this.points = 100;
    win = 0;
  }

  public int getNbtrue(){
    return this.nbtrue;
  }

  public void setNbtrue(int i) {
    this.nbtrue = i;
  }

  public void addNbtrue(int i) {
    this.nbtrue = this.nbtrue + i;
  }

  public int getNbfalse(){
    return this.nbfalse;
  }

  public void setNbfalse(int i) {
    this.nbfalse = i;
  }

  public void addNbfalse(int i) {
    this.nbfalse = this.nbfalse + i;
  }

  public void setPoints(int i) {
    this.points = i;
  }

  public void setWin(){
    this.win = 1;
  }

  public void setLost(){
    this.win = 2;

  }
  public boolean isWin() {
    return this.win == 1;
  }

  public boolean isLost() {
  return this.win == 2;
}

public boolean isUnknown() {
  return this.win == 0;
}


  public void initScore(){
    this.nbtrue = 0;
    this.nbfalse = 0;
    this.points = 100;
    win = 0;

  }

  /**
     * To add or substract points
     *
     * @param i for the number of points to add (if i>0) or substract (if i<0)
     *
     * If new number of points reach the max points or 0 update the win parameter
   */

  public void addPoints(int i){
    if (i > 0) {
      if (this.points + i < maxpoints) {
        this.points = this.points + i;
      }
      else {
        this.points = maxpoints;
        this.setWin();

      }
    }
    else { // i < 0 substract points
      if (this.points + i > 0) {
         this.points = this.points + i;
       }
       else {
         this.points = 0;
         this.setLost();

       }
     }


  }


  public void paint(Graphics g, int width) {

	int xpos = (width - 251) / 2;
    g.setColor(Color.black);
    g.draw3DRect( xpos, 420, 251, 20, true);
    for (int tmp = 0; tmp < this.points; tmp = tmp + 10) {
      if (tmp < 100)
        g.setColor(new Color(60 + (tmp + 10) / 2, 26, 26));
      else
        g.setColor(new Color(110, 26 + (tmp - 90) / 2, 26));
      g.fillRect(xpos + 1 + tmp / 2, 421, 5, 19);
    }

  }
  }
