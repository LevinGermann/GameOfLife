package ch.kbw.gameoflife;

/**
 *
 * @author Levin Germann
 */
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Frame extends JFrame {

    private Screen s;
    private Simulation sim;
    private float timeDif;
    private float PAUSETIME = 0.05f;

    public Frame() {
        setUndecorated(true);

        String input = JOptionPane.showInputDialog(this, "Gittergrösse (px)");
        try {
            Cell.size = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.exit(0);
        }

        input = JOptionPane.showInputDialog(this, "Zeit zwischen Generation (in s)");
        try {
            PAUSETIME = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            System.exit(0);
        }

        setExtendedState(MAXIMIZED_BOTH);
    }

    public void createScreen() {
        sim = new Simulation();

        addKeyListener(sim);
        addMouseListener(sim);
        addMouseMotionListener(sim);

        s = new Screen();
        s.setBounds(0, 0, Main.width, Main.height);
        add(s);
    }

    public void update(float temp) {
        timeDif += temp;
        if (timeDif > PAUSETIME) {
            sim.update();
            timeDif = 0;
        }
    }

    @Override
    public void repaint() {
        s.repaint();
    }

    private class Screen extends JLabel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            sim.draw(g);
        }
    }
}
