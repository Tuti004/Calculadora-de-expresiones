import javax.swing.*;
public class gui{
    public static void main(String args[]) {
        JFrame a = new JFrame("Client");
        a.setSize(500,700);


        JButton b = new JButton("click me");
        b.setBounds(100,90,85,20);
        a.add(b);
        a.setLayout(null);
        a.setVisible(true);
    }
}