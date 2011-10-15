package net.jalmus;

import java.util.ArrayList;

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
	//int[] pitchtab = new int [8];
	ArrayList<Integer> pitchtab = new ArrayList<Integer>(); 

  /*                          C3  D3  E3  F3  G3  A3  B3  */
  /*                         ---------------------------- */
  char[][] sharpsMatrix = { { 60, 62, 64, 65, 67, 69, 71 },
						    {  0,  0,  0,  1,  0,  0,  0 }, // 1 alteration
						    {  1,  0,  0,  1,  0,  0,  0 }, // 2 alterations
						    {  1,  0,  0,  1,  1,  0,  0 }, // 3 alterations
						    {  1,  1,  0,  1,  1,  0,  0 }, // 4 alterations
						    {  1,  1,  0,  1,  1,  1,  0 }, // 5 alterations
						    {  1,  1,  1,  1,  1,  1,  0 }, // 6 alterations
						    {  1,  1,  1,  1,  1,  1,  1 }  // 7 alterations
						  };
  /*                         C3  D3  E3  F3  G3  A3  B3  */
  /*                        ---------------------------- */
  char[][] flatsMatrix = { { 60, 62, 64, 65, 67, 69, 71 },
	     				   {  0,  0,  0,  0,  0,  0,  1 }, // 1 alteration
		     			   {  0,  0,  1,  0,  0,  0,  1 }, // 2 alterations
		     			   {  0,  0,  1,  0,  0,  1,  1 }, // 3 alterations
		     			   {  0,  1,  1,  0,  0,  1,  1 }, // 4 alterations
		     			   {  0,  1,  1,  0,  1,  1,  1 }, // 5 alterations
		     			   {  1,  1,  1,  0,  1,  1,  1 }, // 6 alterations
		     			   {  1,  1,  1,  1,  1,  1,  1 }  // 7 alterations
		   				 };

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
    pitchtab.clear();
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
  
  public void initpitchtab(int mode) {

	  int i = 0, noteIdx = 0, notesNum = 15;
	  int octaveOffset = 0;
	  int altIndex = this.currenttonality.getAlterationsNumber();
	  pitchtab.clear();

	  if (mode == 0) notesNum = 9;

	  if (this.isCurrentKeyBass()) {
		  octaveOffset = -2;
		  if (mode == 0) noteIdx = 4; // starts from G
	  }
	  else if (this.isCurrentKeyTreble()) {
		  octaveOffset = 0;
		  if (mode == 0) noteIdx = 2; // starts from E
	  }

	  for (i = 0; i < notesNum; i++) {
		int alteration = 0;
		if (this.currenttonality.issharp())
			alteration = sharpsMatrix[altIndex][noteIdx];
		else if (this.currenttonality.isflat())
			alteration =  0 - flatsMatrix[altIndex][noteIdx];

		pitchtab.add(sharpsMatrix[0][noteIdx] + (octaveOffset * 12) + alteration);
		
		if (noteIdx == 6) {
			octaveOffset++;
			noteIdx = 0;
		}
		else
			noteIdx++;
	  }

	  for (i = 0; i < pitchtab.size(); i++)
		  System.out.println("pitchtab #" + i + ": " + pitchtab.get(i));
  }
  
  public int getYpos(int pitch) {
	int ypos = 43; // Y position of C3 on treble key
    int octave = 0;
    int octaveOffset = 0;
    int altIndex = this.currenttonality.getAlterationsNumber();
	
    if (this.isCurrentKeyBass()) {
    	ypos = 53; // Y position of C1 on bass key
    	octave = -2;
    	if (pitch > 47) { octave = -1; octaveOffset = 35; }
	}
	else if (this.isCurrentKeyTreble() && pitch > 71) { 
		octave = 1; octaveOffset = 35;
	}

    for (int i = 0; i < 7; i++) {
    	int alteration = 0;
    	if (this.currenttonality.issharp())
    		alteration = sharpsMatrix[altIndex][i];
    	else if (this.currenttonality.isflat())
			alteration =  0 - flatsMatrix[altIndex][i];
    	if (pitch == (sharpsMatrix[0][i] + (octave * 12) + alteration)) {
    	    ypos = ypos - octaveOffset - (i * 5);
    	    //System.out.println("Pitch: " + pitch + ", Octave = " + octave + ", i = " + i + ", ypos= " + ypos);
    		return ypos;
    	}
    }

	return ypos;
  }
  
  public int randomPitch(){
	int pitch = 71;

	pitch =  pitchtab.get((int) (pitchtab.size() * Math.random()));
	//System.out.println("New random pitch = " + pitch);
	  
	return pitch;
  }

  public int tripletRandomPitch(int basePitch) {
	int baseIndex = 0;
	int delta = 4; // within +2 and -2 tones from basePitch
	int shift = -2;
	int pitch = 0;
	for (int i = 0; i < pitchtab.size(); i++)
		if (pitchtab.get(i) == basePitch) {
			baseIndex = i;
			break;
		}
	if (baseIndex == 0) {
		delta = 2; // only +2
		shift = 2;
	}
	else if (baseIndex == pitchtab.size() - 1) {
		delta = 2; // only -2
		shift = -2;
	}
	else if (baseIndex == 1) {
		delta = 3; // only -1/+2
		shift = -1;
	}
	else if (baseIndex == pitchtab.size() - 2) {
		delta = 3; // only -2/+1
		shift = -2;
	}
	
	int randIndex = baseIndex + shift + (int)(Math.random() * delta);
	System.out.println("Triplet: base: " + basePitch + ", baseIndex: " + baseIndex + ", randIdx: " + randIndex);
	
	pitch = pitchtab.get(randIndex);
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