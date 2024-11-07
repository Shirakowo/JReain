

import dev.shirako.reain.core.Game;
import dev.shirako.reain.logger.Logger;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Reain extends JFrame {
    public Color[] blkc = {Color.blue, Color.blue, Color.blue, Color.blue};
    public boolean[] keyPressed = {false, false, false, false};
    public boolean[] noteActive = {true, true, true, true};
    public BufferedImage bi;
    public Graphics g;
    public static final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice gd = ge.getDefaultScreenDevice();
    public static final DisplayMode dm = gd.getDisplayMode();
    public static final int sw = dm.getWidth();
    public static final int sh = dm.getHeight();
    public static final int xw = sw / 2;
    public static final int yh = sh / 2;
    public long lastTime = System.nanoTime();
    public int frames = 0;
    public int fps = 0;
    public Game game = new Game();
    public Clip clip;

    public Reain() throws Exception {
        new Logger();
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
                    case 27: System.exit(0);
                    case 68: keyPressed[0] = true; break;
                    case 70: keyPressed[1] = true; break;
                    case 74: keyPressed[2] = true; break;
                    case 75: keyPressed[3] = true; break;
                    case 123: takeScreenshot(); break;
                    default: break;
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 68: keyPressed[0] = false; break;
                    case 70: keyPressed[1] = false; break;
                    case 74: keyPressed[2] = false; break;
                    case 75: keyPressed[3] = false; break;
                    default: break;
                }
            }
        });
        bi = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
        g = bi.getGraphics();

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/Resources/Harumachi/Harumachi.wav"));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    
        clip.start();

        // Note[] note = {new Note(0), new Note(1), new Note(2), new Note(3)};
        Timer timer = new Timer(0, e -> {
            drawBlocks();
            drawHUD();
            repaint();

            for (int i = 0; i < keyPressed.length; i++) {
                if (keyPressed[i]) {
                    blkc[i] = Color.red;
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

    public void drawBlocks() {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < blkc.length; i++) {
            g.setColor(blkc[i]);
            g.fillRect((int)(xw + (i - 1.5) * 108 - 108 / 2), (int)(yh * 1.75), 108, 27);
        }
    }

    public void drawHUD() {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 24));

        String comboText = game.combo + "x";
        g.drawString(comboText, 10, sh - 30);

        String scoreText = String.format("%08d", (int)game.score);
        g.drawString(scoreText, sw - 110, 30);
    }

    public void takeScreenshot() {
        try {
            BufferedImage screenshot = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
            paint(screenshot.getGraphics());
    
            String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
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
        } catch (IOException e) {
            Logger.logError("Failed to save screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
