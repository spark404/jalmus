/* Jalmus is an application to learn or perfect its music reading.

    FOR MIDICOMMON AND DUMPRECEIVER CLASS (http://www.jsresources.org/)
Copyright (c) 1999 - 2001 by Matthias Pfisterer <Matthias.Pfisterer@web.de>
Copyright (c) 2003 by Florian Bomers


Copyright (C) 2003-2011  RICHARD Christophe

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

/*  FOR KEY AND PIANO CLASS

* @(#)MidiSynth.java	1.15	99/12/03
*
* Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
*
* Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
* modify and redistribute this software in source and binary code form,
* provided that i) this copyright notice and license appear on all copies of
* the software; and ii) Licensee does not utilize the software in a manner
* which is disparaging to Sun.
*
* This software is provided "AS IS," without a warranty of any kind. ALL
* EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
* IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
* NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
* LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
* OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
* LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
* INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
* CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
* OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGES.
*
* This software is not designed or intended for use in on-line control of
* aircraft, air traffic, aircraft navigation or aircraft communications; or in
* the design, construction, operation or maintenance of any nuclear
* facility. Licensee represents and warrants that it will not use or
* redistribute the Software for such purposes.
*/

/********************/
/*FRENCH TRANSLATION*/
/********************/

/*Jalmus est un logiciel pour apprendre ou perfectionner sa lecture musicale.
Copyright (C) 2003-2010 RICHARD Christophe
Ce programme est libre, vous pouvez le redistribuer et/ou
le modifier selon les termes de la Licence Publique Gï¿½nï¿½rale GNU
publiï¿½e par la Free Software Foundation (version 2
ou bien toute autre version ultï¿½rieure choisie par vous).

Ce programme est distribuï¿½ car potentiellement utile,
mais SANS AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
commercialisation ou d'adaptation dans un but spï¿½cifique. Reportez-vous Ã  la
Licence Publique GÃ©nÃ©rale GNU pour plus de dï¿½tails.

Vous devez avoir reï¿½u une copie de la Licence Publique Gï¿½nï¿½rale GNU
en mï¿½me temps que ce programme ; si ce n'est pas le cas, ï¿½crivez ï¿½ la Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, Etats-Unis.
*/

/* Web : http://jalmus.netr
E-mail : cvrichard@infonie.fr */

/* NEED JDK 1.4.2 */

package net.jalmus;

import org.xml.sax.SAXException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.String;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
//import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Jalmus extends JFrame implements KeyListener, ActionListener, ItemListener {

    //----------------------------------------------------------------
    // Translation variables

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String tlicence;

    private String tcredits;

    private String pasclavier="Pas de clavier MIDI             ";
    private String seconde;
    private String tierce;
    private String quarte;
    private String quinte;
    private String sixte;
    private String septieme;
    private String octave;
    private String mineur;
    private String majeur;

    private String DO;
    private String RE;
    private String MI;
    private String FA;
    private String SOL;
    private String LA;
    private String SI;

    private String language="en";

    Font MusiSync; // font used to render scores 
    private ResourceBundle bundle;
    private final Collection<Localizable> localizables=new ArrayList<Localizable>();

    //----------------------------------------------------------------
    // Main variables

    private int selectedGame; // FIRSTSCREEN, NOTEREADING, RHYTHMREADING, SCOREREADING
    private static int FIRSTSCREEN = 0;
    private static int NOTEREADING = 1;
    private static int RHYTHMREADING = 2;
    private static int SCOREREADING = 3;

    //----------------------------------------------------------------
    // Lesson variables
    private Lessons currentlesson=new Lessons();
    private boolean isLessonMode;
    private JMenu lessonsMenu=new JMenu();
    private JMenu[] lessonsMenuDir=new JMenu[16];
    private JMenuItem[][] lessonsMenuItem=new JMenuItem[16][26];
    private String path;
    private String[] pathsubdir = new String[16];

    //----------------------------------------------------------------
    // Note reading variables

    // Midi Resources

    private MidiDevice inputDevice;
    private MidiDevice outputDevice = null;
    private Synthesizer syn;
    private Instrument[] instruments;
    private int noteDuration=2000;
    private ChannelData currentChannel; // current channel
    private boolean open;

    private Piano piano;
  //  private int transpose = 0;  // number of 1/2tons for MIDI transposition -24 +24

    // Animation Resources

    private RenderingThread renderingThread=new RenderingThread();
    private Anim panelanim=new Anim();
    private Image jbackground;

    private Note ncourante=new Note("", "", 0, 25, 0);
    private Chord acourant=new Chord(ncourante, ncourante, ncourante, "", 0);
    private Interval icourant=new Interval(ncourante, ncourante, "");

