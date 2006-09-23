/* Jalmus is an application to learn or perfect its music reading.

    FOR MIDICOMMON AND DUMPRECEIVER CLASS (http://www.jsresources.org/)
Copyright (c) 1999 - 2001 by Matthias Pfisterer <Matthias.Pfisterer@web.de>
Copyright (c) 2003 by Florian Bomers


Copyright (C) 2003-2004  RICHARD Christophe

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
Copyright (C) 2003-2004 RICHARD Christophe
Ce programme est libre, vous pouvez le redistribuer et/ou
le modifier selon les termes de la Licence Publique G�n�rale GNU
publi�e par la Free Software Foundation (version 2
ou bien toute autre version ult�rieure choisie par vous).

Ce programme est distribu� car potentiellement utile,
mais SANS AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
commercialisation ou d'adaptation dans un but sp�cifique. Reportez-vous à la
Licence Publique Générale GNU pour plus de d�tails.

Vous devez avoir re�u une copie de la Licence Publique G�n�rale GNU
en m�me temps que ce programme ; si ce n'est pas le cas, �crivez � la Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, Etats-Unis.
*/



/* Web : http://jalmus.netr
   E-mail : cvrichard@infonie.fr */

/* NEED JDK 1.4.2 */



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.AudioClip;
import java.lang.Math;
import java.lang.Integer;
import javax.sound.midi.*;
import java.util.Vector;
import pck_jlm.*;
import com.centerkey.utils.BareBonesBrowserLaunch;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.image.BufferStrategy;


public class jalmus extends JFrame implements WindowListener, MetaEventListener, MouseListener, KeyListener, ActionListener, ItemListener
                {

// pour traduction
String tlicence;

String tcredits;


  String pasclavier = "Pas de clavier MIDI             ";
  String toutes = "Toutes      ";
  String tous = "Tous";
  String seconde;
  String tierce;
  String quarte;
  String quinte;
  String sixte;
  String septieme;
  String octave;
  String mineur;
  String majeur;
  String diminuee;
  String augmentee;
  String juste;


  String simples;
  String renvers;
  String DO;
  String RE;
  String MI;
  String FA;
  String SOL;
  String LA;
  String SI;

  String langue="en";

 Locale locale         = null;
 ResourceBundle bundle = null;

  int ecranjeu = 0; // 0 ecran presentation, 1 jeu1, 2 jeu2
  MidiDevice.Info info;
  MidiDevice	inputDevice = null;

  boolean open = false;
  String message = "";

    boolean focussed = false;   // True when this applet has input focus.

  int notejouee = 0; //pitch de la note jou�e
  Note ncourante = new Note("","",0,25,0);
  accord acourant = new accord(ncourante,ncourante,ncourante,"",0);
  intervalle icourant = new intervalle(ncourante,ncourante,"");
  Tonalite tcourante = new Tonalite(0,"");
  boolean tonalitehasard = false; //indique si la tonalite est choisie au hasard
  int intervrandom = 2;

  int dportee = 110; //coordonn�e de la première ligne de port�e simple


    Image icone;



    Tabimage ti = new Tabimage();

    MidiChannel[] mc;
    Synthesizer syn = null;
    Instrument[] instr;
    String nomins;
    int dureenote = 2000;
    ChannelData channels[];
    ChannelData cc;    // current channel

//cson.getState() & !erreurmidi



      Piano piano;

    AudioClip songo;
    AudioClip sonerreur;
    //AudioClip sonjuste;
    AudioClip sondo;
    AudioClip sonmi;


/****************************************************************/
/****************************** MENU ****************************/
/****************************************************************/

   // Mise en place du menu
         JMenuBar maBarre = new JMenuBar();
         JMenu jeu = new JMenu();
         JMenuItem lectnote = new JMenuItem(new ImageIcon(getClass().getResource("/images/note.png")));
         JMenuItem lectrythme = new JMenuItem(new ImageIcon(getClass().getResource("/images/rythme.png")));
         JMenuItem quitter = new JMenuItem(new ImageIcon(getClass().getResource("/images/exit.png")));



         JMenu parametres = new JMenu();
         JMenuItem prefs = new JMenuItem(new ImageIcon(getClass().getResource("/images/prefs.png")));
          JMenuItem midi = new JMenuItem(new ImageIcon(getClass().getResource("/images/midi.png")));
          JMenu langues = new JMenu();
          JRadioButtonMenuItem rblanguefr= new JRadioButtonMenuItem();
          JRadioButtonMenuItem rblanguede= new JRadioButtonMenuItem();
          JRadioButtonMenuItem rblanguees= new JRadioButtonMenuItem();
          JRadioButtonMenuItem rblangueen = new JRadioButtonMenuItem();

         JMenu language = new JMenu("Langue");

         JComboBox blangue; //langage de l'applet

         JMenu aide = new JMenu();
         JMenuItem aidesommaire = new JMenuItem(new ImageIcon(getClass().getResource("/images/aide.png")));
         JMenuItem siteinternet = new JMenuItem(new ImageIcon(getClass().getResource("/images/internet.png")));
        /* JMenuItem paypal = new JMenuItem(new ImageIcon(getClass().getResource("/images/paypal.png")));*/
         JMenuItem propos = new JMenuItem(new ImageIcon(getClass().getResource("/images/about.png")));



/****************************************************************/
/***********************BOUTONS JEU - NOTES/GO *****************/
/****************************************************************/
         JPanel pboutonjeu = new JPanel();

    JButton bdo;
    JButton bre;
    JButton bmi;
    JButton bfa;
    JButton bsol;
    JButton bla;
    JButton bsi;
    JButton bdo2;
    JButton bbemol;
    JButton bdiese;
    JButton bbemol2;
    JButton bdiese2;
    JPanel pnotes = new JPanel();

    JButton bgo;    // bouton pour démarrer le jeu
    JButton bpref;  // bouton pour acceder directement aux prefernces jeu





    boolean erreurmidi = false;

/*******************************************************/

JDialog preferences; // Dialog Preferences
    JPanel ppref = new JPanel();

    JTabbedPane tabpref = new JTabbedPane(); // panel pour les parametres
     JPanel pprefjeu1 = new JPanel();
     JPanel ppref1jeu1 = new JPanel (); // panel pour le type du premier jeu
        JComboBox btype; //type de jeux
        JComboBox bvitesse;   // bouton pour choisir la vitesse
     JPanel ppref2jeu1 = new JPanel (); // panel pour la clef du premier jeu
        JComboBox bcle; //  bouton pour choisir la cl�
        JComboBox btonalite; // bouton pour choisir la tonalite
     JPanel ppref3jeu1 = new JPanel (); // panel pour le type de note du premier jeu
        JComboBox bgroupes;  // bouton pour choisir le nombre de differentes note
        JComboBox bselectnotes; // bouton de section pour le groupe
        JComboBox bselectint; // bouton de section pour le groupe
          JComboBox bselectacc; // bouton de section pour le groupe



    JPanel pprefjeu2 = new JPanel();
     JPanel ppref1jeu2 = new JPanel (); // panel pour le type de jeu
     JComboBox btype2;
     JComboBox bvitesse2;
     JPanel ppref2jeu2 = new JPanel (); // panel pour le type de rythme
     JCheckBox cronde;
     JCheckBox cblanche;
     JCheckBox cnoire;
     JCheckBox ccroche;
     JCheckBox csilence;
     JCheckBox cmetronome;
     JPanel ppref3jeu2 = new JPanel (); // panel pour le type de rythme

     JButton okpref;
     JButton cancelpref;

    int sauvprefs[] = new int[16]; // pour bouton cancel
/******************************************************/
    JDialog dmidi;
    JPanel pmidi = new JPanel(); //panel principal midi
    JPanel pmidi1 = new JPanel (); // panel pour le type du premier jeu
    JCheckBox cson;
    JComboBox binstr;
    JPanel pmidi2 = new JPanel (); // panel pour le type du premier jeu
    JCheckBox cexacte; // pour utilisation avec petit clavier nom de note
    JComboBox bmidiin;
    DefaultComboBoxModel model = new DefaultComboBoxModel();

    JButton okmidi;
     JButton cancelmidi;
     boolean selectmidi_forlang = false;

     int sauvmidi[] = new int[16]; // pour bouton cancel
/******************************************************/
    JDialog dapropos;
    JPanel papropos = new JPanel();
    JTextArea texteapropos;
    JScrollPane ascenceur;

    JButton bcredits;
    JButton blicence;
    JButton bfermer;

/******************************************************/


    JPanel principal= new JPanel(); // panel principal






   // ***********************DONNEES DU PROGRAMME

   RenderingThread renderingThread = new RenderingThread();
   anim panelanim = new anim();

                // DEFINITION DE LA NOTE *******************************



    Note ligne [] = new Note [40];   // ligne de notes  TYPE EN LIGNE
    accord ligneacc [] = new accord[40];  // ligne d'accords
    intervalle ligneint [] = new intervalle [40];
    int position = 0;        // position de la note courante dans la liste
    int precedente = 0;       // position de la note précédente pour éviter les répétitions
                 //**************************************************************


    // TABLEAU D'ACCORDS


    int choix = 0; // position de l'accord en cours
    int posnote = 1;       // position de la note courante dans l'accord ou l'intervalle
    boolean alterationok = false;

    String clecourante = "sol";


    int basesol = 25; // hauteur de la note de base choisie par l'utilisateur 20 = sol 4 = fa
    int basefa = 5;

    int marge = 220;

    int score = 100;  // de O PERDU � 1000 GAGNE
    int vitesse = 20;  // temps d'attente du thread = vitesse de la note de 20 � 2 bouton bvitesse
    int nbnotessur = 1;   // nombre de notes au dessus  de la note de base
    int nbnotessous = 1;   // nombre de notes en dessous de la note de base
    int nbnotessurfa = 1;   // nombre de notes au dessus  de la note de base pour cle de fa deuxieme portee
    int nbnotessousfa = 1;   // nombre de notes en dessous de la note de base

    boolean toutesnotes = false;
    String type = "NORMAL";  // type de jeu
    String type2 = "NOTES";  // notes, accords ou intervalle
    String typeaccord = "SIMPLE"; // accord simple ou accord avec renversement
    String typeinterv = "SECONDES"; // type d'intervalle


    boolean parti = false;   //  partie commenc�e ou non
    String resultat  = "INDETERMINE";                        //  "GAGNE" ou "PERDU"


    //**************DONNES POUR LES STATISTIQUES

    int nberreur = 0;
    int nbjuste = 0;


    // ***********************DONNEES POUR L'ANIMATION DE LA NOTE

   // Thread main=new Thread(this);



/******************************************************************/
/***************** DONNES LECTURE RYTHMIQUE ***********************/
/*****************************************************************/

    Rythme ligner[] = new Rythme[80]; // ligne de notes  TYPE EN LIGNE

    int positionr = -1; // position de la note courante dans la liste

    //**************************************************************


     int tempo = 40; // tempo du sequencer - bouton bvitesse2

    double nbtemps = 4; // nombre de temps par mesure
    int nbmesures = 9;


    Track track = null;
    Track metronome = null;
    static final int ppq = 12;

    Niveau nivcourant = new Niveau(true,true,false,false,false);


    //int dureerythme = 10000;
   // ChannelData channels[];
   // ChannelData cc; // current channel
    Sequence sequence = null;
    Sequencer sm_sequencer = null;
      Synthesizer sm_synthesizer = null;
    private static final int VELOCITY = 64;


// ***********************METHODE D'INITIALISATION (gaphics et panels)


    public void init(){
      Graphics g=getGraphics();


      try{
        if (syn == null) {
          if ((syn = MidiSystem.getSynthesizer()) == null) {
            System.out.println ("getSynthesizer() failed!");

            return;
          }
        }
      syn.open();
      }
      catch(MidiUnavailableException e) {
      System.out.println("Midiunavailable : sortie MIDI occupee - fermez toutes les autres applications pour avoir du son");
      erreurmidi = true;
      }

    if (!erreurmidi){
      Soundbank sb = syn.getDefaultSoundbank();
      if (sb != null) {
        instr = syn.getDefaultSoundbank().getInstruments();

       if (instr != null) {syn.loadInstrument(instr[0]);

      }
      else {erreurmidi = true; System.out.println("Soundbank null");}
      }


      MidiChannel mc [] = syn.getChannels();

             channels = new ChannelData[mc.length];
             for (int i = 0; i < channels.length; i++) {
                 channels[i] = new ChannelData(mc[i], i);
             }
        cc = channels[0];
      }


      //catch(InterruptedException e2) {System.out.println("Problème midi");}}





/************************************************************/
/******* PREFERENCES JEU 1 LECTURE DE NOTE  *****************/
/************************************************************/
    pprefjeu1.setLayout(new GridLayout(3,1));

/* 1er panel - type de jeu */

      btype = new JComboBox();

      btype.addItemListener(this);
      ppref1jeu1.add(btype);

      bvitesse = new JComboBox();
      bvitesse.addItem("Largo");
      bvitesse.addItem("Adagio");
      bvitesse.addItem("Moderato");
      bvitesse.addItem("Allegro");
      bvitesse.addItem("Presto");
      bvitesse.addItemListener(this);


      ppref1jeu1.add(bvitesse);


      pprefjeu1.add(ppref1jeu1);

/* 2ème panel - clef */

      bcle = new JComboBox();

      bcle.addItemListener(this);
      ppref2jeu1.add(bcle);

      btonalite = new JComboBox();

      btonalite.addItemListener(this);
      ppref2jeu1.add(btonalite);


      pprefjeu1.add(ppref2jeu1);


/* 3ème panel - Notes */

      bgroupes = new JComboBox();

      bgroupes.addItemListener(this);
      ppref3jeu1.add(bgroupes);
      //bgroupes.setSelectedItem(notes);

      bselectnotes = new JComboBox();

      bselectnotes.addItemListener(this);
      ppref3jeu1.add(bselectnotes);

      bselectint = new JComboBox();
      bselectint.addItemListener(this);

      bselectacc = new JComboBox();
      bselectacc.addItemListener(this);


      //bselectnotes.setSelectedItem("3 notes");


      pprefjeu1.add(ppref3jeu1);



      /************************************************************/
      /******* PREFERENCES JEU 2 LECTURE RYTHMIQUE  ***************/
      /************************************************************/
          pprefjeu2.setLayout(new GridLayout(3,1));

      /* 1er panel - type de jeu */

            btype2 = new JComboBox();
            btype2.addItem("Normal");
            btype2.addItemListener(this);
            ppref1jeu2.add(btype2);

            bvitesse2 = new JComboBox();
            bvitesse2.addItem("Largo");
            bvitesse2.addItem("Adagio");
            bvitesse2.addItem("Moderato");
            bvitesse2.addItem("Allegro");
            bvitesse2.addItem("Presto");
            bvitesse2.addItemListener(this);
            //bvitesse.setSelectedItem("Moderato");
            ppref1jeu2.add(bvitesse2);


            pprefjeu2.add(ppref1jeu2);

            /* 2ème panel - RYTHME */

            cronde = new JCheckBox("", true);
            cblanche = new JCheckBox("", true);
            cnoire = new JCheckBox("", false);
            ccroche = new JCheckBox("", false);
            csilence = new JCheckBox("", true);
            cmetronome = new JCheckBox("", true);
            ppref2jeu2.add(cronde);
            ppref2jeu2.add(cblanche);
            ppref2jeu2.add(cnoire);
            ppref2jeu2.add(ccroche);
            ppref2jeu2.add(csilence);


            ppref3jeu2.add(cmetronome);



            pprefjeu2.add(ppref2jeu2);
            pprefjeu2.add(ppref3jeu2);


            okpref = new JButton();

            okpref. addActionListener(this);
            okpref.setIcon( new ImageIcon(getClass().getResource("/images/ok.png")));

            cancelpref = new JButton();
            cancelpref. addActionListener(this);
            cancelpref.setIcon( new ImageIcon(getClass().getResource("/images/cancel.png")));



            tabpref.addTab("Lecture de note", new ImageIcon(getClass().getResource("/images/note.png")), pprefjeu1);
            tabpref.addTab("Lecture rythmique", new ImageIcon(getClass().getResource("/images/rythme.png")),
                           pprefjeu2);

            ppref.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
            ppref.add(tabpref);
            ppref.add(okpref);
            ppref.add(cancelpref);

/************************************************************/
/****************** FENETRE OPTIONS MIDI  *******************/
/************************************************************/

       pmidi.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

      cson = new JCheckBox("", true);
      cexacte = new JCheckBox("", true);
      bmidiin = new JComboBox();

      model.addElement(pasclavier);




    // bmidiin.addItem(pasclavier);
    // bmidiin.setSelectedIndex(0);
      MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
      for (int i = 0; i < aInfos.length; i++) {
        try {
          MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
          boolean bAllowsInput = (device.getMaxTransmitters() != 0);

          if (bAllowsInput) {
            model.addElement(aInfos[i].getName());
           // bmidiin.addItem(aInfos[i].getName());
            // out("" + i + "  " + aInfos[i].getName());
          }

        }
        catch (MidiUnavailableException e) {
          // device is obviously not available...
          //out(e);
        }
      }
      if (aInfos.length == 0) {
        // out("[No devices available]");
      }
      bmidiin.setModel(model);

      bmidiin.addItemListener(this);
      binstr = new JComboBox();
      for (int i = 0; i < 20; i++)
        binstr.addItem(instr[i].getName());
      binstr.addItemListener(this);

      pmidi1.add(cson);
      pmidi1.add(binstr);
      pmidi2.add(bmidiin);
      pmidi2.add(cexacte);

      okmidi = new JButton();
      okmidi.addActionListener(this);
      okmidi.setIcon(new ImageIcon(getClass().getResource("/images/ok.png")));

      cancelmidi = new JButton();
      cancelmidi.addActionListener(this);
      cancelmidi.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));

      pmidi2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.
          createTitledBorder("Clavier"),
          BorderFactory.createEmptyBorder(5, 5, 5, 5)));

      pmidi.add(pmidi1);
      pmidi.add(pmidi2);
      pmidi.add(okmidi);
      pmidi.add(cancelmidi);








      bgo = new JButton();

      bgo.addActionListener(this);

      bpref = new JButton();

      bpref.addActionListener(this);




      bdo = new JButton();
      bdo. addActionListener(this);
      pnotes.add(bdo);
      bre = new JButton();
      bre. addActionListener(this);
      pnotes.add(bre);
      bmi = new JButton();
      bmi. addActionListener(this);
      pnotes.add(bmi);

      bfa = new JButton();
      bfa. addActionListener(this);
      pnotes.add(bfa);

      bsol = new JButton();
      bsol. addActionListener(this);
      pnotes.add(bsol);

      bla = new JButton();
      bla. addActionListener(this);
      pnotes.add(bla);

      bsi = new JButton();
      bsi. addActionListener(this);
      pnotes.add(bsi);

      bdo2 = new JButton();
      bdo2. addActionListener(this);
      pnotes.add(bdo2);

      // BOUTONS POUR ACCORDS
      bdiese = new JButton();
      bdiese.setText("#");
      bdiese. addActionListener(this);

      bbemol = new JButton();
      bbemol.setText("b");
      bbemol. addActionListener(this);

      bdiese2 = new JButton();
      bdiese2.setText("#");
      bdiese2. addActionListener(this);

      bbemol2 = new JButton();
      bbemol2.setText("b");
      bbemol2. addActionListener(this);

      pnotes.add(bdiese,0);
      pnotes.add(bbemol,5);
      pnotes.add(bbemol2,6);
      pnotes.add(bdiese2,11);
      pnotes.setLayout(new GridLayout(2,6));

      //cson.addActionListener(this);

      // BOUTONS INVISIBLES EN MODE NORMAL
     bdiese.setVisible(false);
      bdiese2.setVisible(false);
      bbemol.setVisible(false);
     bbemol2.setVisible(false);

      //add(pnotes);

      pboutonjeu.setLayout(new FlowLayout());
       pboutonjeu.add(bgo);
       bgo.setPreferredSize(new Dimension(150,20));
       pnotes.setPreferredSize(new Dimension(450,40));
       bpref.setPreferredSize(new Dimension(150,20));
    pboutonjeu.add(pnotes);
    pboutonjeu.add(bpref);
    pnotes.setBackground(Color.white);
    pboutonjeu.setBackground(Color.white);




