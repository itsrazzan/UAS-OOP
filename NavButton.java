import greenfoot.*;

public class NavButton extends MenuButton {
    public NavButton(String label) {
        super(label);
    }

    public void act() {
        super.act();
        if (Greenfoot.mousePressed(this)) {
            if (label.equals("Tutorial")) {
                // Kamu harus buat world TutorialWorld nanti
                // Greenfoot.setWorld(new TutorialWorld()); 
                System.out.println("Buka Tutorial");
            } else if (label.equals("About")) {
                System.out.println("Buka About");
            }
        }
    }
}