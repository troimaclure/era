/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Administrateur
 */
public class Property extends Entite {

    public Property() {
    }

    public String type;
    public String name;
    public Font font = new Font("Segoe UI", 0, 14);
    public Font fontSelected = new Font("Segoe UI", 0, 20);
    public Rectangle commentRect;
    public boolean recalc = false;
    public int order;

    public Property(int number, String type, String name, String comment, int x, int y, int w, int h) {
        super(x, y, w, h);
        this.order = number;
        this.type = type;
        this.name = name;
        this.comment = comment;
        this.commentRect = new Rectangle(x, y - 20, width, 40);
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(hover || selected ? Color.CYAN : fontColor);
        g.setFont(hover || selected ? fontSelected : this.font);
        g.drawString(getMessage(), x, y);
        if (!"".equals(comment)) {
            Stroke stroke = g.getStroke();
            g.setStroke(new BasicStroke(1));
            g.drawLine(x, y + 2, x + getComputeWidth(g), y + 2);
            g.setStroke(stroke);
            if (selected) {
                drawComment(g);
                this.recalc = false;
            } else {
                this.commentRect.x = x;
                this.recalc = true;
            }
        }

    }

    public Rectangle getCommentRect() {
        return commentRect;
    }

    public void setCommentRect(Rectangle commentRect) {
        this.commentRect = commentRect;
    }

    @Override
    public boolean isHover(Point pt) {
        return new Rectangle(x - 4, y - height, width + 8, height).contains(pt);
    }

    public String getMessage() {
        return type + " : " + name;
    }

    public int getComputeWidth(Graphics2D g) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = this.font.getStringBounds(getMessage(), frc);
        return (int) Math.round(r2D.getWidth());
    }

    public int getComputeCommentWidth(Graphics2D g) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = this.font.getStringBounds(getComment(), frc);
        return (int) Math.round(r2D.getWidth());
    }

    public int getComputeHeight(Graphics2D g) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = this.font.getStringBounds(getMessage(), frc);
        return (int) Math.round(r2D.getHeight());
    }

    private void drawComment(Graphics2D g) {
        if (this.commentRect == null) {
            this.commentRect = new Rectangle();
        }
        if (recalc) {
            this.commentRect = new Rectangle(x, y - 20, width, 40);
        }

        this.commentRect.width = this.getComputeCommentWidth(g) + 20;
        if (this.commentRect.intersects(this)) {
            this.commentRect.x -= 10;
        }
        g.setColor(Color.BLACK);
        g.fill(this.commentRect);
        g.setColor(Color.WHITE);
        g.draw(this.commentRect);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(getComment(), this.commentRect.x + 10, this.commentRect.y + 20);
    }

}