/************************************************************************/
/******************************** MENU *********************************/
/***********************************************************************/
       preferences = new JDialog(this, true);
       preferences.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
       preferences.setResizable(false);

       dmidi = new JDialog(this, true);
       dapropos = new JDialog(this, true);
       dapropos.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
       dapropos.setResizable(false);

       jeu.setMnemonic(KeyEvent.VK_J);
       jeu.add(lectnote);
       jeu.add(lectrythme);
       jeu.addSeparator();
       jeu.add(quitter);
       lectnote.addActionListener(this);
       lectrythme.addActionListener(this);
       quitter.addActionListener(this);
       maBarre.add(jeu);

       parametres.add(prefs);
       prefs.addActionListener(this);
       parametres.add(midi);
       midi.addActionListener(this);


       //preferences.add(okpref);

       ButtonGroup group = new ButtonGroup();

      rblanguefr= new JRadioButtonMenuItem("Français");
      rblanguefr.setMnemonic(KeyEvent.VK_F);
       group.add(rblanguefr);
      rblanguefr.addActionListener(this);
       langues.add(rblanguefr);

       rblanguede= new JRadioButtonMenuItem("Deutsch");
     rblanguede.setMnemonic(KeyEvent.VK_F);
      group.add(rblanguede);
     rblanguede.addActionListener(this);
      langues.add(rblanguede);

      rblanguees= new JRadioButtonMenuItem("Espanol");
    rblanguees.setMnemonic(KeyEvent.VK_F);
     group.add(rblanguees);
    rblanguees.addActionListener(this);
     langues.add(rblanguees);



      rblangueen = new JRadioButtonMenuItem("English");
      rblangueen.setSelected(true);
      rblangueen.setMnemonic(KeyEvent.VK_A);
      rblangueen.addActionListener(this);
       group.add(rblangueen);
       langues.add(rblangueen);

       langues.setIcon(new ImageIcon(getClass().getResource("/images/language.png")));



       langues.addActionListener(this);
        langues.setMnemonic(KeyEvent.VK_L);

        parametres.addSeparator();
        parametres.add(langues);
        parametres.setMnemonic(KeyEvent.VK_P);
        maBarre.add(parametres);

        aide.setMnemonic(KeyEvent.VK_A);
        aide.add(aidesommaire);
        aide.addSeparator();
        aide.add(siteinternet);

        aide.add(propos);
        aidesommaire.addActionListener(this);
        siteinternet.addActionListener(this);

        propos.addActionListener(this);
        maBarre.add(aide);

         this.setJMenuBar(maBarre);
         maBarre.setVisible(true);


/**************************************************************/
/**************************************************************/
/**************************************************************/

         /***************** FENETRE A PROPOS ******************************/
             papropos.setVisible(true);
             bcredits = new JButton();

            bcredits.setIcon( new ImageIcon(getClass().getResource("/images/credits.png")));
            bcredits.addActionListener(this);

             blicence = new JButton();

             blicence.setIcon( new ImageIcon(getClass().getResource("/images/licence.png")));
             blicence.addActionListener(this);
             bfermer = new JButton();

             bfermer.setIcon( new ImageIcon(getClass().getResource("/images/cancel.png")));
             bfermer.addActionListener(this);

             dapropos.add(papropos);


             texteapropos = new JTextArea(12, 25);
             ascenceur = new JScrollPane(texteapropos);
             texteapropos.setEditable(false);
             texteapropos.setLineWrap(true);
             texteapropos.setWrapStyleWord(true);
             texteapropos.setFont(new Font("SansSerif", Font.BOLD, 14));
             texteapropos.setBackground(this.getBackground());

             texteapropos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
          // ascenceur.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

           ascenceur.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            ascenceur.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

             // texteapropos.setBorder(new Border
             papropos.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
             papropos.add(ascenceur);
             papropos.add(bcredits);
             papropos.add(blicence);
             blicence.setVisible(true);
             papropos.add(bfermer);
             bfermer.setVisible(true);
/*******************************************************************/

         principal.setLayout(new BorderLayout());

          //  principal.add(bgo); //ajout du bouton de d�marrage au panel principal
    principal.add(pboutonjeu,BorderLayout.NORTH);
    principal.add(panelanim,BorderLayout.CENTER);

    principal.setVisible(true);
    pboutonjeu.setVisible(false);
    getContentPane().add(principal);
   // pnotes.setMaximumSize(new Dimension(20,40));

       panelanim.setVisible(true);
       panelanim.setBackground(Color.white);









     piano = new Piano();

     Toolkit toolkit = Toolkit.getDefaultToolkit()  ;


     icone = toolkit.getImage(getClass().getClassLoader().getResource("images/icon.png"));





     //     songo = getAudioClip(getCodeBase(),"sound/go.wav");
      //sonerreur = getAudioClip(getCodeBase(),"sound/erreur.wav");
      //sonjuste = getAudioClip(getCodeBase(),"sound/juste.wav");


        //principal.setFocusable(true);


      addKeyListener(this);
      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent e) {

        if(ecranjeu==1){

              Key key = piano.getKey(e.getPoint());

              if (piano.Getprevkey() != null && piano.Getprevkey() != key) {
                  piano.Getprevkey().off(cc,cson.isSelected() & !erreurmidi);
              }
              if (key != null && piano.Getprevkey() != key) {
                  key.on(cc,cson.isSelected() & !erreurmidi);
              }
              piano.Setprevkey(key);
              repaint();

        }
      }

      });
      addMouseListener(this);

      addWindowListener(this);

    // afficheportee(g);
     // affichecle(g);
     // affichescore(g,score);

      // copie l'image dans le buffer
     // buffer=createImage(getSize().width,getSize().height);
     // bufferg=buffer.getGraphics();

     setIconImage(icone);

     langue="en";
     updateLang();



      }


      public void close() {
            if (syn != null) {
                syn.close();
            }

            syn = null;
            instr = null;
            channels = null;
          if (inputDevice.isOpen()) inputDevice.close();
          inputDevice = null;
        }


