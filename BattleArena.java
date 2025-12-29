import greenfoot.*;

public class BattleArena extends World {
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    private int difficulty;
    public static GreenfootSound gameMusic; // Static untuk bisa diakses dari luar

    public BattleArena(int diff) {
        super(WIDTH, HEIGHT, 1);

        // Stop menu music dan mainkan game music
        MainMenu.stopMenuMusic();
        playGameMusic();

        // fungsi untuk scaling gambar
        GreenfootImage background = new GreenfootImage("background.gif");
        background.scale(WIDTH, HEIGHT);
        setBackground(background);
        this.difficulty = diff;
        prepareLevel();
    }

    private void playGameMusic() {
        if (gameMusic == null) {
            gameMusic = new GreenfootSound("game_music.mp3");
            gameMusic.setVolume(100); // Set volume 50% (0-100)
        }
        gameMusic.playLoop();
    }

    public static void stopGameMusic() {
        if (gameMusic != null) {
            gameMusic.stop();
        }
    }

    private void prepareLevel() {
        int startHP = 100;
        if (difficulty == 2) {
            startHP = 150;
        } else if (difficulty == 3) {
            startHP = 200;
        }
        // 1. LANTAI DASAR (Transparan menutupi tanah di gambar)
        // Posisi: Tengah bawah (x=800, y=870), Ukuran: Lebar layar (1600), Tinggi (40)=
        // pas di atas tanah
        addObject(new Platform(1600, 40, "transparent"), 800, 870);

        // 2. PLATFORM MELAYANG (Ganti "platform_img.png" dengan nama file asetmu)
        // Platform Kiri
        addObject(new Platform(250, 50, "platform_img.png"), 400, 600);

        // Platform Tengah (Lebih tinggi dan lebih lebar)
        addObject(new Platform(400, 50, "platform_img.png"), 800, 400);

        // Platform Kanan
        addObject(new Platform(250, 50, "platform_img.png"), 1200, 600);

        // HealthBar(int playerNum)
        HealthBar hb1 = new HealthBar(1, startHP);
        HealthBar hb2 = new HealthBar(2, startHP);

        // 2. Tambahkan HealthBar ke dunia (posisi atas layar 1600x900)
        addObject(hb1, 250, 60); // Sisi kiri atas
        addObject(hb2, 1350, 60); // Sisi kanan atas

        // EnergyBar untuk Ultimate (hanya di level Medium dan Hard)
        EnergyBar eb1 = null;
        EnergyBar eb2 = null;
        if (difficulty >= 2) {
            eb1 = new EnergyBar(1);
            eb2 = new EnergyBar(2);
            addObject(eb1, 250, 90); // Di bawah HP bar player 1
            addObject(eb2, 1350, 90); // Di bawah HP bar player 2
        }

        // ItemStatusBar untuk Hard mode (di bawah energy bar)
        ItemStatusBar ib1 = null;
        ItemStatusBar ib2 = null;
        if (difficulty == 3) {
            ib1 = new ItemStatusBar(1);
            ib2 = new ItemStatusBar(2);
            addObject(ib1, 250, 115); // Di bawah energy bar player 1
            addObject(ib2, 1350, 115); // Di bawah energy bar player 2
        }

        // tambahkan hp player sesaui kesulitan
        Player1 p1 = new Player1(hb1, eb1, ib1, difficulty);
        p1.hp = startHP;
        p1.maxHp = startHP;

        // Lakukan hal yang sama untuk Player 2
        Player2 p2 = new Player2(hb2, eb2, ib2, difficulty);
        p2.hp = startHP;
        p2.maxHp = startHP;

        // 3. Panggil Player dengan memasukkan objek HealthBar sebagai parameter
        // Sekarang constructor Player1(HealthBar bar) akan menerima hb1
        addObject(p1, 200, 760);
        addObject(p2, 1400, 760);

        // Jika mode HARD (3), tambahkan Spawner
        if (difficulty == 3) {
            addObject(new ItemSpawner(), 0, 0);
        }

    }

}