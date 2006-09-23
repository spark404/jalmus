package pck_jlm;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;

import java.lang.Math;
import java.lang.Integer;






public class Key extends Rectangle {
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



                public void on(ChannelData cc, boolean midiok) {
                  setNoteState(ON);

                    cc.jouenote(midiok, kNum);
                }

                public void off(ChannelData cc, boolean midiok) {
                  setNoteState(OFF);
                 cc.stopnote(midiok, kNum);

                }

                public void setNoteState(int state) {
                  noteState = state;
                }
            } // End class Key
