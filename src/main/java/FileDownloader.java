import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {
    public void downloadTxt(String destinationPath, List<Coordinates> path) {
        List <Character> listOfDirections = convertCoordinatesToDirections(path);
        List <String> listOfCommands = convertDirectionsToCommands(listOfDirections);
        saveToFile(destinationPath, listOfCommands);
    }
    public void downloadBin(String destinationPath, String sourcePath, List<Coordinates> path) {
        copyToFilepath(destinationPath, sourcePath);
        List <Character> listOfDirections = convertCoordinatesToDirections(path);
        List <Character> compressedDirections = compressDirections(listOfDirections);
        //add saving path to binary file
        for (Character direction : compressedDirections) {
            System.out.print(direction);
        }
    }

    // compress directions to S3E2N3 etc. Max 255 steps in one direction
    public List<Character> compressDirections(List<Character> directions) {
        List<Character> compressedDirections = new ArrayList<>();
        char currentDirection = directions.get(0);
        int count = 1;
        for (int i = 1; i < directions.size(); i++) {
            char nextDirection = directions.get(i);
            if (currentDirection == nextDirection && count < 255) {
                count++;
            } else {
                compressedDirections.add(currentDirection);
                for (char c : String.valueOf(count).toCharArray()) {
                    compressedDirections.add(c);
                }
                count = 1;
            }
            currentDirection = nextDirection;
        }
        compressedDirections.add(currentDirection);
        for (char c : String.valueOf(count).toCharArray()) {
            compressedDirections.add(c);
        }
        return compressedDirections;
    }

    private void copyToFilepath(String destinationPath, String sourcePath) {
        try {
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath));
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file: " + e.getMessage());
        }
    }

    private void saveToFile(String destinationPath, List<String> listOfCommands) {
        try {
            String pathWithoutExtension = destinationPath.substring(0, destinationPath.lastIndexOf("."));
            String extension = destinationPath.substring(destinationPath.lastIndexOf("."));
            int count = 0;
            while (Files.exists(Paths.get(destinationPath))) {
                destinationPath = pathWithoutExtension + "[" + count + "]" + extension;
                count++;
            }
            Files.write(Paths.get(destinationPath), listOfCommands);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
    private List <String> convertDirectionsToCommands(List<Character> directions) {
        List <String> commands = new ArrayList<>();
        char currentDirection = directions.get(0);
        int count = 1;
        for (int i = 1; i < directions.size(); i++) {
            char nextDirection = directions.get(i);
            if (currentDirection == nextDirection) {
                count++;
            } else {
                commands.add("FORWARD " + count);
                count = 1;
                if (currentDirection == 'N') {
                    if (nextDirection == 'E') {
                        commands.add("TURN RIGHT");
                    } else {
                        commands.add("TURN LEFT");
                    }
                } else if (currentDirection == 'S') {
                    if (nextDirection == 'E') {
                        commands.add("TURN LEFT");
                    } else {
                        commands.add("TURN RIGHT");
                    }
                } else if (currentDirection == 'E') {
                    if (nextDirection == 'N') {
                        commands.add("TURN LEFT");
                    } else {
                        commands.add("TURN RIGHT");
                    }
                } else {
                    if (nextDirection == 'N') {
                        commands.add("TURN RIGHT");
                    } else {
                        commands.add("TURN LEFT");
                    }
                }
            }
            currentDirection = nextDirection;
        }
        commands.add("FORWARD " + count);
        return commands;
    }

    // convert coordinates to W, S, E, N
    private List<Character> convertCoordinatesToDirections(List<Coordinates> path) {
        Coordinates current = path.get(0);
        List<Character> directions = new ArrayList<>();
        for (int i = 1 ; i < path.size(); i++) {
            Coordinates next = path.get(i);
            if (current.x == next.x) {
                if (current.y < next.y) {
                    directions.add('E');
                } else {
                    directions.add('W');
                }
            } else {
                if (current.x < next.x) {
                    directions.add('S');
                } else {
                    directions.add('N');
                }
            }
            current = next;
        }
        return directions;
    }
}
