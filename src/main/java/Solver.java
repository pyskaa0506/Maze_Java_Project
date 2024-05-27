import java.util.ArrayList;
import java.util.List;

public class Solver {
    private int[][] maze_tmp;
    private final List<Coordinates> possibleMoves = new ArrayList<>();
    private final List<Coordinates> pathReversed = new ArrayList<>();
    private final List<Coordinates> path = new ArrayList<>();

    public List<Coordinates> solve(char[][] maze, Coordinates entrance, Coordinates exit){

        restart();
        transformMazeCharToInt(maze);
        dijkstra(entrance, exit);
        backtracking(entrance, exit);
        reversePath();

        return path;
    }

    private void restart(){
        maze_tmp = null;
        possibleMoves.clear();
        pathReversed.clear();
        path.clear();
    }

    private void reversePath(){
        for (int i = pathReversed.size() - 1; i >= 0; i--) {
            path.add(pathReversed.get(i));
        }
    }

    private void backtracking(Coordinates entrance, Coordinates exit){
        //come back here and implement backtracking
        int x = exit.x;
        int y = exit.y;
        pathReversed.add(new Coordinates(x, y));
        while (x != entrance.x || y != entrance.y){
            if (x - 1 >= 0 && maze_tmp[x - 1][y] == maze_tmp[x][y] - 1){
                pathReversed.add(new Coordinates(x - 1, y));
                x--;
            }
            else if (x + 1 < maze_tmp.length && maze_tmp[x + 1][y] == maze_tmp[x][y] - 1){
                pathReversed.add(new Coordinates(x + 1, y));
                x++;
            }
            else if (y - 1 >= 0 && maze_tmp[x][y - 1] == maze_tmp[x][y] - 1){
                pathReversed.add(new Coordinates(x, y - 1));
                y--;
            }
            else if (y + 1 < maze_tmp[x].length && maze_tmp[x][y + 1] == maze_tmp[x][y] - 1){
                pathReversed.add(new Coordinates(x, y + 1));
                y++;
            }
        }
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
}

