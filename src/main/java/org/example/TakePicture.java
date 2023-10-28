package main.java.org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class TakePicture {
    public void takePicture() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture camera = new VideoCapture(0); // Abre la cámara. Ajusta el índice si tienes varias cámaras.

        if (!camera.isOpened()) {
            System.out.println("Error: No se puede abrir la cámara.");
            return;
        }

        Mat frame = new Mat();
        camera.read(frame);

        if (!frame.empty()) {
            System.out.println("Capturando una imagen desde la cámara...");

            String filename = "image.png"; // Nombre del archivo de la imagen capturada.
            Imgcodecs.imwrite(filename, frame);

            System.out.println("Imagen capturada y guardada como " + filename);

        } else {
            System.out.println("No se pudo capturar una imagen desde la cámara.");
        }

        camera.release(); // Libera la cámara.
    }
}