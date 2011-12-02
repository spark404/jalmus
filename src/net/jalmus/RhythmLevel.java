package net.jalmus;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * <p>Title: Java Lecture Musicale</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author non attribuable
 * @version 1.0
 */

public class RhythmLevel implements Level {
	int Id;
	String message;
  boolean whole;
  boolean half;
  boolean dottedhalf;
  boolean quarter;
  boolean eighth;
  boolean silence;
  boolean triplet;
  int timeSignNumerator;
  int timeSignDenominator;
  int timeDivision; // ratio between time signature numerator and denominator 
  int speed; // time sleep of thread = speed of the note
  boolean metronome;
  boolean beats;
  

  public RhythmLevel() {
	    this.Id = 0;
	    this.message = "";
	    this.whole = true;
	    this.half = true;
	    this.dottedhalf = false;
	    this.quarter = false;
	    this.eighth = false;
	    this.silence = true;
	    this.triplet = false;
	    
	    this.timeSignNumerator = 4;
	    this.timeSignDenominator = 4;
	    this.timeDivision = 1;     
	    this.speed = 28;
	    this.metronome = true;
	    this.beats = false;
    
  }

  public void init() {
	 this.Id = 0;
	    this.message = "";
    this.whole = true;
    this.half = true;
    this.dottedhalf = false;
    this.quarter = true;
    this.eighth = true;
    this.silence = true;
    this.triplet = false;
    
    this.timeSignNumerator = 4;
    this.timeSignDenominator = 4;
    this.timeDivision = 1;     
    this.speed = 28;
    this.metronome = true;
    this.beats = false;
  }

  public void copy(RhythmLevel n) {
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
  }

  /********************************/

  public void setId(int i) {
    this.Id = i;
  }

  public int getId() {
    return this.Id;
}

  /********************************/
  
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

  public void adjustLevel(boolean r, boolean b, boolean bp, boolean n, boolean c, boolean s, boolean t) {
    this.whole = r;
    this.half = b;
    this.dottedhalf = bp;
    this.quarter = n;
    this.eighth = c;
    this.silence = s;
    this.triplet = t;
  }

  public void paint(Graphics g) {
    /*  g.setColor(c);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      String niv = "";
      if (this.whole) niv = niv + whole;
      if (this.half) niv = niv + half;
      g.drawString(niv, 125, 461);*/

  }
  
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
		System.out.println("CrÃ©ation fichier " + destDir + "\\" + fileName + newline);
		
		fileContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+newline);
		fileContent.append("<!--"+newline+"Document : "+ fileName + newline + "Exercise saved : "+new Date()+newline+"-->"+newline+newline);
		fileContent.append("<levels>"+newline+"<rhythmreading id = '0'>"+newline);
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
		fileContent.append("<speed>"+this.speed+"</speed>"+newline);
		fileContent.append("</rhythmreading>"+newline+"</levels>"+newline);
		writeFile(f, fileContent.toString());
		
  }
  else JOptionPane.showMessageDialog(null, "The personnal lessons directory begin with 99. is missing", "Warning", JOptionPane.ERROR_MESSAGE); 
  


}

}
