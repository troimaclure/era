/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.thread;

import era.Era;

/**
 *
 * @author Administrateur
 */
public class RepaintThread implements Runnable {

    private static int repaintCounter = 0;

    public static void setRepaintCounter(int count) {
        repaintCounter = repaintCounter > count ? repaintCounter : count;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);

                if (repaintCounter > 0) {
                    repaintCounter--;
                    Era.panneau.repaint();
                }
            } catch (InterruptedException ex) {
            }

        }
    }

}
