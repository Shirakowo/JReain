import dev.shirako.reain.Reain;
import javax.swing.SwingUtilities;

public class Program {
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Reain();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}