import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {
    public void downloadTxt(String outPath, List<Coordinates> path) {
        List <Character> listOfDirections = convertCoordinatesToDirections(path);
        List <String> listOfCommands = convertDirectionsToCommands(listOfDirections);
        saveToFile(outPath, listOfCommands);
    }
    public void downloadBin(String outputPath, String inputPath, List<Coordinates> path) {
        //print path
    }
    private void saveToFile(String outPath, List<String> listOfCommands) {
        try {
            String pathWithoutExtension = outPath.substring(0, outPath.lastIndexOf("."));
            String extension = outPath.substring(outPath.lastIndexOf("."));
            int count = 0;
            while (Files.exists(Paths.get(outPath))) {
                outPath = pathWithoutExtension + "[" + count + "]" + extension;
                count++;
            }
            Files.write(Paths.get(outPath), listOfCommands);
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
