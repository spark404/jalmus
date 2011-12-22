package net.jalmus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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


public class NoteLevel implements Level{
  int Id;
  String message;
  String currentKey;
  String gametype;
  String notetype;
  Tonality currenttonality;
  boolean randomtonality;
  int speed; // time sleep of thread = speed of the note

  /* For learning game only */
  int learningduration; // duration of learning game in lesson mode

  /* For notes game only */
  int nbnotes; // number of notes : 0 for all notes, 3, 5, 7, 9 or 15
  int basetreble; // height of the base note choose by user for trebleKey
  int basebass; // height of the base note choose by user for bassKey
  int nbnotesunder; // number of notes under basenote
  int nbnotesupper;// number of notes upper basenote
  
  ArrayList<Integer> pitcheslist; //list of pitch for custom

  /* For chord game only */
  String chordtype;

  /* For interval game only*/
  String intervaltype;



  public NoteLevel() {
    this.Id = 0;
    this.message = "";
    this.currentKey = "treble";
    this.notetype = "notes";
    this.gametype = "normal";
    this.randomtonality = false;
    this.currenttonality = new Tonality(0, "");
    this.speed = 28; //  moderato

    this.learningduration = 20;

    this.nbnotes = 3;
    this.basetreble = 25; // G3
    this.basebass = 5; // F2
    this.nbnotesunder = 1;  // three notes game
    this.nbnotesupper = 1;

    this.chordtype = "root";
    this.intervaltype = "third";
    this.pitcheslist = new ArrayList<Integer>();


  }

  public void init(){
    this.Id = 0;
    this.message = "";
    this.currentKey = "treble";
    this.notetype = "notes";
    this.gametype = "normal";
    this.randomtonality = false;
    this.currenttonality = new Tonality(0, "");
    this.speed = 20; //  moderato

    this.learningduration = 20;

    this.nbnotes = 3;
    this.basetreble = 25; // G3
    this.basebass = 5; // F2
    this.nbnotesunder = 1;  // three notes game
    this.nbnotesupper = 1;

    this.chordtype = "root";
    this.intervaltype = "third";
    
    this.pitcheslist =  new ArrayList<Integer>(); 

  }

/********************************/

  public void setId(int i) {
    this.Id = i;
  }

