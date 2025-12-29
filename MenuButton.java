import greenfoot.*;

public class MenuButton extends Actor {
    protected String label;
    private boolean isHovered = false;

    public MenuButton(String label) {
        this.label = label;
        updateImage(false);
    }

    protected void updateImage(boolean hovered) {
        int width = 280;
        int height = 60;
        GreenfootImage img = new GreenfootImage(width, height);

        // Outer border - wood/gold frame
        img.setColor(new Color(92, 111, 43)); // green sage
        img.fillRect(0, 0, width, height);

        // Inner border lighter
        img.setColor(new Color(220, 180, 80)); // Light gold
        img.fillRect(4, 4, width - 8, height - 8);

        // Main background - blue gradient seperti contoh
        for (int y = 8; y < height - 8; y++) {
            int blue = hovered ? 180 : 150;
            int offset = (y - 8) / 3;
            img.setColor(new Color(92,111,43));
            img.drawLine(8, y, width - 9, y);
        }

        // Inner frame lines
        img.setColor(new Color(100, 70, 30));
        img.drawRect(6, 6, width - 13, height - 13);

        // Text dengan style pixel
        img.setFont(new Font("SansSerif", true, false, 26));
        int textWidth = label.length() * 15;
        int textX = (width - textWidth) / 2;
        int textY = 40;

        // Shadow
        img.setColor(new Color(40, 40, 80));
        img.drawString(label, textX + 2, textY + 2);

        // Main text
        if (hovered) {
            img.setColor(new Color(255, 255, 200)); // Bright yellow when hover
        } else {
            img.setColor(new Color(220, 180, 80)); // Dlight gold text
        }
        img.drawString(label, textX, textY);

        setImage(img);
    }

    public void act() {
        if (Greenfoot.mouseMoved(this)) {
            if (!isHovered) {
                isHovered = true;
                updateImage(true);
            }
        }
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            if (isHovered) {
                isHovered = false;
                updateImage(false);
            }
        }
    }
}