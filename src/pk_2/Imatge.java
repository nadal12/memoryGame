/*
Autor: Nadal Llabrés Belmar.
Enllaç vídeo: https://youtu.be/bHOG3BJzuFk
Curs: 1er Grau Enginyeria Informàtica (Grup 2).
Assignatura: Programació II.
Exercici: Pràctica Final. 
Professor: Miquel Mascaró Oliver.
Data: 07/06/2018.
*/

/*
Classe imatge.
 */
package pk_2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Imatge {

    //Atributs
    private BufferedImage bi;
    private int ID;

    //Constructor 1
    public Imatge(int nomImatge, int ID) {

        this.ID = ID;
        try {
            bi = ImageIO.read(new File("IMATGES/roc" + nomImatge + ".jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Imatge.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Constructor 2.
    public Imatge(String nomImatge) {

        try {
            bi = ImageIO.read(new File(nomImatge));
        } catch (IOException ex) {
            Logger.getLogger(Imatge.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * S'especifica una posició per paràmetre que és on es dibuixarà la imatge.
     *
     * @param g Graphics g
     * @param x Coordenada "x".
     * @param y Coordenada "y".
     */
    public void paintComponent(Graphics g, float x, float y) {
        g.drawImage(bi, (int) x, (int) y, null);
    }

    /**
     * Retorna el valor de l'ID d'una carta.
     *
     * @return ID (int).
     */
    public int getID() {
        return ID;
    }
}
