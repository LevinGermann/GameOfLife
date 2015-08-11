package ch.kbw.gameoflife;

/**
 *
 * @author Levin Germann
 */
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Cell implements Serializable{

    private final int x;
    private final int y;
    private boolean alive;
    private boolean nextround;
    static int size = 10;
    static boolean grid = true;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setNextRound(boolean nextround) {
        this.nextround = nextround;
    }

    public void nextRound() {
        alive = nextround;
    }

    public void draw(Graphics g) {
        if (grid) {
            g.setColor(Color.black);
            g.drawRect(x * size, y * size, size, size);
            if (alive) {
                g.setColor(Color.orange);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(x * size + 1, y * size + 1, size - 1, size - 1);
        } else {
            if (alive) {
                g.setColor(Color.orange);
            } else {
                g.setColor(Color.WHITE);
            }
            g.fillRect(x * size, y * size, size, size);
        }
    }
}
