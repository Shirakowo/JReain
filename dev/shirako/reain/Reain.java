package dev.shirako.reain;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Reain extends JFrame implements ActionListener, KeyListener {
    private static final GraphicsEnvironment var1 = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private static final GraphicsDevice var2 = var1.getDefaultScreenDevice();
    private static final DisplayMode var3 = var2.getDisplayMode();
    public static final int width = var3.getWidth();
    public static final int height = var3.getHeight();
    public static final int centerX = width / 2;
    public static final int centerY = height / 2;
    BufferedImage bi = new BufferedImage(width, height, 2);
    Graphics g = bi.getGraphics();
    Color[] blkc = {Color.blue, Color.blue, Color.blue, Color.blue};

    public Reain() throws Exception {
        super("Reain");
        new Logger();
        new Timer(0, this).start();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);
        setUndecorated(true);
        setFocusable(true);
        setExtendedState(6);
        setBackground(Color.black);
        addKeyListener(this); 
    }

    public void takeScreenshot() {
        try {
            BufferedImage screenshot = new BufferedImage(width, height, 2);
            paint(screenshot.getGraphics());
    
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String baseFileName = "screenshot_" + timeStamp;
            String fileExtension = ".png";
            File outputFile = new File(baseFileName + fileExtension);
    
            int counter = 1;
            while (outputFile.exists()) {
                outputFile = new File(baseFileName + "_" + counter + fileExtension);
                counter++;
            }
    
            ImageIO.write(screenshot, "png", outputFile);
            Logger.logInfo("Screenshot saved as " + outputFile.getName());
        } catch (Exception ex) {
            Logger.logError("Failed to save screenshot: " + ex.getMessage());
        }
    }

    public void drawBlocks() {
        g.setColor(blkc[0]);
        g.fillRect(centerX-216, (int)(centerY*1.65), 108, 27);
        g.setColor(blkc[1]);
        g.fillRect(centerX-108, (int)(centerY*1.65), 108, 27);
        g.setColor(blkc[2]);
        g.fillRect(centerX, (int)(centerY*1.65), 108, 27);
        g.setColor(blkc[3]);
        g.fillRect(centerX+108, (int)(centerY*1.65), 108, 27);
    }

    public void draw() {
        g.clearRect(0, 0, width, height);
        drawBlocks();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        draw();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 27: System.exit(0); break;
            case 68: blkc[0] = Color.red; break;
            case 70: blkc[1] = Color.red; break;
            case 74: blkc[2] = Color.red; break;
            case 75: blkc[3] = Color.red; break;
            case 123: takeScreenshot(); break;
            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case 68: blkc[0] = Color.blue; break;
            case 70: blkc[1] = Color.blue; break;
            case 74: blkc[2] = Color.blue; break;
            case 75: blkc[3] = Color.blue; break;
            default: break;
        }
    }
}