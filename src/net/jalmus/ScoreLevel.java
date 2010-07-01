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

public String getClef() {
	    return this.currentclef;
	  }

  public boolean isCurrentclefTreble() {
    return this.currentclef.equals("treble");
  }

  public boolean isCurrentclefBass() {
    return this.currentclef .equals("bass");
  }

  /*****************************************/
  public boolean getRandomtonality(){
    return this.randomtonality;
  }

  public void setRandomtonality(boolean b){
   this.randomtonality = b;
 }


   public void setCurrentTonality(Tonality t) {
  this.currenttonality = t;
 }

   public Tonality getCurrentTonality(){
     return this.currenttonality;
   }
 /*****************************************/
  
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
	
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  1) pitchtab[1]=66;
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  2) pitchtab[5]=73;
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  3) pitchtab[2]=68;
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  4) pitchtab[6]=75;
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  5) pitchtab[3]=70;
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  6) { pitchtab[0]=65; pitchtab[7]=77;}
	if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  7) pitchtab[4]=72;
	
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  1) pitchtab[4]=70;
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  2) {pitchtab[0]=63; pitchtab[7]=75;}
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  3) pitchtab[3]=68;
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  4) pitchtab[6]=73;
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  5) pitchtab[2]=66;
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  6) pitchtab[5]=71;
	if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  7) pitchtab[1]=64;
		
	
	
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
		
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  1) pitchtab[6]=42;
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  2) pitchtab[3]=37;
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  3) {pitchtab[0]=32;  pitchtab[7]=44; }
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  4) pitchtab[4]=39;
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  5) pitchtab[1]=34;
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  6) pitchtab[5]=41; 
		if (this.currenttonality.issharp() && this.currenttonality.getNbalt() >=  7) pitchtab[2]=36;
		
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  1) pitchtab[2]=34;
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  2) pitchtab[5]=39;
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  3) pitchtab[1]=32;
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  4) pitchtab[4]=37;
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  5) {pitchtab[0]=30; pitchtab[7]=42;}
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  6) pitchtab[3]=35;
		if (this.currenttonality.isflat() && this.currenttonality.getNbalt() >=  7) pitchtab[6]=40;
			
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