package dev.shirako.reain;

import java.awt.Color;
import java.awt.Graphics;

public class Block {
    public boolean enabled = false;
    public Vector2 position;
    public Color color;

    public Block(Vector2 position) {
        this.position = position;
        this.color = Color.blue;
    }

    /** Draw the block */
    public void draw(Graphics g) {
        int dx = Math.round(Window.centerX + this.position.x - 108 / 2);
        int dy = Math.round(Window.centerY - this.position.y - 27 / 2);

        g.fillRect(dx, dy, 108, 27);

    }
}
