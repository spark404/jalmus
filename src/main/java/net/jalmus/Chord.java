package net.jalmus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ResourceBundle;

/**
 * <p>Title: Jalmus</p>
 *
 * <p>Description: Free software for sight reading</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author RICHARD Christophe
 * @version 1.0
 */
public class Chord {


          Note tabnotes [] = new Note [3];
          String name;
          int inversion; // 0 no inversion, 1 first inversion, 2 second inversion
          //AudioClip son;

          public Chord (Note n1, Note n2, Note n3, String m, int r){

              this.tabnotes[0] = n1;
              this.tabnotes[1] = n2;
              this.tabnotes[2] = n3;
              this.name = m;
              this.inversion = r;

          }

          public Note getNote(int i){
            return this.tabnotes[i];
          }

          public String getName() {
            return this.name;
          }

          public int getInversion() {
            return this.inversion;
          }


         public void copy (Chord a){
            this.tabnotes[0] = new Note(a.tabnotes[0].getAlteration(),a.tabnotes[0].getNom(),a.tabnotes[0].getHeight(),a.tabnotes[0].getX(),a.tabnotes[0].getPitch());
            this.tabnotes[1] = new Note(a.tabnotes[1].getAlteration(),a.tabnotes[1].getNom(),a.tabnotes[1].getHeight(),a.tabnotes[1].getX(),a.tabnotes[1].getPitch());
            this.tabnotes[2] = new Note(a.tabnotes[2].getAlteration(),a.tabnotes[2].getNom(),a.tabnotes[2].getHeight(),a.tabnotes[2].getX(),a.tabnotes[2].getPitch());
            this.name = a.name;
            this.inversion = a.inversion;

          }

          public int getNoteposition(int pos, Tonality tcourante, ResourceBundle bundle){
            // to modify position of the note in the chord according to alteration
            int nbalt = 0;
            int resultat = 10;


            //pr = this.posreelle(pos);
            if (this.tabnotes[pos].getAlteration() == "") resultat = 0;
            else {
              for (int i=0;i<3;i=i+1){
              if (((this.tabnotes[i].getAlteration() == "#" | this.tabnotes[i].getAlteration() == "b")& !this.tabnotes[i].accidentalInTonality(tcourante,bundle))
                | this.tabnotes[i].getAlteration() == "n")
                nbalt++;
              }

              if (nbalt == 0) resultat = 0;
                else if (nbalt == 1) resultat = 10;
                else if (nbalt == 2) {
                  if (this.inversion == 0) {
                    if (pos == 0) resultat = 10;
                 else if (pos == 1 & this.tabnotes[0].getAlteration() != "") resultat = 20;
                  else if (pos == 1 & this.tabnotes[2].getAlteration() != "") resultat = 10;
                  /*else if (pos == 2 & this.acc[0].getAlteration() != "") resultat = 8;*/
                  else resultat = 20;
                  }
                  else if (this.inversion == 1) {
                    if (pos ==0 &  this.tabnotes[2].getAlteration() != "") resultat = 20;
                    else if (pos == 2 &  this.tabnotes[1].getAlteration() != "") resultat = 20;
                    else resultat = 10;

                  }
                  else if (this.inversion == 2) {
                    if (pos == 1 &  this.tabnotes[0].getAlteration() != "") resultat = 20;
                     else if (pos == 0 &  this.tabnotes[2].getAlteration() != "") resultat = 20;
                    else resultat = 10;
                  }
                }

                  else if (nbalt == 3) {
                    if (this.inversion == 0) {
                    if (pos == 0) resultat = 14;
                    else if (pos == 1) resultat = 24;
                      else if (pos == 2) resultat = 8;
                    }
                    else if (this.inversion == 1) {
                    if (pos == 2) resultat = 24;
                    else if (pos == 0) resultat = 8;
                    else resultat = 14;

                  }
                  else if (this.inversion == 2) {
                    if (pos == 0) resultat = 24;
                    else if (pos == 1) resultat = 8;
                    else resultat = 14;
                  }
                  }
            }

            return resultat;
          }

          public void printname (Graphics g){
               Color red = new Color(242, 179, 112);
            g.setColor(red);
           g.setFont(new Font("Arial",Font.BOLD,17));
              g.drawString(this.name,380-this.name.length()*4,55);


          }

          public void paint( int position, NoteLevel nrlevel,Graphics g, Font f, boolean accordcourant,
                            Component j, int dportee,  ResourceBundle bundle){
              Color c = new Color(147,22,22);

            //if (type == "NORMAL")

            for (int i=0;i<3;i=i+1){

             if (!(i== this.realposition(position)& accordcourant))
               tabnotes[i].paint(nrlevel, g, f, this.getNoteposition(i,nrlevel.getCurrentTonality(), bundle),0, dportee, j, Color.black,bundle);
            }
            if (accordcourant) tabnotes[this.realposition(position)].paint(nrlevel,g,f,this.getNoteposition(this.realposition(position),nrlevel.getCurrentTonality(), bundle),0, dportee, j, c, bundle);
            // we paint tne current note at the end to keep the color red
            if (nrlevel.isLearninggame()) this.printname(g);

            /*else if (type == "LIGNE") // seul l'accord est demand� -> notes en noir
              for (int i=0;i<3;i=i+1)
               acc[i].paint(cle,g,this.decalage(i),Color.black);*/


            }


            public void move(int nb){

              for (int i=0;i<3;i=i+1)
                this.tabnotes[i].setX(this.tabnotes[i].getX()+nb);
            }

            public void updatex(int newx){
           // il faut mettre � jour la coordonn�e de l'accord pour le jeu en ligne

           for (int i=0;i<3;i=i+1)
             this.tabnotes[i].setX(newx);
            }

            public int realposition(int pos){
             int res = 0;

             if (this.inversion == 0) res = pos;
               else if (this.inversion == 1){
                 if (pos == 0) res = 1;
                 else if (pos == 1) res = 2;
                 else if (pos == 2) res = 0;}
                else {
                  if (pos == 0) res = 2;
                else if (pos == 1) res = 0;
                 else if (pos == 2) res = 1;}

                      return res;

            }

            public void convert(NoteLevel nrlevel){
              // convertit a chord to his inversion

              double tmp;

              if (nrlevel.isChordtypeInversion()) {
                tmp = Math.random();
                this.inversion = 0;
                if (tmp < 0.33) { // first inversion
                  this.inversion = 1;
                  this.tabnotes[0].setHeight(this.tabnotes[0].getHeight() - 35);
                  this.tabnotes[0].setPitch(this.tabnotes[0].getPitch() + 12);
                }
                else if (tmp > 0.33 & tmp < 0.66) { // second inversion
                  this.inversion = 2;
                  this.tabnotes[0].setHeight(this.tabnotes[0].getHeight() - 35);
                  this.tabnotes[0].setPitch(this.tabnotes[0].getPitch() + 12);
                  this.tabnotes[1].setHeight(this.tabnotes[1].getHeight() - 35);
                  this.tabnotes[1].setPitch(this.tabnotes[1].getPitch() + 12);
                }
              }

            }

        }


