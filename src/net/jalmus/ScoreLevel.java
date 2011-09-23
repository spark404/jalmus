package net.jalmus;

public class ScoreLevel {
	 boolean ronde;
	  boolean blanche;
	  boolean noire; 
	  boolean croche;
	  boolean silence;
	  String currentKey;
	  Tonality currenttonality;
	  boolean randomtonality;
	  int[] pitchtab = new int [20];


public ScoreLevel() {
    this.ronde = true;
    this.blanche = true;
    this.noire = false;
    this.croche = false;
    this.silence = true;
    this.currentKey = "treble";
    this.randomtonality = false;
    this.currenttonality = new Tonality(0, "");
    for (int i=0; i<pitchtab.length-1; i++) {
    	pitchtab[i] = 0;
    }
  }

public void setCurrentKey(String s) {
    this.currentKey = s;
  }

public String getKey() {
	    return this.currentKey;
	  }

  public boolean isCurrentKeyTreble() {
    return this.currentKey.equals("treble");
  }

  public boolean isCurrentKeyBass() {
    return this.currentKey .equals("bass");
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

  if (this.isCurrentKeyTreble()){
	
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
	
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  1) pitchtab[1]=66;
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  2) pitchtab[5]=73;
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  3) pitchtab[2]=68;
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  4) pitchtab[6]=75;
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  5) pitchtab[3]=70;
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  6) { pitchtab[0]=65; pitchtab[7]=77;}
	if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  7) pitchtab[4]=72;
	
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  1) pitchtab[4]=70;
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  2) {pitchtab[0]=63; pitchtab[7]=75;}
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  3) pitchtab[3]=68;
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  4) pitchtab[6]=73;
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  5) pitchtab[2]=66;
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  6) pitchtab[5]=71;
	if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  7) pitchtab[1]=64;
  }
	
  else if (this.isCurrentKeyBass()){
		
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

		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  1) pitchtab[6]=42;
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  2) pitchtab[3]=37;
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  3) {pitchtab[0]=32;  pitchtab[7]=44; }
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  4) pitchtab[4]=39;
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  5) pitchtab[1]=34;
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  6) pitchtab[5]=41; 
		if (this.currenttonality.issharp() && this.currenttonality.getAlterationsNumber() >=  7) pitchtab[2]=36;
		
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  1) pitchtab[2]=34;
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  2) pitchtab[5]=39;
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  3) pitchtab[1]=32;
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  4) pitchtab[4]=37;
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  5) {pitchtab[0]=30; pitchtab[7]=42;}
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  6) pitchtab[3]=35;
		if (this.currenttonality.isflat() && this.currenttonality.getAlterationsNumber() >=  7) pitchtab[6]=40;

		}
	
}
public int randomPitch(){
	int pitch = 71;
	int noteIndex = 0;
	
	double rand=Math.random();
	
	for (int i=0; i<pitchtab.length-1; i++) {
		if (pitchtab[i] == 0) {noteIndex = i; break; }
	}
	
	pitch =  pitchtab[(int) (noteIndex * rand)];
	System.out.println("noteIndex = " + noteIndex + ", pitch = " + pitch);
	  
	return pitch;
}

public void updateRhythm(boolean r, boolean b, boolean n, boolean c, boolean s) {
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