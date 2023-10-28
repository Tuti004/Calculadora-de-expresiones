package org.example;


import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ReadImage {
    String text = "";
    public String leerImagen() {
        Tesseract tesseract = new Tesseract();
        try {

            tesseract.setDatapath("Tess4J-3.4.8-src\\Tess4J\\tessdata");

            // the path of your tess data folder
            // inside the extracted file
            text = tesseract.doOCR(new File("image.png"));

        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }
}