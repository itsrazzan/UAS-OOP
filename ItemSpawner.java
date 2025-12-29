import greenfoot.*;

public class ItemSpawner extends Actor {
    private int spawnTimer = 0;
    private int nextSpawnTime = 600; // Awal: 10 detik (10 * 60)

    public void act() {
        spawnTimer++;
        if (spawnTimer >= nextSpawnTime) {
            spawnRandomItem();
            spawnTimer = 0;
            // Acak waktu berikutnya antara 10 - 15 detik
            nextSpawnTime = 600 + Greenfoot.getRandomNumber(300);
        }
    }

    private void spawnRandomItem() {
        int chance = Greenfoot.getRandomNumber(3);
        Item newItem;

        if (chance == 0)
            newItem = new HealPotion();
        else if (chance == 1)
            newItem = new SpeedBoost();
        else
            newItem = new DamageBoost();

        // Munculkan di atas layar agar jatuh mengikuti gravitasi
        int x = 200 + Greenfoot.getRandomNumber(1200);
        int y = 50; // Spawn di atas layar
        getWorld().addObject(newItem, x, y);
    }
}