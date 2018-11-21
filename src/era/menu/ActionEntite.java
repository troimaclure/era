/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.menu;

/**
 *
 * @author Administrateur
 */
public abstract class ActionEntite {

    public String text;

    public ActionEntite(String text) {
        this.text = text;

    }

    public abstract void doAction();
}
