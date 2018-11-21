/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import era.manager.GeneralManager;
import era.utils.Drawutils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class CornerLine extends AbstractLine {

    public ArrayList<Point> ancres = new ArrayList<>();

    public CornerLine() {
    }

    public CornerLine(Entite source, Entite target) {
        super(source, target);
        this.color = Color.WHITE;
        this.fontColor = Color.black;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (source == null || target == null)
            return;
        g.drawLine(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, source.y + source.height / 2);

        if (!arrowed) {
            g.drawLine(target.x + target.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2);
        } else {
            Line2D.Double x = new Line2D.Double(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, source.y + source.height / 2);

            Point p = Drawutils.calculateIntersectionPoint(x, target);
            if (p != null) {
                this.drawArrowLine(g, source.x + source.width / 2, source.y + source.height / 2, p.x, p.y, GeneralManager.arrowHeight, GeneralManager.arrowWidth);
            } else {
                Line2D.Double a = new Line2D.Double(target.x + target.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2);
                p = Drawutils.calculateIntersectionPoint(a, target);

                if (p != null)
                    this.drawArrowLine(g, target.x + target.width / 2, source.y + source.height / 2, p.x, p.y, GeneralManager.arrowHeight, GeneralManager.arrowWidth);
            }
        }
        Rectangle r = new Rectangle(target.x + target.width / 2, source.y + source.height / 2, this.width, this.height);
        r.x += 20;
        r.y += 20;
        Drawutils.centerStringWithEffect(g, r, text, GeneralManager.font);
    }

    @Override
    public boolean isHover(Point pt) {
        return (Line2D.ptSegDist(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, source.y + source.height / 2, pt.x, pt.y) < stroke + currentGrow
                || Line2D.ptSegDist(target.x + target.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2, pt.x, pt.y) < stroke + currentGrow);

    }
}
