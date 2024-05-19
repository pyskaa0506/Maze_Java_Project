import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // this should not be here, it should be for e.g. in MainFrame.java (I think?) but im too scared to touch it for now
//         Maze maze = new Maze();
//            maze.load();
//            maze.solve();

        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(new MainFrame().MainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}