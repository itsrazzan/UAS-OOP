import greenfoot.GreenfootImage;

public class DamageBoost extends Item {
    public DamageBoost() {
        GreenfootImage img = new GreenfootImage("damage_boost.png");
        img.scale(36,40); // Ukuran disesuaikan dengan karakter
        setImage(img);
        itemName = "Damage Boost";
        itemImage = "damage_boost.png";
    }

    protected void applyEffect(Character p) {
        p.damageBoost = 10;
        p.damageBoostTimer = 7 * 60; // 7 detik
        // Set icon timer sama dengan durasi efek
        p.setActiveItemWithTimer(itemName, itemImage, 7 * 60);
    }
}