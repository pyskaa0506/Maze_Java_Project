import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Solver");
            frame.setContentPane(new MainFrame().MainFrame);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

/*

            // this should not be here, it should be somewhere in MainFrame.java (I think?) but im too scared to touch it for now
            Maze maze = new Maze();
            //this should be somewhere where gui "text file" is called. Also path file should be given here as a parameter to this function (or something like that)
            maze.load();
            // this should be called when "Solve maze" button is pressed
            // right now I think it can be called only once, more than once will cause an error (like never ending loop)
            maze.solve();

            //to show you how it looks like
            maze.printMaze();
            maze.printPath();
 */