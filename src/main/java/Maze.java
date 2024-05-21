import java.util.List;

public class Maze {
    private char[][] maze;
    private List<Coordinates> path;
    Coordinates entrance;
    Coordinates exit;
    Loader loader;
    Solver solver;

    Maze() {
        loader = new Loader();
        solver = new Solver();
    }

    // I suggest to change the type of the return value to boolean so that the caller can know if the file was loaded successfully or not
    public boolean load(String filepath) {
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

    public void solve()
    {
        path = solver.solve(maze, entrance, exit);
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
}
