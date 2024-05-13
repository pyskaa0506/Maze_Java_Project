import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(new MainFrame().MainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}