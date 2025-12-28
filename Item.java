import greenfoot.*;

public abstract class Item extends Actor {
    public void act() {
        // Cek apakah ada karakter (Player1 atau Player2) yang menyentuh item ini
        Character p = (Character) getOneIntersectingObject(Character.class);
        if (p != null) {
            applyEffect(p); // Panggil efek spesifik
            getWorld().removeObject(this); // Hapus item dari dunia
        }
    }
    
    // Method ini akan diisi berbeda-beda oleh tiap jenis item
    protected abstract void applyEffect(Character p);
}