/*
 *  ************************************* SCORE LAYOUT ***************************************
 * 
    | window |keyWidth|alteration|timeSign|  noteDistance  
    | Margin |        |  Width   | Width  |    /------\    
    |        |        |          |        |    |      |    
    |         ---GG------#---------------------|------|---------------------------------------
    |         ----G----------#-------4---------|-----O----------------------------------------
    |         --GG---------#---------4--------O-----------------------------------------------
    |         --G-G---------------------------------------------------------------------------
    |         ---G----------------------------------------------------------------------------
 *
 *  ******************************************************************************************
 *	
*/
    
    private int windowMargin = 50; // margin from the window border
    private int keyWidth = 30; // width of score keys
    private int alterationWidth = 0; // width of alterations symbols. None by default
    private int timeSignWidth = 30; // width of current score time signature symbol. This includes also the first note margin

    private int scoreYpos=110; // Y coordinate of the first row of the score
    private int rowsDistance = 100; // distance in pixel between staff rows
    private int numberOfMeasures = 2; // number of measures in a single row
    private int numberOfRows = 4; // number of score rows
    private int notesShift = 10; // space in pixel to align notes to the score layout
    private int noteDistance = 72; // distance in pixel between 1/4 notes
    

    private int posnote=1; // current position of the note within a chor or an interval

    private boolean alterationok;

    private int notemargin=220; // margin for note reading
    private int firstNoteXPos = windowMargin + keyWidth + alterationWidth + timeSignWidth + notesShift;

    private Score currentScore=new Score();

    private NoteLevel noteLevel=new NoteLevel();

    // Learning Game

    private int notecounter=1;

    // Line Game
    private Note[] ligne=new Note[40]; // array of notes
    private Chord[] ligneacc=new Chord[40]; // array of chords
    private Interval[] ligneint=new Interval[40];
    private int position; // position of the current note in the list
    private int precedente; // position of the previous note to avoid repetitions

    private boolean parti; //  partie commencï¿½e ou non
    private boolean paused;

    private boolean midierror;

    //----------------------------------------------------------------
    // Rhythm reading variables

    private RhythmLevel rhythmLevel=new RhythmLevel();
    
    private ArrayList<Rhythm> rhythms = new ArrayList<Rhythm>(); 
    private int rhythmIndex=-1; // index of the current note in the list
    private ArrayList<RhythmAnswer> answers = new ArrayList<RhythmAnswer>();
    private int rhythmAnswerScoreYpos=100; //distance to paint answer
    private float rhythmCursorXpos = firstNoteXPos - noteDistance; // X position of the cursor on the score during rhythm game
    private int rhythmCursorXStartPos = firstNoteXPos - noteDistance;
    private int rhythmCursorXlimit = firstNoteXPos + (4 * numberOfMeasures * noteDistance);
    private int precision = 10; //precision on control between note and answer
    private boolean samerhythms = true;
    private boolean muterhythms = false;
    private boolean paintrhythms = false;
    private boolean cursorstart = false;
    private long timestart; // timestamp of cursor at the beginning of a line
    private long latency; // synthesizer latency
    
    private int rhythmgame = 0;
    
    private int tempo=40; // sequencer time - rhythmGameSpeedComboBox button
    //private double nbtemps=4; // nombre de temps par mesure
    //private int nbmesures=8;
    private int metronomeCount=0;
    private int metronomeYPos=100;

    private Track track;
    private Track mutetrack;
    private Track metronome;
    private static final int ppq=1000;
    private Sequence sequence;
    private Sequencer sm_sequencer;



    private ScoreLevel scoreLevel=new ScoreLevel();

    //----------------------------------------------------------------
    // Menu

    // Mise en place du menu
    private JMenuBar maBarre=new JMenuBar();

    private JMenu menuParameters=new JMenu();
    private JMenuItem menuPrefs=new JMenuItem(new ImageIcon(getClass().getResource("/images/prefs.png")));
    private JMenuItem menuMidi=new JMenuItem(new ImageIcon(getClass().getResource("/images/midi.png")));
    private JMenu languages=new JMenu();
    private JRadioButtonMenuItem rblanguagefr=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagede=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagees=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageen=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageit=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageda=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagetr=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagefi=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageko=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageeo=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagepl=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguageiw=new JRadioButtonMenuItem();
    private JRadioButtonMenuItem rblanguagegr=new JRadioButtonMenuItem();


    private JMenu aide=new JMenu();
    private JMenuItem aidesommaire=new JMenuItem(new ImageIcon(getClass().getResource("/images/aide.png")));
    private JMenuItem siteinternet=new JMenuItem(new ImageIcon(getClass().getResource("/images/internet.png")));
    private JMenuItem propos=new JMenuItem(new ImageIcon(getClass().getResource("/images/about.png")));

    //----------------------------------------------------------------
    // GAME BUTTONS - NOTES/GO
    private JPanel pgamebutton=new JPanel();

    private JButton bdo;
    private JButton bre;
    private JButton bmi;
    private JButton bfa;
    private JButton bsol;
    private JButton bla;
    private JButton bsi;
    private JButton bdo2;
    private JButton bbemol;
    private JButton bdiese;
    private JButton bbemol2;
    private JButton bdiese2;
    private JPanel pnotes=new JPanel();

    private JButton startButton;    // button to start or stop game
    private JButton listenButton;    // button for listen exercise in rhythm game
    private JButton newButton;    // button for new exercise in rhythm game
    private JButton preferencesButton;  // button to access game preferences

    //----------------------------------------------------------------
    // Dialogs

    private JDialog preferencesDialog;
    private static final int NOTE_READING_TAB=0;
    private static final int RHYTM_READING_TAB=1;
    private static final int SCORE_READING_TAB=2;

    private JTabbedPane preferencesTabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT); // panel pour les parametres
    private JComboBox noteGameTypeComboBox; //Game type
    private JComboBox noteGameSpeedComboBox; // button to choose speed
    private JComboBox keyComboBox; //  drop down combo to select the key
    private JComboBox keySignatureCheckBox; // button to choose tonality
    private JPanel noteReadingNotesPanel=new JPanel(); // panel for choose type on fontes on first exercice
    private JComboBox noteGroupComboBox; // to choose notes, intervals or chords
    private JComboBox noteCountComboBox; //  to choose number of notes
    private JComboBox intervalComboBox; //  to choose intervals type
    private JComboBox chordTypeComboBox; // to choose chords type

    private JComboBox rhythmGameTypeComboBox;
    private JComboBox rhythmGameSpeedComboBox;
    private JCheckBox wholeCheckBox;
    private JCheckBox halfCheckBox;
    private JCheckBox dottedhalfCheckBox;
    private JCheckBox quarterCheckBox;
    private JCheckBox eighthCheckBox;
    private JCheckBox restCheckBox;
    private JCheckBox tripletCheckBox;
    private JLabel    timeSignLabel;
    private JComboBox timeSignComboBox;
    private JCheckBox metronomeCheckBox;
    private JCheckBox metronomeShowCheckBox;
    
    private JComboBox scoreGameTypeComboBox; //type of games
    private JComboBox scoreGameSpeedComboBox; // button to choose the speed
    private JComboBox scoreKeyComboBox; //  drop down combo box to select the key
    private JComboBox scoreNotesComboBox; // drop down combo box to choose the number of notes
    private JComboBox scoreAlterationsComboBox; // drop down combo box to choose the pitch
   
    private JCheckBox scorewholeCheckBox;
    private JCheckBox scorehalfCheckBox;
    private JCheckBox scoredottedhalfCheckBox;
    private JCheckBox scorequarterCheckBox;
    private JCheckBox scoreeighthCheckBox;
    private JCheckBox scorerestCheckBox;
    private JCheckBox scoreTripletCheckBox;
    private JLabel    scoreTimeSignLabel;
    private JComboBox scoreTimeSignComboBox;
    private JCheckBox scoreMetronomeCheckBox;
    private JCheckBox scoreMetronomeShowCheckBox;

    private int[] savePrefs=new int[30]; // for cancle button

    
    
    //----SAVE DIALOG

    private JDialog saveDialog=new JDialog();
    private JPanel labelPanel = new JPanel(new GridLayout(2, 1));
    private JPanel fieldPanel = new JPanel(new GridLayout(2, 1));

    private JPanel savePanel=new JPanel();
    private JTextField Lessonname = new JTextField(20);
    private JTextField Lessonmessage = new JTextField(20);
    private JButton oksaveButton=new JButton();
    

    
    //----

    private JDialog levelMessage=new JDialog();
    private JPanel plevelMessage=new JPanel();
    private JLabel textlevelMessage=new JLabel();
    private JPanel pButtonlevelMessage=new JPanel();
    private JButton oklevelMessage=new JButton();

    // JDialog for score message
    private JDialog scoreMessage=new JDialog();
    private JPanel pscoreMessage=new JPanel();
    private JLabel textscoreMessage=new JLabel();
    private JPanel pButtonscoreMessage=new JPanel();
    private JButton okscoreMessage=new JButton();

    //----

    private JDialog midiOptionsDialog;

    private JCheckBox soundOnCheckBox;
    private JComboBox instrumentsComboBox;
    private JComboBox keyboardLengthComboBox; // for length-number of touchs of keyboard
   //private JComboBox transpositionComboBox; // for transposition MIDI keyboard
   private JSpinner transpositionSpinner; // for transposition MIDI keyboard
   private SpinnerNumberModel m_numberSpinnerModel;
   private JLabel transpositionLabel=new JLabel();

    private JSlider latencySlider = new JSlider(JSlider.HORIZONTAL, 0, 250, 0);

    private JCheckBox keyboardsoundCheckBox;

    private JComboBox midiInComboBox;
    private JComboBox midiOutComboBox;
 
    private DefaultComboBoxModel midiOutComboBoxModel=new DefaultComboBoxModel();    
    private DefaultComboBoxModel midiInComboBoxModel=new DefaultComboBoxModel();

    private boolean selectmidi_forlang; // is true when combobox selection occures during language initialization

    private int[] sauvmidi=new int[16]; // for save midi options when cancel

    //----

    private JDialog aboutDialog;
    private JPanel papropos=new JPanel();
    private JPanel paproposboutons=new JPanel(); // panel for buttons
    private JTextArea texteapropos;

    private JButton bcredits;
    private JButton blicence;
    private JButton bfermer;

    //----
    
    //For table to choose notes on exercises
    private JDialog notesDialog;
    ChooseNotePanel ChooseNoteP;
    
    private JDialog ScorenotesDialog;
    ChooseNotePanel ScoreChooseNoteP;
    //----
    
    private JPanel principal=new JPanel(); // panel principal
    
    Properties settings = new Properties();

    //################################################################
    // Initialization methods

    private void init(String paramlanguage) {
    	
    	try{
   	      settings.load(new FileInputStream("settings.properties"));
   	    //  System.out.println("language = " + settings.getProperty("language"));
   	      settings.list(System.out);
   	      //if no language in command line then search in settings file
   	      if ("".equals(paramlanguage)) paramlanguage = settings.getProperty("language");
    	}
    	catch (Exception e) {
   	      System.out.println(e);
    	}

        if (!initializeMidi()) {
            return;
        }

        try {
        	jbackground = ImageIO.read(getClass().getClassLoader().getResource("images/bg1.png"));
        }
        catch(Exception e){
            System.out.println("Cannot load background image");
            //System.exit(0);
          }

        try {
        	InputStream fInput = this.getClass().getResourceAsStream("/images/MusiSync.ttf");
        	MusiSync = Font.createFont (Font.PLAIN, fInput);
        }
        catch(Exception e){
        	System.out.println("Cannot load MusiSync font !!");
        	System.exit(0);
        }
        
        startButton=new JButton();
        startButton.setFocusable(false);
        localizables.add(new Localizable.Button(startButton, "_start"));
        startButton.setPreferredSize(new Dimension(150, 20));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleStartButtonClicked();
            }
        });
        
        listenButton=new JButton();
        listenButton.setFocusable(false);
        localizables.add(new Localizable.Button(listenButton, "_listen"));
        listenButton.setPreferredSize(new Dimension(150, 20));
        listenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleListenButtonClicked();
            }
        });
        
        newButton=new JButton();
        newButton.setFocusable(false);
        localizables.add(new Localizable.Button(newButton, "_new"));
        newButton.setPreferredSize(new Dimension(150, 20));
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNewButtonClicked();
            }
        });

        preferencesButton=new JButton();
        preferencesButton.setFocusable(false);
        localizables.add(new Localizable.Button(preferencesButton, "_menuPreferences"));
        preferencesButton.setPreferredSize(new Dimension(150, 20));
        preferencesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePreferencesClicked();
            }
        });

        
        bundle = ResourceBundle.getBundle("language", new Locale(language));
        System.out.println(new Locale(language));
        
    	ChooseNoteP = new  ChooseNotePanel(noteLevel.getKey(),NOTEREADING,  bundle);
        ChooseNoteP.setOpaque(true); //content panes must be opaque 
        ChooseNoteP.setVisible(true);
       ChooseNoteP.okButton.addActionListener(new ActionListener() {
    	   
           public void actionPerformed(ActionEvent e)
           {
               //Execute when button is pressed
        		 if (! ChooseNoteP.atLeast3Pitches()) JOptionPane.showMessageDialog(null, "Choose at least three notes", "Warning", JOptionPane.ERROR_MESSAGE); 
        		 else    {
        			 notesDialog.setVisible(false);
        			 noteLevel.setPitcheslist(ChooseNoteP.getPitches());
        		 }
              
           }
       });   
       
   	ScoreChooseNoteP = new  ChooseNotePanel(scoreLevel.getKey(), SCOREREADING, bundle);
    ScoreChooseNoteP.setOpaque(true); //content panes must be opaque 
    ScoreChooseNoteP.setVisible(true);
   ScoreChooseNoteP.okButton.addActionListener(new ActionListener() {
	   
       public void actionPerformed(ActionEvent e)
       {
           //Execute when button is pressed
    		 if (! ScoreChooseNoteP.atLeast3Pitches()) JOptionPane.showMessageDialog(null, "Choose at least three notes", "Warning", JOptionPane.ERROR_MESSAGE); 
    		 else    {
    			 ScorenotesDialog.setVisible(false);
    			 scoreLevel.setPitcheslist(ScoreChooseNoteP.getPitches());
    		 }
          
       }
   });    
       
        bdo=new JButton();
        bdo.addActionListener(this);
        pnotes.add(bdo);
        bre=new JButton();
        bre.addActionListener(this);
        pnotes.add(bre);
        bmi=new JButton();
        bmi.addActionListener(this);
        pnotes.add(bmi);

        bfa=new JButton();
        bfa.addActionListener(this);
        pnotes.add(bfa);

        bsol=new JButton();
        bsol.addActionListener(this);
        pnotes.add(bsol);

        bla=new JButton();
        bla.addActionListener(this);
        pnotes.add(bla);

        bsi=new JButton();
        bsi.addActionListener(this);
        pnotes.add(bsi);

        bdo2=new JButton();
        bdo2.addActionListener(this);
        pnotes.add(bdo2);

        // BOUTONS POUR ACCORDS
        bdiese=new JButton();
        bdiese.setText("#");
        bdiese.addActionListener(this);

        bbemol=new JButton();
        bbemol.setText("b");
        bbemol.addActionListener(this);

        bdiese2=new JButton();
        bdiese2.setText("#");
        bdiese2.addActionListener(this);

        bbemol2=new JButton();
        bbemol2.setText("b");
        bbemol2.addActionListener(this);

        pnotes.add(bdiese, 0);
        pnotes.add(bbemol, 5);
        pnotes.add(bbemol2, 6);
        pnotes.add(bdiese2, 11);
        pnotes.setLayout(new GridLayout(2, 6));

        // BOUTONS INVISIBLES EN MODE NORMAL
        bdiese.setVisible(false);
        bdiese2.setVisible(false);
        bbemol.setVisible(false);
        bbemol2.setVisible(false);

        pgamebutton.setLayout(new FlowLayout());
        pgamebutton.add(startButton);
        pnotes.setPreferredSize(new Dimension(450, 40));
        pgamebutton.add(pnotes);
        pgamebutton.add(preferencesButton);
        pnotes.setBackground(Color.white);
        pgamebutton.setBackground(Color.white);

        /************************************************************************/
        /******************************** MENU *********************************/
        /***********************************************************************/
        preferencesDialog=buildPreferencesDialog();

        midiOptionsDialog=buildMidiOptionsDialog();

        aboutDialog=new JDialog(this, true);
        //aboutDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        aboutDialog.setResizable(false);

          notesDialog=new JDialog(this, true);
        //    notesDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        notesDialog.setResizable(false);   
        
        ScorenotesDialog=new JDialog(this, true);
        //    notesDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ScorenotesDialog.setResizable(false);   

        levelMessage=new JDialog(this, true);
        //levelMessage.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        levelMessage.setResizable(false);

        scoreMessage=new JDialog(this, true);
        //scoreMessage.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        scoreMessage.setResizable(false);
        
        saveDialog=new JDialog(this, true);
        //scoreMessage.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        saveDialog.setResizable(false);

        menuParameters.add(menuPrefs);
        menuPrefs.addActionListener(this);
        menuParameters.add(menuMidi);
        menuMidi.addActionListener(this);

        ButtonGroup group=new ButtonGroup();

        rblanguagefr=new JRadioButtonMenuItem("Fran"+"\u00E7"+"ais");
        rblanguagefr.setMnemonic(KeyEvent.VK_F);
        group.add(rblanguagefr);
        rblanguagefr.addActionListener(this);
        languages.add(rblanguagefr);

        rblanguagede=new JRadioButtonMenuItem("Deutsch");
        rblanguagede.setMnemonic(KeyEvent.VK_D);
        group.add(rblanguagede);
        rblanguagede.addActionListener(this);
        languages.add(rblanguagede);

        rblanguagees=new JRadioButtonMenuItem("Espanol");
        rblanguagees.setMnemonic(KeyEvent.VK_S);
        group.add(rblanguagees);
        rblanguagees.addActionListener(this);
        languages.add(rblanguagees);

        rblanguageen=new JRadioButtonMenuItem("English");
        rblanguageen.setMnemonic(KeyEvent.VK_E);
        rblanguageen.addActionListener(this);
        group.add(rblanguageen);
        languages.add(rblanguageen);

        rblanguageit=new JRadioButtonMenuItem("Italiano");
        rblanguageit.setMnemonic(KeyEvent.VK_I);
        rblanguageit.addActionListener(this);
        group.add(rblanguageit);
        languages.add(rblanguageit);

        rblanguageda=new JRadioButtonMenuItem("Dansk");
        rblanguageda.setMnemonic(KeyEvent.VK_A);
        rblanguageda.addActionListener(this);
        group.add(rblanguageda);
        languages.add(rblanguageda);

        rblanguagetr=new JRadioButtonMenuItem("Turkish");
        rblanguagetr.setMnemonic(KeyEvent.VK_T);
        rblanguagetr.addActionListener(this);
        group.add(rblanguagetr);
        languages.add(rblanguagetr);
        
        rblanguagefi=new JRadioButtonMenuItem("Finnish");
        rblanguagefi.setMnemonic(KeyEvent.VK_F);
        rblanguagefi.addActionListener(this);
        group.add(rblanguagefi);
        languages.add(rblanguagefi);
        
        rblanguagepl=new JRadioButtonMenuItem("Polish");
        rblanguagepl.setMnemonic(KeyEvent.VK_O);
        rblanguagepl.addActionListener(this);
        group.add(rblanguagepl);
        languages.add(rblanguagepl);
        
        rblanguageiw=new JRadioButtonMenuItem("Hebrew");
        rblanguageiw.setMnemonic(KeyEvent.VK_H);
        rblanguageiw.addActionListener(this);
        group.add(rblanguageiw);
        languages.add(rblanguageiw);
        
        rblanguageko=new JRadioButtonMenuItem("Korean");
        rblanguageko.setMnemonic(KeyEvent.VK_K);
        rblanguageko.addActionListener(this);
        group.add(rblanguageko);
        languages.add(rblanguageko);
        
        rblanguageeo=new JRadioButtonMenuItem("Esperanto");
        rblanguageeo.setMnemonic(KeyEvent.VK_N);
        rblanguageeo.addActionListener(this);
        group.add(rblanguageeo);
        languages.add(rblanguageeo);
        
        rblanguagegr=new JRadioButtonMenuItem("Greek");
        rblanguagegr.setMnemonic(KeyEvent.VK_G);
        rblanguagegr.addActionListener(this);
        group.add(rblanguagegr);
        languages.add(rblanguagegr);

        if ("es".equals(paramlanguage)) {
            rblanguagees.setSelected(true);
            language="es";
        } else if ("it".equals(paramlanguage)) {
            rblanguageit.setSelected(true);
            language="it";
        } else if ("de".equals(paramlanguage)) {
            rblanguagede.setSelected(true);
            language="de";
        } else if ("fr".equals(paramlanguage)) {
            rblanguagefr.setSelected(true);
            language="fr";
        } else if ("da".equals(paramlanguage)) {
            rblanguageda.setSelected(true);
            language="da";
        } else if ("tr".equals(paramlanguage)) {
            rblanguagetr.setSelected(true);
            language="tr";
        } else if ("fi".equals(paramlanguage)) {
            rblanguagefi.setSelected(true);
            language="fi";
        } else if ("ko".equals(paramlanguage)) {
            rblanguageko.setSelected(true);
            language="ko";
        } else if ("eo".equals(paramlanguage)) {
            rblanguageeo.setSelected(true);
            language="eo";
        } else if ("pl".equals(paramlanguage)) {
        	rblanguagepl.setSelected(true);
        	language="pl"; 
    	} else if ("iw".equals(paramlanguage)) {
    		rblanguageiw.setSelected(true);
    		language="iw";
    	}  else if ("gr".equals(paramlanguage)) {
    		rblanguagegr.setSelected(true);
    		language="gr";
    	}else {
            // must be "en"
            rblanguageen.setSelected(true);
            language="en";
        }
       
       

        languages.setIcon(new ImageIcon(getClass().getResource("/images/language.png")));

        languages.addActionListener(this);
        languages.setMnemonic(KeyEvent.VK_L);

        menuParameters.addSeparator();
        menuParameters.add(languages);
        menuParameters.setMnemonic(KeyEvent.VK_P);
        

        aide.setMnemonic(KeyEvent.VK_A);
        aide.add(aidesommaire);

        aide.add(siteinternet);
        aide.addSeparator();
        aide.add(propos);
        aidesommaire.addActionListener(this);
        siteinternet.addActionListener(this);

        propos.addActionListener(this);
        
        
        maBarre.add(buildExercisesMenu());
        buildLessonsMenu();
        maBarre.add(lessonsMenu);
        maBarre.add(menuParameters);
        maBarre.add(aide);

        setJMenuBar(maBarre);
        maBarre.setVisible(true);

        /**************************************************************/
        /**************************************************************/
        /**************************************************************/

        /***************** FENETRE A PROPOS ******************************/
        papropos.setVisible(true);
        bcredits=new JButton();

        bcredits.setIcon(new ImageIcon(getClass().getResource("/images/credits.png")));
        bcredits.addActionListener(this);

        blicence=new JButton();

        blicence.setIcon(new ImageIcon(getClass().getResource("/images/licence.png")));
        blicence.addActionListener(this);
        bfermer=new JButton();

        bfermer.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        bfermer.addActionListener(this);

        aboutDialog.setContentPane(papropos);

        texteapropos=new JTextArea(12, 25);
        JScrollPane ascenceur=new JScrollPane(texteapropos);
        texteapropos.setEditable(false);
        texteapropos.setLineWrap(true);
        texteapropos.setWrapStyleWord(true);
        texteapropos.setFont(new Font("SansSerif", Font.BOLD, 14));
        texteapropos.setBackground(getBackground());

        texteapropos.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ascenceur.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ascenceur.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        papropos.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        papropos.add(ascenceur);
        paproposboutons.add(bcredits);
        paproposboutons.add(blicence);
        blicence.setVisible(true);
        paproposboutons.add(bfermer);
        bfermer.setVisible(true);
        papropos.add(paproposboutons);

        /*******************************************************************/

       

        plevelMessage.setVisible(true);
        plevelMessage.setLayout(new GridLayout(2, 1));

        oklevelMessage=new JButton();
        oklevelMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLevelOkClicked();
            }
        });
        oklevelMessage.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));
        localizables.add(new Localizable.Button(oklevelMessage, "_buttonok"));

        textlevelMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        plevelMessage.add(textlevelMessage);
        pButtonlevelMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));

        pButtonlevelMessage.add(oklevelMessage);
        plevelMessage.add(pButtonlevelMessage);
        levelMessage.setContentPane(plevelMessage);
        levelMessage.setModal(false);
        levelMessage.setVisible(false);

        pscoreMessage.setVisible(true);
        pscoreMessage.setLayout(new GridLayout(2, 1));

        okscoreMessage=new JButton();
        okscoreMessage.addActionListener(this);
        okscoreMessage.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));

        textscoreMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        pscoreMessage.add(textscoreMessage);
        pButtonscoreMessage.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 5));

        pButtonscoreMessage.add(okscoreMessage);
        pscoreMessage.add(pButtonscoreMessage);
        scoreMessage.setContentPane(pscoreMessage);
        scoreMessage.setModal(false);
        scoreMessage.setVisible(false);
        
        labelPanel.add( new JLabel(bundle.getString("_name"), JLabel.RIGHT));
        labelPanel.add( new JLabel(bundle.getString("_description"), JLabel.RIGHT));
        fieldPanel.add(Lessonname);
        fieldPanel.add(Lessonmessage);
        
        savePanel.add(labelPanel, BorderLayout.WEST);
	   savePanel.add(fieldPanel, BorderLayout.CENTER);

       oksaveButton=new JButton();
       oksaveButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               handleOKSave();
           }
       });
       oksaveButton.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));
       oksaveButton.setText(bundle.getString("_buttonok"));
        savePanel.add(oksaveButton);
        saveDialog.setContentPane(savePanel);
        saveDialog.setVisible(false);
        
         

        /*******************************************************************/

        principal.setLayout(new BorderLayout());

        principal.add(pgamebutton, BorderLayout.NORTH);
        principal.add(panelanim, BorderLayout.CENTER);

        principal.setVisible(true);
        pgamebutton.setVisible(false);
        getContentPane().add(principal);

        panelanim.setVisible(true);
        panelanim.setBackground(Color.white);

        piano=new Piano(73, 40); //initialisation of keyboard 61 keys

        Image icone;
        
        try {
        	icone = ImageIO.read(getClass().getClassLoader().getResource("images/icon.png"));
            setIconImage(icone);
        }
        catch(Exception e){
            System.out.println("Cannot load Jalmus icon");
        }

        addKeyListener(this);
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {

                if (selectedGame==NOTEREADING) {

                    Key key=piano.getKey(e.getPoint());

                    if (piano.Getprevkey()!=null && piano.Getprevkey()!=key) {
                        piano.Getprevkey().off(currentChannel, soundOnCheckBox.isSelected() && !midierror);
                    }
                    if (key!=null && piano.Getprevkey()!=key) {
                       key.on(currentChannel, false);
                    }
                    piano.Setprevkey(key);
                    repaint();

                }
            }

        });

        addComponentListener(new java.awt.event.ComponentAdapter()
    	{
    		public void componentResized(ComponentEvent e)
    		{
    			System.out.println("Jalmus has been resized !");
    			if (selectedGame==RHYTHMREADING || selectedGame == SCOREREADING) {
    				handleNewButtonClicked();
    			}
    		}
    	});


        addMouseListener(new MouseAdapter() {
;
            
            public void mousePressed(MouseEvent e) {
                requestFocus();
     
                Dimension d=getSize();
                
                if (selectedGame==NOTEREADING) {
                	
                	if (piano.rightbuttonpressed(e.getPoint(),d.width))  noteLevel.basenotetoRight(piano);
                	if (piano.leftbuttonpressed(e.getPoint(),d.width))  noteLevel.basenotetoLeft(piano);
                    
                	repaint();
                	
                	// System.out.println (e.getPoint());
                    Key key=piano.getKey(e.getPoint());
                    piano.Setprevkey(key);
                   if (!midierror)  key.on(currentChannel,  !midierror);
                    if (key!=null) {
                        if (key.Getknum()==60 && !parti) {

                            requestFocus();
                            startNoteGame();
                            if (!renderingThread.isAlive()) {
                                renderingThread.start();
                            }
                        } else if (key!=null && parti &&!paused) {
                            key.on(currentChannel, soundOnCheckBox.isSelected() && !midierror);
                            repaint();

                            if (key.Getknum()==ncourante.getPitch()) {
                                rightAnswer();
                            } else {
                                wrongAnswer();
                            }
                        }
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedGame==NOTEREADING) {
                    if (piano.Getprevkey()!=null) {
                        piano.Getprevkey().off(currentChannel, soundOnCheckBox.isSelected() && !midierror);
                        repaint();
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
                if (selectedGame==NOTEREADING) {
                    if (piano.Getprevkey()!=null) {
                        piano.Getprevkey().off(currentChannel, soundOnCheckBox.isSelected() && !midierror);
                        repaint();
                        piano.Setprevkey(null);
                    }
                }
            }

        });
        
  

        addWindowListener(new WindowAdapter() {

            public void windowClosed(java.awt.event.WindowEvent evt) {
            	savesettings();
                dispose();
                System.exit(0);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
            	savesettings();
                dispose();
                System.exit(0);
            }

        });

        updateLang();
        
 
       
       transpositionSpinner.setValue(0);
        
        // load user preferences from settings file
     	try{
      	     
    	      settings.load(new FileInputStream("settings.properties"));
    	      if ("on".equals(settings.getProperty("sound"))) soundOnCheckBox.setSelected(true) ;
    	      else if ("off".equals(settings.getProperty("sound"))) soundOnCheckBox.setSelected(false) ;
    	      if ("on".equals(settings.getProperty("keyboardsound"))) keyboardsoundCheckBox.setSelected(true) ;
    	      else if ("off".equals(settings.getProperty("keyboardsound"))) keyboardsoundCheckBox.setSelected(false) ;
    	      int ins = Integer.parseInt(settings.getProperty("instrument"));
    	      if (ins >=0 & ins < 20) {
    	    	 instrumentsComboBox.setSelectedIndex(ins);
    	      }
    	      int k = Integer.parseInt(settings.getProperty("keyboard"));
    	      if (k> 0 & k < midiInComboBox.getItemCount()) {
    	    	  midiInComboBox.setSelectedIndex(k);
    	    	  midiInComboBoxModel.setSelectedItem(midiInComboBoxModel.getElementAt(k));
    	    	  System.out.println(midiInComboBox.getSelectedItem());
    	      }
    	      int ko = Integer.parseInt(settings.getProperty("midiout"));
    	      if (ko> 0 & ko < midiOutComboBox.getItemCount()) {
    	    	  midiOutComboBox.setSelectedIndex(ko);
    	    	  midiOutComboBoxModel.setSelectedItem(midiOutComboBoxModel.getElementAt(ko));
    	    	  System.out.println(midiOutComboBox.getSelectedItem());
    	      }
    	      int kl = Integer.parseInt(settings.getProperty("keyboardlength"));
    	      if (kl == 61) keyboardLengthComboBox.setSelectedIndex(1);
    	      else if (kl == 73) keyboardLengthComboBox.setSelectedIndex(0);
    	      
    	      int kt = Integer.parseInt(settings.getProperty("transposition"));
    	      if (kt>= -24 & kt < 24) {
    	    	  transpositionSpinner.setValue(kt);
    	      }
    	      
      	}
    	    catch (Exception e) {
    	      System.out.println(e);
    	      }

    }

    //----------------------------------------------------------------
    private JMenu buildExercisesMenu() {
        JMenuItem noteReadingMenuItem=new JMenuItem(new ImageIcon(getClass().getResource("/images/note.png")));
        localizables.add(new Localizable.Button(noteReadingMenuItem, "_menuNotereading"));
        noteReadingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNoteReadingMenuItem();
            }
        });

        JMenuItem rhythmReadingMenuItem=new JMenuItem(new ImageIcon(getClass().getResource("/images/rythme.png")));
        localizables.add(new Localizable.Button(rhythmReadingMenuItem, "_menuRythmreading"));
        rhythmReadingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRhythmReadingMenuItem();
            }
        });
        
        JMenuItem scoreReadingMenuItem=new JMenuItem(new ImageIcon(getClass().getResource("/images/score.png")));
        localizables.add(new Localizable.Button(scoreReadingMenuItem, "_menuScorereading"));
        scoreReadingMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleScoreReadingMenuItem();
            }
        });

 
        JMenuItem exitMenuItem=new JMenuItem(new ImageIcon(getClass().getResource("/images/exit.png")));
        localizables.add(new Localizable.Button(exitMenuItem, "_menuExit"));
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleExitMenuItem();
            }
        });

        JMenu exercisesMenu=new JMenu();
        localizables.add(new Localizable.Button(exercisesMenu, "_menuExercises"));
        exercisesMenu.setMnemonic(KeyEvent.VK_E);
        exercisesMenu.add(noteReadingMenuItem);
        exercisesMenu.add(rhythmReadingMenuItem);  
        exercisesMenu.add(scoreReadingMenuItem);
      //  exercisesMenu.add(lessonsMenuItem);
        exercisesMenu.addSeparator();
        exercisesMenu.add(exitMenuItem);
        return exercisesMenu;
        
      
    }

 
    
    private void buildLessonsMenu() {
    	

    localizables.add(new Localizable.Button(lessonsMenu, "_menuLessons"));
    lessonsMenu.setMnemonic(KeyEvent.VK_L);
    
    	path = currentlesson.getLessonPath(language);
    	
    	   File sousrepertoire=new File(path);

    	  if (sousrepertoire.isDirectory()) {
    	        File[] listsp=sousrepertoire.listFiles();
    	        Arrays.sort(listsp);
    	        if (listsp!=null && listsp.length <= 15) { //15 directory

    	         for   (int i=0; i<listsp.length; i++) {
        
    	                	lessonsMenuDir[i]= new JMenu(listsp[i].getName());
    	                	lessonsMenu.add(lessonsMenuDir[i]);
    	                	
    	                	pathsubdir[i] = path+File.separator+listsp[i].getName();
    	                    File repertoire=new File(pathsubdir[i]);
    	                    File[] list=repertoire.listFiles();
    	                    Arrays.sort(list);
    	                    if (list!=null && list.length <= 25) { //25 lessons max

    	                        for (int i1=0; i1<list.length; i1++) {

    	                            if ("xml".equals(FileTools.getFileExtension(list[i1]))) {
    	                            
    	                            	lessonsMenuItem[i][i1]= new JMenuItem(FileTools.getFileNameWithoutExtension(list[i1]));
    	                            	 lessonsMenuItem[i][i1].addActionListener(this);
    	                            	
    	                                lessonsMenuDir[i].add(lessonsMenuItem[i][i1]);
    	                                
    	                            }
    	                        }


    	                    }
    	                    
    	         			}


    	        }
    	  }
    	  
    	 
    	  
    	  else {
    	        System.err.println(sousrepertoire+" : Reading lessons files error.");
    	    }
    	  
    	  
   	        final Desktop desktop = null;
   	        // Before more Desktop API is used, first check
   	        // whether the API is supported by this particular
   	        // virtual machine (VM) on this particular host.
   	 


   	        
   	    
   	         lessonsMenu.addSeparator();
   	
   	   	     
   	         JMenuItem manageMenuItem=new JMenuItem(new ImageIcon(getClass().getResource("/images/folder.png")));
     	       localizables.add(new Localizable.Button(manageMenuItem, "_menuBrowse"));
     	        manageMenuItem.addActionListener(new ActionListener() {
     	            public void actionPerformed(ActionEvent e) {
     	            	  File sousrepertoire=new File(path);
     	            	 OpenDirectory(sousrepertoire);
     	            }
     	        });	
     	   	     lessonsMenu.add(manageMenuItem);
   	        
   	        
   	   
   // 	path = path+"/Basic";
  //  File repertoire=new File(path);

  //  bLessons.removeAllItems();
   
    //localizables.add(new Localizable.Button(lessonsMenuItem, "_menuLessons"));
   

    }
    
    //----------------------------------------------------------------
    private boolean initializeMidi() {
        try {
            if (syn==null) {
                if ((syn=MidiSystem.getSynthesizer())==null) {
                    System.out.println("getSynthesizer() failed!");

                    return false;
                }
            }
            syn.open();
        }
        catch (MidiUnavailableException e) {
            System.out.println("Midiunavailable : sortie MIDI occupee - fermez toutes les autres applications pour avoir du son. "+e);
            midierror=true;
        }

        if (!midierror) {
            Soundbank sb=syn.getDefaultSoundbank();
            if (sb!=null) {
                instruments=syn.getDefaultSoundbank().getInstruments();

                if (instruments!=null) {
                    syn.loadInstrument(instruments[0]);

                } else {
                    midierror=true;
                    System.out.println("Soundbank null");
                }
            }

            MidiChannel[] mc=syn.getChannels();

            ChannelData[] channels=new ChannelData[mc.length];
            for (int i=0; i<channels.length; i++) {
                channels[i]=new ChannelData(mc[i], i);
            }
            currentChannel=channels[0];
        }
        return true;
    }

    //----------------------------------------------------------------
    private JDialog buildMidiOptionsDialog() {
    	
	    /* Sound panel */

        soundOnCheckBox=new JCheckBox("", true);
   
  	    
        instrumentsComboBox=new JComboBox();
        if (instruments!=null) {
            for (int i=0; i<20; i++) {
                instrumentsComboBox.addItem(instruments[i].getName());
            }
        } else {
            instrumentsComboBox.addItem("No instrument available");
            System.out.println("No soundbank file : http://java.sun.com/products/java-media/sound/soundbanks.html");
        }
        instrumentsComboBox.addItemListener(this);
/*
        audioDriverComboBox=new JComboBox(AsioDriver.getDriverNames().toArray());
        audioDriverComboBox.addItem("WDM Java Sound");
        audioDriverComboBox.addItemListener(this);
*/
        JPanel soundPanel=new JPanel(); // panel midi keyboard
        localizables.add(new Localizable.NamedGroup(soundPanel, "_sound"));
        
        keyboardsoundCheckBox=new JCheckBox("", false);
        
        JPanel keyboardSoundPanel=new JPanel();
        keyboardSoundPanel.add(soundOnCheckBox);
        keyboardSoundPanel.add(keyboardsoundCheckBox);
        keyboardSoundPanel.add(instrumentsComboBox);

        soundPanel.setLayout(new BorderLayout());
        //soundPanel.add(audioDriverComboBox, BorderLayout.NORTH);
        soundPanel.add(keyboardSoundPanel, BorderLayout.CENTER);

        /* Latency - Cursor Speed panel */

        JPanel latencyPanel=new JPanel();
        latencyPanel.add(latencySlider);
        latencySlider.setMajorTickSpacing(50);
        latencySlider.setMinorTickSpacing(10);
        latencySlider.setPaintTicks(true);
        latencySlider.setPaintLabels(true);
/*
        latencyPanel.add(speedcursorSlider);
        speedcursorSlider.setMajorTickSpacing(50);
        speedcursorSlider.setMinorTickSpacing(10);
        speedcursorSlider.setPaintTicks(true);
        speedcursorSlider.setPaintLabels(true);
*/        
    	try{
            latencySlider.setValue(Integer.parseInt(settings.getProperty("latency")));	 
            //speedcursorSlider.setValue(Integer.parseInt(settings.getProperty("speedcursor")));	  
    	}
  	    catch (Exception e) {
  	      System.out.println(e);
  	      }
     localizables.add(new Localizable.NamedGroup(latencyPanel, "_latency"));

        // ----

        midiInComboBoxModel.addElement(pasclavier);
        midiOutComboBoxModel.addElement(pasclavier);
        MidiDevice.Info[] aInfos=MidiSystem.getMidiDeviceInfo();
        for (int i=0; i<aInfos.length; i++) {
            try {
                MidiDevice device=MidiSystem.getMidiDevice(aInfos[i]);
                boolean bAllowsInput=(device.getMaxTransmitters()!=0);
                boolean bAllowsOutput=(device.getMaxReceivers()!=0);
                
                if (bAllowsInput) {
                    midiInComboBoxModel.addElement(aInfos[i].getName());
                }
                
                if (bAllowsOutput) {
                	midiOutComboBoxModel.addElement(aInfos[i].getName());
                }

            }
            catch (MidiUnavailableException e) {
            }
        }
               
      
        midiInComboBox=new JComboBox();
        midiInComboBox.setModel(midiInComboBoxModel);
        midiInComboBox.addItemListener(this);

        midiOutComboBox=new JComboBox();
        midiOutComboBox.setModel(midiOutComboBoxModel);
        midiOutComboBox.addItemListener(this);
        
        
        keyboardLengthComboBox=new JComboBox();
        keyboardLengthComboBox.addItemListener(this);

  
        
        Integer current = new Integer(0);
        Integer min = new Integer(-24);
        Integer max = new Integer(24);
        Integer step = new Integer(1);
        m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
        transpositionSpinner = new JSpinner(m_numberSpinnerModel);
        transpositionSpinner.setSize(40, 40);
        transpositionLabel.setText("Tansposition");

        JPanel keyboardPanel=new JPanel();
        keyboardPanel.add(keyboardLengthComboBox);
        keyboardPanel.add(transpositionLabel);
        keyboardPanel.add(transpositionSpinner);

        JPanel midiInPanel=new JPanel();
        localizables.add(new Localizable.NamedGroup(midiInPanel, "_midiclavier"));
        midiInPanel.setLayout(new BorderLayout());
        midiInPanel.add(midiInComboBox, BorderLayout.NORTH);
        midiInPanel.add(keyboardPanel, BorderLayout.CENTER);

        JPanel midiOutPanel=new JPanel();
        midiOutPanel.setName("Midi out");
        localizables.add(new Localizable.NamedGroup(midiOutPanel, "_midiout"));
        midiOutPanel.setLayout(new BorderLayout());
        midiOutPanel.add(midiOutComboBox, BorderLayout.NORTH);
        
        // ----

        JButton okButton=new JButton();
        localizables.add(new Localizable.Button(okButton, "_buttonok"));
        okButton.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleMidiOptionsOkClicked();
            }
        });

        JButton cancelButton=new JButton();
        localizables.add(new Localizable.Button(cancelButton, "_buttoncancel"));
        cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleMidiOptionsCancelClicked();
            }
        });

        JPanel buttonPanel=new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // ----

        JPanel contentPanel=new JPanel();
        contentPanel.setLayout(new GridLayout(5,1,4,4));
        contentPanel.add(soundPanel);
        contentPanel.add(midiInPanel);
        contentPanel.add(midiOutPanel);
        contentPanel.add(latencyPanel);
        contentPanel.add(buttonPanel);

        JDialog dialog=new JDialog(this, true);
        localizables.add(new Localizable.Dialog(dialog, "_menuMidi"));
        dialog.setContentPane(contentPanel);
        dialog.setSize(450, 550);
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //dialog.setResizable(false);

        return dialog;
    }

    //----------------------------------------------------------------
    private JDialog buildPreferencesDialog() {

        JPanel noteReadingPanel=buildNoteReadingPreferencesPanel();
        JPanel rhythmReadingPanel=buildRhythmReadingPreferencesPanel();
        JPanel scoreReadingPanel=buildScoreReadingPreferencesPanel();
      
        
        preferencesTabbedPane.addTab(null, new ImageIcon(getClass().getResource("/images/note.png")), noteReadingPanel);
        localizables.add(new Localizable.Tab(preferencesTabbedPane, NOTE_READING_TAB, "_menuNotereading"));
        preferencesTabbedPane.addTab(null, new ImageIcon(getClass().getResource("/images/rythme.png")), rhythmReadingPanel);
        localizables.add(new Localizable.Tab(preferencesTabbedPane, RHYTM_READING_TAB, "_menuRythmreading"));
        preferencesTabbedPane.addTab(null, new ImageIcon(getClass().getResource("/images/score.png")), scoreReadingPanel);
        localizables.add(new Localizable.Tab(preferencesTabbedPane, SCORE_READING_TAB, "_menuScorereading"));

        // buttons below tabs

        JButton okButton=new JButton();
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePreferencesOkClicked();
            }
        });
        okButton.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));
        localizables.add(new Localizable.Button(okButton, "_buttonok"));

        JButton cancelButton=new JButton();
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePreferencesCancelClicked();
            }
        });
        cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        localizables.add(new Localizable.Button(cancelButton, "_buttoncancel"));
        
        JButton saveButton=new JButton();
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePreferencesSaveClicked();
            }
        });
        saveButton.setIcon(new ImageIcon(getClass().getResource("/images/save.png")));
        localizables.add(new Localizable.Button(saveButton, "_buttonsave"));
        


        JPanel buttonPanel=new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        JPanel contentPanel=new JPanel();
      // contentPanel.setLayout(new GridLayout(2, 2));
      
        contentPanel.add(preferencesTabbedPane,BorderLayout.CENTER);
        preferencesTabbedPane.setPreferredSize(new Dimension(560, 430));

        contentPanel.add(buttonPanel,BorderLayout.LINE_END);
     

        JDialog dialog=new JDialog(this, true);
        localizables.add(new Localizable.Dialog(dialog, "_menuPreferences"));
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        //dialog.setResizable(false);
        dialog.setContentPane(contentPanel);
        dialog.setSize(580, 550);

        return dialog;
    }

    //----------------------------------------------------------------
    private JPanel buildRhythmReadingPreferencesPanel() {

        /* 1st panel - type of game */

        rhythmGameTypeComboBox=new JComboBox();
        // rhythmGameTypeComboBox.addItem("Learning");
        // rhythmGameTypeComboBox.addItem("Normal");
        rhythmGameTypeComboBox.addItemListener(this);

        rhythmGameSpeedComboBox=new JComboBox();
        rhythmGameSpeedComboBox.addItem("Largo");
        rhythmGameSpeedComboBox.addItem("Adagio");
        rhythmGameSpeedComboBox.addItem("Moderato");
        rhythmGameSpeedComboBox.addItem("Allegro");
        rhythmGameSpeedComboBox.addItem("Presto");
        rhythmGameSpeedComboBox.addItemListener(this);

        JPanel gamePanel=new JPanel();
        gamePanel.add(rhythmGameTypeComboBox);
        gamePanel.add(rhythmGameSpeedComboBox);
        localizables.add(new Localizable.NamedGroup(gamePanel, "_menuExercises"));

        /* 2nd panel - RYTHM */

        wholeCheckBox=new JCheckBox("", true);
        wholeCheckBox.addItemListener(this);
        halfCheckBox=new JCheckBox("", true);
        halfCheckBox.addItemListener(this); 
        dottedhalfCheckBox=new JCheckBox("", false);
        dottedhalfCheckBox.addItemListener(this);
        quarterCheckBox=new JCheckBox("", false);
        quarterCheckBox.addItemListener(this);
        eighthCheckBox=new JCheckBox("", false);
        eighthCheckBox.addItemListener(this);
        restCheckBox=new JCheckBox("", true);
        restCheckBox.addItemListener(this);
        tripletCheckBox=new JCheckBox("", false);
        tripletCheckBox.addItemListener(this);
        
        timeSignComboBox=new JComboBox();
        timeSignComboBox.setPreferredSize(new Dimension(100, 25));
        timeSignComboBox.addItem("4/4");
        timeSignComboBox.addItem("3/4");
        timeSignComboBox.addItem("2/4");
        timeSignComboBox.addItem("6/8");
        timeSignComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JComboBox cb = (JComboBox)e.getSource();
            	int sel = cb.getSelectedIndex();
            	System.out.println("Rhythm time signature changed. Selected: "+ sel);
            	if (sel == 0) {
            		wholeCheckBox.setEnabled(true);
            		wholeCheckBox.setSelected(true);
             		quarterCheckBox.setSelected(true);
            		dottedhalfCheckBox.setSelected(false);
              		dottedhalfCheckBox.setEnabled(true);
            		rhythmLevel.setTimeSignNumerator(4);
            		rhythmLevel.setTimeSignDenominator(4);
            		rhythmLevel.setTimeDivision(1);
            	}
            	else if (sel == 1) {
            		wholeCheckBox.setSelected(false);
            		wholeCheckBox.setEnabled(false);
            		dottedhalfCheckBox.setSelected(true);
              		dottedhalfCheckBox.setEnabled(true);
            		quarterCheckBox.setSelected(true);
            		rhythmLevel.setTimeSignNumerator(3);
            		rhythmLevel.setTimeSignDenominator(4);
            		rhythmLevel.setTimeDivision(1);
            	}
            	else if (sel == 2) {
            		wholeCheckBox.setSelected(false);
            		wholeCheckBox.setEnabled(false);
            		dottedhalfCheckBox.setSelected(false);
              		dottedhalfCheckBox.setEnabled(false);
            		quarterCheckBox.setSelected(true);
            		rhythmLevel.setTimeSignNumerator(2);
            		rhythmLevel.setTimeSignDenominator(4);
            		rhythmLevel.setTimeDivision(1);
            	}
            	else if (sel == 3) {
            		wholeCheckBox.setSelected(false);
            		wholeCheckBox.setEnabled(false);
            		scoredottedhalfCheckBox.setSelected(false);
              		scoredottedhalfCheckBox.setEnabled(false);
            		quarterCheckBox.setSelected(true);
            		rhythmLevel.setTimeSignNumerator(6);
            		rhythmLevel.setTimeSignDenominator(8);
            		rhythmLevel.setTimeDivision(2);
            	}
            }
        });
        
        JPanel rhytmsPanel=new JPanel();
        rhytmsPanel.add(wholeCheckBox);
        rhytmsPanel.add(dottedhalfCheckBox);
        rhytmsPanel.add(halfCheckBox);
        rhytmsPanel.add(quarterCheckBox);
        rhytmsPanel.add(eighthCheckBox);
        rhytmsPanel.add(restCheckBox);
        rhytmsPanel.add(tripletCheckBox);
        
        JPanel timeSignPanel=new JPanel();
        timeSignLabel = new JLabel();
        timeSignPanel.add(timeSignLabel);
        timeSignPanel.add(timeSignComboBox);
        
        JPanel rhythmAndTimePanel=new JPanel();
        rhythmAndTimePanel.setLayout(new BorderLayout());
        rhythmAndTimePanel.add(timeSignPanel, BorderLayout.NORTH);
        rhythmAndTimePanel.add(rhytmsPanel, BorderLayout.CENTER);
        localizables.add(new Localizable.NamedGroup(rhythmAndTimePanel, "_menuRythms"));

        /* 3rd panel - metronome */

        metronomeCheckBox=new JCheckBox("", true);
        metronomeCheckBox.addItemListener(this);
        metronomeShowCheckBox=new JCheckBox("", true);
        metronomeShowCheckBox.setSelected(false);
        metronomeShowCheckBox.addItemListener(this);

        JPanel metronomePanel=new JPanel();
        metronomePanel.add(metronomeCheckBox);
        metronomePanel.add(metronomeShowCheckBox);
        localizables.add(new Localizable.NamedGroup(metronomePanel, "_menuMetronom"));

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(gamePanel);
        panel.add(rhythmAndTimePanel);
        panel.add(metronomePanel);

        return panel;
    }

    
    private JPanel buildScoreReadingPreferencesPanel() {

    	  scoreGameTypeComboBox=new JComboBox();
          scoreGameTypeComboBox.addItemListener(this);

          scoreGameSpeedComboBox=new JComboBox();
          scoreGameSpeedComboBox.addItem("Largo");
          scoreGameSpeedComboBox.addItem("Adagio");
          scoreGameSpeedComboBox.addItem("Moderato");
          scoreGameSpeedComboBox.addItem("Allegro");
          scoreGameSpeedComboBox.addItem("Presto");
          scoreGameSpeedComboBox.addItemListener(this);

          JPanel scoregamePanel=new JPanel();
          scoregamePanel.add(scoreGameTypeComboBox);
          scoregamePanel.add(scoreGameSpeedComboBox);
          localizables.add(new Localizable.NamedGroup(scoregamePanel, "_menuExercises"));

          /* 2nd panel - Key & notes */

          scoreKeyComboBox=new JComboBox();
          scoreKeyComboBox.addItemListener(this);

          scoreNotesComboBox=new JComboBox();
          scoreNotesComboBox.addItemListener(this);

          scoreAlterationsComboBox=new JComboBox();
          scoreAlterationsComboBox.addItemListener(this);

          JPanel scoreKeyPanel=new JPanel(); // panel pour la Key du premier jeu
          scoreKeyPanel.add(scoreKeyComboBox);
          scoreKeyPanel.add(scoreAlterationsComboBox);
          scoreKeyPanel.add(scoreNotesComboBox);
          localizables.add(new Localizable.NamedGroup(scoreKeyPanel, "_menuNotes"));

          /* 3rd panel - RYTHM */

          scorewholeCheckBox=new JCheckBox("", true);
          scorewholeCheckBox.addItemListener(this);   
          scorehalfCheckBox=new JCheckBox("", true);
          scorehalfCheckBox.addItemListener(this);
          scoredottedhalfCheckBox=new JCheckBox("", false);
          scoredottedhalfCheckBox.addItemListener(this);  
          scorequarterCheckBox=new JCheckBox("", false);
          scorequarterCheckBox.addItemListener(this);            
          scoreeighthCheckBox=new JCheckBox("", false);
          scoreeighthCheckBox.addItemListener(this);          
          scorerestCheckBox=new JCheckBox("", true);
          scorerestCheckBox.addItemListener(this);      
          scoreTripletCheckBox=new JCheckBox("", false);
          scoreTripletCheckBox.addItemListener(this);

          JPanel scorerhytmsPanel=new JPanel();
          scorerhytmsPanel.add(scorewholeCheckBox);
          scorerhytmsPanel.add(scoredottedhalfCheckBox);
          scorerhytmsPanel.add(scorehalfCheckBox);
          scorerhytmsPanel.add(scorequarterCheckBox);
          scorerhytmsPanel.add(scoreeighthCheckBox);
          scorerhytmsPanel.add(scorerestCheckBox);
          scorerhytmsPanel.add(scoreTripletCheckBox);

          scoreTimeSignComboBox=new JComboBox();
          scoreTimeSignComboBox.setPreferredSize(new Dimension(100, 25));
          scoreTimeSignComboBox.addItem("4/4");
          scoreTimeSignComboBox.addItem("3/4");
          scoreTimeSignComboBox.addItem("2/4");
          scoreTimeSignComboBox.addItem("6/8");
          scoreTimeSignComboBox.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              	JComboBox cb = (JComboBox)e.getSource();
              	int sel = cb.getSelectedIndex();
              	System.out.println("Rhythm time signature changed. Selected: "+ sel);
              	if (sel == 0) {
              		scorewholeCheckBox.setEnabled(true);
              		scorewholeCheckBox.setSelected(true);
              		scorequarterCheckBox.setSelected(true);
              		scoredottedhalfCheckBox.setEnabled(true);
              		scoredottedhalfCheckBox.setSelected(false);
              		scoreLevel.setTimeSignNumerator(4);
            		scoreLevel.setTimeSignDenominator(4);
            		scoreLevel.setTimeDivision(1);
              	}
              	else if (sel == 1) {
              		scorewholeCheckBox.setSelected(false);
              		scorewholeCheckBox.setEnabled(false);
              		scoredottedhalfCheckBox.setSelected(true);
              		scoredottedhalfCheckBox.setEnabled(true);
              		scorequarterCheckBox.setSelected(true);
              		scoreLevel.setTimeSignNumerator(3);
            		scoreLevel.setTimeSignDenominator(4);
            		scoreLevel.setTimeDivision(1);
              	}
              	else if (sel == 2) {
              		scorewholeCheckBox.setSelected(false);
              		scoredottedhalfCheckBox.setSelected(false);
              		scoredottedhalfCheckBox.setEnabled(false);
              		scorequarterCheckBox.setSelected(true);
              		scoreLevel.setTimeSignNumerator(2);
            		scoreLevel.setTimeSignDenominator(4);
            		scoreLevel.setTimeDivision(1);
              	}
            	else if (sel == 3) {
            		scorewholeCheckBox.setSelected(false);
            		scorewholeCheckBox.setEnabled(false);
            		scoredottedhalfCheckBox.setSelected(false);
              		scoredottedhalfCheckBox.setEnabled(false);
            		scorequarterCheckBox.setSelected(true);
            		scoreLevel.setTimeSignNumerator(6);
            		scoreLevel.setTimeSignDenominator(8);
            		scoreLevel.setTimeDivision(2);
            	}
              }
          });
          
          JPanel timeSignPanel=new JPanel();
          scoreTimeSignLabel = new JLabel();
          timeSignPanel.add(scoreTimeSignLabel);
          timeSignPanel.add(scoreTimeSignComboBox);
          
          JPanel scoreRhythmAndTimePanel=new JPanel();
          scoreRhythmAndTimePanel.setLayout(new BorderLayout());
          scoreRhythmAndTimePanel.add(timeSignPanel, BorderLayout.NORTH);
          scoreRhythmAndTimePanel.add(scorerhytmsPanel, BorderLayout.CENTER);
          localizables.add(new Localizable.NamedGroup(scoreRhythmAndTimePanel, "_menuRythms"));

          /* 4th panel - sound */

          scoreMetronomeCheckBox=new JCheckBox("", true);
          scoreMetronomeShowCheckBox=new JCheckBox("", true);
          scoreMetronomeShowCheckBox.setSelected(false);
          scoreMetronomeCheckBox.addActionListener(this);
          scoreMetronomeShowCheckBox.addActionListener(this);
          
          JPanel scoremetronomePanel=new JPanel();
          scoremetronomePanel.add(scoreMetronomeCheckBox);
          scoremetronomePanel.add(scoreMetronomeShowCheckBox);
          localizables.add(new Localizable.NamedGroup(scoremetronomePanel, "_menuMetronom"));
          
       
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        panel.add(scoregamePanel);
        panel.add(scoreKeyPanel);
        panel.add(scoreRhythmAndTimePanel);
        panel.add(scoremetronomePanel);
        //panel.add(latencyPanel);
    
        return panel;
    }

    //----------------------------------------------------------------
    private JPanel buildNoteReadingPreferencesPanel() {

        /* 1er panel - type de jeu */

        noteGameTypeComboBox=new JComboBox();
        noteGameTypeComboBox.addItemListener(this);

        noteGameSpeedComboBox=new JComboBox();
        noteGameSpeedComboBox.addItem("Largo");
        noteGameSpeedComboBox.addItem("Adagio");
        noteGameSpeedComboBox.addItem("Moderato");
        noteGameSpeedComboBox.addItem("Allegro");
        noteGameSpeedComboBox.addItem("Presto");
        noteGameSpeedComboBox.addItemListener(this);

        JPanel gamePanel=new JPanel();
        gamePanel.add(noteGameTypeComboBox);
        gamePanel.add(noteGameSpeedComboBox);
        localizables.add(new Localizable.NamedGroup(gamePanel, "_menuExercises"));

        /* 2nd panel - Key */

        keyComboBox=new JComboBox();
        keyComboBox.addItemListener(this);

        keySignatureCheckBox=new JComboBox();
        keySignatureCheckBox.addItemListener(this);

        JPanel KeyPanel=new JPanel(); // panel pour la Key du premier jeu
        KeyPanel.add(keyComboBox);
        KeyPanel.add(keySignatureCheckBox);
        localizables.add(new Localizable.NamedGroup(KeyPanel, "_menuClef"));

        /* 3rd panel - Notes */

        noteGroupComboBox=new JComboBox();
        noteGroupComboBox.addItemListener(this);

        noteCountComboBox=new JComboBox();
        noteCountComboBox.addItemListener(this);

        intervalComboBox=new JComboBox();
        intervalComboBox.addItemListener(this);

        chordTypeComboBox=new JComboBox();
        chordTypeComboBox.addItemListener(this);

        noteReadingNotesPanel.add(noteGroupComboBox);
       noteReadingNotesPanel.add(noteCountComboBox);


        localizables.add(new Localizable.NamedGroup(noteReadingNotesPanel, "_menuNotes"));

        

      

        
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(gamePanel);
        panel.add(KeyPanel);
        panel.add(noteReadingNotesPanel);
        
        return panel;
    }

    //################################################################
    // METHODES D'ACTION DES BOUTONS ET CHOICES

    /** Initialize note reading game if there is modification in
     * parameters and game restart. */
    private void changeScreen() {

        if (isLessonMode) {			
        		if (currentlesson.isNoteLevel()) {	
	            startButton.setVisible(false);
	            preferencesButton.setVisible(false);
	            newButton.setVisible(false);
	            listenButton.setVisible(false);
	
	            menuPrefs.setEnabled(false);
        		}
        		else if (currentlesson.isRhythmLevel() || currentlesson.isScoreLevel()) {	
    	            
    	            pgamebutton.add(newButton);
    	            pgamebutton.add(listenButton);
    	            pgamebutton.add(startButton);
    	            pgamebutton.add(preferencesButton);
    	            scoreYpos=110;
    	            if ( currentlesson.isScoreLevel()) {
    	            	alterationWidth = scoreLevel.getCurrentTonality().getAlterationsNumber() * 12;
    	            	firstNoteXPos = windowMargin + keyWidth + alterationWidth + timeSignWidth + notesShift;
    	            	   Dimension size=getSize();
    	            	   int scoreLineWidth = keyWidth + alterationWidth + timeSignWidth;
    	            	 numberOfMeasures = (size.width - (windowMargin * 2) - scoreLineWidth) / (scoreLevel.getTimeSignNumerator() * noteDistance);         	  
    	            }
    	            else if ( currentlesson.isRhythmLevel()) {
    	            
    	            	   Dimension size=getSize();
    	            	   int scoreLineWidth = keyWidth + alterationWidth + timeSignWidth;
    	            	 numberOfMeasures = (size.width - (windowMargin * 2) - scoreLineWidth) / (rhythmLevel.getTimeSignNumerator() * noteDistance);         	  
    	       
    	            }
    	           
    	            
    	            repaint();
    	            pgamebutton.setVisible(true);
    	            

    	   
    	            menuPrefs.setEnabled(false);
            	}

        } else {
            startButton.setVisible(true);
            preferencesButton.setVisible(true);
            menuPrefs.setEnabled(true);

        }

        if (selectedGame==NOTEREADING) {
            pgamebutton.setVisible(true);
            pnotes.setVisible(true);
            principal.setVisible(true);
            System.out.println(noteLevel.getNbnotes());
            if (noteLevel.isNotesgame() && noteLevel.getCurrentTonality().getAlterationsNumber()==0) {

                bdiese.setVisible(false);
                bdiese2.setVisible(false);
                bbemol.setVisible(false);
                bbemol2.setVisible(false);
                pnotes.validate();
            } else {
                bdiese.setVisible(true);
                bdiese2.setVisible(true);
                bbemol.setVisible(true);
                bbemol2.setVisible(true);
                pnotes.validate();

            }

        } else if (selectedGame==RHYTHMREADING || selectedGame==SCOREREADING) {
            pgamebutton.setVisible(true);
            pnotes.setVisible(false);
            newButton.setVisible(true);
            listenButton.setVisible(true);
            principal.setVisible(true);

        }

    }

    private void updateTonality() {
        String stmp;

        if ((selectedGame==NOTEREADING && noteLevel.getRandomtonality())
        		|| (selectedGame==SCOREREADING && scoreLevel.getRandomtonality())) { // to change tonality when randomly
            int i=(int)Math.round((Math.random()*7));
            double tmp=Math.random();
            if (tmp<0.1) {
                stmp="";
            } else if (tmp>=0.1 && tmp<0.6) {
                stmp="#";
            } else {
                stmp="b";
            }

           if (selectedGame==NOTEREADING) noteLevel.getCurrentTonality().init(i, stmp);
           else if (selectedGame==SCOREREADING) scoreLevel.getCurrentTonality().init(i, stmp);
        } else
        if (!isLessonMode && noteLevel.getCurrentTonality().getAlterationsNumber()==0) {
            // Do Major when tonality is no sharp no  flat
            double tmp=Math.random();
            if (tmp<0.5) {
                stmp="#";
            } else {
                stmp="b";
            }
            noteLevel.getCurrentTonality().init(0, stmp);
        }

    }
    


    private void initNoteGame() {

        parti=false;
        
        currentScore.initScore();

        precedente=0;
        notecounter=1;

        scoreYpos=110;
        paused=false;
        // stopSound();

        ColorUIResource def=new ColorUIResource(238, 238, 238);
        bdo.setBackground(def);
        bre.setBackground(def);
        bmi.setBackground(def);
        bfa.setBackground(def);
        bsol.setBackground(def);
        bla.setBackground(def);
        bsi.setBackground(def);
        bdiese.setBackground(def);
        bbemol2.setBackground(def);

    

        if (noteLevel.isNormalgame() || noteLevel.isLearninggame()) {
            notemargin=220;
            repaint();
        } else if (noteLevel.isInlinegame()) {
            notemargin=30;
            repaint();
        }
    }

    /** Initialise rhythm reading game if there is modification in
     * parameters and game restart. */
    private void stopRhythmGame() {

    	int tmpdiv = 1;
    	
  	  if (selectedGame==RHYTHMREADING)
        	tmpdiv = rhythmLevel.getTimeDivision(); 
        else
      	  tmpdiv = scoreLevel.getTimeDivision(); 

     //   parti=false;
        startButton.setText(bundle.getString("_start"));
        
        rhythmIndex=-1;
        scoreYpos=110;
        rhythmCursorXpos = firstNoteXPos - (noteDistance * tmpdiv);
    	rhythmCursorXStartPos = firstNoteXPos - (noteDistance * tmpdiv);
        rhythmAnswerScoreYpos=100;
        cursorstart = false;
        metronomeCount = 0;
        metronomeYPos = 100;
        
        if (sm_sequencer !=null) {
      	sm_sequencer.close();
      	
      	
     
      }
    	//repaint();

    }

    /** Stops all games. */
    
    private void stopGames() {
    	  if (selectedGame==NOTEREADING) stopNoteGame();
    	  else stopRhythmGame();
    	
    }
    
    private void stopNoteGame() {
    	
        parti=false;
        startButton.setText(bundle.getString("_start"));
        
        ncourante= new Note("", "", 0, 25, 0);
        acourant = new Chord(ncourante, ncourante, ncourante, "", 0);
        icourant= new Interval(ncourante, ncourante, "");
        resetButtonColor();
        
        stopSound();
   //     if (sm_sequencer!=null) {
  //          sm_sequencer.stop();
    //    }

    }

    private void initRhythmGame() {
    	
    	System.out.println("[initRhythmGame] latency: " + latency);
    	


    
    	initializeMidi(); 
    	if (!renderingThread.isAlive()) {
             renderingThread.start();
        }  
        stopRhythmGame(); // stop previous game

        if (!samerhythms) createSequence();

        try {
            sm_sequencer=MidiSystem.getSequencer();
        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (sm_sequencer==null) {
            System.out.println("Can't get a Sequencer");
            System.exit(1);
        }

        try {
            sm_sequencer.open();
        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            sm_sequencer.setSequence(sequence);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (!(sm_sequencer instanceof Synthesizer)) {

            try {
                Synthesizer sm_synthesizer=MidiSystem.getSynthesizer();
                sm_synthesizer.open();
                Receiver synthReceiver=sm_synthesizer.getReceiver();
                Transmitter seqTransmitter=sm_sequencer.getTransmitter();
                seqTransmitter.setReceiver(synthReceiver);
                //latency = sm_synthesizer.getLatency()/1000;  
                latency = latencySlider.getValue();
                System.out.println("MIDI latency " + latency);
            }
            catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
        

        sm_sequencer.addMetaEventListener(new MetaEventListener() {
            public void meta(MetaMessage meta) {
           
                byte[] abData=meta.getData();
                String strText=new String(abData);
                
                int tmpnum = 4; int tmpden = 4; int tmpdiv = 1;
                if (selectedGame == RHYTHMREADING ) {
              	  tmpnum = rhythmLevel.getTimeSignNumerator();
              	  tmpden = rhythmLevel.getTimeSignDenominator();
              	  tmpdiv = rhythmLevel.getTimeDivision();
                }
                else if (selectedGame == SCOREREADING ) {
              	  tmpnum = scoreLevel.getTimeSignNumerator();
              	  tmpden = scoreLevel.getTimeSignDenominator();
              	  tmpdiv = scoreLevel.getTimeDivision();
                }
                
               
                if ("departthread".equals(strText)) {
                	System.out.println("Cursor started");
                	rhythmCursorXlimit = firstNoteXPos + (tmpnum * numberOfMeasures * noteDistance);
                    cursorstart = true;
                    timestart = System.currentTimeMillis();
                } 
                
                if ("depart".equals(strText)) {
                	System.out.println("Game start");
                    rhythmIndex=0;
                    repaint();
                }
           	    else if ("beat".equals(strText)) {
                    // show metronome beats
           		    //System.out.println("Added metronome beat");
           		   	answers.add(new RhythmAnswer(firstNoteXPos + (metronomeCount%((tmpnum/tmpdiv) * numberOfMeasures)) * (noteDistance * tmpdiv), metronomeYPos - 30, true, 3 ));
           		   	metronomeCount++;
           		   	//System.out.println("Metronome beat: " + metronomeCount + ", metronomeYPos: " + metronomeYPos);
           		   	if (metronomeCount == ((tmpnum/tmpdiv) * numberOfMeasures) && 
           		   						   metronomeYPos < scoreYpos + (numberOfRows * rowsDistance)) {
           		   		metronomeYPos+=rowsDistance;
           		   		metronomeCount=0;
           		   	}
                }
           	    else {
           	    	//System.out.println("Event: " + strText);
           	    	nextRythm();
           	    	repaint();
                }
                //System.out.println("rhythmIndex: " + rhythmIndex);
            }
        });
        if (selectedGame==RHYTHMREADING)
        	tempo = rhythmLevel.getspeed(); 
        else
        	tempo = scoreLevel.getspeed(); 

        sm_sequencer.setTempoInBPM(tempo);
        System.out.println("[initRhythmGame] tempo : " + tempo);
           
        //init line answers
        
        answers.clear();
    }

    
    private void startRhythmGame() {
    	 sm_sequencer.start();
    	int tmpdiv = 1;
    	
    	  if (selectedGame==RHYTHMREADING)
          	tmpdiv = rhythmLevel.getTimeDivision(); 
          else
        	  tmpdiv = scoreLevel.getTimeDivision(); 

      //   Track[] tracks=sequence.getTracks();
         
          if (muterhythms) {
         	 sm_sequencer.setTrackMute(1, true); 
         	 sm_sequencer.setTrackMute(0, false); 
          }
          else {
         	 sm_sequencer.setTrackMute(1, false); 
         	 sm_sequencer.setTrackMute(0, true); 
          }
         
          
         parti=true; // start game
         startButton.setText(bundle.getString("_stop"));
         rhythmCursorXpos = firstNoteXPos - (noteDistance * tmpdiv);
         
         cursorstart = false;
    }
    
    
    
    private void startNoteGame() {
    	
        initNoteGame();     // to stop last game
        updateTonality(); //when selected random tonality

        if (noteLevel.isNormalgame() || noteLevel.isLearninggame()) {
            if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) {
                newnote();
            } else if (noteLevel.isChordsgame()) {
                newChord();
            } else if (noteLevel.isIntervalsgame()) {
                newinterval();
            }
        } else if (noteLevel.isInlinegame()) {
            createline();
        }

        parti=true;        // start of game
        startButton.setText(bundle.getString("_stop"));
    }

    private void rightAnswer() {
        if (noteLevel.isLearninggame()) {

            if (noteLevel.isChordsgame() || noteLevel.isIntervalsgame()) {
                /* if (isLessonMode & notecounter < noteLevel.getLearningduration()){
                       parti = false;
                       nextlevel();
                     }

                else*/
                nextnote();

            } else if (isLessonMode && notecounter==noteLevel.getLearningduration()) {
                parti=false;
                startButton.setText(bundle.getString("_start"));
                nextLevel();
            } else {
                newnote();
            }

            resetButtonColor();
        } else {
            currentScore.addNbtrue(1);

            if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) {
                currentScore.addPoints(10);
            } else if (noteLevel.isChordsgame() || noteLevel.isIntervalsgame()) {
                currentScore.addPoints(5);
            }

            if (currentScore.isWin()) {
                parti=false;
                startButton.setText(bundle.getString("_start"));
                showResult();

            }

            if (noteLevel.isInlinegame() && position==ligne.length-1) { // derniÃ¨re note trouvÃ©e
                currentScore.setWin();
                parti=false;
                startButton.setText(bundle.getString("_start"));
                showResult();

            }
            if (noteLevel.isChordsgame() || noteLevel.isIntervalsgame()) {
                nextnote();
            } else {
                newnote();
            }
        }
    }

    private void startLevel() {

        if (currentlesson.isNoteLevel()) {       	
	        if (!noteLevel.isMessageEmpty()) {
	
	            levelMessage.setTitle(bundle.getString("_information"));
	
	            textlevelMessage.setText("  "+noteLevel.getMessage()+"  ");
	            levelMessage.pack();
	            levelMessage.setLocationRelativeTo(this);
	            levelMessage.setVisible(true);
	
	        } else {
	            startButton.doClick();
	        }        
        }
        
        else if (currentlesson.isRhythmLevel()) {       	
	        if (!rhythmLevel.isMessageEmpty()) {
	
	            levelMessage.setTitle(bundle.getString("_information"));
	
	            textlevelMessage.setText("  "+rhythmLevel.getMessage()+"  ");
	            levelMessage.pack();
	            levelMessage.setLocationRelativeTo(this);
	            levelMessage.setVisible(true);
	
	        } else {
	            startButton.doClick();
	        }        
        }
        
        else if (currentlesson.isScoreLevel()) {       	
	        if (!scoreLevel.isMessageEmpty()) {
	
	            levelMessage.setTitle(bundle.getString("_information"));
	
	            textlevelMessage.setText("  "+scoreLevel.getMessage()+"  ");
	            levelMessage.pack();
	            levelMessage.setLocationRelativeTo(this);
	            levelMessage.setVisible(true);
	
	        } else {
	           startButton.doClick();
	        }        
        }
        
    }

    private void nextLevel() {

        if (!currentlesson.lastexercice()) {
            stopNoteGame();
            currentlesson.nextLevel();

            if (currentlesson.isNoteLevel()) {	
            	noteLevel.copy((NoteLevel)currentlesson.getLevel());
            	noteLevel.updatenbnotes(piano);

                selectedGame = NOTEREADING ;
            initNoteGame();
            changeScreen();
            noteLevel.printtest();


            startLevel();
            }

            else if (currentlesson.isRhythmLevel()) {	
            	rhythmLevel.copy((RhythmLevel)currentlesson.getLevel());
            

            	selectedGame = RHYTHMREADING;
            	initRhythmGame();

            	changeScreen();
            	rhythmLevel.printtest();
                newButton.doClick();
            	startLevel();
            }
            
            else if (currentlesson.isScoreLevel()) {	
            	scoreLevel.copy((ScoreLevel)currentlesson.getLevel());
            

            	selectedGame = SCOREREADING;
            	initRhythmGame();

            	changeScreen();
            	scoreLevel.printtest();
                newButton.doClick();
            	startLevel();
            }

        } else {
            System.out.println("End level");

            JOptionPane.showMessageDialog(this, bundle.getString("_lessonfinished"),
                bundle.getString("_congratulations"),
                JOptionPane.INFORMATION_MESSAGE);

            	 isLessonMode = false;
            	 stopGames();
            	 handleNoteReadingMenuItem();
            	 
            	 repaint();
           


        }
    }

    private void wrongAnswer() {
        alterationok=false;

        if (!noteLevel.isLearninggame()) {

            currentScore.addNbfalse(1);
            // if (soundOnCheckBox.getState()) sonerreur.play();

            if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame()  || noteLevel.isCustomNotesgame()) {
                currentScore.addPoints(-20);
            } else if (noteLevel.isChordsgame() || noteLevel.isIntervalsgame()) {
                currentScore.addPoints(-10);
            }

            if (currentScore.isLost()) {
                parti=false;
                startButton.setText(bundle.getString("_start"));
                showResult();
            }
        }

    }

  
    /** FONCTIONS POUR SAISIE AU CLAVIER */
    public void keyTyped(KeyEvent evt) {
        char ch=evt.getKeyChar();  // The character typed.

        //  System.out.println(ch);

        if (selectedGame==NOTEREADING && parti) {
            if (ch=='P' || ch=='p') {
                if (!paused) {
                    paused=true;
                }

                int n=JOptionPane.showConfirmDialog(this, "",
                    bundle.getString("_gamepaused"),
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

                if (n==0) {
                    paused=false;
                }

            }
        }
        
 

        if (selectedGame==NOTEREADING && parti && !paused && noteLevel.isNotesgame()) {

            if (ch=='Q' || ch=='q' || ch=='A' || ch=='a' || ch=='S' || ch=='s' ||
                ch=='D' || ch=='d' || ch=='F' || ch=='f' || ch=='G' || ch=='g' ||
                ch=='H' || ch=='h' || ch=='J' || ch=='j' || ch=='K' || ch=='k') {

                if (((language=="fr" && (ch=='Q' || ch=='q'))
                    || ((language=="en" || language=="es" || language=="de") && (ch=='A' || ch=='a')))
                    && ncourante.getNom()==DO)
                {
                    rightAnswer();
                } else if ((ch=='S' || ch=='s') && ncourante.getNom().equals(RE)) {
                    rightAnswer();
                } else if ((ch=='D' || ch=='d') && ncourante.getNom().equals(MI)) {
                    rightAnswer();
                } else if ((ch=='F' || ch=='f') && ncourante.getNom().equals(FA)) {
                    rightAnswer();
                } else if ((ch=='G' || ch=='g') && ncourante.getNom().equals(SOL)) {
                    rightAnswer();
                } else if ((ch=='H' || ch=='h') && ncourante.getNom().equals(LA)) {
                    rightAnswer();
                } else if ((ch=='J' || ch=='j') && ncourante.getNom().equals(SI)) {
                    rightAnswer();
                } else if ((ch=='K' || ch=='k') && ncourante.getNom().equals(DO)) {
                    rightAnswer();
                } else {
                    wrongAnswer();
                }
            }
        }
    }  // end keyTyped()

    public void keyPressed(KeyEvent evt) {

        // Called when the user has pressed a key, which can be
        // a special key such as an arrow key.
        int key=evt.getKeyCode(); // keyboard code for the key that was pressed
       
        if (isLessonMode && parti && key == KeyEvent.VK_ESCAPE) {
        	
       	 parti=false;
            nextLevel();
       }
        
        if (selectedGame==NOTEREADING && !isLessonMode && !parti && (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) &&
            !noteLevel.isAllnotesgame())
        {
            if (key==KeyEvent.VK_LEFT) {
                noteLevel.basenotetoLeft(piano);
            } else if (key==KeyEvent.VK_RIGHT) {
                noteLevel.basenotetoRight(piano);
            }
        }
        
        else if (selectedGame == RHYTHMREADING && rhythmgame == 0 && muterhythms && parti) {
    	  if (key==KeyEvent.VK_SPACE) {
    		  rhythmKeyPressed(71);
    	  }
        }
        repaint();
    } // end keyPressed()

    public void keyReleased(KeyEvent evt) {
        // empty method, required by the KeyListener Interface
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==rblanguageen) {
            language="en";
            updateLang();
            //To do : recharge lessons with new lang
        }

        if (e.getSource()==rblanguagede) {
            language="de";
            updateLang();
        }

        if (e.getSource()==rblanguagees) {
            language="es";
            updateLang();
        }

        if (e.getSource()==rblanguagefr) {
            language="fr";
            updateLang();
        }

        if (e.getSource()==rblanguageit) {
            language="it";
            updateLang();
        }

        if (e.getSource()==rblanguageda) {
            language="da";
            updateLang();
        }

        if (e.getSource()==rblanguagetr) {
            language="tr";
            updateLang();
        }
            
        if (e.getSource()==rblanguagefi) {
                language="fi";
                updateLang();
        } 
        
        if (e.getSource()==rblanguageko) {
            language="ko";
            updateLang();
        }

        if (e.getSource()==rblanguagepl) {
            language="pl";
            updateLang();
        }   
        
        if (e.getSource()==rblanguageiw) {
            language="iw";
            updateLang();
        }   
        
        if (e.getSource()==rblanguageeo) {
        	language="eo";
        	updateLang();
        } 
        
        if (e.getSource()==rblanguagegr) {
        	language="gr";
        	updateLang();
        } 
        
        for (int i0=0; i0<lessonsMenuItem.length; i0++) {
        for (int i=0; i<lessonsMenuItem[0].length; i++) {
        	if (e.getSource() == lessonsMenuItem[i0][i]) {
        		handleLessonMenuItem(lessonsMenuItem[i0][i].getText(),i0);
        		System.out.println("lesson " + i0 + i + lessonsMenuItem[i0][i].getText());
        	}
        }
        }
        
         if (e.getSource()==menuPrefs) {
        	stopGames();
            backupPreferences();

            preferencesDialog.setLocationRelativeTo(this);
            preferencesDialog.setVisible(true);

        } else if (e.getSource()==aidesommaire) {
            stopGames();
            Object[] options={bundle.getString("_yes"),
                bundle.getString("_no")};
            int n=JOptionPane.showOptionDialog(this,
                bundle.getString("_wikidoc"),
                "Information",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //don't use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

            if (n==0) {
                OpenURI("http://www.jalmus.net/pmwiki/pmwiki.php/"+language);
            }

        } else if (e.getSource()==siteinternet) {
        	stopGames();
           OpenURI("http://jalmus.net?lang="+language);
        } else if (e.getSource()==okscoreMessage) {
            scoreMessage.dispose();

            if (isLessonMode) {
                if ( (currentlesson.isNoteLevel() & currentScore.isWin()) || currentlesson.isRhythmLevel() || currentlesson.isScoreLevel())

                {
                    nextLevel();
           
                } else {
                    startLevel();
                }
            }
        } else if (e.getSource()==menuMidi) {

            if (parti) {
                paused=true;
            }
            backupMidiOptions();
            midiOptionsDialog.setLocationRelativeTo(this);
            midiOptionsDialog.setVisible(true);
        } else if (e.getSource()==bfermer) {
            aboutDialog.setVisible(false);
        } else if (e.getSource()==propos) {
        	stopGames();
            aboutDialog.setContentPane(papropos);
            texteapropos.setText(tcredits);
            aboutDialog.setSize(400, 330);
            aboutDialog.setLocationRelativeTo(this);
            aboutDialog.setVisible(true);

        } else if (e.getSource()==blicence) {
            texteapropos.setText(tlicence);

        } else if (e.getSource()==bcredits) {
            texteapropos.setText(tcredits);
        } 

        //  SI LE LABEL DU BOUTON SELECTIONNE EST EGAL A LA NOTE COURANTE   ----> GAGNE

        if ((parti && selectedGame==NOTEREADING && !paused) && (e.getSource()==bdo || e.getSource()==bre || e.getSource()==bmi || e.getSource()==bfa
            || e.getSource()==bsol || e.getSource()==bla || e.getSource()==bsi || e.getSource()==bdo2
            || e.getSource()==bdiese || e.getSource()==bdiese2 || e.getSource()==bbemol || e.getSource()==bbemol2)) {

            if (!ncourante.getAlteration().equals("")) {  // NOTES AVEC ALTERATION
                if (((JButton)e.getSource()).getText().equals(ncourante.getAlteration())) {
                    alterationok=true;
                } else
                if (alterationok && ((JButton)e.getSource()).getText().equals(ncourante.getNom())) {
                    rightAnswer();
                } else {
                    wrongAnswer();
                }
            } else
            if (ncourante.getAlteration().equals("")) { // NOTE SANS ALTERATION
                //     System.out.println(ncourante.getNom());
                if (((JButton)e.getSource()).getText()==ncourante.getNom()) {
                    //  System.out.println( ( (JButton) e.getSource()).getText());
                    rightAnswer();
                } else {
                    wrongAnswer();
                }
            }

        }
        repaint();
    }

    private void handleExitMenuItem() {
    	stopGames();
        dispose();
    }


    
    private void handleLessonMenuItem(String lesson, Integer i) {
    String parseerror;
    
    stopGames();
    isLessonMode=true;

    try {
        // crÃ©ation d'une fabrique de parseurs SAX
        SAXParserFactory fabrique=SAXParserFactory.newInstance();

        // crÃ©ation d'un parseur SAX
        SAXParser parseur=fabrique.newSAXParser();
        currentlesson=new Lessons();
        // lecture d'un fichier XML avec un DefaultHandler

        File lessonFile=new File(pathsubdir[i]+File.separator+lesson+".xml");
        parseur.parse(lessonFile, currentlesson);

        if (currentlesson.isNoteLevel()) {	
        	noteLevel.copy((NoteLevel)currentlesson.getLevel());
        	noteLevel.updatenbnotes(piano);

        	selectedGame = NOTEREADING;
        	initNoteGame();

        	changeScreen();
        	noteLevel.printtest();
        	startLevel();
        }
        else if (currentlesson.isRhythmLevel()) {	
        	rhythmLevel.copy((RhythmLevel)currentlesson.getLevel());
        

        	selectedGame = RHYTHMREADING;       
        	changeScreen();
        	initRhythmGame();
        	rhythmLevel.printtest();
            newButton.doClick();
        	startLevel();
        }
        
        else if (currentlesson.isScoreLevel()) {	
        	scoreLevel.copy((ScoreLevel)currentlesson.getLevel());
        
        	scoreLevel.printtest();
        	selectedGame = SCOREREADING;
        	changeScreen();
        	initRhythmGame();
           newButton.doClick();
        	startLevel();
    
        }

    } catch (ParserConfigurationException pce) {
        parseerror="Configuration Parser error.";
        
        JOptionPane.showMessageDialog(this, parseerror, "Warning", JOptionPane.WARNING_MESSAGE);

    } catch (SAXException se) {
        parseerror="Parsing error : "+se.getMessage();
       
        JOptionPane.showMessageDialog(this, parseerror, "Warning", JOptionPane.WARNING_MESSAGE);

        se.printStackTrace();
    } catch (IOException ioe) {
        parseerror="Parsing error : I/O error";
       
        JOptionPane.showMessageDialog(this, parseerror, "Warning", JOptionPane.WARNING_MESSAGE);

    }

  //  lessonsDialog.setVisible(false);

}

    private void handleRhythmReadingMenuItem() {
    	stopGames();
/*
    	if (latencySlider.getValue() == 0)
    	  JOptionPane.showMessageDialog(this, bundle.getString("_setlatency"),
                  "", JOptionPane.INFORMATION_MESSAGE);
*/
     //   pgamebutton.removeAll();
    
        pgamebutton.add(newButton);
        pgamebutton.add(listenButton);
        pgamebutton.add(startButton);
        pgamebutton.add(preferencesButton);
        scoreYpos=110;
        repaint();
        
        
        selectedGame = RHYTHMREADING;
       newButton.doClick();
        if (isLessonMode) {
            noteLevel.init();
        }
        isLessonMode=false;
        changeScreen();
    }
    
    private void handleScoreReadingMenuItem() {
    	stopGames();
       //   pgamebutton.removeAll();
    	
/*
    	if (latencySlider.getValue() == 0)
    	  JOptionPane.showMessageDialog(this, bundle.getString("_setlatency"),
                  "", JOptionPane.INFORMATION_MESSAGE);
*/
          pgamebutton.add(newButton);
          pgamebutton.add(listenButton);
          pgamebutton.add(startButton);
          pgamebutton.add(preferencesButton);
          scoreYpos=110;
          repaint();
          
          
          selectedGame = SCOREREADING;
          newButton.doClick();
          if (isLessonMode) {
              noteLevel.init();
          }
          isLessonMode=false;
          changeScreen();
      }

    private void handleNoteReadingMenuItem() {
    	stopGames();
    	 pgamebutton.removeAll();
 
        pgamebutton.add(startButton);
        pgamebutton.add(pnotes);
        pgamebutton.add(preferencesButton);
        
        initNoteGame();
        if (isLessonMode) {
            noteLevel.init();
        }
        selectedGame = NOTEREADING;
        isLessonMode=false;
        changeScreen();
    }

    private void handleMidiOptionsCancelClicked() {
        restoreMidiOptions();
        midiOptionsDialog.setVisible(false);
        if (paused) {
            paused=false;
        }
    }

    private void handleMidiOptionsOkClicked() {
        midiOptionsDialog.setVisible(false);
        if (paused) {
            paused=false;
        }
    }

    private void handleStartButtonClicked() {
    	stopSound();
        if (selectedGame==NOTEREADING) {
            if (parti) {
            	stopNoteGame();
                initNoteGame(); //stop the game before restart

               
            } else {
                requestFocus();
                startNoteGame();
                if (!renderingThread.isAlive()) {
                    renderingThread.start();
                }
            }
        } else if (selectedGame==RHYTHMREADING || selectedGame==SCOREREADING) {
        	if (parti) {
        		
        		stopRhythmGame();
        		parti = false;
        		
        	}
        	else if (paintrhythms){
        		samerhythms = true;
        		muterhythms = true;
        		initRhythmGame();
        		startRhythmGame();
        	  

        	}
        }
    }

    private void handleListenButtonClicked() {
 
    	samerhythms = true;
    	muterhythms = false;
    	initRhythmGame();
    	startRhythmGame();
    	
    }
    
    
    private void handleNewButtonClicked() {
    	 
    	samerhythms = false;
    	muterhythms = false;
	initRhythmGame();
    	paintrhythms = true; 
    	repaint(); //only to paint exercise
    	parti = false;
    	
    }
    
    private void handlePreferencesClicked() {
        if (selectedGame==NOTEREADING) {
            preferencesTabbedPane.setSelectedIndex(NOTE_READING_TAB);
        } else if (selectedGame==RHYTHMREADING) {
            preferencesTabbedPane.setSelectedIndex(RHYTM_READING_TAB);     
        }
        else if (selectedGame==SCOREREADING) {
            preferencesTabbedPane.setSelectedIndex(SCORE_READING_TAB);     
        }
        menuPrefs.doClick();
    }

    private void handleLevelOkClicked() {
        levelMessage.dispose();
        if (isLessonMode) {
            startButton.doClick();
        }
    }

    private void handlePreferencesCancelClicked() {
        restorePreferences();
        preferencesDialog.setVisible(false);
    }

    private void handlePreferencesSaveClicked() {

    		
    		saveDialog.setTitle(bundle.getString("_buttonsave"));
    	//	saveDialog.setLayout(new GridLayout(3, 1));    
    		saveDialog.pack();
    		saveDialog.setLocationRelativeTo(this);
    		saveDialog.setVisible(true);

           
    }
    
    private void handleOKSave() {
    	try {
    		
    		if (Lessonname.getText().length()!=0){
    		if (preferencesTabbedPane.getSelectedIndex() == 0) noteLevel.save(currentlesson,Lessonname.getText()+".xml", Lessonmessage.getText(), language);
    		else if (preferencesTabbedPane.getSelectedIndex() == 1) {
    			rhythmLevel.printtest();
    			rhythmLevel.save(currentlesson,Lessonname.getText()+".xml", Lessonmessage.getText(), language);
    		}
    		else if (preferencesTabbedPane.getSelectedIndex() == 2) {
    			scoreLevel.printtest();
    			scoreLevel.save(currentlesson,Lessonname.getText()+".xml", Lessonmessage.getText(), language);
    		}
    		
    		saveDialog.setVisible(false);
			
			lessonsMenu.removeAll();
			buildLessonsMenu();
		        
    		}
    		else JOptionPane.showMessageDialog(null, "Give the name of the lesson", "Warning", JOptionPane.ERROR_MESSAGE); 
    	      
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void handlePreferencesOkClicked() {

    	if (selectedGame==NOTEREADING) {
          // update current level for note reading
          noteLevel.inibasenote();
          initNoteGame();
        
          noteLevel.updatenbnotes(piano);
    	}
    	
    	else if (selectedGame==RHYTHMREADING) {
    		
          // update parameters for rhythm reading NO MORE NEEDED now on Itemstatechanged
        /*  if (! wholeCheckBox.isSelected() && !halfCheckBox.isSelected() && 
       		  !quarterCheckBox.isSelected() && !eighthCheckBox.isSelected()) {

            JOptionPane.showMessageDialog(this,
                bundle.getString("_leastrythm"),
                "Warning",
                JOptionPane.WARNING_MESSAGE);

          } else {
            rhythmLevel.adjustLevel(wholeCheckBox.isSelected(), halfCheckBox.isSelected(), quarterCheckBox.isSelected(),
                eighthCheckBox.isSelected(), restCheckBox.isSelected(), tripletCheckBox.isSelected());
          }*/
          newButton.doClick();
    	}
    	
    	else if ( selectedGame==SCOREREADING) {
    		
    		 scoreLevel.printtest();
            // update parameters for rhythm reading
          /*  if (!scorewholeCheckBox.isSelected() && !scorehalfCheckBox.isSelected() && !scorequarterCheckBox.isSelected() && !scoreeighthCheckBox.isSelected()) {

                JOptionPane.showMessageDialog(this,
                    bundle.getString("_leastrythm"),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);

            } else {
                scoreLevel.updateRhythm(scorewholeCheckBox.isSelected(), scorehalfCheckBox.isSelected(), scorequarterCheckBox.isSelected(),
                		scoreeighthCheckBox.isSelected(), scorerestCheckBox.isSelected(), scoreTripletCheckBox.isSelected());
            }*/
            newButton.doClick();
        }

        // update screen
        changeScreen();
        preferencesDialog.setVisible(false);
        repaint();
    }

    private void backupPreferences() {
        savePrefs[0]=noteGameTypeComboBox.getSelectedIndex();
        savePrefs[1]=noteGameSpeedComboBox.getSelectedIndex();
        savePrefs[2]=keyComboBox.getSelectedIndex();
        savePrefs[4]=keySignatureCheckBox.getSelectedIndex();
        savePrefs[5]=noteGameSpeedComboBox.getSelectedIndex();
        savePrefs[6]=noteGroupComboBox.getSelectedIndex();
        if (noteGroupComboBox.getSelectedIndex()==0 || noteGroupComboBox.getSelectedIndex()==1) {
            savePrefs[7]=noteCountComboBox.getSelectedIndex();
        } else if (noteGroupComboBox.getSelectedIndex()==2) {
            savePrefs[7]=intervalComboBox.getSelectedIndex();
        } else if (noteGroupComboBox.getSelectedIndex()==3) {
            savePrefs[7]=chordTypeComboBox.getSelectedIndex();
        }
        savePrefs[8]=rhythmGameTypeComboBox.getSelectedIndex();
        savePrefs[9]=rhythmGameSpeedComboBox.getSelectedIndex();
        if (wholeCheckBox.isSelected()) {
            savePrefs[10]=1;
        } else {
            savePrefs[10]=0;
        }
        if (halfCheckBox.isSelected()) {
            savePrefs[11]=1;
        } else {
            savePrefs[11]=0;
        }
        if (dottedhalfCheckBox.isSelected()) {
            savePrefs[28]=1;
        } else {
            savePrefs[28]=0;
        }
        if (quarterCheckBox.isSelected()) {
            savePrefs[12]=1;
        } else {
            savePrefs[12]=0;
        }
        if (eighthCheckBox.isSelected()) {
            savePrefs[13]=1;
        } else {
            savePrefs[13]=0;
        }
        if (restCheckBox.isSelected()) {
            savePrefs[14]=1;
        } else {
            savePrefs[14]=0;
        }
        if (metronomeCheckBox.isSelected()) {
            savePrefs[15]=1;
        } else {
            savePrefs[15]=0;
        }
        savePrefs[16]=scoreGameTypeComboBox.getSelectedIndex();
        savePrefs[17]=scoreGameSpeedComboBox.getSelectedIndex();
        if (scorewholeCheckBox.isSelected()) {
            savePrefs[18]=1;
        } else {
            savePrefs[18]=0;
        }
        if (scorehalfCheckBox.isSelected()) {
            savePrefs[19]=1;
        } else {
            savePrefs[19]=0;
        }
        if (scoredottedhalfCheckBox.isSelected()) {
            savePrefs[28]=1;
        } else {
            savePrefs[28]=0;
        }
        if (scorequarterCheckBox.isSelected()) {
            savePrefs[20]=1;
        } else {
            savePrefs[20]=0;
        }
        if (scoreeighthCheckBox.isSelected()) {
            savePrefs[21]=1;
        } else {
            savePrefs[21]=0;
        }
        if (scorerestCheckBox.isSelected()) {
            savePrefs[22]=1;
        } else {
            savePrefs[22]=0;
        }
        if (scoreMetronomeCheckBox.isSelected()) {
            savePrefs[23]=1;
        } else {
            savePrefs[23]=0;
        }
        savePrefs[24]=scoreKeyComboBox.getSelectedIndex();
        savePrefs[25]=scoreAlterationsComboBox.getSelectedIndex();
        if (tripletCheckBox.isSelected()) {
            savePrefs[26]=1;
        } else {
            savePrefs[26]=0;
        }
        if (scoreTripletCheckBox.isSelected()) {
            savePrefs[27]=1;
        } else {
            savePrefs[27]=0;
        }
    }

    private void restorePreferences() {

        noteGameTypeComboBox.setSelectedIndex(savePrefs[0]);
        noteGameSpeedComboBox.setSelectedIndex(savePrefs[1]);
        keyComboBox.setSelectedIndex(savePrefs[2]);
        keySignatureCheckBox.setSelectedIndex(savePrefs[4]);
        noteGameSpeedComboBox.setSelectedIndex(savePrefs[5]);
        noteGroupComboBox.setSelectedIndex(savePrefs[6]);
        if (noteGroupComboBox.getSelectedIndex()==0 || noteGroupComboBox.getSelectedIndex()==1) {
            noteCountComboBox.setSelectedIndex(savePrefs[7]);
        } else if (noteGroupComboBox.getSelectedIndex()==2) {
            intervalComboBox.setSelectedIndex(savePrefs[7]);
        } else if (noteGroupComboBox.getSelectedIndex()==3) {
            chordTypeComboBox.setSelectedIndex(savePrefs[7]);
        }
        rhythmGameTypeComboBox.setSelectedIndex(savePrefs[8]);
        rhythmGameSpeedComboBox.setSelectedIndex(savePrefs[9]);
        if (savePrefs[10]==1) {
            wholeCheckBox.setSelected(true);
        } else {
            wholeCheckBox.setSelected(false);
        }
        if (savePrefs[11]==1) {
            halfCheckBox.setSelected(true);
        } else {
            halfCheckBox.setSelected(false);
        }
        if (savePrefs[28]==1) {
            dottedhalfCheckBox.setSelected(true);
        } else {
            dottedhalfCheckBox.setSelected(false);
        }
        if (savePrefs[12]==1) {
            quarterCheckBox.setSelected(true);
        } else {
            quarterCheckBox.setSelected(false);
        }
        if (savePrefs[13]==1) {
            eighthCheckBox.setSelected(true);
        } else {
            eighthCheckBox.setSelected(false);
        }
        if (savePrefs[14]==1) {
            restCheckBox.setSelected(true);
        } else {
            restCheckBox.setSelected(false);
        }
        if (savePrefs[15]==1) {
            metronomeCheckBox.setSelected(true);
        } else {
            metronomeCheckBox.setSelected(false);
        }
       
        scoreGameTypeComboBox.setSelectedIndex(savePrefs[16]);
        scoreGameSpeedComboBox.setSelectedIndex(savePrefs[17]);
        if (savePrefs[18]==1) {
            scorewholeCheckBox.setSelected(true);
        } else {
            scorewholeCheckBox.setSelected(false);
        }
        if (savePrefs[19]==1) {
            scorehalfCheckBox.setSelected(true);
        } else {
            scorehalfCheckBox.setSelected(false);
        }
        if (savePrefs[28]==1) {
            scoredottedhalfCheckBox.setSelected(true);
        } else {
            scoredottedhalfCheckBox.setSelected(false);
        }
        if (savePrefs[20]==1) {
            scorequarterCheckBox.setSelected(true);
        } else {
            scorequarterCheckBox.setSelected(false);
        }
        if (savePrefs[21]==1) {
            scoreeighthCheckBox.setSelected(true);
        } else {
            scoreeighthCheckBox.setSelected(false);
        }
        if (savePrefs[22]==1) {
            scorerestCheckBox.setSelected(true);
        } else {
            scorerestCheckBox.setSelected(false);
        }
        if (savePrefs[23]==1) {
            scoreMetronomeCheckBox.setSelected(true);
        } else {
            scoreMetronomeCheckBox.setSelected(false);
        }
        scoreKeyComboBox.setSelectedIndex(savePrefs[24]);
        scoreAlterationsComboBox.setSelectedIndex(savePrefs[25]);
        if (savePrefs[26]==1) {
            tripletCheckBox.setSelected(true);
        } else {
        	tripletCheckBox.setSelected(false);
        }
        if (savePrefs[27]==1) {
        	scoreTripletCheckBox.setSelected(true);
        } else {
        	scoreTripletCheckBox.setSelected(false);
        }

    }
    

    /**
     * Open uri with default browser
     *
     * @param  uristring uri to open
     * @return      void
     *
     */
     public  void OpenURI(String uristring) {

            if( !java.awt.Desktop.isDesktopSupported() ) {
                System.err.println( "Desktop is not supported (fatal)" );
                System.exit( 1 );
            }

            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

            if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
                System.err.println( "Desktop doesn't support the browse action (fatal)" );
                System.exit( 1 );
            }
      
            try {
                 java.net.URI uri = new java.net.URI( uristring );
                 desktop.browse( uri );
                }
            catch ( Exception e ) {
                    System.err.println( e.getMessage() );
                }       
        }
    
     
     public  void OpenDirectory(File dir) {

         if( !java.awt.Desktop.isDesktopSupported() ) {
             System.err.println( "Desktop is not supported (fatal)" );
             System.exit( 1 );
         }

         java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

         if( !desktop.isSupported( java.awt.Desktop.Action.OPEN ) ) {
             System.err.println( "Desktop doesn't support the open action (fatal)" );
             System.exit( 1 );
         }
   
         try {
       
              desktop.open(dir);
             }
         catch ( Exception e ) {
                 System.err.println( e.getMessage() );
             }       
     } 
    private void savesettings(){
    		settings.setProperty("transposition",String.valueOf(transpositionSpinner.getValue())); 
	    	if (keyboardLengthComboBox.getSelectedIndex()==1) 
	    		settings.setProperty("keyboardlength","61");
	    	else settings.setProperty("keyboardlength","73");
	    	settings.setProperty("keyboard",String.valueOf(midiInComboBox.getSelectedIndex())); 
	    	settings.setProperty("midiout",String.valueOf(midiOutComboBox.getSelectedIndex()));
	    	settings.setProperty("instrument",String.valueOf(instrumentsComboBox.getSelectedIndex())); 
	    	if (soundOnCheckBox.isSelected())   settings.setProperty("sound","on");
		    else settings.setProperty("sound","off"); 
	    	if (keyboardsoundCheckBox.isSelected())   settings.setProperty("keyboardsound","on");
		    else settings.setProperty("keyboardsound","off"); 
	    	settings.setProperty("latency",String.valueOf(latencySlider.getValue())); 
	    	//settings.setProperty("speedcursor",String.valueOf(speedcursorSlider.getValue()));
	    	 
	   	 	settings.setProperty("language",language);
       
        
        try { 
        	settings.store(new FileOutputStream("settings.properties"), null); 
        	settings.list(System.out);
        	} 
        catch (IOException e) { } 
   	
   }

    private void backupMidiOptions() {
        //TODO: This approach does not work when midi device order is changed!!
    	if (soundOnCheckBox.isSelected()) {
            sauvmidi[0]=1;
        } else {
            sauvmidi[0]=0;
        }
        sauvmidi[1]=instrumentsComboBox.getSelectedIndex();
        sauvmidi[2]=midiInComboBox.getSelectedIndex();
        sauvmidi[3]= ((Number)transpositionSpinner.getValue()).intValue();
        if (keyboardsoundCheckBox.isSelected()) {
            sauvmidi[4]=1;
        } else {
            sauvmidi[4]=0;
        }
        sauvmidi[5]=midiOutComboBox.getSelectedIndex();

    }

    private void restoreMidiOptions() {
        //TODO: This approach does not work when midi device order is changed!!
        if (sauvmidi[0]==1) {
            soundOnCheckBox.setSelected(true);
        } else {
            soundOnCheckBox.setSelected(false);
        }
        instrumentsComboBox.setSelectedIndex(sauvmidi[1]);
        midiInComboBox.setSelectedIndex(sauvmidi[2]);
        transpositionSpinner.setValue(sauvmidi[3]);
        if (sauvmidi[4]==1) {
            keyboardsoundCheckBox.setSelected(true);
        } else {
            keyboardsoundCheckBox.setSelected(false);
        }
        midiOutComboBox.setSelectedIndex(sauvmidi[5]);

    }

    public void itemStateChanged(ItemEvent evt) {

        if (evt.getItemSelectable()==midiInComboBox && !selectmidi_forlang) {
            String smidiin=(String)midiInComboBox.getSelectedItem();
            if (smidiin!=pasclavier) {
            	
            	if (open)
            	{
            		inputDevice.close();
            		open=false;
            	}
                
            	String midimessage="Initialisation "+smidiin;

                MidiDevice.Info info=MidiCommon.getMidiDeviceInfo(smidiin, false);
                if (info==null) {

                    midimessage="nodevice";
                    System.out.println(midimessage);
                } else {

                    try {
                        inputDevice=MidiSystem.getMidiDevice(info);
                        inputDevice.open();

                        // open = true;
                    }
                    catch (MidiUnavailableException e) {
                        midimessage="nodevice";
                        System.out.println(midimessage);
                    }

                    Receiver r=new DumpReceiver();
                    try {
                        Transmitter t=inputDevice.getTransmitter();
                        t.setReceiver(r);
                    }
                    catch (MidiUnavailableException e) {
                        midimessage="wasn't able to connect the device's Transmitter to the Receiver:";
                        System.out.println(e);
                        inputDevice.close();
                        System.exit(1);
                    }
                    midimessage="End initialisation";
                }
                if (inputDevice.isOpen()) {
                    System.out.println("Midi Device open : play a key, if this key don't change his color at screen, verify the MIDI port name");
                }
                open=true;
            }
        }

        if (evt.getItemSelectable()==midiOutComboBox && !selectmidi_forlang) {
            String smidiout=(String)midiOutComboBox.getSelectedItem();
            if (smidiout!=pasclavier){// && !open) {
                String midimessage="Initialisation "+smidiout;

                MidiDevice.Info info=MidiCommon.getMidiDeviceInfo(smidiout, true);
                if (info==null) {

                    midimessage="nodevice";
                    System.out.println(midimessage);
                } else {
	
                    try {
                        outputDevice=MidiSystem.getMidiDevice(info);
                        outputDevice.open();

                        // open = true;
                    }
                    catch (MidiUnavailableException e) {
                        midimessage="nodevice";
                        System.out.println(midimessage);
                    }

                    //Receiver r=new DumpReceiver();
                    //try {
                    //    Receiver t=outputDevice.getReceiver();
                    //}
                    //catch (MidiUnavailableException e) {
                    //    midimessage="wasn't able to connect the device's Receiver to the Receiver:";
                    //    System.out.println(e);
                    //    inputDevice.close();
                    //    System.exit(1);
                    //}
                    //midimessage="End initialisation";
                }
                //if (inputDevice.isOpen()) {
                //    System.out.println("Midi Device open : play a key, if this key don't change his color at screen, verify the MIDI port name");
                //}
                //open=true;
            }
        }
        
        
        // For rhythm level update
        else if (evt.getItemSelectable()==wholeCheckBox) {
        	if (wholeCheckBox.isSelected()) rhythmLevel.setWholeNote(true);
        	else rhythmLevel.setWholeNote(false);
        }
        else if (evt.getItemSelectable()==halfCheckBox) {
        	if (halfCheckBox.isSelected()) rhythmLevel.setHalfNote(true);
        	else rhythmLevel.setHalfNote(false);
        } 
        else if (evt.getItemSelectable()==dottedhalfCheckBox) {
        	if (dottedhalfCheckBox.isSelected()) rhythmLevel.setDottedHalfNote(true);
        	else rhythmLevel.setDottedHalfNote(false);
        } 
        else if (evt.getItemSelectable()==quarterCheckBox) {
        	if (quarterCheckBox.isSelected()) rhythmLevel.setQuarterNote(true);
        	else rhythmLevel.setQuarterNote(false);
        } 
        else if (evt.getItemSelectable()==eighthCheckBox) {
        	if (eighthCheckBox.isSelected()) rhythmLevel.setEighthNote(true);
        	else rhythmLevel.setEighthNote(false);
        } 
        else if (evt.getItemSelectable()==restCheckBox) {
        	if (restCheckBox.isSelected()) rhythmLevel.setSilence(true);
        	else rhythmLevel.setSilence(false);
        } 
        else if (evt.getItemSelectable()==tripletCheckBox) {
        	if (tripletCheckBox.isSelected()) rhythmLevel.setTriplet(true);
        	else rhythmLevel.setTriplet(false);
        } 
        else if (evt.getItemSelectable()== metronomeCheckBox) {
        	if (metronomeCheckBox.isSelected()) rhythmLevel.setMetronome(true);
        	else rhythmLevel.setMetronome(false);
        } 
        else if (evt.getItemSelectable()== metronomeShowCheckBox) {
        	if (metronomeShowCheckBox.isSelected()) rhythmLevel.setMetronomeBeats(true);
        	else rhythmLevel.setMetronomeBeats(false);
        } 
        
        
        // For score level update
        else if (evt.getItemSelectable()==scorewholeCheckBox) {
        	if (scorewholeCheckBox.isSelected()) scoreLevel.setWholeNote(true);
        	else scoreLevel.setWholeNote(false);
        }
        else if (evt.getItemSelectable()==scorehalfCheckBox) {
        	if (scorehalfCheckBox.isSelected()) scoreLevel.setHalfNote(true);
        	else scoreLevel.setHalfNote(false);
        } 
        else if (evt.getItemSelectable()==scoredottedhalfCheckBox) {
        	if (scoredottedhalfCheckBox.isSelected()) scoreLevel.setDottedHalfNote(true);
        	else scoreLevel.setDottedHalfNote(false);
        } 
        else if (evt.getItemSelectable()==scorequarterCheckBox) {
        	if (scorequarterCheckBox.isSelected()) scoreLevel.setQuarterNote(true);
        	else scoreLevel.setQuarterNote(false);
        } 
        else if (evt.getItemSelectable()==scoreeighthCheckBox) {
        	if (scoreeighthCheckBox.isSelected()) scoreLevel.setEighthNote(true);
        	else scoreLevel.setEighthNote(false);
        } 
        else if (evt.getItemSelectable()==scorerestCheckBox) {
        	if (scorerestCheckBox.isSelected()) scoreLevel.setSilence(true);
        	else scoreLevel.setSilence(false);
        } 
        else if (evt.getItemSelectable()==scoreTripletCheckBox) {
        	if (scoreTripletCheckBox.isSelected()) scoreLevel.setTriplet(true);
        	else scoreLevel.setTriplet(false);
        }
        else if (evt.getItemSelectable()== scoreMetronomeCheckBox) {
        	if (scoreMetronomeCheckBox.isSelected()) scoreLevel.setMetronome(true);
        	else scoreLevel.setMetronome(false);
        } 
        else if (evt.getItemSelectable()== scoreMetronomeShowCheckBox) {
        	if (scoreMetronomeShowCheckBox.isSelected()) scoreLevel.setMetronomeBeats(true);
        	else scoreLevel.setMetronomeBeats(false);
        } 
        
        
        else if (evt.getItemSelectable()==instrumentsComboBox) {
            if (!midierror && instruments!=null) {

                currentChannel.getchannel().programChange(instrumentsComboBox.getSelectedIndex());
            }
        } else if (evt.getItemSelectable()==keyComboBox) {
            if (keyComboBox.getSelectedIndex()==0) {
                noteLevel.setCurrentKey("treble");
                noteLevel.resetPitcheslist();
            } else if (keyComboBox.getSelectedIndex()==1) {
                noteLevel.setCurrentKey("bass");
                noteLevel.resetPitcheslist();
            } else if (keyComboBox.getSelectedIndex()==2) {
                noteLevel.setCurrentKey("both");
                noteLevel.resetPitcheslist();
            }

        }
        
        else if (evt.getItemSelectable()==scoreKeyComboBox) {
            if (scoreKeyComboBox.getSelectedIndex()==0) {
                scoreLevel.setCurrentKey("treble");
                scoreLevel.initPitcheslist(9);
                if (selectedGame == SCOREREADING ) initRhythmGame();
                
            } else if (scoreKeyComboBox.getSelectedIndex()==1) {
                scoreLevel.setCurrentKey("bass");
                scoreLevel.initPitcheslist(9);
                if (selectedGame == SCOREREADING ) initRhythmGame();
            }

        }

        // CHOIX ARMURE

        else if (evt.getItemSelectable()==keySignatureCheckBox) {
            initNoteGame();

            if (keySignatureCheckBox.getSelectedIndex()==0) {
                double tmp=Math.random();  // to choice same alteration for alterated notes
                String stmp;
                if (tmp<0.5) {
                    stmp="#";
                } else {
                    stmp="b";
                }
                noteLevel.setRandomtonality(false);
                noteLevel.getCurrentTonality().init(0, stmp);


            } else if (keySignatureCheckBox.getSelectedIndex()==15) {
                // choix de la tonalite au hasard au lancement du jeu
                noteLevel.setRandomtonality(true);
                noteLevel.getCurrentTonality().init(0, "r");
            } else {
                noteLevel.setRandomtonality(false);
                if (keySignatureCheckBox.getSelectedIndex()==1) {
                    noteLevel.getCurrentTonality().init(1, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==2) {
                    noteLevel.getCurrentTonality().init(2, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==3) {
                    noteLevel.getCurrentTonality().init(3, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==4) {
                    noteLevel.getCurrentTonality().init(4, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==5) {
                    noteLevel.getCurrentTonality().init(5, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==6) {
                    noteLevel.getCurrentTonality().init(6, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==7) {
                    noteLevel.getCurrentTonality().init(7, "#");
                } else if (keySignatureCheckBox.getSelectedIndex()==8) {
                    noteLevel.getCurrentTonality().init(1, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==9) {
                    noteLevel.getCurrentTonality().init(2, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==10) {
                    noteLevel.getCurrentTonality().init(3, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==11) {
                    noteLevel.getCurrentTonality().init(4, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==12) {
                    noteLevel.getCurrentTonality().init(5, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==13) {
                    noteLevel.getCurrentTonality().init(6, "b");
                } else if (keySignatureCheckBox.getSelectedIndex()==14) {
                    noteLevel.getCurrentTonality().init(7, "b");
                }


            }

        }
        // Game type choice
        else if (evt.getItemSelectable()==noteGameTypeComboBox) {
            if (noteGameTypeComboBox.getSelectedIndex()==0) {
                noteLevel.setGametype("normal");
            } else if (noteGameTypeComboBox.getSelectedIndex()==1) {
                noteLevel.setGametype("inline");
            } else if (noteGameTypeComboBox.getSelectedIndex()==2) {
                noteLevel.setGametype("learning");
            }
        }
        
        else if (evt.getItemSelectable()==rhythmGameTypeComboBox) {
            if (rhythmGameTypeComboBox.getSelectedIndex()==0) {
                rhythmgame = 0; // fix this with creating class level rhythm
            }/* else if (rhythmGameTypeComboBox.getSelectedIndex()==1) {
                rhythmgame = 1;
            
            }*/
        }

        // Speed choice note reading
        else if (evt.getItemSelectable()==noteGameSpeedComboBox) {
            if (noteGameSpeedComboBox.getSelectedIndex()==0) {
                noteLevel.setSpeed(28);
            } else if (noteGameSpeedComboBox.getSelectedIndex()==1) {
                noteLevel.setSpeed(22);
            } else if (noteGameSpeedComboBox.getSelectedIndex()==2) {
                noteLevel.setSpeed(16);
            } else if (noteGameSpeedComboBox.getSelectedIndex()==3) {
                noteLevel.setSpeed(12);
            } else if (noteGameSpeedComboBox.getSelectedIndex()==4) {
                noteLevel.setSpeed(8);
            }
            // System.out.println("-"+vitesse);
        }

        // Speed choice rhythm reading
        else if (evt.getItemSelectable()==rhythmGameSpeedComboBox) {
        	   if (rhythmGameSpeedComboBox.getSelectedIndex()==0) {
                   rhythmLevel.setSpeed(40);
               } else if (rhythmGameSpeedComboBox.getSelectedIndex()==1) {
               	 rhythmLevel.setSpeed(60);
               } else if (rhythmGameSpeedComboBox.getSelectedIndex()==2) {
               	 rhythmLevel.setSpeed(100);
               } else if (rhythmGameSpeedComboBox.getSelectedIndex()==3) {
               	 rhythmLevel.setSpeed(120);
               } else if (rhythmGameSpeedComboBox.getSelectedIndex()==4) {
               	 rhythmLevel.setSpeed(160);
               }
        }

        // Number of notes choice note reading
        else if (evt.getItemSelectable()==noteGroupComboBox) {

            if (noteGroupComboBox.getSelectedIndex()==0) {
                noteLevel.setNotetype("notes");
                noteReadingNotesPanel.removeAll();
                noteReadingNotesPanel.add(noteGroupComboBox);
                noteReadingNotesPanel.add(noteCountComboBox);
                noteReadingNotesPanel.repaint();
                preferencesDialog.repaint();
            }

            if (noteGroupComboBox.getSelectedIndex()==1) {
                noteLevel.setNotetype("accidentals");

                noteReadingNotesPanel.removeAll();
                noteReadingNotesPanel.add(noteGroupComboBox);
                noteReadingNotesPanel.add(noteCountComboBox);
                noteReadingNotesPanel.repaint();
                preferencesDialog.repaint();
                
            }  else if (noteGroupComboBox.getSelectedIndex()==2) {
                noteLevel.setNotetype("custom");

                noteReadingNotesPanel.removeAll();
                noteReadingNotesPanel.add(noteGroupComboBox);
                preferencesDialog.repaint();
                

            	ChooseNoteP = new  ChooseNotePanel(noteLevel.getKey(),NOTEREADING,  bundle);
                ChooseNoteP.updateTable(noteLevel.getPitcheslist());
                ChooseNoteP.setOpaque(true); //content panes must be opaque 
                ChooseNoteP.setVisible(true);
               ChooseNoteP.okButton.addActionListener(new ActionListener() {
            	   
                   public void actionPerformed(ActionEvent e)
                   {
                       //Execute when button is pressed
                		 if (! ChooseNoteP.atLeast3Pitches()) JOptionPane.showMessageDialog(null, "Choose at least three notes", "Warning", JOptionPane.ERROR_MESSAGE); 
                		 else    {
                			 notesDialog.setVisible(false);
                			 noteLevel.setPitcheslist(ChooseNoteP.getPitches());
                		 }
                      
                   }
               });   
                
                notesDialog.setContentPane(ChooseNoteP);
               notesDialog.setSize(650, 220);
                notesDialog.setLocationRelativeTo(this);
                notesDialog.setVisible(true);

                ChooseNoteP.setVisible(true);
            

                this.add(notesDialog);
                
            }
            else if (noteGroupComboBox.getSelectedIndex()==3) {
                noteLevel.setNotetype("intervals");

                noteReadingNotesPanel.removeAll();
                noteReadingNotesPanel.add(noteGroupComboBox);
                noteReadingNotesPanel.add(intervalComboBox);

                noteReadingNotesPanel.repaint();
                preferencesDialog.repaint();
                
            } else if (noteGroupComboBox.getSelectedIndex()==4) {

                noteLevel.setNotetype("chords");

                noteReadingNotesPanel.removeAll();
                noteReadingNotesPanel.add(noteGroupComboBox);
                noteReadingNotesPanel.add(chordTypeComboBox);

                noteReadingNotesPanel.repaint();
                preferencesDialog.repaint();

            }
        } else if (evt.getItemSelectable()==keyboardLengthComboBox) {
            if (keyboardLengthComboBox.getSelectedIndex()==0) {
                piano=new Piano(73, 40);
            } else if (keyboardLengthComboBox.getSelectedIndex()==1) {
                piano=new Piano(61, 90);
            }


        } else if (evt.getItemSelectable()==noteCountComboBox) {
            if (noteCountComboBox.getSelectedIndex()==0) {
                noteLevel.setNbnotes(3);
            } else if (noteCountComboBox.getSelectedIndex()==1) {
                noteLevel.setNbnotes(5);
            } else if (noteCountComboBox.getSelectedIndex()==2) {
                noteLevel.setNbnotes(9);
            } else if (noteCountComboBox.getSelectedIndex()==3) {
                noteLevel.setNbnotes(15);
            } else if (noteCountComboBox.getSelectedIndex()==4) {
                noteLevel.setNbnotes(0);
            }
            ;
        } else if (evt.getItemSelectable()==chordTypeComboBox) {
            if (chordTypeComboBox.getSelectedIndex()==0) {
                noteLevel.setChordtype("root");
            } else if (chordTypeComboBox.getSelectedIndex()==1) {
                noteLevel.setChordtype("inversion");
            }
        } else if (evt.getItemSelectable()==intervalComboBox) {

            if (intervalComboBox.getSelectedIndex()==0) {
                noteLevel.setIntervaltype("second");
            } else if (intervalComboBox.getSelectedIndex()==1) {
                noteLevel.setIntervaltype("third");
            } else if (intervalComboBox.getSelectedIndex()==2) {
                noteLevel.setIntervaltype("fourth");
            } else if (intervalComboBox.getSelectedIndex()==3) {
                noteLevel.setIntervaltype("fifth");
            } else if (intervalComboBox.getSelectedIndex()==4) {
                noteLevel.setIntervaltype("sixth");
            } else if (intervalComboBox.getSelectedIndex()==5) {
                noteLevel.setIntervaltype("seventh");
            } else if (intervalComboBox.getSelectedIndex()==6) {
                noteLevel.setIntervaltype("octave");
            } else if (intervalComboBox.getSelectedIndex()==7) {
                noteLevel.setIntervaltype("random");
            } else if (intervalComboBox.getSelectedIndex()==8) {
                noteLevel.setIntervaltype("all");
            }
        }
        
        else if (evt.getItemSelectable()==scoreGameSpeedComboBox) {
       
                if (scoreGameSpeedComboBox.getSelectedIndex()==0) {
                    scoreLevel.setSpeed(40);
                } else if (scoreGameSpeedComboBox.getSelectedIndex()==1) {
                	 scoreLevel.setSpeed(60);
                } else if (scoreGameSpeedComboBox.getSelectedIndex()==2) {
                	 scoreLevel.setSpeed(100);
                } else if (scoreGameSpeedComboBox.getSelectedIndex()==3) {
                	 scoreLevel.setSpeed(120);
                } else if (scoreGameSpeedComboBox.getSelectedIndex()==4) {
                	 scoreLevel.setSpeed(160);
                }
            
        }
        
        else if (evt.getItemSelectable()==scoreNotesComboBox) {
        	 if (scoreNotesComboBox.getSelectedIndex()==0) {
        		  scoreLevel.setNotetype("notes");
                 scoreLevel.setNbnotes(9);
             } 
        	 if (scoreNotesComboBox.getSelectedIndex()==1) {
        		  scoreLevel.setNotetype("notes");
                 scoreLevel.setNbnotes(15);
             } 
        	 if (scoreNotesComboBox.getSelectedIndex()==2) {
                 scoreLevel.setNbnotes(0);
                 scoreLevel.setNotetype("custom");

                 
              	ScoreChooseNoteP = new  ChooseNotePanel(scoreLevel.getKey(), SCOREREADING, bundle);
                ScoreChooseNoteP.updateTable(scoreLevel.getPitcheslist());
                ScoreChooseNoteP.setOpaque(true); //content panes must be opaque 
                ScoreChooseNoteP.setVisible(true);
               ScoreChooseNoteP.okButton.addActionListener(new ActionListener() {
            	   
                   public void actionPerformed(ActionEvent e)
                   {
                       //Execute when button is pressed
                		 if (! ScoreChooseNoteP.atLeast3Pitches()) JOptionPane.showMessageDialog(null, "Choose at least three notes", "Warning", JOptionPane.ERROR_MESSAGE); 
                		 else    {
                			 ScorenotesDialog.setVisible(false);
                			 scoreLevel.setPitcheslist(ScoreChooseNoteP.getPitches());
                		 }
                      
                   }
               });    
               
   
                 
                 ScorenotesDialog.setContentPane(ScoreChooseNoteP);
                ScorenotesDialog.setSize(650, 220);
                 ScorenotesDialog.setLocationRelativeTo(this);
                 ScorenotesDialog.setVisible(true);
                 
              
                 ScoreChooseNoteP.setVisible(true);
             

                 this.add(ScorenotesDialog);
             } 
        }
      
        else if (scoreAlterationsComboBox.getSelectedIndex()==15) {
            // random choice of tonality when game start
            scoreLevel.setRandomtonality(true);
            scoreLevel.getCurrentTonality().init(0, "r");
        } else {
            scoreLevel.setRandomtonality(false);
            if (scoreAlterationsComboBox.getSelectedIndex()==1) {
                scoreLevel.getCurrentTonality().init(1, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==2) {
                scoreLevel.getCurrentTonality().init(2, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==3) {
                scoreLevel.getCurrentTonality().init(3, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==4) {
                scoreLevel.getCurrentTonality().init(4, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==5) {
                scoreLevel.getCurrentTonality().init(5, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==6) {
                scoreLevel.getCurrentTonality().init(6, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==7) {
            	scoreLevel.getCurrentTonality().init(7, "#");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==8) {
            	scoreLevel.getCurrentTonality().init(1, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==9) {
            	scoreLevel.getCurrentTonality().init(2, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==10) {
            	scoreLevel.getCurrentTonality().init(3, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==11) {
            	scoreLevel.getCurrentTonality().init(4, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==12) {
            	scoreLevel.getCurrentTonality().init(5, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==13) {
            	scoreLevel.getCurrentTonality().init(6, "b");
            } else if (scoreAlterationsComboBox.getSelectedIndex()==14) {
            	scoreLevel.getCurrentTonality().init(7, "b");
            }


        }

   
    }

    // Translating functions

    private void updateLang() {

    	
         
        bundle = ResourceBundle.getBundle("language", new Locale(language));
      System.out.println(new Locale(language));
        changeLanguage();

    }

    private void changeLanguage() {
        for (Iterator<Localizable> itr=localizables.iterator(); itr.hasNext();) {
            Localizable localizable=(Localizable)itr.next();
            localizable.update(bundle);
        }

        menuParameters.setText(bundle.getString("_menuSettings"));
        menuPrefs.setText(bundle.getString("_menuPreferences"));
        menuMidi.setText(bundle.getString("_menuMidi"));
        languages.setText(bundle.getString("_menuLanguage"));
        aide.setText(bundle.getString("_menuHelp"));
        aidesommaire.setText(bundle.getString("_menuContents"));
        siteinternet.setText(bundle.getString("_menuWeb"));
        propos.setText(bundle.getString("_menuAbout"));
        timeSignLabel.setText(bundle.getString("_timeSignature"));
        scoreTimeSignLabel.setText(bundle.getString("_timeSignature"));
        aboutDialog.setTitle(bundle.getString("_menuAbout"));
		notesDialog.setTitle("Choose notes to study");
		ScorenotesDialog.setTitle("Choose notes to study");


        tlicence=bundle.getString("_licence");
        tcredits=bundle.getString("_credits");

        keyComboBox.removeAllItems();
        keyComboBox.addItem(bundle.getString("_trebleclef"));
        keyComboBox.addItem(bundle.getString("_bassclef"));
        keyComboBox.addItem(bundle.getString("_bothclefs"));

        noteGroupComboBox.removeAllItems();
        noteGroupComboBox.addItem(bundle.getString("_notes"));
        noteGroupComboBox.addItem(bundle.getString("_alterednotes"));
        noteGroupComboBox.addItem(bundle.getString("_customnotes"));
        noteGroupComboBox.addItem(bundle.getString("_intervals"));
        noteGroupComboBox.addItem(bundle.getString("_chords"));

        noteGameTypeComboBox.removeAllItems();
        noteGameTypeComboBox.addItem(bundle.getString("_normalgame"));
        noteGameTypeComboBox.addItem(bundle.getString("_linegame"));
        noteGameTypeComboBox.addItem(bundle.getString("_learninggame"));
        
        rhythmGameTypeComboBox.removeAllItems();
      //  rhythmGameTypeComboBox.addItem(bundle.getString("_learninggame"));
        rhythmGameTypeComboBox.addItem(bundle.getString("_normalgame"));

        noteCountComboBox.removeAllItems();
        noteCountComboBox.addItem("3 "+bundle.getString("_menuNotes"));
        noteCountComboBox.addItem("5 "+bundle.getString("_menuNotes"));
        noteCountComboBox.addItem("9 "+bundle.getString("_menuNotes"));
        noteCountComboBox.addItem("15 "+bundle.getString("_menuNotes"));
        noteCountComboBox.addItem(bundle.getString("_all"));

        keySignatureCheckBox.removeAllItems();
        keySignatureCheckBox.addItem(bundle.getString("_nosharpflat"));
        keySignatureCheckBox.addItem("1 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("2 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("3 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("4 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("5 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("6 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("7 "+bundle.getString("_sharp"));
        keySignatureCheckBox.addItem("1 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("2 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("3 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("4 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("5 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("6 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem("7 "+bundle.getString("_flat"));
        keySignatureCheckBox.addItem(bundle.getString("_random"));

        keyboardLengthComboBox.removeAllItems();
        keyboardLengthComboBox.addItem("73 "+bundle.getString("_keys"));
        keyboardLengthComboBox.addItem("61 "+bundle.getString("_keys"));



        seconde=bundle.getString("_second");
        tierce=bundle.getString("_third");
        quarte=bundle.getString("_fourth");
        quinte=bundle.getString("_fifth");
        sixte=bundle.getString("_sixth");
        septieme=bundle.getString("_seventh");
        octave=bundle.getString("_octave");
        mineur=bundle.getString("_minor");
        majeur=bundle.getString("_major");
      

        intervalComboBox.removeAllItems();
        intervalComboBox.addItem(seconde);
        intervalComboBox.addItem(tierce);
        intervalComboBox.addItem(quarte);
        intervalComboBox.addItem(quinte);
        intervalComboBox.addItem(sixte);
        intervalComboBox.addItem(septieme);
        intervalComboBox.addItem(octave);
        intervalComboBox.addItem(bundle.getString("_random"));
        intervalComboBox.addItem(bundle.getString("_all"));

        chordTypeComboBox.removeAllItems();
        chordTypeComboBox.addItem(bundle.getString("_rootposition"));
        chordTypeComboBox.addItem(bundle.getString("_inversion"));

        soundOnCheckBox.setText(bundle.getString("_notessound"));
        keyboardsoundCheckBox.setText(bundle.getString("_keyboardsound"));
        metronomeCheckBox.setText(bundle.getString("_menuMetronom"));
        metronomeShowCheckBox.setText(bundle.getString("_menuShowMetronom"));

        selectmidi_forlang=true;
        int indextmp = midiInComboBox.getSelectedIndex();
        midiInComboBoxModel.removeElementAt(0);
        midiInComboBoxModel.insertElementAt(bundle.getString("_nomidiin"), 0);
        midiInComboBox.setSelectedIndex(indextmp);
        indextmp = midiOutComboBox.getSelectedIndex();
        midiOutComboBoxModel.removeElementAt(0);
        midiOutComboBoxModel.insertElementAt(bundle.getString("_nomidiin"), 0);
        midiOutComboBox.setSelectedIndex(indextmp);
        selectmidi_forlang=false;

        wholeCheckBox.setText(bundle.getString("_wholenote"));
        halfCheckBox.setText(bundle.getString("_halfnote"));
        dottedhalfCheckBox.setText(bundle.getString("_dottedhalfnote"));
        quarterCheckBox.setText(bundle.getString("_quarternote"));
        eighthCheckBox.setText(bundle.getString("_eighthnote"));
        restCheckBox.setText(bundle.getString("_rest"));
        tripletCheckBox.setText(bundle.getString("_triplet"));

        okscoreMessage.setText(bundle.getString("_buttonok"));
        bfermer.setText(bundle.getString("_buttonclose"));
        bcredits.setText(bundle.getString("_buttoncredits"));
        blicence.setText(bundle.getString("_buttonlicense"));
     
        DO=bundle.getString("_do");
        RE=bundle.getString("_re");
        MI=bundle.getString("_mi");
        FA=bundle.getString("_fa");
        SOL=bundle.getString("_sol");
        LA=bundle.getString("_la");
        SI=bundle.getString("_si");

        bdo.setText(DO);
        bre.setText(RE);
        bmi.setText(MI);
        bfa.setText(FA);
        bsol.setText(SOL);
        bla.setText(LA);
        bsi.setText(SI);
        bdo2.setText(DO);

        scoreKeyComboBox.removeAllItems();
        scoreKeyComboBox.addItem(bundle.getString("_trebleclef"));
        scoreKeyComboBox.addItem(bundle.getString("_bassclef"));

        scoreGameTypeComboBox.removeAllItems();
        scoreGameTypeComboBox.addItem(bundle.getString("_normalgame"));

        scoreNotesComboBox.removeAllItems();
        scoreNotesComboBox.addItem("9 "+bundle.getString("_menuNotes"));
        scoreNotesComboBox.addItem("15 "+bundle.getString("_menuNotes"));
        scoreNotesComboBox.addItem(bundle.getString("_customnotes"));

        scoreAlterationsComboBox.removeAllItems();
        scoreAlterationsComboBox.addItem(bundle.getString("_nosharpflat"));
        scoreAlterationsComboBox.addItem("1 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("2 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("3 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("4 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("5 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("6 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("7 "+bundle.getString("_sharp"));
        scoreAlterationsComboBox.addItem("1 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("2 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("3 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("4 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("5 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("6 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem("7 "+bundle.getString("_flat"));
        scoreAlterationsComboBox.addItem(bundle.getString("_random"));

        scorewholeCheckBox.setText(bundle.getString("_wholenote"));
        scorehalfCheckBox.setText(bundle.getString("_halfnote"));
        scoredottedhalfCheckBox.setText(bundle.getString("_dottedhalfnote"));
        scorequarterCheckBox.setText(bundle.getString("_quarternote"));
        scoreeighthCheckBox.setText(bundle.getString("_eighthnote"));
        scorerestCheckBox.setText(bundle.getString("_rest"));
        scoreTripletCheckBox.setText(bundle.getString("_triplet"));
        scoreMetronomeCheckBox.setText(bundle.getString("_menuMetronom"));
        scoreMetronomeShowCheckBox.setText(bundle.getString("_menuShowMetronom"));
    }

    // DRAW METHODS

    // KEYS

    private void drawKeys(Graphics g) {
    	
        if (selectedGame==NOTEREADING) {
            if (noteLevel.isCurrentKeyTreble()) {
            	g.setFont(MusiSync.deriveFont(70f));
            	g.drawString("G", notemargin, scoreYpos + 42);
            } else if (noteLevel.isCurrentKeyBass()) {
            	g.setFont(MusiSync.deriveFont(60f));
                g.drawString("?", notemargin, scoreYpos + 40);
            } else if (noteLevel.isCurrentKeyBoth()) {
            	g.setFont(MusiSync.deriveFont(70f));
            	g.drawString("G", notemargin, scoreYpos+42);
            	g.setFont(MusiSync.deriveFont(60f));
            	g.drawString("?", notemargin, scoreYpos+130);
            }
        } else if (selectedGame==RHYTHMREADING ) {
            for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
            //	g.setFont(MusiSync.deriveFont(70f));
            	//g.drawString("G", windowMargin, scoreYpos+42+rowNum*rowsDistance);
            }
        } else if (selectedGame==SCOREREADING ) {
        	if (scoreLevel.isCurrentKeyTreble())
            for (int rowNum=0; rowNum < numberOfRows; rowNum++) {
            	g.setFont(MusiSync.deriveFont(70f));
            	g.drawString("G", windowMargin, scoreYpos+42+rowNum*rowsDistance);
            }
        	else if (scoreLevel.isCurrentKeyBass())
                for (int rowNum=0; rowNum < numberOfRows; rowNum++) {
                	g.setFont(MusiSync.deriveFont(60f));
                	g.drawString("?", windowMargin, scoreYpos+40+rowNum*rowsDistance);
                }
        }
    }
    
    private void drawTimeSignature(Graphics g) {
    	g.setFont(MusiSync.deriveFont(58f));
    	
    	  int tmpnum = 4; int tmpden = 4; int tmpdiv = 1;
          if (selectedGame == RHYTHMREADING ) {
        	  tmpnum = rhythmLevel.getTimeSignNumerator();
        	  tmpden = rhythmLevel.getTimeSignDenominator();
        	  tmpdiv = rhythmLevel.getTimeDivision();
          }
          else if (selectedGame == SCOREREADING ) {
        	  tmpnum = scoreLevel.getTimeSignNumerator();
        	  tmpden = scoreLevel.getTimeSignDenominator();
        	  tmpdiv = scoreLevel.getTimeDivision();
          }
          
    	for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
    		String t = "";
    		

	    		if (tmpnum == 4 && tmpden == 4)
	    			t = "$";
	    		if (tmpnum == 3 && tmpden == 4)
	    			t = "#";
	    		if (tmpnum == 2 && tmpden == 4)
	    			t = "@";
	    		if (tmpnum == 6 && tmpden == 8)
	    			t = "P";
    	
    		
    		g.drawString(t, windowMargin + keyWidth + alterationWidth, scoreYpos+41+rowNum*rowsDistance);
    	}
    }

    // ROWS

    private void drawInlineGame(Graphics g) {
        Dimension size=getSize();
        g.setColor(Color.black);
        int yd;

        for (yd=scoreYpos; yd<=scoreYpos+40; yd+=10) { //  1ere ligne ï¿½ 144;   derniï¿½re ï¿½ 176
            g.drawLine(notemargin, yd, size.width-notemargin, yd);
        }

        if (noteLevel.isCurrentKeyBoth()) {  // dessine la deuxiï¿½me portï¿½e 72 points en dessous
            for (yd=scoreYpos+90; yd<=scoreYpos+130; yd+=10) {  //  1ere ligne ï¿½ 196;   derniï¿½re ï¿½ 228
                g.drawLine(notemargin, yd, size.width-notemargin, yd);
            }
        }
        if (noteLevel.isInlinegame()) {
            g.setColor(Color.red);
            g.drawLine(notemargin+98, scoreYpos-30, notemargin+98, scoreYpos+70);
            if (noteLevel.isCurrentKeyBoth()) {
                g.drawLine(notemargin+98, scoreYpos+20, notemargin+98, scoreYpos+160);
            }
            g.setColor(Color.black);
        }
    }

    private void drawScore(Graphics g) {
        Dimension size=getSize();
        g.setColor(Color.black);
        alterationWidth = scoreLevel.getCurrentTonality().getAlterationsNumber() * 12;
        int tmpnum = 4;
        if (selectedGame == RHYTHMREADING ) tmpnum = rhythmLevel.getTimeSignNumerator();
        else if (selectedGame == SCOREREADING ) tmpnum = scoreLevel.getTimeSignNumerator();
        
        int scoreLineWidth = keyWidth + alterationWidth + timeSignWidth;
        firstNoteXPos = windowMargin + keyWidth + alterationWidth + timeSignWidth + notesShift;
        numberOfMeasures = (size.width - (windowMargin * 2) - scoreLineWidth) / (tmpnum * noteDistance);
        numberOfRows = (size.height - scoreYpos - 50) / rowsDistance; // 50 = window bottom margin
        int yPos = scoreYpos;
        int vXPos = windowMargin + scoreLineWidth + (tmpnum * noteDistance);

        scoreLineWidth += windowMargin + (numberOfMeasures * (tmpnum * noteDistance));
        
        for (int r = 0; r < numberOfRows; r++) {
        	// draw vertical separators first
        	for (int v = 0; v < numberOfMeasures; v++)
        		g.drawLine(vXPos + v * (tmpnum * noteDistance), yPos, vXPos + v * (tmpnum * noteDistance), yPos+40);
        	// draw the score 5 rows 
        	if (selectedGame == SCOREREADING ) {
	        	for (int l = 0;l < 5;l++,yPos+=10) {
	        		g.drawLine(windowMargin, yPos, scoreLineWidth, yPos);
	        	}
	        	
        	}
        	else if (selectedGame == RHYTHMREADING ) { //only one line
        		g.drawLine(windowMargin, yPos+20, scoreLineWidth, yPos+20);
        		yPos+=(rowsDistance - 50);
        	}
        	yPos+=(rowsDistance - 50);
        
        }
    }
    
    // NOTE

    private boolean isSameNote(int p1, int p2) {
        // compare two pitch when using computer keyboard
        return p1+(((Number)transpositionSpinner.getValue()).intValue())==p2;
    }

    /**
     * To choice a random hight for a note according to base note
     *
     * @param nbupper1 and nbunder1 are tne number of notes upper or under the base note for alone Key
     * @param nbupper2 and nbunder2 are tne number of notes upper or under the base note for bass Key when here are both Keys
     */
    private int setNoteHeight(int nbupper1, int nbunder1, int nbupper2, int nbunder2) {
        int i;
        int h=0;
        double tmp;

        // FIRST CASE alone Key

        if (noteLevel.isCurrentKeyTreble() || noteLevel.isCurrentKeyBass()) {

            tmp=Math.random();
            if (tmp<0.5) {
                i=(int)Math.round((Math.random()*nbupper1));
            }   // between 0 and upper note 
            else {
                i=-(int)Math.round((Math.random()*nbunder1));
            }
                       // negative number berween under note and 0 
            if (noteLevel.isCurrentKeyTreble()) {
                h=(scoreYpos+noteLevel.getBasetreble())-(i*5); // 20 for trebble key
            } else {
                h=(scoreYpos+noteLevel.getBasebass())-(i*5); // 4 far bass key
            }
            //   scoreYpos+20  =  Sol en clï¿½ de sol  128 = Fa en clï¿½ de fa
        }

        // SECOND CASE double Key
        else if (noteLevel.isCurrentKeyBoth()) {
            int dessousbase;
            if (nbupper2<0) {
                dessousbase=nbupper2;
            } else {
                dessousbase=0;
            }

            double tmpcle=Math.random();
            if (tmpcle<0.5) { // trebble key
                tmp=Math.random();
                if (tmp<0.5) {
                    i=(int)Math.round((Math.random()*nbupper1));
                }             // between 0 and upper note 
                else {
                    i=-(int)Math.round((Math.random()*nbunder1));
                }
             // negative number berween under note and 0 
                h=scoreYpos+noteLevel.getBasetreble()-(i*5);     //   scoreYpos+20  =  Sol en clï¿½ de sol
            } else {       // clï¿½ de fa
                tmp=Math.random();
                if (tmp<0.5) {
                    i=(int)Math.round((Math.random()*nbupper2)+dessousbase);

                } else {
                    i=-(int)Math.round((Math.random()*nbunder2))+dessousbase;
                }

                h=scoreYpos+noteLevel.getBasebass()+90-(i*5);
            }
        }
        return h;
    }
    
    
    private void rhythmKeyReleased(int pitch){
    	
    	
    	  if (keyboardsoundCheckBox.isSelected()){
    		  currentChannel.stopnote(true,pitch);
    		  }
    	  
    	  float rhythmCursorXposcorrected;
		  if (cursorstart)
			  rhythmCursorXposcorrected = rhythmCursorXStartPos + ((System.currentTimeMillis()-timestart-latency)*noteDistance)/(60000/tempo);
		  else 
			  rhythmCursorXposcorrected = rhythmCursorXpos;
		  
		  System.out.println ("rhythmCursorXpos" + rhythmCursorXposcorrected);
		  if (cursorstart) {
		// key should be released at tne end of the rhythm
			  if ((rhythmIndex >= 0) && (rhythmIndex < rhythms.size()) 
				  && (!rhythms.get(rhythmIndex).isSilence()) && (rhythms.get(rhythmIndex).duration != 0)
				  && ((int)rhythmCursorXposcorrected < rhythms.get(rhythmIndex).getPosition() + 8/rhythms.get(rhythmIndex).duration * 27 - precision) 
				  && ((int)rhythmCursorXposcorrected > rhythms.get(rhythmIndex).getPosition() + precision) 
				 ) {
				 
				  answers.add(new RhythmAnswer((int)rhythmCursorXposcorrected, rhythmAnswerScoreYpos -15 , true, 2 ));
		  }
		//key should be released just before a silent  
			  if ((rhythmIndex >= 0) && (rhythms.get(rhythmIndex).isSilence()) 
					  && (rhythmIndex-1 >=0)
					  && (!rhythms.get(rhythmIndex-1).isSilence())	
					  && ((int)rhythmCursorXposcorrected > rhythms.get(rhythmIndex).getPosition() + precision) 
					 && ((int)rhythmCursorXposcorrected < rhythms.get(rhythmIndex).getPosition() + 8/rhythms.get(rhythmIndex).duration * 27 - precision) 
					 	 
					  ) {
				  
				  answers.add(new RhythmAnswer((int)rhythmCursorXposcorrected, rhythmAnswerScoreYpos -15 , true, 2 ));
			  }
			  
		  }
    	  
    }
    
    
    private void rhythmKeyPressed(int pitch){
    	  int result = 0;
    	  boolean goodnote = false;
		  
		  if (keyboardsoundCheckBox.isSelected()){
		    //currentChannel.stopnotes();
		    currentChannel.playNote(true, pitch, 2000);
		  }
		  //  System.out.println("time sound" + System.currentTimeMillis());
		  float rhythmCursorXposcorrected;

		  if (cursorstart)
			  rhythmCursorXposcorrected = rhythmCursorXStartPos + ((System.currentTimeMillis()-timestart-latency)*noteDistance)/(60000/tempo);
		  else 
			  rhythmCursorXposcorrected = rhythmCursorXpos;

		  System.out.println ("rhythmCursorXpos" + rhythmCursorXposcorrected);
		  
		  if (((rhythmIndex >= 0) && ((int)rhythmCursorXposcorrected < rhythms.get(rhythmIndex).getPosition() + precision) 
				  && ((int)rhythmCursorXposcorrected > rhythms.get(rhythmIndex).getPosition() - precision) && !rhythms.get(rhythmIndex).isSilence()))
			  if (pitch == rhythms.get(rhythmIndex).getPitch()) {
				  result = 0;
				  goodnote = true;
			  }
			  else {
				  result = 0;
				  goodnote = false;
			  }
				 //to resolve problem with eight on fast tempo 
		  else  if (((rhythmIndex-1 >= 0) && ((int)rhythmCursorXposcorrected < rhythms.get(rhythmIndex-1).getPosition() + precision) 
	    				  && ((int)rhythmCursorXposcorrected > rhythms.get(rhythmIndex-1).getPosition() - precision) && !rhythms.get(rhythmIndex-1).isSilence()))
			  if (pitch == rhythms.get(rhythmIndex).getPitch()) {
				  result = 0;
				  goodnote = true;
			  }
			  else {
				  result = 0;
				  goodnote = false;
			  }
		  else 
			  if (rhythmIndex >= 0 && pitch == rhythms.get(rhythmIndex).getPitch()) {
				  result = 1;
				  goodnote = true;
			  }
			  else {
				  result = 1;
				  goodnote = false;
			  }
				  
		  answers.add(new RhythmAnswer((int)rhythmCursorXposcorrected,  rhythmAnswerScoreYpos -15, goodnote, result ));
    }

    private Interval intervalchoice() {
        int i=1;
        if (noteLevel.isSecondInterval()) {
            i=1;
        } else if (noteLevel.isThirdInterval()) {
            i=2;
        } else if (noteLevel.isFourthInterval()) {
            i=3;
        } else if (noteLevel.isFifthInterval()) {
            i=4;
        } else if (noteLevel.isSixthInterval()) {
            i=5;
        } else if (noteLevel.isSeventhInterval()) {
            i=6;
        } else if (noteLevel.isOctaveInterval()) {
            i=7;
        } else if (noteLevel.isRandomInterval()) {
            i=(int)Math.round((Math.random()*6))+1;
        }

        int h;
        if (noteLevel.isCurrentKeyBoth()) {
            h=setNoteHeight(13-i, 5, 6-i, 10);
            while (h==precedente) {
                h=setNoteHeight(13-i, 5, 6-i, 10);
            }

        } else {
            h=setNoteHeight(13-i, 8, 13-i, 8);
            while (h==precedente) {
                h=setNoteHeight(13-i, 8, 13-i, 8);
            }

        }

        Note n1=new Note("", "", h, notemargin+98, 0);
        n1.updateNote(noteLevel, scoreYpos, bundle);
        n1.updateAccidental(noteLevel, bundle);

        Note n2=new Note("", "", h-i*5, notemargin+98, 0);
        n2.updateNote(noteLevel, scoreYpos, bundle);
        n2.updateAccidental(noteLevel, bundle);

        String nom="";
        if (n2.getPitch()-n1.getPitch()==0 && i==1) {
            nom=bundle.getString("_seconddim");
        } else if (n2.getPitch()-n1.getPitch()==1 && i==1) {
            nom=bundle.getString("_secondmin");
        } else if (n2.getPitch()-n1.getPitch()==2 && i==1) {
            nom=bundle.getString("_secondmaj");
        } else if (n2.getPitch()-n1.getPitch()==3 && i==1) {
            nom=bundle.getString("_secondaug");
        } else if (n2.getPitch()-n1.getPitch()==2 && i==2) {
            nom=bundle.getString("_thirddim");
        } else if (n2.getPitch()-n1.getPitch()==3 && i==2) {
            nom=bundle.getString("_thirdmin");
        } else if (n2.getPitch()-n1.getPitch()==4 && i==2) {
            nom=bundle.getString("_thirdmaj");
        } else if (n2.getPitch()-n1.getPitch()==5 && i==2) {
            nom=bundle.getString("_thirdaug");
        } else if (n2.getPitch()-n1.getPitch()==4 && i==3) {
            nom=bundle.getString("_fourthdim");
        } else if (n2.getPitch()-n1.getPitch()==5 && i==3) {
            nom=bundle.getString("_fourthper");
        } else if (n2.getPitch()-n1.getPitch()==6 && i==3) {
            nom=bundle.getString("_fourthaug");
        } else if (n2.getPitch()-n1.getPitch()==6 && i==4) {
            nom=bundle.getString("_fifthdim");
        } else if (n2.getPitch()-n1.getPitch()==7 && i==4) {
            nom=bundle.getString("_fifthper");
        } else if (n2.getPitch()-n1.getPitch()==8 && i==4) {
            nom=bundle.getString("_fifthaug");
        } else if (n2.getPitch()-n1.getPitch()==7 && i==5) {
            nom=bundle.getString("_sixthdim");
        } else if (n2.getPitch()-n1.getPitch()==8 && i==5) {
            nom=bundle.getString("_sixthmin");
        } else if (n2.getPitch()-n1.getPitch()==9 && i==5) {
            nom=bundle.getString("_sixthmaj");
        } else if (n2.getPitch()-n1.getPitch()==10 && i==5) {
            nom=bundle.getString("_sixthaug");
        } else if (n2.getPitch()-n1.getPitch()==9 && i==6) {
            nom=bundle.getString("_seventhdim");
        } else if (n2.getPitch()-n1.getPitch()==10 && i==6) {
            nom=bundle.getString("_seventhmin");
        } else if (n2.getPitch()-n1.getPitch()==11 && i==6) {
            nom=bundle.getString("_seventhmaj");
        } else if (n2.getPitch()-n1.getPitch()==12 && i==6) {
            nom=bundle.getString("_seventhaug");//inusitï¿½e
        } else if (n2.getPitch()-n1.getPitch()==11 && i==7) {
            nom=bundle.getString("_octavedim");
        } else if (n2.getPitch()-n1.getPitch()==12 && i==7) {
            nom=bundle.getString("_octaveper");
        } else if (n2.getPitch()-n1.getPitch()==13 && i==7) {
            nom=bundle.getString("_octaveaug");
        }

        Interval inter=new Interval(n1, n2, nom);
        precedente=n1.getHeight();

        return inter;
    }

    private void newinterval() {
    	stopSound();
        icourant.copy(intervalchoice());
        if (noteLevel.isNormalgame() || noteLevel.isLearninggame()) {
            posnote=0;
            ncourante=icourant.getNote(posnote);
            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }
        } else if (noteLevel.isInlinegame()) {
            if (position<ligne.length-1) {
                position+=1;
                icourant.copy(ligneint[position]);

                posnote=0;
                //acourant.convertir(clecourante,typeaccord);
                ncourante=icourant.getNote(posnote);
                if (soundOnCheckBox.isSelected()) {
                    synthNote(ncourante.getPitch(), 80, noteDuration);
                }


            }
        }
    }

    private Chord chordchoice() {
        int h;
        Note n1=new Note("", "", 0, 0, 0);
        Note n2=new Note("", "", 0, 0, 0);
        Note n3=new Note("", "", 0, 0, 0);

        if (noteLevel.isCurrentKeyBoth()) {
            h=setNoteHeight(6, 5, -2, 10);
            while (h==precedente) {
                h=setNoteHeight(6, 5, -2, 10);
            }

        } else {
            h=setNoteHeight(6, 8, 6, 8);
            while (h==precedente) {
                h=setNoteHeight(6, 8, 6, 8);
            }

        }

        String minmaj="";
        String salt="";
        boolean ok=false;
        while (!ok) {

            n1=new Note("", "", h, notemargin+98, 0);
            n1.updateNote(noteLevel, scoreYpos, bundle);
            n1.updateAccidental(noteLevel, bundle);

            n2=new Note("", "", h-2*5, notemargin+98, 0);
            n2.updateNote(noteLevel, scoreYpos, bundle);
            n2.updateAccidentalInChord(noteLevel.getCurrentTonality(), n1.getPitch(), 2, bundle); //deuxieme note

            n3=new Note("", "", h-4*5, notemargin+98, 0);
            n3.updateNote(noteLevel, scoreYpos, bundle);
            n3.updateAccidentalInChord(noteLevel.getCurrentTonality(), n1.getPitch(), 3, bundle); //troisieme note

            if (n2.getPitch()-n1.getPitch()==3 && n3.getPitch()-n1.getPitch()==7) {
                minmaj=mineur;
                ok=true;
            } else if (n2.getPitch()-n1.getPitch()==3 && n3.getPitch()-n1.getPitch()==6) {
                minmaj="dim";
                ok=true;
            } else if (n2.getPitch()-n1.getPitch()==4 && n3.getPitch()-n1.getPitch()==7) {
                minmaj=majeur;
                ok=true;
            } else if (n2.getPitch()-n1.getPitch()==4 && n3.getPitch()-n1.getPitch()==8) {
                minmaj="aug";
                ok=true;
            } else {
                ok=false;
            }

            if (n1.getAlteration()=="n") {
                salt="";
            } else {
                salt=n1.getAlteration();
            }
            // if (!ok) System.out.println("faux"+n1.getNom() +salt);

        }
        Chord a=new Chord(n1, n2, n3, n1.getNom()+salt+" "+minmaj, 0);
        precedente=n1.getHeight();
        return a;

    }

    private void resetButtonColor() {

        ColorUIResource def=new ColorUIResource(238, 238, 238);
        bdo.setBackground(def);
        bre.setBackground(def);
        bmi.setBackground(def);
        bfa.setBackground(def);
        bsol.setBackground(def);
        bla.setBackground(def);
        bsi.setBackground(def);
        bdiese.setBackground(def);
        bbemol2.setBackground(def);

    }

    private void applyButtonColor() {

        resetButtonColor();

        Color red=new Color(242, 179, 112);
        if (ncourante.getNom().equals(bdo.getText())) {
            bdo.setBackground(red);
        } else if (ncourante.getNom().equals(bre.getText())) {
            bre.setBackground(red);
        } else if (ncourante.getNom().equals(bmi.getText())) {
            bmi.setBackground(red);
        } else if (ncourante.getNom().equals(bfa.getText())) {
            bfa.setBackground(red);
        } else if (ncourante.getNom().equals(bsol.getText())) {
            bsol.setBackground(red);
        } else if (ncourante.getNom().equals(bla.getText())) {
            bla.setBackground(red);
        } else if (ncourante.getNom().equals(bsi.getText())) {
            bsi.setBackground(red);
        }

        if (ncourante.getAlteration().equals(bdiese.getText())) {
            bdiese.setBackground(red);
        } else if (ncourante.getAlteration().equals(bbemol.getText())) {
            bbemol2.setBackground(red);
        }

    }

    private void drawChord(Chord a, Graphics g, boolean accordcourant) {
        Dimension d=getSize();

        if (a.getNote(posnote).getX()<d.width-notemargin &&
            a.getNote(posnote).getX()>=notemargin+98 && parti) {
            // NOTE DANS LIMITES
            a.paint(posnote, noteLevel, g, MusiSync, accordcourant, this,
                scoreYpos, bundle);
            //g.drawString("Renv" + a.renvst,100,100);
        } else {
            if (noteLevel.isNormalgame()) {
                currentScore.addPoints(-20);

                if (currentScore.isLost()) {
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    stopSound();
                    showResult();
                }

                if (parti) newChord();
            } else if (noteLevel.isLearninggame()) {
                newChord();
                resetButtonColor();
            } else if (noteLevel.isInlinegame() && parti) {
                if (noteLevel.isChordsgame() &&
                    ligneacc[position].getNote(0).getX()<notemargin+98) {
                    // If the current note (except the last) touch the limit
                    currentScore.setPoints(0);
                    currentScore.setLost();
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    stopSound();
                    showResult();
                }
            }
        }
    }

    private void synthNote(int nNoteNumber, int nVelocity, int nDuration) {

        currentChannel.playNote(!midierror, nNoteNumber);

    }

    private void newnote() {

        if ((noteLevel.isNormalgame() || noteLevel.isLearninggame()) & parti) {
            notecounter++;
            if (precedente!=0 & soundOnCheckBox.isSelected()) {
                stopSound();
            }
            ncourante.init();
            
            if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame()) {
            	//choosing note with height to do change to choose note with pitch
            ncourante.setHeight(setNoteHeight(noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder(), noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder()));
            while (ncourante.getHeight()==precedente) {
                ncourante.setHeight(setNoteHeight(noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder(), noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder()));
            }
            ncourante.updateNote(noteLevel, scoreYpos, bundle);

            ncourante.updateAccidental(noteLevel, bundle);
            precedente=ncourante.getHeight();
            }
            
            else if (noteLevel.isCustomNotesgame()){
            	// choosing note with pitch
            	
            	ncourante.setPitch(noteLevel.getRandomPitch());
            	  ncourante.updateNotePitch(noteLevel, scoreYpos, bundle);
            	  precedente=ncourante.getHeight();
            }
            		
            ncourante.setX(notemargin+98);
             System.out.println(ncourante.getNom());
            System.out.println(ncourante.getHeight());
			System.out.println(ncourante.getPitch());
            //if (soundOnCheckBox.isSelected()) sons[indiceson(ncourante.getHeight())].play();

            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }

        } else if (noteLevel.isInlinegame()) {
            //sons[indiceson(ncourante.getHeight())].stop();
        	if (precedente!=0 & soundOnCheckBox.isSelected()) {
                stopSound();
            }
            if (position<ligne.length-1) {
                position+=1;
                ncourante.copy(ligne[position]);
            }
            //if (soundOnCheckBox.isSelected()) sons[indiceson(ncourante.getHeight())].play();
            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }
        }
    }

    private void stopSound() {
        //if (soundOnCheckBox.isSelected())
        //for (int i=0;i<37;i=i+1){
        //sons[i].stop();
        currentChannel.stopnotes();
     // System.out.println ("Stop sound");
        //}

    }

    private void drawNote(Note note, Graphics g, Font f, Color couleur) {
        Dimension size=getSize();

        g.setColor(couleur);
        if (note.getX()<size.width-notemargin && note.getX()>=notemargin+98 && parti) { // NOTE DANS LIMITES
            if (noteLevel.isAccidentalsgame()|| noteLevel.isCustomNotesgame()) {
                note.paint(noteLevel, g, f, 9, 0, scoreYpos, this, couleur, bundle);
            } else {
                note.paint(noteLevel, g, f, 0, 0, scoreYpos, this, couleur, bundle);
            }
        } else {
            if (noteLevel.isNormalgame()) {
                currentScore.addPoints(-20);
                if (currentScore.isLost()) {
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    showResult();

                }
                newnote();

            } else if (noteLevel.isLearninggame()) {
                newnote();
                resetButtonColor();
            } else if (noteLevel.isInlinegame() && parti) {
                if (ligne[position].getX()<notemargin+98) { // Si la note courant (sauf la derniï¿½re)dï¿½passe la limite ici marge +25
                    currentScore.setPoints(0);
                    currentScore.setLost();
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    showResult();

                }
            }
        }
    }

    //################################################################
    // RHYTHMS READING

    private void nextRythm() {
    	 System.out.println ("rhytm xpos: " + rhythms.get(rhythmIndex).getPosition() + 
    			 " pitch: " + rhythms.get(rhythmIndex).getPitch() + 
    			 " index: " + rhythmIndex);
 		 
        if (rhythms.get(rhythmIndex).getDuration()!=0) {
            if (rhythmIndex<rhythms.size()-1) {
                rhythmIndex++;
                repaint();
                /* if (soundOnCheckBox.getState() & !ligne[position].silence) Synthnote(71,80,durationrhythme);*/
            }
           
        }
    }

    private void createMetronome() {

        final int TEXT=0x01;
        int nbpulse;

        try {
            //ShortMessage sm=new ShortMessage();
            //sm.setMessage(ShortMessage.PROGRAM_CHANGE, 1, 115, 0);
            //metronome.add(new MidiEvent(sm, 0));
        	
        	  int tmpnum = 4; int tmpden = 4; int tmpdiv = 1;
              if (selectedGame == RHYTHMREADING ) {
            	  tmpnum = rhythmLevel.getTimeSignNumerator();
            	  tmpden = rhythmLevel.getTimeSignDenominator();
            	  tmpdiv = rhythmLevel.getTimeDivision();
              }
              else if (selectedGame == SCOREREADING ) {
            	  tmpnum = scoreLevel.getTimeSignNumerator();
            	  tmpden = scoreLevel.getTimeSignDenominator();
            	  tmpdiv = scoreLevel.getTimeDivision();
              }
              
              
              
        	System.out.println("[createMetronome] timeSignNumerator =  " + tmpnum + ", timeSignDenominator = " + tmpden);

            String textd="depart";
            addEvent(metronome, TEXT, textd.getBytes(), (int)(tmpnum/tmpdiv)*ppq);
            
            String textdt="departthread"; //one beat before rhythms
            addEvent(metronome, TEXT, textdt.getBytes(), (int)((tmpnum/tmpdiv)-1)*ppq);

            if ((selectedGame == RHYTHMREADING && rhythmLevel.getMetronome()) || 
            	(selectedGame==SCOREREADING && scoreLevel.getMetronome())) {
            		nbpulse = (tmpnum * numberOfMeasures * numberOfRows) + tmpnum;
            }
            else nbpulse = tmpnum; //only few first to indicate pulse
            
            nbpulse = nbpulse / tmpdiv;

            for (int i=0; i < nbpulse; i++) {
          		ShortMessage mess=new ShortMessage();
           		ShortMessage mess2=new ShortMessage();
           		mess.setMessage(ShortMessage.NOTE_ON, 9, 76, 40); // can use 37 as well, but it has reverb

           		metronome.add(new MidiEvent(mess, i*ppq));
           		mess2.setMessage(ShortMessage.NOTE_OFF, 9, 77, 0);
           		metronome.add(new MidiEvent(mess2, (i*ppq)+1));
            		
           		if ((selectedGame == RHYTHMREADING && rhythmLevel.getMetronomeBeats() && i > ((tmpnum / tmpdiv) - 1)) 
           				||	(selectedGame == SCOREREADING && scoreLevel.getMetronomeBeats()) && i > ((tmpnum / tmpdiv) - 1))
           				 {
            			//System.out.println("adding metronome beat : "+i + "tmpnum : " + tmpnum + "tmpdiv : "+tmpdiv);
            			String textb="beat";
            			addEvent(metronome, TEXT, textb.getBytes(), (int)i*ppq);
           		}
            }

        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void addEvent(Track track, int type, byte[] data, long tick) {
        MetaMessage message=new MetaMessage();
        try {
            message.setMessage(type, data, data.length);
            MidiEvent event=new MidiEvent(message, tick);
            track.add(event);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    private static MidiEvent createNoteOnEvent(int nKey, int velocity, long lTick) {
        return createNoteEvent(ShortMessage.NOTE_ON, nKey, velocity, lTick);
    }

    private static MidiEvent createNoteOffEvent(int nKey, long lTick) {
        return createNoteEvent(ShortMessage.NOTE_OFF, nKey, 0, lTick);
    }

    private static MidiEvent createNoteEvent(int nCommand, int nKey,
        int nVelocity, long lTick) {
        ShortMessage message=new ShortMessage();
        try {
            message.setMessage(nCommand,
                0, // always on channel 1
                nKey,
                nVelocity);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new MidiEvent(message, lTick);
    }

    
    
    private int addRhythm(double duration, int pitch, boolean stemup, int currentTick, int row, int newXPos) {
        int tick = currentTick;
        int velocity = 71;
        boolean silence = false;

        final int TEXT=0x01;
        String text="off";
        int tmpdiv = 1;
        int tmpnum = 4;
        int tmpden = 4;
        
        if (selectedGame == RHYTHMREADING) {
        	silence = rhythmLevel.getSilence();
        	tmpdiv =  rhythmLevel.getTimeDivision();
        	tmpnum =  rhythmLevel.getTimeSignNumerator();
        	tmpden =  rhythmLevel.getTimeSignDenominator();
        	
        }
        else if (selectedGame == SCOREREADING) {
        	silence = scoreLevel.getSilence();
        	tmpdiv =  scoreLevel.getTimeDivision();
        	tmpnum =  scoreLevel.getTimeSignNumerator();
        	tmpden =  scoreLevel.getTimeSignDenominator();
        }
        
        if (duration == 0.333) // do not handle pauses into triplets for now 
        	silence = false;

        System.out.println("[addRhythm] pitch: " + pitch + "duration: " + duration + "stemup " + stemup);

        double tmpsilence=Math.random();
        if (!silence || (silence && tmpsilence<0.85) || (duration == 3 && tmpnum !=3 ) ) {

          rhythms.add(new Rhythm(duration, newXPos, pitch,  row, stemup, false, false, 0));

            track.add(createNoteOnEvent(pitch, velocity, tick));
            mutetrack.add(createNoteOnEvent(pitch, 0, tick));
            tick+=(int)((duration*tmpdiv)*ppq);
            addEvent(track, TEXT, text.getBytes(), tick);
            addEvent(mutetrack, TEXT, text.getBytes(), tick);
            track.add(createNoteOffEvent(pitch, tick));
            mutetrack.add(createNoteOffEvent(pitch, tick));

           

        } else { // silence
          	rhythms.add(new Rhythm(duration, newXPos, pitch, row, false, false, true, 0));

            track.add(createNoteOffEvent(pitch, tick));
            mutetrack.add(createNoteOffEvent(pitch, tick));

            tick+=(int)((duration*tmpdiv)*ppq);
            addEvent(track, TEXT, text.getBytes(), tick);
            addEvent(mutetrack, TEXT, text.getBytes(), tick);
        }
       
        return tick;
    }
    
    private void setTripletValue(int val) {
    	rhythms.get(rhythms.size() - 1).setTrpletValue(val);
    }

    private boolean isBeginMeasure(int i) {
        double d=0;
        int id=0;
        for (int j=0; j<i; j++) {
         //   d+=4.0/rhythms.get(j).getDuration();
        	d+=rhythms.get(j).getDuration();
        }
        id = (int) Math.round(d); // we should round because of 0.33 triplet need to be fix
        

        int tmpnum = 4; int tmpden = 4; int tmpdiv = 1;
        if (selectedGame == RHYTHMREADING ) {
      	  tmpnum = rhythmLevel.getTimeSignNumerator();
      	  tmpden = rhythmLevel.getTimeSignDenominator();
      	  tmpdiv = rhythmLevel.getTimeDivision();
        }
        else if (selectedGame == SCOREREADING ) {
      	  tmpnum = scoreLevel.getTimeSignNumerator();
      	  tmpden = scoreLevel.getTimeSignDenominator();
      	  tmpdiv = scoreLevel.getTimeDivision();
        }
        
        boolean reponse=false;
        for (int k=1; k<tmpnum * 2; k++) {
            if (id==tmpnum*k) {
                reponse=true;
            }
        }
        return reponse;
    }
    
  

    private void createSequence() {
    	repaint();
    	  int tmpnum = 4; int tmpden = 4; int tmpdiv = 1;
    	  int currentTick=0;
        int rowCount=0; // measures counter
        double tpsmes=0; // number of quarters 
        int currentXPos = windowMargin + keyWidth + alterationWidth + timeSignWidth + notesShift;
        int pitch;
        boolean wholeNote = false, halfNote = false, dottedhalfNote = false, quarterNote= false, eighthNote = false, triplet = false;
        boolean stemup = true;
        Dimension size=getSize();

        	
        if (selectedGame == RHYTHMREADING) 
        {
        	wholeNote = rhythmLevel.getWholeNote();
        	halfNote = rhythmLevel.getHalfNote();
        	dottedhalfNote = rhythmLevel.getDottedHalfNote();
        	quarterNote = rhythmLevel.getQuarterNote();
        	eighthNote = rhythmLevel.getEighthNote();
        	triplet = rhythmLevel.getTriplet();
        	 tmpnum = rhythmLevel.getTimeSignNumerator();
       	  tmpden = rhythmLevel.getTimeSignDenominator();
       	  tmpdiv = rhythmLevel.getTimeDivision();

        } 
        else if  (selectedGame == SCOREREADING) 
        {
        	wholeNote = scoreLevel.getWholeNote();
        	halfNote = scoreLevel.getHalfNote();
        	dottedhalfNote = scoreLevel.getDottedHalfNote();
        	quarterNote = scoreLevel.getQuarterNote();
        	eighthNote = scoreLevel.getEighthNote();
        	triplet = scoreLevel.getTriplet();
        	  tmpnum = scoreLevel.getTimeSignNumerator();
        	  tmpden = scoreLevel.getTimeSignDenominator();
        	  tmpdiv = scoreLevel.getTimeDivision();

        }
        
         currentTick=(int)((tmpnum/tmpdiv)*ppq);

        // INITIALIZE Sequence and tracks
        try {
            sequence=new Sequence(Sequence.PPQ, ppq);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }

        mutetrack=sequence.createTrack();
        track=sequence.createTrack();
        metronome=sequence.createTrack();

        rhythms.clear();

        createMetronome();

        try {
            ShortMessage sm=new ShortMessage();
            sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrumentsComboBox.getSelectedIndex(), 0);

            track.add(new MidiEvent(sm, 0));

        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            System.exit(1);
        }

        updateTonality(); //when selected random tonality

        if (selectedGame == SCOREREADING && !scoreLevel.isCustomNotes()) {
        	updateTonality(); //when selected random tonality       
        	scoreLevel.initPitcheslist( scoreLevel.getNbnotes());
        }

        for (int r = 1; r <= (numberOfMeasures * numberOfRows); r++) 
        { // creates all the measures
            while (tpsmes != tmpnum) 
            {
            	//System.out.println("tpsmes : " + tpsmes);
                double tmp=Math.random();
                if (selectedGame == RHYTHMREADING) {
                	pitch = 71;
                	stemup = true;
                }
                else {
                	pitch = scoreLevel.getRandomPitch();
                	if (scoreLevel.isCurrentKeyTreble() && pitch >= 71) stemup = false; //SI
                	else if (scoreLevel.isCurrentKeyTreble() && pitch < 71) stemup = true;
                	else if (scoreLevel.isCurrentKeyBass() && pitch >= 50) stemup = false; //RE
                	else if (scoreLevel.isCurrentKeyBass() && pitch < 50) stemup = true;
                	// it will be better to use noteY than pitch
                }

                if (wholeNote && tpsmes+4<=tmpnum && tmp<0.2) 
                { // ronde, whole
                    tpsmes+=4;
                    currentTick=addRhythm(4, pitch, stemup, currentTick, rowCount, currentXPos);
                    currentXPos+=(noteDistance*4);
                } 
                
                else if (dottedhalfNote && tpsmes+3<=tmpnum && tmp<0.4)
            	{ // blanche pointee, dotted half
                    tpsmes+=3;
                    currentTick=addRhythm(3, pitch, stemup, currentTick, rowCount, currentXPos);
                    currentXPos+=(noteDistance*3);
                } 
                
                else if (halfNote && tpsmes+2<=tmpnum && tmp<0.4)
            	{ // blanche, half
                    tpsmes+=2;
                    currentTick=addRhythm(2, pitch, stemup, currentTick, rowCount, currentXPos);
                    currentXPos+=(noteDistance*2);
                } 
             
                else if (quarterNote && tpsmes+1<=tmpnum && tmp<0.6) 
                { // noire, quarter
                    tpsmes+=1;
                    currentTick=addRhythm(1, pitch, stemup, currentTick, rowCount, currentXPos);
                    currentXPos+=noteDistance;
                }
                else if (eighthNote && tpsmes+0.5<=tmpnum && tmp<0.8)
                { // croche, eighth
                    tpsmes+=0.5;
                    currentTick=addRhythm(0.5, pitch, stemup, currentTick, rowCount, currentXPos);
                    currentXPos+=(noteDistance/2);
                }
                else if (triplet && tpsmes+1<=tmpnum && tmp<0.9)
                { // triplet
                  int[] tripletPitches = { pitch, 71, 71 };
                  int lowestPitch = tripletPitches[0];
                  if (selectedGame == SCOREREADING) {
                	  tripletPitches[1] = scoreLevel.tripletRandomPitch(tripletPitches[0]);
                	  tripletPitches[2] = scoreLevel.tripletRandomPitch(tripletPitches[0]);
                  }
                  for (int i = 1; i < 3; i++) {
                	  if (tripletPitches[i] < lowestPitch && !stemup)
                		  lowestPitch = tripletPitches[i];
                	  else if (tripletPitches[i] > lowestPitch && stemup)
            		  lowestPitch = tripletPitches[i];
                  }
                  
                  System.out.println("Triplet pitches: " + tripletPitches[0] + ", " + tripletPitches[1] + ", " + tripletPitches[2]);
                  System.out.println("Triplet lowest: " + lowestPitch);

                  currentTick=addRhythm(0.333, pitch, stemup, currentTick, rowCount, currentXPos);
                  setTripletValue(lowestPitch);
                  currentXPos+=(noteDistance/3);
                  currentTick=addRhythm(0.333, tripletPitches[1], stemup, currentTick, rowCount, currentXPos);
                  setTripletValue(100 + lowestPitch);
                  currentXPos+=(noteDistance/3);
                  currentTick=addRhythm(0.333, tripletPitches[2], stemup, currentTick, rowCount, currentXPos);
                  setTripletValue(100 + lowestPitch);
                  tpsmes+=1;
                  currentXPos+=(noteDistance/3);
                }
            }

            tpsmes=0;
            if ((r%numberOfMeasures) == 0) {
            	currentXPos = windowMargin + keyWidth + alterationWidth + timeSignWidth + notesShift;
            	rowCount++;
            }
/*
            } else {
                rhythms.add(new Rhythm(0, 0, 0, 71, false, false, 0));
            }
*/
        }

      if (selectedGame == RHYTHMREADING) regroupenotes(); //not workin with Scorereading yet

    }

    private void regroupenotes() {
        for (int i=0; i<rhythms.size()-1; i++) {
            if (rhythms.get(i).getDuration()==0.5 && rhythms.get(i+1).getDuration()==0.5 &&  //TO BE FIX  FOR 8
                !rhythms.get(i+1).isSilence() && !rhythms.get(i).isSilence() &&
                !isBeginMeasure(i+1)  && !rhythms.get(i).isGroupee())
            {
                rhythms.get(i).setGroupee(1);
                rhythms.get(i+1).setGroupee(2);
            }

        }
    }

    // LINES OF NOTES

    private void createline() {
        Dimension size=getSize();
        Chord a=new Chord(ncourante, ncourante, ncourante, "", 0);
        Interval inter=new Interval(ncourante, ncourante, "");

        // System.out.println(type2);

        if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame()) {
            ligne[0]=new Note("", "", setNoteHeight(noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder(), noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder()), size.width-notemargin, 0);
            ligne[0].updateNote(noteLevel, scoreYpos, bundle);
            ligne[0].updateAccidental(noteLevel, bundle);

            String tmpa="";
            for (int i=1; i<ligne.length; i++) {
                int tmph=setNoteHeight(noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder(), noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder());
                while (tmph==ligne[i-1].getHeight()) {
                    tmph=setNoteHeight(noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder(), noteLevel.getNbnotesupper(), noteLevel.getNbnotesunder()); // pour �viter les r�p�titions
                }

                ligne[i]=new Note(tmpa, "", tmph, size.width-notemargin+i*35, 0);
                ligne[i].updateNote(noteLevel, scoreYpos, bundle);
                ligne[i].updateAccidental(noteLevel, bundle);
            }
        }

            else if (noteLevel.isCustomNotesgame()) {
            
                ligne[0]=new Note("", "", 0, size.width-notemargin, noteLevel.getRandomPitch() );
                ligne[0].updateNotePitch(noteLevel, scoreYpos, bundle);
        

                String tmpa="";
                for (int i=1; i<ligne.length; i++) {
                    int tmpp=noteLevel.getRandomPitch();
                    while (tmpp==ligne[i-1].getPitch()) {
                        tmpp=noteLevel.getRandomPitch(); // to avoid same pitch
                    }

                    ligne[i]=new Note(tmpa, "", 0, size.width-notemargin+i*35, tmpp);
                    ligne[i].updateNotePitch(noteLevel, scoreYpos, bundle);
                   
                }
            }
                
         
            
            position=0;
            ncourante=ligne[position]; // initialisa tion avec la premiï¿½re note
            //if (soundOnCheckBox.isSelected()) sons[indiceson(ncourante.getHeight())].play(); // dï¿½part du son de la premiï¿½re note
           if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            
        } else if (noteLevel.isChordsgame()) {
            // voir pour precedant
            for (int i=0; i<ligne.length; i++) {

                a.copy(chordchoice());

                a.updatex(size.width-notemargin+i*50);
                ligneacc[i]=new Chord(a.getNote(0), a.getNote(1), a.getNote(2),
                    a.getName(), a.getInversion());
                ligneacc[i].convert(noteLevel);

            }
            position=0;
            posnote=0;

            acourant.copy(ligneacc[position]);
            // acourant.convertir(clecourante,typeaccord);
            ncourante=acourant.getNote(acourant.realposition(posnote));
            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }
        } else if (noteLevel.isIntervalsgame()) {
            // voir pour precedant
            for (int i=0; i<ligne.length; i++) {
                inter.copy(intervalchoice());
                //i = nouvelintervalle();
                inter.updatex(size.width-notemargin+i*65);
                ligneint[i]=new Interval(inter.getNote(0), inter.getNote(1),
                    inter.getName());

            }
            position=0;
            posnote=0;

            icourant.copy(ligneint[position]);
            ncourante=icourant.getNote(posnote); //0
            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }

        }
    }

    private void drawInlineNotes(Graphics g, Font f) {

        for (int i=position; i<ligne.length; i++) {
            // n'affiche que la ligne ï¿½ partir de la position
            if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) {
                drawNote(ligne[i], g, f, Color.black);
            } else if (noteLevel.isChordsgame()) {
                drawChord(ligneacc[i], g, i==position);
            } else if (noteLevel.isIntervalsgame()) {
                drawInterval(ligneint[i], g, i==position);
            }
        }

    }

    private void drawNotesAndAnswers(Graphics g, Font f) {

        // paint answers: red = wrong, green = good
        for (int i=0; i<answers.size(); i++) {
        	if (!answers.get(i).isnull()) answers.get(i).paint(g);
        }

        for (int i=0; i<rhythms.size(); i++) {
            // System.out.println(i);
            if (rhythms.get(i).getDuration()!=0) {
               
                if ((rhythmgame == 0) && (i!=rhythmIndex) || (muterhythms)) { //only paint note in learning mode
                    rhythms.get(i).paint(g, selectedGame, f, scoreLevel, 9, rowsDistance, false, scoreYpos, this);
                } else {
                    rhythms.get(i).paint(g, selectedGame, f, scoreLevel, 9, rowsDistance, true, scoreYpos, this);
                }
            }
        }

    }

    // CHORDS

    private void newChord() {

        if (noteLevel.isNormalgame() || noteLevel.isLearninggame()) {

            posnote=0;
            acourant.copy(chordchoice());
            acourant.convert(noteLevel);
            ncourante=acourant.getNote(acourant.realposition(posnote));
            if (soundOnCheckBox.isSelected()) {
                synthNote(ncourante.getPitch(), 80, noteDuration);
            }

        } else if (noteLevel.isInlinegame()) {
            if (position<ligne.length-1) {
                position+=1;
                acourant.copy(ligneacc[position]);

                posnote=0;
                //acourant.convertir(clecourante,typeaccord);
                ncourante=acourant.getNote(acourant.realposition(posnote));
                if (soundOnCheckBox.isSelected()) {
                    synthNote(ncourante.getPitch(), 80, noteDuration);
                }

            }
        }
    }

    private void drawInterval(Interval inter, Graphics g,
        boolean Intervallecourant) {
        Dimension size=getSize();

        if (inter.getNote(posnote).getX()<size.width-notemargin &&
            inter.getNote(posnote).getX()>=notemargin+98 && parti) {
            // NOTE DANS LIMITES
            inter.paint(posnote, noteLevel, g, MusiSync, scoreYpos,
                bundle, Intervallecourant, this);
            //g.drawString("Renv" + a.renvst,100,100);
        } else {
            if (noteLevel.isNormalgame()) {
                currentScore.addPoints(-20);
                if (currentScore.isLost()) {
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    stopSound();
                    showResult();
                }

                if (parti) newinterval();
            } else if (noteLevel.isLearninggame()) {
                newinterval();
                resetButtonColor();
            } else if (noteLevel.isInlinegame() && parti) {
                if (ligneint[position].getNote(0).getX()<notemargin+98) { // Si la note courant dï¿½passe la limite ici marge +25
                    currentScore.setPoints(0);
                    currentScore.setLost();
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    stopSound();
                    showResult();
                }
            }
        }
    }

    private void nextnote() {

        if (noteLevel.isChordsgame()) {
            if (posnote<2) {
                posnote+=1;

                ncourante=acourant.getNote(acourant.realposition(posnote));
                alterationok=false;
                if (soundOnCheckBox.isSelected()) {
                    synthNote(ncourante.getPitch(), 80, noteDuration);
                }

            } else {
                if (isLessonMode && notecounter==noteLevel.getLearningduration()) {
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    nextLevel();
                } else {
                    newChord();
                    notecounter++;
                }
            }
        } else if (noteLevel.isIntervalsgame()) {
            if (posnote==0) {
                posnote+=1;

                ncourante=icourant.getNote(posnote);
                alterationok=false;
                if (soundOnCheckBox.isSelected()) {
                    synthNote(ncourante.getPitch(), 80, noteDuration);
                }

            } else {
                if (isLessonMode && notecounter==noteLevel.getLearningduration()) {
                    parti=false;
                    startButton.setText(bundle.getString("_start"));
                    nextLevel();
                } else {
                    newinterval();
                    notecounter++;
                }
            }
        }
    }

    // SCORE

    private void showResult() {
    	if (selectedGame == NOTEREADING ){

        if (currentScore.isWin()) {
            scoreMessage.setTitle(bundle.getString("_congratulations"));

            textscoreMessage.setText("  "+currentScore.getNbtrue()+" "+bundle.getString("_correct")+
                " / "+currentScore.getNbfalse()+" "+
                bundle.getString("_wrong")+"  ");
            scoreMessage.pack();
            scoreMessage.setLocationRelativeTo(this);

            scoreMessage.setVisible(true);

            stopNoteGame();


        } else if (currentScore.isLost()) {
            scoreMessage.setTitle(bundle.getString("_sorry"));

            textscoreMessage.setText("  "+currentScore.getNbtrue()+" "+bundle.getString("_correct")+
                " / "+currentScore.getNbfalse()+" "+
                bundle.getString("_wrong")+"  ");
            scoreMessage.pack();
            scoreMessage.setLocationRelativeTo(this);
            scoreMessage.setVisible(true);

            stopNoteGame();

        }
    	}
    	else if (selectedGame == RHYTHMREADING || selectedGame==SCOREREADING ) {
    		
    		  int nbgood = 0;
    		  int nbnotefalse = 0;
    		  int nbrhythmfalse = 0;
    		  int nbrhythms = 0;
    		  
    		   for (int i=0; i<answers.size(); i++) {
    	        	if (answers.get(i).allgood() && !answers.get(i).isnull()) nbgood= nbgood +1;
    	        	if (!answers.get(i).isnull() && answers.get(i).badnote()) nbnotefalse = nbnotefalse +1;
    	        	if (!answers.get(i).isnull() && answers.get(i).badrhythm() ) nbrhythmfalse = nbrhythmfalse +1;
    	    	       
    		   }
    	 
    		   //Nb rhythms
    		   for (int i=0; i<rhythms.size(); i++) {
   	        	if (!rhythms.get(i).isSilence() && !rhythms.get(i).isnull()) nbrhythms=  nbrhythms +1;
   	        
   	        }
    		   if (nbrhythms ==  nbgood)  scoreMessage.setTitle(bundle.getString("_congratulations"));
    		   else  scoreMessage.setTitle(bundle.getString("_sorry"));

    			   
              textscoreMessage.setText("  "+nbrhythms+" "+bundle.getString("_menuRythms")+" : "+nbgood+" "+bundle.getString("_correct")+
                  " / "+nbnotefalse+" "+ bundle.getString("_wrong")+"  "
                  +nbrhythmfalse+" "+ bundle.getString("_wrongrhythm")+"  ");
              scoreMessage.pack();
              scoreMessage.setLocationRelativeTo(this);

              scoreMessage.setVisible(true);
    	}

    }

    //****************     METHODS OF NOTES ANIMATION THREAD (run and stop)

    private class RenderingThread extends Thread {

        /**
         *  This thread calls Jalmus window refresh every 10ms
         */
        public void run() {
        	
            while (true) {
                try {
              
                    if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame()|| noteLevel.isCustomNotesgame()) {
                    	if (noteLevel.isInlinegame()) sleep(noteLevel.getSpeed()+4);
                    	else sleep(noteLevel.getSpeed());

                    } else if (noteLevel.isIntervalsgame()) {
                    	if (noteLevel.isInlinegame()) sleep(noteLevel.getSpeed()*3/2 + 4);
                    	else  sleep(noteLevel.getSpeed()*3/2);
                    } else if (noteLevel.isChordsgame()) {
                    	if (noteLevel.isInlinegame()) sleep(noteLevel.getSpeed()*2 + 4);
                    	else  sleep(noteLevel.getSpeed()*2);
                     
                    } else { //why ?
                    	if (noteLevel.isInlinegame()) sleep(noteLevel.getSpeed()+18 + 6);
                    	else  sleep(noteLevel.getSpeed()+18);
                    }

                    if (parti && !paused) {
                        if ((noteLevel.isNormalgame() || noteLevel.isLearninggame()) &&
                            (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame())) {
                            ncourante.setX(ncourante.getX()+1);
                        } else
                        if ((noteLevel.isNormalgame() || noteLevel.isLearninggame()) &&
                            noteLevel.isChordsgame()) {
                            acourant.move(1);
                        } else
                        if ((noteLevel.isNormalgame() || noteLevel.isLearninggame()) &&
                            noteLevel.isIntervalsgame()) {
                            icourant.move(1);
                        } else if (noteLevel.isInlinegame() &&
                            (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame())) {
                            for (int i=0; i<ligne.length; i++) {

                                ligne[i].setX(ligne[i].getX()-1);
                            }
                        } else
                        if (noteLevel.isInlinegame() && noteLevel.isChordsgame()) {
                            for (int i=0; i<ligneacc.length; i++) {
                                ligneacc[i].move(-1);
                            }
                        } else
                        if (noteLevel.isInlinegame() && noteLevel.isIntervalsgame()) {
                            for (int i=0; i<ligneint.length; i++) {
                                ligneint[i].move(-1);

                            }
                        }
                    }

                    panelanim.repaint();

                    int tmpdiv = 1;	
                    //thread for rhythm game move the rhythm cursor according to tempo
                    if ((selectedGame == RHYTHMREADING || selectedGame==SCOREREADING) && rhythmgame == 0 && muterhythms && cursorstart) {
                       
                    	if (selectedGame == RHYTHMREADING){
                    		tempo = rhythmLevel.getspeed();
                    		tmpdiv = rhythmLevel.getTimeDivision();
                    	}
                    	else {
                    		tempo = scoreLevel.getspeed();
                    		tmpdiv = scoreLevel.getTimeDivision();
                    	}
                    		
                    		
                    	if (timestart != 0) {
                    	   rhythmCursorXpos = rhythmCursorXStartPos + ((System.currentTimeMillis()-timestart)*(noteDistance*tmpdiv))/(60000/tempo);
                    	   //System.out.println(rhythmCursorXpos);
                    	}

                        if (rhythmCursorXpos >= rhythmCursorXlimit - notesShift) {
                            if (rhythmAnswerScoreYpos < scoreYpos + (rowsDistance * (numberOfRows - 2))) {
                            	rhythmAnswerScoreYpos += rowsDistance;
                            	rhythmCursorXStartPos = firstNoteXPos - notesShift;
                            	rhythmCursorXpos = rhythmCursorXStartPos;
                            	timestart = System.currentTimeMillis();
                            }
                            else { //end of game
                               	showResult();
                               	stopRhythmGame();
                                parti = false;
                               	repaint();
                            }
                        }
                    	sleep(10); // cursor moves every 10 milliseconds
                    }
                }
                    
                catch (Exception e) {
                }
            }

        }
    }

    private class Anim extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
        int width=680, height=480;

        public Anim() {
            setPreferredSize(new Dimension(width, height));
            setDoubleBuffered(true);

        }

        public void paintComponent(Graphics g) {
        	((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        	        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Dimension d=getSize();
            
            if (selectedGame==NOTEREADING) {

                super.paintComponent(g);

                g.setColor(Color.white);
                g.fillRect(0, 0, d.width, d.height);

                if (parti && !paused && (noteLevel.isNormalgame() || noteLevel.isLearninggame())) {
                    if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) {
                        drawNote(ncourante, g, MusiSync, Color.black);
                    }
                    //on affiche la note que lorsque la partie a commencï¿½e
                    else if (noteLevel.isChordsgame()) {
                        drawChord(acourant, g, true);
                    } else if (noteLevel.isIntervalsgame()) {
                        drawInterval(icourant, g, true);
                    }
                } else if ((parti && !paused && noteLevel.isInlinegame())) {
                    drawInlineNotes(g, MusiSync);
                }

                drawInlineGame(g);
                drawKeys(g);
                noteLevel.getCurrentTonality().paint(1,noteLevel.getKey(), g, MusiSync, notemargin + keyWidth, scoreYpos, rowsDistance, 1, this, bundle);

                if (!noteLevel.isLearninggame()) {
                    currentScore.paint(g, d.width);
                }

                Note basenotet1 =new Note("","",0,0,0);
                Note basenotet2 =new Note("","",0,0,0);
                
                Note basenoteb1 =new Note("","",0,0,0);
                Note basenoteb2 =new Note("","",0,0,0);
                
                if (noteLevel.isCurrentKeyTreble() ) {
                	basenotet1.setHeight(scoreYpos+noteLevel.getBasetreble()-(noteLevel.getNbnotesunder()*5));
                	basenotet1.updateNote(noteLevel, scoreYpos, bundle);
                	basenotet2.setHeight(scoreYpos+noteLevel.getBasetreble()+(noteLevel.getNbnotesupper()*5));
                	basenotet2.updateNote(noteLevel, scoreYpos, bundle);
                }
                else if (noteLevel.isCurrentKeyBass()) {
                	basenoteb1.setHeight(scoreYpos+noteLevel.getBasebass()-(noteLevel.getNbnotesunder()*5));
                    basenoteb1.updateNote(noteLevel, scoreYpos, bundle);
                	basenoteb2.setHeight(scoreYpos+noteLevel.getBasebass()+(noteLevel.getNbnotesupper()*5));
                    basenoteb2.updateNote(noteLevel, scoreYpos, bundle);                	
                }
                else if (noteLevel.isCurrentKeyBoth()){
                	basenotet1.setHeight(scoreYpos+noteLevel.getBasetreble()-(noteLevel.getNbnotesunder()*5));
                    basenotet1.updateNote(noteLevel, scoreYpos, bundle);
                    basenotet2.setHeight(scoreYpos+noteLevel.getBasetreble()+(noteLevel.getNbnotesupper()*5));
                    basenotet2.updateNote(noteLevel, scoreYpos, bundle);
                    basenoteb1.setHeight(scoreYpos+noteLevel.getBasebass()+90-(noteLevel.getNbnotesunder()*5));
                    basenoteb1.updateNote(noteLevel, scoreYpos, bundle);
                 	basenoteb2.setHeight(scoreYpos+noteLevel.getBasebass()+90+(noteLevel.getNbnotesupper()*5));
                    basenoteb2.updateNote(noteLevel, scoreYpos, bundle);
                }
                
                if (noteLevel.isLearninggame() ) {
                    if (noteLevel.isNotesgame() || noteLevel.isAccidentalsgame() || noteLevel.isCustomNotesgame()) {
                        piano.paint(g, d.width, !isLessonMode & !parti, basenotet1.getPitch(), basenotet2.getPitch(),
                        		    basenoteb1.getPitch(), basenoteb2.getPitch(), ncourante.getPitch(), 0, 0, noteLevel.isCustomNotesgame(), noteLevel.getPitcheslist());
                    } else if (noteLevel.isIntervalsgame()) {
                        piano.paint(g, d.width, false, basenotet1.getPitch(), basenotet2.getPitch(),basenoteb1.getPitch(), 
                        		    basenoteb2.getPitch(), icourant.getNote(0).getPitch(),
                        		    icourant.getNote(1).getPitch(), 0,noteLevel.isCustomNotesgame(), noteLevel.getPitcheslist());
                    } else if (noteLevel.isChordsgame()) {
                        piano.paint(g, d.width, false, basenotet1.getPitch(), basenotet2.getPitch(),basenoteb1.getPitch(), 
                        		    basenoteb2.getPitch(), acourant.getNote(0).getPitch(),
                        		    acourant.getNote(1).getPitch(),
                        		    acourant.getNote(2).getPitch(),
                        		    noteLevel.isCustomNotesgame(), noteLevel.getPitcheslist());
                    }
                    applyButtonColor();
                } 
                else {
                    piano.paint(g, d.width, !isLessonMode & !parti & (noteLevel.isNotesgame()|| noteLevel.isAccidentalsgame()),
                    		basenotet1.getPitch(), basenotet2.getPitch(),basenoteb1.getPitch(), basenoteb2.getPitch(),  0, 0, 0,
                    		noteLevel.isCustomNotesgame(), noteLevel.getPitcheslist());
                }

            } else if (selectedGame==FIRSTSCREEN) {

                g.drawImage(jbackground, 0, 0, d.width, d.height, this);

                Color color=new Color(5, 5, 100);
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 60));
                g.drawString("Jalmus", (d.width/2) - 95, (d.height / 2) - 35);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                g.drawString("Java Lecture Musicale", (d.width/2) - 155, (d.height / 2) + 15);
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("Copyright (C) 2003-2011 RICHARD Christophe", 10, d.height - 40);
                
                
            } else if (selectedGame==RHYTHMREADING || selectedGame==SCOREREADING) {

                g.setColor(Color.white);
                g.fillRect(0, 0, d.width, d.height);
                pgamebutton.setBackground(Color.white);

                if ((selectedGame==RHYTHMREADING && rhythmLevel.getTriplet()) ||
                	(selectedGame==SCOREREADING && scoreLevel.getTriplet()))
                	//rowsDistance = 130;
                	rowsDistance = 100;
                else
                	rowsDistance = 100;

                drawScore(g);
                drawKeys(g);
                drawTimeSignature(g);

                if (selectedGame==SCOREREADING) {
                	numberOfRows = ((getSize().height - scoreYpos - 50) / rowsDistance)+1;    
                	scoreLevel.getCurrentTonality().paint(3, scoreLevel.getKey(), g, MusiSync, windowMargin + keyWidth, scoreYpos, rowsDistance, numberOfRows, this, bundle);
                }
                /* Show cursor if enabled */
                if ((selectedGame == RHYTHMREADING && rhythmLevel.getMetronomeBeats()) ||
                   	(selectedGame==SCOREREADING && scoreMetronomeShowCheckBox.isSelected())) {
                	g.setColor(Color.orange);
                    g.fillRect(rhythmCursorXStartPos, rhythmAnswerScoreYpos - 31, (int)rhythmCursorXpos - rhythmCursorXStartPos, 3);
                }
                	
                if (paintrhythms)
                    drawNotesAndAnswers(g, MusiSync);
            }
        }
    }

    private class DumpReceiver implements Receiver {

        public DumpReceiver() {

        }

        public void send(MidiMessage event, long time) {

            String output="";

            if (outputDevice != null)
            {
            	try {
					outputDevice.getReceiver().send(event, time);
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            if (selectedGame==NOTEREADING || selectedGame==RHYTHMREADING || selectedGame==SCOREREADING) {

                if (event instanceof ShortMessage) {
                    if (!open) {
                        open=true;

                    }
                    switch (event.getStatus()&0xf0) {
                        case 0x90:
                            output=("   Note On Key: "+((ShortMessage)event).getData1()+
                                " Velocity: "+((ShortMessage)event).getData2());
                            //pitch de la note jouï¿½e
                            int notejouee=((ShortMessage)event).getData1()+((Number)transpositionSpinner.getValue()).intValue();

                            //System.out.println(((ShortMessage)event).getData2());

                            // touche C3 pour lancer le jeu au clavier
                            
                            if (selectedGame==NOTEREADING) {

                            if (!parti&(((ShortMessage)event).getData2()!=0)&((ShortMessage)event).getData1()==60) {
                            	System.out.println("C3");
                                if (levelMessage.isVisible()) {
                                    System.out.println("levelmessage");

                                    oklevelMessage.doClick();
                                } else if (scoreMessage.isVisible()) {

                                    okscoreMessage.doClick();

                                } else {
                                	requestFocus();
                                    startNoteGame();
                                	if (!renderingThread.isAlive()) {
                                	renderingThread.start();
                                	}
                                         
                                }


                            } else {
                            	if (keyboardsoundCheckBox.isSelected())
                            	{
	                                if (((ShortMessage)event).getData2()!=0) {
	                                    piano.playNote(currentChannel, !midierror, notejouee, 1);
	                                } else {
	                                    piano.playNote(currentChannel, !midierror, notejouee, 0);
	                                }
                            	}

                                repaint();

                                if (((ShortMessage)event).getData2()!=0&parti&!paused) {
                                  //  System.out.print(((ShortMessage)event).getData1());
                                  //  System.out.println("-"+ncourante.getPitch());

                                    if (isSameNote(((ShortMessage)event).getData1(), ncourante.getPitch()))
                                        rightAnswer();
                                    else
                                    {
                                    	System.out.println("Input:" + ((ShortMessage)event).getData1() +" Correct note:" + ncourante.getPitch() );
                                        wrongAnswer();
                                    }

                                    repaint();
                                }
                            }
                            }
                            
                            if (selectedGame==RHYTHMREADING && parti) {
                            	if (((ShortMessage)event).getData2()!=0)
                            		rhythmKeyPressed(71);
                            	else  {
                            		 rhythmKeyReleased(71);
                            		 // System.out.println ("released");
                            	}
                            	
                            }
                            
                            if (selectedGame==SCOREREADING && parti) {
                            	if (((ShortMessage)event).getData2()!=0)
                            		rhythmKeyPressed(((ShortMessage)event).getData1());
                            	else  {
                            		 rhythmKeyReleased(((ShortMessage)event).getData1());
                            		//  System.out.println ("released");
                            	}
                            	
                            }
                           
                            break;
                        case 0x80:
                            output=("   Note Off  Key: "+((ShortMessage)event).getData1()+
                                " Velocity: "+((ShortMessage)event).getData2());
                            break;
                        case 0xb0:
                            if (((ShortMessage)event).getData1()<120) {
                                output=("   Controller No.: "+
                                    ((ShortMessage)event).getData1()+
                                    " Value: "+((ShortMessage)event).getData2());
                            } else {
                                output=("   ChannelMode Message No.: "+
                                    ((ShortMessage)event).getData1()+" Value: "+
                                    ((ShortMessage)event).getData2());
                            }
                            break;
                        case 0xe0:
                            output=("   Pitch lsb: "+((ShortMessage)event).getData1()+
                                " msb: "+((ShortMessage)event).getData2());
                            break;
                        case 0xc0:
                            output=("   Program Change No: "+
                                ((ShortMessage)event).getData1()+
                                " Just for Test: "+((ShortMessage)event).getData2());
                            break;
                        case 0xd0:
                            output=("   Channel Aftertouch Pressure: "+
                                ((ShortMessage)event).getData1()+" Just for Test: "+
                                ((ShortMessage)event).getData2());
                            break;
                    }
                } else if (event instanceof SysexMessage) {
                    output=("   SysexMessage: "+(event.getStatus()-256));
                    byte[] data=((SysexMessage)event).getData();
                    for (int x=0; x<data.length; x++) {
                        output=(" "+Integer.toHexString(data[x]));
                    }
                } else {
                    output=("   MetaEvent");
                }
                  if (output != "") System.out.println(output);
            }
        }

        public void close() {}

    }
    


    public static void main(String[] arg) {
        // Event pour la gestion des Evenements et principalement le message EXIT
        // Constructions de la frame
    	Dimension dim = new Dimension(790, 590);

        Jalmus jalmus=new Jalmus();
        // Initialization
        if (arg.length==0) {
            jalmus.init("");
        } else {
            jalmus.init(arg[0]);
        }

        // Force the window size
        jalmus.setSize(800, 600);
        jalmus.setMinimumSize(dim);

        // Draw
        jalmus.repaint();

        jalmus.setVisible(true);
        jalmus.setFocusable(true);

        //jalmus.setResizable(false);

        jalmus.setTitle("Jalmus"); // Give the application a title

        jalmus.setLocationRelativeTo(null); // Center the window on the display

        jalmus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when frame closed

    }

}
