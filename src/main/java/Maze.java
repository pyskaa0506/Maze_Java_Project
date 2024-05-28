import java.util.List;

public class Maze {
    private char[][] maze;
    private char[][] originalMaze;
    private List<Coordinates> path;
    private String filepath;
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

    public boolean load(String filepath) {
        this.filepath = filepath;
        maze = loader.loadMaze(filepath);
        if (maze == null) {
            return false;
        }
        entrance = new Coordinates().getPositionOfLetter(maze, 'P');
        exit = new Coordinates().getPositionOfLetter(maze, 'K');
        new Coordinates().stripMazeOfLetter(maze, 'P');
        new Coordinates().stripMazeOfLetter(maze, 'K');

        System.out.println("Entrance: " + entrance.x + " " + entrance.y);
        System.out.println("Exit: " + exit.x + " " + exit.y);
        return true;
    }

    public void solve() {
        path = solver.solve(maze, entrance, exit);
        if (path == null) {
            System.out.println("No path found");
        }
        for (Coordinates c : path) {
            maze[c.x][c.y] = '#';
        }
//        fileDownloader.downloadTxt("src/main/resources/PlaceholderFolder/CommandPath.txt", path); // for testing purposes, feel free to delete it when downloading will be connected properly to the GUI
//        fileDownloader.downloadBin("src/main/resources/PlaceholderFolder/CommandPath.bin", filepath, path); //not finished yet
    }

    public void downloadTxt(String destinationPath) {
        fileDownloader.downloadTxt(destinationPath, path);
    }
    public void downloadBin(String destinationPath) {
        String sourcePath = this.filepath;
        fileDownloader.downloadBin(destinationPath, sourcePath, path);
    }

    public void printPath() {
        for (Coordinates c : path) {
            System.out.println(c.x + " " + c.y);
        }
    }

    public void printMaze() {
        for (char[] chars : maze) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }

    public char[][] getMaze() {
        return maze;
    }

    public List<Coordinates> getPath() {
        return path;
    }

    public void removePath() {
        if (path != null && maze != null) {
            originalMaze = new char[maze.length][];
            for (int i = 0; i < maze.length; i++) {
                originalMaze[i] = maze[i].clone();
            }
            for (Coordinates c : path) {
                maze[c.x][c.y] = ' ';
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

    public Coordinates getEntrance() {
        return entrance;
    }

    public Coordinates getExit() {
        return exit;
    }

}
