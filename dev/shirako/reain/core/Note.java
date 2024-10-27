package dev.shirako.reain.core;

import java.awt.*;
import dev.shirako.reain.Reain;

public class Note {
    private int x; // PosX
    private int y; // PosY

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
        this.y = 0;
    }

    public void move() {
        y += 20;
    }

    public void drawNote(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, 108, 27);
    }
}
