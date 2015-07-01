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
           if ((!this.Alteration.equals("") & !this.accidentalInTonality(nrlevel.getCurrentTonality(),b)) | (this.Alteration.equals("n")))
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
                 g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i); // dessine la port�e en dessous de la port�e normale

                 }
               }

               else  if (this.Height<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Height+5; i=i-10){

                   if (i != this.Height+5) g.setColor(Color.black);
                   else g.setColor(couleur);
                   g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i);  // dessine la portee en dessus de la port�e normale
                 }
               };
             }

             // CAS DE LA DOUBLE CLE SOL ET FA

             else if( nrlevel.isCurrentKeyBoth()) {

               // cas de la cl� de sol
               if (this.Height>=dportee+45 & this.Height <= dportee+55) { // du DO jusqu'au LA en dessous de la port�e
                 for(i=dportee+50; i<=this.Height+5; i=i+10){
                 g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Height<=dportee-15) {  // <LA au dessus de la port�e en cl� de sol
                 for(i=dportee-10; i>=this.Height+5; i=i-10){
                 g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i); }       // dessine la portee en dessus de la port�e normale
               }


               // cas de la cl� de fa
               else if (this.Height>=dportee+135) {  // � partie du MI en dessous de la port�e
                 for(i=dportee+140; i<=this.Height+5; i=i+10){
                 g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i); }       // dessine la port�e en dessous de la port�e normale
               }
               else if (this.Height<=dportee+75 & this.Height >=dportee+60) {
                 for(i=dportee+80; i>=this.Height+5; i=i-10){
                 g.drawLine(this.X-2+decalagen,i,this.X+18+decalagen, i); }      // dessine la portee en dessus de la port�e normale
               };
             }
           }



           public boolean samenoteHeight(int base){
           int x;
            boolean b = false;



            for (x=0;x<=3;x++){ //28 = 8 notes entre 2 notes identiques * 4 entre chaque note
              if ((this.Height+x*35==base) | (this.Height-x*35==base))  b = true;                                                   }
                return b;
                }
           

           public boolean samenotePitch(int pitchbase){
           int x;
            boolean b = false;



            for (x=0;x<=9;x++){ //28 = 8 notes entre 2 notes identiques * 4 entre chaque note
              if ((this.Pitch+x*12==pitchbase) | (this.Pitch-x*12==pitchbase))  b = true;                                                   }
                return b;
                }

          public boolean accidentalInTonality(Tonality t, ResourceBundle bundle){

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


          public void updateAccidental( NoteLevel nrlevel, ResourceBundle b){
            int alt = 0;
            double tmp = 0;

            if (!nrlevel.isNotesgame()) {
              tmp = Math.random();

              if (this.accidentalInTonality(nrlevel.getCurrentTonality(), b)) {
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
            else if (this.accidentalInTonality(nrlevel.getCurrentTonality(),b))
              this.Alteration = nrlevel.getCurrentTonality().Alteration;

            else this.Alteration = "";

//System.out.println(this.Alteration);

            // MODIFY PITCH ACCORDING TO ACCIDENTAL
        if (this.Alteration.equals("#")) alt = 1;
          else  if (this.Alteration.equals("b")) alt = -1;
          else alt = 0;

          this.Pitch = this.Pitch+alt;
                }


         public void updateAccidentalInChord(Tonality t, int pitch0, int nnote, ResourceBundle b){ //pour les accords
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
                        if (this.accidentalInTonality(t,b)) this.Alteration = "n";
                      }
                    else //passer a tierce majeure
                      this.Alteration = "#";
                    }



                    else if (this.Pitch-pitch0 == 4){
                      if (tmp<0.4 | t.Alteration.equals("#")) { //laisser tierce majeure
                        if (this.accidentalInTonality(t,b)) this.Alteration = "n";
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
                        if (this.accidentalInTonality(t,b)) this.Alteration = "n";
                      }
                      else this.Alteration = "#";

                    }

                    else if (this.Pitch-pitch0 == 7){
                      if (tmp<0.1 & t.Alteration.equals("b")) this.Alteration = "b"; // quinte diminuee
                      else  if (tmp<0.2 & t.Alteration.equals("#")) this.Alteration = "#"; // quinte augmentee

                       else if (this.accidentalInTonality(t,b)) this.Alteration = "n";
                    }


                    else if (this.Pitch-pitch0 == 8){
                      if (tmp<0.4 | t.Alteration.equals("#")) {//laisser quinte augmentee
                        if (this.accidentalInTonality(t,b)) this.Alteration = "n";
                      }
                      else this.Alteration = "b";

                    }

                  }

//System.out.println(this.Alteration);

                  // MODIFY PITCH ACCORDING TO ACCIDENTAL
                  if (this.Alteration.equals("#")) alt = 1;
                  else  if (this.Alteration.equals("b")) alt = -1;
                  else alt = 0;

                  this.Pitch = this.Pitch+alt;

                 // System.out.println(this.Pitch-pitch0+" APRES");


                }





          public void updateNote(NoteLevel nrlevel, int dportee, ResourceBundle bundle){

            String DO = bundle.getString("_do");
            String RE = bundle.getString("_re");
            String MI = bundle.getString("_mi");
            String FA = bundle.getString("_fa");
            String SOL = bundle.getString("_sol");
            String LA = bundle.getString("_la");
            String SI = bundle.getString("_si");

            if (nrlevel.isCurrentKeyTreble()){//Trebble key

              if (this.samenoteHeight(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Height-(dportee+10))/28*12;}
              else if (this.samenoteHeight(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Height-(dportee+15))/28*12;}
              else if (this.samenoteHeight(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Height-(dportee+20))/28*12;}
              else  if  (this.samenoteHeight(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Height-(dportee+25))/28*12;}
              else if (this.samenoteHeight(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Height-(dportee+30))/28*12;}
              else if (this.samenoteHeight(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Height-(dportee+35))/28*12;}
              else if (this.samenoteHeight(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Height-(dportee+40))/28*12;}
              }

            else if (nrlevel.isCurrentKeyBass()){ // Bass key

              if (this.samenoteHeight(dportee+20)) { this.Nom = DO; this.Pitch = 48-(this.Height-(dportee+20))/28*12;}
              else if (this.samenoteHeight(dportee+25)) { this.Nom = SI;this.Pitch = 47-(this.Height-(dportee+25))/28*12;}
              else if (this.samenoteHeight(dportee+30)) { this.Nom = LA;this.Pitch = 45-(this.Height-(dportee+30))/28*12;}
              else  if  (this.samenoteHeight(dportee+35)) { this.Nom = SOL;this.Pitch = 43-(this.Height-(dportee+35))/28*12;}
              else if (this.samenoteHeight(dportee+5)) { this.Nom = FA;this.Pitch = 53-(this.Height-(dportee+5))/28*12;}
              else if (this.samenoteHeight(dportee+10)) { this.Nom = MI;this.Pitch = 52-(this.Height-(dportee+10))/28*12;}
              else if (this.samenoteHeight(dportee+15)) { this.Nom = RE;this.Pitch = 50-(this.Height-(dportee+15))/28*12;};
              }

            else if (nrlevel.isCurrentKeyBoth()){
              if (this.Height<=dportee+55){   // Trebble key
                if (this.samenoteHeight(dportee+10)) { this.Nom = DO; this.Pitch = 72-(this.Height-(dportee+10))/28*12;}
              else if (this.samenoteHeight(dportee+15)) { this.Nom = SI; this.Pitch = 71-(this.Height-(dportee+15))/28*12;}
              else if (this.samenoteHeight(dportee+20)) { this.Nom = LA; this.Pitch = 69-(this.Height-(dportee+20))/28*12;}
              else  if  (this.samenoteHeight(dportee+25)) { this.Nom = SOL;this.Pitch = 67-(this.Height-(dportee+25))/28*12;}
              else if (this.samenoteHeight(dportee+30)) { this.Nom = FA;this.Pitch = 65-(this.Height-(dportee+30))/28*12;}
              else if (this.samenoteHeight(dportee+35)) { this.Nom = MI;this.Pitch = 64-(this.Height-(dportee+35))/28*12;}
              else if (this.samenoteHeight(dportee+40)) { this.Nom = RE;this.Pitch = 62-(this.Height-(dportee+40))/28*12;}
                                                              }
              else {       //  Bass key
                if (this.samenoteHeight(dportee+110)) { this.Nom = DO; this.Pitch = 48-(this.Height-(dportee+110))/28*12;}
                else if (this.samenoteHeight(dportee+115)) { this.Nom = SI;this.Pitch = 47-(this.Height-(dportee+115))/28*12;}
                else if (this.samenoteHeight(dportee+120)) { this.Nom = LA;this.Pitch = 45-(this.Height-(dportee+120))/28*12;}
                else  if  (this.samenoteHeight(dportee+125)) { this.Nom = SOL;this.Pitch = 43-(this.Height-(dportee+125))/28*12;}
                else if (this.samenoteHeight(dportee+95)) { this.Nom = FA;this.Pitch = 53-(this.Height-(dportee+95))/28*12;}
                else if (this.samenoteHeight(dportee+100)) { this.Nom = MI;this.Pitch = 52-(this.Height-(dportee+100))/28*12;}
                else if (this.samenoteHeight(dportee+105)) { this.Nom = RE;this.Pitch = 50-(this.Height-(dportee+105))/28*12;};
                }
              }
            }

          

          public void updateNotePitch(NoteLevel nrlevel, int dportee, ResourceBundle bundle){

            String DO = bundle.getString("_do");
            String RE = bundle.getString("_re");
            String MI = bundle.getString("_mi");
            String FA = bundle.getString("_fa");
            String SOL = bundle.getString("_sol");
            String LA = bundle.getString("_la");
            String SI = bundle.getString("_si");
            
            Integer heigth2staff = 0;
            if (nrlevel.isCurrentKeyBoth()) heigth2staff = 90; else heigth2staff = 0;

            if (nrlevel.isCurrentKeyTreble()|| (nrlevel.isCurrentKeyBoth() & this.Pitch >= 57)){//base cl� de sol : SOL = dportee+25

              if (this.samenotePitch(0)) { this.Nom = DO; this.Height = (60-this.Pitch)*35/12 + dportee+45;  
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
              
              else if (this.samenotePitch(1)) { 
            	  if (nrlevel.getCurrentTonality().isflat()) {this.Nom = RE; this.Height = (61-this.Pitch)*35/12 + dportee+40;  
            	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }
              		else {this.Nom = DO; this.Height = (61-this.Pitch)*35/12 + dportee+45; 
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }          		
              }	
              
              else   if (this.samenotePitch(2)) { this.Nom = RE; this.Height = (62-this.Pitch)*35/12 + dportee+40;  
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                         
              
              else if (this.samenotePitch(3)) { 
            	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = RE; this.Height = (63-this.Pitch)*35/12 + dportee+40;  
            	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
              		else {this.Nom = MI; this.Height = (63-this.Pitch)*35/12 + dportee+35; 
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
              }	
              
              else   if (this.samenotePitch(4)) { this.Nom = MI; this.Height = (64-this.Pitch)*35/12 + dportee+35;  
    					if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
           
              else   if (this.samenotePitch(5)) { this.Nom = FA; this.Height = (65-this.Pitch)*35/12 + dportee+30;  
    					if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
           
              else if (this.samenotePitch(6)) { 
            	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = FA; this.Height = (66-this.Pitch)*35/12 + dportee+30;  
            	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
              		else {this.Nom = SOL; this.Height = (66-this.Pitch)*35/12 + dportee+25; 
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
              }	
              
              else   if (this.samenotePitch(7)) { this.Nom = SOL; this.Height = (67-this.Pitch)*35/12 + dportee+25;  
  						if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
         
              else if (this.samenotePitch(8)) { 
            	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = SOL; this.Height = (68-this.Pitch)*35/12 + dportee+25;  
            	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
              		else {this.Nom = LA; this.Height = (68-this.Pitch)*35/12 + dportee+20; 
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
              }	
             
              else   if (this.samenotePitch(9)) { this.Nom = LA; this.Height = (69-this.Pitch)*35/12 + dportee+20;  
				if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
              
              else if (this.samenotePitch(10)) { 
            	  if (nrlevel.getCurrentTonality().isflat()) {this.Nom = SI; this.Height = (70-this.Pitch)*35/12 + dportee+15;  
            	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }
              		else {this.Nom = LA; this.Height = (70-this.Pitch)*35/12 + dportee+20; 
              			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }          		
              }	
              
              else   if (this.samenotePitch(11)) { this.Nom = SI; this.Height = (71-this.Pitch)*35/12 + dportee+15;  
				if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                        
              
  }

            else if (nrlevel.isCurrentKeyBass() || (nrlevel.isCurrentKeyBoth() & this.Pitch < 57)){
            


                    if (this.samenotePitch(0)) { this.Nom = DO; this.Height = (60-this.Pitch)*35/12 + dportee-15+heigth2staff;  
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                    
                    else if (this.samenotePitch(1)) { 
                  	  if (nrlevel.getCurrentTonality().isflat()) {this.Nom = RE; this.Height = (61-this.Pitch)*35/12 + dportee-20+heigth2staff;  
                  	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }
                    		else {this.Nom = DO; this.Height = (61-this.Pitch)*35/12 + dportee-15+heigth2staff; 
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }          		
                    }	
                    
                    else   if (this.samenotePitch(2)) { this.Nom = RE; this.Height = (62-this.Pitch)*35/12 + dportee-20+heigth2staff;  
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                               
                    
                    else if (this.samenotePitch(3)) { 
                  	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = RE; this.Height = (63-this.Pitch)*35/12 + dportee-20+heigth2staff;  
                  	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
                    		else {this.Nom = MI; this.Height = (63-this.Pitch)*35/12 + dportee-25+heigth2staff; 
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
                    }	
                    
                    else   if (this.samenotePitch(4)) { this.Nom = MI; this.Height = (64-this.Pitch)*35/12 + dportee-25+heigth2staff;  
          					if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                 
                    else   if (this.samenotePitch(5)) { this.Nom = FA; this.Height = (65-this.Pitch)*35/12 + dportee-30+heigth2staff;  
          					if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                 
                    else if (this.samenotePitch(6)) { 
                  	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = FA; this.Height = (66-this.Pitch)*35/12 + dportee-30+heigth2staff;  
                  	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
                    		else {this.Nom = SOL; this.Height = (66-this.Pitch)*35/12 + dportee-35+heigth2staff; 
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
                    }	
                    
                    else   if (this.samenotePitch(7)) { this.Nom = SOL; this.Height = (67-this.Pitch)*35/12 + dportee-35+heigth2staff;  
        						if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
               
                    else if (this.samenotePitch(8)) { 
                  	  if (nrlevel.getCurrentTonality().issharp()) {this.Nom = SOL; this.Height = (68-this.Pitch)*35/12 + dportee-35+heigth2staff;  
                  	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }
                    		else {this.Nom = LA; this.Height = (68-this.Pitch)*35/12 + dportee-40+heigth2staff; 
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }          		
                    }	
                   
                    else   if (this.samenotePitch(9)) { this.Nom = LA; this.Height = (69-this.Pitch)*35/12 + dportee-40+heigth2staff;  
      							if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                    
                    else if (this.samenotePitch(10)) { 
                  	  if (nrlevel.getCurrentTonality().isflat()) {this.Nom = SI; this.Height = (70-this.Pitch)*35/12 + dportee-45+heigth2staff;  
                  	  		if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "b"; }
                    		else {this.Nom = LA; this.Height = (70-this.Pitch)*35/12 + dportee-40+heigth2staff; 
                    			if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = ""; else this.Alteration = "#"; }          		
                    }	
                    
                    else   if (this.samenotePitch(11)) { this.Nom = SI; this.Height = (71-this.Pitch)*35/12 + dportee-45+heigth2staff;  
      				if (accidentalInTonality(nrlevel.getCurrentTonality(), bundle)) this.Alteration = "n"; else this.Alteration = ""; }
                              
                    	
            	
           }


            }

          
      }





