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
      boolean good; // 
	/**
	 * 
	 */
	public RhythmAnswer(int x, int y, boolean good) {
		this.posx = x;
		this.posy = y;
		this.good = good;

	}
	
	public void init() {
		this.posx = -1;
		this.posy = -1;
		this.good = true;

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
            return this.good;
          }
	
        public void paint(Graphics g){
        	  Color cr = new Color(238, 0, 0);
              Color cg = new Color(152, 251, 152);
              if (good) g.setColor(cg);
              else g.setColor(cr);
              g.fillOval(this.posx, this.posy, 10,10);
        }
}
