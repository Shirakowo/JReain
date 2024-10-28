package dev.shirako.reain;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import javax.sound.sampled.*;
import dev.shirako.reain.core.*;
import dev.shirako.reain.logger.*;

public class Reain extends JFrame {
    private Color[] blkc = {Color.blue, Color.blue, Color.blue, Color.blue};
    private BufferedImage bi;
    private Graphics g;
    private static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice gd = ge.getDefaultScreenDevice();
    private static final DisplayMode dm = gd.getDisplayMode();
    private static final int sw = dm.getWidth();
    private static final int sh = dm.getHeight();
    public static final int xw = sw / 2;
    public static final int yh = sh / 2;
    private long lastTime = System.nanoTime();
    private int frames = 0;
    private int fps = 0;

    public Reain() throws Exception {
        setTitle("Reain");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setFocusable(true);
        setExtendedState(MAXIMIZED_BOTH);        
        setIconImage(ImageIO.read(getClass().getResource("/Resources/Reain.png")));
        setBackground(Color.black);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 27:
                        System.exit(0);
                        break;
                    case 68:
                        blkc[0] = Color.red;
                        break;
                    case 70:
                        blkc[1] = Color.red;
                        break;
                    case 74:
                        blkc[2] = Color.red;
                        break;
                    case 75:
                        blkc[3] = Color.red;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 68:
                        blkc[0] = Color.blue;
                        break;
                    case 70:
                        blkc[1] = Color.blue;
                        break;
                    case 74:
                        blkc[2] = Color.blue;
                        break;
                    case 75:
                        blkc[3] = Color.blue;
                        break;
                    default:
                        break;
                }
            }
        });
        bi = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
        g = bi.getGraphics();

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/Resources/Harumachi/Harumachi.wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();

        Note[] note = {new Note(0), new Note(1), new Note(2), new Note(3)};
        Timer timer = new Timer(15, e -> {
            drawBlocks();
            repaint();

            for (int i = 0; i < note.length; i++) {
                Note n = note[i];
                n.move();
                n.drawNote(g);
                if (n.touchesBlock(xw + (i - 1.5) * 108, yh * 1.75, 108, 27)) {
                    blkc[i] = Color.green;
                } else {
                    blkc[i] = Color.blue;
                }
            }

            long currentTime = System.nanoTime();
            frames++;

            if (currentTime - lastTime >= 1e9) {
                fps = frames;
                Logger.logInfo("FPS: " + fps);
                frames = 0;
                lastTime = currentTime;
            }
        });
        timer.start();

        setVisible(true);
    }

    private void drawBlocks() {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(blkc[0]);
        g.fillRect((int)(xw-108*1.5-108/2), (int)(yh*1.75), 108, 27); // Block 1
        g.setColor(blkc[1]);
        g.fillRect((int)(xw-108*0.5-108/2), (int)(yh*1.75), 108, 27); // Block 2
        g.setColor(blkc[2]);
        g.fillRect((int)(xw+108*0.5-108/2), (int)(yh*1.75), 108, 27); // Block 3
        g.setColor(blkc[3]);
        g.fillRect((int)(xw+108*1.5-108/2), (int)(yh*1.75), 108, 27); // Block 4
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, this);
    }
}

