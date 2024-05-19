import java.util.ArrayList;
import java.util.List;

public class Solver {
    private int[][] maze_tmp;
    private final List<Coordinates> possibleMoves = new ArrayList<>();
    private List<Coordinates> path = new ArrayList<>();


    Solver(){
    }
    public List<Coordinates> solve(char[][] maze, Coordinates entrance, Coordinates exit){

        transformMazeCharToInt(maze);
        dijkstra(entrance, exit);


        //placeholder
        return path;
    }

    private void backtracking(Coordinates entrance, Coordinates exit){
        //come back here and implement backtracking
    }

    private void dijkstra(Coordinates entrance, Coordinates exit){

        int x = entrance.x;
        int y = entrance.y;

        maze_tmp[x][y] = 0;
        possibleMoves.add(new Coordinates(x, y));


        while (!possibleMoves.isEmpty()){

            Coordinates smallest = findSmallestInPossibleMoves();
            x = smallest.x;
            y = smallest.y;
            possibleMoves.remove(smallest);

            if (x == exit.x && y == exit.y){
                break;
            }

            if (x - 1 >= 0 && maze_tmp[x - 1][y] == -1){
                //set value of the cell to the value of the cell before + 1 (int)
                maze_tmp[x - 1][y] = maze_tmp[x][y] + 1;
                possibleMoves.add(new Coordinates(x - 1, y));
            }
            if (x + 1 < maze_tmp.length && maze_tmp[x + 1][y] == -1){
                maze_tmp[x + 1][y] = maze_tmp[x][y] + 1;
                possibleMoves.add(new Coordinates(x + 1, y));
            }
            if (y - 1 >= 0 && maze_tmp[x][y - 1] == -1){
                maze_tmp[x][y - 1] = maze_tmp[x][y] + 1;
                possibleMoves.add(new Coordinates(x, y - 1));
            }
            if (y + 1 < maze_tmp[x].length && maze_tmp[x][y + 1] == -1){
                maze_tmp[x][y + 1] = maze_tmp[x][y] + 1;
                possibleMoves.add(new Coordinates(x, y + 1));
            }
            printRefresh();
        }
    }
    private Coordinates findSmallestInPossibleMoves(){
        Coordinates smallest = possibleMoves.get(0);
        for (int i = 1; i < possibleMoves.size(); i++) {
            if (maze_tmp[possibleMoves.get(i).x][possibleMoves.get(i).y] < maze_tmp[smallest.x][smallest.y]){
                smallest = possibleMoves.get(i);
            }
        }
        return smallest;
    }
    //transform char maze to int maze
private void transformMazeCharToInt(char[][] maze){
        maze_tmp = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == ' '){
                    maze_tmp[i][j] = -1;
                }
                else {
                    maze_tmp[i][j] = -2;
                }
            }
        }
    }


    private void printRefresh(){
        //clear console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        printMaze();
    }
    private void printPossibleMoves(){
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println(possibleMoves.get(i).x + " " + possibleMoves.get(i).y);
        }
    }
    private void printMaze(){
        for (int i = 0; i < maze_tmp.length; i++) {
            for (int j = 0; j < maze_tmp[i].length; j++) {
                System.out.print(maze_tmp[i][j]);
            }
            System.out.println();
        }
    }
}

