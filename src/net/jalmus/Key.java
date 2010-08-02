package net.jalmus;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */

import java.awt.Rectangle;

public class Key extends Rectangle {
  /**
	 * 
	 */
				private static final long serialVersionUID = 1L;
				final int ON = 0, OFF = 1;
                int noteState = OFF;
                int kNum;

                public Key(int x, int y, int width, int height, int num) {
                  super(x, y, width, height);
                  kNum = num;
                }

                public int Getknum(){
                  return this.kNum;
                }

                public boolean isNoteOn() {
                  return noteState == ON;
                }



                public void on(net.jalmus.ChannelData cc, boolean midiok) {
                  setNoteState(ON);

                    cc.jouenote(midiok, kNum);
                }

                public void off(net.jalmus.ChannelData cc, boolean midiok) {
                  setNoteState(OFF);
                 cc.stopnote(midiok, kNum);

                }

                public void setNoteState(int state) {
                  noteState = state;
                }
            } // End class Key
