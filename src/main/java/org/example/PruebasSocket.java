/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;


/**
 *
 * @author dylan
 */
public class PruebasSocket {

    private static int port;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // Take an available port
        ServerSocket s = new ServerSocket(5555);
        port = s.getLocalPort();
        s.close();

        Executors.newSingleThreadExecutor().submit(() -> new EchoMultiServer().start(port));
        Thread.sleep(2000);

        EchoClient client1 = new EchoClient();
        client1.startConnection("127.0.0.1", port);
        String msg1 = client1.sendMessage("hello");
        System.out.println(msg1);
        String msg2 = client1.sendMessage("world");
        System.out.println(msg2);
        
    
        EchoClient client2 = new EchoClient();
        client2.startConnection("127.0.0.1", port);
        String msg3 = client2.sendMessage("hello");
        System.out.println(msg3);
        String msg4 = client2.sendMessage("world");
        System.out.println(msg4);


        EchoClient client3 = new EchoClient();
        client3.startConnection("127.0.0.1", port);
        String msg5 = client3.sendMessage("hello");
        System.out.println(msg5);
        String msg6 = client3.sendMessage("world");
        System.out.println(msg6);
        
        String terminate2 = client2.sendMessage(".");
        System.out.println(terminate2);

        client2.stopConnection();
        
        String terminate = client1.sendMessage(".");
        System.out.println(terminate);

        client1.stopConnection();
        
        String terminate7 = client3.sendMessage(".");
        System.out.println(terminate7);

        client3.stopConnection();
        
        // Recordar detenet programa
    }
}
