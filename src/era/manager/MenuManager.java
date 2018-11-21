/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.manager;

import era.menu.ActionColor;
import era.menu.ActionEntite;
import era.menu.ActionFontColor;
import era.menu.MenuContainerRect;
import era.menu.MenuItemRect;
import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Administrateur
 */
public class MenuManager {

    public static MenuContainerRect menu;

    public static void show(int x, int y, ActionEntite[] actions) {
        GeneralManager.menuScroll = 0;
        menu = new MenuContainerRect(x, y, 0, 0);
        for (ActionEntite action : actions) {
            menu.items.add(new MenuItemRect(x, y, 150, 50, "", null, action, Color.decode("#34495e")));
        }
    }

    public static void show(int x, int y, ActionColor[] actions) {
        GeneralManager.menuScroll = 0;
        menu = new MenuContainerRect(x, y, 0, 0);
        for (ActionColor action : actions) {
            menu.items.add(new MenuItemRect(x, y, 150, 50, "", null, action, action.color));
        }
    }

    public static void show(int x, int y, ActionFontColor[] actions) {
        GeneralManager.menuScroll = 0;
        menu = new MenuContainerRect(x, y, 0, 0);
        for (ActionFontColor action : actions) {
            menu.items.add(new MenuItemRect(x, y, 150, 50, "", null, action, action.color));
        }
    }

    public static void hide() {
        menu = null;
    }

    public static boolean click(int x, int y) {
        if (menu != null) {
            for (MenuItemRect item : menu.items) {
                if (item.contains(new Point(x, y))) {
                    item.click();
                    hide();
                    return true;
                }
            }

        }
        return false;
    }
}
