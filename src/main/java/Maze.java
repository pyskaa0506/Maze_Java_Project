import java.util.List;

public class Maze {
    private char[][] maze;
    private List<Coordinates> path;
    Coordinates entrance;
    Coordinates exit;
    Loader loader;
    Solver solver;
    Maze(){
        loader = new Loader();
        solver = new Solver();
    }

    public void load(){

        if (loader.containsPath()){
            maze = loader.loadMaze();
        }
        else {
            maze = loader.loadMaze();
            path = loader.loadPath();
            entrance = new Coordinates().getPositionOfLetter(maze, 'P');
            exit = new Coordinates().getPositionOfLetter(maze, 'K');
            new Coordinates().stripMazeOfLetter(maze, 'P');
            new Coordinates().stripMazeOfLetter(maze, 'K');

            System.out.println("Entrance: " + entrance.x + " " + entrance.y);
            System.out.println("Exit: " + exit.x + " " + exit.y);
        }
    }
    public void solve(){
        path = solver.solve(maze, entrance, exit);
    }
    public void printPath(){
        for (Coordinates c : path){
            System.out.println(c.x + " " + c.y);
        }
    }
    public void printMaze(){
        for (char[] chars : maze) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
}