//**************** METHODES D'ACTION DES BOUTONS ET CHOICES


      // INITIALISE JEU  en cas de modification des paramètres le jeu redemarre

      public void initialisejeu() {

        parti = false;
        score = 100;
        nberreur = 0;
        nbjuste = 0;
        precedente = 0;
        resultat = "INDETERMINE";
        precedente = 0;
        dportee = 110;
        // stopson();

        if ( (type2 == "NOTES" | type2 == "ALTERATIONS") & !toutesnotes) {
          if (clecourante == "sol") {
            piano.Setpositionbase2(0);
            piano.Setpositionbase1(30 - basesol / 5);
          }
          else if (clecourante == "fa") {
            piano.Setpositionbase1(0);
            piano.Setpositionbase2(18 - basefa / 5);
          }
          else if (clecourante == "sol+fa") {
            piano.Setpositionbase1(30 - basesol / 5);
            piano.Setpositionbase2(18 - basefa / 5);
          }
        }
        else {
          piano.Setpositionbase1(0);
          piano.Setpositionbase2(0);
        }

        if (type == "NORMAL") {
          marge = 220;
          repaint();
        }

        else if (type == "LIGNE") {
          marge = 30;
          repaint();
        }
      }

       public void initialisejeu2() {

         parti = false;
         positionr = -1;
         dportee = 100;
         marge = 50;

         if (sm_sequencer != null) {

           sm_sequencer.stop();
         }
       }

       public void stopjeux(){
         if (ecranjeu == 1) initialisejeu();
         else if (ecranjeu == 2)initialisejeu2();
       }

       public void lancementjeu2() {
         initialisejeu2(); // arret du jeu pr�c�dent
         String stmp = "";

         creationligner();

         try {
           sm_sequencer = MidiSystem.getSequencer();
         }
         catch (MidiUnavailableException e) {
           e.printStackTrace();
           System.exit(1);
         }
         if (sm_sequencer == null) {
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

         if (! (sm_sequencer instanceof Synthesizer)) {

           try {
             sm_synthesizer = MidiSystem.getSynthesizer();
             sm_synthesizer.open();
             Receiver synthReceiver = sm_synthesizer.getReceiver();
             Transmitter seqTransmitter = sm_sequencer.getTransmitter();
             seqTransmitter.setReceiver(synthReceiver);
           }
           catch (MidiUnavailableException e) {
             e.printStackTrace();
           }
         }

         sm_sequencer.addMetaEventListener(this);

         sm_sequencer.setTempoInBPM(tempo);

         sm_sequencer.start();

         Track[] tracks = sequence.getTracks();

         for (int i = 0; i < tracks.length; i++) {

           sm_sequencer.setTrackMute(i, !cson.isSelected());
         }

         parti = true; // d�part du jeu
       }



         public void lancementjeu(){
           initialisejeu();     // arret du jeu précédent
           String stmp = "";



           if (tonalitehasard)  { // pour le changement de tonalite à chaque lancement
             int i = (int)Math.round((Math.random()*7));
             double tmp = Math.random();
             if (tmp<0.1) stmp = "";
             else if (tmp >= 0.1 & tmp<0.6) stmp = "#";
             else stmp = "b";


          tcourante.init(i,stmp);
          }
          if (typeinterv == "RANDOM")  intervrandom = (int)Math.round((Math.random()*6))+1;

          if ((type2 == "ACCORDS" | type2 == "INTERVALLES")& tcourante.getNbalt() == 0){
            // pour le changement d'alteration en DOM
          double tmp = Math.random();
          if (tmp<0.5) stmp = "#";
          else stmp = "b";
          tcourante.init(0,stmp);
          }

           if (type == "NORMAL") {
             if (type2 == "NOTES" | type2 == "ALTERATIONS") {nouvellenote();}
             else if (type2 == "ACCORDS") {nouvelaccord();}
             else if (type2 == "INTERVALLES") {nouvelintervalle();}
           }
           else if (type == "LIGNE") creationligne();

          // if (cson.getState()) songo.play();

          // main.start();        // lance le thread pour l'animation
          parti = true;        // d�part du jeu
         }

         public void reponsejuste(){
           nbjuste ++;

           if (type2 == "NOTES" | type2 == "ALTERATIONS") {
               if (score+10<500) {score = score + 10;}
               else {score = 500; resultat = "GAGNE"; parti = false; afficheresultat();}}
           else if (type2 == "ACCORDS" | type2 == "INTERVALLES") {
                   if  (score+5<500) {score = score + 5;}
             else {score = 500; resultat = "GAGNE"; parti = false; afficheresultat();};}

           if(type == "LIGNE" & position == ligne.length-1) { // dernière note trouvée
                      resultat = "GAGNE";
                      parti = false;
                      afficheresultat();
                     };
           if (type2 == "ACCORDS" | type2 == "INTERVALLES") {
                       notesuivante();
          }
          else nouvellenote();
         }

         public void reponsefausse(){
           alterationok = false;

           nberreur++;
          // if (cson.getState()) sonerreur.play();

           if (type2 == "NOTES" | type2 == "ALTERATIONS") {
             if(score-20<=0) {score = 0; resultat = "PERDU"; parti =  false; afficheresultat();  }
             else {score = score - 20;}}
           else if (type2 == "ACCORDS" | type2 == "INTERVALLES") {
             if(score-10<=0) {score = 0; resultat = "PERDU"; parti =  false; afficheresultat();}
             else {score = score - 10;}}
           }


/************** FONCTIONS POUR SAISIE AU CLAVIER */
           public void keyTyped(KeyEvent evt) {
            if (ecranjeu==1 & parti & type2 == "NOTES"){
                char ch = evt.getKeyChar();  // The character typed.
              //  System.out.println(ch);

                if (((langue == "fr" & (ch == 'Q' || ch == 'q'))
                    || ((langue == "en" || langue == "es" || langue == "de") & (ch == 'A' || ch == 'a')))
                  &  ncourante.getNom() == DO) {
                       reponsejuste();

                }
                else if ((ch == 'S' || ch == 's') & ncourante.getNom() == RE){
                  reponsejuste();
                }
                else if ((ch == 'D' || ch == 'd') & ncourante.getNom() == MI){
                  reponsejuste();
                }
                else if ((ch == 'F' || ch == 'f') & ncourante.getNom() == FA){
                  reponsejuste();
                }
                else if ((ch == 'G' || ch == 'g') & ncourante.getNom() == SOL){
                  reponsejuste();
                }
                else if ((ch == 'H' || ch == 'h') & ncourante.getNom() == LA){
                  reponsejuste();
                }
                else if ((ch == 'J' || ch == 'j') & ncourante.getNom() == SI){
                  reponsejuste();
                }
                else if ((ch == 'K' || ch == 'k') & ncourante.getNom() == DO){
                  reponsejuste();
                }
                else  { reponsefausse();

                }
             }
             }  // end keyTyped()






public void mousePressed(MouseEvent e) {
 requestFocus();
 Key key;
 if (ecranjeu==1) {
   key = piano.getKey(e.getPoint());
   piano.Setprevkey(key);
   if (key != null)
     if (key.Getknum() == 60 & !parti)
       lancementjeu();
     else if (key != null & parti) {
       key.on(cc, cson.isSelected() & !erreurmidi);
       repaint();

       if (key.Getknum() == ncourante.getPitch())
         reponsejuste();
       else
         reponsefausse();
     }
 }
}


public void mouseReleased(MouseEvent e) {
 if (ecranjeu==1) {
   if (piano.Getprevkey() != null) {
     piano.Getprevkey().off(cc, cson.isSelected() & !erreurmidi);
     repaint();
   }
 }
}

public void mouseExited(MouseEvent e) {
 if (ecranjeu==1) {
   if (piano.Getprevkey() != null) {
     piano.Getprevkey().off(cc, cson.isSelected() & !erreurmidi);
     repaint();
     piano.Setprevkey(null);
   }
 }
}

public void mouseClicked(MouseEvent e) {}

public void mouseEntered(MouseEvent e) {}

public void keyPressed(KeyEvent evt) {

 // Called when the user has pressed a key, which can be
 // a special key such as an arrow key.
 if (ecranjeu==1 & !parti & (type2 == "NOTES" | type2 == "ALTERATIONS") &
     !toutesnotes) {
   int key = evt.getKeyCode(); // keyboard code for the key that was pressed

   if (key == KeyEvent.VK_LEFT) {

     if (clecourante == "sol" & basesol < 45){
       basesol = basesol + 5;
       piano.Setpositionbase1(piano.Getpositionbase1() - 1);
     }
     //jusqu'� do
     else if (clecourante == "fa" & basefa < 55) {
       basefa = basefa + 5; //fa = 17kw
       piano.Setpositionbase2(piano.Getpositionbase2() - 1);
     }
     else if (clecourante == "sol+fa" & basesol < 35) {
       basefa = basefa - 5;
       basesol = basesol + 5;
       piano.Setpositionbase1(piano.Getpositionbase1()-1);
       piano.Setpositionbase2(piano.Getpositionbase2()+1);

     }

     repaint();
   }
   else if (key == KeyEvent.VK_RIGHT) {

     if (clecourante == "sol" & basesol > -25) {
       basesol = basesol - 5; //jusqu'� do
       piano.Setpositionbase1(piano.Getpositionbase1()+1);
     }

     if (clecourante == "fa" & basefa > -15){
       basefa = basefa - 5; //fa = 17kw
       piano.Setpositionbase2(piano.Getpositionbase2()+1);
     }
     else if (clecourante == "sol+fa" & basesol > -25) {
       basefa = basefa + 5;
       basesol = basesol - 5;
       piano.Setpositionbase1(piano.Getpositionbase1()+1);
        piano.Setpositionbase2(piano.Getpositionbase2()-1);
     }
    repaint();
   }
 }

} // end keyPressed()

public void keyReleased(KeyEvent evt) {
 // empty method, required by the KeyListener Interface
}

public void actionPerformed(ActionEvent e) {
 Graphics g = getGraphics();
 int x;

 if (e.getSource() ==rblangueen) {
   langue = "en";
   updateLang();
 }

 if (e.getSource() ==rblanguede) {
   langue = "de";
   updateLang();
 }

 if (e.getSource() ==rblanguees) {
   langue = "es";
   updateLang();
 }


 if (e.getSource() ==rblanguefr) {
  langue = "fr";
  updateLang();
}


 else if (e.getSource() == prefs) {
    stopjeux();
    sauvegardeprefs();
   preferences.setContentPane(ppref);
   preferences.setSize(400, 330);

   preferences.setLocationRelativeTo(this);
   preferences.setVisible(true);

 }

 else if (e.getSource() == aidesommaire) {
   stopjeux();
       JOptionPane.showMessageDialog(this,
          "Not yet available",
         "Information",
         JOptionPane.INFORMATION_MESSAGE);


      }
 else if (e.getSource() == siteinternet) {
   stopjeux();
   BareBonesBrowserLaunch.openURL("http://jalmus.net");
 }

/* else if (e.getSource() == paypal) {
   stopjeux();
   BareBonesBrowserLaunch.openURL("https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=cvrichard%40infonie%2efr&item_name=Suport%20Jalmus&no_shipping=2&no_note=1&tax=0&currency_code=EUR&bn=PP%2dDonationsBF&charset=UTF%2d8");
 }*/


 else if (e.getSource() == okpref) {

  if (!cronde.isSelected() & !cblanche.isSelected() & !cnoire.isSelected() & !ccroche.isSelected()) {
    JOptionPane.showMessageDialog(this,
    "Au moins un type de rythme doit être sélectionné.",
    "Warning",
    JOptionPane.WARNING_MESSAGE);

  }
  else {
    nivcourant.majniveau(cronde.isSelected(),cblanche.isSelected(),cnoire.isSelected(),ccroche.isSelected(),csilence.isSelected());
    preferences.setVisible(false);
  }
}

else if (e.getSource() == cancelpref){
  restaureprefs();
   preferences.setVisible(false);
}


 else if (e.getSource() == midi) {
    stopjeux();
    sauvegardemidi();
   dmidi.setContentPane(pmidi);
   dmidi.setSize(380, 250);
   dmidi.setLocationRelativeTo(this);
   dmidi.setVisible(true);
   dmidi.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
   dmidi.setResizable(false);
 }

 else if (e.getSource() == okmidi) {
    dmidi.setVisible(false);

}

else if (e.getSource() == cancelmidi){
  restauremidi();
   dmidi.setVisible(false);
}

 else if (e.getSource() == bpref) {


   if (ecranjeu == 1) {
     initialisejeu();
   }
   else if (ecranjeu == 2) {
     tabpref.setSelectedComponent(pprefjeu2);
     initialisejeu2();
   }
   prefs.doClick();
 }

 else if (e.getSource() == quitter) {
    stopjeux();
   this.dispose();
 }

 else if (e.getSource() == bfermer) {
   dapropos.setVisible(false);
 }


 else if (e.getSource() == propos) {
    stopjeux();
  dapropos.setContentPane(papropos);
  texteapropos.setText(tcredits);
  //papropos.paintComponent(g);
  papropos.setVisible(true);
  dapropos.setSize(400, 330);
  dapropos.setLocationRelativeTo(this);
  dapropos.setVisible(true);

}

else if (e.getSource() == blicence) {
  texteapropos.setText(tlicence);

}

else if (e.getSource() == bcredits) {
  texteapropos.setText(tcredits);
}

 else if (e.getSource() == lectnote) {
   stopjeux();
   initialisejeu();
   pboutonjeu.setVisible(true);
   pnotes.setVisible(true);

   principal.setVisible(true);

   if (ecranjeu == 0) {
    // main.start();
   }
   ecranjeu = 1;
 }
 else if (e.getSource() == lectrythme) {
   stopjeux();
   initialisejeu2();
   pboutonjeu.setVisible(true);
   pnotes.setVisible(false);

   principal.setVisible(true);


   if (ecranjeu == 0) {
     //main.start();
   }
   ecranjeu = 2;

 }





          if (e.getSource()==bgo) {  // POUR DEMARRAGE DU PROGRAMME
            if (ecranjeu == 1){
               this.requestFocus();
              lancementjeu();
                  if (!renderingThread.isAlive()) renderingThread.start();

            }
          else if (ecranjeu ==2) lancementjeu2();
            }


            //  SI LE LABEL DU BOUTON SELECTIONNE EST EGAL A LA NOTE COURANTE   ----> GAGNE

            else  if (parti & ecranjeu == 1) {


               if (ncourante.getAlteration() != "") {  // NOTES AVEC ALTERATION
                  if (((JButton)e.getSource()).getText()== ncourante.getAlteration()){
                  alterationok = true;}
                  else if (alterationok & ((JButton)e.getSource()).getText()==ncourante.getNom())
                    reponsejuste();
                 else reponsefausse();
               }


              else if (ncourante.getAlteration() == "") { // NOTE SANS ALTERATION
                System.out.println(ncourante.getNom());
                   if (((JButton)e.getSource()).getText()== ncourante.getNom()){
                     System.out.println( ( (JButton) e.getSource()).getText());
                     reponsejuste();
                   }
              else reponsefausse();
              }

            }
            repaint();
          }

          public void windowOpened(java.awt.event.WindowEvent evt){

          }
          public void windowClosed(java.awt.event.WindowEvent evt){
             dispose();
             System.exit(0) ;

          }
          public void windowIconified(java.awt.event.WindowEvent evt){

          }

          public void windowDeiconified(java.awt.event.WindowEvent evt){

                    }
                    public void windowActivated(java.awt.event.WindowEvent evt){

                  }

                  public void windowDeactivated(java.awt.event.WindowEvent evt){

                                    }





                  public void windowClosing(java.awt.event.WindowEvent evt) {
                          dispose();
                          System.exit(0) ;

                  }

    public void sauvegardeprefs(){
      sauvprefs[0] = btype.getSelectedIndex();
      sauvprefs[1] = bvitesse.getSelectedIndex();
      sauvprefs[2] = bcle.getSelectedIndex();
      sauvprefs[4] = btonalite.getSelectedIndex();
      sauvprefs[5] = bvitesse.getSelectedIndex();
      sauvprefs[6] = bgroupes.getSelectedIndex();
      if (bgroupes.getSelectedIndex()==0 | bgroupes.getSelectedIndex()==1 ) sauvprefs[7] = bselectnotes.getSelectedIndex();
      else if (bgroupes.getSelectedIndex()==2 ) sauvprefs[7] = bselectint.getSelectedIndex();
      else if (bgroupes.getSelectedIndex()==3 ) sauvprefs[7] = bselectacc.getSelectedIndex();
      sauvprefs[8] = btype2.getSelectedIndex();
      sauvprefs[9] = bvitesse2.getSelectedIndex();
      if (cronde.isSelected()) sauvprefs[10] = 1; else sauvprefs[10] = 0;
      if (cblanche.isSelected()) sauvprefs[11] = 1; else sauvprefs[11] = 0;
      if (cnoire.isSelected()) sauvprefs[12] = 1; else sauvprefs[12] = 0;
      if (ccroche.isSelected()) sauvprefs[13] = 1; else sauvprefs[13] = 0;
      if (csilence.isSelected()) sauvprefs[14] = 1; else sauvprefs[14] = 0;
      if (cmetronome.isSelected()) sauvprefs[15] = 1; else sauvprefs[15] = 0;

    }


    public void restaureprefs(){

       btype.setSelectedIndex(sauvprefs[0]);
       bvitesse.setSelectedIndex(sauvprefs[1]) ;
       bcle.setSelectedIndex(sauvprefs[2]) ;
      btonalite.setSelectedIndex(sauvprefs[4]) ;
       bvitesse.setSelectedIndex(sauvprefs[5]) ;
       bgroupes.setSelectedIndex(sauvprefs[6]) ;
      if (bgroupes.getSelectedIndex()==0 | bgroupes.getSelectedIndex()==1 )  bselectnotes.setSelectedIndex(sauvprefs[7]);
      else if (bgroupes.getSelectedIndex()==2 )  bselectint.setSelectedIndex(sauvprefs[7]);
      else if (bgroupes.getSelectedIndex()==3 )  bselectacc.setSelectedIndex(sauvprefs[7]);
      btype2.setSelectedIndex(sauvprefs[8]);
      bvitesse2.setSelectedIndex(sauvprefs[9]);
      if (sauvprefs[10] == 1) cronde.setSelected(true); else cronde.setSelected(false);
      if (sauvprefs[11] == 1) cblanche.setSelected(true); else cblanche.setSelected(false);
      if (sauvprefs[12] == 1) cnoire.setSelected(true); else cnoire.setSelected(false);
      if (sauvprefs[13] == 1) ccroche.setSelected(true); else ccroche.setSelected(false);
      if (sauvprefs[14] == 1) csilence.setSelected(true); else csilence.setSelected(false);
      if (sauvprefs[10] == 1) cmetronome.setSelected(true); else cmetronome.setSelected(false);

    }


    public void sauvegardemidi(){
   if (cson.isSelected()) sauvmidi[0] = 1; else sauvmidi[0] = 0;
   sauvmidi[1] = binstr.getSelectedIndex();
   sauvmidi[2] = bmidiin.getSelectedIndex();
   if (cexacte.isSelected()) sauvmidi[3] = 1; else sauvmidi[3] = 0;


 }


 public void restauremidi(){
   if (sauvmidi[0] == 1) cson.setSelected(true); else cson.setSelected(false);
    binstr.setSelectedIndex(sauvmidi[1]);
    bmidiin.setSelectedIndex(sauvmidi[2]) ;
    if (sauvmidi[3] == 1) cexacte.setSelected(true); else cexacte.setSelected(false);

 }


    public void itemStateChanged(ItemEvent evt){
      Graphics g=getGraphics();






      String smidiin;





    if (evt.getItemSelectable() == bmidiin & !selectmidi_forlang){
        smidiin = (String) bmidiin.getSelectedItem();
        if (smidiin != pasclavier & !open )
                        {
        message = "Initialisation " + smidiin;

                info = MidiCommon.getMidiDeviceInfo(smidiin, false);
                  if (info == null){

                  message = "nodevice";
                  System.out.println(message);
                }

                else {

                try
                {
                inputDevice = MidiSystem.getMidiDevice(info);
                inputDevice.open();

               // open = true;
                }
                catch (MidiUnavailableException e)
                                      {
                                          message = "nodevice";
                                          System.out.println(message);
                                      }


                Receiver	r = new DumpReceiver();
                      try
                                {
                                        Transmitter	t = inputDevice.getTransmitter();
                                        t.setReceiver(r);
                                }
                        catch (MidiUnavailableException e)
                                {
                                        message ="wasn't able to connect the device's Transmitter to the Receiver:";
                                        System.out.println(e);
                                        inputDevice.close();
                                        System.exit(1);
                                }
                        message = "Fin initialisation";
                        }
                       if (inputDevice.isOpen())
                         System.out.println("Midi Device open : jouez une touche, si celle-ci ne change pas de couleur � l'�cran, vérifiez le nom du port");
                       open = true;
                     }
        }
          // else if (inputDevice.isOpen()) inputDevice.close();

          else if (evt.getItemSelectable() == binstr){
                     if(!erreurmidi){

                       cc.getchannel().programChange(binstr.getSelectedIndex());
                     }
             }

          else if (evt.getItemSelectable() == bcle){
            if (bcle.getSelectedIndex()==0){
              initialisejeu();
              clecourante = "sol";
              repaint();
            }
            else if (bcle.getSelectedIndex()==1) {
              initialisejeu();
              clecourante = "fa";
              repaint();
            }
            else if (bcle.getSelectedIndex()==2) {
              initialisejeu();
              basesol = 25; // hauteur de la note de base choisie par l'utilisateur 20 = sol 4 = fa
              basefa = 5;
              clecourante = "sol+fa";
              repaint();
            }
          }


      // CHOIX ARMURE

      else if (evt.getItemSelectable() == btonalite){
        initialisejeu();
        String stmp = "";

        if (btonalite.getSelectedIndex()==0) {
          if (type2 == "NOTES"){
          bdiese.setVisible(false);
          bdiese2.setVisible(false);
          bbemol.setVisible(false);
          bbemol2.setVisible(false);
          pnotes.validate();
          tcourante.init(0,"");
          }

          else if (type2 == "ALTERATIONS") tcourante.init(0,"");
          else if (type2 == "ACCORDS" | type2 == "INTERVALLES"){

           double tmp = Math.random();
           if (tmp<0.5) stmp = "#";
           else stmp = "b";
           tcourante.init(0,stmp);
            }
           tonalitehasard = false;
        }

        else {
          bdiese.setVisible(true);
          bdiese2.setVisible(true);
          bbemol.setVisible(true);
          bbemol2.setVisible(true);
          pnotes.validate();

          if (btonalite.getSelectedIndex()==15) {
            // choix de la tonalite au hasard au lancement du jeu
          tonalitehasard = true;
          tcourante.init(0,"");
          }


          else {
             tonalitehasard = false;
            if (btonalite.getSelectedIndex()==1)  tcourante.init(1,"#");
            else if (btonalite.getSelectedIndex()==2)  tcourante.init(2,"#");
            else if (btonalite.getSelectedIndex()==3)  tcourante.init(3,"#");
            else if (btonalite.getSelectedIndex()==4)  tcourante.init(4,"#");
            else if (btonalite.getSelectedIndex()==5)  tcourante.init(5,"#");
            else if (btonalite.getSelectedIndex()==6)  tcourante.init(6,"#");
            else if (btonalite.getSelectedIndex()==7)  tcourante.init(7,"#");
            else if (btonalite.getSelectedIndex()==8)  tcourante.init(1,"b");
            else if (btonalite.getSelectedIndex()==9)  tcourante.init(2,"b");
            else if (btonalite.getSelectedIndex()==10)  tcourante.init(3,"b");
            else if (btonalite.getSelectedIndex()==11)  tcourante.init(4,"b");
            else if (btonalite.getSelectedIndex()==12)  tcourante.init(5,"b");
            else if (btonalite.getSelectedIndex()==13)  tcourante.init(6,"b");
            else if (btonalite.getSelectedIndex()==14)  tcourante.init(7,"b");



            }
        }

        repaint();
      }
      // CHOIX DU JEU
      else if (evt.getItemSelectable() == btype){
        if (btype.getSelectedIndex()==0) {initialisejeu(); type = "NORMAL"; marge = 220; repaint();}
        else if (btype.getSelectedIndex()==1) {initialisejeu(); type = "LIGNE"; marge = 30; repaint();};
      }

      // CHOIX DE LA VITESSE
      else if (evt.getItemSelectable() == bvitesse){
        if (bvitesse.getSelectedIndex()==0) {initialisejeu(); vitesse = 24; repaint();}
        else  if (bvitesse.getSelectedIndex()==1) {initialisejeu(); vitesse = 20; repaint();}
        else  if (bvitesse.getSelectedIndex()==2) {initialisejeu(); vitesse = 16; repaint();}
        else  if (bvitesse.getSelectedIndex()==3) {initialisejeu(); vitesse = 12; repaint();}
        else  if (bvitesse.getSelectedIndex()==4) {initialisejeu(); vitesse = 8; repaint();}
       // System.out.println("-"+vitesse);
      }


      // CHOIX DE LA VITESSE
      // CHOIX DE LA tempo
             else if (evt.getItemSelectable() == bvitesse2) {
               if (bvitesse2.getSelectedIndex()==0) {
                 initialisejeu();
                 tempo = 40;
                 repaint();
               }
               else if (bvitesse2.getSelectedIndex()==1) {
                 initialisejeu();
                 tempo = 60;
                 repaint();
               }
               else if (bvitesse.getSelectedIndex()==2) {
                 initialisejeu();
                 tempo = 110;
                 repaint();
               }
               else if (bvitesse.getSelectedIndex()==3) {
                 initialisejeu();
                 tempo = 120;
                 repaint();
               }
               else if (bvitesse.getSelectedIndex()==4) {
                 initialisejeu();
                 tempo = 160;
                 repaint();
               }

             }




      // CHOIX DU NOMBRE DE NOTES
     else if (evt.getItemSelectable() == bgroupes){

       if(bgroupes.getSelectedIndex()==0 & type2 != "NOTES"){
         initialisejeu();
         type2 = "NOTES";

         ppref3jeu1.removeAll();
         ppref3jeu1.add(bgroupes);
         ppref3jeu1.add(bselectnotes);
         ppref3jeu1.repaint();

         initialisejeu();
         nbnotessur = 1;
         nbnotessous = 1;
         nbnotessurfa = 1;
         nbnotessousfa = 1;
         repaint();

         if (tcourante.getAlteration().equals("")) {
           repaint();
           bdiese.setVisible(false);
           bdiese2.setVisible(false);
           bbemol.setVisible(false);
           bbemol2.setVisible(false);
           pnotes.validate();
           repaint();
         }
       }



       if (bgroupes.getSelectedIndex()==1 & type2 != "ALTERATIONS") {
         initialisejeu();
         type2 = "ALTERATIONS";
         if (tcourante.getNbalt() == 0) tcourante.setAlteration("");

         ppref3jeu1.removeAll();
           ppref3jeu1.add(bgroupes);
           ppref3jeu1.add(bselectnotes);
           ppref3jeu1.repaint();

           initialisejeu();
           nbnotessur = 1; nbnotessous = 1;
           nbnotessurfa = 1; nbnotessousfa = 1;
           repaint();

         bdiese.setVisible(true);
         bdiese2.setVisible(true);
         bbemol.setVisible(true);
         bbemol2.setVisible(true);
         pnotes.validate();
       }

       else  if (bgroupes.getSelectedIndex()==2) {
            //initialisetabaccords();
            initialisejeu();
            type2 = "INTERVALLES";
            basesol = 25; // hauteur de la note de base choisie par l'utilisateur 20 = sol 4 = fa
            basefa = 5;
            bdiese.setVisible(true);
            bdiese2.setVisible(true);
            bbemol.setVisible(true);
            bbemol2.setVisible(true);

            if (tcourante.getAlteration() == ""){
              double tmp = Math.random();
              if (tmp<0.5) tcourante.init(0,"#");
              else tcourante.init(0,"b");
            }


           ppref3jeu1.removeAll();
          ppref3jeu1.add(bgroupes);
          ppref3jeu1.add(bselectint);
          ppref3jeu1.repaint();

       }
       else  if (bgroupes.getSelectedIndex()==3) {
         //initialisetabaccords();
         initialisejeu();
         basesol = 25; // hauteur de la note de base choisie par l'utilisateur 20 = sol 4 = fa
          basefa = 5;

         type2 = "ACCORDS";
         bdiese.setVisible(true);
         bdiese2.setVisible(true);
         bbemol.setVisible(true);
         bbemol2.setVisible(true);



         ppref3jeu1.removeAll();
          ppref3jeu1.add(bgroupes);
          ppref3jeu1.add(bselectacc);
          ppref3jeu1.repaint();


         if (tcourante.getAlteration() == ""){
            double tmp = Math.random();
            if (tmp<0.5) tcourante.init(0,"#");
            else tcourante.init(0,"b");
         }

       }



     }



       else if (evt.getItemSelectable() == bselectnotes){
        if (bselectnotes.getSelectedIndex()==0) {
          initialisejeu();
          toutesnotes = false;
          nbnotessur = 1; nbnotessous = 1;
          nbnotessurfa = 1; nbnotessousfa = 1;
          repaint();
          }
        else  if (bselectnotes.getSelectedIndex()==1) {
          initialisejeu();
          toutesnotes = false;
          nbnotessur = 2; nbnotessous = 2;
          nbnotessurfa = 2; nbnotessousfa = 2;
          repaint();
          }
        else  if (bselectnotes.getSelectedIndex()==2) {
          initialisejeu();
          toutesnotes = false;
          nbnotessur = 3; nbnotessous = 3;
          nbnotessurfa = 3; nbnotessousfa = 3;
          repaint();
          }
        else  if (bselectnotes.getSelectedIndex()==3) {
          initialisejeu();
          toutesnotes = false;
          nbnotessur = 4; nbnotessous = 4;
          nbnotessurfa = 4; nbnotessousfa = 4;
          repaint();
          }

        else  if (bselectnotes.getSelectedIndex()==4) {
          initialisejeu();
          basesol = 25; // hauteur de la note de base choisie par l'utilisateur 20 = sol 4 = fa
          basefa = 5;

          toutesnotes = true;
          if (clecourante == "sol") {nbnotessur = 14; nbnotessous = 8;}
          else if (clecourante == "fa") {nbnotessur = 8; nbnotessous = 14;}
          else if (clecourante == "sol+fa") {
          nbnotessur = 14; nbnotessous = 6;
          nbnotessurfa = 6; nbnotessousfa = 14;
          }  // inversement pour cl� de fa
          repaint();
          }
       }
         else if (evt.getItemSelectable() == bselectacc){
         if (bselectacc.getSelectedIndex()==0)  {initialisejeu(); typeaccord = "SIMPLE"; repaint();}
      else  if (bselectacc.getSelectedIndex()==1)  {initialisejeu(); typeaccord = "RENV"; repaint();}
         }

         else if (evt.getItemSelectable() == bselectint){

       if (bselectint.getSelectedIndex()==0)  {initialisejeu(); typeinterv = "SECONDES"; repaint();}
      else  if (bselectint.getSelectedIndex()==1)  {initialisejeu(); typeinterv = "TIERCES"; repaint();}
      else  if (bselectint.getSelectedIndex()==2)  {initialisejeu(); typeinterv = "QUARTES"; repaint();}
      else  if (bselectint.getSelectedIndex()==3)  {initialisejeu(); typeinterv = "QUINTES"; repaint();}
      else  if (bselectint.getSelectedIndex()==4)  {initialisejeu(); typeinterv = "SIXTES"; repaint();}
      else  if (bselectint.getSelectedIndex()==5)  {initialisejeu(); typeinterv = "SEPTIEMES"; repaint();}
      else  if (bselectint.getSelectedIndex()==6)  {initialisejeu(); typeinterv = "OCTAVES"; repaint();}
      else  if (bselectint.getSelectedIndex()==7)  {initialisejeu(); typeinterv = "RANDOM"; repaint();}
      else  if (bselectint.getSelectedIndex()==8)  {initialisejeu(); typeinterv = "TOUS"; repaint();}


       }


        }





//**************************** METHODES DE TRADUCTION
         public void updateLang() {




           bundle = ResourceBundle.getBundle("language", new Locale(langue));
           changeLanguage();

         }


       public void changeLanguage() {
         jeu.setText(bundle.getString("_menuGame"));
         lectnote.setText(bundle.getString("_menuNotereading"));
         lectrythme.setText(bundle.getString("_menuRythmreading"));
         quitter.setText(bundle.getString("_menuExit"));

         parametres.setText(bundle.getString("_menuSettings"));
         prefs.setText(bundle.getString("_menuPreferences"));
         preferences.setTitle(bundle.getString("_menuPreferences"));
         midi.setText(bundle.getString("_menuMidi"));
         dmidi.setTitle(bundle.getString("_menuMidi"));
         langues.setText(bundle.getString("_menuLanguage"));
         aide.setText(bundle.getString("_menuHelp"));
         aidesommaire.setText(bundle.getString("_menuContents"));
         siteinternet.setText(bundle.getString("_menuWeb"));
         propos.setText(bundle.getString("_menuAbout"));
         dapropos.setTitle(bundle.getString("_menuAbout"));


         tlicence = bundle.getString("_licence");
         tcredits = bundle.getString("_credits");

         bcle.removeAllItems();
          bcle.addItem(bundle.getString("_trebleclef"));
         bcle.addItem(bundle.getString("_bassclef"));
         bcle.addItem(bundle.getString("_bothclefs"));

         bgroupes.removeAllItems();
         bgroupes.addItem(bundle.getString("_notes"));
         bgroupes.addItem(bundle.getString("_alterednotes"));
         bgroupes.addItem(bundle.getString("_intervals"));
         bgroupes.addItem(bundle.getString("_chords"));

         btype.removeAllItems();
         btype.addItem(bundle.getString("_normalgame"));
         btype.addItem(bundle.getString("_linegame"));

         bselectnotes.removeAllItems();
         bselectnotes.addItem("3 "+bundle.getString("_menuNotes"));
     bselectnotes.addItem("5 "+bundle.getString("_menuNotes"));
     bselectnotes.addItem("7 "+bundle.getString("_menuNotes"));
     bselectnotes.addItem("9 "+bundle.getString("_menuNotes"));
     bselectnotes.addItem(bundle.getString("_all"));

     btonalite.removeAllItems();
     btonalite.addItem(bundle.getString("_nosharpflat"));
    btonalite.addItem("1 "+bundle.getString("_sharp"));
    btonalite.addItem("2 "+bundle.getString("_sharp"));
    btonalite.addItem("3 "+bundle.getString("_sharp"));
    btonalite.addItem("4 "+bundle.getString("_sharp"));
    btonalite.addItem("5 "+bundle.getString("_sharp"));
    btonalite.addItem("6 "+bundle.getString("_sharp"));
    btonalite.addItem("7 "+bundle.getString("_sharp"));
    btonalite.addItem("1 "+bundle.getString("_flat"));
    btonalite.addItem("2 "+bundle.getString("_flat"));
    btonalite.addItem("3 "+bundle.getString("_flat"));
    btonalite.addItem("4 "+bundle.getString("_flat"));
    btonalite.addItem("5 "+bundle.getString("_flat"));
    btonalite.addItem("6 "+bundle.getString("_flat"));
    btonalite.addItem("7 "+bundle.getString("_flat"));
    btonalite.addItem(bundle.getString("_random"));



         ppref1jeu1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuGame")),
             BorderFactory.createEmptyBorder(5,5,5,5)));
         ppref2jeu1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuClef")),
             BorderFactory.createEmptyBorder(5,5,5,5)));
         ppref3jeu1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuNotes")),
             BorderFactory.createEmptyBorder(5,5,5,5)));

         ppref1jeu2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuGame")),
               BorderFactory.createEmptyBorder(5,5,5,5)));
         ppref2jeu2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuRythms")),
             BorderFactory.createEmptyBorder(5, 5, 5, 5)));
         ppref3jeu2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(bundle.getString("_menuMetronom")),
               BorderFactory.createEmptyBorder(5, 5, 5, 5)));

         tabpref.setTitleAt(0,bundle.getString("_menuNotereading"));
         tabpref.setTitleAt(1,bundle.getString("_menuRythmreading"));

         seconde = bundle.getString("_second");
         tierce = bundle.getString("_third");
         quarte = bundle.getString("_fourth");
         quinte = bundle.getString("_fifth");
         sixte = bundle.getString("_sixth");
         septieme = bundle.getString("_seventh");
         octave = bundle.getString("_octave");
         mineur = bundle.getString("_minor");
         majeur = bundle.getString("_major");
         diminuee = bundle.getString("_diminished");
         augmentee = bundle.getString("_augmented");
         juste = bundle.getString("_perfect");

         bselectint.removeAllItems();
           bselectint.addItem(seconde);
           bselectint.addItem(tierce);
           bselectint.addItem(quarte);
           bselectint.addItem(quinte);
           bselectint.addItem(sixte);
           bselectint.addItem(septieme);
           bselectint.addItem(octave);
           bselectint.addItem(bundle.getString("_random"));
           bselectint.addItem(bundle.getString("_all"));

           bselectacc.removeAllItems();
           bselectacc.addItem(bundle.getString("_rootposition"));
           bselectacc.addItem(bundle.getString("_inversion"));

           cson.setText(bundle.getString("_sound"));
           cexacte.setText(bundle.getString("_exactnote"));
           cmetronome.setText(bundle.getString("_menuMetronom"));

         //  bmidiin.removeItemAt(0);
         selectmidi_forlang = true;
         model.removeElementAt(0);
        model.insertElementAt(bundle.getString("_nomidiin"),0);
        model.setSelectedItem(model.getElementAt(0));
         selectmidi_forlang = false;

           cronde.setText(bundle.getString("_wholenote"));
           cblanche.setText(bundle.getString("_halfnote"));
           cnoire.setText(bundle.getString("_quarternote"));
           ccroche.setText(bundle.getString("_eighthnote"));
           csilence.setText(bundle.getString("_rest"));


           pmidi1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.
               createTitledBorder(bundle.getString("_sound")),
               BorderFactory.createEmptyBorder(5, 5, 5, 5)));
           pmidi2.setBorder(BorderFactory.createCompoundBorder(BorderFactory.
               createTitledBorder(bundle.getString("_midiclavier")),
               BorderFactory.createEmptyBorder(5, 5, 5, 5)));

           okpref.setText(bundle.getString("_buttonok"));
           cancelpref.setText(bundle.getString("_buttoncancel"));
           okmidi.setText(bundle.getString("_buttonok"));
           cancelmidi.setText(bundle.getString("_buttoncancel"));
           bfermer.setText(bundle.getString("_buttonclose"));
           bcredits.setText(bundle.getString("_buttoncredits"));
           blicence.setText(bundle.getString("_buttonlicense"));
           bgo.setText(bundle.getString("_start"));
           bpref.setText(bundle.getString("_menuPreferences"));

           DO = bundle.getString("_do");
           RE = bundle.getString("_re");
           MI = bundle.getString("_mi");
           FA = bundle.getString("_fa");
           SOL = bundle.getString("_sol");
           LA = bundle.getString("_la");
           SI = bundle.getString("_si");

           bdo.setText(DO);
           bre.setText(RE);
           bmi.setText(MI);
           bfa.setText(FA);
           bsol.setText(SOL);
           bla.setText(LA);
           bsi.setText(SI);
           bdo2.setText(DO);


       }




        //**************************** METHODES D'AFFICHAGE



        //*******VERSION

      public void afficheversion(Graphics g,Color c){
        g.setColor(c);
        g.setFont(new Font("Arial",Font.BOLD,14));
        g.drawString("V 4.10",625,420);
        }




      //*******CLE


      public void affichecle(Graphics g){
        if (ecranjeu == 1){
          if (clecourante.equals("sol"))
            g.drawImage(ti.Getimage(0), marge, dportee - 15, this);
          else if (clecourante.equals("fa"))
            g.drawImage(ti.Getimage(1), marge, dportee, this);
          else if (clecourante.equals("sol+fa")) {
            g.drawImage(ti.Getimage(0), marge, dportee - 15, this);
            g.drawImage(ti.Getimage(1), marge, dportee + 90, this);
          }
        }
        else if (ecranjeu ==2){
          for (int nbportee = 0; nbportee < 3; nbportee++){
                       g.drawImage(ti.Getimage(0), marge, dportee - 16 + nbportee*100, this);
                     }
        }
      }




      //*******PORTEE

      public void afficheportee(Graphics g){
        Dimension d = getSize();
        g.setColor(Color.black);
        int yd  = dportee;


        for(yd=dportee;yd<=dportee+40;yd=yd+10){ //  1ere ligne � 144;   derni�re � 176
          g.drawLine(marge, yd, d.width-marge,yd);
          };

          if (clecourante == "sol+fa"){  // dessine la deuxi�me port�e 72 points en dessous
            for(yd=dportee+90;yd<=dportee+130;yd=yd+10){  //  1ere ligne � 196;   derni�re � 228
              g.drawLine(marge, yd, d.width-marge,yd);
              };
            }
          if (type == "LIGNE") {
            g.setColor(Color.red);
            g.drawLine(marge+98, dportee-30, marge+98, dportee+70);
            if (clecourante == "sol+fa"){ g.drawLine(marge+98, dportee+20, marge+98, dportee+160);}
            }
        }

       public void afficheportee2(Graphics g) {
         Dimension d = getSize();
         g.setColor(Color.black);

         for (int nbportee = 0; nbportee < 3; nbportee++) {
           for (int yd = dportee; yd <= dportee + 40; yd = yd + 10) { //  1ere ligne � 144;   derni�re � 176
             g.drawLine(marge, yd + nbportee * 100, d.width - marge,
                        yd + nbportee * 100);
           }
           ;

           g.drawLine(marge + 240, dportee + nbportee * 100, marge + 240,
                      dportee + nbportee * 100 + 40);
           g.drawLine(marge + 458, dportee + nbportee * 100, marge + 458,
                      dportee + nbportee * 100 + 40);
           g.drawLine(d.width - marge, dportee + nbportee * 100, d.width - marge,
                      dportee + nbportee * 100 + 40);
         }
         g.drawLine(d.width - marge - 3, dportee + 2 * 100, d.width - marge - 3,
                    dportee + 2 * 100 + 40);

       }



      //*******NOTE

        public boolean memenote(int p1, int p2){ //compare deux pitch pour saisie clavier
          int i;
          boolean rep = false;

          for (i = 0;i<=6;i++){
            if((p1>=p2) & (p1-i*12 == p2)) rep = true;
            else if ((p1<p2) & (p1+i*12 == p2)) rep = true;
          }
          return rep;
        }



      public int choixhauteur(){
        int i;
        int h = 0;
        int dessousbase = 0;
        double tmp, tmpcle;


//CHOIX NOTE DE DEPART

        // DEUX CAS : CLES SIMPLES ET CLES DOUBLES


         if (clecourante == "sol" | clecourante == "fa") {

           tmp = Math.random();
           if (tmp<0.5) {  i = (int)Math.round((Math.random()*nbnotessur));}   // nombre entre 0 et le nb de notes au dessus
           else { i = -(int)Math.round((Math.random()*nbnotessous));};          // nombre n�gatif entre le nb de notes en dessous et 0
           if (clecourante == "sol") h = (dportee+basesol)- (i*5); // 20 pour sol
         else h = (dportee+basefa)- (i*5); // 4 pour FA
                                                     //   dportee+20  =  Sol en cl� de sol  128 = Fa en cl� de fa
           }

           // DEUXIEME CAS DOUBLE CLE pour les limites nb notes on utilise l'inverse pour la cl� de FA
          else if (clecourante == "sol+fa"){
            if (nbnotessurfa <0) dessousbase = nbnotessurfa;
            else dessousbase = 0;

            tmpcle = Math.random();
            if (tmpcle<0.5) { // cl� de sol
              tmp = Math.random();
              if (tmp<0.5) {  i = (int)Math.round((Math.random()*nbnotessur));}              // nombre entre 0 et le nb de notes au dessus
              else { i = -(int)Math.round((Math.random()*nbnotessous));};           // nombre n�gatif entre le nb de notes en dessous et 0
              h = dportee+basesol - (i*5);     //   dportee+20  =  Sol en cl� de sol
                                             }

            else {       // cl� de fa
              tmp = Math.random();
              if (tmp<0.5) {
                i = (int)Math.round((Math.random()*nbnotessurfa)+dessousbase);

              }                        // inverse de cl� de sol
              else { i = -(int)Math.round((Math.random()*nbnotessousfa))+dessousbase;};
              //System.out.println(i);
              //System.out.println(tmp);

              h = dportee+basefa+90 - (i*5);  //   dportee+76  =  Fa en cl� de fa
              }
            }
            return h;
          }




          public intervalle choixintervalle(){
                      int i;
                      int h = 0;
                      Note n1;
                      Note n2;

                      String nom = "";
                      intervalle inter;
                      double tmp;

                      if (typeinterv == "SECONDES") i = 1;
                      else if (typeinterv == "TIERCES") i = 2;
                      else if (typeinterv == "QUARTES") i = 3;
                      else if (typeinterv == "QUINTES") i = 4;
                      else if (typeinterv == "SIXTES") i = 5;
                      else if (typeinterv == "SEPTIEMES") i = 6;
                      else if (typeinterv == "OCTAVES") i = 7;
                      else if (typeinterv == "RANDOM") i = intervrandom;

                       else i = (int)Math.round((Math.random()*6))+1; //entre 1 et 7 pour l'octave

                       if (clecourante == "sol+fa") {
                          nbnotessur = 13-i;
                          nbnotessous = 5;
                          nbnotessurfa = 6-i;
                          nbnotessousfa = 10;
                       }
                       else {
                         nbnotessur = 13-i;
                         nbnotessous = 8;
                       }


                          h = choixhauteur();
                          while(h == precedente) h = choixhauteur();
                          tmp = Math.random();
                          //if (tmp<0.2) { n1 = new Note ("#","",h,marge+98,0);}
                          //else if (tmp>= 0.2 & tmp<0.4) {  n1 = new Note ("b","",h,marge+98,0);}
                          //else  n1 = new Note ("","",h,marge+98,0);
                          n1 = new Note ("","",h,marge+98,0);
                          n1.majnote(clecourante,dportee,bundle);
                          n1.majalteration(tcourante, type2,bundle);

                          tmp = Math.random();
                          /*if (n1.getAlteration() == "#"){
                            if (tmp<0.4) { n2 = new Note ("#","",h-i*4,marge+98,0);}
                            else  n2 = new Note ("","",h-i*4,marge+98,0);}
                          else if (n1.getAlteration() == "b"){
                            if (tmp<0.4) {  n2 = new Note ("b","",h-i*4,marge+98,0);}
                            else  n2 = new Note ("","",h-i*4,marge+98,0);}
                          else
                            if (tmp<0.2)  n2 = new Note ("#","",h-i*4,marge+98,0);
                            else if (tmp<0.4)   n2 = new Note ("b","",h-i*4,marge+98,0);
                            else n2 = new Note ("","",h-i*4,marge+98,0);*/

                          n2 = new Note ("","",h-i*5,marge+98,0);
                          n2.majnote(clecourante, dportee,bundle);
                          n2.majalteration(tcourante, type2,bundle);


                          if (n2.getPitch()-n1.getPitch() == 0 & i == 1) nom = bundle.getString("_seconddim");
                          else if (n2.getPitch()-n1.getPitch() == 1 & i == 1) nom = bundle.getString("_secondmin");
                          else if (n2.getPitch()-n1.getPitch() == 2 & i == 1) nom = bundle.getString("_secondmaj");
                          else if (n2.getPitch()-n1.getPitch() == 3 & i == 1) nom = bundle.getString("_secondaug");
                          else if (n2.getPitch()-n1.getPitch() == 2 & i == 2) nom = bundle.getString("_thirddim");
                          else if (n2.getPitch()-n1.getPitch() == 3 & i == 2) nom =  bundle.getString("_thirdmin");
                          else if (n2.getPitch()-n1.getPitch() == 4 & i == 2) nom =  bundle.getString("_thirdmaj");
                          else if (n2.getPitch()-n1.getPitch() == 5 & i == 2) nom =  bundle.getString("_thirdaug");
                          else if (n2.getPitch()-n1.getPitch() == 4 & i == 3) nom = bundle.getString("_fourthdim");
                          else if (n2.getPitch()-n1.getPitch() == 5 & i == 3) nom = bundle.getString("_fourthper");
                          else if (n2.getPitch()-n1.getPitch() == 6 & i == 3) nom = bundle.getString("_fourthaug");
                          else if (n2.getPitch()-n1.getPitch() == 6 & i == 4) nom = bundle.getString("_fifthdim");
                          else if (n2.getPitch()-n1.getPitch() == 7 & i == 4) nom = bundle.getString("_fifthper");
                          else if (n2.getPitch()-n1.getPitch() == 8 & i == 4) nom = bundle.getString("_fifthaug");
                          else if (n2.getPitch()-n1.getPitch() == 7 & i == 5) nom = bundle.getString("_sixthdim");
                          else if (n2.getPitch()-n1.getPitch() == 8 & i == 5) nom = bundle.getString("_sixthmin");
                          else if (n2.getPitch()-n1.getPitch() == 9 & i == 5) nom = bundle.getString("_sixthmaj");
                          else if (n2.getPitch()-n1.getPitch() == 10 & i == 5) nom = bundle.getString("_sixthaug");
                          else if (n2.getPitch()-n1.getPitch() == 9 & i == 6) nom = bundle.getString("_seventhdim");
                          else if (n2.getPitch()-n1.getPitch() == 10 & i == 6) nom = bundle.getString("_seventhmin");
                          else if (n2.getPitch()-n1.getPitch() == 11 & i == 6) nom = bundle.getString("_seventhmaj");
                          else if (n2.getPitch()-n1.getPitch() == 12 & i == 6) nom = bundle.getString("_seventhaug");//inusit�e
                          else if (n2.getPitch()-n1.getPitch() == 11 & i == 7) nom = bundle.getString("_octavedim");
                          else if (n2.getPitch()-n1.getPitch() == 12 & i == 7) nom = bundle.getString("_octaveper");
                          else if (n2.getPitch()-n1.getPitch() == 13 & i == 7) nom = bundle.getString("_octaveaug");

                          inter = new intervalle (n1,n2,nom);
                          precedente = n1.getHauteur();

                          return inter;
                        }


          public void nouvelintervalle(){


                   icourant.copy(choixintervalle());
                   if (type == "NORMAL") {
                     posnote = 0;
                     ncourante = icourant.interv [posnote];
                     if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);
                    }

                    else if (type == "LIGNE") {
                       if (position < ligne.length-1) {
                         position = position + 1;
                         icourant.copy(ligneint[position]);

                         posnote = 0;
                        //acourant.convertir(clecourante,typeaccord);
                        ncourante = icourant.interv [posnote];
                        if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);


                           }
    }
                 }

        public accord choixaccord(){
          int h = 0;
          Note n1 = new Note ("","",0,0,0);
          Note n2 = new Note ("","",0,0,0);
          Note n3 = new Note ("","",0,0,0);
          accord a;
          String minmaj = "";
          String salt = "";
          boolean ok = false;

          if (clecourante == "sol+fa") {
            nbnotessur = 6;
            nbnotessous = 5;
            nbnotessurfa = -2;
            nbnotessousfa = 10;
          }
          else {
            nbnotessur = 6;
            nbnotessous = 8;
          }

          while (!ok){

          h = choixhauteur();
          while(h == precedente) h = choixhauteur();


          n1 = new Note ("","",h,marge+98,0);
          n1.majnote(clecourante, dportee,bundle);
          n1.majalteration(tcourante, type2,bundle);



          n2 = new Note ("","",h-2*5,marge+98,0);
          n2.majnote(clecourante, dportee,bundle);
          n2.majalteration(tcourante,n1.getPitch(),2,bundle);//deuxieme note


          n3 = new Note ("","",h-4*5,marge+98,0);
          n3.majnote(clecourante, dportee,bundle);
          n3.majalteration(tcourante,n1.getPitch(),3,bundle);//troisieme note

          if (n2.getPitch()-n1.getPitch() == 3 & n3.getPitch()-n1.getPitch() == 7) {minmaj = mineur; ok = true;}
          else if (n2.getPitch()-n1.getPitch() == 3 & n3.getPitch()-n1.getPitch() == 6) {minmaj = "dim"; ok = true;}
          else if (n2.getPitch()-n1.getPitch() == 4 & n3.getPitch()-n1.getPitch() == 7) {minmaj = majeur; ok = true;}
          else if (n2.getPitch()-n1.getPitch() == 4 & n3.getPitch()-n1.getPitch() == 8) {minmaj = "aug"; ok = true;}
          else ok = false;


          if (n1.getAlteration() == "n") salt = "";
          else salt = n1.getAlteration();
         // if (!ok) System.out.println("faux"+n1.getNom() +salt);


          }
          a = new accord(n1,n2,n3, n1.getNom() +salt+ " " + minmaj,0);
          precedente = n1.getHauteur();
          return a;

        }





           public void afficheaccord(accord a,Graphics g,boolean accordcourant){
                       Dimension d = getSize();
                     int i;   // compteur


                     if(a.acc[posnote].getX()< d.width-marge & a.acc[posnote].getX() >= marge+98 & parti) {
                       // NOTE DANS LIMITES
                       a.paint(clecourante, tcourante, posnote,type,g,accordcourant, this);
                       //g.drawString("Renv" + a.renvst,100,100);
                     }

                     else {
                       if (type == "NORMAL"){
                         if(score-20<=0) {score = 0; resultat = "PERDU"; parti = false;afficheresultat();}
                         else {
                             score = score - 20;
                           //  if (cson.isSelected()) sonerreur.play();
                             }

                             nouvelaccord();
                                             }
                       else if (type == "LIGNE" & parti){
                        if (type2 == "ACCORDS" & ligneacc [position].acc[0].getX() < marge+98)
                       {  // Si la note courant (sauf la derni�re)d�passe la limite ici marge +25
                           score = 0;
                           resultat = "PERDU";
                           parti =  false;
                           afficheresultat();
                         }
                       }
                     }
      }

     public void Synthnote(int nNoteNumber,int nVelocity,int nDuration)
              {

      /* Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
       syn.loadInstrument(instr[21]);
       mc = syn.getChannels();
       //Thread t = new Thread();*/

      // mc[5].noteOn(nNoteNumber, nVelocity);

       cc.jouenote(!erreurmidi,nNoteNumber);


              }




    public void nouvellenote(){
      double tmp;

      if (type == "NORMAL") {
        if (precedente!=0) stopson();
        ncourante.init();
        ncourante.setHauteur(choixhauteur());
        while(ncourante.getHauteur() == precedente) ncourante.setHauteur(choixhauteur());
        ncourante.majnote(clecourante, dportee,bundle);


        ncourante.majalteration(tcourante, type2,bundle);
        precedente = ncourante.getHauteur();
        ncourante.setX(marge+98);
        System.out.println(ncourante.getNom());
        System.out.println(ncourante.getHauteur());
        //if (cson.isSelected()) sons[indiceson(ncourante.getHauteur())].play();

        if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);

        }
      else if (type == "LIGNE") {
        //sons[indiceson(ncourante.getHauteur())].stop();
        stopson();
        if (position < ligne.length-1) {
          position = position + 1;
          ncourante.copy(ligne[position]);
          }
          //if (cson.isSelected()) sons[indiceson(ncourante.getHauteur())].play();
          if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);
      }
    }









    public void stopson(){
      //if (cson.isSelected())
      //for (int i=0;i<37;i=i+1){
       //sons[i].stop();
       cc.stopnotes(cson.isSelected() & !erreurmidi);

      //}

    }


    public void affichenote(Note n, Graphics g, Color couleur){
      Dimension d = getSize();
      int i;   // compteur

      g.setColor(couleur);
      if(n.getX()< d.width-marge & n.getX() >= marge+98 & parti) { // NOTE DANS LIMITES
        if (type2 == "ALTERATIONS") n.paint(clecourante, tcourante,g,9,0,dportee, ti, this, couleur,bundle);
        else n.paint(clecourante, tcourante, g,0,0,dportee, ti, this, couleur,bundle);
      }


      else {
        if (type == "NORMAL"){
          if(score-20<=0) {score = 0; resultat = "PERDU"; parti = false;afficheresultat();}
          else {
              score = score - 20;
          //    if (cson.isSelected()) sonerreur.play();

              }
          nouvellenote();

                              }
        else if (type == "LIGNE" & parti){
          if (ligne [position].getX() < marge+98) {  // Si la note courant (sauf la derni�re)d�passe la limite ici marge +25
            score = 0;

            resultat = "PERDU";
            parti =  false;
            afficheresultat();

          }
        }
      }
    }


