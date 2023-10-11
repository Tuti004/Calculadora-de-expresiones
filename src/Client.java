import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 5000);
            System.out.println("Client Connected");

        }catch(Exception e) {
            System.out.println(e);
        }
    }
}

}
