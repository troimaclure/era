/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class Drawutils {

    public static Point calculateIntersectionPoint(Line2D.Double line, Rectangle r) {

        ArrayList<Line2D.Double> lines = new ArrayList<>();

        //left
        if (line.x1 < r.x) {
            lines.add(new Line2D.Double(r.x, r.y, r.x, r.y + r.height));
        } else {
            lines.add(new Line2D.Double(r.x + r.width, r.y, r.x + r.width, r.y + r.height));
        }
        //top
        if (line.y1 < r.y) {
            lines.add(new Line2D.Double(r.x, r.y, r.x + r.width, r.y));
        } else {
            lines.add(new Line2D.Double(r.x, r.y + r.height, r.x + r.width, r.y + r.height));
        }
        ArrayList<Point> pts = new ArrayList<>();
        lines.forEach((l) -> {
            pts.add(get_line_intersection(line, l));
        });
        return getClosed(new Point((int) line.x1, (int) line.y1), pts);
    }

    private static Point getClosed(Point p, ArrayList<Point> pts) {
        Point point = null;
        double dist = 5000;
        for (Point pt : pts) {
            if (pt == null) {
                continue;
            }
            if (point == null) {
                point = pt;
                continue;
            }
            double df = Point2D.distance(p.x, p.y, pt.x, pt.y);
            if (df < dist) {
                point = pt;
                dist = df;
            }
        }
        return point;
    }

    private static Point get_line_intersection(Line2D.Double pLine1, Line2D.Double pLine2) {
        Point result = null;

        double s1_x = pLine1.x2 - pLine1.x1,
                s1_y = pLine1.y2 - pLine1.y1,
                s2_x = pLine2.x2 - pLine2.x1,
                s2_y = pLine2.y2 - pLine2.y1,
                s = (-s1_y * (pLine1.x1 - pLine2.x1) + s1_x * (pLine1.y1 - pLine2.y1)) / (-s2_x * s1_y + s1_x * s2_y),
                t = (s2_x * (pLine1.y1 - pLine2.y1) - s2_y * (pLine1.x1 - pLine2.x1)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            // Collision detected
            result = new Point(
                    (int) (pLine1.x1 + (t * s1_x)),
                    (int) (pLine1.y1 + (t * s1_y)));
        }   // end if

        return result;
    }

    public static void centerString(Graphics2D g, Rectangle r, String s, Font font, boolean circle) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        if (!circle) {
            g.drawString(s, r.x + a, r.y + b);
        } else {
            g.drawString(s, r.x + a, (int) (r.y + r.height / 2 + r2D.getHeight() / 4));
        }
    }

    public static void centerStringWithEffect(Graphics2D g, Rectangle r, String s, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;
        g.setFont(font);
        g.setFont(new Font("Segoe UI", Font.BOLD, 15));
        g.drawString(s, r.x + a, r.y + b);

    }

    public static void centerStringX(Graphics2D g, Rectangle r, String s, Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + rHeight + 5);
    }
}
