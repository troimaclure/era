/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era;

import era.thread.RepaintThread;
import era.ui.Fenetre;
import era.ui.Help;
import era.ui.Panneau;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Administrateur
 */
public class Era {

    public static Fenetre fenetre;
    public static Panneau panneau;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        }

        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        fenetre = new Fenetre();
        panneau = fenetre.panneau;
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fenetre.setVisible(true);
        new Help(fenetre, true);
        new Thread(new RepaintThread()).start();
    }

}
