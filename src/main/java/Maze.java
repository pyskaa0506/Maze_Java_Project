import java.io.IOException;
import java.util.List;

public class Maze {
    private char[][] maze;
    private char[][] originalMaze;
    private List<Coordinates> path;
    private String filepath;
    private boolean isBinary;
    Coordinates entrance;
    Coordinates exit;
    Loader loader;
    Solver solver;
    FileDownloader fileDownloader;

    Maze() {
        loader = new Loader();
        solver = new Solver();
        fileDownloader = new FileDownloader();
    }

    public boolean isBinary() {
        return isBinary;
    }

    public boolean load(String filepath) {
        reset();
        this.filepath = filepath;
        this.isBinary = Loader.isBinary(filepath);
        maze = loader.loadMaze(filepath);
        if (maze == null) {
            return false;
        }
        entrance = new Coordinates().getPositionOfLetter(maze, 'P');
        exit = new Coordinates().getPositionOfLetter(maze, 'K');
        new Coordinates().stripMazeOfLetter(maze, 'P');
        new Coordinates().stripMazeOfLetter(maze, 'K');
        return true;
    }

    public void solve() {
        path = solver.solve(maze, entrance, exit);
        if (path == null) {
            MessageUtils.ErrorMessage("No path found");
        }
        for (Coordinates c : path) {
            maze[c.x][c.y] = '#';
        }
    }

    public void downloadTxt(String destinationPath) {
        fileDownloader.downloadTxt(destinationPath, path);
    }
    public void downloadBin(String destinationPath) {
        String sourcePath = this.filepath;
        fileDownloader.downloadBin(destinationPath, sourcePath, path);
    }

    public char[][] getMaze() {
        return maze;
    }

    public void reset() {
        maze = null;
        originalMaze = null;
        path = null;
        filepath = null;
        isBinary = false;
        entrance = null;
        exit = null;
    }


    public void removePath() {
        if (path != null && maze != null) {
            for (Coordinates c : path) {
                if (c.x >= 0 && c.x < maze.length && c.y >= 0 && c.y < maze[0].length) {
                    maze[c.x][c.y] = ' ';
                }
            }
        }
    }

    public void restorePath() {
        if (originalMaze != null) {
            maze = originalMaze;
            originalMaze = null;
        }
    }

    public void setEntrance(Coordinates entrance) {
        this.entrance = entrance;
    }

    public void setExit(Coordinates exit) {
        this.exit = exit;
    }

    public void downloadPng(String destinationPath, boolean solved) throws IOException, IOException {
        Render render = new Render(maze);
        if (solved && path != null) {
            for (Coordinates c : path) {
                maze[c.x][c.y] = '#';
            }
        }
        render.saveImage(destinationPath, solved);
    }

}
