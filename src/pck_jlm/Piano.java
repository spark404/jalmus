package pck_jlm;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;

import java.lang.Math;
import java.lang.Integer;


import java.util.Vector;

public class Piano  {

               Vector blackKeys = new Vector();
               Vector keys = new Vector();
               Vector whiteKeys = new Vector();
               Color jfcBlue = new Color(204, 204, 255);
               Color pink = new Color(255, 175, 175);

               Key prevKey;
               int positionbase1 = 0; // 0 si pas de note de base � afficher sinon position sur le clavier
               int positionbase2 = 0; // 0 pour deuxième clé
               final int kw = 16, kh = 80;
               final int haut = 315;
               final int marge = 40;


               public Piano() {
                   //setLayout(new BorderLayout());
                  // setPreferredSize(new Dimension(42*kw, kh+1));
                   int transpose = 24;
                   int whiteIDs[] = { 0, 2, 4, 5, 7, 9, 11 };

                   for (int i = 0, x = 3; i < 6; i++) {
                       for (int j = 0; j < 7; j++, x += kw) {
                           int keyNum = i * 12 + whiteIDs[j] + transpose;
                           whiteKeys.add(new Key(x+marge, haut, kw, kh, keyNum));
                       }
                   }
                   for (int i = 0, x = 3; i < 6; i++, x += kw) {
                       int keyNum = i * 12 + transpose;
                       blackKeys.add(new Key((x += kw)-4+marge, haut, kw/2, kh/2, keyNum+1));
                       blackKeys.add(new Key((x += kw)-4+marge, haut, kw/2, kh/2, keyNum+3));
                       x += kw;
                       blackKeys.add(new Key((x += kw)-4+marge, haut, kw/2, kh/2, keyNum+6));
                       blackKeys.add(new Key((x += kw)-4+marge, haut, kw/2, kh/2, keyNum+8));
                       blackKeys.add(new Key((x += kw)-4+marge, haut, kw/2, kh/2, keyNum+10));
                   }
                   keys.addAll(blackKeys);
                   keys.addAll(whiteKeys);


               }

               public void Setpositionbase1(int posnotebase){
                 this.positionbase1 = posnotebase;
                 }


                 public void Setpositionbase2(int posnotebase){
                 this.positionbase2 = posnotebase;
                 }

              public void Setprevkey(Key k){
                this.prevKey = k;
              }

              public Key Getprevkey(){
                return this.prevKey;
              }

              public int Getpositionbase1(){
                return this.positionbase1;
              }

              public int Getpositionbase2(){
                            return this.positionbase2;
                          }




               public Key getKey(Point point) {
                 Point p = new Point();
                 p.setLocation(point.getX(),point.getY()-100);
                    for (int i = 0; i < keys.size(); i++) {
                       if (((Key) keys.get(i)).contains(p)) {
                           return (Key) keys.get(i);
                       }
                   }
                   return null;
               }

               public void notejouee(ChannelData cc, boolean midiok,int pitch, int onoff){
                     for (int i = 0; i < whiteKeys.size(); i++) {
                       Key key = (Key) whiteKeys.get(i);
                       if (onoff == 1 & key.kNum == pitch) key.on(cc, midiok);
                       else if (onoff == 0 & key.kNum == pitch) key.off(cc, midiok);
                     }
                     for (int i = 0; i < blackKeys.size(); i++) {
                            Key key = (Key) blackKeys.get(i);
                            if (onoff == 1 & key.kNum == pitch) key.on(cc, midiok);
                       else if (onoff == 0 & key.kNum == pitch) key.off(cc, midiok);
                     }
                    }



              public void affichenotebase(Graphics g, String clecourante){
                Color c = new Color(50,255,50);
                g.setColor(c);
              /*  if (clecourante == "sol" | clecourante == "sol+fa")
                g.fillOval((30-basesol/4)*kw+8,haut+kh-8,6,6); //sol = 25kw
               if (clecourante == "fa" | clecourante == "sol+fa")
                 g.fillOval((18-basefa/4)*kw+8,haut+kh-8,6,6); //fa = 17kw*/
              if (this.positionbase1 != 0)
                g.fillOval(this.positionbase1 * kw + 8 + marge, haut + kh - 8, 6, 6);

              if (this.positionbase2 != 0)
                g.fillOval(this.positionbase2 * kw + 8 + marge, haut + kh - 8, 6, 6);


              }

               public void paint(Graphics g, String clecourante) {
                   Graphics2D g2 = (Graphics2D) g;
                 //  Dimension d = getSize();
                   float f1, f2, f3 = 1;
                   Color c = new Color(255,235,235);

      //             g2.setColor(Color.white);
             //      g2.fillRect(marge+4, haut, 42*kw, kh);
             g2.setColor(Color.black);
             g2.drawRect(marge+3,haut,42*kw,kh);
                   for (int i = 0; i < whiteKeys.size(); i++) {
                       Key key = (Key) whiteKeys.get(i);
                       if (key.isNoteOn()) {
                           g2.setColor(jfcBlue);
                           g2.fill(key);
                       }
                       if (key.kNum ==60 & !key.isNoteOn()) {
                         g2.setColor(c);
                         g2.fill(key);
                         g2.setColor(Color.white);
                       }

                       g2.setColor(Color.black);
                       g2.draw(key);
                   }
                   for (int i = 0; i < blackKeys.size(); i++) {
                       Key key = (Key) blackKeys.get(i);
                       if (key.isNoteOn()) {
                           g2.setColor(jfcBlue);
                           g2.fill(key);
                           g2.setColor(Color.black);
                           g2.draw(key);
                       } else {
                           g2.setColor(Color.black);
                           g2.fill(key);
                       }
                   }
                 //   if ((type2 == "NOTES" | type2 == "ALTERATIONS") & !toutesnotes)
                    affichenotebase(g, clecourante);
               }
     } // End class Piano


