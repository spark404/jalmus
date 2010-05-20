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
              int nportee; // 0 pour la premi�re ligne de port�e ...
              boolean pointee;
              boolean silence;

             int groupee; //0 non regroup�e 1 debut de regroupement 2 fin du regroupement

              public Rhythm(int val, int pos, int np, boolean pt, boolean sl, int bm) {
                this.valeur = val;
                this.position = pos;
                this.nportee = np;
                this.pointee = pt;
                this.silence = sl;
                this.groupee = bm;

              }

              public void init() {
                this.valeur = 0;
                this.position = 0;
                this.nportee = 0;
                this.pointee = false;
                this.silence = false;
                this.groupee = 0;
              }

              public void copy(Rhythm r) {
                this.valeur = r.valeur;
                this.position = r.position;
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

              public boolean getSilence() {
                return this.silence;
        }



              public void paint(Graphics g, int position, boolean courant, int dportee, Tabimage t, Component l) {

                int i; // compteur

                //  g.setColor(couleur);

                if (this.valeur == 1) {
                  if (this.silence) {
                    if (courant)
                      g.drawImage(t.Getimage(15), this.position, dportee + this.nportee*100 + 14, l);
                    else
                      g.drawImage(t.Getimage(14), this.position, dportee + this.nportee*100 + 14, l);
                  }

                  else {
                    if (courant)
                      g.drawImage(t.Getimage(3), this.position, dportee + this.nportee*100 + 14, l);
                    else
                      g.drawImage(t.Getimage(2), this.position, dportee + this.nportee*100 + 14, l);
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
                      g.drawImage(t.Getimage(5), this.position, dportee + this.nportee*100 + 14, l);
                    else
                      g.drawImage(t.Getimage(4), this.position, dportee + this.nportee*100 + 14, l);
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
                      g.drawImage(t.Getimage(7), this.position, dportee + this.nportee*100 + 14, l);
                    else
                      g.drawImage(t.Getimage(6), this.position, dportee + this.nportee*100 + 14, l);
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
                                 dportee + this.nportee * 100 + 14, l);
                     g.setColor(Color.BLACK);
                   g.fillRect(this.position, dportee+ this.nportee*100+59, 28, 3);
                   }
                   else if (this.groupee == 2){
                     g.drawImage(t.Getimage(7), this.position,
                              dportee + this.nportee * 100 + 14, l);

                   }
                   else {
                     g.drawImage(t.Getimage(13), this.position,
                                 dportee + this.nportee * 100 + 14, l);
                   }

                 else
                 if (this.groupee == 1) {
                   g.drawImage(t.Getimage(6), this.position, dportee + this.nportee * 100 + 14, l);
                   g.setColor(Color.BLACK);
                   g.fillRect(this.position, dportee+this.nportee*100+59, 28, 3);

                 }
                 else if (this.groupee == 2){
                    g.drawImage(t.Getimage(6), this.position, dportee + this.nportee * 100 + 14, l);

                  }

                 else

                   g.drawImage(t.Getimage(12), this.position, dportee + this.nportee * 100 + 14, l);
               }                  }


              }

                  }
