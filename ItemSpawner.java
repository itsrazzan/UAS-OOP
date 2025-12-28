import greenfoot.*;

public class ItemSpawner extends Actor {
    private int spawnTimer = 0;
    private int nextSpawnTime = 900; // Awal: 15 detik (15 * 60)

    public void act() {
        spawnTimer++;
        if (spawnTimer >= nextSpawnTime) {
            spawnRandomItem();
            spawnTimer = 0;
            // Acak waktu berikutnya antara 15 - 20 detik
            nextSpawnTime = 900 + Greenfoot.getRandomNumber(300); 
        }
    }

    private void spawnRandomItem() {
        int chance = Greenfoot.getRandomNumber(3);
        Item newItem;
        
        if (chance == 0) newItem = new HealPotion();
        else if (chance == 1) newItem = new SpeedBoost();
        else newItem = new DamageBoost();

        // Munculkan di koordinat acak (pastikan di atas platform)
        int x = 200 + Greenfoot.getRandomNumber(1200);
        int y = 400 + Greenfoot.getRandomNumber(300);
        getWorld().addObject(newItem, x, y);
    }
}