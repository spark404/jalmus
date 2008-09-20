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
import java.util.ResourceBundle;

 public class Note{
        String Alteration;
        String Nom;
        int Hauteur;
        int X; //coordonn�ee pour l'animation
        int Pitch; //hauteur midi


        public Note(String alt, String nom, int h, int x, int p){
          this.Alteration = alt;
          this.Nom = nom;
          this.Hauteur = h; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
          this.X = x;
          this.Pitch = p;
        }

        public void init(){
         this.Alteration = "";
         this.Nom = "";
         this.Hauteur = 0; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
         this.X = 0;
         this.Pitch = 0;
        }

        public void copy(Note n){
          this.Alteration = n.Alteration;
          this.Nom = n.Nom;
          this.Hauteur = n.Hauteur; // pos. de la note dportee+20 = sol cle de sol, dportee+24 = fa (+4)
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

        public int getHauteur(){
                  return this.Hauteur;
                }

        public void setHauteur(int h){
          this.Hauteur = h;
        }





        public void paint(NoteLevel nrlevel, Graphics g, int decalagea, int decalagen, int dportee,  Tabimage tab, Component j, Color couleur, ResourceBundle b){

          // decalagea est utilis� pour le d�calage des alt�rations dans l'accord
          // decalagen est utilis� pour le d�calage des notes dans l'intervalle
           int i;   // compteur

           g.setColor(couleur);
            //g.fillOval(this.X+decalagen,this.Hauteur+5,11,12);    // DESSINE LA NOTE
             if (couleur==Color.black) {

               g.drawImage(tab.Getimage(22), this.X + decalagen, this.Hauteur, j);
               if ((!this.Alteration.equals("") & !this.alteree(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
             if (this.Alteration.equals("#")) g.drawImage(tab.Getimage(17), this.X-(decalagea+1),  this.Hauteur-10,j); //diese y-8 bemol y-14
           else if (this.Alteration.equals("b"))  g.drawImage(tab.Getimage(18), this.X-(decalagea+1),  this.Hauteur-11, j);
          else if (this.Alteration.equals("n"))  g.drawImage(tab.Getimage(16), this.X-(decalagea-1),  this.Hauteur-7, j);

             }
             else {

               g.drawImage(tab.Getimage(23), this.X + decalagen, this.Hauteur, j);
               if ((!this.Alteration.equals("") & !this.alteree(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
            if (this.Alteration.equals("#")) g.drawImage(tab.Getimage(20), this.X-(decalagea+1),  this.Hauteur-10,j); //diese y-8 bemol y-14
          else if (this.Alteration.equals("b"))  g.drawImage(tab.Getimage(21), this.X-(decalagea+1),  this.Hauteur-11, j);
         else if (this.Alteration.equals("n"))  g.drawImage(tab.Getimage(19), this.X-(decalagea-1),  this.Hauteur-7, j);

             }


            //     affichealt(g,this.Alteration,, this.Hauteur+1);
             // DEUX CAS  Simple clé ou double clés


             if (nrlevel.isCurrentclefTreble() | nrlevel.isCurrentclefBass()) {
               if (this.Hauteur>=dportee+45) { // <DO en dessous de la port�e en cl� de sol
                 for(i=dportee+50; i<=this.Hauteur+5; i=i+10){
                   if (i != this.Hauteur+5) g.setColor(Color.black); //!= this.Hauteur+4
                   else g.setColor(couleur);
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); // dessine la port�e en dessous de la port�e normale

                 }
               }

               else  if (this.Hauteur<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Hauteur+5; i=i-10){

                   if (i != this.Hauteur+5) g.setColor(Color.black);
                   else g.setColor(couleur);
                   g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i);  // dessine la portee en dessus de la port�e normale
                 }
               };
             }

             // CAS DE LA DOUBLE CLE SOL ET FA

             else if( nrlevel.isCurrentclefBoth()) {

               // cas de la cl� de sol
               if (this.Hauteur>=dportee+45 & this.Hauteur <= dportee+55) { // du DO jusqu'au LA en dessous de la port�e
                 for(i=dportee+50; i<=this.Hauteur+5; i=i+10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Hauteur<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Hauteur+5; i=i-10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la portee en dessus de la port�e normale
               }


               // cas de la cl� de fa
               else if (this.Hauteur>=dportee+135) {  // � partie du MI en dessous de la port�e
                 for(i=dportee+140; i<=this.Hauteur+5; i=i+10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Hauteur<=dportee+75 & this.Hauteur >=dportee+60) {
                 for(i=dportee+80; i>=this.Hauteur+5; i=i-10){
                 g.drawLine(this.X-1+decalagen,i,this.X+16+decalagen, i); }      // dessine la portee en dessus de la port�e normale
               };
             }
           }



           public boolean identiques(int base){
           int x;
            boolean b = false;



            for (x=0;x<=3;x++){ //28 = 8 notes entre 2 notes identiques * 4 entre chaque note
              if ((this.Hauteur+x*35==base) | (this.Hauteur-x*35==base))  b = true;                                                   }
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
              if (this.Nom == FA & t.Nbalt >=1) return true;
                else if (this.Nom == DO & t.Nbalt >=2) return true;
                else if (this.Nom == SOL & t.Nbalt >=3) return true;
                else if (this.Nom == RE & t.Nbalt >=4) return true;
                else if (this.Nom == LA & t.Nbalt >=5) return true;
                else if (this.Nom == MI & t.Nbalt >=6) return true;
                else if (this.Nom == SI & t.Nbalt >=7) return true;
                else return false;
            else if (t.Alteration.equalsIgnoreCase("b"))
              if (this.Nom == SI & t.Nbalt >=1) return true;
            else if (this.Nom == MI & t.Nbalt >=2) return true;
            else if (this.Nom == LA & t.Nbalt >=3) return true;
            else if (this.Nom == RE & t.Nbalt >=4) return true;
            else if (this.Nom == SOL & t.Nbalt >=5) return true;
            else if (this.Nom == DO & t.Nbalt >=6) return true;
            else if (this.Nom == FA & t.Nbalt >=7) return true;
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
                  int minmaj = 0;
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


            if (nrlevel.isCurrentclefTreble()){//base cl� de sol : SOL = dportee+25

              if (this.identiques(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Hauteur-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Hauteur-(dportee+15))/28*12;}
              else if (this.identiques(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Hauteur-(dportee+20))/28*12;}
              else  if  (this.identiques(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Hauteur-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Hauteur-(dportee+30))/28*12;}
              else if (this.identiques(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Hauteur-(dportee+35))/28*12;}
              else if (this.identiques(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Hauteur-(dportee+40))/28*12;}
              }

            else if (nrlevel.isCurrentclefBass()){ //base cl� de fa : FA = dportee+5

              if (this.identiques(dportee+20)) { this.Nom = DO; this.Pitch = 48-(this.Hauteur-(dportee+20))/28*12;}
              else if (this.identiques(dportee+25)) { this.Nom = SI;this.Pitch = 47-(this.Hauteur-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = LA;this.Pitch = 45-(this.Hauteur-(dportee+30))/28*12;}
              else  if  (this.identiques(dportee+35)) { this.Nom = SOL;this.Pitch = 43-(this.Hauteur-(dportee+35))/28*12;}
              else if (this.identiques(dportee+5)) { this.Nom = FA;this.Pitch = 53-(this.Hauteur-(dportee+5))/28*12;}
              else if (this.identiques(dportee+10)) { this.Nom = MI;this.Pitch = 52-(this.Hauteur-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = RE;this.Pitch = 50-(this.Hauteur-(dportee+15))/28*12;};
              }

            else if (nrlevel.isCurrentclefBoth()){
              if (this.Hauteur<=dportee+55){   // CLE DE SOL
                if (this.identiques(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Hauteur-(dportee+10))/28*12;}
              else if (this.identiques(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Hauteur-(dportee+15))/28*12;}
              else if (this.identiques(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Hauteur-(dportee+20))/28*12;}
              else  if  (this.identiques(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Hauteur-(dportee+25))/28*12;}
              else if (this.identiques(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Hauteur-(dportee+30))/28*12;}
              else if (this.identiques(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Hauteur-(dportee+35))/28*12;}
              else if (this.identiques(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Hauteur-(dportee+40))/28*12;}
                                                              }
              else {       // CLE DE FA  //base cl� de sol : FA = dportee+76
                if (this.identiques(dportee+110)) { this.Nom = DO; this.Pitch = 48-(this.Hauteur-(dportee+110))/28*12;}
                else if (this.identiques(dportee+115)) { this.Nom = SI;this.Pitch = 47-(this.Hauteur-(dportee+115))/28*12;}
                else if (this.identiques(dportee+120)) { this.Nom = LA;this.Pitch = 45-(this.Hauteur-(dportee+120))/28*12;}
                else  if  (this.identiques(dportee+125)) { this.Nom = SOL;this.Pitch = 43-(this.Hauteur-(dportee+125))/28*12;}
                else if (this.identiques(dportee+95)) { this.Nom = FA;this.Pitch = 53-(this.Hauteur-(dportee+95))/28*12;}
                else if (this.identiques(dportee+100)) { this.Nom = MI;this.Pitch = 52-(this.Hauteur-(dportee+100))/28*12;}
                else if (this.identiques(dportee+105)) { this.Nom = RE;this.Pitch = 50-(this.Hauteur-(dportee+105))/28*12;};
                }
              }
            }

      }





