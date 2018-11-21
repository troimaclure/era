/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Administrateur
 */
public class EntiteEmpty extends Entite {

    public EntiteEmpty() {
    }

    public EntiteEmpty(int i, int i1, int i2, int i3) {
        super(i, i1, i2, i3);
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public boolean isHover(Point pt) {
        return false;
    }

}
