package net.jalmus;

import java.awt.Color;
import java.awt.Component;
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
              int valeur; // 1 ronde, 2 blanche, 4 noire, 8 croche, 16 double croche
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


              public void paint(Graphics g, int position, boolean courant, int dportee, Tabimage t, Component l) {

                int i; // compteur
                int hight = 14;
                int alteration = 0; //0 no alteration 1 sharp 2 flat

                if (this.pitch == 60) {hight = 43; alteration = 0;} //DO
                else if  (this.pitch == 61) {hight = 43; alteration = 1;} //DO# REb
                else if  (this.pitch == 62) {hight = 38; alteration = 0;} //RE
                else if  (this.pitch == 63) {hight = 38; alteration = 1;} //RE# MIb
                else if  (this.pitch == 64) {hight = 33; alteration = 0;} //MI
                else if  (this.pitch == 65) {hight = 28; alteration = 0;} //FA
                else if  (this.pitch == 66) {hight = 28; alteration = 1;} //FA# SOLb
                else if  (this.pitch == 67) {hight = 23; alteration = 0;} //SOL
                else if  (this.pitch == 68) {hight = 23; alteration = 1;} //SOL# LAb
                else if  (this.pitch == 69) {hight = 18; alteration = 0;} //LA
                else if  (this.pitch == 70) {hight = 18; alteration = 1;} //LA# SIb
                else if  (this.pitch == 71) {hight = 13; alteration = 0;} //SI
                else if  (this.pitch == 72) {hight = 8; alteration = 0;} //do
                else if  (this.pitch == 74) {hight = 5; alteration = 0;} //re
                else if  (this.pitch == 76) {hight = 0; alteration = 0;} //mi
                
               
                //  g.setColor(couleur);

                if (this.valeur == 1) {
                  if (this.silence) {
                    if (courant)
                      g.drawImage(t.Getimage(15), this.position, dportee + this.nportee*100 + 14 , l);
                    else
                      g.drawImage(t.Getimage(14), this.position, dportee + this.nportee*100 + 14 , l);
                  }

                  else {
                    if (courant)
                      g.drawImage(t.Getimage(3), this.position, dportee + this.nportee*100 + hight +2, l);
                    else
                      g.drawImage(t.Getimage(2), this.position, dportee + this.nportee*100 + hight +2, l);
                  }

                }
                else if (this.valeur == 2) {
                  if (this.silence) {
                    if (courant)
                      g.drawImage(t.Getimage(15), this.position, dportee + this.nportee*100 + 10, l);
                    else
                      g.drawImage(t.Getimage(14), this.position, dportee + this.nportee*100 + 10, l);
                  }

                  else {

                    if (courant)
                      g.drawImage(t.Getimage(5), this.position, dportee + this.nportee*100 + hight +2, l);
                    else
                      g.drawImage(t.Getimage(4), this.position, dportee + this.nportee*100 + hight +2, l);
                  }

                }

                else if (this.valeur == 4) {
                  if (this.silence) {

                    if (courant)
                      g.drawImage(t.Getimage(9), this.position, dportee + this.nportee*100, l);
                    else
                      g.drawImage(t.Getimage(8), this.position, dportee + this.nportee*100, l);
                  }

                  else {

                    if (courant)
                      g.drawImage(t.Getimage(7), this.position, dportee + this.nportee*100 + hight +1, l);
                    else
                      g.drawImage(t.Getimage(6), this.position, dportee + this.nportee*100 + hight +1, l);
                  }
                }

              else if (this.valeur == 8) {
               if (this.silence) {

                 if (courant)
                   g.drawImage(t.Getimage(11), this.position, dportee + this.nportee*100 + 10, l);
                 else
                   g.drawImage(t.Getimage(10), this.position, dportee + this.nportee*100 + 10, l);
               }

               else {

                 if (courant)
                   if (this.groupee == 1) {
                     g.drawImage(t.Getimage(7), this.position,
                                 dportee + this.nportee * 100 + hight, l);
                     g.setColor(Color.BLACK);
                   g.fillRect(this.position, dportee+ this.nportee*100+59, 28, 3);
                   }
                   else if (this.groupee == 2){
                     g.drawImage(t.Getimage(7), this.position,
                              dportee + this.nportee * 100 + hight, l);

                   }
                   else {
                     g.drawImage(t.Getimage(13), this.position,
                                 dportee + this.nportee * 100 + hight, l);
                   }

                 else
                 if (this.groupee == 1) {
                   g.drawImage(t.Getimage(6), this.position, dportee + this.nportee * 100 + hight, l);
                   g.setColor(Color.BLACK);
                   g.fillRect(this.position, dportee+this.nportee*100+59, 28, 3);

                 }
                 else if (this.groupee == 2){
                    g.drawImage(t.Getimage(6), this.position, dportee + this.nportee * 100 + hight, l);

                  }

                 else

                   g.drawImage(t.Getimage(12), this.position, dportee + this.nportee * 100 + hight, l);
               }                  }


              }

                  }
