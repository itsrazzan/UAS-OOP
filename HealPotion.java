import greenfoot.GreenfootImage;

public class HealPotion extends Item {
    public HealPotion() {
        GreenfootImage img = new GreenfootImage("heal_potion.png");
        setImage(img);
    }
    
    protected void applyEffect(Character p) {
        p.hp = Math.min(p.hp + 30, p.maxHp); // Tambah HP, tidak boleh lewat batas
        p.updateHealthUI();
    }
}