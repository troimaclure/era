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

/**
 *
 * @author Administrateur
 */
public class SimpleLine extends AbstractLine {

    public SimpleLine() {
    }

    public SimpleLine(Entite source, Entite target) {
        this.source = source;
        this.target = target;
        this.color = Color.white;
        this.fontColor = Color.black;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        Line2D.Double a = new Line2D.Double(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2);
        if (source == null || target == null)
            return;
        if (!arrowed) {
            g.drawLine(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2);
        } else {
            Point p = Drawutils.calculateIntersectionPoint(a, target);
            if (p != null) {
                this.drawArrowLine(g, source.x + source.width / 2, source.y + source.height / 2, p.x, p.y, GeneralManager.arrowHeight, GeneralManager.arrowWidth);
            }
        }
        Rectangle z = a.getBounds();
        z.x += 20;
        z.y += 20;
        Drawutils.centerStringWithEffect(g, z, text, GeneralManager.font);
    }

    @Override
    public boolean isHover(Point pt) {
        return Line2D.ptSegDist(source.x + source.width / 2, source.y + source.height / 2, target.x + target.width / 2, target.y + target.height / 2, pt.x, pt.y) < 3f;
    }

}
