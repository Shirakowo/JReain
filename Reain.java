import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Reain {
    private class Note extends JPanel {
        int posY = 0;
        int delta = 10;
      
        public Note(int delay) {
            Timer timer = new Timer(delay, e -> {
                posY += delta;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.red);
            g.fillRect(100, posY, 25, 25);
        }
    }

    final JFrame frame = new JFrame("Reain");

    public Reain() {
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == 27) {
                    System.exit(0);
                }
            }
        });
        final Note note = new Note(200);
        frame.add(note);
        frame.setVisible(true);
    }
}
