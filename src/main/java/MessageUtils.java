import javax.swing.*;

public class MessageUtils {
    public static void ErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void SuccessMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Succcess", JOptionPane.INFORMATION_MESSAGE);
    }
}
