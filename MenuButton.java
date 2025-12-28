import greenfoot.*;

public class MenuButton extends Actor {
    protected String label;
    
    public MenuButton(String label) {
        this.label = label;
        updateImage(Color.WHITE); // Warna teks awal
    }
    
    protected void updateImage(Color textColor) {
        GreenfootImage img = new GreenfootImage(250, 60);
        img.setColor(new Color(0, 0, 0, 160)); // Background hitam transparan
        img.fillRect(0, 0, 250, 60);
        img.setColor(textColor);
        img.setFont(new Font("SansSerif", true, false, 30));
        img.drawString(label, 40, 40);
        setImage(img);
    }
    
    public void act() {
        // Efek Hover (Berubah warna saat disentuh mouse)
        if (Greenfoot.mouseMoved(this)) {
            updateImage(Color.YELLOW);
        }
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            updateImage(Color.WHITE);
        }
    }
}