package net.jalmus;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ResourceBundle;

public class Tonality {
  int Nbalt;
  String Alteration;

  public Tonality(int nb, String alt) {
    this.Alteration = alt;
    this.Nbalt = nb;
  }

  public void init(int i, String s) {
    this.Alteration = s;
    this.Nbalt = i;
  }

  public void printtest(){
    System.out.println("Alteration : "+this.Alteration+" Nb alt : "+this.Nbalt);
  }

  public void copy(Tonality t) {
    this.Alteration = t.Alteration;
    this.Nbalt = t.Nbalt;
  }

  public void setNbalt(int nb) {
    this.Nbalt = nb;
  }

  public int getNbalt() {
    return this.Nbalt;
  }

  public String getAlteration() {
    return this.Alteration;
  }

  public void setAlteration(String s) {
    this.Alteration = s;
  }

  //*****ALTERATION

   public void affichealt(Graphics g, int x, int y, Image ialt, Component j) {
     g.drawImage(ialt, x+2 , y, j); //diese y-8 bemol y-14
   }

  //  else if (s == "n")  g.drawImage(becarre, x+1, y-8, 16, 24,j);




  public void paint(NoteLevel nrlevel, Graphics g, int marge, int dportee, net.jalmus.Tabimage tab, Component j, ResourceBundle bundle) {
    int i = 0;
    int decalagecle = 0;
    boolean ok = false;
    Image diese = tab.Getimage(17);
    Image bemol = tab.Getimage(18);

    if (nrlevel.isCurrentclefTreble() | nrlevel.isCurrentclefBass())
      if (nrlevel.isCurrentclefBass())
        decalagecle = 10;
    affichenom(g, marge, bundle);

    if (this.Alteration.equalsIgnoreCase("#")) {
      if (this.Nbalt >= 1) // FA#
        affichealt(g, marge + 25, dportee - 15 + decalagecle, diese, j); //-9
      if (this.Nbalt >= 2) // DO#
        affichealt(g, marge + 25 + 10, dportee - 1 + decalagecle, diese, j);
      if (this.Nbalt >= 3) // SOL#
        affichealt(g, marge + 25 + 20, dportee - 19 + decalagecle, diese, j);
      if (this.Nbalt >= 4) // RE#
        affichealt(g, marge + 25 + 30, dportee - 5 + decalagecle, diese, j);
      if (this.Nbalt >= 5) // LA#
        affichealt(g, marge + 25 + 40, dportee + 8 + decalagecle, diese, j);
      if (this.Nbalt >= 6) // MI#
        affichealt(g, marge + 25 + 50, dportee - 9 + decalagecle, diese, j);
      if (this.Nbalt >= 7) // SI#
        affichealt(g, marge + 25 + 60, dportee + 5 + decalagecle, diese, j);
    }

    if (this.Alteration.equalsIgnoreCase("b")) {
      if (this.Nbalt >= 1) // SIb
        affichealt(g, marge + 26, dportee + 4 + decalagecle, bemol, j);
      if (this.Nbalt >= 2) // MIb
        affichealt(g, marge + 26 + 9, dportee - 12 + decalagecle, bemol, j);
      if (this.Nbalt >= 3) // LAb
        affichealt(g, marge + 26 + 18, dportee + 8 + decalagecle, bemol, j);
      if (this.Nbalt >= 4) // REb
        affichealt(g, marge + 26 + 27, dportee - 6 + decalagecle, bemol, j);
      if (this.Nbalt >= 5) // SOLb
        affichealt(g, marge + 26 + 36, dportee + 14 + decalagecle, bemol, j);
      if (this.Nbalt >= 6) // DOb
        affichealt(g, marge + 26 + 45, dportee - 2 + decalagecle, bemol, j);
      if (this.Nbalt >= 7) // FAb
        affichealt(g, marge + 26 + 54, dportee + 18 + decalagecle, bemol, j);
    }

    if (nrlevel.isCurrentclefBoth()) {
      decalagecle = 100;

      if (this.Alteration.equalsIgnoreCase("#")) {
        if (this.Nbalt >= 1) // FA#
          affichealt(g, marge + 25, dportee - 15 + decalagecle, diese, j);
        if (this.Nbalt >= 2) // DO#
          affichealt(g, marge + 25 + 10, dportee - 1 + decalagecle, diese, j);
        if (this.Nbalt >= 3) // SOL#
          affichealt(g, marge + 25 + 20, dportee - 19 + decalagecle, diese, j);
        if (this.Nbalt >= 4) // RE#
          affichealt(g, marge + 25 + 30, dportee - 5 + decalagecle, diese, j);
        if (this.Nbalt >= 5) // LA#
          affichealt(g, marge + 25 + 40, dportee + 8 + decalagecle, diese, j);
        if (this.Nbalt >= 6) // MI#
          affichealt(g, marge + 25 + 50, dportee - 9 + decalagecle, diese, j);
        if (this.Nbalt >= 7) // SI#
          affichealt(g, marge + 25 + 60, dportee + 5 + decalagecle, diese, j);
      }

      if (this.Alteration.equalsIgnoreCase("b")) {
        if (this.Nbalt >= 1) // SIb
          affichealt(g, marge + 26, dportee + 4 + decalagecle, bemol, j);
        if (this.Nbalt >= 2) // MIb
          affichealt(g, marge + 26 + 9, dportee - 12 + decalagecle, bemol, j);
        if (this.Nbalt >= 3) // LAb
          affichealt(g, marge + 26 + 18, dportee + 8 + decalagecle, bemol, j);
        if (this.Nbalt >= 4) // REb
          affichealt(g, marge + 26 + 27, dportee - 6 + decalagecle, bemol, j);
        if (this.Nbalt >= 5) // SOLb
          affichealt(g, marge + 26 + 36, dportee + 14 + decalagecle, bemol, j);
        if (this.Nbalt >= 6) // DOb
          affichealt(g, marge + 26 + 45, dportee - 2 + decalagecle, bemol, j);
        if (this.Nbalt >= 7) // FAb
          affichealt(g, marge + 26 + 54, dportee + 18 + decalagecle, bemol, j);
      }
    }

  }

