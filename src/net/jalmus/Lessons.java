package net.jalmus;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

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
public class Lessons extends DefaultHandler{
   LinkedList<Level> levelslist;
   int currentlevel;
   NoteLevel level;
              //flags nous indiquant la position du parseur
   boolean inExercices, inLevel, inGametype, inNotestype, inNbnotes, inMessage, inSpeed, inStartingnote, inClef, inTonality,
   inIntervals, inChords, inDuration, inPitches;
              //buffer nous permettant de récupérer les données
  private StringBuffer buffer;


   public Lessons() {

     this.levelslist = new LinkedList<Level>();
     this.currentlevel = 0;
   }


public void nextLevel(){
  if (this.currentlevel+1 < this.levelslist.size() )
  this.currentlevel ++;
}


public String  getLessonPath(String language){
  	String path = "";
  	
  	  path=getClass().getSimpleName()+".class";
  	    URL url=getClass().getResource(path);
  	    try {
  	        path=URLDecoder.decode(url.toString(), "UTF-8");
  	    }
  	    catch (UnsupportedEncodingException ex) {
  	    }

  	    // suppression de  la classe ou du jar du path de l'url
  	    int index=path.lastIndexOf('/');
  	    path=path.substring(0, index);

  	    if (path.startsWith("jar:file:")) {
  	        // suppression de jar:file: de l'url d'un jar
  	        // ainsi que du path de la classe dans le jar
  	        index=path.indexOf('!');
  	        path=path.substring(9, index);
  	    } else {
  	        // suppresion du file: de l'url si c'est une classe en dehors d'un jar
  	        // et suppression du path du package si il est prÃ©sent.
  	        path=path.substring(5, path.length());
  	        Package pack=getClass().getPackage();
  	        if (null!=pack) {
  	            String packPath=pack.toString().replace('.', '/');
  	            if (path.endsWith(packPath)) {
  	                path=path.substring(0, (path.length()-packPath.length()));
  	            }
  	        }
  	    }

  	    index=path.lastIndexOf('/');
  	    path=path.substring(0, index);

  	    index=path.lastIndexOf('/');
  	    path=path.substring(0, index);
  	    
  	    path=path+File.separator+"lessons"+File.separator+language;
  	    
  	    //for test	
  	     path = "/home/christophe/git/code/lessons/de";
  	    
  	    return path;
  }


public NoteLevel getLevel(){
  return (NoteLevel) this.levelslist.get(this.currentlevel);
}
   //détection d'ouverture de balise
   public void startElement(String uri, String localName,
                            String qName, Attributes attributes) throws
       SAXException {
     if (qName.equals("levels")) {
       this.levelslist = new LinkedList<Level>();
       inExercices = true;
     }
     else if (qName.equals("notereading")) {
       level = new NoteLevel();
       try {
         int id = Integer.parseInt(attributes.getValue("id"));
         level.setId(id);
       }
       catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException(e);
       }
       inLevel = true;
     }
     else {
       buffer = new StringBuffer();
       if (qName.equals("message")) {
         inMessage = true;
       }

       else if (qName.equals("game")) {
         inGametype = true;
       }
       else if (qName.equals("clef")) {
         inClef = true;
       }
       else if (qName.equals("tonality")) {
        inTonality= true;
      }


       else if (qName.equals("notes")) {
         inNotestype = true;
       }
       else if (qName.equals("pitches")) {
           inPitches = true;
         }
       else if (qName.equals("nbnotes")) {
         inNbnotes = true;
       }
       else if (qName.equals("intervals")) {
         inIntervals = true;
       }
       else if (qName.equals("chords")) {
         inChords = true;
       }


       else if (qName.equals("speed")) {
         inSpeed = true;
       }
       else if (qName.equals("learningduration")) {
       inDuration = true;
     }

       else if (qName.equals("startingnote")) {
         inStartingnote = true;
       }


       else {
         //erreur, on peut lever une exception
         throw new SAXException("Markup " + qName + " unknown.");
       }
     }
   }

   //détection fin de balise
   public void endElement(String uri, String localName, String qName) throws
       SAXException {
     if (qName.equals("levels")) {
       inExercices = false;
     }
     else if (qName.equals("notereading")) {
       this.levelslist.add(level);
       //level = null;
       inLevel = false;
     }
     else if (qName.equals("message")) {
      level.setMessage(buffer.toString());
      buffer = null;
      inMessage = false;
    }

     else if (qName.equals("game")) {
           String tmpgame = buffer.toString();
         if (tmpgame.equals("normal") | tmpgame.equals("learning") | tmpgame.equals("inline")){
           level.setGametype(buffer.toString());
         }
         else
        throw new SAXException("In level " + level.getId() + " game type should be normal, inline or learning");

       buffer = null;
       inGametype = false;
     }
     else if (qName.equals("clef")) {
       String tmpclef = buffer.toString();
       if (tmpclef.equals("treble") | tmpclef.equals("bass") | tmpclef.equals("both")){
         level.setCurrentKey(tmpclef);
         level.inibasenote();
       }
       else
         throw new SAXException("In level " + level.getId() + " clef should be treble, bass or both");

       buffer = null;
       inClef = false;
     }
     else if (qName.equals("tonality")) {
       String tmpton = buffer.toString();
       if (tmpton.equals("random")) level.setRandomtonality(true);
       else {
         try {
           int tmpnbalt = Integer.parseInt(buffer.toString().substring(0,1));
           String tmpalt = buffer.toString().substring(1,2);
           if ((tmpalt.equals("#") | tmpalt.equals("b")) & (tmpnbalt >=0 & tmpnbalt <= 7 ))
             level.setCurrentTonality(new Tonality(tmpnbalt,tmpalt));
           else
             throw new SAXException("In level " + level.getId() + " tonality should be n# or nb with n int between 0 and 7");

         }
         catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException("In level " + level.getId() + e);
       }

       }

       buffer = null;
         inTonality = false;
     }

     else if (qName.equals("notes")) {
       String tmpclef = buffer.toString();
      if (tmpclef.equals("notes") | tmpclef.equals("accidentals") | tmpclef.equals("custom") | tmpclef.equals("intervals") | tmpclef.equals("chords")){
            level.setNotetype(buffer.toString());

      }
      else
        throw new SAXException("In level " + level.getId() + " notes type should be notes, accidentals, custom,  intervals or chords");


       buffer = null;
       inNotestype = false;
     }
     else if (qName.equals("nbnotes")) {
       try {
         int temp = Integer.parseInt(buffer.toString());
         if (temp == 3 | temp == 5 | temp == 7 | temp == 9 | temp == 15){
           level.setNbnotes(temp);
           //level.updatenbnotes();
         }
         else
           throw new SAXException("In level " + level.getId() + " number of notes should be 3, 5, 7, 9 or 15");

         buffer = null;
         inNbnotes = false;
       }
       catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException(e);
       }
     }
     
     else if (qName.equals("pitches")) {
         
           String temp = buffer.toString();
           StringTokenizer st=new StringTokenizer(temp,",;");
           ArrayList<Integer> pitcheslist =  new ArrayList<Integer>(); 
           Integer p;
           
           while ( st.hasMoreTokens() ) {
        	   // System.out.println(p);
        	    p = Integer.parseInt(st.nextToken());
        	  System.out.println(p);
        	    if ( level.isCurrentKeyTreble()) {
        	    	if (p >= 47 & p <= 96)	    pitcheslist.add(p);
        	    	else   throw new SAXException("In level " + level.getId() + " pitches should be list pitch 47 to 96");
        	    }
        	    	
        	    else if ( level.isCurrentKeyBass()) {
        	    	if (p >= 26 & p <= 74)	    pitcheslist.add(p);
      	      		else   throw new SAXException("In level " + level.getId() + " pitches should be list pitch 26 to 74");
        	    }
        	    
      	    else if ( level.isCurrentKeyBoth()) {       	    	
        	    	if (p >= 26 & p <= 96)	    pitcheslist.add(p);
        	    	 else   throw new SAXException("In level " + level.getId() + " pitches should be list pitch 26 to 96");

        	    }
       	     
        	    
        	}
           
        
             level.setPitcheslist(pitcheslist);
 
           buffer = null;
           inPitches = false;
   
       }
     
     else if (qName.equals("intervals")) {
         String tmpintervals = buffer.toString();
       if (tmpintervals.equals("second") | tmpintervals.equals("third") | tmpintervals.equals("fourth") |
           tmpintervals.equals("fifth") | tmpintervals.equals("sixth") | tmpintervals.equals("seventh") |
       tmpintervals.equals("octave") | tmpintervals.equals("random") ){
         level.setIntervaltype(tmpintervals);
       }
       else
      throw new SAXException("In level " + level.getId() + " interval type should be random or second, third ...");

     buffer = null;
     inIntervals = false;
   }
   else if (qName.equals("chords")) {
       String tmpchords = buffer.toString();
     if (tmpchords.equals("root") | tmpchords.equals("inversion") ){
       level.setChordtype(tmpchords);
     }
     else
    throw new SAXException("In level " + level.getId() + " chord type should be root or inversion");

   buffer = null;
   inChords = false;
 }


     else if (qName.equals("speed")) {
       try {
         int temp = Integer.parseInt(buffer.toString());
         if (temp >= 0 & temp <= 40)
           level.setSpeed(temp);
         else
           throw new SAXException("In level " + level.getId() + " speed should be an integer between 0 and 40");
         buffer = null;
         inSpeed = false;
       }
       catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException(e);
       }
     }

     else if (qName.equals("learningduration")) {
       try {
         int temp = Integer.parseInt(buffer.toString());
         if (temp >= 10 & temp <= 100)
           level.setLearningduration(temp);
         else
           throw new SAXException("In level " + level.getId() + " learning duration should be an integer between 10 and 100");
         buffer = null;
         inDuration = false;
       }
       catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException(e);
       }
     }
     else if (qName.equals("startingnote")) {
       try {
          int temp = Integer.parseInt(buffer.toString());
          if (!level.moveBasenote(temp))
              throw new SAXException("In level " + level.getId() + " starting note is out of range");

          buffer = null;
          inStartingnote = false;
     }
     catch (Exception e) {
        //erreur, le contenu de id n'est pas un entier
        throw new SAXException(e);
      }
    }

else {
       //erreur, on peut lever une exception
       throw new SAXException("Markup " + qName + " unknown.");
     }
   }

   //détection de caractères
   public void characters(char[] ch, int start, int length) throws SAXException {
     String lecture = new String(ch, start, length);
     if (buffer != null)
       buffer.append(lecture);
   }

   //début du parsing
   public void startDocument() throws SAXException {
     System.out.println("Start of parsing");
   }

   //fin du parsing
   public void endDocument() throws SAXException {
     System.out.println("End of parsing");
     System.out.println("Results of parsing");

   }


   public boolean lastexercice(){
     return this.levelslist.size()-1 == this.currentlevel;
   }
 }
