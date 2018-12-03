/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.ui;

import era.listener.GeneralKeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Administrateur
 */
public class Fenetre extends javax.swing.JFrame {

    /**
     * Creates new form Fenetre
     */
    public boolean isFullscreen = false;

    public Fenetre() {
        initComponents();
        setIconImage(new ImageIcon(Fenetre.class.getResource("era.png")).getImage());

        this.addKeyListener(new GeneralKeyListener());
        this.setFocusTraversalKeysEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panneau = new era.ui.Panneau();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mon E.R.A que j <3 tro");
        setState(6);

        panneau.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout panneauLayout = new javax.swing.GroupLayout(panneau);
        panneau.setLayout(panneauLayout);
        panneauLayout.setHorizontalGroup(
            panneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 787, Short.MAX_VALUE)
        );
        panneauLayout.setVerticalGroup(
            panneauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panneau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panneau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public era.ui.Panneau panneau;
    // End of variables declaration//GEN-END:variables

    public void toggleFullScreen() {
        dispose();
        isFullscreen = !isFullscreen;
        this.setResizable(!isFullscreen);
        this.setUndecorated(isFullscreen);
        this.setState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
