import greenfoot.*;

public class Player1 extends Character {
    private GreenfootImage standby, walk1, shieldImg, attack1, attack2, ult1, ult2;
    private int moveTimer = 0;
    private HealthBar myBar;
    private EnergyBar myEnergyBar;
    private ItemStatusBar myItemBar;
    private int shieldOffset = 10;
    private boolean isPositionLowered = false;
    private boolean isAttacking = false;
    private int attackFrameCounter = 0;
    private int jumpTimer = 0;
    private boolean jumpKeyPressed = false;
    private int animationFrame = 0;
    // maxHp dideklarasikan di Character, tidak perlu duplikat di sini

    public Player1(HealthBar bar, EnergyBar energyBar, ItemStatusBar itemBar, int diff) {
        this.myBar = bar;
        this.myEnergyBar = energyBar;
        this.myItemBar = itemBar;
        this.difficulty = diff;
        // Inisialisasi dan scaling gambar
        standby = new GreenfootImage("player1-standby.png");
        standby.scale(120, 180);
        walk1 = new GreenfootImage("player1-walk.png");
        walk1.scale(120, 180);
        shieldImg = new GreenfootImage("player1-shield.png");
        shieldImg.scale(160, 160);

        attack1 = new GreenfootImage("player1-attack1.png");
        attack1.scale(125, 180); // Disamakan tingginya 180 agar lebih stabil
        attack2 = new GreenfootImage("player1-attack2.png");
        attack2.scale(150, 180);

        ult1 = new GreenfootImage("player1-ult1.png");
        ult1.scale(200, 200);
        ult2 = new GreenfootImage("player1-ult2.png");
        ult2.scale(220, 200);

        setImage(standby);
    }

    public void act() {
        super.act();
        handleInput();
        fixFloatingBug(); // Penstabil posisi akhir
    }

    private void handleInput() {
        boolean moving = false;

        // 1. GERAK HORIZONTAL
        if (Greenfoot.isKeyDown("d")) {
            facingRight = true;
            move(speed);
            moving = true;
        } else if (Greenfoot.isKeyDown("a")) {
            facingRight = false;
            move(-speed);
            moving = true;
        }

        // 2. LOGIKA DOUBLE JUMP (ARCADE)
        if (jumpTimer > 0)
            jumpTimer--;
        if (Greenfoot.isKeyDown("w")) {
            if (!jumpKeyPressed) {
                if (onGround() && jumpTimer == 0) {
                    vSpeed = jumpStrength;
                    jumpCount = 1;
                    jumpTimer = 25;
                    cancelShield();
                } else if (jumpCount == 1) {
                    vSpeed = jumpStrength;
                    jumpCount = 2;
                    jumpTimer = 25;
                    cancelShield();
                }
                jumpKeyPressed = true;
            }
        } else {
            jumpKeyPressed = false;
        }

        // Reset jumpCount hanya jika menapak dan tidak sedang melompat
        if (onGround() && vSpeed >= 0 && jumpTimer == 0) {
            jumpCount = 0;
        }

        // 3. SHIELD & ANIMASI
        if (Greenfoot.isKeyDown("s") && !isShieldActive && !isAttacking) {
            activateShield();
        }

        // HIRARKI ANIMASI: Ultimate > Attack > Shield > Walk/Standby
        if (isUltimateActive) {
            animateUltimate();
        } else if (isAttacking) {
            animateAttack();
        } else if (isShieldActive) {
            setImageShield();
            if (!isShieldActive)
                resetShieldPosition();
        } else {
            if (isPositionLowered)
                resetShieldPosition();
            if (moving)
                animateWalk();
            else
                setImageNormal();
        }

        // 4. ATTACK & ULTIMATE
        String key = Greenfoot.getKey();
        if ("f".equals(key) && !isAttacking) { // Only attack if not already attacking
            cancelShield();
            executeAttack();
        }
        if ("r".equals(key) && !isAttacking && !isUltimateActive) {
            cancelShield();
            executeUltimate();
        }
    }

