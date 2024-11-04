package dev.shirako.reain.core;

import dev.shirako.reain.Reain;
import java.awt.Color;
import java.awt.Graphics;

public class Note {
    private int x; // PosX
    private int y; // PosY
    private int speed = 5;

    public Note(int block) {
        switch (block) {
            case 0:
                this.x = (int)(Reain.xw-108*1.5-108/2);
                break;
            case 1:
                this.x = (int)(Reain.xw-108*0.5-108/2);
                break;
            case 2:
                this.x = (int)(Reain.xw+108*0.5-108/2);
                break;
            case 3:
                this.x = (int)(Reain.xw+108*1.5-108/2);
                break;
            default:
                throw new IllegalArgumentException("The value of block is out of range");
        }
        this.y = Reain.yh;
    }

    public void move() {
        y += speed;
    }

    public void drawNote(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 108, 27);
    }

    public boolean touchesBlock(double blockX, double blockY, int blockWidth, int blockHeight) {
        return x < blockX + blockWidth && x + 108 > blockX && y < blockY + blockHeight && y + 27 > blockY;
    }
    public boolean isPastBlock(double blockY) {
        return y > blockY;
    }
}