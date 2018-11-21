/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Administrateur
 */
public class GeneralManager {

    public static Font font = new Font("Segoe UI", 1, 16);
    public static Color background = new Color(51, 51, 51);
    public static Color colorSelected = Color.decode("#2980b9");
    public static Point cursor = new Point(0, 0);
    public static float zoom = 1.0f;
    public static Point translate = new Point(0, 0);
    public static Point startingTranslate = new Point(0, 0);
    public static int menuScroll = 0;
    public static Rectangle2D selector;
    public static boolean isTranslateMode;
    public static int spaceLine = 10;
    public static String currentFile;
    public static String name = "";
    public static boolean alignAuto = false;
    public static int caseSize = 50;
    public static int arrowWidth = 20;
    public static int arrowHeight = 20;
    public static Point startingTranslateRelative = new Point(0, 0);

    public static Point getRelativeCursor() {
        float x = (((cursor.x) - (translate.x)) / zoom);
        float y = (((cursor.y) - (translate.y)) / zoom);
        return new Point((int) x, (int) y);
    }

    public static Point getCursor() {

        return cursor;
    }

    public static void setCursor(int x, int y) {
        cursor.x = x;
        cursor.y = y;
    }

    public static void translateRelative(int x, int y) {
        translate.x = (int) (x - (startingTranslate.x));
        translate.y = (int) (y - (startingTranslate.y));
    }

    public static void translate(int x, int y) {
        translate.x = -(x - era.Era.panneau.getWidth() / 2);
        translate.y = -(y - era.Era.panneau.getHeight() / 2);
    }

    public static void createSelector(int x1, int y1, int x2, int y2) {
        selector = new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public static void toggleAlignAuto() {
        alignAuto = !alignAuto;
    }

}