/*************************************************************************/
/*********** LECTURE RYTHMIQUE *******************************************/
/*************************************************************************/

    public void rythmesuivant() {

                    if (ligner[positionr].getValeur() != 0) {
                      if (positionr < ligner.length - 1) {
                        positionr = positionr + 1;
                        repaint();
                            /* if (cson.getState() & !ligne[position].silence) Synthnote(71,80,dureerythme);*/
                      }
                    }
                  }


    public void meta(MetaMessage meta) {
               byte[] abData = meta.getData();
               String strText = new String(abData);
               if (strText.equals("depart")) {
                 positionr = 0;
                 repaint();
               }
               else {
                 rythmesuivant();
               repaint();
               }
                 //System.out.println(positionr);

             }


    public void creationmetronome() {

                  final int TEXT = 0x01;
                  String textd = "depart";


                    try {
                      ShortMessage sm = new ShortMessage();
                      sm.setMessage(ShortMessage.PROGRAM_CHANGE, 1, 12, 0);
                      metronome.add(new MidiEvent(sm, 0));

                      addEvent(metronome, TEXT, textd.getBytes(), (int)nbtemps * ppq);

                      if(cmetronome.isSelected()){
                        for (int l = 0; l <= 60; l++) {
                          ShortMessage mess = new ShortMessage();
                          ShortMessage mess2 = new ShortMessage();
                          mess.setMessage(ShortMessage.NOTE_ON, 1, 60, 40);

                          metronome.add(new MidiEvent(mess, l * ppq));
                          mess2.setMessage(ShortMessage.NOTE_OFF, 1, 60, 0);
                          metronome.add(new MidiEvent(mess2, l * ppq + 1));
                        }
                    }

                }
                catch (InvalidMidiDataException e) {
                  e.printStackTrace();
                  System.exit(1);
                }

                }


    private void addEvent(Track track, int type, byte[] data, long tick) {
               MetaMessage message = new MetaMessage();
               try {
                 message.setMessage(type, data, data.length);
                 MidiEvent event = new MidiEvent(message, tick);
                 track.add(event);
               }
               catch (InvalidMidiDataException e) {
                 e.printStackTrace();
               }
             }

             private static MidiEvent createNoteOnEvent(int nKey, long lTick) {
               return createNoteEvent(ShortMessage.NOTE_ON, nKey, VELOCITY, lTick);
             }

             private static MidiEvent createNoteOffEvent(int nKey, long lTick) {
               return createNoteEvent(ShortMessage.NOTE_OFF, nKey, 0, lTick);
             }

             private static MidiEvent createNoteEvent(int nCommand, int nKey,
                                                      int nVelocity, long lTick) {
               ShortMessage message = new ShortMessage();
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
               MidiEvent event = new MidiEvent(message,
                                               lTick);
               return event;
             }


    public int ajouterythme(int duree, int i, int tickcourant, int nbmes,
                            int poscourante) {
      double tmpsilence;
      int tick = tickcourant;

      final int TEXT = 0x01;
      String text = "off";

      tmpsilence = Math.random();
      if (!nivcourant.getSilence() | (nivcourant.getSilence() & tmpsilence < 0.85)) {
        if (nbmes <= 3)
          ligner[i] = new Rythme(duree, poscourante, 0, false, false, 0);
        else if (nbmes <= 6)
          ligner[i] = new Rythme(duree, poscourante, 1, false, false, 0);
        else if (nbmes <= 9)
          ligner[i] = new Rythme(duree, poscourante, 2, false, false, 0);

        track.add(createNoteOnEvent(71, tick));
        tick = tick + (int) (4.0 / duree * ppq);
        addEvent(track, TEXT, text.getBytes(), tick);
        track.add(createNoteOffEvent(71, tick));

      }

      else {
        if (nbmes <= 3)
          ligner[i] = new Rythme(duree, poscourante, 0, false, true, 0);
        else if (nbmes <= 6)
          ligner[i] = new Rythme(duree, poscourante, 1, false, true, 0);
        else if (nbmes <= 9)
          ligner[i] = new Rythme(duree, poscourante, 2, false, true, 0);

        track.add(createNoteOffEvent(71, tick));

        tick = tick + (int) (4.0 / duree * ppq);
        addEvent(track, TEXT, text.getBytes(), tick);
      }
      return tick;
    }

    public boolean debutdemesure(int i) {
      double d = 0;
      boolean reponse = false;
      for (int j = 0; j < i; j++) {
        d = d + 4.0 / ligner[j].getValeur();
      }

      for (int k = 1; k < nbmesures; k++)
        if (d == nbtemps * k)
          reponse = true;
      return reponse;
    }

    public void creationligner() {

      Dimension d = getSize();

      int poscourante = 82;
      int tickcourant = (int) (nbtemps * ppq);
      double tpsmes = 0; // nombre de temps
      int nbmes = 1; //numero de la mesure

      int i = 0;
      double tmp;

      // INNITIALISATION Sequence et tracks
      try {
        sequence = new Sequence(Sequence.PPQ, ppq);
      }
      catch (InvalidMidiDataException e) {
        e.printStackTrace();
        System.exit(1);
      }

      track = sequence.createTrack();
      metronome = sequence.createTrack();

      creationmetronome();

      try {
        ShortMessage sm = new ShortMessage();
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, binstr.getSelectedIndex(), 0);

        track.add(new MidiEvent(sm, 0));
      }
      catch (InvalidMidiDataException e) {
        e.printStackTrace();
        System.exit(1);
      }

      while (i < ligner.length) {
        if (nbmes <= nbmesures) {
          while (tpsmes != nbtemps) {
            tmp = Math.random();
            if (nivcourant.getRonde() & tpsmes + 4 <= nbtemps & tmp < 0.2) { //ronde
              tpsmes = tpsmes + 4;
              tickcourant = ajouterythme(1, i, tickcourant, nbmes, poscourante);
              poscourante = poscourante + 216;
              i++;
            }
            else if (nivcourant.getBlanche() & tpsmes + 2 <= nbtemps & tmp < 0.4) { //blanche
              tpsmes = tpsmes + 2;
              tickcourant = ajouterythme(2, i, tickcourant, nbmes, poscourante);
              poscourante = poscourante + 108;
              i++;
            }
            else if (nivcourant.getNoire() & tpsmes + 1 <= nbtemps & tmp < 0.7) { //noire
              tpsmes = tpsmes + 1;
              tickcourant = ajouterythme(4, i, tickcourant, nbmes, poscourante);
              poscourante = poscourante + 54;
              i++;
            }
            else if (nivcourant.getCroche() & tpsmes + 0.5 <= nbtemps) { //croche
              tpsmes = tpsmes + 0.5;
              tickcourant = ajouterythme(8, i, tickcourant, nbmes, poscourante);
              poscourante = poscourante + 27;
              i++;
            }

          }

          tpsmes = 0;
          nbmes = nbmes + 1;
          if (nbmes == 4 | nbmes == 7)
            poscourante = 82;

        }

        else {
          ligner[i] = new Rythme(0, 0, 0, false, false, 0);
          i++;
        }
      }

      regroupenotes();

    }



    public void regroupenotes() {
      for (int i = 0; i < ligner.length - 1; i++) {
        if (ligner[i].getValeur() == 8 & ligner[i + 1].getValeur() == 8 &
            !ligner[i + 1].getSilence() & !ligner[i].getSilence() & !debutdemesure(i + 1)) {
          ligner[i].setGroupee(1);
          ligner[i + 1].setGroupee(2);
        }

      }
    }

  // LIGNE DE NOTES


  public void creationligne(){
    Dimension d = getSize();
    accord a = new accord(ncourante,ncourante,ncourante,"",0);
    intervalle inter = new intervalle(ncourante,ncourante,"");
    int tmph;
    String tmpa = "";
    double tmp;

   // System.out.println(type2);

    if (type2 == "NOTES" | type2 == "ALTERATIONS") {
    ligne [0] = new Note("","",choixhauteur(),d.width-marge,0);
    ligne[0].majnote(clecourante, dportee,bundle);
    ligne[0].majalteration(tcourante, type2, bundle);

    for (int i=1;i<ligne.length;i++) {
      tmph = choixhauteur();
      while (tmph == ligne [i-1].getHauteur())  tmph = choixhauteur();   // pour �viter les r�p�titions



      ligne [i] = new Note(tmpa,"",tmph,d.width-marge + i*35,0);
      ligne[i].majnote(clecourante, dportee,bundle);
      ligne[i].majalteration(tcourante, type2,bundle);
      }

      position = 0;
      ncourante = ligne[position];// initialisa tion avec la premi�re note
      //if (cson.isSelected()) sons[indiceson(ncourante.getHauteur())].play(); // d�part du son de la premi�re note
      if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);
    }


    else if (type2 == "ACCORDS"){
      // voir pour precedant
      for (int i=0;i<ligne.length;i++) {

       a.copy(choixaccord());

        a.modifiex(d.width-marge + i*50);
        ligneacc[i] = new accord(a.acc[0],a.acc[1],a.acc[2],a.nom,a.renvst);
        ligneacc[i].convertir(typeaccord);

      }
      position = 0;
      posnote = 0;

      acourant.copy(ligneacc[position]);
      // acourant.convertir(clecourante,typeaccord);
      ncourante = acourant.acc [acourant.posreelle(posnote)];
      if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);
    }

    else if (type2 == "INTERVALLES"){
         // voir pour precedant
         for (int i=0;i<ligne.length;i++) {
           inter.copy(choixintervalle());
           //i = nouvelintervalle();
           inter.modifiex(d.width-marge + i*65);
           ligneint[i] = new intervalle(inter.interv[0],inter.interv[1],inter.nom);


         }
         position = 0;
         posnote = 0;

         icourant.copy(ligneint[position]);
         ncourante = icourant.interv [posnote]; //0
         if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);

    }
    }




    public void afficheligne(Graphics g) {

      for (int i = position; i < ligne.length; i++) {
        // n'affiche que la ligne � partir de la position
        if (type2 == "NOTES" | type2 == "ALTERATIONS")
          affichenote(ligne[i], g, Color.black);
        else if (type2 == "ACCORDS")
          afficheaccord(ligneacc[i], g, i == position);
        else if (type2 == "INTERVALLES")
          afficheintervalle(ligneint[i], g, i == position);
      }

    }




    public void afficheligner(Graphics g) {
      int precedant = 0;
      int suivant = 0;


      for (int i = 0; i < ligner.length; i++) {
       // System.out.println(i);
        if (ligner[i].getValeur() != 0) {
          if (i != 0)
            precedant = ligner[i - 1].getValeur();
          else
            precedant = 0;
          if (i != ligner.length - 1)
            suivant = ligner[i + 1].getValeur();
          else
            suivant = 0;
          if (i != positionr)

            ligner[i].paint(g, 9, false, dportee, ti,this);
          else

          ligner[i].paint(g, 9, true, dportee, ti,this);
        }
      }
    }


  //***************ACCORDS







  public void nouvelaccord(){



    if (type == "NORMAL") {
    //  choix = (int)Math.round((Math.random()*(tabaccords.length-1)));


          posnote = 0;
          acourant.copy(choixaccord());
          acourant.convertir(typeaccord);
          ncourante = acourant.acc [acourant.posreelle(posnote)];
          if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);


      }
    else if (type == "LIGNE") {
      if (position < ligne.length-1) {
        position = position + 1;
        acourant.copy(ligneacc[position]);

        posnote = 0;
       //acourant.convertir(clecourante,typeaccord);
       ncourante = acourant.acc [acourant.posreelle(posnote)];
       if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);


          }
    }
    }

    public void afficheintervalle(intervalle inter,Graphics g,boolean intervallecourant){
      Dimension d = getSize();
    int i;   // compteur


    if(inter.interv[posnote].getX()< d.width-marge & inter.interv[posnote].getX() >= marge+98 & parti) {
      // NOTE DANS LIMITES
      inter.paint(clecourante, tcourante,posnote,type,g,intervallecourant,this);
      //g.drawString("Renv" + a.renvst,100,100);
    }

    else {
      if (type == "NORMAL"){
        if(score-20<=0) {score = 0; resultat = "PERDU"; parti = false;afficheresultat();}
        else {
            score = score - 20;
            /*if (cson.isSelected()) sonerreur.play();*/
            }

            nouvelintervalle();
                            }
      else if (type == "LIGNE" & parti){
       if (ligneint [position].interv[0].getX() < marge+98) {  // Si la note courant d�passe la limite ici marge +25
          score = 0;
                System.out.println("toto");
          resultat = "PERDU";
          parti =  false;
          afficheresultat();
        }
      }
    }
      }


    public void notesuivante(){

      if (type2 == "ACCORDS") {
        if (posnote<2){
          posnote = posnote + 1;

          ncourante = acourant.acc[acourant.posreelle(posnote)];
          alterationok = false;
          if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);

          }

          else { nouvelaccord();}
          }


    else if (type2 == "INTERVALLES") {
       if (posnote == 0) {
         posnote = posnote + 1;

         ncourante = icourant.interv[posnote];
         alterationok = false;
         if (cson.isSelected()) Synthnote(ncourante.getPitch(),80,dureenote);

         }

         else { nouvelintervalle();}
         }
    }


   /* }
      else if (type == "LIGNE")
        if (!cherchemode) cherchemode = true;
        else nouvelaccord();
    }*/



  //*******SCORE

  public void affichescore(Graphics g, int s){
    Color c;

    g.setColor(Color.black);
    g.draw3DRect(260,420,251,20,true);
    for (int tmp=0; tmp<s; tmp=tmp+10){
      if (tmp<100) g.setColor(c=new Color(60+(tmp+10)/2,26,26));
      else g.setColor(c=new Color(110,26+(tmp-90)/2,26));
      g.fillRect(261+tmp/2,421,5,19);
    }
    g.setFont(new Font("Arial",Font.BOLD,25));
    // g.drawString("     SCORE :   " + s,240,420);
    }


    public void afficheresultat(){

      if (resultat == "GAGNE") {
        JOptionPane.showMessageDialog(this,
           nbjuste + " " + bundle.getString("_correct") + " / " + nberreur + " " + bundle.getString("_wrong"),
          bundle.getString("_congratulations"),
          JOptionPane.INFORMATION_MESSAGE);
        stopjeux();


      }
      else if (resultat == "PERDU") {
        JOptionPane.showMessageDialog(this,
           nbjuste + " " + bundle.getString("_correct") + " / " + nberreur + " " + bundle.getString("_wrong"),
          bundle.getString("_sorry"),
          JOptionPane.INFORMATION_MESSAGE);
        stopjeux();

      };

      }





