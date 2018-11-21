/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package era.manager;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import era.Era;
import java.awt.Graphics2D;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;

/**
 *
 * @author Administrateur
 */
public class ExportManager {

    public static void exportPng() {
//        BufferedImage bi = new BufferedImage(era.Era.panneau.getSize().width, era.Era.panneau.getSize().height, BufferedImage.TYPE_INT_ARGB);
//        Graphics g = bi.createGraphics();
//        era.Era.panneau.paint(g);
//        g.dispose();

        //print the panel to pdf
        JFileChooser fil = new JFileChooser();
        fil.showSaveDialog(era.Era.fenetre);
        if (fil.getSelectedFile() == null)
            return;
        String f = fil.getSelectedFile().getAbsolutePath() + (fil.getSelectedFile().getAbsolutePath().contains(".pdf") ? "" : ".pdf");

        EntiteManager.LayoutToPrint r = EntiteManager.getDocumentSize();
        GeneralManager.translate(r.translateX - 50 + Era.panneau.getWidth() / 2, r.translateY - 50 + Era.panneau.getHeight() / 2);
        int width = r.mostRight + Math.abs(r.translateX) + Era.panneau.getWidth() / 2;
        int height = r.mostBottom + Math.abs(r.translateY) + Era.panneau.getHeight() / 2;
        Document document = new Document(new Rectangle(width, height));
        try {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            PdfContentByte contentByte = writer.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D g2 = template.createGraphics(width, height);
            Era.panneau.print(g2);

            g2.dispose();
            contentByte.addTemplate(template, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }

    }
}
