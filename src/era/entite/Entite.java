/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.entite;

import era.interfaces.IDrawable;
import era.interfaces.IHoverable;
import era.manager.GeneralManager;
import era.menu.ActionEntite;
import era.ui.RenameElement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Administrateur
 */
public abstract class Entite extends Rectangle implements IDrawable, IHoverable {

    public ArrayList<Property> props = new ArrayList<>();
    public int currentGrow = 0;
    public int maxGrow = 20;
    public Color color;
    public Color fontColor;
    public String text = "";
    public boolean hover;
    public boolean selected;
    public boolean isShowProperty = true;
    public String comment = "";

    public Entite() {
    }

    public ArrayList<Property> getProps() {
        return (ArrayList<Property>) props.clone();
    }

    public Entite(int i, int i1, int i2, int i3) {
        super(i, i1, i2, i3);

    }

    public int getCurrentGrow() {
        return currentGrow;
    }

    public void setCurrentGrow(int currentGrow) {
        this.currentGrow = currentGrow;
    }

    public int getMaxGrow() {
        return maxGrow;
    }

    public void setMaxGrow(int maxGrow) {
        this.maxGrow = maxGrow;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<ActionEntite> getActions() {
        ArrayList<ActionEntite> actions = new ArrayList<>();
        actions.add(new ActionEntite("Renommer") {
            @Override
            public void doAction() {
                RenameElement r = new RenameElement(Entite.this);
                r.setLocationRelativeTo(era.Era.fenetre);
                r.setVisible(true);
            }
        });
        return actions;
    }

    public void resizeWithProperties(Graphics2D g) {
        int nw = 0;
        int nh = 0;
        for (Property property : (ArrayList<Property>) (props.clone())) {
            int cw = property.getComputeWidth(g);
            nw = nw < cw ? cw : nw;
            nh += property.getComputeHeight(g);
        }
        nh += GeneralManager.spaceLine * 4;
        this.width = this.width < nw + 10 ? nw + 10 : this.width;
        this.height = this.height < nh ? nh : this.height;
        for (Property prop : getProps()) {
            prop.height = prop.getComputeHeight(g);
            prop.width = this.width;
        }

    }
    
}
