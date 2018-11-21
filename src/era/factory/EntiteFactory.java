/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.factory;

import era.entite.EntiteEmpty;
import era.entite.EntiteJoin;
import era.entite.EntiteRect;

/**
 *
 * @author Administrateur
 */
public class EntiteFactory {

    public static EntiteRect createRect(int x, int y, String text) {
        return new EntiteRect(x, y, 50, 50, text);
    }

    public static EntiteEmpty createEmpty(int x, int y) {
        return new EntiteEmpty(x, y, 1, 1);
    }

}
