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
Classe Carta.
 */
package pk_2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Carta {

    //Atributs
    private Rectangle2D.Float rectangle;
    private Boolean destapada = false;
    private Imatge imatge;
    private final Imatge REVERS;

    //Constructor.
    public Carta(Rectangle2D.Float r) {

        this.destapada = false;
        this.rectangle = r;
        this.REVERS = new Imatge("IMATGES/back.png");
    }

    /**
     * Dibuixa la carta. Si l'atribut "destapada" és fals, dibuixarà la part
     * posterior, sino, la imatge que correspongui a la carta.
     *
     * @param g
     */
    public void paintComponent(Graphics g) {

        if (!destapada) {
            this.REVERS.paintComponent(g, this.rectangle.x, this.rectangle.y);
        } else {
            this.imatge.paintComponent(g, this.rectangle.x, this.rectangle.y);
        }
    }

    /**
     * Assigna una imatge passada per paràmetre a l'atribut "imatge".
     *
     * @param imatge
     */
    public void setImatge(Imatge imatge) {
        this.imatge = imatge;
    }

    /**
     * Destapa una carta canviant el valor de la variable "destapada".
     */
    public void destaparCarta() {
        destapada = true;
    }

    /**
     * Tapa una carta canviant el valor de la variable "destapada".
     */
    public void taparCarta() {
        destapada = false;
    }

    /**
     * Retorna el rectangle corresponent a la carta.
     *
     * @return rectangle
     */
    public Rectangle2D.Float getRec() {
        return rectangle;
    }

    /**
     * Retorna un objecte Imatge corresponent a la carta.
     *
     * @return Imatge
     */
    public Imatge getImg() {
        return imatge;
    }

    /**
     * Retorna "true" si la carta està destapada.
     *
     * @return Boolean.
     */
    public boolean estaDestapada() {
        return destapada;
    }
}
