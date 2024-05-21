import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class FileValidator {
    public static boolean isBinFileValid(String filepath) {

        try (FileInputStream fis = new FileInputStream(filepath)){
            byte[] header = new byte[40];
            int read = fis.read(header);
            if (read != header.length) {
                MessageUtils.ErrorMessage("Invalid file. Could not fully read the header");
                throw new IOException("Invalid file. Could not fully read the header");
            }
            ByteBuffer buffer = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);

            int fileId = buffer.getInt(); // 4 bytes
            byte escape = buffer.get(); // 1 byte
            if (escape != 0x1B) {
                MessageUtils.ErrorMessage("Invalid file. Incorrect escape character");
                return false;
            }
            short columns = buffer.getShort(); // 2 bytes
            short lines = buffer.getShort();
            if (columns < 3 || lines < 3) {
                MessageUtils.ErrorMessage("Invalid file. Number of columns and rows must be greater than 2.");
                return false;
            }
            short entryX = buffer.getShort();
            short entryY = buffer.getShort();
            short exitX = buffer.getShort();
            short exitY = buffer.getShort();
            if (entryX < 0 || entryY < 0 || exitX < 0 || exitY < 0) {
                MessageUtils.ErrorMessage("Invalid file. Coordinates cannot be negative.");
                return false;
            }
            if (entryX > columns || entryY > lines || exitX > columns || exitY > lines) {
                MessageUtils.ErrorMessage("Invalid file. Coordinates cannot be greater than number of columns or rows.");
                return false;
            }
            buffer.position(buffer.position() + 12);
            int counter = buffer.getInt();
            if (counter < 0) {
                MessageUtils.ErrorMessage("Invalid file. Counter cannot be negative.");
                return false;
            }
            int solutionOffset = buffer.getInt();
            if (solutionOffset < 0) {
                MessageUtils.ErrorMessage("Invalid file. Solution offset cannot be negative.");
                return false;
            }
            byte separator = buffer.get();
            byte wall = buffer.get();
            byte path = buffer.get();

            int how_many = 0;
            for (int i = 0; i < counter; i++){
                byte sep = (byte) fis.read();
                byte value = (byte) fis.read();
                byte count = (byte) fis.read();

                if (sep != separator) {
                    MessageUtils.ErrorMessage("Invalid file. Separator is incorrect.");
                    return false;
                }

                if (value != wall && value != path) {
                    MessageUtils.ErrorMessage("Invalid file. Value is incorrect.");
                    return false;
                }

                int actualCount = (count & 0xFF) + 1;
                for (int j = 0; j < actualCount; j++){
                    how_many++;
                    if (how_many > columns * lines) {
                        MessageUtils.ErrorMessage("Invalid file. Counter is greater than number of columns * number of rows.");
                        return false;
                    }
                }
            }
            if (how_many != columns * lines) {
                MessageUtils.ErrorMessage("Invalid file. Counter is not equal to number of columns * number of rows.");
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

        public static boolean isFileValid(List<String> strings) {
        // check if number of lines is higher than 2 and number of columns is higher than 2
        if (strings.size() < 3) {
            return false;
        }
        if (strings.get(1).length() < 3) {
            return false;
        }
        //check if number of rows is equal in all columns
        for (int i = 1; i < strings.size(); i++) {
            if (strings.get(i).length() != strings.get(1).length()) {
                MessageUtils.ErrorMessage("Invalid file. Number of rows is not equal in all columns.");
                return false;
            }
        }

        //check if number of columns is equal in all rows
        for (int i = 1; i < strings.get(1).length(); i++) {
            if (strings.get(i).length() != strings.get(1).length()) {
                MessageUtils.ErrorMessage("Invalid file. Number of columns is not equal in all rows.");
                return false;
            }
        }

        //check if there is only one entrance and one exit
        int entrance = 0;
        int exit = 0;
        for (String string : strings) {
            for (int j = 0; j < string.length(); j++) {
                if (string.charAt(j) == 'P') {
                    entrance++;
                }
                if (string.charAt(j) == 'K') {
                    exit++;
                }
            }
        }
        if (entrance != 1 || exit != 1) {
            MessageUtils.ErrorMessage("Invalid file. Incorrect number of entrances or exits.");
            return false;
        }

        //check if file contains only '#', 'P', 'K', '\n' and ' '
        for (String string : strings) {
            for (int j = 0; j < string.length(); j++) {
                char c = string.charAt(j);
                if (c != 'X' && c != 'P' && c != 'K' && c != '\n' && c != ' ') {
                    MessageUtils.ErrorMessage("Invalid file. File contains invalid characters.");
                    return false;
                }
            }
        }
        //check if borders are correct (no ' ')
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).charAt(0) == ' ' || strings.get(i).charAt(strings.get(i).length() - 1) == ' ') { //check left and right borders
                MessageUtils.ErrorMessage("Invalid file. Borders are incorrect.");
                return false;
            }
        }

        for (int i = 0; i < strings.get(1).length(); i++) {
            if (strings.get(0).charAt(i) == ' ' || strings.get(strings.size() - 1).charAt(i) == ' ') { //check top and bottom borders
                MessageUtils.ErrorMessage("Invalid file. Borders are incorrect.");
                return false;
            }
        }

        return true;
    }
}
