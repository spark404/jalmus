package net.jalmus;

/**
 * <p>Title: Java Lecture Musicale</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author RICHARD Christophe
 * @version 1.0
 */

import javax.sound.midi.MidiChannel;

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

        public void playNote(boolean midiok, int kNum){
          if (midiok)
                   this.channel.noteOn(kNum, 25);
               }
        
        public void playNote(boolean midiok, int kNum, int i){
            if (midiok)
                     this.channel.noteOn(kNum, i);
                 }

        public void stopnote(boolean midiok, int kNum){
               if (midiok)
                        this.channel.noteOff(kNum, 25);
                    }

        public void stopnotes(){
          this.channel.allNotesOff();
        }




}
