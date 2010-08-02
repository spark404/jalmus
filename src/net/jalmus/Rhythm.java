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


              public void paint(Graphics g, Tonality ton, int position, boolean courant, int dportee, Tabimage t, Component l) {

              
                int hight = 14;
                @SuppressWarnings("unused")
				int alteration = 0; //0 no alteration 1 sharp 2 flat
                
             

                if (this.pitch == 60) {hight = 43; alteration = 0;} //DO
                else if  (this.pitch == 61) {hight = 43; alteration = 1;} //DO# REb
                else if  (this.pitch == 62) {hight = 38; alteration = 0;} //RE
                
                
                else if  (this.pitch == 63  && ton.isflat() && ton.getNbalt() >=  2) {hight = 33; alteration = 2;} // MIb
                else if  (this.pitch == 63) {hight = 38; alteration = 1;} //RE# 
                else if  (this.pitch == 64 && ton.isflat() && ton.getNbalt() >=  7) {hight = 28; alteration = 2;} // fab
                else if  (this.pitch == 64) {hight = 33; alteration = 0;} //MI
                else if  (this.pitch == 65 && ton.issharp() && ton.getNbalt() >=  6) {hight = 33; alteration = 1;} //MI#
                else if  (this.pitch == 65) {hight = 28; alteration = 0;} //FA
                else if  (this.pitch == 66 && ton.issharp() && ton.getNbalt() >=  1) {hight = 28; alteration = 1;} //FA# 
                else if  (this.pitch == 66 && ton.isflat() && ton.getNbalt() >=  5) {hight = 23; alteration = 2;} // SOLb
                else if  (this.pitch == 67) {hight = 23; alteration = 0;} //SOL
                else if  (this.pitch == 68 && ton.issharp() && ton.getNbalt() >=  3) {hight = 23; alteration = 1;} //SOL# 
                else if  (this.pitch == 68 && ton.isflat() && ton.getNbalt() >=  3) {hight = 18; alteration = 2;} // LAb
                else if  (this.pitch == 69) {hight = 18; alteration = 0;} //LA
                else if  (this.pitch == 70 && ton.issharp() && ton.getNbalt() >=  5) {hight = 18; alteration = 1;} //LA# 
                else if  (this.pitch == 70 && ton.isflat() && ton.getNbalt() >=  1) {hight = 13; alteration = 2;} // SIb
                
                else if  (this.pitch == 71 && ton.isflat() && ton.getNbalt() >=  6) {hight = 8; alteration = 0;} //dob
                else if  (this.pitch == 71) {hight = 13; alteration = 0;} //SI
                else if  (this.pitch == 72 && ton.issharp() && ton.getNbalt() >=  7) {hight = 13; alteration = 1;} //si#
                else if  (this.pitch == 72) {hight = 8; alteration = 0;} //do
                else if  (this.pitch == 73 && ton.issharp() && ton.getNbalt() >=  2) {hight = 8; alteration = 1;} //do#
                else if  (this.pitch == 73 && ton.isflat() && ton.getNbalt() >=  4) {hight = 3; alteration = 2;} //reb
                else if  (this.pitch == 74) {hight = 3; alteration = 0;} //re
                else if  (this.pitch == 75 && ton.issharp() && ton.getNbalt() >=  4) {hight = 3; alteration = 1;} //re#
                else if  (this.pitch == 75  && ton.isflat() && ton.getNbalt() >=  2) {hight = -2; alteration = 2;} // MIb
                else if  (this.pitch == 76) {hight = -2; alteration = 0;} //mi
                else if  (this.pitch == 77  && ton.issharp() && ton.getNbalt() >=  6) {hight = -2; alteration = 1;} //mi#
                else if  (this.pitch == 77) {hight = -7; alteration = 0;} //fa
             
              
                if (this.pitch == 27) {hight = 43; alteration = 0;} //
                else if  (this.pitch == 28) {hight = 43; alteration = 1;} //
              
                
                else if  (this.pitch == 30 && ton.isflat() && ton.getNbalt() >=  5) {hight = 33; alteration = 2;} // SOLb
                else if  (this.pitch == 30) {hight = 38; alteration = 0;} // fa
                else if  (this.pitch == 31) {hight = 33; alteration = 1;} //SOL
                else if  (this.pitch == 32 && ton.issharp() && ton.getNbalt() >=  3) {hight = 33; alteration = 1;} //SOL# 
                else if  (this.pitch == 32 && ton.isflat() && ton.getNbalt() >=  3) {hight = 28; alteration = 2;} // LAb     
                else if  (this.pitch == 33) {hight = 28; alteration = 0;} //LA
                else if  (this.pitch == 34 && ton.issharp() && ton.getNbalt() >=  5) {hight = 28; alteration = 1;} //LA# 
                else if  (this.pitch == 34 && ton.isflat() && ton.getNbalt() >=  1) {hight = 23; alteration = 2;} // SIb               
                else if  (this.pitch == 35 && ton.isflat() && ton.getNbalt() >=  6) {hight = 18; alteration = 2;} //dob 
                else if  (this.pitch == 35) {hight = 23; alteration = 0;} //SI
                else if  (this.pitch == 36 && ton.issharp() && ton.getNbalt() >=  7) {hight = 23; alteration = 1;} //si#        
                else if  (this.pitch == 36) {hight = 18; alteration = 1;} //do
                else if  (this.pitch == 37 && ton.issharp() && ton.getNbalt() >=  2) {hight = 18; alteration = 1;} //do#
                else if  (this.pitch == 37 && ton.isflat() && ton.getNbalt() >=  4) {hight = 13; alteration = 2;} //reb 
                else if  (this.pitch == 38) {hight = 13; alteration = 0;} //re
                else if  (this.pitch == 39 && ton.issharp() && ton.getNbalt() >=  4) {hight = 13; alteration = 1;} //re#
                else if  (this.pitch == 39  && ton.isflat() && ton.getNbalt() >=  2) {hight = 8; alteration = 2;} // MIb
                else if  (this.pitch == 40 && ton.isflat() && ton.getNbalt() >=  7) {hight = 3; alteration = 2;} //fab
                else if  (this.pitch == 40) {hight = 8; alteration = 0;} //mi
                else if  (this.pitch == 41  && ton.issharp() && ton.getNbalt() >=  6) {hight = 8; alteration = 1;} //mi#  
                else if  (this.pitch == 41) {hight = 3; alteration = 0;} //fa
                else if  (this.pitch == 42 && ton.issharp() && ton.getNbalt() >=  1) {hight = 3; alteration = 1;} //FA# 
                else if  (this.pitch == 42 && ton.isflat() && ton.getNbalt() >=  5) {hight = -2; alteration = 2;} // SOLb 
                else if  (this.pitch == 43) {hight = -2; alteration = 0;} //sol
                else if  (this.pitch == 44 && ton.issharp() && ton.getNbalt() >=  3) {hight = -2; alteration = 1;} // SOL#         
                
               
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
                   g.fillRect(this.position, dportee+ this.nportee*100+hight, 38, 3);
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
                   g.fillRect(this.position, dportee+this.nportee*100+47+hight, 38, 3);

                 }
                 else if (this.groupee == 2){
                    g.drawImage(t.Getimage(6), this.position, dportee + this.nportee * 100 + hight, l);

                  }

                 else

                   g.drawImage(t.Getimage(12), this.position, dportee + this.nportee * 100 + hight, l);
               }                  }


              }

                  }
