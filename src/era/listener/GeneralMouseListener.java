/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.listener;

import era.entite.CornerLine;
import era.entite.Entite;
import era.entite.LineWrapper;
import era.entite.Property;
import era.factory.EntiteFactory;
import era.manager.EntiteManager;
import era.manager.GeneralManager;
import static era.manager.GeneralManager.zoom;
import era.manager.MenuManager;
import era.thread.RepaintThread;
import era.ui.RenameProperty;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.SwingUtilities;

/**
 *
 * @author Administrateur
 */
public class GeneralMouseListener extends MouseAdapter {

    private AffineTransform coordTransform;

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {
        GeneralManager.selector = null;
        EntiteManager.currentHover = null;
        Entite e = EntiteManager.getHoverNotLine();
        if (EntiteManager.currentLine != null && e != null && e != EntiteManager.currentLine.source) {
            EntiteManager.entites.add(new LineWrapper(EntiteManager.currentLine.source, e));
            EntiteManager.currentLine = null;
        } else if (EntiteManager.currentLine != null && e == null) {
            EntiteManager.currentLine = null;
        }
        EntiteManager.mouseRelease();
    }

    private void leftClick(MouseEvent me) {
        Point p = GeneralManager.getRelativeCursor();
        if (me.getClickCount() > 1) {
            Property prop = EntiteManager.getPropHover();
            if (prop != null) {
                RenameProperty rename = new RenameProperty(prop);
                rename.setLocationRelativeTo(era.Era.fenetre);
                rename.setVisible(true);
            } else {
                EntiteManager.rename();
            }
        }
        if (!MenuManager.click(GeneralManager.getCursor().x, GeneralManager.getCursor().y)) {
            if (me.isShiftDown()) {
                EntiteManager.clickShift(p.x, p.y);
            } else if (me.isControlDown()) {
                EntiteManager.clickControl(p.x, p.y);
            } else {
                EntiteManager.click();
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        RepaintThread.setRepaintCounter(25);
        GeneralManager.setCursor(me.getX(), me.getY());
        Point p = GeneralManager.getRelativeCursor();
        EntiteManager.mouseMove(p.x, p.y);
        EntiteManager.moveCurrentEmpty(p);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent me) {
        RepaintThread.setRepaintCounter(25);
        Point p = GeneralManager.getRelativeCursor();
//        GeneralManager.translate((int) (GeneralManager.translate.x / zoom), (int) (GeneralManager.translate.y / zoom));
//        startingTranslate.x = (int) (p.x);
//        startingTranslate.y = (int) (p.y);
        if (me.isControlDown()) {
            EntiteManager.getEntites().stream().filter(e -> e.selected).forEach((e) -> {
                e.width += me.getWheelRotation() == -1 ? 20 : (-20);
                e.height += me.getWheelRotation() == -1 ? 20 : (-20);
            });
        } else {
            if (MenuManager.menu == null) {
                zoom += me.getWheelRotation() == -1 ? (zoom < 4.5f ? 0.1f : 0) : (zoom > 0.3f ? -0.1f : 0);
            } else {
                GeneralManager.menuScroll += me.getWheelRotation() == -1 ? 20 : (-20);
            }
        }

//        int tX = GeneralManager.translate.x;
//        int tY = GeneralManager.translate.y;
//
//        int dx = Era.panneau.getWidth() / 2;
//        int dy = Era.panneau.getHeight() / 2;
//
//        int rx = GeneralManager.cursor.x - dx;
//        int ry = GeneralManager.cursor.y - dy;
//        GeneralManager.translate((int) (tX + tX / zoom), (int) (tY + tY / zoom));
//        GeneralManager.translate(tX + rx, tY + ry);
//        float x = (startingTranslate.x / zoom);
//        float y = (startingTranslate.y / zoom);
//        GeneralManager.translate((int) x, (int) y);
//        System.out.println(GeneralManager.zoom);
    }

    private Point2D.Float transformPoint(Point p1) throws NoninvertibleTransformException {
//        System.out.println("Model -> Screen Transformation:");
//        showMatrix(coordTransform);
        coordTransform = new AffineTransform();
        AffineTransform inverse = coordTransform.createInverse();
//        System.out.println("Screen -> Model Transformation:");
//        showMatrix(inverse);

        Point2D.Float p2 = new Point2D.Float();
        inverse.transform(p1, p2);
        return p2;
    }

    private void showMatrix(AffineTransform at) {
        double[] matrix = new double[6];
        at.getMatrix(matrix);  // { m00 m10 m01 m11 m02 m12 }
        int[] loRow = {0, 0, 1};
        for (int i = 0; i < 2; i++) {
            System.out.print("[ ");
            for (int j = i; j < matrix.length; j += 2) {
            }
        }
        System.out.print("[ ");
        for (int i = 0; i < loRow.length; i++) {
        }

        System.out.print("]\n");

    }

    @Override
    public void mouseDragged(MouseEvent me) {
        RepaintThread.setRepaintCounter(25);
        GeneralManager.setCursor(me.getX(), me.getY());
        Point p = GeneralManager.getRelativeCursor();
        EntiteManager.moveCurrentEmpty(p);
        if (EntiteManager.currentLine != null) {
            EntiteManager.mouseMove(p.x, p.y);
        }
        if (SwingUtilities.isLeftMouseButton(me)) {
            Entite e = EntiteManager.getCurrentHover();
            if (GeneralManager.isTranslateMode) {
                GeneralManager.translateRelative(me.getX(), me.getY());
            } else if (e != null) {
                EntiteManager.moveElement(p, e);
                EntiteManager.mouseMove(p.x, p.y);
            } else {
                GeneralManager.createSelector(GeneralManager.startingTranslateRelative.x, GeneralManager.startingTranslateRelative.y, p.x, p.y);
                EntiteManager.checkEntiteSelector(me.isShiftDown());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        RepaintThread.setRepaintCounter(10);
        GeneralManager.startingTranslate.x = me.getX() - GeneralManager.translate.x;
        GeneralManager.startingTranslate.y = me.getY() - GeneralManager.translate.y;
        GeneralManager.startingTranslateRelative.x = GeneralManager.getRelativeCursor().x;
        GeneralManager.startingTranslateRelative.y = GeneralManager.getRelativeCursor().y;

        if (GeneralManager.selector == null) {
            if (SwingUtilities.isLeftMouseButton(me)) {
                leftClick(me);
            } else {
                rightClick(me);
            }
        }
    }

    private void rightClick(MouseEvent me) {
        Entite e = EntiteManager.getHover();
        if (e == null) {
            return;
        }
        Point p = GeneralManager.getRelativeCursor();
        EntiteManager.currentEmpty = EntiteFactory.createEmpty(p.x, p.y);
        EntiteManager.currentLine = new CornerLine(e, EntiteManager.currentEmpty);
    }
}