//****************     METHODES D'ANIMATION DE LA NOTE THREAD (run et stop)

       class RenderingThread extends Thread{
         /**
          *  Ce thread appelle le rafraichissement de notre fenêtre
          *  toutes les 10 milli-secondes
          */
         public void run() {




                 while (true) {
                   try {

                     if (type2 == "NOTES" | type2 == "ALTERATIONS") {
                       sleep(vitesse);

                     }
                     else if (type2 == "INTERVALLES")
                       sleep(vitesse * 3 / 2);
                     else if (type2 == "ACCORDS")
                       sleep(vitesse * 2);
                     else
                       sleep(vitesse + 18);

                     if (parti) {
                       if (type == "NORMAL" &
                           (type2 == "NOTES" | type2 == "ALTERATIONS"))
                         ncourante.setX(ncourante.getX() + 1);
                       else if (type == "NORMAL" & type2 == "ACCORDS")
                         acourant.avance(1);
                       else if (type == "NORMAL" & type2 == "INTERVALLES")
                         icourant.avance(1);
                       else if (type == "LIGNE" &
                                (type2 == "NOTES" | type2 == "ALTERATIONS"))
                         for (int i = 0; i < ligne.length; i++) {

                           ligne[i].setX(ligne[i].getX() - 1);
                         }

                       else if (type == "LIGNE" & type2 == "ACCORDS")
                         for (int i = 0; i < ligneacc.length; i++) {
                           ligneacc[i].avance( -1);
                         }

                       else if (type == "LIGNE" & type2 == "INTERVALLES")
                         for (int i = 0; i < ligneint.length; i++) {
                           ligneint[i].avance( -1);

                         }

                       panelanim.repaint();
                     }
                   }
                   catch (Exception e) {}
                 }

         }
       }



  public void stop(){
   // main = null;
    //if(main!=null){main.stop();}
    }







