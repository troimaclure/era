/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.menu;

import era.entite.Entite;
import era.interfaces.IDrawable;
import era.manager.GeneralManager;
import era.utils.Drawutils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Administrateur
 */
public class MenuItemRect extends Rectangle implements IDrawable {

    String text;
    public Entite entite;
    public ActionEntite action;
    private Color color;
    private Color fontColor;
    public boolean setted;
    public int currentY;

    public MenuItemRect() {
    }

    public MenuItemRect(int i, int i1, int i2, int i3, String text, Entite entite, ActionEntite action, Color color) {
        super(i, i1, i2, i3);
        this.text = text;
        this.entite = entite;
        this.action = action;
        this.color = color;
        this.fontColor = Color.white;
    }

    public MenuItemRect(int i, int i1, int i2, int i3, String text, Entite entite, ActionEntite action, Color color, Color fontColor) {
        super(i, i1, i2, i3);
        this.text = text;
        this.entite = entite;
        this.action = action;
        this.color = color;
        this.fontColor = fontColor;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(5));
        g.fill(this);
        g.setColor(fontColor);
        g.draw(this);
        Drawutils.centerString(g, this, text, GeneralManager.font, false);
        if (setted) {
            this.y = currentY + (GeneralManager.menuScroll);
        }
    }

    public void click() {
        action.doAction();
    }

}
