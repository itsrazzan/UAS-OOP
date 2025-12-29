import greenfoot.*;

public class ItemStatusBar extends Actor {
    private int playerNumber;
    private String currentImage = "";
    private int iconSize = 35;

    public ItemStatusBar(int playerNum) {
        this.playerNumber = playerNum;
        updateBar("", ""); // Mulai dengan kosong
    }

    public void updateBar(String itemName, String imagePath) {
        this.currentImage = imagePath;

        if (imagePath != null && !imagePath.isEmpty()) {
            // Tampilkan icon item
            try {
                GreenfootImage img = new GreenfootImage(imagePath);
                img.scale(iconSize, iconSize);
                setImage(img);
            } catch (Exception e) {
                // Jika gambar tidak ditemukan, tampilkan placeholder
                setEmptyImage();
            }
        } else {
            // Tidak ada item aktif
            setEmptyImage();
        }
    }

    private void setEmptyImage() {
        // Buat gambar transparan
        GreenfootImage img = new GreenfootImage(iconSize, iconSize);
        img.setTransparency(0);
        setImage(img);
    }
}
