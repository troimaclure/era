/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import era.manager.GeneralManager;
import era.utils.Drawutils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Administrateur
 */
public class EntiteOval extends Entite {

    public EntiteOval(int i, int i1, int i2, int i3, String text) {
        super(i, i1, i2, i3);
        this.color = Color.decode("#e74c3c");
        this.fontColor = Color.WHITE;
        this.text = text;
    }

    @Override
    public void draw(Graphics2D g) {
        int w = g.getFontMetrics().stringWidth(text);
        if (this.width < w) {
            this.width = w + w / 3;
        }
        currentGrow += hover ? (currentGrow <= maxGrow ? 2 : 0) : currentGrow == 0 ? 0 : (-2);
        g.setStroke(new BasicStroke(5));
        Rectangle r = new Rectangle(x - currentGrow / 4, y - currentGrow / 4, width + currentGrow / 2, height + currentGrow / 2);
        g.setColor(hover ? color.brighter() : color);
        g.fillOval(r.x, r.y, r.width, r.height);
        g.setColor(hover ? Color.decode("#3498db") : selected ? GeneralManager.colorSelected : this.fontColor);
        g.drawOval(r.x, r.y, r.width, r.height);
        Drawutils.centerString(g, r, text, GeneralManager.font, true);
    }

    @Override
    public boolean isHover(Point pt) {
        return this.contains(pt);
    }

}