//************** METHODE PAINT ET UPDATE POUT L'AFFICHAGE DE L'APPLET




  /*  public void update(Graphics g){

      Dimension d=getSize();

      if (ecranjeu) {
      bufferg.setColor(Color.white);
      bufferg.fillRect(0, 0, d.width, d.height);
      affichefocus(bufferg);
      if (parti & type == "NORMAL") {
        if (type2 == "NOTES"| type2 == "ALTERATIONS")  affichenote(ncourante,bufferg,Color.black);
        //on affiche la note que lorsque la partie a commenc�e
        else if (type2 == "ACCORDS") afficheaccord(acourant,bufferg,true);
        else if (type2 == "INTERVALLES") afficheintervalle(icourant,bufferg,true);
        }
      else if ((parti & type == "LIGNE")) afficheligne(bufferg);
      afficheportee(bufferg);
      affichecle(bufferg);
      tcourante.paint(clecourante,bufferg, marge, dportee, diese, bemol, this);
      afficheversion(bufferg,Color.black);     // VERSION DE L'APPLET
      affichescore(bufferg,score);
      afficheresultat(bufferg);
      piano.paint(bufferg);
   //  bufferg.drawString("note courante:   " + notecourante,125,dureenote);

// if (open) bufferg.drawString("OPEN" ,300,100);
    // bufferg.drawString(message,290,105);
      //bufferg.drawString(nomins,325,310);
      }
      else {
          buffer=createImage(getSize().width,getSize().height);
     bufferg=buffer.getGraphics();
        bufferg.drawImage(imagefond,0,0,this);
        // affichefocus(bufferg);
        Color c = new Color(5,5,100);
        bufferg.setColor(c);
        afficheversion(bufferg,c);
        bufferg.setFont(new Font("Arial",Font.BOLD,40));
        bufferg.drawString("LECTURE DE NOTE",140,210);
        bufferg.drawString("    NOTE READING",140,260);
        bufferg.setFont(new Font("Arial",Font.BOLD,15));
        bufferg.drawString("Copyright (C) 2003-2004 RICHARD Christophe" ,10,460);
      }

      g.drawImage(buffer,0,0,this);    // affiche le buffer

      }*/








