import java.io.IOException;
import java.util.List;

public class Maze {
    private char[][] maze;
    private char[][] originalMaze;
    private char[][] solvedMaze;
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
        originalMaze = copyMaze(maze);
        entrance = new Coordinates().getPositionOfLetter(maze, 'P');
        exit = new Coordinates().getPositionOfLetter(maze, 'K');
        new Coordinates().stripMazeOfLetter(maze, 'P');
        new Coordinates().stripMazeOfLetter(maze, 'K');
        return true;
    }

    public void solve() {
        char[][] mazeCopy = copyMaze(maze);
        path = solver.solve(mazeCopy, entrance, exit);
        if (path == null) {
            MessageUtils.ErrorMessage("No path found");
        }
        for (Coordinates c : path) {
            mazeCopy[c.x][c.y] = '#';
        }
        solvedMaze = mazeCopy;
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

    public char[][] getSolvedMaze() {
        return solvedMaze;
    }

    public void reset() {
        maze = null;
        originalMaze = null;
        solvedMaze = null;
        path = null;
        filepath = null;
        isBinary = false;
        entrance = null;
        exit = null;
    }

    private char[][] copyMaze(char[][] source) {
        if (source == null) {
            return null;
        }
        char[][] copy = new char[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i].clone();
        }
        return copy;
    }

    public void setEntrance(Coordinates entrance) {
        this.entrance = entrance;
    }

    public void setExit(Coordinates exit) {
        this.exit = exit;
    }

    public void saveMazeImage(String destinationPath, boolean solved) throws IOException {
        Render render = new Render(solved ? getSolvedMaze() : getMaze());
        render.saveImage(destinationPath, solved);
    }

}
