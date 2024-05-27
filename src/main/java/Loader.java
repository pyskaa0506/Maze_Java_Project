import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    public char[][] loadMaze(String filepath)
    {
        char[][] mazeMatrix = null;

        if (isBinary(filepath)) {
            System.out.println("Binary file detected");
            if (FileValidator.isBinFileValid(filepath)) {
                mazeMatrix = loadBinaryMaze(filepath);
            }

        } else {
            System.out.println("Text file detected");
            if (FileValidator.isFileValid(loadFile(filepath))) {
                mazeMatrix = convertToCharMatrix(loadFile(filepath));
            }
        }
        return mazeMatrix;
    }

    private char[][] loadBinaryMaze(String filepath) {
        try (FileInputStream fis = new FileInputStream(filepath)){
            byte[] header = new byte[40];
            int read = fis.read(header);
            if (read != header.length) {
                MessageUtils.ErrorMessage("Could not fully read the header");
                throw new IOException("Could not fully read the header");
            }
            ByteBuffer buffer = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);

            int fileId = buffer.getInt(); // 4 bytes
            byte escape = buffer.get(); // 1 byte
            short columns = buffer.getShort(); // 2 bytes
            short lines = buffer.getShort();
            short entryX = buffer.getShort();
            short entryY = buffer.getShort();
            short exitX = buffer.getShort();
            short exitY = buffer.getShort();
            buffer.position(buffer.position() + 12);
            int counter = buffer.getInt();
            int solutionOffset = buffer.getInt();
            byte separator = buffer.get();
            byte wall = buffer.get();
            byte path = buffer.get();

            System.out.printf("File ID: 0x%08X%n", fileId);
            System.out.printf("Escape: 0x%02X%n", escape);
            System.out.printf("Columns: %d%n", columns);
            System.out.printf("Lines: %d%n", lines);
            System.out.printf("Entry: (%d, %d)%n", entryX, entryY);
            System.out.printf("Exit: (%d, %d)%n", exitX, exitY);
            System.out.printf("Counter: %d%n", counter);
            System.out.printf("Solution Offset: %d%n", solutionOffset);
            System.out.println("Separator:" + (char) separator);
            System.out.println("Wall: " + (char) wall);
            System.out.println("Path:" + (char) path);

            char[][] binMaze = new char[lines][columns];
            int tempCol = 0;
            int tempRow = 0;

            for (int i = 0; i < counter; i++){
                byte sep = (byte) fis.read();
                byte value = (byte) fis.read();
                byte count = (byte) fis.read();
                int actualCount = (count & 0xFF) + 1;

                for (int j = 0; j < actualCount; j++){
                    if (tempCol == columns){
                        tempCol = 0;
                        tempRow++;
                    }
                    binMaze[tempRow][tempCol] = (char) value;
                    tempCol++;
                }
            }

            binMaze[entryY-1][entryX-1] = 'P';
            binMaze[exitY-1][exitX-1] = 'K';

            return binMaze;

        } catch (IOException e) {
            MessageUtils.ErrorMessage("Could not read the file");
            throw new RuntimeException(e);
        }
    }

    private List<String> loadFile(String filepath){

        String line;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            MessageUtils.ErrorMessage("Could not read the file");
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

    public static boolean isBinary(String filepath){
        try(InputStream inputStream = new FileInputStream(filepath)){
            int size = inputStream.available();
            byte[] data = new byte[size];
            inputStream.read(data);
            for (byte datum : data) {
                if (datum == 0) {
                    // returns true if a null byte was found (it's likely a bin file)
                    return true;
                }
            }
        }
        catch (IOException e) {
            MessageUtils.ErrorMessage("Could not read the file");
            throw new RuntimeException(e);
        }
        return false;
    }
}