//#############################################################################
//################################ CLASSE ACCORD ##############################
//#############################################################################


      class accord{
        Note acc [] = new Note [3];
        String nom;
        int renvst; // 0 non renvers� 1 1er renversement 2 second renversement
        //AudioClip son;

        public accord (Note n1, Note n2, Note n3, String m, int r){

            this.acc[0] = n1;
            this.acc[1] = n2;
            this.acc[2] = n3;
            this.nom = m;
            this.renvst = r;

        }

       public void copy (accord a){
          this.acc[0] = new Note(a.acc[0].getAlteration(),a.acc[0].getNom(),a.acc[0].getHauteur(),a.acc[0].getX(),a.acc[0].getPitch());
          this.acc[1] = new Note(a.acc[1].getAlteration(),a.acc[1].getNom(),a.acc[1].getHauteur(),a.acc[1].getX(),a.acc[1].getPitch());
          this.acc[2] = new Note(a.acc[2].getAlteration(),a.acc[2].getNom(),a.acc[2].getHauteur(),a.acc[2].getX(),a.acc[2].getPitch());
          this.nom = a.nom;
          this.renvst = a.renvst;

        }

        public int decalage(int pos){
          int nbalt = 0;
          int resultat = 10;
          int pr = 0;

          //pr = this.posreelle(pos);
          if (this.acc[pos].getAlteration() == "") resultat = 0;
          else {
            for (int i=0;i<3;i=i+1){
            if (((this.acc[i].getAlteration() == "#" | this.acc[i].getAlteration() == "b")& !this.acc[i].alteree(tcourante,bundle))
              | this.acc[i].getAlteration() == "n")
              nbalt++;
            }

            if (nbalt == 0) resultat = 0;
              else if (nbalt == 1) resultat = 10;
              else if (nbalt == 2) {
                if (this.renvst == 0) {
                  if (pos == 0) resultat = 10;
               else if (pos == 1 & this.acc[0].getAlteration() != "") resultat = 20;
                else if (pos == 1 & this.acc[2].getAlteration() != "") resultat = 10;
                /*else if (pos == 2 & this.acc[0].getAlteration() != "") resultat = 8;*/
                else resultat = 20;
                }
                else if (this.renvst == 1) {
                  if (pos ==0 &  this.acc[2].getAlteration() != "") resultat = 20;
                  else if (pos == 2 &  this.acc[1].getAlteration() != "") resultat = 20;
                  else resultat = 10;

                }
                else if (this.renvst == 2) {
                  if (pos == 1 &  this.acc[0].getAlteration() != "") resultat = 20;
                   else if (pos == 0 &  this.acc[2].getAlteration() != "") resultat = 20;
                  else resultat = 10;
                }
              }

                else if (nbalt == 3) {
                  if (this.renvst == 0) {
                  if (pos == 0) resultat = 14;
                  else if (pos == 1) resultat = 24;
                    else if (pos == 2) resultat = 8;
                  }
                  else if (this.renvst == 1) {
                  if (pos == 2) resultat = 24;
                  else if (pos == 0) resultat = 8;
                  else resultat = 14;

                }
                else if (this.renvst == 2) {
                  if (pos == 0) resultat = 24;
                  else if (pos == 1) resultat = 8;
                  else resultat = 14;
                }
                }
          }

          return resultat;
        }

        public void affichenom (Graphics g){

          g.setColor(Color.green);
         g.setFont(new Font("Arial",Font.BOLD,17));
            g.drawString(this.nom,380-this.nom.length()*4,55);


        }

        public void paint(String cle, Tonalite t, int position, String type,Graphics g,boolean accordcourant, Component j){
            Color c = new Color(147,22,22);

          //if (type == "NORMAL")

          for (int i=0;i<3;i=i+1){

           if (!(i== this.posreelle(position)& accordcourant))
             acc[i].paint(cle, t, g,this.decalage(i),0, dportee, ti, j, Color.black,bundle);
          }
          if (accordcourant) acc[this.posreelle(position)].paint(cle,t,g,this.decalage(this.posreelle(position)),0, dportee, ti, j, c, bundle);
          // on affiche la note courante en dernier pour garder la couleur rouge de la ligne sup

          if (type == "NORMAL" | (type == "LIGNE" & accordcourant))this.affichenom(g);

          /*else if (type == "LIGNE") // seul l'accord est demand� -> notes en noir
            for (int i=0;i<3;i=i+1)
             acc[i].paint(cle,g,this.decalage(i),Color.black);*/


          }


          public void avance(int nb){

            for (int i=0;i<3;i=i+1)
              this.acc[i].setX(this.acc[i].getX()+nb);
          }

          public void modifiex(int newx){
         // il faut mettre � jour la coordonn�e de l'accord pour le jeu en ligne

         for (int i=0;i<3;i=i+1)
           this.acc[i].setX(newx);
          }

          public int posreelle(int pos){
           int res = 0;

           if (this.renvst == 0) res = pos;
             else if (this.renvst == 1){
               if (pos == 0) res = 1;
               else if (pos == 1) res = 2;
               else if (pos == 2) res = 0;}
              else {
                if (pos == 0) res = 2;
              else if (pos == 1) res = 0;
               else if (pos == 2) res = 1;}

                    return res;

          }

          public void convertir(String typeacc){ //renvoie le type de renversement
            // convertit l'accord courant pris dans le tableau en fonction de la cl�
             int differencecle = 0;
             double tmp;



                 // convertit l'accord en son inversement
            if (typeacc == "RENV") {
              tmp = Math.random();
              this.renvst = 0;
              if (tmp<0.33) {  // 1er inversement
                this.renvst = 1;
                   this.acc[0].setHauteur(this.acc[0].getHauteur()-35) ;
                   this.acc[0].setPitch(this.acc[0].getPitch()+12);
                  }
              else if (tmp>0.33 & tmp <0.66) {  // 2�me inversement
                this.renvst = 2;
                    this.acc[0].setHauteur(this.acc[0].getHauteur()-35);
                    this.acc[0].setPitch(this.acc[0].getPitch()+12) ;
                    this.acc[1].setHauteur(this.acc[1].getHauteur()-35);
                    this.acc[1].setPitch(this.acc[1].getPitch()+12);
                  }
            }

          }

      }

