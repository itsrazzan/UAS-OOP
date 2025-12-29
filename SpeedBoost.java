import greenfoot.GreenfootImage;

public class SpeedBoost extends Item {
    public SpeedBoost() {
        GreenfootImage img = new GreenfootImage("speed_boost.png");
        img.scale(56,40); // Ukuran disesuaikan dengan karakter
        setImage(img);
        itemName = "Speed Boost";
        itemImage = "speed_boost.png";
    }

    protected void applyEffect(Character p) {
        p.speed = (int) (p.originalSpeed * 1.5);
        p.speedBoostTimer = 7 * 60; // 7 detik (asumsi 60 FPS)
        // Set icon timer sama dengan durasi efek
        p.setActiveItemWithTimer(itemName, itemImage, 7 * 60);
    }
}