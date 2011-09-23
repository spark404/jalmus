/**
 * 
 */
package net.jalmus;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author christophe
 *
 */
public class RhythmAnswer {
	  int posx; // position of the point
      int posy;
      boolean goodnote;
      int result; // 0 good rhythm, 1 false key pressed, 2 false key released, 3 show rythm
	/**
	 * 
	 */
	public RhythmAnswer(int x, int y, boolean b, int result) {
		this.posx = x;
		this.posy = y;
		this.goodnote = b;
		this.result = result;

	}
	
	public void init() {
		this.posx = -1;
		this.posy = -1;
		this.result = 0;

	}
	
	public boolean isnull(){
		return this.posx == -1;
	}
	
	public boolean allgood(){
		return (this.goodnote  && this.result == 0);
	}
	
	public boolean badnote(){
		return (!this.goodnote );
	}
	
	public boolean badrhythm(){
		return ( this.result == 1);
		}
	
    public void setPosx(int x) {
        this.posx = x;
      }

      public int getPosx() {
        return this.posx;
      }
      
      public void setPosy(int y) {
          this.posx = y;
        }

        public int getPosy() {
          return this.posy;
        }
	
        public boolean isgoodnote() {
            return this.result == 0;
          }
	
        public void paint(Graphics g){
        	  Color cr = new Color(238, 0, 0);
        	  Color co = new Color(238, 153, 0);
              Color cg = new Color(152, 251, 152);
              Color ct = new Color(0, 0, 0);
              if (result == 0) g.setColor(cg);
              else if (result == 1) g.setColor(cr);
              else if (result == 2) g.setColor(co);
              else if (result == 3) g.setColor(ct);
              if (this.goodnote) g.fillOval(this.posx, this.posy-5, 10,10);
              else {
            	 // g.drawRect(this.posx-5, this.posy-5, 8, 4);
            	  Font f = new Font ("Sanserif", Font.BOLD, 16);
            	  g.setFont (f);
            	  g.drawString("X", this.posx, this.posy+10);
              }
        }
}