//#############################################################################
//################################ CLASSE INTERVALLE ##########################
//#############################################################################


      class intervalle{
        Note interv [] = new Note [2];
        String nom;


        public intervalle (Note n1, Note n2,  String m){

            this.interv[0] = n1;
            this.interv[1] = n2;
            this.nom = m;
        }


       public void copy (intervalle a){
          this.interv[0] = new Note(a.interv[0].getAlteration(),a.interv[0].getNom(),a.interv[0].getHauteur(),a.interv[0].getX(),a.interv[0].getPitch());
          this.interv[1] = new Note(a.interv[1].getAlteration(),a.interv[1].getNom(),a.interv[1].getHauteur(),a.interv[1].getX(),a.interv[1].getPitch());
          this.nom = a.nom;
       }



          public void avance(int nb){

            for (int i=0;i<2;i=i+1)
              this.interv[i].setX(this.interv[i].getX()+nb);
          }

        public void affichenom (Graphics g){

          g.setColor(Color.green);
         g.setFont(new Font("Arial",Font.BOLD,16));
          g.drawString(this.nom,380-this.nom.length()*4,55);
        }

        public void paint(String cle, Tonalite t, int position, String type,Graphics g, boolean intervcourant, Component j){
          Color c = new Color(147,22,22);


          //if (type == "NORMAL")


             if (position == 0 & intervcourant) interv[0].paint(cle,t,g,9,0,dportee, ti, j,c, bundle);
             else interv[0].paint(cle,t,g,9,0,dportee, ti, j,Color.black, bundle);

              if (position == 1 & intervcourant) interv[1].paint(cle,t,g,-19,28, dportee,  ti, j, c, bundle);
              else interv[1].paint(cle,t,g,-19,28,dportee, ti, j, Color.black, bundle);




          if (type == "NORMAL" | (type == "LIGNE" & intervcourant))this.affichenom(g);

          }



          public void modifiex(int newx){
         // il faut mettre � jour la coordonn�e de l'accord pour le jeu en ligne

         for (int i=0;i<2;i=i+1)
           this.interv[i].setX(newx);
          }



          public void convertir(String cle,String typeacc){ //renvoie le type de renversement
            // convertit l'accord courant pris dans le tableau en fonction de la cl�
             int differencecle = 0;
             double tmp;



            if (cle == "sol") differencecle = 0;
            else if (cle == "fa") differencecle = 8;
            else if (cle == "sol+fa"){
              tmp = Math.random();
              if (tmp<0.5) {  // CLE DE FA
                differencecle = 80;

              }
              else differencecle = 0; //CLE DE SOL

            }

            for (int i=0;i<3;i=i+1)
              this.interv[i].setHauteur(this.interv[i].getHauteur()+differencecle);


            if ((cle == "fa") | ((cle == "sol+fa") & (this.interv[0].getHauteur()>dportee+55))){
            // convertit le son pour la cl� de FA
           this.interv[0].setPitch(this.interv[0].getPitch()-24);
           this.interv[1].setPitch(this.interv[1].getPitch()-24);

           }


          }

      }



      public class anim extends JPanel
  {
    int dep = 0;
    Timer timer;
    int largeur = 680, hauteur = 480;

    public void anim() {
       setPreferredSize(new Dimension(largeur, hauteur));
       setDoubleBuffered(true);


    }



    public void paintComponent(Graphics g) {
      int i, j;
      Dimension d = getSize();

      g.setColor(Color.white);
      g.fillRect(0, 0, d.width, d.height);





      if (ecranjeu == 1) {

        super.paintComponent(g);

        if (parti & type == "NORMAL") {
          if (type2 == "NOTES" | type2 == "ALTERATIONS")
            affichenote(ncourante, g, Color.black);
          //on affiche la note que lorsque la partie a commenc�e
          else if (type2 == "ACCORDS")
            afficheaccord(acourant, g, true);
          else if (type2 == "INTERVALLES")
            afficheintervalle(icourant, g, true);
        }
        else if ( (parti & type == "LIGNE"))
          afficheligne(g);
        afficheportee(g);
        affichecle(g);
        tcourante.paint(clecourante, g, marge, dportee, ti, this, bundle);

      affichescore(g, score);

        piano.paint(g, clecourante);

        // pnotes.setVisible(true);
        // System.out.println(piano.Getpositionbase());
        // principal.setVisible(true);
        //  bufferg.drawString("note courante:   " + notecourante,125,dureenote);

      }
      else if (ecranjeu == 0) {

        g.drawImage(ti.Getimage(24), 0, 0, this);
        Color c = new Color(5, 5, 100);
        g.setColor(c);
        // afficheversion(g, c);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Jalmus", 300, 250);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Java Lecture Musicale", 240, 300);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Copyright (C) 2003-2006 RICHARD Christophe", 10, 500);
      }
      else if (ecranjeu == 2) {
      //  super.paintComponent(g);
         afficheportee2(g);
          affichecle(g);
        if (parti) {
       afficheligner(g);
           afficheportee2(g);




        }
      }
    }
  }


  public class apropos extends JPanel
{

  int largeur = 680, hauteur = 480;

  public void apropos() {
    // setPreferredSize(new Dimension(largeur, hauteur));

  }



  public void paintComponent(Graphics g) {
    int i, j;
    Dimension d = getSize();

    setOpaque(true);


      g.drawImage(ti.Getimage(24), 0, 0, this);
      Color c = new Color(5, 5, 100);
      g.setColor(c);
      // afficheversion(g, c);
      g.setFont(new Font("Arial", Font.BOLD, 20));
      g.drawString("Jalmus", 80, 30);
      g.setFont(new Font("Arial", Font.BOLD, 10));
      g.drawString("Java Lecture Musicale", 20, 50);
      g.setFont(new Font("Arial", Font.BOLD, 10));
      g.drawString("Copyright (C) 2003-2006 RICHARD Christophe", 10, 80);






  }
}

      class DumpReceiver implements Receiver
           {


   public DumpReceiver()
           {

           }



   public void send(MidiMessage event,long time)
           {

       Integer i=new Integer(0);
           String output = "";

           if (ecranjeu == 1){

             if (event instanceof ShortMessage) {
               if (!open) {
                 open = true;

               }
               switch (event.getStatus() & 0xf0) {
                 case 0x90:
                   output = ("   Note On Key: " + ( (ShortMessage) event).getData1() +
                             " Velocity: " + ( (ShortMessage) event).getData2());
                   notejouee = ( (ShortMessage) event).getData1();


                   //System.out.println(((ShortMessage)event).getData2());

                   // touche C3 pour lancer le jeu au clavier
                   if (!parti & ( (ShortMessage) event).getData1() == 60) { //C3 pour commencer

                     lancementjeu();
                   }

                   if ( ( (ShortMessage) event).getData2() != 0)
                     piano.notejouee(cc, cson.isSelected() & !erreurmidi, notejouee, 1);
                   else
                     piano.notejouee(cc, cson.isSelected() & !erreurmidi, notejouee, 0);

                   if ( ( (ShortMessage) event).getData2() != 0 & parti) {
                     System.out.print(((ShortMessage) event).getData1());
                     System.out.println("-"+ncourante.getPitch());
                     if ( (cexacte.isSelected() &
                           ( (ShortMessage) event).getData1() == ncourante.getPitch())
                         |
                         (!cexacte.isSelected() &
                          (memenote( ( (ShortMessage) event).getData1(),
                                    ncourante.getPitch()))))

                       reponsejuste();

                     else
                       reponsefausse();

                     repaint();
                   }

                   break;
                 case 0x80:
                   output = ("   Note Off  Key: " + ( (ShortMessage) event).getData1() +
                             " Velocity: " + ( (ShortMessage) event).getData2());
                   break;
                 case 0xb0:
                   if ( ( (ShortMessage) event).getData1() < 120)
                     output = ("   Controller No.: " + ( (ShortMessage) event).getData1() +
                               " Value: " + ( (ShortMessage) event).getData2());
                   else
                     output = ("   ChannelMode Message No.: " +
                               ( (ShortMessage) event).getData1() + " Value: " +
                               ( (ShortMessage) event).getData2());
                   break;
                 case 0xe0:
                   output = ("   Pitch lsb: " + ( (ShortMessage) event).getData1() +
                             " msb: " + ( (ShortMessage) event).getData2());
                   break;
                 case 0xc0:
                   output = ("   Program Change No: " + ( (ShortMessage) event).getData1() +
                             " Just for Test: " + ( (ShortMessage) event).getData2());
                   break;
                 case 0xd0:
                   output = ("   Channel Aftertouch Pressure: " +
                             ( (ShortMessage) event).getData1() + " Just for Test: " +
                             ( (ShortMessage) event).getData2());
                   break;
               }
             }
             else if (event instanceof SysexMessage) {
               output = ("   SysexMessage: " + (event.getStatus() - 256));
               byte[] data = ( (SysexMessage) event).getData();
               for (int x = 0; x < data.length; x++)
                 output = (" " + i.toHexString(data[x]));
             }
             else
               output = ("   MetaEvent");
             //  if (output != "") System.out.println(output);
           }
         }

public void close() {}

}


 public static void main(String arg[]) {
  // Event pour la gestion des Evenements et principalement le message EXIT
        // Constructions de la frame
        jalmus l = new jalmus();
        // Initialisation
        l.init();
        // Forcer la taille
       l.setSize(790,590);
        // Affichage
       l.repaint();


        l.setVisible(true);
        l.setFocusable(true);


        l.setResizable(false);

        l.setTitle("Jalmus"); //On donne un titre à l'application

                        l.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran

                        l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer
		//lors du clic sur la croix



  }







                }





