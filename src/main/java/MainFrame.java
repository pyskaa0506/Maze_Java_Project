import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JScrollPane ScrollPane;

    private Maze maze;
    private Render render;

    MainFrame() {
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
                    MessageUtils.SuccessMessage("Select a valid file (.txt or .bin).");
                }
            }
        });

        /*
        Always shows the scroll bars, even if they are not needed (e.g. a 5*5 maze);
        generally optional
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         */

        ShowMaze.addActionListener(e -> {
            render = new Render(maze.getMaze());
            ScrollPane.setViewportView(render);
            render.setMaze(maze.getMaze());
            ScrollPane.setViewportView(render);
            render.revalidate();
            render.repaint();
        });


        SolveMaze.addActionListener(e -> {
            maze.solve();
            render.setMaze(maze.getMaze());
            ScrollPane.setViewportView(render);
            render.revalidate();
            render.repaint();
        });

        howToUseATextArea.setEditable(false);
        BCText.setEditable(false);
        MCText.setEditable(false);
        SCText.setEditable(false);
    }

}
