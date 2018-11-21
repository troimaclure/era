/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.ui;

import era.entite.AbstractLine;
import era.listener.GeneralMouseListener;
import era.manager.EntiteManager;
import era.manager.GeneralManager;
import era.manager.MenuManager;
import era.thread.RepaintThread;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

/**
 *
 * @author Administrateur
 */
public class Panneau extends JPanel {

    public Panneau() {
        super(true);
        GeneralMouseListener m = new GeneralMouseListener();
        this.addMouseListener(m);
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);

    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);

        Graphics2D g = (Graphics2D) grphcs;

        AffineTransform oldTransform = g.getTransform();
        AffineTransform t = new AffineTransform();
        setAntialiasing(g);
        setConfig(g);

        g.setColor(GeneralManager.background);
        g.fill(new Rectangle(0, 0, 10000, 10000));
        if (!GeneralManager.name.isEmpty()) {
            drawTitle(g, GeneralManager.name);
        }

        t.translate(GeneralManager.translate.x, GeneralManager.translate.y);
        t.scale(GeneralManager.zoom, GeneralManager.zoom);
        g.setTransform(t);
        EntiteManager.getEntites().forEach((e) -> {
            if (e instanceof AbstractLine) {
                e.draw(g);
            }
        });

        if (EntiteManager.currentLine != null) {
            EntiteManager.currentLine.draw(g);
        }
        EntiteManager.getEntites().forEach((e) -> {
            if (!(e instanceof AbstractLine)) {
                e.draw(g);
            }
        });
        drawSelector(g);
        g.setColor(Color.WHITE);
        g.setTransform(oldTransform);
        if (MenuManager.menu != null) {
            MenuManager.menu.draw(g);
        }
        g.dispose();
    }

    private void drawSelector(Graphics2D g) {
        g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{15}, 0));
        if (GeneralManager.selector != null) {
            g.setColor(Color.decode("#3498db"));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            g.fill(GeneralManager.selector);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g.draw(GeneralManager.selector);
        }

    }

    private void setConfig(Graphics2D g) {
        g.setFont(GeneralManager.font);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    }

    private void setAntialiasing(Graphics2D g2) {

        g2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));

    }

    public void drawTitle(Graphics2D g, String name) {
        g.setFont(new Font("Segoe UI", Font.BOLD, 42));

        g.setColor(GeneralManager.background.equals(Color.BLACK) ? Color.WHITE : GeneralManager.background.darker());
        g.drawString(name, 49, 49);
        g.drawString(name, 48, 51);
        g.drawString(name, 51, 49);
        g.drawString(name, 51, 51);
        g.setColor(GeneralManager.background.equals(Color.BLACK) ? Color.BLACK : GeneralManager.background.brighter());
        g.drawString(name, 50, 50);

    }

}
