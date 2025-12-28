import greenfoot.*;

public class DiffButton extends MenuButton {
    private int difficulty;

    public DiffButton(String label, int diff) {
        super(label);
        this.difficulty = diff;
    }

    public void act() {
        super.act(); // Jalankan efek hover dari induk
        if (Greenfoot.mousePressed(this)) {
            Greenfoot.setWorld(new BattleArena(difficulty));
        }
    }
}