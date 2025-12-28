import greenfoot.*;

public abstract class Character extends Actor {
    protected int hp;
    protected int maxHp; //batasan untuk heal potion 
    protected int baseAttack;
    protected int ultimateDamage = 30;
    protected int vSpeed = 0;
    protected int speed = 7;
    protected int originalSpeed = 7;
    protected int jumpStrength = -20;
    protected int jumpCount = 0;
    protected boolean facingRight = true;

    protected boolean isShieldActive = false;
    protected long shieldStartTime = 0;
    protected long lastAttackTime = 0;
    protected long lastUltimateTime = 0;
    protected int attackCooldown = 500;
    protected int shieldDuration = 3000;
    //mekanik energy dan items
    protected double energy = 0;
    protected double energyRechargeRate = 100.0 / (15.0 * 60.0); // 100 unit dalam 15 detik (asumsi 60 fps)
    protected int damageBoost = 0;
    protected int damageBoostTimer = 0;
    protected int speedBoostTimer = 0;
    // Tambahkan di bagian variabel Character
    protected boolean isUltimateActive = false;
    protected int ultimateFrameCounter = 0;

    public void act() {
        applyGravity();

        handleShieldTimer();
        handleItemTimers();
        handleEnergyRecharge();
        checkFall();
    }

    protected void applyGravity() {
        setLocation(getX(), getY() + vSpeed);
        vSpeed += 1; // Percepatan gravitasi
    }

    protected void handleEnergyRecharge(){
        if(energy < 100){
            energy += energyRechargeRate;
        }
    }

    protected void handleShieldTimer() {
        if (isShieldActive && System.currentTimeMillis() - shieldStartTime > shieldDuration) {
            isShieldActive = false;
        }
    }

    protected void handleItemTimers() {
        if(damageBoostTimer > 0){
            damageBoostTimer--;
            //reset bonus damage
            if (damageBoostTimer == 0) damageBoost = 0;
        }
        if(speedBoostTimer > 0){
            speedBoostTimer--;
            //reset speed
            if (speedBoostTimer == 0) speed = originalSpeed; 
        }
    }

    public void takeDamage(int dmg, int attackerX) {
        if (isShieldActive) {
            hp -= (dmg * 0.5);
        } else {
            hp -= dmg;
            applyKnockback(attackerX);
        }
        updateHealthUI();
        if (this.hp <= 0)
            die();
    }

    private void die() {
        getWorld().showText("GAME OVER!", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        getWorld().removeObject(this);
    }

    private void applyKnockback(int attackerX) {
        int pushBack = (getX() < attackerX) ? -50 : 50;
        // Hanya push horizontal, TIDAK push ke atas untuk menghindari floating
        setLocation(getX() + pushBack, getY());
        // Set vSpeed sedikit negatif untuk efek "terpental" ringan
        vSpeed = -5;
    }

    protected abstract void setImageNormal();

    protected abstract void setImageShield();

    protected abstract void updateHealthUI();

    protected void checkFall() {
        if (getY() > getWorld().getHeight() - 10) {
            takeDamage(20, getX());
            setLocation(getWorld().getWidth() / 2, 100);
            vSpeed = 0;
        }
    }
}