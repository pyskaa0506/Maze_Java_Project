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
        if (!dijkstra(entrance, exit)) return null;
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

    private boolean dijkstra(Coordinates entrance, Coordinates exit){

        if (!isAccessible(maze_tmp, entrance) || !isAccessible(maze_tmp, exit)) {
            MessageUtils.ErrorMessage("Invalid maze. Entrance or exit is surrounded by walls.");
            return false;
        }

        maze_tmp[exit.x][exit.y] = -1;

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
                return true;
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
        MessageUtils.ErrorMessage("No path found.");
        return false;
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

    public boolean isAccessible(int[][] maze, Coordinates coord) {
        int x = coord.x;
        int y = coord.y;
        int rows = maze.length;
        int cols = maze[0].length;
        int accessibleCount = 0;

        if (x > 0 && maze[x - 1][y] != -2) accessibleCount++;
        if (x < rows - 1 && maze[x + 1][y] != -2) accessibleCount++;
        if (y > 0 && maze[x][y - 1] != -2) accessibleCount++;
        if (y < cols - 1 && maze[x][y + 1] != -2) accessibleCount++;

        return accessibleCount > 0;
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

