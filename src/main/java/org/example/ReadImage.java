package org.example;

/**
 *importa libreria File usada para poder accesar el archivo de tessarrect
 */
import java.io.File;
/**
 * importa libreria de tessarrect para que pueda ser usado en el programa
 */
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * La clase ReadImage usa la libreria tessarrect y lee la imagen tomada por la camara
 */
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