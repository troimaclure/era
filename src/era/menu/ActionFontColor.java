/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.menu;

import era.manager.EntiteManager;
import java.awt.Color;

/**
 *
 * @author Administrateur
 */
public class ActionFontColor extends ActionEntite {

    public Color color;

    public ActionFontColor(String text, Color color) {
        super(text);
        this.color = color;
    }

    @Override
    public void doAction() {
        EntiteManager.getEntites().stream().filter(e -> e.selected).forEach((e) -> {
            e.fontColor = this.color;
        });
    }
}
