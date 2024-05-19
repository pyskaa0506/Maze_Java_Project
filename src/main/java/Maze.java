import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] maze;
    private List<Coordinates> path;
    Coordinates entrance;
    Coordinates exit;

    char charBeforeChangeEntrace = 'X';
    char charBeforeChangeExit = 'X';

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


//            //print maze
//            for (int i = 0; i < maze.length; i++) {
//                for (int j = 0; j < maze[i].length; j++) {
//                    System.out.print(maze[i][j]);
//                }
//                System.out.println();
//            }

        }
    }
    public void solve(){
        solver.solve(maze, entrance, exit);
    }
}

