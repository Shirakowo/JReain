package dev.shirako.reain;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

public class Window extends Frame implements Runnable {
    public static final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static final GraphicsDevice dev = env.getDefaultScreenDevice();
    public static final DisplayMode display = dev.getDisplayMode();
    public static final int width = display.getWidth();
    public static final int height = display.getHeight();
    public static final int centerX = width / 2;
    public static final int centerY = height / 2;
    
    BufferedImage image;
    BufferedImage buffer;
    float factor = 0.5f;
    long lastTime = System.nanoTime();
    long lastFrameTime = lastTime;
    int frames = 0;
    int fps = 0;

    Block[] blocks;

    public Window() throws Exception {
        super("Reain");
        new Thread(this).start();
        setExtendedState(6);
        setUndecorated(true);
        setFocusable(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 0x1b: System.exit(0); break;
                    case 0x26: rescale(+0.05f); break;
                    case 0x28: rescale(-0.05f); break;
                    case 0x44: blocks[0].enabled = true; break;
                    case 0x46: blocks[1].enabled = true; break;
                    case 0x4a: blocks[2].enabled = true; break;
                    case 0x4b: blocks[3].enabled = true; break;
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case 0x44: blocks[0].enabled = false; break;
                    case 0x46: blocks[1].enabled = false; break;
                    case 0x4a: blocks[2].enabled = false; break;
                    case 0x4b: blocks[3].enabled = false; break;
                }
            }
        });

        image = ImageIO.read(new File("MarbleBlue/MarbleBlue.png"));
        buffer = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        blocks = new Block[4];
        blocks[0] = new Block(new Vector2(-162, -405));
        blocks[1] = new Block(new Vector2(-54, -405));
        blocks[2] = new Block(new Vector2(54, -405));
        blocks[3] = new Block(new Vector2(162, -405));
    }

    public void rescale(float factor) {
        this.factor = Math.max(0, Math.min(this.factor + factor, 1f));
        System.out.println(this.factor);
    }

    @Override
    public void run() {
        while (true) {
            long now = System.nanoTime();
            frames++;
            
            Time.deltaTime = (now - lastFrameTime) / 1000000000f;
            lastFrameTime = now;

            if (now - lastTime >= 1000000000) {
                fps = frames;
                frames = 0;
                lastTime = now;
                // System.out.println("FPS: " + fps);
            }

            repaint();

            // try {
            //     Thread.sleep(16); // ~60 frames per second
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics g = buffer.getGraphics();
        RescaleOp rescaleOp = new RescaleOp(this.factor, 0, null);
        BufferedImage buffer = new BufferedImage(this.image.getWidth(), this.image.getHeight(), this.image.getType());
        rescaleOp.filter(this.image, buffer);

        BufferedImage buffered = buffer;
        g.drawImage(buffered, 0, 0, null);

        g.setColor(new Color(118, 185, 0));
        g.drawString(Integer.toString(fps), 10, 20);

        for (Block block : this.blocks) {
            if (block.enabled) {
                block.color = Color.red;
            } else {
                block.color = Color.blue;
            }

            g.setColor(block.color);
            block.draw(g);
        }

        graphics.drawImage(this.buffer, 0, 0, this);
    }
}