  public void affichenom(Graphics g, int marge, ResourceBundle bundle) {
    String nom = "";

    String DO = bundle.getString("_do");
    String RE = bundle.getString("_re");
    String MI = bundle.getString("_mi");
    String FA = bundle.getString("_fa");
    String SOL = bundle.getString("_sol");
    String LA = bundle.getString("_la");
    String SI = bundle.getString("_si");

    g.setColor(Color.green);
    g.setFont(new Font("Arial", Font.BOLD, 17));

    if (this.Nbalt == 0)
      nom = DO + " Maj | " + LA + " min";
    else if (this.Nbalt == 1)
      if (this.Alteration.equals("#"))
        nom = SOL + " Maj | " + MI + " min";
      else
        nom = FA + " Maj | " + RE + " min";
    if (this.Nbalt == 2)
      if (this.Alteration.equals("#"))
        nom = RE + " Maj | " + SI + " min";
      else
        nom = SI + "b Maj | " + SOL + " min";
    if (this.Nbalt == 3)
      if (this.Alteration.equals("#"))
        nom = LA + " Maj | " + FA + "# min";
      else
        nom = MI + "b Maj | " + DO + " min";
    if (this.Nbalt == 4)
      if (this.Alteration.equals("#"))
        nom = MI + " Maj | " + DO + "# min";
      else
        nom = LA + "b Maj | " + FA + " min";
    if (this.Nbalt == 5)
      if (this.Alteration.equals("#"))
        nom = SI + " Maj | " + SOL + "# min";
      else
        nom = RE + "b Maj | " + SI + "b min";
    if (this.Nbalt == 6)
      if (this.Alteration.equals("#"))
        nom = FA + "# Maj | " + RE + "# min";
      else
        nom = SOL + "b Maj | " + MI + "b min";
    if (this.Nbalt == 7)
      if (this.Alteration.equals("#"))
        nom = DO + "# Maj |" + LA + "# min";
      else
        nom = DO + "b Maj |" + LA + "b min";

    g.setColor(Color.gray);
    g.setFont(new Font("Arial", Font.ITALIC, 11));

    g.drawString(nom, marge, 90);

  }

}

