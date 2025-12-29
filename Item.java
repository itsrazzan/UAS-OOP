import greenfoot.*;

public abstract class Item extends Actor {
    protected double vSpeed = 0; // Gunakan double untuk kecepatan pecahan
    protected String itemName = "Item"; // Nama item untuk status bar
    protected String itemImage = ""; // Path gambar item untuk icon

    public void act() {
        // Apply gravity dan platform collision
        applyGravity();

        // Cek apakah ada karakter (Player1 atau Player2) yang menyentuh item ini
        Character p = (Character) getOneIntersectingObject(Character.class);
        if (p != null) {
            applyEffect(p); // Panggil efek spesifik (termasuk set icon timer)
            getWorld().removeObject(this); // Hapus item dari dunia
        }
    }

    private void applyGravity() {
        // Jatuh mengikuti gravitasi (0.5x lebih lambat)
        setLocation(getX(), getY() + (int) vSpeed);
        vSpeed += 0.5; // Percepatan gravitasi lebih lambat

        // Cek platform collision (berhenti di atas platform)
        Actor platform = getOneObjectAtOffset(0, getImage().getHeight() / 2, Platform.class);
        if (platform != null) {
            // Snap to platform top
            int platformTop = platform.getY() - platform.getImage().getHeight() / 2;
            int myHeight = getImage().getHeight();
            setLocation(getX(), platformTop - myHeight / 2);
            vSpeed = 0;
        }
    }

    // Method ini akan diisi berbeda-beda oleh tiap jenis item
    protected abstract void applyEffect(Character p);
}