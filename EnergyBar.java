import greenfoot.*;

public class EnergyBar extends Actor {
    private double maxEnergy = 100;
    private double currentEnergy;
    private int barWidth = 150;
    private int barHeight = 12;
    private int playerNumber;

    public EnergyBar(int playerNum) {
        this.playerNumber = playerNum;
        this.currentEnergy = 0; // Mulai dari 0
        updateBar(currentEnergy);
    }

    public void updateBar(double energy) {
        this.currentEnergy = Math.min(energy, maxEnergy); // Cap at 100
        GreenfootImage img = new GreenfootImage(barWidth + 4, barHeight + 4);

        // Background Bar (Bingkai)
        img.setColor(Color.DARK_GRAY);
        img.fillRect(0, 0, barWidth + 4, barHeight + 4);
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, barWidth + 3, barHeight + 3);

        // Isi Bar (Warna Hijau/Kuning tergantung level)
        if (currentEnergy >= 100) {
            img.setColor(new Color(0, 255, 100)); // Hijau terang saat penuh
        } else {
            img.setColor(new Color(50, 200, 50)); // Hijau biasa
        }

        int currentBarWidth = (int) ((currentEnergy / maxEnergy) * barWidth);
        img.fillRect(2, 2, currentBarWidth, barHeight);

        // Teks Energy
        img.setColor(Color.WHITE);
        img.setFont(new Font("SansSerif", true, false, 10));
        img.drawString("ULT: " + (int) currentEnergy + "%", 5, 11);

        setImage(img);
    }
}