    // Solusi Kuat: Menghitung ulang koordinat Y berdasarkan tinggi gambar aktif
    private void fixFloatingBug() {
        // SOLUSI RADIKAL: Cek platform dengan multiple offsets
        // Ini memastikan karakter SELALU snap ke ground

        if (vSpeed >= 0) { // Hanya saat jatuh atau diam
            // Cek beberapa offset untuk menangkap semua kasus
            for (int offset = 0; offset <= 10; offset++) {
                Actor platform = getOneObjectAtOffset(0, getImage().getHeight() / 2 + offset, Platform.class);

                if (platform != null) {
                    // FOUND PLATFORM! Snap immediately
                    vSpeed = 0;

                    // Hitung posisi yang TEPAT
                    int platformTop = platform.getY() - platform.getImage().getHeight() / 2;
                    int myHeight = getImage().getHeight();
                    int targetY = platformTop - (myHeight / 2);

                    // Adjust untuk shield
                    if (isShieldActive && isPositionLowered) {
                        targetY += shieldOffset;
                    }

                    // SNAP!
                    setLocation(getX(), targetY);
                    break; // Keluar dari loop setelah snap
                }
            }
        }
    }

    private void cancelShield() {
        if (isShieldActive) {
            isShieldActive = false;
            resetShieldPosition();
            setImageNormal();
        }
    }

    private void animateWalk() {
        moveTimer++;
        if (moveTimer % 10 == 0) {
            animationFrame = (animationFrame == 0) ? 1 : 0;
            setImage(processImage(animationFrame == 1 ? walk1 : standby));
        }
    }

    private GreenfootImage processImage(GreenfootImage baseImg) {
        GreenfootImage temp = new GreenfootImage(baseImg);
        if (!facingRight)
            temp.mirrorHorizontally();
        return temp;
    }

    protected void setImageNormal() {
        setImage(processImage(standby));
    }

    protected void setImageShield() {
        setImage(processImage(shieldImg));
    }

    protected void updateHealthUI() {
        if (myBar != null)
            myBar.updateBar(hp);
    }

    protected void updateEnergyUI() {
        if (myEnergyBar != null)
            myEnergyBar.updateBar(energy);
    }

    protected void updateItemStatusUI() {
        if (myItemBar != null)
            myItemBar.updateBar(activeItem, activeItemImage);
    }

    private void executeAttack() {
        if (System.currentTimeMillis() - lastAttackTime > attackCooldown) {
            isAttacking = true;
            attackFrameCounter = 0;

            // Play attack sound
            Greenfoot.playSound("attack2.mp3");

            Player2 target = (Player2) getOneIntersectingObject(Player2.class);
            if (target != null) {
                // Basic Attack Terkena Buff Damage Boost
                target.takeDamage(baseAttack + damageBoost, getX());
            }
            lastAttackTime = System.currentTimeMillis();
        }
    }

    private void animateAttack() {
        attackFrameCounter++;
        // Slower animation: 30 frames for attack1, 50 frames total (matches Player2)
        if (attackFrameCounter < 30)
            setImage(processImage(attack1));
        else if (attackFrameCounter < 50)
            setImage(processImage(attack2));
        else {
            isAttacking = false;
            attackFrameCounter = 0;
            setImageNormal();
        }
    }

    private void executeUltimate() {
        // Ultimate butuh energi 100 (berbasis waktu)
        if (energy >= 100) {
            isUltimateActive = true;
            ultimateFrameCounter = 0;
            energy = 0; // Reset energi saat aktivasi

            // Play ultimate sound
            Greenfoot.playSound("ultimate.wav");
        }
    }

    private void animateUltimate() {
        ultimateFrameCounter++;
        if (ultimateFrameCounter < 20) {
            setImage(processImage(ult1)); // Gambar 1
        } else if (ultimateFrameCounter < 40) {
            setImage(processImage(ult2)); // Gambar 2
            // Kirim damage tepat saat gambar kedua muncul (sekali saja)
            if (ultimateFrameCounter == 21) {
                Player2 target = (Player2) getOneIntersectingObject(Player2.class);
                if (target != null)
                    target.takeDamage(ultimateDamage, getX());
            }
        } else {
            isUltimateActive = false;
            ultimateFrameCounter = 0;
            setImageNormal();
        }
    }

    public void activateShield() {
        if (!isShieldActive) {
            isShieldActive = true;
            shieldStartTime = System.currentTimeMillis();
            if (!isPositionLowered) {
                setLocation(getX(), getY() + shieldOffset);
                isPositionLowered = true;
            }
            setImageShield();
        }
    }

    private void resetShieldPosition() {
        if (isPositionLowered) {
            setLocation(getX(), getY() - shieldOffset);
            isPositionLowered = false;
        }
    }

    private boolean onGround() {
        return getOneObjectAtOffset(0, getImage().getHeight() / 2 + 2, Platform.class) != null;
    }
}