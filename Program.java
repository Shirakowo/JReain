import javax.swing.SwingUtilities;

import dev.shirako.reain.Window;

public class Program {
    public static void main(String var0[]) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Window().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        });
    }
}
