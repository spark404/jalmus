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


enum TLevel {NOTELEVEL, RHYTHMLEVEL, SCORELEVEL};

public class Lessons extends DefaultHandler{
   LinkedList<Level> levelslist;
   int currentlevel;
   NoteLevel nlevel;
   RhythmLevel rlevel;
   ScoreLevel slevel;  
              //flags nous indiquant la position du parseur
   boolean inExercices, inNLevel, inRLevel, inSLevel, inGametype, inNotestype, inNbnotes, inMessage, inSpeed, inStartingnote, inClef, inTonality,
   inIntervals, inChords, inDuration, inPitches,  inRhythms, inTime, inRests, inTuplets, inMetronome;
   
  TLevel leveltype; 
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
  	   //  path = "/home/christophe/git/code/lessons/en";
  	//   path = "C:\\Documents and Settings\\crichard\\git\\code\\lessons\\fr";
  	    
  	    return path;
  }


public Level getLevel(){
	if(this.levelslist.get(this.currentlevel).getClass().getSimpleName().equals("NoteLevel"))
		return (NoteLevel) this.levelslist.get(this.currentlevel);
	else if(this.levelslist.get(this.currentlevel).getClass().getSimpleName().equals("RhythmLevel"))
		return (RhythmLevel) this.levelslist.get(this.currentlevel);
	else //Scorelevel
		return (ScoreLevel) this.levelslist.get(this.currentlevel);
}

public boolean isNoteLevel(){
	if(this.levelslist.get(this.currentlevel).getClass().getSimpleName().equals("NoteLevel"))
		return true;
	else
		return false;
}

public boolean isRhythmLevel(){
	if(this.levelslist.get(this.currentlevel).getClass().getSimpleName().equals("RhythmLevel"))
		return true;
	else
		return false;
}

