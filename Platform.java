import greenfoot.*;

public class Platform extends Actor {
    public Platform(int width, int height, String imageName) {
        GreenfootImage img;
        
        if (imageName.equals("transparent")) {
            // Untuk lantai dasar yang tidak terlihat
            img = new GreenfootImage(width, height);
            img.setColor(new Color(0, 0, 0, 0)); 
            img.fill();
        } else {
            // Untuk platform melayang dengan gambar custom
            img = new GreenfootImage(imageName);
            img.scale(width, height);
        }
        setImage(img);
    }
}