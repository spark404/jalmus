package net.jalmus;

public class ScoreLevel {
	 boolean ronde;
	  boolean blanche;
	  boolean noire; 
	  boolean croche;
	  boolean silence;
	  String currentclef;
	  Tonality currenttonality;
	  boolean randomtonality;
	  int[] pitchtab = new int [20];


public ScoreLevel() {
    this.ronde = true;
    this.blanche = true;
    this.noire = false;
    this.croche = false;
    this.silence = false;
    this.currentclef = "treble";
    this.randomtonality = false;
    this.currenttonality = new Tonality(0, "");
    for (int i=0; i<pitchtab.length-1; i++) {
    	pitchtab[i] = 0;
    }
  }

public void setCurrentclef(String s) {
    this.currentclef = s;
  }

  public boolean isCurrentclefTreble() {
    return this.currentclef.equals("treble");
  }

  public boolean isCurrentclefBass() {
    return this.currentclef .equals("bass");
  }

  
public void initpitchtab(){

	if (this.isCurrentclefTreble()){
	
	for (int i=0; i<pitchtab.length-1; i++) {
		   pitchtab[i] = 0;
	}

	pitchtab[0]=64;
	pitchtab[1]=65;
	pitchtab[2]=67;
	pitchtab[3]=69;
	pitchtab[4]=71;
	pitchtab[5]=72;
	pitchtab[6]=74;
	pitchtab[7]=76;
	
	}
	
	else if (this.isCurrentclefBass()){
		
		for (int i=0; i<pitchtab.length-1; i++) {
			   pitchtab[i] = 0;
		}

		pitchtab[0]=31;
		pitchtab[1]=33;
		pitchtab[2]=35;
		pitchtab[3]=36;
		pitchtab[4]=38;
		pitchtab[5]=40;
		pitchtab[6]=41;
		pitchtab[7]=43;
		
		}
	
}
public int randompitch(){
	int pitch = 71;
	int nbnotes = 0;
	boolean exit = false;
	
	double rand=Math.random();
	
	  for (int i=0; i<pitchtab.length-1 && !exit; i++) {
	    	if (pitchtab[i] == 0) {nbnotes = i; exit = true;}
	    }
	
	  pitch =  pitchtab[(int) (nbnotes * rand)];
	  System.out.println(nbnotes+ "nbnotes" + pitch + " pitch = " + pitch);
	  
	return pitch;
}

public void updaterhythm(boolean r, boolean b, boolean n, boolean c, boolean s) {
    this.ronde = r;
    this.blanche = b;
    this.noire = n;
    this.croche = c;
    this.silence = s;
  }


public boolean getRonde() {
    return this.ronde;
  }

  public boolean getBlanche() {
    return this.blanche;
  }

  public boolean getNoire() {
    return this.noire;
  }

  public boolean getCroche() {
    return this.croche;
  }

  public boolean getSilence() {
    return this.silence;
  }
  
}