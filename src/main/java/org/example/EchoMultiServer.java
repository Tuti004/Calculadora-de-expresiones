package main.java.org.example;

import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class EchoMultiServer {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new EchoClientHandler(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }

    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    private static class EchoClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        LogFile file = new LogFile();
        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                String ResultadoFinal;

                while ((inputLine = in.readLine()) != null) {
                    try {
                        if ("bye".equals(inputLine)) {
                            out.println("Conexión Terminada");
                            break;
                        }
                        String[] message = inputLine.split(",");
                        System.out.println(message[1]);
                        if (message[1].contains("&") || message[1].contains("|") || message[1].contains("~")) {
                            ExpressionTree2 et = new ExpressionTree2();
                            et.buildTreeFromInfix(message[1]);
                            ResultadoFinal = message[0] + ", " + message[1] + ", " + et.evaluate() + ", " + getDate();
                        } else {
                            ExpressionTree et = new ExpressionTree();
                            et.buildTreeFromInfix(message[1]);
                            ResultadoFinal = message[0] + ", " + message[1] + ", " + et.evaluate() + ", " + getDate();
                        }
                        file.AppendStringToFile(ResultadoFinal,"");
                        out.println(ResultadoFinal);
                    }
                    catch(Exception e){
                        System.out.println("Small server error!");
                    }
                }

                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        EchoMultiServer server = new EchoMultiServer();
        server.start(5555);
    }

}