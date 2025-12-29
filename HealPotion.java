import greenfoot.GreenfootImage;

public class HealPotion extends Item {
    public HealPotion() {
        GreenfootImage img = new GreenfootImage("heal_potion.png");
        img.scale(30,40); // Ukuran disesuaikan dengan karakter
        setImage(img);
        itemName = "Heal Potion";
        itemImage = "heal_potion.png";
    }

    protected void applyEffect(Character p) {
        // Langsung tambah HP dengan pengecekan batas maksimum
        int newHp = p.hp + 30;
        if (newHp > p.maxHp) {
            newHp = p.maxHp;
        }
        p.hp = newHp;
        p.updateHealthUI();
        // Set timer untuk indicator (2 detik = 120 frame)
        p.setActiveItemWithTimer(itemName, itemImage, 120);
    }
}