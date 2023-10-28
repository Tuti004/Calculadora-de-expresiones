package org.example;
/**
 * importa la clase FileWriter para poder usar los archivos csv.
 * importa la clase BufferedWriter para escribir informacion en el archivo
 * importa la clase IOException para mandar una señal de que ha habido una excepcion de algun tipo
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * La clase LogFile escribe en el archivo csv. los datos del usuario
 */
public class LogFile {
    public void AppendStringToFile (String data, String usuario) {
        String fileName = "";
        if(usuario != ""){
            fileName = (usuario + ".csv");
        }
        else{
            fileName = "LogFile.csv";
        }
/**
 * escribe la informacion del usuario en el archivo csv
 */
        try {
            // Create a FileWriter in append mode
            FileWriter fileWriter = new FileWriter(fileName, true);

            // Create a BufferedWriter to write the string
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the string to the file with a newline character
            bufferedWriter.write(data);
            bufferedWriter.newLine();

            // Close the BufferedWriter and FileWriter
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Log guardado.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
