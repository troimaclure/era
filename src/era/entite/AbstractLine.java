/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author Administrateur
 */
public abstract class AbstractLine extends Entite {

    public Entite source;
    public Entite target;
    public boolean dotted;
    public int stroke = 4;
    public int maxGrowLine = 10;
    public boolean arrowed = true;

    public AbstractLine() {
    }

    public AbstractLine(Entite source, Entite target) {
        this.source = source;
        this.target = target;
        this.color = Color.WHITE;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        currentGrow += hover ? (currentGrow <= maxGrowLine ? 2 : 0) : currentGrow == 0 ? 0 : (-2);
        if (dotted) {
            //float dash[] = {10.0f};
            Stroke dashed = new BasicStroke(stroke + currentGrow, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
        } else {
            g.setStroke(new BasicStroke(stroke + currentGrow));
        }

    }

    public void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

}
