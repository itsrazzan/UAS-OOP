import greenfoot.*;

public class HealthBar extends Actor {
    private int maxHp; // Variabel penyimpan batas HP sesuai tingkat kesulitan
    private int currentHp;
    private int barWidth = 200;
    private int barHeight = 20;
    private int playerNumber;

    public HealthBar(int playerNum, int maxHp) {
        this.playerNumber = playerNum;
        this.maxHp = maxHp;
        this.currentHp = maxHp; // Di awal, HP penuh
        updateBar(currentHp);
    }

    public void updateBar(int hp) {
        this.currentHp = hp;
        GreenfootImage img = new GreenfootImage(barWidth + 4, barHeight + 4);
        
        // Background Bar (Bingkai)
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, barWidth + 2, barHeight + 2);
        
        // Isi Bar (Warna Merah)
        img.setColor(Color.RED);
        // Kalkulasi lebar bar secara persentase: (HP sekarang / HP Maks) * Lebar Bar
        int currentBarWidth = (int)((double)currentHp / maxHp * barWidth);
        img.fillRect(2, 2, currentBarWidth, barHeight);
        
        // Teks HP
        img.setColor(Color.WHITE);
        img.setFont(new Font("SansSerif", true, false, 14));
        img.drawString("P" + playerNumber + " HP: " + currentHp, 10, 16);
        
        setImage(img);
    }
}