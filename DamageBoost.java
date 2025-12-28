import greenfoot.GreenfootImage;

public class DamageBoost extends Item {
    public DamageBoost() {
        setImage(new GreenfootImage("damage_boost.png"));
    }
    
    protected void applyEffect(Character p) {
        p.damageBoost = 10;
        p.damageBoostTimer = 7 * 60; // 7 detik
    }
}