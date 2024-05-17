public class Maze {
    private char[][] maze;
    private char[][] path;
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
        }
    }
    public void solve(){
        // will have something here
    }
}
class Coordinates {
    int x, y;

    public Coordinates getPositionOfLetter(char[][] maze, char letter){
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == letter){
                    x = i;
                    y = j;
                    return new Coordinates();
                }
            }
        }
        return null;
    }
    public Coordinates stripMazeOfLetter(char[][] maze, char letter){
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == letter){
                    maze[i][j] = ' ';
                }
            }
        }
        return new Coordinates();
    }
}
