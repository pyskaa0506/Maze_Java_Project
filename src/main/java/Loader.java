import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// all that Loader does rn is loading text file as char[]][] (MazeMatrix) in loadMaze()
// loadPath() and containsPath() are placeholders
// feel free to change argument usage if needed
public class Loader {
    private char[][] MazeMatrix;
    private String filepath = "src/main/resources/5x5.txt";

    // to bin files: should return maze with entrance and exit (P and K should be included)
    public char[][]loadMaze() {
        if (isBinary(filepath)){
            //return maze with entrance and exit
        }
        this.MazeMatrix = convertToCharMatrix( loadFile(filepath) );
        return MazeMatrix;
    }

    public List<Coordinates> loadPath() {
        //method not implemented yet, should return path in Maze.load() if binary file contains path
        //everything under this comment is a placeholder
        return new ArrayList<>();
    }

    public Boolean containsPath() {
        //method not implemented yet, should return true if binary file contains path
        //everything under this comment is a placeholder
        return false;
    }

    public Boolean isBinary(String filepath){
        //method not implemented yet, should return true if file is binary
        //everything under this comment is a placeholder
        return false;
    }


    private List<String> loadFile(String filepath){

        String line;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }
    private char[][] convertToCharMatrix(List<String> Maze){
        char[][] MazeMatrix = new char[Maze.size()][Maze.get(0).length()];
        for (int i = 0; i < Maze.size(); i++) {
            for (int j = 0; j < Maze.get(i).length(); j++) {
                MazeMatrix[i][j] = Maze.get(i).charAt(j);
            }
        }
        return MazeMatrix;
    }
}
