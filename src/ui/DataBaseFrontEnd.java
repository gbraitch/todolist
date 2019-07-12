package ui;//<sscce>
import javax.swing.JOptionPane;

class DataBaseFrontEnd {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                "<html><body><center>Welcome to the " +
                        "<br><em>XYZ DataBase Application</em>");
        String response = JOptionPane.showInputDialog(null,
                "Please enter your information ");
        JOptionPane.showMessageDialog(null,
                "Sending to D/B: '" + response + "'");
    }
}
