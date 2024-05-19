import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame {

    public JPanel MainFrame;
    private JButton UploadText;
    private JButton UploadBin;
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

    MainFrame(){
        UploadText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(file.getName().endsWith(".txt")){
                        // TODO operacja wczytania labiryntu; BCText.setText(selectedFile.getPath());
                        JOptionPane.showMessageDialog(null, "The file was successfully loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Select a text file (.txt).", "Error", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        });

        UploadBin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary file", "bin");
                fileChooser.setFileFilter(filter);
                int response = fileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    if(file.getName().endsWith(".bin")){
                        // TODO operacja wczytania labiryntu; (selectedFile)
                        JOptionPane.showMessageDialog(null, "The file was successfully loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Select a text file (.bin).", "Error", JOptionPane.ERROR_MESSAGE);

                    }

                }
            }
        });

        howToUseATextArea.setEditable(false);
    }

}
