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
JOC DEL MEMORY  

El joc es planteja com una graella de 4x4 caselles on s’hi troben amagades 8 parelles de cartes. El joc es
juga amb un únic jugador. Inicialment totes les cartes es troben cap per avall, a cada torn el jugador
destapa dues cartes si les cartes coincideixen queden destapades en cas contrari es tornen a tapar. El joc
acaba quan totes les cartes estan destapades.
 */
package main;

import com.sun.glass.ui.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pk_2.Carta;
import pk_2.Tauler;

public class Memory extends JFrame implements MouseListener {

    //Declaració dels elements del menú. 
    private JMenuBar jmBar;
    private JMenu jmJoc;
    private JMenuItem jmItemInicialitzar;
    private JMenuItem jmItemEscapçar;
    private JMenuItem jmItemTaparTot;
    private JMenuItem jmItemDestaparTot;
    private JMenuItem jmItemSortir;
    private JToolBar jtBar;

    //Altres declaracions. 
    private Tauler tauler;
    private int numeroCartesGirades = 0;
    private int[] coordenades = new int[2];
    /*Array que enmagatzema les coordenades 
     de la primera carta que s'ha destapat.*/

     /*Array de boolean on cada vegada que una parella s'hagui trobat, es posarà la posició corresponent
    de l'array a "true". Quan totes les posicions de l'array estiguin a "true", s'hauràn trobat totes les parelles.*/
    Boolean[] cartesAparellades = new Boolean[(Tauler.DIMENSIO * tauler.DIMENSIO) / 2];

    public Memory() {
        tauler = new Tauler();
        //Inicialitza l'array de "cartesAparellades" a "false". 
        resetParelles();
        tauler.addMouseListener(this);
        getContentPane().add(tauler);
        setTitle("Joc Memory");
        this.setMinimumSize(new Dimension(tauler.getDimensio(), 650)); 
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(Memory.EXIT_ON_CLOSE);
        initComponents();

    }