  public int getId() {
    return this.Id;
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

    public void setLearningduration(int i) {
      this.learningduration = i;
    }

    public int getLearningduration() {
      return this.learningduration;
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

  public boolean isCurrentKeyBoth() {
    return this.currentKey.equals("both");
  }
/*****************************************/

 /*****************************************/
 public void setGametype(String s) {
   this.gametype = s;
 }

 public String getGametype() {
  return this.gametype;
}


 public boolean isNormalgame() {
   return this.gametype.equals("normal");
 }

 public boolean isInlinegame() {
   return this.gametype.equals("inline");
 }

 public boolean isLearninggame() {
   return this.gametype.equals("learning");
 }
 /*****************************************/

 /*****************************************/
  public void setNotetype(String s) {
 this.notetype = s;
}
  public boolean isNotesgame() {
   return this.notetype.equals("notes");
 }

 public boolean isAccidentalsgame() {
   return this.notetype.equals("accidentals");
 }

 public boolean isCustomNotesgame() {
	return this.notetype.equals("custom");
}
 
 public boolean isIntervalsgame() {
   return this.notetype.equals("intervals");
 }
 public boolean isChordsgame() {
  return this.notetype.equals("chords");
}
 /*****************************************/

 /*****************************************/
 
 public ArrayList<Integer> getPitcheslist(){
	   return this.pitcheslist;
	 }

 public void setPitcheslist(ArrayList<Integer> l){		
	 	this.pitcheslist =  new ArrayList<Integer>(); 
		 this.pitcheslist.addAll(l);
	}

 public void resetPitcheslist(){		
	 	this.pitcheslist =  new ArrayList<Integer>(); 
	}



 public  Integer getRandomPitch(){
			     
		Random generator = new Random();
		int index = generator.nextInt ( this.pitcheslist.size() );
		if( index >-1 )  return (this.pitcheslist.get( index ));  	
		else return 0;
	}
	    
 /*****************************************/

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

/*****************************************/
  public void setNbnotes(int nb) {
    this.nbnotes = nb;
  }

  public int getNbnotes() {
    return this.nbnotes;
  }

  public boolean isAllnotesgame() {
    return this.nbnotes == 0;
  }
/*****************************************/

  /*****************************************/
  public void setSpeed(int nb) {
    this.speed = nb;
  }

  public int getSpeed() {
    return this.speed;
  }

/*****************************************/

  /*****************************************/
  public void setChordtype(String s) {
    this.chordtype = s;
  }

  public boolean isChordtypeRoot() {
    return this.chordtype.equals("root");
  }

  public boolean isChordtypeInversion() {
    return this.chordtype.equals("inversion");
  }

 /*****************************************/

 /*****************************************/
public void setIntervaltype(String s) {
  this.intervaltype = s;
}

public boolean isSecondInterval() {
  return this.intervaltype.equals("second");
}

public boolean isThirdInterval() {
  return this.intervaltype.equals("third");
}

public boolean isFourthInterval() {
  return this.intervaltype.equals("fourth");
}

public boolean isFifthInterval() {
  return this.intervaltype.equals("fifth");
}

public boolean isSixthInterval() {
  return this.intervaltype.equals("sixth");
}

public boolean isSeventhInterval() {
  return this.intervaltype.equals("seventh");
}

public boolean isOctaveInterval() {
  return this.intervaltype.equals("octave");
}

public boolean isRandomInterval() {
  return this.intervaltype.equals("random");
}

public boolean isAllInterval() {
  return this.intervaltype.equals("random");
}
/*****************************************/
public int getBasetreble(){
  return this.basetreble;
}

public int getBasebass(){
  return this.basebass;
}

public int getNbnotesupper(){
  return this.nbnotesupper;
}

public int getNbnotesunder(){
  return this.nbnotesunder;
}


public void copy(NoteLevel nl){
  this.message = nl.message;
  this.currentKey = nl.currentKey;
 this.notetype = nl.notetype;
 this.gametype = nl.gametype;
 this.currenttonality.copy(nl.currenttonality);
 this.speed = nl.speed; //  moderato

 this.nbnotes = nl.nbnotes;
 this.basetreble = nl.basetreble; // G3
 this.basebass = nl.basebass; // F2
 this.nbnotesunder = nl.nbnotesunder;  // three notes game
 this.nbnotesupper = nl.nbnotesupper;

 this.chordtype = nl.chordtype;
 this.intervaltype = nl.intervaltype;
 
this.pitcheslist =  new ArrayList<Integer>(); 
this.pitcheslist.addAll(nl.getPitcheslist());



}

private static void writeFile(File destFile, String content)
throws IOException {
BufferedWriter writer = new BufferedWriter(new FileWriter(destFile));
writer.write(content);
writer.flush();
writer.close();
writer = null;
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
		System.out.println("Création fichier " + destDir + "\\" + fileName + newline);
		
		fileContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+newline);
		fileContent.append("<!--"+newline+"Document : "+ fileName + newline + "Exercise saved : "+new Date()+newline+"-->"+newline+newline);
		fileContent.append("<levels>"+newline+"<notereading id = '0'>"+newline);
		fileContent.append("<message>"+message+"</message>"+newline);
		fileContent.append("<game>"+this.gametype+"</game>"+newline);
		if (this.isLearninggame()) fileContent.append("<learningduration>30<learningduration>"+newline);
		fileContent.append("<clef>"+this.currentKey+"</clef>"+newline);
		if (this.randomtonality) fileContent.append("<tonality>random</tonality>"+newline);
		else  if (this.currenttonality.getAlterationsNumber()!=0) fileContent.append("<tonality>"+this.currenttonality.getAlterationsNumber()+this.currenttonality.getAlteration()+ "</tonality>"+newline);
		fileContent.append("<notes>"+this.notetype+"</notes>"+newline);
		if (this.isNotesgame() || this.isAccidentalsgame()){
			fileContent.append("<nbnotes>"+this.nbnotes+"</nbnotes>"+newline);
			if (this.isCurrentKeyBass()) fileContent.append("<startingnote>"+(5 - this.basebass)/5 +"</startingnote>"+newline);
			else if (this.isCurrentKeyTreble()) fileContent.append("<startingnote>"+(25 - this.basetreble)/5 +"</startingnote>"+newline);
			else if (this.isCurrentKeyBoth()) fileContent.append("<startingnote>"+(20 - this.basebass)/5 +"</startingnote>"+newline);
			   
		}
		if (this.isIntervalsgame()){
			fileContent.append("<intervals>"+this.intervaltype+"</intervals>"+newline);
		}
		if (this.isChordsgame()){
			fileContent.append("<chords>"+this.chordtype+"</chords>"+newline);
		}
		if (this.isCustomNotesgame()){
			fileContent.append("<pitches>");
			for (Integer pitch : this.pitcheslist) {
			fileContent.append(pitch+",");
			}
			fileContent.append("</pitches>"+newline);
		}
		
		fileContent.append("<speed>"+this.speed+"</speed>"+newline);
		fileContent.append("</notereading>"+newline+"</levels>");
		writeFile(f, fileContent.toString());
		
    }
    else JOptionPane.showMessageDialog(null, "The personnal lessons directory begin with 99. is missing", "Warning", JOptionPane.ERROR_MESSAGE); 
    


}

	
public void printtest(){
   System.out.println("Level n°"+this.Id);
    System.out.println(this.currentKey);
  System.out.println(this.gametype);
  System.out.println(this.notetype);
    System.out.println(this.currenttonality);
    System.out.println("Nb notes "+this.nbnotes);
  System.out.println("Base note treble "+this.basetreble);
  System.out.println("Base note bass "+this.basebass);
  System.out.println("nbnotes upper "+this.nbnotesupper);
  System.out.println("nbnotes under "+this.nbnotesunder);
}



public void basenotetoLeft(Piano piano){  //base note is move when user pres key left

  if (this.isCurrentKeyTreble()) {
    if ( (this.nbnotesupper == 7 & this.basetreble < 50) //15notes
        | (this.nbnotesupper == 4 & this.basetreble < 65) // 9 notes
        | (this.nbnotesupper == 3 & this.basetreble < 70) // 9 notes
        | (this.nbnotesupper == 2 & this.basetreble < 75) // 5 notes
        | (this.nbnotesupper == 1 & this.basetreble < 80)) { // 3notes
      this.basetreble = this.basetreble + 5;
    //  piano.Setpositionbase1(piano.Getpositionbase1() - 1);
    }

  }
  //jusqu'� do
  else if (this.isCurrentKeyBass()) {
    if (piano.is73keys() &
        ( (this.nbnotesupper == 7 & basebass < 50)
         | (this.nbnotesupper == 4 & basebass < 65)
         | (this.nbnotesupper == 3 & basebass < 60)
         | (this.nbnotesupper == 2 & basebass < 75)
         | (this.nbnotesupper == 1 & basebass < 80))) {
      basebass = basebass + 5; //fa = 17kw

    }
    ;

    if (piano.is61keys() &
        ( (this.nbnotesupper == 7 & basebass < 15)
         | (this.nbnotesupper == 4 & basebass < 30)
         | (this.nbnotesupper == 3 & basebass < 35)
         | (this.nbnotesupper == 2 & basebass < 40)
         | (this.nbnotesupper == 1 & basebass < 45))) {
      basebass = basebass + 5; //fa = 17kw

    }
    ;

  }

  else if (this.isCurrentKeyBoth()) {
    if (piano.is73keys() & ( (this.nbnotesupper == 7 & this.basetreble < 15)
                            | (this.nbnotesupper == 4 & this.basetreble < 30)
                        | (this.nbnotesupper == 3 & this.basetreble < 35)
                            | (this.nbnotesupper == 2 & this.basetreble < 40)
                            | (this.nbnotesupper == 1 & this.basetreble < 45))) {
      basebass = basebass + 5;
      this.basetreble = this.basetreble + 5;


    }

    if (piano.is61keys() & ( (this.nbnotesupper == 7 & this.basetreble < 5)
                            | (this.nbnotesupper == 4 & this.basetreble < 20)
                        | (this.nbnotesupper == 3 & this.basetreble < 25)
                            | (this.nbnotesupper == 2 & this.basetreble < 30)
                            | (this.nbnotesupper == 1 & this.basetreble < 35))) {
      basebass = basebass + 5;
      this.basetreble = this.basetreble + 5;


    }

  }
}

/** Move starting note by n keys to right  when user press key Right or for lesson
 @param
 @return no return
 */

public void basenotetoRight(Piano piano){


  if (this.isCurrentKeyTreble()) {
    if ( (this.nbnotesupper == 7 & this.basetreble > -20) //15notes
        | (this.nbnotesupper == 4 & this.basetreble > -35) // 9 notes
        | (this.nbnotesupper == 3 & this.basetreble > -40) // 5 notes
        | (this.nbnotesupper == 2 & this.basetreble > -45) // 5 notes
        | (this.nbnotesupper == 1 & this.basetreble > -50)) // 3notes

    {
      this.basetreble = this.basetreble - 5;


    }


  }
  else if (this.isCurrentKeyBass()) {
    if ( (this.nbnotesupper == 7 & this.basebass > -20)
        | (this.nbnotesupper == 4 & this.basebass > -35)
        | (this.nbnotesupper == 3 & this.basebass > -40) // 5 notes
        | (this.nbnotesupper == 2 & this.basebass > -45)
        | (this.nbnotesupper == 1 & this.basebass > -50)) {

      this.basebass = this.basebass - 5; //fa = 17kw


    }


  }
  else if (this.isCurrentKeyBoth()) {
    if ( (this.nbnotesupper == 7 & this.basebass > 15) //15notes
        | (this.nbnotesupper == 4 & this.basebass > 0) // 9 notes
        | (this.nbnotesupper == 3 & this.basebass > -5) // 5 notes
        | (this.nbnotesupper == 2 & this.basebass > -10) // 5 notes
        | (this.nbnotesupper == 1 & this.basebass > -15)) { // 3notes
      this.basebass = this.basebass -  5;
      this.basetreble = this.basetreble - 5;


    }

  }

}


/** Move starting note by n keys  for the lessons (the keyboard length is not known)
 @param Range to indicate the number of keys to move
 @return Return true if the modification of base note is possible
 */

public boolean moveBasenote(int range){
  if (range != 0){


    if (this.isCurrentKeyTreble()) {
      if ( (this.nbnotes == 15 & ( (range > 0 & range <= 9) | (range < 0 & Math.abs(range) <= 5))) //15notes
          | (this.nbnotes == 9 & ( (range > 0 & range <= 12) | (range < 0 & Math.abs(range) <= 8))) // 9 notes
          | (this.nbnotes == 7 & ( (range > 0 & range <= 13) | (range < 0 & Math.abs(range) <= 9))) // 7 notes
          | (this.nbnotes == 5 & ( (range > 0 & range <= 14) | (range < 0 & Math.abs(range) <= 10))) // 5 notes
          | (this.nbnotes == 3 & ( (range > 0 & range <= 15) | (range < 0 & Math.abs(range) <= 11)))) { // 3notes
        this.basetreble = this.basetreble - 5 * range;
        return true;

      }
      else
        return false;

    }
    else if (this.isCurrentKeyBass()) {
      if ( (this.nbnotes == 15 & ( (range > 0 & range <= 5) | (range < 0 & Math.abs(range) <= 9))) //15notes
          | (this.nbnotes == 9 & ( (range > 0 & range <= 8) | (range < 0 & Math.abs(range) <= 12))) // 9 notes
          | (this.nbnotes == 7 & ( (range > 0 & range <= 9) | (range < 0 & Math.abs(range) <= 13))) // 9 notes
          | (this.nbnotes == 5 & ( (range > 0 & range <= 10) | (range < 0 & Math.abs(range) <= 14))) // 5 notes
          | (this.nbnotes == 3 & ( (range > 0 & range <= 11) | (range < 0 & Math.abs(range) <= 15)))) { // 3notes
        this.basebass = this.basebass - 5 * range;
        return true;

      }
      else
        return false;

    }

    else if (this.isCurrentKeyBoth()) {
      if ( (this.nbnotes == 15 & ( (range > 0 & range <= 1) | (range < 0 & Math.abs(range) <= 1)))
          | (this.nbnotes == 9 & ( (range > 0 & range <= 4) | (range < 0 & Math.abs(range) <= 4)))
          | (this.nbnotes == 7 & ( (range > 0 & range <= 5) | (range < 0 & Math.abs(range) <= 5)))
          | (this.nbnotes == 5 & ( (range > 0 & range <= 6) | (range < 0 & Math.abs(range) <= 6)))
          | (this.nbnotes == 3 & ( (range > 0 & range <= 7) | (range < 0 & Math.abs(range) <= 7)))) {
        this.basebass = this.basebass - 5 * range;
        this.basetreble = this.basetreble - 5 * range;
        return true;
      }
      else
        return false;

    }
    else
      return false;
  }
  else return true;
}

public void inibasenote(){
  if (this.isCurrentKeyTreble() | this.isCurrentKeyBass()) {
    this.basetreble = 25;
    this.basebass = 5;
  }
  else {
    this.basetreble = 10;
    this.basebass = 20;

  }
}
  public void updatenbnotes(Piano p) {
    if (this.isNotesgame() | this.isAccidentalsgame()) {
      if (this.isCurrentKeyTreble()) {

        if (this.nbnotes == 3) {
          this.nbnotesupper = 1;
          this.nbnotesunder = 1;
        }
        else if (this.nbnotes == 5) {
          this.nbnotesupper = 2;
          this.nbnotesunder = 2;
        }
        else if (this.nbnotes == 7) {
        this.nbnotesupper = 3;
        this.nbnotesunder = 3;
      }

        else if (this.nbnotes == 9) {
          this.nbnotesupper = 4;
          this.nbnotesunder = 4;
        }

        else if (this.nbnotes == 15) {
          this.nbnotesupper = 7;
          this.nbnotesunder = 7;
        }
        else if (this.nbnotes == 0) {
          this.nbnotesupper = 16;
          this.nbnotesunder = 12;
        }
      }

      else if (this.isCurrentKeyBass()) {

        if (this.nbnotes == 3) {
          this.nbnotesupper = 1;
          this.nbnotesunder = 1;
        }
        else if (this.nbnotes == 5) {
          this.nbnotesupper = 2;
          this.nbnotesunder = 2;
        }
        else if (this.nbnotes == 7) {
       this.nbnotesupper = 3;
       this.nbnotesunder = 3;
     }

        else if (this.nbnotes == 9) {
          this.nbnotesupper = 4;
          this.nbnotesunder = 4;
        }
        else if (this.nbnotes == 15) {
          this.nbnotesupper = 7;
          this.nbnotesunder = 7;
        }
        else if (this.nbnotes == 0) {
          if (p.is73keys()) {
            this.nbnotesupper = 12;
            this.nbnotesunder = 16;
          }
          else if (p.is61keys()) {
            this.nbnotesupper = 12;
            this.nbnotesunder = 9;
          }
        }
      }

      else if (this.isCurrentKeyBoth()) {

        if (this.nbnotes == 3) {
          this.nbnotesupper = 1;
          this.nbnotesunder = 1;
        }
        else if (this.nbnotes == 5) {
          this.nbnotesupper = 2;
          this.nbnotesunder = 2;
        }
        else if (this.nbnotes == 7) {
       this.nbnotesupper = 3;
       this.nbnotesunder = 3;
     }

        else if (this.nbnotes == 9) {
          this.nbnotesupper = 4;
          this.nbnotesunder = 4;
        }
        else if (this.nbnotes == 15) {
          this.nbnotesupper = 7;
          this.nbnotesunder = 7;
        }
        else if (this.nbnotes == 0) {

          if (p.is73keys()) {
            this.nbnotesupper = 8;
            this.nbnotesunder = 8;

          }
          else if (p.is61keys()) {
            this.nbnotesupper = 7;
            this.nbnotesunder = 7;

          }
        }

      }
    }
  }


}

