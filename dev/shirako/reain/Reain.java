package dev.shirako.reain;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Reain extends JFrame implements ActionListener, KeyListener {
    private class Game {
        private static boolean started = false;
        private static boolean paused = false;
        private static boolean ended = false;
    }
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
    Clip clip;
    JButton resumeButton, restartButton, exitButton;
    JPanel gamePanel;
    JLayeredPane layeredPane;
    BufferedImage backgroundImage;
    float dim = 0.25f;

    public Reain() throws Exception {
        super("Reain");
        new Logger();
        new Timer(0, this).start();
        
        setDefaultCloseOperation(3);
        setUndecorated(true);
        setFocusable(true);        
        setResizable(false);
        setBackground(Color.black);
        setIconImage(ImageIO.read(getClass().getResource("/assets/Reain.png")));
        addKeyListener(this);
        BufferedImage originalImage = ImageIO.read(getClass().getResource("/assets/Harumachi.png"));
        backgroundImage = darkenImage(originalImage, dim);
        
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));
        setContentPane(layeredPane);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bi, 0, 0, this);
            }
        };
        gamePanel.setOpaque(false);
        gamePanel.setBounds(0, 0, width, height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);

        int buttonWidth = 200;
        int buttonHeight = 40;
        int buttonSpacing = 20;
        int totalHeight = 3 * buttonHeight + 2 * buttonSpacing;

        resumeButton = new JButton("Resume");
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");

        resumeButton.setBounds(centerX - buttonWidth/2, centerY - totalHeight/2, buttonWidth, buttonHeight);
        restartButton.setBounds(centerX - buttonWidth/2, centerY - buttonHeight/2, buttonWidth, buttonHeight);
        exitButton.setBounds(centerX - buttonWidth/2, centerY + totalHeight/2 - buttonHeight, buttonWidth, buttonHeight);
        
        resumeButton.addActionListener(e -> resume());
        restartButton.addActionListener(e -> restart());
        exitButton.addActionListener(e -> System.exit(0));

        layeredPane.add(resumeButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(restartButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);

        hideButtons();

        setSize(width, height);
        setLocationRelativeTo(null);
        setExtendedState(6);
    }

    public void drawBlocks() {
        for (int i = 0; i < 4; i++) {
            int offset = i * 108;
            g.setColor(blkc[i]);
            g.fillRect(centerX-216+offset, (int)(centerY*1.75), 108, 27);
        }
    }

    public void draw() {
        g.clearRect(0, 0, width, height);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, width, height, null);
        }
        drawBlocks();
    }

    @Override
    public void paint(Graphics g) {
        gamePanel.repaint();
        super.paint(g);
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
        // Logger.logInfo(Integer.toString(ke.getKeyCode()));
        if (!Game.started) {
            switch (ke.getKeyCode()) {
                case 27: System.exit(0); break;
                default: start(); break;
            }
        } else {
            switch (ke.getKeyCode()) {
                case 27: pause(); break;
                case 192: restart(); break;
                case 68: blkc[0] = Color.red; break;
                case 70: blkc[1] = Color.red; break;
                case 74: blkc[2] = Color.red; break;
                case 75: blkc[3] = Color.red; break;
                case 123: takeScreenshot(); break;
                default: break;
            }
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

    private BufferedImage darkenImage(BufferedImage original, float darkenFactor) {
        BufferedImage darkened = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int rgb = original.getRGB(x, y);
                int a = (rgb >> 24) & 0xff;
                int r = (int) (((rgb >> 16) & 0xff) * darkenFactor);
                int g = (int) (((rgb >> 8) & 0xff) * darkenFactor);
                int b = (int) ((rgb & 0xff) * darkenFactor);
                darkened.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
            }
        }
        return darkened;
    }


    private void hideButtons() {
        resumeButton.setVisible(false);
        restartButton.setVisible(false);
        exitButton.setVisible(false);
    }

    private void showButtons() {
        resumeButton.setVisible(true);
        restartButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void start() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource("/assets/Harumachi.wav".toString()));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();   
        } catch (Exception ex) {
            Logger.logFatal("Failed to play music: " + ex.getMessage(), 32);
        }

        Game.started = true;
    }

    public void pause() {
        if (!Game.paused) {
            clip.stop();
            Game.paused = true;
            showButtons();
        } else {
            resume();
        }
    }

    private void resume() {
        clip.start();
        hideButtons();
        Game.paused = false;
    }

    private void restart() {
        clip.stop();
        clip.setMicrosecondPosition(0);     
        hideButtons();
        Game.paused = false;
        Game.started = false;
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

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Reain().setVisible(true);;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
