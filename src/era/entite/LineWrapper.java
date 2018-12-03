/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Administrateur
 */
public class LineWrapper extends AbstractLine {

    public AbstractLine line;
    boolean type;

    public LineWrapper() {
    }

    public LineWrapper(Entite source, Entite target) {
        super(source, target);
        line = type ? new SimpleLine(source, target) : new CornerLine(source, target);
    }

    public void setType() {
        type = !type;
        line = type ? new SimpleLine(source, target) : new CornerLine(source, target);
    }

    @Override
    public void draw(Graphics2D g) {
        line.dotted = dotted;
        line.hover = hover;
        line.selected = selected;
        line.text = text;
        line.color = hover ? Color.decode("#3498db") : selected ? Color.decode("#2980b9") : color;
        line.fontColor = fontColor;
        line.draw(g);
        if (hover) {
            line.color = Color.WHITE;
            g.setStroke(new BasicStroke(stroke / 2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            line.draw(g);
        }
    }

    @Override
    public boolean isHover(Point pt) {
        return line.isHover(pt);
    }

}
