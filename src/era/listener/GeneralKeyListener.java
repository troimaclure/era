/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.listener;

import era.entite.Entite;
import era.entite.Property;
import era.manager.EntiteManager;
import era.manager.ExportManager;
import era.manager.GeneralManager;
import static era.manager.GeneralManager.translate;
import era.manager.MenuManager;
import era.manager.XmlManager;
import era.menu.ActionColor;
import era.menu.ActionFontColor;
import era.thread.RepaintThread;
import era.ui.ColorChooser;
import era.ui.CreateElement;
import era.ui.CreateElementJoin;
import era.ui.CreateProperty;
import era.ui.Help;
import era.ui.RenameProject;
import era.ui.SearchElement;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class GeneralKeyListener extends KeyAdapter {

    public boolean isEntitySelected = false;

    @Override
    public void keyReleased(KeyEvent ke) {
        RepaintThread.setRepaintCounter(1);
        System.out.println(ke.getKeyCode());
        switch (ke.getKeyCode()) {
            case 32: //space
                GeneralManager.isTranslateMode = false;
                era.Era.panneau.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                break;
            case 9: //tab
                EntiteManager.selectNext();
                break;
            case 18: //alt
                EntiteManager.removedMode = false;
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        RepaintThread.setRepaintCounter(1);;
        switch (ke.getKeyCode()) {
            case 18: //alt
                EntiteManager.removedMode = true;
                break;
            case 116://f5
                XmlManager.reload();
                break;
            case 37: //left
                isEntitySelected = false;
                EntiteManager.getEntites().stream().filter((e) -> e.selected).forEach((t) -> {
                    isEntitySelected = true;
                    if (ke.isControlDown()) {
                        t.width -= 50;
                    } else {
                        t.x -= 50;
                    }
                });
                if (!isEntitySelected) {
                    translate.x += (50);
                }
                break;
            case 38: //up
                if (ke.isAltDown()) {
                    upWithAlt();
                } else {
                    upWithoutAlt(ke);
                }

                break;
            case 39: //right
                isEntitySelected = false;
                EntiteManager.getEntites().stream().filter((e) -> e.selected).forEach((t) -> {
                    isEntitySelected = true;
                    if (ke.isControlDown()) {
                        t.width += 50;
                    } else {
                        t.x += 50;
                    }
                });
                if (!isEntitySelected) {
                    translate.x -= (50);
                }
                break;
            case 40://down            
                if (ke.isAltDown()) {
                    downWithAlt();
                } else {
                    downWithoutAlt(ke);
                }
                break;
            case 27: //echap
                MenuManager.hide();
                EntiteManager.currentLine = null;
                break;
            case 127: //supr
                EntiteManager.deleteSelected();
                return;
            case 32: //space
                GeneralManager.isTranslateMode = true;
                era.Era.panneau.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                break;
            case 74:
                CreateElementJoin c = new CreateElementJoin();
                c.setLocationRelativeTo(era.Era.fenetre);
                c.setVisible(true);

                break;
            case 79: //o
                if (ke.isControlDown()) {
                    XmlManager.load();
                }
                break;
            case 83: //s
                if (ke.isControlDown()) {
                    XmlManager.save();
                }
                break;
            case 82: //r
                if (ke.isControlDown()) {
                    new RenameProject();
                }
                break;
            case 69: //e
                if (ke.isControlDown()) {
                    ExportManager.exportPng();
                }
                break;
            case 80: //p
                if (ke.isControlDown()) {
                    era.Era.fenetre.toggleFullScreen();
                }
                break;
            case 66: //b
                if (ke.isControlDown()) {
                    new ColorChooser(era.Era.fenetre, true).setVisible(true);
                }
                break;
            case 65: //a
                if (ke.isControlDown()) {
                    EntiteManager.selectAll();
                }
                break;
            case 71: //g
                if (ke.isControlDown()) {
                    GeneralManager.toggleAlignAuto();
                }
                break;
            case 44: //?
                if (ke.isShiftDown()) {
                    new Help(era.Era.fenetre, true);
                }
                break;
            case 78: //N
                if (ke.isControlDown()) {
                    EntiteManager.entites.clear();
                    GeneralManager.translate = new Point(0, 0);
                    GeneralManager.currentFile = null;
                }
                break;
            case 70: //F
                if (ke.isControlDown()) {
                    new SearchElement();
                }
                break;
            case 67:
                if (ke.isControlDown()) {
                    EntiteManager.copy();
                }
                break;
            case 86:
                if (ke.isControlDown()) {
                    EntiteManager.paste();
                }
                break;

        }
        switch (ke.getKeyChar()) {

            case 'p':
                if (EntiteManager.getSelected() != null) {
                    CreateProperty pr = new CreateProperty();
                    pr.setLocationRelativeTo(era.Era.fenetre);
                    pr.setVisible(true);
                }

                break;
            case 'a':
                EntiteManager.lineArrowed();
                break;
            case 'r':
                EntiteManager.rename();
                break;

            case 'n':

                CreateElement c = new CreateElement();
                c.setLocationRelativeTo(era.Era.fenetre);
                c.setVisible(true);
                Point p = GeneralManager.getRelativeCursor();
                c.x = p.x;
                c.y = p.y;

//                EntiteManager.entites.add(EntiteFactory.createRect((int) GeneralManager.cursor.getX(), (int) GeneralManager.cursor.getY()));
                break;
            case 'c':
                if (ke.isControlDown()) {
                } else {
                    RepaintThread.setRepaintCounter(50);
                    //<editor-fold defaultstate="collapsed" desc="backcolor">
                    MenuManager.show(GeneralManager.getCursor().x, GeneralManager.getCursor().y,
                            new ActionColor[]{
                                new ActionColor("", Color.decode("#ffffff")),
                                new ActionColor("", Color.decode("#000000")),
                                new ActionColor("", Color.decode("#1abc9c")),
                                new ActionColor("", Color.decode("#16a085")),
                                new ActionColor("", Color.decode("#2ecc71")),
                                new ActionColor("", Color.decode("#27ae60")),
                                new ActionColor("", Color.decode("#3498db")),
                                new ActionColor("", Color.decode("#2980b9")),
                                new ActionColor("", Color.decode("#9b59b6")),
                                new ActionColor("", Color.decode("#8e44ad")),
                                new ActionColor("", Color.decode("#34495e")),
                                new ActionColor("", Color.decode("#2c3e50")),
                                new ActionColor("", Color.decode("#f1c40f")),
                                new ActionColor("", Color.decode("#f39c12")),
                                new ActionColor("", Color.decode("#e67e22")),
                                new ActionColor("", Color.decode("#d35400")),
                                new ActionColor("", Color.decode("#e74c3c")),
                                new ActionColor("", Color.decode("#c0392b")),
                                new ActionColor("", Color.decode("#ecf0f1")),
                                new ActionColor("", Color.decode("#bdc3c7")),
                                new ActionColor("", Color.decode("#95a5a6")),
                                new ActionColor("", Color.decode("#7f8c8d"))
                            });
//</editor-fold>
                }

                break;

            case 'v':

                break;
            case 'f':
                RepaintThread.setRepaintCounter(50);
                //<editor-fold defaultstate="collapsed" desc="fontColor">
                MenuManager.show(GeneralManager.getCursor().x, GeneralManager.getCursor().y,
                        new ActionFontColor[]{
                            new ActionFontColor("", Color.decode("#ffffff")),
                            new ActionFontColor("", Color.decode("#000000")),
                            new ActionFontColor("", Color.decode("#1abc9c")),
                            new ActionFontColor("", Color.decode("#16a085")),
                            new ActionFontColor("", Color.decode("#2ecc71")),
                            new ActionFontColor("", Color.decode("#27ae60")),
                            new ActionFontColor("", Color.decode("#3498db")),
                            new ActionFontColor("", Color.decode("#2980b9")),
                            new ActionFontColor("", Color.decode("#9b59b6")),
                            new ActionFontColor("", Color.decode("#8e44ad")),
                            new ActionFontColor("", Color.decode("#34495e")),
                            new ActionFontColor("", Color.decode("#2c3e50")),
                            new ActionFontColor("", Color.decode("#f1c40f")),
                            new ActionFontColor("", Color.decode("#f39c12")),
                            new ActionFontColor("", Color.decode("#e67e22")),
                            new ActionFontColor("", Color.decode("#d35400")),
                            new ActionFontColor("", Color.decode("#e74c3c")),
                            new ActionFontColor("", Color.decode("#c0392b")),
                            new ActionFontColor("", Color.decode("#ecf0f1")),
                            new ActionFontColor("", Color.decode("#bdc3c7")),
                            new ActionFontColor("", Color.decode("#95a5a6")),
                            new ActionFontColor("", Color.decode("#7f8c8d"))
                        });
//</editor-fold>
                break;
            case 't':
                EntiteManager.setType();
                break;
            case 'd':
                EntiteManager.setLineStyle();
                break;
            case 's':
                EntiteManager.setLineDirection();
                break;
            case 'g':
                EntiteManager.group();
                break;

        }
    }

    private void downWithoutAlt(KeyEvent ke) {
        //down
        isEntitySelected = false;
        EntiteManager.getEntites().stream().filter((e) -> e.selected).forEach((t) -> {
            isEntitySelected = true;
            if (ke.isControlDown()) {
                t.height += 50;
            } else {
                t.y += 50;
            }
        });
        if (!isEntitySelected) {
            translate.y -= (50);
        }
        return;
    }

    private void upWithoutAlt(KeyEvent ke) {
        isEntitySelected = false;
        EntiteManager.getEntites().stream().filter((e) -> e.selected).forEach((t) -> {
            isEntitySelected = true;
            if (ke.isControlDown()) {
                t.height -= 50;
            } else {
                t.y -= 50;
            }
        });
        if (!isEntitySelected) {
            translate.y += (50);
        }
    }

    private void upWithAlt() {
        EntiteManager.getEntites().forEach((entite) -> {
            entite.props.stream().filter((prop) -> (prop.selected)).forEachOrdered((prop) -> {
                EntiteManager.up(entite, prop);
            });
        });

    }

    private void downWithAlt() {
        EntiteManager.getEntites().forEach((entite) -> {
            entite.props.stream().filter((prop) -> (prop.selected)).forEachOrdered((prop) -> {
                EntiteManager.down(entite, prop);
            });
        });
    }
}