public boolean isScoreLevel(){
	if(this.levelslist.get(this.currentlevel).getClass().getSimpleName().equals("ScoreLevel")){
		System.out.println("Score level");
		return true;
	}
	else
		return false;
}

   //détection d'ouverture de balise
   public void startElement(String uri, String localName,
                            String qName, Attributes attributes) throws
       SAXException {
	   
	   System.out.println(leveltype + " "+ qName);
	   
     if (qName.equals("levels")) {
       this.levelslist = new LinkedList<Level>();
       inExercices = true;
     }
     else if (qName.equals("notereading")) {
       nlevel = new NoteLevel();
       leveltype = TLevel.NOTELEVEL;
       try {
         int id = Integer.parseInt(attributes.getValue("id"));
         nlevel.setId(id);
       }
       catch (Exception e) {
         //erreur, le contenu de id n'est pas un entier
         throw new SAXException(e);
       }
       inNLevel = true;
     }
     
     else if (qName.equals("rhythmreading")) {
         rlevel = new RhythmLevel();
         leveltype = TLevel.RHYTHMLEVEL;
         try {
           int id = Integer.parseInt(attributes.getValue("id"));
           rlevel.setId(id);
         }
         catch (Exception e) {
           //erreur, le contenu de id n'est pas un entier
           throw new SAXException(e);
         }
         inRLevel = true;
       }
     
     else if (qName.equals("scorereading")) {
         slevel = new ScoreLevel();
         leveltype = TLevel.SCORELEVEL;
         try {
           int id = Integer.parseInt(attributes.getValue("id"));
           slevel.setId(id);
         }
         catch (Exception e) {
           //erreur, le contenu de id n'est pas un entier
           throw new SAXException(e);
         }
         inSLevel = true;
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
       
       else if (qName.equals("time")) {
           inTime = true;
         }
       
       else if (qName.equals("rhythms")) {
           inRhythms = true;
         }
       else if (qName.equals("rests")) {
           inRests = true;
         }
       else if (qName.equals("tuplets")) {
           inTuplets = true;
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
       else if (qName.equals("metronome")) {
           inMetronome = true;
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
       this.levelslist.add(nlevel);
       //level = null;
       inNLevel = false;
     }
     
     else if (qName.equals("rhythmreading")) {
         this.levelslist.add(rlevel);
         //level = null;
         inRLevel = false;
       }
     
     else if (qName.equals("scorereading")) {
         this.levelslist.add(slevel);
         //level = null;
         inSLevel = false;
       }
     
     else if (qName.equals("message")) {
    	  switch (leveltype)
    	     {
    	       case NOTELEVEL :
    	    	   nlevel.setMessage(buffer.toString());
    	         break;
    	       case RHYTHMLEVEL:
    	    	   rlevel.setMessage(buffer.toString());
    	         break;
    	       case SCORELEVEL :
    	    	   slevel.setMessage(buffer.toString());
    	         break;
    	     }
    	  
      
      buffer = null;
      inMessage = false;
    }

     else if (qName.equals("game")) {
           String tmpgame = buffer.toString();
     	  switch (leveltype)
 	     {
 	       case NOTELEVEL :
 	    	  if (tmpgame.equals("normal") | tmpgame.equals("learning") | tmpgame.equals("inline")){
 	             nlevel.setGametype(buffer.toString());
 	           }
 	           else
 	          throw new SAXException("In level " + nlevel.getId() + " game type should be normal, inline or learning");
 	    	  break;
 	       case RHYTHMLEVEL:
 	    	  throw new SAXException("In level " + rlevel.getId() + " no game type on rhythm level");
 	  
 	       case SCORELEVEL :
 	    	  throw new SAXException("In level " + slevel.getId() + "  no game type on score level");
 
 	     }
       
       buffer = null;
       inGametype = false;
     }
     else if (qName.equals("clef")) {
       String tmpclef = buffer.toString();
       switch (leveltype)
	     {
	       case NOTELEVEL :
	    	   if (tmpclef.equals("treble") | tmpclef.equals("bass") | tmpclef.equals("both")){
	    	         nlevel.setCurrentKey(tmpclef);
	    	         nlevel.inibasenote();
	    	       }
	    	       else
	    	         throw new SAXException("In level " + nlevel.getId() + " clef should be treble, bass or both");
	    	  break;
	       case RHYTHMLEVEL:
	    	  throw new SAXException("In level " + rlevel.getId() + " no key on rhythm level");
	  	 
	       case SCORELEVEL :
	    	   if (tmpclef.equals("treble") | tmpclef.equals("bass")){
	    	         slevel.setCurrentKey(tmpclef);
	    	         //slevel.inibasenote();
	    	       }
	    	       else
	    	         throw new SAXException("In level " + nlevel.getId() + " clef should be treble or bass ");
	    	  break;
	    	
	     }
     
       buffer = null;
       inClef = false;
     }
     else if (qName.equals("tonality")) {
       String tmpton = buffer.toString();
       
       switch (leveltype)
	     {
	       case NOTELEVEL :
	    	   if (tmpton.equals("random")) nlevel.setRandomtonality(true);
	           else {
	             try {
	               int tmpnbalt = Integer.parseInt(buffer.toString().substring(0,1));
	               String tmpalt = buffer.toString().substring(1,2);
	               if ((tmpalt.equals("#") | tmpalt.equals("b")) & (tmpnbalt >=0 & tmpnbalt <= 7 ))
	                 nlevel.setCurrentTonality(new Tonality(tmpnbalt,tmpalt));
	               else
	                 throw new SAXException("In level " + nlevel.getId() + " tonality should be n# or nb with n int between 0 and 7");

	             }
	             catch (Exception e) {
	             //erreur, le contenu de id n'est pas un entier
	             throw new SAXException("In level " + nlevel.getId() + e);
	           }

	           }
	    	  break;
	       case RHYTHMLEVEL:
	    	  throw new SAXException("In level " + rlevel.getId() + " no tonality on rhythm level");
	  	    
	       case SCORELEVEL :
	    	   if (tmpton.equals("random")) slevel.setRandomtonality(true);
	           else {
	             try {
	               int tmpnbalt = Integer.parseInt(buffer.toString().substring(0,1));
	               String tmpalt = buffer.toString().substring(1,2);
	               if ((tmpalt.equals("#") | tmpalt.equals("b")) & (tmpnbalt >=0 & tmpnbalt <= 7 ))
	                 slevel.setCurrentTonality(new Tonality(tmpnbalt,tmpalt));
	               else
	                 throw new SAXException("In level " + slevel.getId() + " tonality should be n# or nb with n int between 0 and 7");

	             }
	             catch (Exception e) {
	             //erreur, le contenu de id n'est pas un entier
	             throw new SAXException("In level " + nlevel.getId() + e);
	           }

	           }
	    	  break;
	     }
     

       buffer = null;
         inTonality = false;
     }

     else if (qName.equals("notes")) {
       String tmpclef = buffer.toString();
       switch (leveltype)
	     {
	       case NOTELEVEL :
	    	   if (tmpclef.equals("notes") | tmpclef.equals("accidentals") | tmpclef.equals("custom") | tmpclef.equals("intervals") | tmpclef.equals("chords")){
	               nlevel.setNotetype(buffer.toString());

	         }
	         else
	           throw new SAXException("In level " + nlevel.getId() + " notes type should be notes, accidentals, custom,  intervals or chords");

	    	  break;
	       case RHYTHMLEVEL:
	    	  throw new SAXException("In level " + rlevel.getId() + " no notes type on rhythm level");
	  	       
	       case SCORELEVEL :
	    	   if (tmpclef.equals("notes") |  tmpclef.equals("custom")){
	               slevel.setNotetype(buffer.toString());
	         }
	    	   else
		           throw new SAXException("In level " + slevel.getId() + " notes type should be notes or custom");
  
	    	 break;
	     }
     

       buffer = null;
       inNotestype = false;
     }
     
     
/******************** Time signature <time>3/4</time>
*********************************************************/
     
     else if (qName.equals("time")) { //time signature
         String tmprhythms = buffer.toString();
         switch (leveltype)
  	     {
  	       case NOTELEVEL :
  	    	
  	        //   throw new SAXException("In level " + nlevel.getId() + " no time on note level");

  	       case RHYTHMLEVEL:

  
  	           StringTokenizer st=new StringTokenizer(tmprhythms,"/");
  	           Integer p;
  	           //System.out.println("Tokens"+st.countTokens());
  	           if (st.countTokens() != 2) throw new SAXException("In level " + rlevel.getId() + " time should be like n/m");
  	           else {
  	        	 p = Integer.parseInt(st.nextToken());
  	        	 	if (p == 2) rlevel.setTimeSignNumerator(2);
  	        	  else if (p == 3) rlevel.setTimeSignNumerator(3);
 	        	  else if (p == 4) rlevel.setTimeSignNumerator(4);
 	        	  else if (p == 6) rlevel.setTimeSignNumerator(6);
 	        	  else throw new SAXException("In level " + rlevel.getId() + " numerator should be  2, 3, 4, 6  ");
  	        
  	        	 	p = Integer.parseInt(st.nextToken());
  	    
  	         	 	if (p == 4 & ((rlevel.getTimeSignNumerator() == 2)|(rlevel.getTimeSignNumerator() == 3)|(rlevel.getTimeSignNumerator() == 4))) {
  	         	 		rlevel.setTimeSignDenominator(4);
  	         	 		rlevel.setTimeDivision(1);
  	         	 	}
  	         	 	
    	        	else if (p == 8 & (rlevel.getTimeSignNumerator() == 6)) {
  	         	 		rlevel.setTimeSignDenominator(8);
  	         	 		rlevel.setTimeDivision(2);
  	         	 	}
   	        	  else throw new SAXException("In level " + rlevel.getId() + " denominator should be  4,8  ");
  	           }
  	          
  	           
  	  	         break;
  	       case SCORELEVEL :
  	    	    StringTokenizer sts=new StringTokenizer(tmprhythms,"/");
   	           Integer ps;
   	           //System.out.println("Tokens"+st.countTokens());
   	           if (sts.countTokens() != 2) throw new SAXException("In level " + slevel.getId() + " time should be like n/m");
   	           else {
   	        	 ps = Integer.parseInt(sts.nextToken());
   	        	 	if (ps == 2) slevel.setTimeSignNumerator(2);
   	        	  else if (ps == 3) slevel.setTimeSignNumerator(3);
  	        	  else if (ps == 4) slevel.setTimeSignNumerator(4);
  	        	  else if (ps == 6) slevel.setTimeSignNumerator(6);
  	        	  else throw new SAXException("In level " + slevel.getId() + " numerator should be  2, 3, 4, 6  ");
   	        
   	        	 	p = Integer.parseInt(sts.nextToken());
   	    
   	         	 	if (p == 4 & ((slevel.getTimeSignNumerator() == 2)|(slevel.getTimeSignNumerator() == 3)|(slevel.getTimeSignNumerator() == 4))) {
   	         	 		slevel.setTimeSignDenominator(4);
   	         	 		slevel.setTimeDivision(1);
   	         	 	}
   	         	 	
     	        	else if (p == 8 & (slevel.getTimeSignNumerator() == 6)) {
   	         	 		slevel.setTimeSignDenominator(8);
   	         	 		slevel.setTimeDivision(2);
   	         	 	}
    	        	  else throw new SAXException("In level " + slevel.getId() + " denominator should be  4,8  ");
   	           }
   	          
   	           
   	  	         break;
  	     }
         buffer = null;
         inTime = false;
       }
  
     
     
/******************** Rhythms <rhythms>1,2,4,8</rhythms> 1 whole note ...
************************************************************************/
     
     else if (qName.equals("rhythms")) {
         String tmprhythms = buffer.toString();
         switch (leveltype)
  	     {
  	       case NOTELEVEL :
  	    	
  	           throw new SAXException("In level " + nlevel.getId() + " no rhythms on note level");


  	       case RHYTHMLEVEL:

  
  	           StringTokenizer st=new StringTokenizer(tmprhythms,",;");
  	           Integer p;
  	           
  	           rlevel.adjustLevel(false, false, false, false, false, false, false);
  	           while ( st.hasMoreTokens() ) {
  	        	    p = Integer.parseInt(st.nextToken());
  	        	  if (p == 1 && rlevel.getTimeSignNumerator()==4 && rlevel.getTimeSignDenominator()==4) rlevel.setWholeNote(true);
  	        	  else if (p == 2) rlevel.setHalfNote(true);
  	        	  else if (p == 3 && rlevel.getTimeSignNumerator()>=3 && rlevel.getTimeSignDenominator()==4) rlevel.setDottedHalfNote(true);
  	        	  else if (p == 4) rlevel.setQuarterNote(true);
  	        	  else if (p == 8) rlevel.setEighthNote(true);
  	        	  else throw new SAXException("In level " + rlevel.getId() + " rhythms should be 1, 2, 3, 4, 8  ");
  	        	    
  	        	}
  	           
  	  	
  	           
  	  	         break;
  	  	         
  	  	         
  	       case SCORELEVEL :
  	    	   StringTokenizer sts=new StringTokenizer(tmprhythms,",;");
  	           Integer ps;
  	           
  	           slevel.adjustLevel(false, false, false, false, false, false);
  	           while ( sts.hasMoreTokens() ) {
  	        	    ps = Integer.parseInt(sts.nextToken());
  	        	  if (ps == 1 && slevel.getTimeSignNumerator()==4 && slevel.getTimeSignDenominator()==4) slevel.setWholeNote(true);  	        	  
  	        	  else if (ps == 2) slevel.setHalfNote(true);
  	        	  else if (ps == 3 && slevel.getTimeSignNumerator()>=3 && slevel.getTimeSignDenominator()==4) slevel.setDottedHalfNote(true);	        	  
  	        	  else if (ps == 4) slevel.setQuarterNote(true);
  	        	  else if (ps == 8) slevel.setEighthNote(true);
  	        	  else throw new SAXException("In level " + slevel.getId() + " rhythms should be 1, 2, 3, 4, 8  ");
  	        	    
  	        	}
  	           
  	  	         break;
  	     }

         buffer = null;
         inRhythms = false;
       }
     

     /******************** Rests <rests>1,2,4,8</rests> actually only boolean
     ************************************************************************/
          
          else if (qName.equals("rests")) {
              String tmprhythms = buffer.toString();
              switch (leveltype)
       	     {
       	       case NOTELEVEL :
       	    	
       	           throw new SAXException("In level " + nlevel.getId() + " no rests on note level");


       	       case RHYTHMLEVEL:

       
       	           StringTokenizer st=new StringTokenizer(tmprhythms,",;");
       	           Integer p;
       	           
       	           while ( st.hasMoreTokens() ) {
       	        	   // System.out.println(p);
       	        	    p = Integer.parseInt(st.nextToken());
       	        	  if (p == 1) rlevel.setSilence(true);
       	        	  else if (p == 2) rlevel.setSilence(true);
       	        	  else if (p == 3) rlevel.setSilence(true);
       	        	  else if (p == 4) rlevel.setSilence(true);
       	        	  else if (p == 8) rlevel.setSilence(true);
       	        	  else throw new SAXException("In level " + rlevel.getId() + " rests should be 1, 2, 3, 4, 8  ");
       	        	    
       	        	}
       	           
       	  	         break;
       	       case SCORELEVEL :
       	    	   StringTokenizer sts=new StringTokenizer(tmprhythms,",;");
       	           Integer ps;
       	           
       	           while ( sts.hasMoreTokens() ) {
       	        	   // System.out.println(p);
       	        	    ps = Integer.parseInt(sts.nextToken());
       	        	  if (ps == 1) slevel.setSilence(true);
       	        	  else if (ps == 2) slevel.setSilence(true);
       	        	  else if (ps == 3) slevel.setSilence(true);
       	        	  else if (ps == 4) slevel.setSilence(true);
       	        	  else if (ps == 8) slevel.setSilence(true);
       	        	  else throw new SAXException("In level " + slevel.getId() + " rests should be 1, 2, 3, 4, 8  ");
       	        	    
       	        	}
       	           
       	  	         break;
       	     }

              buffer = null;
              inRests = false;
            }
     
     
/******************** Truplets <tuplets>3</tuplets> actually only 3 for triplet
************************************************************************/
           
           else if (qName.equals("tuplets")) {
               String tmprhythms = buffer.toString();
               switch (leveltype)
        	     {
        	       case NOTELEVEL :
        	    	
        	           throw new SAXException("In level " + nlevel.getId() + " no triplets on note level");


        	       case RHYTHMLEVEL:
    
        	           StringTokenizer st=new StringTokenizer(tmprhythms,",;");
        	           Integer p;
        	           
        	
        	           while ( st.hasMoreTokens() ) {
        	        	    p = Integer.parseInt(st.nextToken());

        	        	  if (p == 3) rlevel.setTriplet(true);
      
        	        	  else throw new SAXException("In level " + rlevel.getId() + " rests should be 1, 2, 4, 8  ");
        	        	    
        	        	}
        	           
        	  	         break;
        	       case SCORELEVEL :
        	    	   StringTokenizer sts=new StringTokenizer(tmprhythms,",;");
        	           Integer ps;
        	           
      
        	           while ( sts.hasMoreTokens() ) {
        	        	    ps = Integer.parseInt(sts.nextToken());

        	        	  if (ps == 3) rlevel.setTriplet(true);
      
        	        	  else throw new SAXException("In level " + slevel.getId() + " rests should be 1, 2, 4, 8  ");
        	        	    
        	        	}
        	           
        	  	         break;
        	     }

               buffer = null;
               inTuplets = false;
             }

     
/******************** Metronome <metronome>sound</metronome> none sound visual both
************************************************************************/
                
                else if (qName.equals("metronome")) {
    
                    switch (leveltype)
             	     {
             	       case NOTELEVEL :
             	    	
             	           throw new SAXException("In level " + nlevel.getId() + " no metronome on note level");


             	       case RHYTHMLEVEL:
         
             	    	   String tmpmetronome= buffer.toString();
             	          if (tmpmetronome.equals("none")){
             	            rlevel.setMetronome(false);
             	            rlevel.setMetronomeBeats(false);
             	          }
             	          else if (tmpmetronome.equals("sound")){
              	            rlevel.setMetronome(true);
              	            rlevel.setMetronomeBeats(false);
              	          }
             	         else if (tmpmetronome.equals("visual")){
             	        	   rlevel.setMetronome(false);
             	        	   rlevel.setMetronomeBeats(true);
               	          }
             	        else if (tmpmetronome.equals("both")){
             	        	rlevel.setMetronome(true);
                	         rlevel.setMetronomeBeats(true);
                	          }
             	          else
             	         throw new SAXException("In level " + rlevel.getId() + " metronome should be none sound visual both");
             	           
             	  	     break;
             	  	     
             	       case SCORELEVEL :
              	   
              	         
             	    	   String tmpmetronomes= buffer.toString();
             	          if (tmpmetronomes.equals("none")){
             	            slevel.setMetronome(false);
             	            slevel.setMetronomeBeats(false);
             	          }
             	          else if (tmpmetronomes.equals("sound")){
              	            slevel.setMetronome(true);
              	            slevel.setMetronomeBeats(false);
              	          }
             	         else if (tmpmetronomes.equals("visual")){
             	        	   slevel.setMetronome(false);
             	        	   slevel.setMetronomeBeats(true);
               	          }
             	        else if (tmpmetronomes.equals("both")){
             	        	slevel.setMetronome(true);
                	        slevel.setMetronomeBeats(true);
                	          }
             	          else
             	         throw new SAXException("In level " + slevel.getId() + " metronome should be none sound visual both");
             	           
             	  	     break;
             	     }

                    buffer = null;
                    inMetronome = false;
                  }
     
     
/******************** Nbnotes for note reading <nbnotes>3</nbnotes>   
*******************************************************************/    
     
     else if (qName.equals("nbnotes")) {
    	 
         switch (leveltype)
 	     {
 	       case NOTELEVEL :
 	    	   
		       try {
		         int temp = Integer.parseInt(buffer.toString());
		         if (temp == 3 | temp == 5 | temp == 7 | temp == 9 | temp == 15){
		           nlevel.setNbnotes(temp);
		           //level.updatenbnotes();
		         }
		         else
		           throw new SAXException("In level " + nlevel.getId() + " number of notes should be 3, 5, 7, 9 or 15");
		
		     
		       }
		       catch (Exception e) {
		         //erreur, le contenu de id n'est pas un entier
		         throw new SAXException(e);
		       }
		       break;
 	      case RHYTHMLEVEL:
 	    	//  throw new SAXException("In level " + rlevel.getId() + " no number notes on rhythm level");
 	    
 	     case SCORELEVEL:
 	    	  try {
 		         int temp = Integer.parseInt(buffer.toString());
 		         if ( temp == 9 | temp == 15){
 		           slevel.setNbnotes(temp);
 		           //level.updatenbnotes();
 		         }
 		         else
 		           throw new SAXException("In level " + nlevel.getId() + " number of notes should be , 9 or 15");
 		
 		     
 		       }
 		       catch (Exception e) {
 		         //erreur, le contenu de id n'est pas un entier
 		         throw new SAXException(e);
 		       }

 	     }
         buffer = null;
         inNbnotes = false;
     }
 
     
/******************** List pitches  <pitches>60,63,68</pitches>   
*******************************************************************/    
     else if (qName.equals("pitches")) {
         
           String temp = buffer.toString();
           StringTokenizer st=new StringTokenizer(temp,",;");
           ArrayList<Integer> pitcheslist =  new ArrayList<Integer>(); 
           Integer p;
           
           switch (leveltype)
           {
   	       case NOTELEVEL :
           
	           while ( st.hasMoreTokens() ) {
	        	   // System.out.println(p);
	        	    p = Integer.parseInt(st.nextToken());
	        	  System.out.println(p);
	        	    if ( nlevel.isCurrentKeyTreble()) {
	        	    	if (p >= 47 & p <= 96)	    pitcheslist.add(p);
	        	    	else   throw new SAXException("In level " + nlevel.getId() + " pitches should be list pitch 47 to 96");
	        	    }
	        	    	
	        	    else if ( nlevel.isCurrentKeyBass()) {
	        	    	if (p >= 26 & p <= 74)	    pitcheslist.add(p);
	      	      		else   throw new SAXException("In level " + nlevel.getId() + " pitches should be list pitch 26 to 74");
	        	    }
	        	    
	      	    else if ( nlevel.isCurrentKeyBoth()) {       	    	
	        	    	if (p >= 26 & p <= 96)	    pitcheslist.add(p);
	        	    	 else   throw new SAXException("In level " + nlevel.getId() + " pitches should be list pitch 26 to 96");
	
	        	    }	    
	        	}
	           nlevel.setPitcheslist(pitcheslist);
	           break;
	           
   	    case RHYTHMLEVEL:
	    	  throw new SAXException("In level " + rlevel.getId() + " no pitches on rhythm level");
	    
	    case SCORELEVEL: //to do
	    	 while ( st.hasMoreTokens() ) {
	        	   // System.out.println(p);
	        	    p = Integer.parseInt(st.nextToken());
	        	  System.out.println(p);
	        	    if ( slevel.isCurrentKeyTreble()) {
	        	    	if (p >= 55 & p <= 84)	    pitcheslist.add(p); //SOL DO
	        	    	else   throw new SAXException("In level " + slevel.getId() + " pitches should be list pitch 55 to 84");
	        	    }
	        	    	
	        	    else if ( slevel.isCurrentKeyBass()) {
	        	    	if (p >= 35 & p <= 64)	    pitcheslist.add(p); //E-2 E0
	      	      		else   throw new SAXException("In level " + slevel.getId() + " pitches should be list pitch 35 to 64");
	        	    }
	        	    
	      	    
	        	}
	           slevel.setPitcheslist(pitcheslist);
	           break;
           }

           buffer = null;
           inPitches = false;
       }
     
     else if (qName.equals("intervals")) {
    	   switch (leveltype)
   	     {
   	       case NOTELEVEL :
   	    	   
   	        String tmpintervals = buffer.toString();
   	       if (tmpintervals.equals("second") | tmpintervals.equals("third") | tmpintervals.equals("fourth") |
   	           tmpintervals.equals("fifth") | tmpintervals.equals("sixth") | tmpintervals.equals("seventh") |
   	       tmpintervals.equals("octave") | tmpintervals.equals("random") ){
   	         nlevel.setIntervaltype(tmpintervals);
   	       }
   	       else
   	      throw new SAXException("In level " + nlevel.getId() + " interval type should be random or second, third ...");
   	    break;
  		  
   	      case RHYTHMLEVEL:
   	    	  throw new SAXException("In level " + rlevel.getId() + " no number notes on rhythm level");
   	    
   	     case SCORELEVEL:
   	      throw new SAXException("In level " + slevel.getId() + " no number notes on rhythm level");
   	     }
    	   buffer = null;
    	   inIntervals = false;
       }
    	 





     else if (qName.equals("speed")) {
    	 
    	   switch (leveltype)
    	     {
    	       case NOTELEVEL :
    	    	      try {
    	    	          int temp = Integer.parseInt(buffer.toString());
    	    	          if (temp >= 0 & temp <= 40)
    	    	            nlevel.setSpeed(temp);
    	    	          else
    	    	            throw new SAXException("In level " + nlevel.getId() + " speed should be an integer between 0 and 40");
    	    	       
    	    	        }
    	    	        catch (Exception e) {
    	    	          //erreur, le contenu de id n'est pas un entier
    	    	          throw new SAXException(e);
    	    	        }
    	    	        break;

    	       case RHYTHMLEVEL:

    
    	    	      try {
    	    	          int temp = Integer.parseInt(buffer.toString());
    	    	          if (temp >= 0 & temp <= 200)
    	    	            rlevel.setSpeed(temp);
    	    	          else
    	    	            throw new SAXException("In level " + rlevel.getId() + " speed should be an integer between 0 and 200");
	         
    	    	        }
    	    	        catch (Exception e) {
    	    	          //erreur, le contenu de id n'est pas un entier
    	    	          throw new SAXException(e);
    	    	        }
    	           
    	  	         break;
    	       case SCORELEVEL :
    	    	   try {
 	    	          int temp = Integer.parseInt(buffer.toString());
 	    	          if (temp >= 0 & temp <= 200)
 	    	            slevel.setSpeed(temp);
 	    	          else
 	    	            throw new SAXException("In level " + slevel.getId() + " speed should be an integer between 0 and 200");
 	    	
 	    	        }
 	    	        catch (Exception e) {
 	    	          //erreur, le contenu de id n'est pas un entier
 	    	          throw new SAXException(e);
 	    	        }
 	           
 	  	         break;
    	     }
    	   buffer = null;
	       inSpeed = false;
     }

     
     else if (qName.equals("learningduration")) {
    	 
  	   switch (leveltype)
 	     {
 	       case NOTELEVEL :
 	    	   
 	    	  try {
 	             int temp = Integer.parseInt(buffer.toString());
 	             if (temp >= 10 & temp <= 100)
 	               nlevel.setLearningduration(temp);
 	             else
 	               throw new SAXException("In level " + nlevel.getId() + " learning duration should be an integer between 10 and 100");
 	             buffer = null;
 	             inDuration = false;
 	           }
 	           catch (Exception e) {
 	             //erreur, le contenu de id n'est pas un entier
 	             throw new SAXException(e);
 	           }
 	          break;
		  
 	      case RHYTHMLEVEL:
 	    	//  throw new SAXException("In level " + rlevel.getId() + " no number notes on rhythm level");
 	    
 	     case SCORELEVEL:
 	   //   throw new SAXException("In level " + slevel.getId() + " no number notes on rhythm level");
 	     }
  	   buffer = null;

     }
     
      
    
     else if (qName.equals("startingnote")) {
    
   switch (leveltype)
   	 {
     case NOTELEVEL :
   	   
    	   try {
    	          int temp = Integer.parseInt(buffer.toString());
    	          if (!nlevel.moveBasenote(temp))
    	              throw new SAXException("In level " + nlevel.getId() + " starting note is out of range");

    	          buffer = null;
    	          inStartingnote = false;
    	     }
    	     catch (Exception e) {
    	        //erreur, le contenu de id n'est pas un entier
    	        throw new SAXException(e);
    	      }
    	     break;

	  
      case RHYTHMLEVEL:
    	//  throw new SAXException("In level " + rlevel.getId() + " no number notes on rhythm level");
    
     case SCORELEVEL:
     // throw new SAXException("In level " + slevel.getId() + " no number notes on rhythm level");
     }
	   buffer = null;

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
