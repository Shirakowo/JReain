import javax.swing.SwingUtilities;

import dev.shirako.reain.Window;

public class Program implements Runnable {
    public static void main(String var0[]) {
        Thread server = new Thread(new ReaiNET());
        Thread client = new Thread(new Program());

        server.start();
        client.start();
    }

    @Override
    public void run() {
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
