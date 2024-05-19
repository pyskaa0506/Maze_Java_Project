    public class Coordinates {
        int x, y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
        Coordinates(){

        }


        public Coordinates getPositionOfLetter(char[][] maze, char letter){
            Coordinates coordinates = new Coordinates();
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == letter){
                        coordinates.x = i;
                        coordinates.y = j;
                        return coordinates;
                    }
                }
            }
            return coordinates;
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

