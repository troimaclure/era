/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.manager;

import era.entite.AbstractLine;
import era.entite.Entite;
import era.entite.EntiteEmpty;
import era.entite.EntiteJoin;
import era.entite.EntiteRect;
import era.entite.LineWrapper;
import era.entite.Property;
import era.thread.RepaintThread;
import era.ui.RenameElement;
import era.utils.DeepCopy;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class EntiteManager {

    public static ArrayList<Entite> entites = new ArrayList<>();
    public static EntiteEmpty currentEmpty = null;
    public static AbstractLine currentLine = null;
    public static Entite currentHover;
    private static ArrayList<Entite> arrayCopy;
    private static Entite movedEntite;
    public static boolean removedMode = false;

    public static void mouseMove(int x, int y) {
        Point p = new Point(x, y);
        boolean find = false;
        //entity befor lines 
        for (Entite entite : getEntites()) {
            if (!(entite instanceof AbstractLine) && !(entite instanceof EntiteJoin)) {
                entite.hover = entite.isHover(p);
                if (entite.hover) {
                    find = true;
                }
                for (Property prop : entite.getProps()) {
                    prop.hover = entite.hover && (prop.isHover(p));
                }
            }
        }
        //lines after
        if (!find) {
            for (Entite entite : getEntites()) {
                if ((entite instanceof AbstractLine)) {
                    entite.hover = entite.isHover(p);
                    if (entite.hover) {
                        find = true;
                        break;
                    }
                }
            }
        } else {
            getEntites().stream().filter((entite) -> ((entite instanceof AbstractLine))).forEachOrdered((entite) -> {
                entite.hover = false;
            });
        }
        //join after
        if (!find) {
            for (Entite entite : getEntites()) {
                if ((entite instanceof EntiteJoin)) {
                    entite.hover = entite.isHover(p);
                    if (entite.hover) {
                        break;
                    }
                }
            }
        } else {
            getEntites().stream().filter((entite) -> ((entite instanceof EntiteJoin))).forEachOrdered((entite) -> {
                entite.hover = false;
            });
        }

    }

    public static ArrayList<Entite> getEntites() {
        return (ArrayList<Entite>) entites.clone();
    }

    public static void click() {
        getEntites().forEach((entite) -> {
            entite.selected = entite.hover;
            for (Property prop : entite.getProps()) {
                prop.selected = prop.hover;
            }
        });

    }

    public static void clickShift(int x, int y) {
        Entite entite = getHover();
        if (entite == null)
            return;
        entite.selected = true;
    }

    public static void clickControl(int x, int y) {
        Entite entite = getHover();
        if (entite == null)
            return;
        entite.selected = !entite.selected;
    }

    public static void moveElement(Point point, Entite e) {
        movedEntite = e;
        if (e instanceof EntiteJoin)
            return;
        if (GeneralManager.alignAuto) {
            while (point.x % 50 > 0) {
                point.x--;
            }
            while (point.y % 50 > 0) {
                point.y--;
            }
        }
        e.setLocation(point);
        ArrayList<EntiteJoin> joins = new ArrayList<>();
        for (Entite entite : entites) {
            if (entite instanceof EntiteJoin) {
                joins.add((EntiteJoin) entite);
                if (((EntiteJoin) entite).entites.contains(e) && !removedMode)
                    ((EntiteJoin) entite).resize();
            }
        }
        for (EntiteJoin join : joins) {
            join.isDropping = e.intersects(join) && !join.entites.contains(e);
        }
        if (removedMode) {
            for (EntiteJoin join : joins) {
                if (join.entites.contains(e) && !e.intersects(join)) {
                    join.entites.remove(e);
                }
            }
        }

    }

    public static Entite getHover() {
        return getEntites().stream().filter(d -> d.hover).findFirst().orElse(null);
    }

    public static Entite getCurrentHover() {
        if (currentHover == null) {
            currentHover = getEntites().stream().filter(d -> d.hover).findFirst().orElse(null);
        }
        return currentHover;
    }

    public static Entite getHoverNotLine() {
        return getEntites().stream().filter(d -> d.hover && !(d instanceof LineWrapper)).findFirst().orElse(null);
    }

    public static void moveCurrentEmpty(Point pt) {
        if (currentEmpty != null) {
            currentEmpty.setLocation(pt);
        }

    }

    public static void rename() {
        Entite e = EntiteManager.getEntites().stream().filter(en -> en.selected).findFirst().orElse(null);
        if (e != null) {
            RenameElement r = new RenameElement(e);
            r.setLocationRelativeTo(era.Era.fenetre);
            r.setVisible(true);
        }
    }

    public static void deleteSelected() {
        boolean propSelected = entites.stream().anyMatch((e) -> e.getProps().stream().anyMatch((p) -> p.selected));

        if (propSelected) {
            getEntites().forEach((entite) -> {
                entite.getProps().stream().filter((prop) -> (prop.selected)).forEachOrdered((prop) -> {
                    entite.props.remove(prop);
                });
            });

        } else {
            entites.removeIf((t) -> {
                return t.selected;
            });
            entites.removeIf((t) -> {
                return t instanceof LineWrapper && (!entites.contains(((LineWrapper) t).target) || !entites.contains(((LineWrapper) t).source));
            });
            for (Entite entite : entites) {
                if (entite instanceof EntiteJoin) {
                    EntiteJoin e = ((EntiteJoin) entite);
                    e.entites.removeIf((t) -> {
                        return t.selected;
                    });
                }
            }
        }
    }

    public static void setType() {

        getEntites().stream().filter((entite) -> (entite.selected && entite instanceof LineWrapper)).forEachOrdered((entite) -> {
            ((LineWrapper) entite).setType();
        });

        getEntites().stream().filter((entite) -> (entite.selected && entite instanceof EntiteRect)).forEachOrdered((entite) -> {
            ((EntiteRect) entite).type = !((EntiteRect) entite).type;
        });
    }

    public static void setLineStyle() {
        getEntites().stream().filter((entite) -> (entite.selected && entite instanceof LineWrapper)).forEachOrdered((entite) -> {
            ((LineWrapper) entite).dotted = !((LineWrapper) entite).dotted;
        });

    }

    public static void checkEntiteSelector(boolean isShift) {
        getEntites().forEach((entite) -> {
            entite.selected = isShift ? entite.selected || (entite.intersects(GeneralManager.selector)) : (entite.intersects(GeneralManager.selector));
        });
    }

    public static void lineArrowed() {
        getEntites().stream().filter((entite) -> (entite.selected && entite instanceof LineWrapper)).forEachOrdered((entite) -> {
            ((LineWrapper) entite).line.arrowed = !((LineWrapper) entite).line.arrowed;
        });
    }

    public static void setLineDirection() {
        getEntites().stream().filter((entite) -> (entite.selected && entite instanceof LineWrapper)).forEachOrdered((entite) -> {
            Entite t = ((LineWrapper) entite).line.target;
            Entite s = ((LineWrapper) entite).line.source;
            ((LineWrapper) entite).line.source = t;
            ((LineWrapper) entite).line.target = s;
        });
    }

    public static void selectNext() {

        int index = 0;
        int i = 0;
        for (Entite entite : getEntites()) {
            if (entite.selected) {
                index = i + 1;
            }
            entite.selected = false;
            i++;
        }
        try {
            getEntites().get(index).selected = true;
        } catch (Exception e) {
            try {
                getEntites().get(0).selected = true;
            } catch (Exception ex) {
            }
        }
    }

    public static Property getPropHover() {
        Property prop = null;
        for (Entite entite : getEntites()) {
            prop = entite.getProps().stream().filter((d) -> d.selected).findFirst().orElse(null);
            if (prop != null) {
                break;
            }
        }
        return prop;
    }

    public static Entite getSelected() {
        return getEntites().stream().filter((e) -> e.selected).findFirst().orElse(null);
    }

    public static Entite searchNext(String text, Entite entite) {
        return getEntites().stream().filter((e) -> e != entite && e.text.toLowerCase().contains(text.toLowerCase())).findFirst().orElse(null);
    }

    public static void moveTo(Entite current) {
        GeneralManager.translate(current.x + current.width / 2, current.y + current.height / 2);
    }

    public static void selectOnly(Entite current) {
        getEntites().forEach((entite) -> {
            entite.selected = entite == current;
        });
    }

    public static void group() {
        int size = 0;
        for (Entite entite : getEntites()) {
            if (!entite.selected)
                continue;
            size = entite.width > size ? entite.width : size;
        }
        for (Entite entite : getEntites()) {
            if (!entite.selected)
                continue;
            entite.width = size;
            entite.height = 50;
        }
    }

    public static void selectAll() {
        for (Entite entite : EntiteManager.getEntites()) {
            entite.selected = true;
        }
    }

    public static void copy() {

        EntiteManager.arrayCopy = new ArrayList<>();
        EntiteManager.getEntites().stream().filter((entite) -> (entite.selected)).forEachOrdered((entite) -> {
            arrayCopy.add(entite);
        });

    }

    public static void paste() {
        EntiteManager.arrayCopy.stream().forEachOrdered((entite) -> {
            Entite e = (Entite) DeepCopy.copy(entite);
            e.y += 40;
            EntiteManager.entites.add(e);

        });
        RepaintThread.setRepaintCounter(1);

    }

    public static void join(String text) {

        ArrayList<Entite> selected = new ArrayList<>();
        getEntites().stream().filter((entite) -> (entite.selected) && !(entite instanceof EntiteJoin)).forEachOrdered((entite) -> {
            selected.add(entite);
        });
        if (entites.isEmpty())
            return;
        EntiteJoin j = new EntiteJoin(text);
        j.entites.addAll(selected);
        j.resize();
        entites.add(j);
        RepaintThread.setRepaintCounter(1);

    }

    public static LayoutToPrint getDocumentSize() {

        LayoutToPrint printer = new LayoutToPrint();
        for (Entite entite : getEntites()) {
            printer.translateX = entite.x < printer.translateX ? entite.x : printer.translateX;
            printer.translateY = entite.y < printer.translateY ? entite.y : printer.translateY;
            printer.mostRight = entite.x > printer.mostRight ? entite.x : printer.mostRight;
            printer.mostBottom = entite.y > printer.mostBottom ? entite.y : printer.mostBottom;
        }
        return printer;
    }

    public static void mouseRelease() {
        for (Entite entite : entites) {
            if (entite instanceof EntiteJoin && ((EntiteJoin) entite).isDropping) {
                if (movedEntite != null) {
                    ((EntiteJoin) entite).entites.add(movedEntite);
                }
                ((EntiteJoin) entite).isDropping = false;
            }
        }
    }

    public static class LayoutToPrint {

        int translateX;
        int translateY;
        int mostRight;
        int mostBottom;

        public LayoutToPrint() {
        }

    }
}
