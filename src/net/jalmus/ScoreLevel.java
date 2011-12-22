package net.jalmus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

public class ScoreLevel implements Level {
	int Id;
	String message;
	boolean whole;
	boolean half;
	boolean dottedhalf;
	boolean quarter; 
	boolean eighth;
	boolean silence;
	boolean triplet;
	String currentKey;
	Tonality currenttonality;
	boolean randomtonality;
	String notetype;
	int nbnotes;
	int timeSignNumerator;
	int timeSignDenominator;
	int timeDivision; // ratio between time signature numerator and denominator 
	int speed; // time sleep of thread = speed of the note
	boolean metronome;
	boolean beats;
	//int[] pitchtab = new int [8];
	ArrayList<Integer> pitcheslist = new ArrayList<Integer>(); 

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
	this.Id = 0;
	this.message = "";
    this.whole = true;
    this.half = true;
    this.dottedhalf = false;
    this.quarter = false;
    this.eighth = false;
    this.silence = true;
    this.triplet = false;
    this.currentKey = "treble";
    this.randomtonality = false;
    this.currenttonality = new Tonality(0, "");
    this.pitcheslist = new ArrayList<Integer>(); 
    this.notetype = "notes";
	this.nbnotes = 9;
    this.timeSignNumerator = 4;
    this.timeSignDenominator = 4;
    this.timeDivision = 1;     
    this.speed = 28;
    this.metronome = true;
    this.beats = false;
  }

  /********************************/

  public void setId(int i) {
    this.Id = i;
  }

  public int getId() {
    return this.Id;
}

  
  /*****************************************/
  public void setNotetype(String s) {
 this.notetype = s;
}
  public boolean isNotes() {
   return this.notetype.equals("notes");
 }

 public boolean isCustomNotes() {
	return this.notetype.equals("custom");
}
 

 
  /*******************************************/
  public void setMessage(String s) {
    this.message = s;
  }

  public String getMessage() {
    return this.message;
}

public boolean isMessageEmpty(){
  return this.message.equals("");
}

