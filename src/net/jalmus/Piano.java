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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

public class Piano {

  Vector blackKeys = new Vector();
  Vector keys = new Vector();
  Vector whiteKeys = new Vector();
  Color jfcBlue = new Color(204, 204, 255);
  Color red = new Color(242, 179, 112);
  Color pink = new Color(255, 175, 175);

  net.jalmus.Key prevKey;
  int positionbase1 = 0; // 0 si pas de note de base � afficher sinon position sur le clavier
  int positionbase2 = 0; // 0 pour deuxième clé
  final int kw = 16, kh = 80;
  final int haut = 315;
  int marge = 40;
  int length = 73;

  public Piano(int l, int m) {

    int octavanumber;
    int transpose;

    this.marge = m;
    this.length = l;

    if (length == 61) {
      octavanumber = 5;
      transpose = 36;
    }
    else {
      octavanumber = 6;
      transpose = 24;
    }

    keys.removeAll(blackKeys);
    keys.removeAll(whiteKeys);

    int whiteIDs[] = {
        0, 2, 4, 5, 7, 9, 11};

    for (int i = 0, x = 3; i < octavanumber; i++) {
      for (int j = 0; j < 7; j++, x += kw) {
        int keyNum = i * 12 + whiteIDs[j] + transpose;
        whiteKeys.add(new net.jalmus.Key(x + marge, haut, kw, kh, keyNum));
      }
    }

    whiteKeys.add(new net.jalmus.Key(7 * octavanumber * kw + marge + 3, haut, kw, kh,
                          octavanumber * 12 + transpose));

    for (int i = 0, x = 3; i < octavanumber; i++, x += kw) {
      int keyNum = i * 12 + transpose;
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 1));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 3));
      x += kw;
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 6));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 8));
      blackKeys.add(new net.jalmus.Key( (x += kw) - 4 + marge, haut, kw / 2, kh / 2 + 11,
                            keyNum + 10));
    }
    keys.addAll(blackKeys);
    keys.addAll(whiteKeys);

  }

  public boolean is73keys() {
    return this.length == 73;
  }

  public boolean is61keys() {
    return this.length == 61;
  }

  public void Setpositionbase1(int posnotebase) {

    this.positionbase1 = posnotebase;
  }

  public void Setpositionbase2(int posnotebase) {
    this.positionbase2 = posnotebase;
  }

  public void Setprevkey(net.jalmus.Key k) {
    this.prevKey = k;
  }

  public net.jalmus.Key Getprevkey() {
    return this.prevKey;
  }

  public int Getpositionbase1() {
    return this.positionbase1;
  }

  public int Getpositionbase2() {
    return this.positionbase2;
  }

  /**
     * To return Key of the Piano where the mouse aim
     * @return Key of the piano where the mouse aim
     * @param Point of the mouse pointer
   */
  public net.jalmus.Key getKey(Point point) {
    Point p = new Point();

    p.setLocation(point.getX() - 4, point.getY() - 100);

    for (int i = 0; i < keys.size(); i++) {
      if ( ( (net.jalmus.Key) keys.get(i)).contains(p)) {
        return (net.jalmus.Key) keys.get(i);
      }
    }
    return null;
  }

  /**
   * To update base note position for notes game according to clef
   *
   * @param Level for note reading game
   */
  public void updatepositionbase(net.jalmus.NoteLevel nrlevel) {
    if ( (nrlevel.isNotesgame() | nrlevel.isAccidentalsgame()) &
        !nrlevel.isAllnotesgame()) {
      if (nrlevel.isCurrentclefTreble()) {
        this.Setpositionbase2(0);
        this.Setpositionbase1(30 - nrlevel.getBasetreble() / 5);
      }
      else if (nrlevel.isCurrentclefBass()) {
        this.Setpositionbase1(0);
        this.Setpositionbase2(18 - nrlevel.getBasebass() / 5);
      }
      else if (nrlevel.isCurrentclefBoth()) {
        this.Setpositionbase1(30 - nrlevel.getBasetreble() / 5);
        this.Setpositionbase2(18 - nrlevel.getBasebass() / 5);
      }
    }
    else {
      this.Setpositionbase1(0);
      this.Setpositionbase2(0);
    }
  }

  /**
     * To play or stop the midi sound of a note when key of piano is pressed
     *
     * @param ChannelData where the note should be play,
     *  boolean indicate if the midi channel work and if sound is activate,
     *  int represent pitch of the note,
     *  int onoff if the note is already play or not.
   */
  public void notejouee(net.jalmus.ChannelData cc, boolean midiok, int pitch, int onoff) {
    // System.out.println("note jouee");

    for (int i = 0; i < whiteKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) whiteKeys.get(i);
      if (onoff == 1 & key.kNum == pitch)
        key.on(cc, midiok);
      else if (onoff == 0 & key.kNum == pitch)
        key.off(cc, midiok);
    }
    for (int i = 0; i < blackKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) blackKeys.get(i);
      if (onoff == 1 & key.kNum == pitch)
        key.on(cc, midiok);
      else if (onoff == 0 & key.kNum == pitch)
        key.off(cc, midiok);
    }
  }

  public void affichenotebase(Graphics g) {
    Color c = new Color(50, 255, 50);
    g.setColor(c);

    int decalage = 0;

    if (this.length == 61)
      decalage = -7; //one octava
    else if (this.length == 73)
      decalage = 0;

    if (this.positionbase1 != 0)
      g.fillOval( (this.positionbase1 + decalage) * kw + 8 + marge,
                 haut + kh - 8, 6, 6);

    if (this.positionbase2 != 0)
      g.fillOval( (this.positionbase2 + decalage) * kw + 8 + marge,
                 haut + kh - 8, 6, 6);

  }

  public void paint(Graphics g, int pitchcourant0, int pitchcourant1,
                    int pitchcourant2) {
    Graphics2D g2 = (Graphics2D) g;
    //  Dimension d = getSize();
    float f1, f2, f3 = 1;
    Color c = new Color(255, 235, 235);

    g2.setColor(Color.black);
    // g2.drawRect(marge+3,haut,42*kw,kh);
    for (int i = 0; i < whiteKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) whiteKeys.get(i);
      if (key.isNoteOn()) {
        g2.setColor(jfcBlue);
        g2.fill(key);
      }
      else if (key.kNum == pitchcourant0 | key.kNum == pitchcourant1 |
               key.kNum == pitchcourant2) {
        g2.setColor(red);
        g2.fill(key);
      }

      else if (key.kNum == 60 & !key.isNoteOn()) {
        g2.setColor(c);
        g2.fill(key);
        g2.setColor(Color.white);
      }

      g2.setColor(Color.black);
      g2.draw(key);
    }
    for (int i = 0; i < blackKeys.size(); i++) {
      net.jalmus.Key key = (net.jalmus.Key) blackKeys.get(i);
      if (key.isNoteOn()) {
        g2.setColor(jfcBlue);
        g2.fill(key);
        g2.setColor(Color.black);
        g2.draw(key);
      }
      else if (key.kNum == pitchcourant0 | key.kNum == pitchcourant1 |
               key.kNum == pitchcourant2) {
        g2.setColor(red);
        g2.fill(key);
        g2.setColor(Color.black);
        g2.draw(key);
      }
      else {
        g2.setColor(Color.black);
        g2.fill(key);
      }
    }
    //   if ((type2 == "NOTES" | type2 == "ALTERATIONS") & !toutesnotes)
    affichenotebase(g);
  }
} // End class Piano

