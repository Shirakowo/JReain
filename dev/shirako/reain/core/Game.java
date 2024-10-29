package dev.shirako.reain.core;

public class Game {
    public int noteCount;
    public float score;
    public int combo;
    public int maxCombo;
    public int perfectCount;
    public int missCount;

    public void notePerfect() {
        perfectCount++;
        combo++;
        score += 300;
        if (combo > maxCombo) {
            maxCombo = combo;
        }
    }

    public void noteMiss() {
        missCount++;
        combo = 0;
    }
}