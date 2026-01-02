import greenfoot.*;

public class NavButton extends MenuButton {
    public NavButton(String label) {
        super(label);
    }

    public void act() {
        super.act();
        if (Greenfoot.mousePressed(this)) {
            if (label.equals("Tutorial")) {
                // Navigate to TutorialWorld (music continues playing)
                Greenfoot.setWorld(new TutorialWorld());
            } else if (label.equals("About")) {
                // About functionality - music continues playing
                System.out.println("Buka About");
            }
        }
    }
}