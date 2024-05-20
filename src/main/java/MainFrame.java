import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame {

    public JPanel MainFrame;
    private JButton UploadText;
    private JTextArea BCText;
    private JTextArea MCText;
    private JTextArea SCText;
    private JTextField BCField;
    private JTextField MCField;
    private JTextField SCField;
    private JButton BCPalette;
    private JButton MCPalette;
    private JButton SCPalette;
    private JButton ShowMaze;
    private JButton SolveMaze;
    private JButton downloadMaze;
    private JButton downloadSolved;
    private JButton ChangeP;
    private JButton ChangeK;
    private JLabel ProgrammeName;
    private JTextArea howToUseATextArea;
    private JLabel ColourInfo;

    private Maze maze;

    MainFrame(){
         maze = new Maze();

        UploadText.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and binary file", "txt", "bin");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filepath = file.getAbsolutePath();
                boolean isBinary = Loader.isBinary(filepath); //changed isBinary to static, so it can be called without creating an instance of Loader
                String fileType = isBinary ? "binary" : "text";

                if (isBinary || filepath.endsWith(".txt")) {
                    maze.load(filepath);
                    MessageUtils.SuccessMessage("The " + fileType + " file was successfully loaded.");
                } else {
                    MessageUtils.ErrorMessage("Select a valid file (.txt or .bin).");
                }
                /*
                pierwsza wersja - nie podaje komunikat o tym jaki plik (txt czy bin) został wczytany;
                if (isBinary || filepath.endsWith(".txt")) {
                    Maze maze = new Maze();
                    maze.load(filepath);
                    JOptionPane.showMessageDialog(null, "The file was successfully loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Select a valid file (.txt or .bin).", "Error", JOptionPane.ERROR_MESSAGE);
                    */
                }
        });

        ShowMaze.addActionListener(e -> maze.printMaze());

        SolveMaze.addActionListener(e -> {
            maze.solve();
            maze.printPath();
        });

        howToUseATextArea.setEditable(false);
        BCText.setEditable(false);
        MCText.setEditable(false);
        SCText.setEditable(false);




        /*
        ChangeP.addActionListener(e -> {
            String newP = JOptionPane.showInputDialog("Enter new start point coordinates (x,y):");
            if(newP != null && !newP.isEmpty()){
                String[] coords = newP.split(",");
                if (coords.length == 2) {
                    int x = Integer.parseInt(coords[0].trim());
                    int y = Integer.parseInt(coords[0].trim());

                    maze.changeStartPoint(x,y);
                    MessageUtils.SuccessMessage("Start point updated successfully.");
                }
                else
                {
                    MessageUtils.printErrorMessage("Invalid coordinates format.");
                }
            }
        });

        ChangeK.addActionListener(e -> {
            String newK = JOptionPane.showInputDialog("Enter new start point coordinates (x,y):");
            if(newK != null && !newK.isEmpty()){
                String[] coords = newK.split(",");
                if (coords.length == 2) {
                    int x = Integer.parseInt(coords[0].trim());
                    int y = Integer.parseInt(coords[0].trim());

                    maze.changeEndPoint(x,y);
                    JMessageUtils.SuccessMessage("End point updated successfully.");
                }
                else
                {
                    MessageUtils.printErrorMessage("Invalid coordinates format.");
                }
            }
        });
        */


    }

}