    private void initComponents() {

        //Inicialització de les components del menú.
        jmBar = new JMenuBar();
        jmJoc = new JMenu();
        jmItemInicialitzar = new JMenuItem();
        jmItemEscapçar = new JMenuItem();
        jmItemTaparTot = new JMenuItem();
        jmItemDestaparTot = new JMenuItem();
        jmItemSortir = new JMenuItem();

        //Afegeix una icona devora de l'opció del menú
        jmItemSortir.setIcon(new ImageIcon("ICONES/exit.png"));
        jtBar = new JToolBar();

        //Afegim el menú al JFRAME.        
        getContentPane().add(jtBar);
        getContentPane().add(jmBar);

        //Donem un nom a cada un dels ítems del menú.
        jmJoc.setText("Joc");

        jmItemInicialitzar.setText("Inicialitzar");
        jmItemEscapçar.setText("Escapçar");
        jmItemTaparTot.setText("Tapar tot");
        jmItemDestaparTot.setText("Destapar tot");
        jmItemSortir.setText("Sortir");
    
        //Assignem una acció als ítems del menú 
        jmItemInicialitzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemInicialitzarActionPerformed(evt);
            }
        });

        jmItemEscapçar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemEscapçarActionPerformed(evt);
            }
        });

        jmItemTaparTot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemTaparTotActionPerformed(evt);
            }
        });

        jmItemDestaparTot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemDestaparActionPerformed(evt);
            }
        });

        jmItemSortir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jmItemSortirActionPerformed(evt);
            }
        });

        //Afegim els ítems al menú. 
        jmJoc.add(jmItemInicialitzar);
        jmJoc.add(jmItemEscapçar);
        jmJoc.add(jmItemTaparTot);
        jmJoc.add(jmItemDestaparTot);
        jmJoc.addSeparator();
        jmJoc.add(jmItemSortir);
        jmBar.add(jmJoc);

        //Definim el menú. 
        setJMenuBar(jmBar);

    
    }
    //Accions que es realitzaràn a cada opció del menú.
    private void jmItemInicialitzarActionPerformed(ActionEvent evt) {

        tauler.inicialitzar();
        resetParelles();

    }

    private void jmItemEscapçarActionPerformed(ActionEvent evt) {

        tauler.escapçar();
        resetParelles();
    }

    private void jmItemTaparTotActionPerformed(ActionEvent evt) {

        tauler.taparTot();
        resetParelles();
    }

    private void jmItemDestaparActionPerformed(ActionEvent evt) {

        tauler.destaparTot();
        resetParelles();
    }

    private void jmItemSortirActionPerformed(ActionEvent evt) {

        System.exit(0);

    }

    public static void main(String[] args) {

        /*Detecta el sistema on s'executa el codi i adapta l'estil del menú amb l'estil
        del sistema operatiu.*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Memory().setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        //Declaracions
        int x, y, i;
        int j = 0;

        /*La segona condició serveix perque si el tauler esta tot destapat 
        no es pugui interaccionar amb ell.*/
        if (e.getButton() == MouseEvent.BUTTON1 && !tauler.taulerDestapat()) {

            //Obtenim la posició on s'ha fet el clic.
            x = e.getX();
            y = e.getY();
            boolean trobat = false;

            //Detectem a quina casella s'ha produit el clic. 
            for (i = 0; i < tauler.getDimensio() && !trobat; i++) {
                for (j = 0; j < tauler.getDimensio() && !trobat; j++) {
                    trobat = tauler.dinsCasella(i, j, x, y);
                }
            }
            i--;
            j--;

            /*Entrerem dins el condicional en el cas de que no tinguem cartes girades (per trobar la parella)
            i si el clic no ha estat a una carta de les que ja estan girades amb la seva parella.*/
            if (!(cartesAparellades[tauler.getIDCarta(i, j)]) && (numeroCartesGirades == 0)) {
                tauler.destapa(i, j);

                //Després de destapar la carta repintem el cuadrat corresponent.
                tauler.paintImmediately(tauler.getRectangle(i, j));
                //Guardem les coordenades de la carta que hem destapat (primera).
                coordenades[0] = i;
                coordenades[1] = j;

                numeroCartesGirades++;
            }

            if (!tauler.cartaDestapada(i, j)) {

                tauler.destapa(i, j);
                tauler.paintImmediately(tauler.getRectangle(i, j));

                /*Si l'ID de la carta no es igual que l'ID de la primera carta que hem emmagatzemat a dins
                l'array "coordenades"*/
                if (tauler.getIDCarta(i, j) != tauler.getIDCarta(coordenades[0], coordenades[1])) {

                    //Fem una espera de 0,5 segons.
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Memory.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //Tapem les dues cartes. 
                    tauler.tapa(i, j);
                    tauler.tapa(coordenades[0], coordenades[1]);

                    //Repintem els dos cuadrats corresponents. 
                    tauler.paintImmediately(tauler.getRectangle(i, j));
                    tauler.paintImmediately(tauler.getRectangle(coordenades[0], coordenades[1]));

                    numeroCartesGirades = 0;

                } else {

                    /*En cas de que siguin iguales posem la posició de l'array de aparellades a "true".
                    La posició es decideix depenent de l'ID.*/
                    cartesAparellades[tauler.getIDCarta(i, j)] = true;

                    if (jocAcabat()) {

                        ImageIcon icon = new ImageIcon("IMATGES/final.png");

                        //Mostrem una petita finestra indicant que el joc s'ha acabat. 
                        JOptionPane.showMessageDialog(null, "Has guanyat!", "Joc Acabat", JOptionPane.INFORMATION_MESSAGE, icon);

                    }
                    numeroCartesGirades = 0;
                }
            }
        }
    }

    /**
     * Inicialitza/reseteja l'array "cartesAparellades" per a poder iniciar una
     * nova partida.
     */
    public void resetParelles() {
        for (int q = 0; q < cartesAparellades.length; q++) {
            cartesAparellades[q] = false;
        }
    }

    /**
     * Retorna true si l'array "cartesAparellades" està tot a "true".
     *
     * @return boolean.
     */
    public boolean jocAcabat() {
        boolean jocAcabat = true;
        for (int a = 0; a < cartesAparellades.length && (jocAcabat); a++) {
            if (!cartesAparellades[a]) {
                jocAcabat = false;
            }
        }
        return jocAcabat;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
