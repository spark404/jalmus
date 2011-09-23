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
public class Interval {


        Note interv [] = new Note [2];
        String name;


        public Interval(Note n1, Note n2, String m) {
          this.interv[0] = n1;
          this.interv[1] = n2;
          this.name = m;
        }

        public Note getNote(int i) {
          return this.interv[i];
        }

        public String getName() {
          return this.name;
        }


        public void copy(Interval a) {
          this.interv[0] = new Note(a.interv[0].getAlteration(), a.interv[0].getNom(),
                                    a.interv[0].getHeight(), a.interv[0].getX(),
                                    a.interv[0].getPitch());
          this.interv[1] = new Note(a.interv[1].getAlteration(), a.interv[1].getNom(),
                                    a.interv[1].getHeight(), a.interv[1].getX(),
                                    a.interv[1].getPitch());
          this.name = a.name;
        }

        public void move(int nb) {
          for (int i = 0; i < 2; i = i + 1)
            this.interv[i].setX(this.interv[i].getX() + nb);
        }

        public void printname(Graphics g) {
        Color red = new Color(242, 179, 112);
          g.setColor(red);
          g.setFont(new Font("Arial", Font.BOLD, 16));
          g.drawString(this.name, 380 - this.name.length() * 4, 55);
        }



        public void paint( int position, NoteLevel nrlevel,
                          Graphics g, Font f, int dportee,
                          ResourceBundle bundle, boolean intervcourant,
                          Component j) {

          Color c = new Color(147, 22, 22);

          if (position == 0 & intervcourant)
            interv[0].paint(nrlevel, g, f, 9, 0, dportee, j, c, bundle);
          else
            interv[0].paint(nrlevel, g, f, 9, 0, dportee, j, Color.black, bundle);

          if (position == 1 & intervcourant)
            interv[1].paint(nrlevel, g, f, -19, 28, dportee, j, c, bundle);
          else
            interv[1].paint(nrlevel, g, f, -19, 28, dportee, j, Color.black, bundle);

        //  if ( (nrlevel.isNormalgame() | nrlevel.isLearninggame()) |
         //     (nrlevel.isInlinegame() & intervcourant))
          if ( nrlevel.isLearninggame()) // name only for learning exercise
            this.printname(g);
        }



        public void updatex(int newx) {
          // to update the abscissa  of interval for the game in line

          for (int i = 0; i < 2; i = i + 1)
            this.interv[i].setX(newx);
        }




      }
