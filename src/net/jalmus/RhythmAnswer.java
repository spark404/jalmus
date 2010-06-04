/**
 * 
 */
package net.jalmus;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author christophe
 *
 */
public class RhythmAnswer {
	  int posx; // position of the point
      int posy;
      int result; // 0 good rhythm, 1 false key pressed, 2 false key relesad
	/**
	 * 
	 */
	public RhythmAnswer(int x, int y, int result) {
		this.posx = x;
		this.posy = y;
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
	
        public boolean isgood() {
            return this.result == 0;
          }
	
        public void paint(Graphics g){
        	  Color cr = new Color(238, 0, 0);
        	  Color co = new Color(238, 153, 0);
              Color cg = new Color(152, 251, 152);
              if (result == 0) g.setColor(cg);
              else if (result == 1) g.setColor(cr);
              else g.setColor(co);
              g.fillOval(this.posx, this.posy, 10,10);
        }
}
