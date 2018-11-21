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
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class EntiteJoin extends Entite {

    public ArrayList<Entite> entites = new ArrayList<>();

    public EntiteJoin(String text) {
        this.text = text;
        fontColor = Color.WHITE;
        color = Color.BLACK;
    }

    @Override
    public void draw(Graphics2D g) {
        currentGrow += hover ? (currentGrow <= maxGrow ? 2 : 0) : currentGrow == 0 ? 0 : (-2);
        Rectangle r = new Rectangle(x - currentGrow / 4, y - currentGrow / 4, width + currentGrow / 2, height + currentGrow / 2);
        g.setColor(color);
        g.draw(r);

        g.setFont(GeneralManager.font);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = GeneralManager.font.getStringBounds(text, frc);
        g.setColor(color);
        Rectangle rect = new Rectangle(this.x + this.width - (int) r2D.getWidth() - 20 + currentGrow / 4, this.y - 50 - currentGrow / 4, (int) r2D.getWidth() + 23, 50);
        g.fill(rect);

        g.setColor(fontColor);
        g.drawString(text, x + width - (int) r2D.getWidth() - 10 + currentGrow / 4, y - (int) r2D.getHeight() - currentGrow / 4);
        if (this.selected) {
            g.setColor(Color.decode("#27ae60"));
            Rectangle r2 = new Rectangle(x - currentGrow / 4 - 4, y - currentGrow / 4 - 4, width + currentGrow / 2 + 8, height + currentGrow / 2 + 8);
            g.draw(r2);
        }

    }

    @Override
    public boolean isHover(Point pt) {
        if (!entites.stream().noneMatch((entite) -> (entite.hover)))
            return false;
        return this.contains(pt);
    }

    public void resize() {
        x = 1000000000;
        y = 1000000000;
        width = -100000;
        height = -1000000;
        x = entites.get(0).x;
        y = entites.get(0).y;
        for (Entite entite : entites) {
            x = entite.x < x ? entite.x : x;
            y = entite.y < y ? entite.y : y;
        }
        for (Entite entite : entites) {
            width = width < entite.x + entite.width - x ? entite.x + entite.width - x : width;
            height = height < entite.y + entite.height - y ? entite.y + entite.height - y : height;
        }
        x -= 10;
        y -= 10;
        width += 20;
        height += 20;
    }

}
