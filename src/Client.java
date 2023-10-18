import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Client");
        JTextField inputField = new JTextField(20);
        JButton sendButton = new JButton("Send");
        JButton exitButton = new JButton("Exit");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);
        panel.add(exitButton);

        frame.add(panel);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try (Socket socket = new Socket("localhost", 5000)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String message = inputField.getText();
                    out.println(message);
                    inputField.setText("");
                }
            });

            exitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        out.println("exit");
                        socket.close();
                        System.exit(0);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            while (true) {
                System.out.println("Server replied: " + in.readLine());
                // You can handle server responses here if needed
                // For example: System.out.println("Server replied: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