/*******************************************/
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


   public void setNbnotes(int i) {
     this.nbnotes = i;
   }

   public int getNbnotes() {
     return this.nbnotes;
 }

   public void copy(ScoreLevel n) {
		this.Id = n.Id;
	    this.message =  n.message;
	    this.whole = n.whole;
	    this.half = n.half;
	    this.dottedhalf = n.dottedhalf;
	    this.quarter = n.quarter;
	    this.eighth = n.eighth;
	    this.silence = n.silence;
	    this.triplet = n.triplet;
	    
	    this.timeSignNumerator = n.timeSignNumerator;
	    this.timeSignDenominator = n.timeSignDenominator;
	    
	    this.timeDivision = n.timeDivision ; 
	    this.metronome =   n.metronome;
	    this.beats =    n.beats ;
	    
	    this.currentKey =  n.currentKey;
	    this.randomtonality = n.randomtonality;
	    this.currenttonality = n.currenttonality;
	    
		this.nbnotes = n.nbnotes;
		
		this.pitcheslist =  new ArrayList<Integer>(); 
		this.pitcheslist.addAll(n.pitcheslist);

	  }
   
   
   public void adjustLevel(boolean r, boolean b, boolean n, boolean c, boolean s, boolean t) {
	    this.whole = r;
	    this.half = b;
	    this.quarter = n;
	    this.eighth = c;
	    this.silence = s;
	    this.triplet = t;
	  }
   
   /********************************/  
   
   
   public void setPitcheslist(ArrayList<Integer> l){		
	 	this.pitcheslist =  new ArrayList<Integer>(); 
		 this.pitcheslist.addAll(l);
	}
   
   
   public ArrayList<Integer> getPitcheslist(){		
	  return	this.pitcheslist;
	}
   
  public void initPitcheslist(int notesNum) {

	  int i = 0, noteIdx = 0; 
	  int octaveOffset = 0;
	  int altIndex = this.currenttonality.getAlterationsNumber();
	  pitcheslist.clear();


	  if (this.isCurrentKeyBass()) {
		  octaveOffset = -2;
		  if (notesNum == 9) noteIdx = 4; // starts from G
	  }
	  else if (this.isCurrentKeyTreble()) {
		  octaveOffset = 0;
		  if (notesNum == 9) noteIdx = 2; // starts from E
	  }

	  for (i = 0; i < notesNum; i++) {
		int alteration = 0;
		if (this.currenttonality.issharp())
			alteration = sharpsMatrix[altIndex][noteIdx];
		else if (this.currenttonality.isflat())
			alteration =  0 - flatsMatrix[altIndex][noteIdx];

		pitcheslist.add(sharpsMatrix[0][noteIdx] + (octaveOffset * 12) + alteration);
		
		if (noteIdx == 6) {
			octaveOffset++;
			noteIdx = 0;
		}
		else
			noteIdx++;
	  }

	//  for (i = 0; i < pitcheslist.size(); i++)
	//	  System.out.println("pitchtab #" + i + ": " + pitcheslist.get(i));
  }

 /* public int getYpos(int pitch) {
	int ypos = 43; // Y position of C3 on treble key
    int octave = 0;
    int octaveOffset = 0;
    int altIndex = this.currenttonality.getAlterationsNumber();
	
    if (this.isCurrentKeyBass()) {
    	ypos = 53; // Y position of C1 on bass key
    	octave = -2;
    	if (pitch > 47) { octave = -1; octaveOffset = 35; }
    	if (pitch > 59) { octave = 0; octaveOffset = 70; }
	}
	else if (this.isCurrentKeyTreble()) {
		if (pitch > 83) { octave = 2; octaveOffset = 70; }
		else if (pitch > 71) { octave = 1; octaveOffset = 35; }
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
  }*/
  
  public int getRandomPitch(){
	int pitch = 71;

//	pitch =  pitcheslist.get((int) (pitcheslist.size() * Math.random()));
	
	Random generator = new Random();
	int index = generator.nextInt ( this.pitcheslist.size() );
	if( index >-1 )  return (this.pitcheslist.get( index ));  	
	else return 0;
	
	//System.out.println("New random pitch = " + pitch);
	  
  }

  public int tripletRandomPitch(int basePitch) {
	int baseIndex = 0;
	int delta = 4; // within +2 and -2 tones from basePitch
	int shift = -2;
	int pitch = 0;
	for (int i = 0; i < pitcheslist.size(); i++)
		if (pitcheslist.get(i) == basePitch) {
			baseIndex = i;
			break;
		}
	if (baseIndex == 0) {
		delta = 2; // only +2
		shift = 2;
	}
	else if (baseIndex == pitcheslist.size() - 1) {
		delta = 2; // only -2
		shift = -2;
	}
	else if (baseIndex == 1) {
		delta = 3; // only -1/+2
		shift = -1;
	}
	else if (baseIndex == pitcheslist.size() - 2) {
		delta = 3; // only -2/+1
		shift = -2;
	}
	
	int randIndex = baseIndex + shift + (int)(Math.random() * delta);
	System.out.println("Triplet: base: " + basePitch + ", baseIndex: " + baseIndex + ", randIdx: " + randIndex);
	
	pitch = pitcheslist.get(randIndex);
	return pitch;
  }

  public void updateRhythm(boolean r, boolean b,  boolean bp, boolean n, boolean c, boolean s, boolean t) {
    this.whole = r;
    this.half = b;
    this.dottedhalf = bp;
    this.quarter = n;
    this.eighth = c;
    this.silence = s;
    this.triplet = t;
  }


  /*******************************************/

    public boolean getWholeNote() {
      return this.whole;
    }

    public boolean getHalfNote() {
      return this.half;
    }

    public boolean getDottedHalfNote() {
	    return this.dottedhalf;
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

    public void setWholeNote(boolean b) {
  	this.whole = b;
    }

    public void setHalfNote(boolean b) {
  	this.half = b;
    }
    
    public void setDottedHalfNote(boolean b) {
    	this.dottedhalf = b;
      }	
        
    public void setQuarterNote(boolean b) {
  	this.quarter = b;
    }
  	
    public void setEighthNote(boolean b) {
  	this.eighth = b;
    }
  	
    public void setSilence(boolean b) {
  	this.silence = b;
    }
  		  
    public void setTriplet(boolean b) {
  	this.triplet = b;
    }  

    /********************************/
    
    public int getTimeSignNumerator() {
  	    return  this.timeSignNumerator ;
  }
    
    public int getTimeSignDenominator() {
  	    return  this.timeSignDenominator ;
  }
    
    public void setTimeSignNumerator(int i) {
  	    this.timeSignNumerator = i ;
  }

  public void setTimeSignDenominator(int i) {
  	this.timeSignDenominator = i ;
  }
    
  public int getTimeDivision() {
      return  this.timeDivision ;
  }

  public void setTimeDivision(int i) {
  	this.timeDivision = i ;
  }

  /********************************/


  public void setSpeed(int i) {
    this.speed = i;
  }

  public int getspeed() {
    return this.speed;
  }

  /********************************/

  public void setMetronome(boolean b) {
      this.metronome = b;
    }

    public boolean getMetronome() {
      return this.metronome;
  }

    /********************************/
    


    public void setMetronomeBeats(boolean b) {
        this.beats = b;
      }

      public boolean getMetronomeBeats() {
        return this.beats;
    }

      /********************************/
 public void printtest(){
   	   System.out.println("Level n°"+this.Id);
   	    System.out.println("Whole note : " + this.whole);
   	  System.out.println("Half note : " + this.half);
	  System.out.println("DottedHalf note : " + this.dottedhalf);
   	    System.out.println("Quarter note : " + this.quarter);
   	  System.out.println("Eighth note : " + this.eighth);
   	  System.out.println("Rests : " + this.silence);
   	  System.out.println("Triplets : " + this.triplet);
   	  System.out.println("time signature : " + this.timeSignNumerator + "/" + this.timeSignDenominator + "div " + this.timeDivision);
   	  System.out.println("Speed : " + this.speed);
   	  System.out.println("Metronome sound : " + this.metronome+ " visual beats : " + this.beats);
   	  System.out.println("Key : " + this.currentKey);
   	  System.out.println("Piches liste : " + this.pitcheslist);

   	  System.out.println("Tonality : " + this.currenttonality);
	  System.out.println("Nb notes "+this.nbnotes);
   	}

      
      
  public void save(Lessons l, String fileName, String message, String language) 
	throws IOException {
	
	File destDir = new File("");;
final String newline = "\r\n";
String path = "";
StringBuffer fileContent = new StringBuffer();
boolean dirmylessonok = false;

path = l.getLessonPath(language);

File sousrepertoire=new File(path);

// find the personnal lessons directory 99. in the name
if (sousrepertoire.isDirectory()) {
    File[] listsp=sousrepertoire.listFiles();
    Arrays.sort(listsp);
    if (listsp!=null) { 

     for   (int i=0; i<listsp.length; i++) {
    	 		if (listsp[i].getName().indexOf("99.") != -1)  {

            	
            	path = path+File.separator+listsp[i].getName();
    	 	    dirmylessonok = true;
    	 		}
    	 		
                
     }
    }
}

if ( dirmylessonok) {
	destDir = new File(path);

		//return File.createTempFile(new File(fileName).getName() + "_" + datev,"", destDir);
		File f = new File(destDir, fileName);
		System.out.println("Create file " + destDir + "\\" + fileName + newline);
		
		fileContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+newline);
		fileContent.append("<!--"+newline+"Document : "+ fileName + newline + "Exercise saved : "+new Date()+newline+"-->"+newline+newline);
		fileContent.append("<levels>"+newline+"<scorereading id = '0'>"+newline);
		fileContent.append("<message>"+message+"</message>"+newline);
	//	fileContent.append("<game>"+this.gametype+"</game>"+newline);
		fileContent.append("<time>"+this.getTimeSignNumerator()+"/"+this.getTimeSignDenominator()+"</time>"+newline);
		fileContent.append("<rhythms>");
		if (this.getWholeNote()) fileContent.append(1+",");
		if (this.getHalfNote()) fileContent.append(2+",");
		if (this.getDottedHalfNote()) fileContent.append(3+",");
		if (this.getQuarterNote()) fileContent.append(4+",");
		if (this.getEighthNote()) fileContent.append(8+",");
		fileContent.append("</rhythms>"+newline);
		if (this.getSilence()) fileContent.append("<rests>1,2,3,4,8</rests>"+newline); // no distinction yet
		if (this.getTriplet()) fileContent.append("<tuplets>3</tuplets>"+newline); // no distinction yet
		if (this.getMetronome() & this.getMetronomeBeats()) fileContent.append("<metronome>both</metronome>"+newline);
		else if (this.getMetronome() & !this.getMetronomeBeats()) fileContent.append("<metronome>sound</metronome>"+newline);
		else if (!this.getMetronome() & !this.getMetronomeBeats()) fileContent.append("<metronome>none</metronome>"+newline);
		else if (!this.getMetronome() & this.getMetronomeBeats()) fileContent.append("<metronome>visual</metronome>"+newline);
		fileContent.append("<clef>"+this.currentKey+"</clef>"+newline);
		if (this.randomtonality) fileContent.append("<tonality>random</tonality>"+newline);
		else  if (this.currenttonality.getAlterationsNumber()!=0) fileContent.append("<tonality>"+this.currenttonality.getAlterationsNumber()+this.currenttonality.getAlteration()+ "</tonality>"+newline);
		fileContent.append("<notes>"+this.notetype+"</notes>"+newline);
		if (this.isNotes()) fileContent.append("<nbnotes>"+this.getNbnotes()+"</nbnotes>"+newline);
		if (this.isCustomNotes()){
			fileContent.append("<pitches>");
			for (Integer pitch : this.pitcheslist) {
			fileContent.append(pitch+",");
			}
			fileContent.append("</pitches>"+newline);
		}
		fileContent.append("<speed>"+this.speed+"</speed>"+newline);
		fileContent.append("</scorereading>"+newline+"</levels>"+newline);
		writeFile(f, fileContent.toString());
		
}
else JOptionPane.showMessageDialog(null, "The personnal lessons directory begin with 99. is missing", "Warning", JOptionPane.ERROR_MESSAGE); 



}

  private static void writeFile(File destFile, String content)
  throws IOException {
  BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
  writer.write(content);
  writer.flush();
  writer.close();
  writer = null;
  }

}