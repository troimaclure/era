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
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class EntiteRect extends Entite {

    public boolean type = true;

    public EntiteRect() {
    }

    public EntiteRect(int i, int i1, int i2, int i3, String text) {
        super(i, i1, i2, i3);
        this.color = Color.black;
        this.fontColor = Color.WHITE;
        this.text = text;

    }

    @Override
    public void draw(Graphics2D g) {
        int w = g.getFontMetrics().stringWidth(text);
        if (this.width < w) {
            this.width = w + w / 3;
        }
        if (height < 10)
            height = 10;
        currentGrow += hover ? (currentGrow <= maxGrow ? 2 : 0) : currentGrow == 0 ? 0 : (-2);
        g.setStroke(new BasicStroke(5));
        Rectangle r = new Rectangle(x - currentGrow / 4, y - currentGrow / 4, width + currentGrow / 2, height + currentGrow / 2);
        g.setColor(color);
        if (type) {
            g.fill(r);
        } else {
            g.fillOval(r.x, r.y, r.width, r.height);
        }
        g.setColor(this.fontColor);
        if (type) {
            g.draw(r);
        } else {
            g.drawOval(r.x, r.y, r.width, r.height);
        }
        if (!props.isEmpty()) {
            Drawutils.centerStringX(g, r, text, GeneralManager.font);
        } else {
            Drawutils.centerString(g, r, text, GeneralManager.font, !type);
        }
        if (this.selected) {
            g.setColor(Color.decode("#27ae60"));
            Rectangle r2 = new Rectangle(x - currentGrow / 4 - 4, y - currentGrow / 4 - 4, width + currentGrow / 2 + 8, height + currentGrow / 2 + 8);
            if (type) {
                g.draw(r2);
            } else {
                g.drawOval(r2.x, r2.y, r2.width, r2.height);
            }
        }
        if (isShowProperty) {
            int count = 0;

            for (Property property : (ArrayList<Property>) (props.clone())) {
                property.fontColor = fontColor;
                count++;
                property.x = this.x + 5;
                property.y = GeneralManager.spaceLine * 3 + this.y + count * (property.getComputeHeight(g));
                property.draw(g);
            }
            resizeWithProperties(g);
        }

    }

    @Override
    public boolean isHover(Point pt) {
        return type ? this.contains(pt) : new Rectangle(x, y, width, width).contains(pt);
    }

}
