import greenfoot.*;

public class BattleArena extends World {
  public static final int WIDTH = 1600;
  public static final int HEIGHT = 900;

  public BattleArena() {
    super(WIDTH, HEIGHT, 1);
    // prepareBackground();
    // setPaintOrder(Player.class, Item.class, Platform.class);
    // fungsi untuk scaling gambar
    // 2.Load gambar secara eksplisit dari folder images
    GreenfootImage background = new GreenfootImage("background.gif");
    // 3. Tarik (scale) gambar agar pas dengan resolusi World
    background.scale(WIDTH, HEIGHT);

    // 4. Pasang sebagai background utama
    setBackground(background);

    prepareLevel();
  }

  private void prepareLevel() {
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
    HealthBar hb1 = new HealthBar(1);
    HealthBar hb2 = new HealthBar(2);

    // 2. Tambahkan HealthBar ke dunia (posisi atas layar 1600x900)
    addObject(hb1, 250, 60); // Sisi kiri atas
    addObject(hb2, 1350, 60); // Sisi kanan atas

    // 3. Panggil Player dengan memasukkan objek HealthBar sebagai parameter
    // Sekarang constructor Player1(HealthBar bar) akan menerima hb1
    addObject(new Player1(hb1), 200, 760);
    addObject(new Player2(hb2), 1400, 760);
  }
}