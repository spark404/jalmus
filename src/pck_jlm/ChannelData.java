package pck_jlm;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */

import javax.sound.midi.*;

public class ChannelData {

       MidiChannel channel;
       boolean solo, mono, mute, sustain;
       int velocity, pressure, bend, reverb;
       int row, col, num;

       public ChannelData(MidiChannel channel, int num) {
           this.channel = channel;
           this.num = num;
           velocity = pressure = bend = reverb = 64;
        }

        public MidiChannel getchannel(){
          return this.channel;
        }

        public void jouenote(boolean midiok, int kNum){
          if (midiok)
                   this.channel.noteOn(kNum, 25);
               }

        public void stopnote(boolean midiok, int kNum){
               if (midiok)
                        this.channel.noteOff(kNum, 25);
                    }

        public void stopnotes(boolean midiok){
          this.channel.allNotesOff();
        }




}
