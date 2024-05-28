    public class Coordinates {
        int x, y;
        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinates() {

        }

        public Coordinates getPositionOfLetter(char[][] maze, char letter) {
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == letter) {
                        return new Coordinates(i, j);
                    }
                }
            }
            return null;
        }

        public void stripMazeOfLetter(char[][] maze, char letter){
            //print maze

            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == letter){
                        maze[i][j] = ' ';
                    }
                }
            }
        }
    }

