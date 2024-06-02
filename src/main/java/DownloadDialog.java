import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DownloadDialog extends JDialog {
    private JPanel contentPane;
    private JButton txtButton;
    private JButton binButton;
    private JButton pngButton;
    private JButton cancelButton;
    private boolean isSolved;
    private Render render;
    private Maze maze;

    public DownloadDialog(JFrame parent, boolean isSolved, Render render, Maze maze, boolean isBinary) {
        super(parent, "Download Maze", true);
        this.isSolved = isSolved;
        this.render = render;
        this.maze = maze;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(cancelButton);

        cancelButton.addActionListener(e -> onCancel());

        pngButton.addActionListener(e -> handlePngDownload());
        txtButton.addActionListener(e -> handleTextDownload());
        binButton.addActionListener(e -> handleBinDownload());

        binButton.setVisible(isBinary);

        configureButtons();

        pack();
        setLocationRelativeTo(parent);
    }

    private void configureButtons() {
        if (isSolved) {
            txtButton.setEnabled(true);
            binButton.setEnabled(true);
        } else {
            txtButton.setVisible(false);
            binButton.setVisible(false);
        }
    }

    private void handlePngDownload() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }
            try {
                maze.saveMazeImage(render, filePath, isSolved);
                MessageUtils.SuccessMessage("Maze image saved successfully.");
            } catch (IOException ex) {
                MessageUtils.ErrorMessage("Failed to save the file: " + ex.getMessage());
            }
            dispose();
        }
    }



    private void handleTextDownload() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }
            maze.downloadTxt(filePath);
            MessageUtils.SuccessMessage("File saved successfully!");
            dispose();
        }
    }

    private void handleBinDownload() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".bin")) {
                filePath += ".bin";
            }
            maze.downloadBin(filePath);
            MessageUtils.SuccessMessage("File saved successfully!");
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }
}
