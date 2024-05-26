import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

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
    private JLabel ColorInfo;
    private JScrollPane ScrollPane;

    private Maze maze;
    private Render render;

    MainFrame() {
        maze = new Maze();

        downloadMaze.setVisible(false);
        downloadSolved.setVisible(false);

        UploadText.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("./default_maps");
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
            applyColors();
            render.revalidate();
            render.repaint();
            downloadMaze.setVisible(true);
        });


        SolveMaze.addActionListener(e -> {
            maze.solve();
            render.setMaze(maze.getMaze());
            applyColors();
            ScrollPane.setViewportView(render);
            render.revalidate();
            render.repaint();
            downloadSolved.setVisible(true);
        });

        howToUseATextArea.setEditable(false);
        BCText.setEditable(false);
        MCText.setEditable(false);
        SCText.setEditable(false);

        downloadMaze.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("png image", "png");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                String filepath = file.getAbsolutePath();
                if (!filepath.endsWith("png")){
                    filepath += ".png";
                }
                try {
                    maze.removePath();
                    render.setMaze(maze.getMaze());
                    render.saveMazeAsImage(filepath);
                    maze.restorePath();
                    render.setMaze(maze.getMaze());
                    render.repaint();
                    MessageUtils.SuccessMessage("Maze image saved.");
                } catch (IOException ex) {
                    MessageUtils.ErrorMessage("Failed to save image.");
                }
            }
        });

        downloadSolved.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(".");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".png")) {
                    filePath += ".png";
                }
                try {
                    render.saveSolvedAsImage(filePath);
                    MessageUtils.SuccessMessage("Solved maze image saved successfully.");
                } catch (IOException ex) {
                    MessageUtils.ErrorMessage("Failed to save solved maze image.");
                }
            }
        });

        BCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getBackgroundColor() : Color.WHITE;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            if (color != null){
                BCField.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                if (render != null) {
                    render.setBackgroundColor(color);
                }
            }
        });

        MCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getMazeColor() : Color.BLACK;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            if (color != null){
                MCField.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                if (render != null) {
                    render.setMazeColor(color);
                }
            }
        });

        SCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getSolveColor() : Color.BLACK;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            if (color != null){
                SCField.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                if (render != null) {
                    render.setSolveColor(color);
                }
            }
        });

        BCField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Color color = Color.decode(BCField.getText());
                    if (render != null ){
                        render.setBackgroundColor(color);
                        render.repaint();
                    }
                }
                catch (NumberFormatException exep){
                    MessageUtils.ErrorMessage("Invalid color code");
                }
            }
        });

        MCField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Color color = Color.decode(MCField.getText());
                    if (render != null ){
                        render.setMazeColor(color);
                        render.repaint();
                    }
                }
                catch (NumberFormatException exep){
                    MessageUtils.ErrorMessage("Invalid color code");
                }
            }
        });

        SCField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Color color = Color.decode(SCField.getText());
                    if (render != null ){
                        render.setSolveColor(color);
                        render.repaint();
                    }
                }
                catch (NumberFormatException exep){
                    MessageUtils.ErrorMessage("Invalid color code");
                }
            }
        });




    }

    private void applyColors() {
        try {
            if(!BCField.getText().isEmpty()) {
                Color backgroundColor = Color.decode(BCField.getText());
                render.setBackgroundColor(backgroundColor);
                render.repaint();
            }
            if(!MCField.getText().isEmpty()) {
                Color mazeColor = Color.decode(MCField.getText());
                render.setMazeColor(mazeColor);
                render.repaint();
            }
            if(!SCField.getText().isEmpty()) {
                    Color solveColor = Color.decode(SCField.getText());
                    render.setSolveColor(solveColor);
                    render.repaint();
            }
        } catch (NumberFormatException e) {
            MessageUtils.ErrorMessage("Invalid color code in one of the fields.");
        }
    }


}
