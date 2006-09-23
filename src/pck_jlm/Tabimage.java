package pck_jlm;

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

import java.awt.*;
import javax.imageio.*;

public class Tabimage {
   Image T [] = new Image [25];

  public Tabimage() {

;

    try{
      T[0] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/cledesol.gif"));
      T[1] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/cledefa.gif"));
      T[2] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/ronde.gif"));
      T[3] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/ronder.gif"));
      T[4] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/blanche.gif"));
      T[5] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/blancher.gif"));
      T[6] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/noire.gif"));
      T[7] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/noirer.gif"));
      T[8] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/soupir.gif"));
      T[9] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/soupirr.gif"));
      T[10] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/demisoupir.gif"));
      T[11] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/demisoupirr.gif"));
      T[12] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/croche.gif"));
      T[13] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/crocher.gif"));
      T[14] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/pause.gif"));
      T[15] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/pauser.gif"));

      T[16] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/becarre.gif"));
      T[17] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/diese.gif"));
      T[18] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/bemol.gif"));
      T[19] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/becarrer.gif"));
      T[20] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/dieser.gif"));
      T[21] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/bemolr.gif"));

      T[22] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/note.gif"));
      T[23] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/noter.gif"));
      T[24] = ImageIO.read(getClass().getClassLoader().getResource(
          "images/bg1.gif"));

    }
    catch(Exception e){

      System.out.println("Impossible de charger les images");

      System.exit(0);

    }

  }

  public Image Getimage(int i){
    return this.T[i];
  }
}
