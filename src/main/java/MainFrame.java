import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private boolean changeEntrance = false;
    private boolean changeExit = false;

    MainFrame() {
        maze = new Maze();

        downloadMaze.setVisible(false);
        downloadSolved.setVisible(false);

        disableButtons();

        UploadText.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("./default_maps");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text and binary file", "txt", "bin");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filepath = file.getAbsolutePath();
                boolean isBinary = Loader.isBinary(filepath);
                String fileType = isBinary ? "binary" : "text";

                if (isBinary || filepath.endsWith(".txt")) {
                    if (maze.load(filepath)){
                        MessageUtils.SuccessMessage("The " + fileType + " file was successfully loaded.");
                        enableButtons();
                    } else {
                        disableButtons();
                    }
                } else {
                    MessageUtils.SuccessMessage("Select a valid file (.txt or .bin).");
                }
            }
        });

        ShowMaze.addActionListener(e -> {
            render = new Render(maze.getMaze());
            ScrollPane.setViewportView(render);
            render.setMaze(maze.getMaze());
            ScrollPane.setViewportView(render);
            applyColors();
            render.revalidate();
            render.repaint();
            downloadMaze.setVisible(true);

            render.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    int x = e.getY()/render.getCell();
                    int y = e.getX()/render.getCell();
                    Coordinates newCoordinate = new Coordinates(x, y);
                    if (changeEntrance)
                    {
                        maze.setEntrance(newCoordinate);
                        render.setEntrance(newCoordinate);
                        changeEntrance = false;
                    }
                    else if (changeExit)
                    {
                        maze.setExit(newCoordinate);
                        render.setExit(newCoordinate);
                        changeExit = false;
                    }
                }
            });
            SolveMaze.setEnabled(true);
            ChangeP.setEnabled(true);
            ChangeK.setEnabled(true);
        });

        SolveMaze.addActionListener(e -> {
            maze.removePath();
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

        downloadMaze.addActionListener(e -> openDownloadDialog(false));
        downloadSolved.addActionListener(e -> openDownloadDialog(true));

        BCPalette.setText("Color palette");
        BCPalette.setBackground(Color.WHITE);

        BCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getBackgroundColor() : Color.WHITE;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            BCPalette.setBackground(color);
            if (color != null){
                BCField.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                if (render != null) {
                    render.setBackgroundColor(color);
                }
            }
        });

        MCPalette.setText("Color palette ");
        MCPalette.setBackground(Color.BLACK);

        MCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getMazeColor() : Color.BLACK;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            MCPalette.setBackground(color);
            if (color != null){
                MCField.setText(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                if (render != null) {
                    render.setMazeColor(color);
                }
            }
        });

        SCPalette.setText("Color palette");
        SCPalette.setBackground(Color.ORANGE);

        SCPalette.addActionListener(e -> {
            Color initialColor = render != null ? render.getSolveColor() : Color.BLACK;
            Color color = JColorChooser.showDialog(null, "Select a color", initialColor);
            SCPalette.setBackground(color);
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

        ChangeP.addActionListener(e -> {
            changeEntrance = true;
        });

        ChangeK.addActionListener(e -> {
            changeExit = true;
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

    private void disableButtons(){
        ShowMaze.setEnabled(false);
        SolveMaze.setEnabled(false);
        BCPalette.setEnabled(false);
        MCPalette.setEnabled(false);
        SCPalette.setEnabled(false);
        BCField.setEnabled(false);
        MCField.setEnabled(false);
        SCField.setEnabled(false);
        ChangeP.setEnabled(false);
        ChangeK.setEnabled(false);
        downloadMaze.setEnabled(false);
        downloadSolved.setEnabled(false);
    }

    private void enableButtons(){
        ShowMaze.setEnabled(true);
        BCPalette.setEnabled(true);
        MCPalette.setEnabled(true);
        SCPalette.setEnabled(true);
        BCField.setEnabled(true);
        MCField.setEnabled(true);
        SCField.setEnabled(true);
        downloadMaze.setEnabled(true);
        downloadSolved.setEnabled(true);
    }

    private void openDownloadDialog(boolean isSolved) {
        boolean isBinary = maze.isBinary();
        boolean allowTxtBin = isSolved ? true : false;
        applyColors();
        DownloadDialog dialog = new DownloadDialog(maze, isSolved, allowTxtBin, isBinary);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
