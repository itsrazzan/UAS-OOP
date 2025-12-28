import greenfoot.GreenfootImage;

public class SpeedBoost extends Item {
    public SpeedBoost() {
        setImage(new GreenfootImage("speed_boost.png"));
    }
    
    protected void applyEffect(Character p) {
        p.speed = (int)(p.originalSpeed * 1.5);
        p.speedBoostTimer = 7 * 60; // 7 detik (asumsi 60 FPS)
    }
}