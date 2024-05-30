import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileDownloader {
    public void downloadTxt(String destinationPath, List<Coordinates> path) {
        List <Character> listOfDirections = convertCoordinatesToDirections(path);
        List <String> listOfCommands = convertDirectionsToCommands(listOfDirections);
        saveToTxtFile(destinationPath, listOfCommands);
    }
    public void downloadBin(String destinationPath, String sourcePath, List<Coordinates> path) {
        String uniqueDestinationPath = getUniqueFilePath(destinationPath);
        copyToFile(uniqueDestinationPath, sourcePath);
        List<Character> listOfDirections = convertCoordinatesToDirections(path);
        List<Character> compressedDirections = compressDirections(listOfDirections);
        saveToBinFile(uniqueDestinationPath, compressedDirections);
    }

    private String getUniqueFilePath(String destinationPath) {
        String pathWithoutExtension = destinationPath.substring(0, destinationPath.lastIndexOf("."));
        String extension = destinationPath.substring(destinationPath.lastIndexOf("."));
        int count = 0;
        while (Files.exists(Paths.get(destinationPath))) {
            destinationPath = pathWithoutExtension + "[" + count + "]" + extension;
            count++;
        }
        return destinationPath;
    }

    private void saveToBinFile(String destinationPath, List<Character> compressedPath) {
        try (RandomAccessFile raf = new RandomAccessFile(destinationPath, "rw")) {
            byte[] header = new byte[40];
            int read = raf.read(header);
            if (read != header.length) {
                MessageUtils.ErrorMessage("Could not fully read the header");
                throw new IOException("Could not fully read the header");
            }
            byte[] firstFourBytes = new byte[4];
            System.arraycopy(header, 0, firstFourBytes, 0, 4);

            ByteBuffer buffer = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);
            buffer.position(29);
            int counter = buffer.getInt();
            int mazeDataEndPos = 40 + (counter * 3);

            buffer.position(33);
            buffer.putInt(mazeDataEndPos);

            raf.seek(0);
            raf.write(buffer.array());

            buffer.position(33);
            int what = buffer.getInt();
            System.out.println("Solution Offset: " + what);

            raf.seek(mazeDataEndPos);

            List<Byte> bytesToWrite = parseCompressedPath(compressedPath);
            for (byte b : bytesToWrite) {
                raf.writeByte(b);
            }

            long newEndPos = mazeDataEndPos + bytesToWrite.size();
            raf.setLength(newEndPos);

            raf.seek(newEndPos);
            raf.write(firstFourBytes);

            MessageUtils.SuccessMessage("File saved successfully");

        } catch (FileNotFoundException e) {
            MessageUtils.ErrorMessage("File not found: " + e.getMessage());
            throw new RuntimeException(e);

        } catch (IOException e) {
            MessageUtils.ErrorMessage("An error occurred while writing to the file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    private List<Byte> parseCompressedPath(List<Character> compressedPath) {
        List<Byte> bytes = new ArrayList<>();
        int length = compressedPath.size();
        for (int i = 0; i < length; i++) {
            char direction = compressedPath.get(i);
            i++;
            StringBuilder countBuilder = new StringBuilder();
            while (i < length && Character.isDigit(compressedPath.get(i))) {
                countBuilder.append(compressedPath.get(i));
                i++;
            }
            i--;
            int count = Integer.parseInt(countBuilder.toString());

            bytes.add((byte) direction);
            bytes.add((byte) count);
        }
        return bytes;
    }

        // compress directions to S3E2N3 etc. Max 255 steps in one direction
    private List<Character> compressDirections(List<Character> directions) {
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

    private void copyToFile(String destinationPath, String sourcePath) {
        try {
            Files.copy(Paths.get(sourcePath), Paths.get(destinationPath));
        } catch (IOException e) {
            System.out.println("An error occurred while copying the file: " + e.getMessage());
        }
    }

    private void saveToTxtFile(String destinationPath, List<String> listOfCommands) {
        try {
            String pathWithoutExtension = destinationPath.substring(0, destinationPath.lastIndexOf("."));
            String extension = destinationPath.substring(destinationPath.lastIndexOf("."));
            int count = 0;
            while (Files.exists(Paths.get(destinationPath))) {
                destinationPath = pathWithoutExtension + "[" + count + "]" + extension;
                count++;
            }
            Files.write(Paths.get(destinationPath), listOfCommands);
            MessageUtils.SuccessMessage("File saved successfully");
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
