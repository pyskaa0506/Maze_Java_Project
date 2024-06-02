import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Render extends JPanel {
    private char[][] maze;
    private Color backgroundColor = Color.WHITE;
    private Color mazeColor = Color.BLACK;
    private Color solveColor = Color.ORANGE;
    private int cell = 10; // we can change this value to make the maze bigger or smaller;

    private Coordinates entrance;
    private Coordinates exit;

    public Render(char[][] maze) {
        this.maze = maze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (maze != null) {
            for (int y = 0; y < maze.length; y++) {
                for (int x = 0; x < maze[y].length; x++) {
                    if (maze[y][x] == 'X') {
                        g.setColor(mazeColor);
                    }
                    else if (maze[y][x] == ' ') {
                        g.setColor(backgroundColor);
                    }
                    else if (maze[y][x] == '#') {
                        g.setColor(solveColor);
                    }
                    g.fillRect(x * getCell(), y * getCell(), getCell(), getCell());
                    g.setColor(Color.GRAY); // grid color;
                    g.drawRect(x * getCell(), y * getCell(), getCell(), getCell());
                    if (entrance != null){
                        g.setColor(Color.BLUE);
                        g.fillRect(entrance.y*cell, entrance.x*cell, cell,cell);
                    }
                    if(exit != null){
                        g.setColor(Color.GREEN);
                        g.fillRect(exit.y*cell, exit.x*cell, cell,cell);
                    }
                }

            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (maze != null)
        {
            return new Dimension(maze[0].length * getCell(), maze.length * getCell());
        }
        return super.getPreferredSize();
    }

    public void setMaze(char[][] maze) {
        this.maze = maze;
        revalidate();
        repaint();
    }

    public void saveImage(String destinationPath, boolean solved) throws IOException {
        int width = maze[0].length * cell;
        int height = maze.length * cell;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        paintComponent(g);
        g.dispose();
        ImageIO.write(image, "png", new File(destinationPath));
    }

    public void saveMazeAsImage(String filepath) throws IOException{
        saveImage(filepath, false);
    }

    public void saveSolvedAsImage(String filepath) throws IOException {
        saveImage(filepath, true);
    }

    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor(){
        return backgroundColor;
    }

    public void setMazeColor(Color mazeColor){
        this.mazeColor = mazeColor;
    }

    public Color getMazeColor(){
        return mazeColor;
    }

    public void setSolveColor(Color solveColor){
        this.solveColor = solveColor;
    }

    public Color getSolveColor(){
        return solveColor;
    }

    public int getCell() {
        return cell;
    }

    public void setEntrance(Coordinates entrance) {
        this.entrance = entrance;
        repaint();
    }

    public void setExit(Coordinates exit) {
        this.exit = exit;
        repaint();
    }

}
