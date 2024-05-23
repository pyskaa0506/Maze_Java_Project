import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            remember to change the value to the same in getPreferredSize() and saveImage()
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

    private void saveImage(String filepath, boolean solved) throws IOException {
        int cell = 10;
        int width = maze[0].length*cell;
        int height = maze.length*cell;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        paintComponent(graphics2D);
        graphics2D.dispose();
        ImageIO.write(image, "png", new File(filepath));
    }

    public void saveMazeAsImage(String filepath) throws IOException{
        saveImage(filepath, false);
    }

    public void saveSolvedAsImage(String filepath) throws IOException {
        saveImage(filepath, true);
    }

}
