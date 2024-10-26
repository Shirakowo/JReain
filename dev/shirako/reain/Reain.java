package dev.shirako.reain;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class Reain extends JFrame {
    private Color[] blkc = {Color.blue, Color.blue, Color.blue, Color.blue};
    private BufferedImage bi;
    private Graphics g;
    private final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final GraphicsDevice gd = ge.getDefaultScreenDevice();
    private final DisplayMode dm = gd.getDisplayMode();
    private final int sw = dm.getWidth();
    private final int sh = dm.getHeight();

    public Reain() throws Exception {
        setTitle("Reain");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setFocusable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);        
        setIconImage(ImageIO.read(getClass().getResource("/Resources/Reain.png")));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 27:
                        System.exit(0);
                        break;
                    case 68:
                        blkc[0] = Color.red;
                        drawBlocks();
                        break;
                    case 70:
                        blkc[1] = Color.red;
                        drawBlocks();
                        break;
                    case 74:
                        blkc[2] = Color.red;
                        drawBlocks();
                        break;
                    case 75:
                        blkc[3] = Color.red;
                        drawBlocks();
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
                        drawBlocks();
                        break;
                    case 70:
                        blkc[1] = Color.blue;
                        drawBlocks();
                        break;
                    case 74:
                        blkc[2] = Color.blue;
                        drawBlocks();
                        break;
                    case 75:
                        blkc[3] = Color.blue;
                        drawBlocks();
                        break;
                    default:
                        break;
                }
            }
        });
        bi = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
        g = bi.getGraphics();

        setVisible(true);
    }

    private void drawBlocks() {
        int xw = sw / 2;
        int yh = sh / 2;

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

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, this);
    }
}