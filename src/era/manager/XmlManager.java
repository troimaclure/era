/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.manager;

import era.entite.Entite;
import java.awt.Point;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrateur
 */
public class XmlManager {

    public static File currentFile;

    public static void save() {
        XMLEncoder e;
        try {
            if (GeneralManager.currentFile == null) {
                JFileChooser fil = new JFileChooser();
                fil.showSaveDialog(era.Era.fenetre);
                try {
                    GeneralManager.currentFile = fil.getSelectedFile().getAbsolutePath() + (fil.getSelectedFile().getAbsolutePath().contains(".xml") ? "" : ".xml");
                } catch (NullPointerException ex) {
                }
            }
            Wrapper w = new Wrapper();
            w.entites = EntiteManager.entites;
            w.name = GeneralManager.name;
            w.currentPoint = GeneralManager.translate;
            e = new XMLEncoder(
                    new BufferedOutputStream(
                            new FileOutputStream(GeneralManager.currentFile)));
            e.writeObject(w);
            e.close();
        } catch (FileNotFoundException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public static void reload() {

        try (XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(currentFile)))) {
            Wrapper wrap = (Wrapper) d.readObject();
            EntiteManager.entites = wrap.entites;
            GeneralManager.translate = wrap.currentPoint;
            GeneralManager.name = wrap.name;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void load() {
        String path = "";
        Properties prop = new Properties();
        File pref = null;
        try {
            String applicationPath = new File(".").getCanonicalPath();
            pref = new File(applicationPath + File.separator + "preference.ini");
            if (!pref.exists()) {
                pref.createNewFile();
            }

            InputStream in = new FileInputStream(pref);
            prop.load(in);
            in.close();
            path = prop.getProperty("workingDir");
            if (path == null) {
                path = applicationPath;
                prop.setProperty("workingDir", path);
                OutputStream out = new FileOutputStream(pref);
                prop.store(out, "Configuration file");
            }
        } catch (IOException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (path == null)
            path = "";
        JFileChooser fil = new JFileChooser(path);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");

        fil.setFileFilter(filter);
        fil.showOpenDialog(era.Era.fenetre);
        File file = fil.getSelectedFile();
        if (file == null)
            return;
        try {
            currentFile = file;
            try (XMLDecoder d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(currentFile)))) {
                Wrapper wrap = (Wrapper) d.readObject();
                EntiteManager.entites = wrap.entites;
                GeneralManager.translate = wrap.currentPoint;
                GeneralManager.name = wrap.name;
                GeneralManager.currentFile = file.getAbsolutePath();
            }
            prop.setProperty("workingDir", file.getAbsolutePath());
            OutputStream out = new FileOutputStream(pref);
            prop.store(out, "Configuration file");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static class Wrapper {

        public ArrayList<Entite> entites = new ArrayList<>();
        public String name = "";
        public Point currentPoint = new Point();

        public Wrapper() {
        }

    }
}
