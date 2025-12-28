import greenfoot.*;

public class MainMenu extends World {
    private GreenfootImage[] frames = new GreenfootImage[30];
    private int frameIndex = 0;
    private int animTimer = 0;

    public MainMenu() {    
        super(1600, 900, 1);
        for(int i=0; i<30; i++) {
            frames[i] = new GreenfootImage("bg_" + i + ".png");
            frames[i].scale(1600, 900);
        }
        setBackground(frames[0]);
        prepare();
    }

    public void act() {
        // Logika Video Loop
        animTimer++;
        if (animTimer % 21 == 0) { // Delay agar tidak terlalu cepat
            frameIndex = (frameIndex + 1) % 30;
            setBackground(frames[frameIndex]);
        }
    }

    private void prepare() {
        // Tambahkan tombol-tombol
        addObject(new DiffButton("Easy", 1), 800, 400);
        addObject(new DiffButton("Medium", 2), 800, 500);
        addObject(new DiffButton("Hard", 3), 800, 600);
        addObject(new NavButton("Tutorial"), 800, 720);
        addObject(new NavButton("About"), 800, 800);
    }
}