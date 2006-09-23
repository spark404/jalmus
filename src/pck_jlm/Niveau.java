package pck_jlm;

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

public class Niveau {
  boolean ronde;
  boolean blanche;
  boolean noire; // 0 pour la premi�re ligne de port�e ...
  boolean croche;
  boolean silence;

  public Niveau(boolean r, boolean b, boolean n, boolean c, boolean s) {
    this.ronde = r;
    this.blanche = b;
    this.noire = n;
    this.croche = c;
    this.silence = s;
  }

  public void init() {
    this.ronde = true;
    this.blanche = true;
    this.noire = true;
    this.croche = true;
    this.silence = true;

  }

  public void copy(Niveau n) {
    this.ronde = n.ronde;
    this.blanche = n.blanche;
    this.noire = n.noire;
    this.croche = n.croche;
    this.silence = n.silence;

  }

  public boolean getRonde() {
    return this.ronde;
  }

  public boolean getBlanche() {
    return this.blanche;
  }

  public boolean getNoire() {
    return this.noire;
  }

  public boolean getCroche() {
    return this.croche;
  }

  public boolean getSilence() {
    return this.silence;
  }

  public void majniveau(boolean r, boolean b, boolean n, boolean c, boolean s) {
    this.ronde = r;
    this.blanche = b;
    this.noire = n;
    this.croche = c;
    this.silence = s;
  }

  public void paint(Graphics g) {
    /*  g.setColor(c);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      String niv = "";
      if (this.ronde) niv = niv + ronde;
      if (this.blanche) niv = niv + blanche;
      g.drawString(niv, 125, 461);*/

  }
}
