import java.io.*;
import java.util.ArrayList;
import java.util.List;

// all that Loader does rn is loading text file as char[]][] (MazeMatrix) in loadMaze()
// loadPath() and containsPath() are placeholders
// feel free to change argument usage if needed
public class Loader {
    private char[][] MazeMatrix;
    //private String filepath = "src/main/resources/5x5.txt";

    // to bin files: should return maze with entrance and exit (P and K should be included)
    public char[][] loadMaze(String filepath)
    {
        if (isBinary(filepath)) {
            // Implement binary file reading logic here
            // Example: return loadBinaryMaze(filepath);
        } else {
            this.MazeMatrix = convertToCharMatrix(loadFile(filepath));
        }
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

    public boolean isBinary(String filepath){
        try(InputStream inputStream = new FileInputStream(filepath)){
            int size = inputStream.available();
            byte[] data = new byte[size];
            inputStream.read(data);
            for (int i = 0; i < data.length; i++){
                if(data[i] == 0 ){
                    // returns true if a null byte was found (it's likely a bin file)
                    return true;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
