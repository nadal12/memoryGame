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
Classe tauler.
 */
package pk_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Tauler extends JPanel {

    //Atributs
    public static final int DIMENSIO = 4;
                                          /*Nombre de cartes per costat. És públic 
                                          per poder assignar el tamany de l'array 
                                          "cartesAparellades" que es troba al programa
                                          principal*/

    private static final int MAXIM = 600; //Tamany màxim per costat del tauler (amb píxels).
    private static final int COSTAT = (MAXIM / DIMENSIO); //Tamany del costat de cada carta. 
    private Carta carta[][];
    private Imatge img;

    public Tauler() {
        inicialitzar();
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                carta[i][j].paintComponent(g);
            }
        }
    }

    /**
     * Retorna la dimensió del tauler.
     *
     * @return Dimension (tauler).
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAXIM, MAXIM);
    }

    /**
     * Destapa totes les cartes del tauler modificant l'atribut "destapada" de
     * cada una d'elles. El mètode ja fa el repintat del panell.
     */
    public void destaparTot() {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                carta[i][j].destaparCarta();
            }
        }
        repaint();
    }

    /**
     * Tapa totes les cartes del panell modiciant l'atribut "destapada" de cada
     * una d'elles. El mètode ja fa el repintat del panell.
     */
    public void taparTot() {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {
                carta[i][j].taparCarta();
            }
        }
        repaint();
    }

    /**
     * Retorna "true" si el clic que s'ha fet amb el ratolí, s'ha fet a la carta
     * que es passa per paràmetre.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     * @param x Coordenada "x" del ratolí al moment del clic.
     * @param y Coordenada "y" del ratolí al moment del clic.
     * @return boolean.
     */
    public boolean dinsCasella(int i, int j, int x, int y) {
        return carta[i][j].getRec().contains(x, y);
    }

    /**
     * Retorna "true" si la carta ubicada a les coordenades passades 
     * per paràmetre està destapada.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     * @return boolean.
     */
    public boolean cartaDestapada(int i, int j) {
        return carta[i][j].estaDestapada();
    }

    /**
     * Destapa la carta que es troba a les coordenades passades per paràmetre.
     * No fa el repintat.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     */
    public void destapa(int i, int j) {
        carta[i][j].destaparCarta();
    }

    /**
     * Tapa la carta que es passa per paràmetre. No fa el repintat.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     */
    public void tapa(int i, int j) {
        carta[i][j].taparCarta();
    }

    /**
     * Retorna el nombre de cartes per costat del tauler.
     *
     * @return (int) DIMENSIÓ.
     */
    public int getDimensio() {
        return DIMENSIO;
    }

    /**
     * Retorna el rectangle corresponent a la posició que s'hagui especificat al
     * paràmetre.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     * @return Rectangle.
     */
    public Rectangle getRectangle(int i, int j) {
        return carta[i][j].getRec().getBounds();
    }

    /**
     * Retorna un enter corresponent a l'ID de la carta que s'ha especificat per
     * paràmetre.
     *
     * @param i Coordenada "i" de la carta.
     * @param j Coordenada "j" de la carta.
     * @return ID (int).
     */
    public int getIDCarta(int i, int j) {
        return carta[i][j].getImg().getID();
    }

    /**
     * Col·loca totes les cartes amb ordre i cap per avall. Exemple: Si A1 és
     * parella amb A2, A1 es trobarà a (0,0) i A2 a (0,1).
     */
    public void inicialitzar() {
        //Declaracions
        int comptador = -1;
        /*El comptador elegirà la imatge que s'ha de posar a la carta
                            i també l'ID que tindrà*/
        int y = 0;
        int x = 0;
        /*La "x" i la "y" serviràn per dibuixar les cartes amb un cert
                            desplaçament perquè no es solapin.*/
        carta = new Carta[DIMENSIO][DIMENSIO];

        for (int i = 0; i < DIMENSIO; i++) {
            x = 0;
            for (int j = 0; j < DIMENSIO; j++) {
                if ((j % 2) == 0) {
                    comptador++;
                }
                Rectangle2D.Float rectangle = new Rectangle2D.Float(x, y, COSTAT, COSTAT);
                carta[i][j] = new Carta(rectangle);
                img = new Imatge(comptador, comptador);
                carta[i][j].setImatge(img);
                x = x + COSTAT;
            }
            y = y + COSTAT;
        }
        repaint();
    }

    /**
     * Escapça les cartes del tauler utilitzant el mètode de "Fisher-Yates".
     *
     */
    public void escapçar() {
        //Declaracions

        /*El següent array està inicialitzat amb les imatges ordenades. L'algoritme 
        escapçarà aquest array per després elegir les imatges a partir d'ell.*/
        int[] IDImatges = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7};
        int n = IDImatges.length;
        int aleatori;
        /*Variable per emmagatzemar el valor aleatori.*/
        int salvarID;
        /*Variable per emmagatzemar un valor intermitg.*/
        int y = 0;
        int e = 0;
        int x;

        /*Comença amb l'índex al final de l'array i genera un nombre aleatori
        de 0 a l'índex. Una vegada es té l'aleatori es canvia la carta per la 
        que està apuntant l'índex. Es decrementa l'índex i es continua igual fins 
        que l'índex arriba al principi de l'array. */
        for (int i = n - 1; i >= 0; i--) {
            aleatori = (int) (Math.random() * i);
            salvarID = IDImatges[i];
            IDImatges[i] = IDImatges[aleatori];
            IDImatges[aleatori] = salvarID;
        }

        /*Ja mesclades les imatges, les assignem a la posició corresponent del 
        tauler.*/
        carta = new Carta[DIMENSIO][DIMENSIO];
        for (int i = 0; i < DIMENSIO; i++) {
            x = 0;
            for (int j = 0; j < DIMENSIO; j++, e++) {

                Rectangle2D.Float rectangle = new Rectangle2D.Float(x, y, COSTAT, COSTAT);
                carta[i][j] = new Carta(rectangle);
                img = new Imatge(IDImatges[e], IDImatges[e]);
                carta[i][j].setImatge(img);
                x = x + COSTAT;
            }
            y = y + COSTAT;
        }
        repaint();
    }

    /**
     * Retorna "true" si el tauler conté totes les cartes destapades.
     *
     * @return Boolean.
     */
    public boolean taulerDestapat() {
        //Declaracions
        boolean destapat = true;

        for (int i = 0; i < DIMENSIO && destapat; i++) {
            for (int j = 0; j < DIMENSIO && destapat; j++) {

                if (!carta[i][j].estaDestapada()) {

                    destapat = false;

                }
            }
        }
        return destapat;
    }
}
