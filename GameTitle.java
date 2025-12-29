import greenfoot.*;

/**
 * Judul Game dengan style pixel art
 */
public class GameTitle extends Actor {

    public GameTitle() {
        updateImage();
    }

    public void act() {
        // Static title, no animation needed
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(800, 80);

        // Text dengan style pixel/retro
        img.setFont(new Font("SansSerif", true, false, 60));

        // Hitung posisi x untuk center text (estimasi lebar text ~650px)
        int textX = 75; // (800 - 650) / 2

        // Shadow effect
        img.setColor(new Color(80, 50, 20));
        img.drawString("KNIGHT BATTLE GAME", textX + 3, 55);

        // Main text - Gold/Orange color
        img.setColor(new Color(255, 220, 100));
        img.drawString("KNIGHT BATTLE GAME", textX, 50);

        // Highlight di atas
        img.setColor(new Color(222, 128, 43));
        img.drawString("KNIGHT BATTLE GAME", textX, 52);

        setImage(img);
    }
}
