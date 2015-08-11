package ch.kbw.gameoflife;

/**
 *
 * @author Levin Germann
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation implements KeyListener, MouseMotionListener, MouseListener {

    private Cell[][] cells;
    private Random random;
    private final int width = Main.width / Cell.size;
    private final int height = Main.height / Cell.size;
    private int generation;
    private boolean go;
    private int button;

    public Simulation() {
        random = new Random();

        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public void update() {
        if (go) {
            generation++;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int mx = x - 1;
                    if (mx < 0) {
                        mx = width - 1;
                    }
                    int my = y - 1;
                    if (my < 0) {
                        my = height - 1;
                    }
                    int gx = (x + 1) % width;
                    int gy = (y + 1) % height;

                    int alivecounter = 0;
                    if (cells[mx][my].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[mx][y].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[mx][gy].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[x][my].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[x][gy].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[gx][my].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[gx][y].isAlive()) {
                        alivecounter++;
                    }
                    if (cells[gx][gy].isAlive()) {
                        alivecounter++;
                    }
                    if (alivecounter < 2 || alivecounter > 3) {
                        cells[x][y].setNextRound(false);
                    } else if (alivecounter == 2) {
                        cells[x][y].setNextRound(cells[x][y].isAlive());
                    } else if (alivecounter == 3) {
                        cells[x][y].setNextRound(true);
                    }
                }
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    cells[x][y].nextRound();
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y].draw(g);
            }
        }

        g.setColor(Color.RED);
        g.drawString("G: Gitter umschalten", 10, 20);
        g.drawString("Z: Random", 10, 35);
        g.drawString("R: Reset", 10, 50);
        g.drawString("Space: Start/Stop", 10, 65);
        g.drawString("Esc: Exit", 10, 80);
        g.drawString("Save: Ctrl + S", 10, 95);
        g.drawString("Load: Ctrl + O", 10, 110);
        g.drawString("Steps: " + generation, 10, 125);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
            Savegame game = new Savegame(cells);
            try {
                FileOutputStream f_out = new FileOutputStream("gameoflife.sav");
                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                obj_out.writeObject(game);
            } catch (Exception ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O) {
            FileInputStream f_in;
            try {
                f_in = new FileInputStream("gameoflife.sav");
            
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            Object obj = obj_in.readObject();

            if (obj instanceof Savegame) {
                Savegame game = (Savegame) obj;
                this.cells = game.getCells();
                
                
            }
            
            } catch (Exception ex) {
                Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_G:
                if (Cell.grid) {
                    Cell.grid = false;
                } else {
                    Cell.grid = true;
                }
                break;
            case KeyEvent.VK_Z:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        cells[x][y].setAlive(random.nextBoolean());
                    }
                }
                break;
            case KeyEvent.VK_R:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        cells[x][y].setAlive(false);
                    }
                }
                generation = 0;
                break;
            case KeyEvent.VK_SPACE:
                if (go) {
                    go = false;
                } else {
                    go = true;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!go) {
            int mx = e.getX() / Cell.size;
            int my = e.getY() / Cell.size;
            if (button == 1) {
                cells[mx][my].setAlive(true);
            } else {
                cells[mx][my].setAlive(false);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        button = e.getButton();
        if (!go) {
            int mx = e.getX() / Cell.size;
            int my = e.getY() / Cell.size;
            if (button == 1) {
                cells[mx][my].setAlive(true);
            } else {
                cells[mx][my].setAlive(false);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        button = -1;
    }
}
