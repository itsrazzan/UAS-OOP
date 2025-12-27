import greenfoot.*;

public class HealthBar extends Actor {
    private int width = 400;
    private int height = 300;
    private int playerNum;

    public HealthBar(int playerNum) {
        this.playerNum = playerNum;
        updateBar(100); // Mulai dengan HP 100
    }

    public void updateBar(int hp) {
        GreenfootImage img = new GreenfootImage(width, 50);
        // Background Bar (Merah)
        img.setColor(Color.RED);
        img.fillRect(0, 10, width, 30);
        // Foreground Bar (Hijau)
        img.setColor(Color.BLUE);
        int currentHpWidth = (int)((hp / 100.0) * width);
        img.fillRect(0, 10, currentHpWidth, 30);
        
        // Teks Player
        img.setColor(Color.WHITE);
        img.drawString("PLAYER " + playerNum + " : " + hp + " HP", 10, 30);
        setImage(img);
    }
}