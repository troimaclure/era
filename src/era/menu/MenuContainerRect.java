/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.menu;

import era.interfaces.IDrawable;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class MenuContainerRect extends Rectangle implements IDrawable {

    public ArrayList<MenuItemRect> items = new ArrayList<>();
    private int currentY = 0;

    public MenuContainerRect() {
    }

    public void show() {
        for (MenuItemRect item : items) {
            item.x = x;
            item.x = y;
        }
    }

    public MenuContainerRect(int i, int i1, int i2, int i3) {
        super(i, i1, i2, i3);
    }

    @Override
    public void draw(Graphics2D g) {
        int i = 0;
        boolean flag = false;
        for (MenuItemRect item : items) {

            for (MenuItemRect item1 : items) {
                if (item != item1 && item.intersects(item1)) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                item.y += i;
                i += 2;
            } else if (!item.setted) {
                item.currentY = item.y;
                item.setted = true;
            }
            item.draw(g);
        }
    }

    public void hide() {
    }

}
