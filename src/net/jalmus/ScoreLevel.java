package net.jalmus;

public class ScoreLevel {
	boolean whole;
	boolean half;
	boolean quarter; 
	boolean eighth;
	boolean silence;
	boolean triplet;
	String currentKey;
	Tonality currenttonality;
	boolean randomtonality;
	int[] pitchtab = new int [8];


public ScoreLevel() {
    this.whole = true;
    this.half = true;
    this.quarter = false;
    this.eighth = false;
    this.silence = true;
    this.triplet = false;
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

  for (int i=0; i<pitchtab.length; i++)
	  pitchtab[i] = 0;
	
  if (this.isCurrentKeyTreble()){

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
	//int noteIndex = 0;
	
/*
	for (int i=0; i<pitchtab.length-1; i++) {
		if (pitchtab[i] == 0) {noteIndex = i; break; }
	}
*/
	pitch =  pitchtab[(int) (pitchtab.length * Math.random())];
	System.out.println("noteIndex = " + pitchtab.length + ", pitch = " + pitch);
	  
	return pitch;
}

public int tripletRandomPitch(int basePitch) {
	int baseIndex = 0;
	int delta = 4; // within +2 and -2 tones from basePitch
	int shift = -2;
	int pitch = 0;
	for (int i = 0; i < pitchtab.length; i++)
		if (pitchtab[i] == basePitch) {
			baseIndex = i;
			break;
		}
	if (baseIndex == 0) {
		delta = 2; // only +2
		shift = 2;
	}
	else if (baseIndex == pitchtab.length - 1) {
		delta = 2; // only -2
		shift = -2;
	}
	else if (baseIndex == 1) {
		delta = 3; // only -1/+2
		shift = -1;
	}
	else if (baseIndex == pitchtab.length - 2) {
		delta = 3; // only -2/+1
		shift = -2;
	}
	
	int randIndex = baseIndex + shift + (int)(Math.random() * delta);
	System.out.println("Triplet: base: " + basePitch + ", baseIndex: " + baseIndex + ", randIdx: " + randIndex);
	
	pitch = pitchtab[randIndex];
	return pitch;
}

public void updateRhythm(boolean r, boolean b, boolean n, boolean c, boolean s, boolean t) {
    this.whole = r;
    this.half = b;
    this.quarter = n;
    this.eighth = c;
    this.silence = s;
    this.triplet = t;
  }


public boolean getWholeNote() {
    return this.whole;
  }

  public boolean getHalfNote() {
    return this.half;
  }

  public boolean getQuarterNote() {
    return this.quarter;
  }

  public boolean getEighthNote() {
    return this.eighth;
  }

  public boolean getSilence() {
    return this.silence;
  }

  public boolean getTriplet() {
	    return this.triplet;
} 
  
}