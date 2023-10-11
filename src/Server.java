import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss= new ServerSocket(5000);
            System.out.println("Waiting for client");
            Socket server=ss.accept();
            System.out.println("Client connected");

        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
