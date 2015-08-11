package ch.kbw.gameoflife;

/**
 *
 * @author Levin Germann
 */
public class Main {

    static int width;
    static int height;

    public static void main(String[] args) {
        Frame f = new Frame();
        f.setDefaultCloseOperation(3);
        f.setVisible(true);
        f.setResizable(false);

        width = f.getWidth();
        height = f.getHeight();
        f.createScreen();

        long saved1 = System.currentTimeMillis();
        while (true) {
            long saved2 = System.currentTimeMillis();
            float timeDif = (float) ((saved2 - saved1) / 1000.0);
            saved1 = saved2;

            f.update(timeDif);

            f.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
