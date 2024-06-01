import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static sun.tools.jconsole.inspector.XDataViewer.dispose;

public class DownloadDialog extends JDialog {
    private JPanel contentPane;
    private JButton txtButton;
    private JButton binButton;
    private JButton pngButton;
    private JButton cancelButton;

    private Maze maze;
    private boolean isSolved;

    public DownloadDialog(Maze maze, boolean isSolved, boolean allowTxtBin, boolean isBinary) {
        this.maze = maze;
        this.isSolved = isSolved;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(cancelButton);

        txtButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onTxt();
            }
        });

        binButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBin();
            }
        });

        pngButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPng();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        // Hide or show txtButton and binButton based on allowTxtBin and isBinary
        txtButton.setVisible(isBinary);
        txtButton.setVisible(allowTxtBin);
        binButton.setVisible(isBinary);
        binButton.setVisible(allowTxtBin);
    }

    private void onTxt() {
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filepath = file.getAbsolutePath();
            if (!filepath.endsWith(".txt")) {
                filepath += ".txt";
            }
            maze.downloadTxt(filepath);
            MessageUtils.SuccessMessage("TXT file saved successfully.");
        }
        dispose();
    }

    private void onBin() {
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary file", "bin");
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filepath = file.getAbsolutePath();
            if (!filepath.endsWith(".bin")) {
                filepath += ".bin";
            }
            maze.downloadBin(filepath);
            MessageUtils.SuccessMessage("BIN file saved successfully.");
        }
        dispose();
    }

    private void onPng() {
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG file", "png");
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showSaveDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filepath = file.getAbsolutePath();
            if (!filepath.endsWith(".png")) {
                filepath += ".png";
            }
            try {
                if (isSolved) {
                    maze.downloadPng(filepath, true);
                } else {
                    maze.downloadPng(filepath, false);
                }
                MessageUtils.SuccessMessage("PNG file saved successfully.");
            } catch (IOException ioException) {
                MessageUtils.ErrorMessage("Error saving PNG file.");
            }
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
