import greenfoot.*;

public class MainMenu extends World {
    private GreenfootImage[] frames = new GreenfootImage[30];
    private int frameIndex = 0;
    private int animTimer = 0;
    public static GreenfootSound menuMusic; // Static agar bisa diakses dari luar
    private boolean musicStarted = false;

    public MainMenu() {
        super(1600, 900, 1);
        for (int i = 0; i < 30; i++) {
            frames[i] = new GreenfootImage("bg_" + i + ".png");
            frames[i].scale(1600, 900);
        }
        setBackground(frames[0]);
        prepare();
        // Music tidak dimulai di sini - tunggu sampai game di-run
    }

    // Method ini dipanggil ketika tombol Run ditekan
    public void started() {
        // Only start music if not already playing (prevents restart when returning from game)
        if (menuMusic == null || !menuMusic.isPlaying()) {
            playMenuMusic();
        }
        musicStarted = true;
    }

    // Method ini dipanggil ketika game di-pause
    public void stopped() {
        if (menuMusic != null) {
            menuMusic.pause();
        }
    }

    private void playMenuMusic() {
        if (menuMusic == null) {
            menuMusic = new GreenfootSound("menu_music.mp3");
            menuMusic.setVolume(100); // Set volume 50% (0-100)
        }
        // Only start if not already playing
        if (!menuMusic.isPlaying()) {
            menuMusic.playLoop();
        }
    }

    public static void stopMenuMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }
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
        // Tambahkan judul game di tengah atas
        addObject(new GameTitle(), 800, 150);

        // Tambahkan tombol-tombol di tengah layar
        addObject(new DiffButton("Easy", 1), 800, 320);
        addObject(new DiffButton("Medium", 2), 800, 420);
        addObject(new DiffButton("Hard", 3), 800, 520);
        addObject(new NavButton("Tutorial"), 800, 640);
        addObject(new NavButton("About"), 800, 740);
    }
}