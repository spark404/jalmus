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
import java.util.ResourceBundle;

 public class Note{
        String Alteration;
        String Nom;
        int Height;
        int X; // x position for animation
        int Pitch; // midi pitch


        public Note(String alt, String nom, int h, int x, int p){
          this.Alteration = alt;
          this.Nom = nom;
          this.Height = h; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
          this.X = x;
          this.Pitch = p;
        }

        public void init(){
         this.Alteration = "";
         this.Nom = "";
         this.Height = 0; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
         this.X = 0;
         this.Pitch = 0;
        }

        public void copy(Note n){
          this.Alteration = n.Alteration;
          this.Nom = n.Nom;
          this.Height = n.Height; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
          this.X = n.X;
          this.Pitch = n.Pitch;
        }

        public String getNom(){
          return this.Nom;
        }

        public String getAlteration(){
                return this.Alteration;
              }

        public int getPitch(){
          return this.Pitch;
        }

        public void setPitch(int i){
          this.Pitch = i;
        }

        public int getX(){
                  return this.X;
                }

        public void setX(int i){
          this.X = i;
        }

        public int getHeight(){
                  return this.Height;
                }

        public void setHeight(int h){
          this.Height = h;
        }





        public void paint(NoteLevel nrlevel, Graphics g, Font f, int decalagea, int decalagen, int dportee, Component j, Color couleur, ResourceBundle b){

          // decalagea est utilis� pour le d�calage des alt�rations dans l'accord
          // decalagen est utilis� pour le d�calage des notes dans l'intervalle
           int i;   // compteur

           g.setFont(f.deriveFont(50f));
           g.setColor(couleur);

           //g.fillOval(this.X+decalagen,this.Height+5,11,12);    // DESSINE LA NOTE
           g.drawString("w", this.X+decalagen, this.Height + 11);
           if ((!this.Alteration.equals("") & !this.alteree(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
           if (this.Alteration.equals("#")) g.drawString("B", this.X-decalagea, this.Height+10);
           else if (this.Alteration.equals("b")) g.drawString("b", this.X-decalagea, this.Height+10);
           else if (this.Alteration.equals("n")) {
        	   String bq = "" + (char)0xBD;
        	   g.drawString(bq, this.X-decalagea, this.Height + 10);
           }
/*
            if (couleur==Color.black) {
               
               g.drawImage(tab.Getimage(22), this.X + decalagen, this.Height, j);
               if ((!this.Alteration.equals("") & !this.alteree(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
               if (this.Alteration.equals("#")) g.drawImage(tab.Getimage(17), this.X-(decalagea+1),  this.Height-10,j); //diese y-8 bemol y-14
               else if (this.Alteration.equals("b"))  g.drawImage(tab.Getimage(18), this.X-(decalagea+1),  this.Height-11, j);
               else if (this.Alteration.equals("n"))  g.drawImage(tab.Getimage(16), this.X-(decalagea-1),  this.Height-7, j);
             }
             else {
               g.drawImage(tab.Getimage(23), this.X + decalagen, this.Height, j);
               if ((!this.Alteration.equals("") & !this.alteree(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
               if (this.Alteration.equals("#")) g.drawImage(tab.Getimage(20), this.X-(decalagea+1),  this.Height-10,j); //diese y-8 bemol y-14
               else if (this.Alteration.equals("b"))  g.drawImage(tab.Getimage(21), this.X-(decalagea+1),  this.Height-11, j);
               else if (this.Alteration.equals("n"))  g.drawImage(tab.Getimage(19), this.X-(decalagea-1),  this.Height-7, j);
             }
*/

            //     affichealt(g,this.Alteration,, this.Height+1);
             // DEUX CAS  Simple clé ou double clés


             if (nrlevel.isCurrentKeyTreble() | nrlevel.isCurrentKeyBass()) {
               if (this.Height>=dportee+45) { // <DO en dessous de la port�e en cl� de sol
                 for(i=dportee+50; i<=this.Height+5; i=i+10){
                   if (i != this.Height+5) g.setColor(Color.black); //!= this.Height+4
                   else g.setColor(couleur);
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); // dessine la port�e en dessous de la port�e normale

                 }
               }

               else  if (this.Height<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Height+5; i=i-10){

                   if (i != this.Height+5) g.setColor(Color.black);
                   else g.setColor(couleur);
                   g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i);  // dessine la portee en dessus de la port�e normale
                 }
               };
             }

             // CAS DE LA DOUBLE CLE SOL ET FA

             else if( nrlevel.isCurrentKeyBoth()) {

               // cas de la cl� de sol
               if (this.Height>=dportee+45 & this.Height <= dportee+55) { // du DO jusqu'au LA en dessous de la port�e
                 for(i=dportee+50; i<=this.Height+5; i=i+10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Height<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Height+5; i=i-10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la portee en dessus de la port�e normale
               }


               // cas de la cl� de fa
               else if (this.Height>=dportee+135) {  // � partie du MI en dessous de la port�e
                 for(i=dportee+140; i<=this.Height+5; i=i+10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Height<=dportee+75 & this.Height >=dportee+60) {
                 for(i=dportee+80; i>=this.Height+5; i=i-10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }      // dessine la portee en dessus de la port�e normale
               };
             }
           }



           public boolean identiques(int base){
           int x;
            boolean b = false;



            for (x=0;x<=3;x++){ //28 = 8 notes entre 2 notes identiques * 4 entre chaque note
              if ((this.Height+x*35==base) | (this.Height-x*35==base))  b = true;                                                   }
                return b;
                }

          public boolean alteree(Tonality t, ResourceBundle bundle){

            String DO = bundle.getString("_do");
            String RE = bundle.getString("_re");
            String MI = bundle.getString("_mi");
            String FA = bundle.getString("_fa");
            String SOL = bundle.getString("_sol");
            String LA = bundle.getString("_la");
            String SI = bundle.getString("_si");

            if (t.Alteration.equalsIgnoreCase("#"))
              if (this.Nom == FA & t.getAlterationsNumber() >=1) return true;
              else if (this.Nom == DO & t.getAlterationsNumber() >=2) return true;
              else if (this.Nom == SOL & t.getAlterationsNumber() >=3) return true;
              else if (this.Nom == RE & t.getAlterationsNumber() >=4) return true;
              else if (this.Nom == LA & t.getAlterationsNumber() >=5) return true;
              else if (this.Nom == MI & t.getAlterationsNumber() >=6) return true;
              else if (this.Nom == SI & t.getAlterationsNumber() >=7) return true;
              else return false;
            else if (t.Alteration.equalsIgnoreCase("b"))
              if (this.Nom == SI & t.getAlterationsNumber() >=1) return true;
              else if (this.Nom == MI & t.getAlterationsNumber() >=2) return true;
              else if (this.Nom == LA & t.getAlterationsNumber() >=3) return true;
              else if (this.Nom == RE & t.getAlterationsNumber() >=4) return true;
              else if (this.Nom == SOL & t.getAlterationsNumber() >=5) return true;
              else if (this.Nom == DO & t.getAlterationsNumber() >=6) return true;
              else if (this.Nom == FA & t.getAlterationsNumber() >=7) return true;
              else return false;

            else return false;
          }


          public void majalteration( NoteLevel nrlevel, ResourceBundle b){
            int alt = 0;
            double tmp = 0;

            if (!nrlevel.isNotesgame()) {
              tmp = Math.random();

              if (this.alteree(nrlevel.getCurrentTonality(), b)) {
                if (tmp < 0.9)
                  this.Alteration = nrlevel.getCurrentTonality().Alteration;
                else
                  this.Alteration = "n";
              }
              else {

                if (tmp < 0.9)
                  this.Alteration = "";
                                 else
                   this.Alteration = nrlevel.getCurrentTonality().Alteration;

              }
            }
            else if (this.alteree(nrlevel.getCurrentTonality(),b))
              this.Alteration = nrlevel.getCurrentTonality().Alteration;

            else this.Alteration = "";

//System.out.println(this.Alteration);

              // MODIFICATION DU PITCH MIDI EN FONCTION DE L'ALTERATION
        if (this.Alteration.equals("#")) alt = 1;
          else  if (this.Alteration.equals("b")) alt = -1;
          else alt = 0;

          this.Pitch = this.Pitch+alt;
                }


         public void majalteration(Tonality t, int pitch0, int nnote, ResourceBundle b){ //pour les accords
                  int alt = 0;
 
                  double tmp = 0;
                  //System.out.println(this.Pitch-pitch0+" AVANT");
                  tmp = Math.random();


                  if (nnote == 2) { //deuxieme note de l'accord tierce majeure ou mineure

                    if (this.Pitch-pitch0 == 2){
                      if (t.Alteration.equals("#")) this.Alteration = "#";

                    }

                    else if (this.Pitch-pitch0 == 3){
                      if (tmp<0.4 | t.Alteration.equals("b")) {//laisser tierce mineure
                        if (this.alteree(t,b)) this.Alteration = "n";
                      }
                    else //passer a tierce majeure
                      this.Alteration = "#";
                    }



                    else if (this.Pitch-pitch0 == 4){
                      if (tmp<0.4 | t.Alteration.equals("#")) { //laisser tierce majeure
                        if (this.alteree(t,b)) this.Alteration = "n";
                      }
                      else //passer a tierce mineure
                        this.Alteration = "b";
                    }

                    else if (this.Pitch-pitch0 == 5){
                      if (t.Alteration.equals("b")) this.Alteration = "b";

                    }

                  }

                  else if (nnote == 3) { // troisieme note de l'accord quinte juste

                    if (this.Pitch-pitch0 == 6){
                      if (tmp<0.4 | t.Alteration.equals("b")) {//laisser quinte diminuee
                        if (this.alteree(t,b)) this.Alteration = "n";
                      }
                      else this.Alteration = "#";

                    }

                    else if (this.Pitch-pitch0 == 7){
                      if (tmp<0.1 & t.Alteration.equals("b")) this.Alteration = "b"; // quinte diminuee
                      else  if (tmp<0.2 & t.Alteration.equals("#")) this.Alteration = "#"; // quinte augmentee

                       else if (this.alteree(t,b)) this.Alteration = "n";
                    }


                    else if (this.Pitch-pitch0 == 8){
                      if (tmp<0.4 | t.Alteration.equals("#")) {//laisser quinte augmentee
                        if (this.alteree(t,b)) this.Alteration = "n";
                      }
                      else this.Alteration = "b";

                    }

                  }

//System.out.println(this.Alteration);

                  // MODIFICATION DU PITCH MIDI EN FONCTION DE L'ALTERATION
                  if (this.Alteration.equals("#")) alt = 1;
                  else  if (this.Alteration.equals("b")) alt = -1;
                  else alt = 0;

                  this.Pitch = this.Pitch+alt;

                 // System.out.println(this.Pitch-pitch0+" APRES");


                }





          public void majnote(NoteLevel nrlevel, int dportee, ResourceBundle bundle){

            String DO = bundle.getString("_do");
            String RE = bundle.getString("_re");
            String MI = bundle.getString("_mi");
            String FA = bundle.getString("_fa");
            String SOL = bundle.getString("_sol");
            String LA = bundle.getString("_la");
            String SI = bundle.getString("_si");

            if (nrlevel.isCurrentKeyTreble()){//base cl� de sol : SOL = dportee+25

              if (this.identiques(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Height-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Height-(dportee+15))/28*12;}
              else if (this.identiques(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Height-(dportee+20))/28*12;}
              else  if  (this.identiques(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Height-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Height-(dportee+30))/28*12;}
              else if (this.identiques(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Height-(dportee+35))/28*12;}
              else if (this.identiques(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Height-(dportee+40))/28*12;}
              }

            else if (nrlevel.isCurrentKeyBass()){ //base cl� de fa : FA = dportee+5

              if (this.identiques(dportee+20)) { this.Nom = DO; this.Pitch = 48-(this.Height-(dportee+20))/28*12;}
              else if (this.identiques(dportee+25)) { this.Nom = SI;this.Pitch = 47-(this.Height-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = LA;this.Pitch = 45-(this.Height-(dportee+30))/28*12;}
              else  if  (this.identiques(dportee+35)) { this.Nom = SOL;this.Pitch = 43-(this.Height-(dportee+35))/28*12;}
              else if (this.identiques(dportee+5)) { this.Nom = FA;this.Pitch = 53-(this.Height-(dportee+5))/28*12;}
              else if (this.identiques(dportee+10)) { this.Nom = MI;this.Pitch = 52-(this.Height-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = RE;this.Pitch = 50-(this.Height-(dportee+15))/28*12;};
              }

            else if (nrlevel.isCurrentKeyBoth()){
              if (this.Height<=dportee+55){   // CLE DE SOL
                if (this.identiques(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Height-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Height-(dportee+15))/28*12;}
              else if (this.identiques(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Height-(dportee+20))/28*12;}
              else  if  (this.identiques(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Height-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Height-(dportee+30))/28*12;}
              else if (this.identiques(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Height-(dportee+35))/28*12;}
              else if (this.identiques(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Height-(dportee+40))/28*12;}
                                                              }
              else {       // CLE DE FA  //base cl� de sol : FA = dportee+76
                if (this.identiques(dportee+110)) { this.Nom = DO; this.Pitch = 48-(this.Height-(dportee+110))/28*12;}
                else if (this.identiques(dportee+115)) { this.Nom = SI;this.Pitch = 47-(this.Height-(dportee+115))/28*12;}
                else if (this.identiques(dportee+120)) { this.Nom = LA;this.Pitch = 45-(this.Height-(dportee+120))/28*12;}
                else  if  (this.identiques(dportee+125)) { this.Nom = SOL;this.Pitch = 43-(this.Height-(dportee+125))/28*12;}
                else if (this.identiques(dportee+95)) { this.Nom = FA;this.Pitch = 53-(this.Height-(dportee+95))/28*12;}
                else if (this.identiques(dportee+100)) { this.Nom = MI;this.Pitch = 52-(this.Height-(dportee+100))/28*12;}
                else if (this.identiques(dportee+105)) { this.Nom = RE;this.Pitch = 50-(this.Height-(dportee+105))/28*12;};
                }
              }
            }

      }





