import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {
    private char[][] maze;

    public Render(char[][] maze) {
        this.maze = maze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze != null) {
            /*
            we can change this value to make the maze bigger or smaller;
            remember to change the value to the same in getPreferredSize()
             */
            int сell = 10;
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[y].length; x++) {
                    if (maze[y][x] == 'X') {
                        g.setColor(Color.BLACK);
                    }
                    else if (maze[y][x] == ' ') {
                        g.setColor(Color.WHITE);
                    }
                    else if (maze[y][x] == '#') {
                        g.setColor(Color.RED);
                    }
                    g.fillRect(x * сell, y * сell, сell, сell);
                    g.setColor(Color.GRAY); // grid color;
                    /*
                    later I will try to change the colour of the grid to match the background colour specified by the user
                     */
                    g.drawRect(x * сell, y * сell, сell, сell);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (maze != null)
        {
            int сell = 10;
            return new Dimension(maze[0].length * сell, maze.length * сell);
        }
        return super.getPreferredSize();
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
        revalidate();
        repaint();
    }
}
