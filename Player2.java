import greenfoot.*;

public class Player2 extends Character {
    private GreenfootImage standby, walk1, shieldImg, attack1, attack2, ult1, ult2;
    private int moveTimer = 0;
    private HealthBar myBar;
    private int shieldOffset = 10;
    private boolean isPositionLowered = false;
    private boolean isAttacking = false;
    private int attackFrameCounter = 0;
    private int jumpTimer = 0;
    private boolean jumpKeyPressed = false;
    private int animationFrame = 0;
    public int maxHp; // Deklarasikan agar bisa diakses oleh Item
    private boolean attackKeyPressed = false; // Untuk mengunci input tombol O
    private boolean ultimateKeyPressed = false; // Untuk mengunci input tombol P

    public Player2(HealthBar bar) {
        this.myBar = bar;
        this.facingRight = false; // Player 2 menghadap kiri

        // Load Aset Player 2
        standby = new GreenfootImage("player2-standby.png");
        standby.scale(120, 180);
        walk1 = new GreenfootImage("player2-walk.png");
        walk1.scale(120, 180);
        shieldImg = new GreenfootImage("player2-shield.png");
        shieldImg.scale(160, 160);
        attack1 = new GreenfootImage("player2-attack1.png");
        attack1.scale(125, 180);
        attack2 = new GreenfootImage("player2-attack2.png");
        attack2.scale(150, 180);

        ult1 = new GreenfootImage("player2-ult1.png");
        ult1.scale(200, 200);
        ult2 = new GreenfootImage("player2-ult2.png");
        ult2.scale(220, 200);

        setImage(processImage(standby));
    }

    public void act() {
        super.act();
        handleInput();
        fixFloatingBug();
    }

    private void handleInput() {
        boolean moving = false;

        // 1. GERAK HORIZONTAL
        if (Greenfoot.isKeyDown("right")) {
            facingRight = true;
            move(speed);
            moving = true;
        } else if (Greenfoot.isKeyDown("left")) {
            facingRight = false;
            move(-speed);
            moving = true;
        }

        // 2. LOMPAT (Arrow Up)
        if (jumpTimer > 0)
            jumpTimer--;
        if (Greenfoot.isKeyDown("up")) {
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

        if (onGround() && vSpeed >= 0 && jumpTimer == 0)
            jumpCount = 0;

        // 3. LOGIKA ATTACK (Gunakan isKeyDown + Lock agar Responsif)
        if (Greenfoot.isKeyDown("/")) {
            if (!attackKeyPressed) { // Tombol baru ditekan
                cancelShield();
                executeAttack();
                attackKeyPressed = true; // Kunci agar tidak attack terus-menerus
            }
        } else {
            attackKeyPressed = false; // Lepas kunci saat tombol dilepas
        }

        if (Greenfoot.isKeyDown(".")) {
            if (!ultimateKeyPressed) {
                cancelShield();
                executeUltimate();
                animateUltimate();
                ultimateKeyPressed = true;
            }
        } else {
            ultimateKeyPressed = false;
        }

        // 4. SHIELD & ANIMASI
        if (Greenfoot.isKeyDown("down") && !isShieldActive && !isAttacking) {
            activateShield();
        }

        // HIRARKI ANIMASI: Attack harus paling atas
        if (isAttacking) {
            animateAttack();
        } else if (isShieldActive) {
            setImageShield();
        } else {
            if (isPositionLowered)
                resetShieldPosition();
            if (moving)
                animateWalk();
            else
                setImageNormal();
        }
    }

    // --- LOGIKA ATTACK PLAYER 2 ---
    private void executeAttack() {
        // Cek cooldown
        if (System.currentTimeMillis() - lastAttackTime > attackCooldown) {
            isAttacking = true;
            attackFrameCounter = 0; // Mulai animasi dari frame 0
            
            // Deteksi target Player1
            Player1 target = (Player1) getOneIntersectingObject(Player1.class);
            if (target != null) {
                target.takeDamage(baseAttack, getX());
            }
            lastAttackTime = System.currentTimeMillis();
        }
    }

    private void animateAttack() {
        attackFrameCounter++;
        if (attackFrameCounter < 15) {
            setImage(processImage(attack1));
        } else if (attackFrameCounter < 25) {
            setImage(processImage(attack2));
        } else {
            isAttacking = false;
            attackFrameCounter = 0;
            setImageNormal();
        }
    }

    // --- LOGIKA LAINNYA ---
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

                    // DEBUG: Print info
                    System.out.println(
                        "P2 SNAP: offset=" + offset + ", myY=" + getY() + ", platformY=" + platform.getY());
                    System.out.println("  myHeight=" + getImage().getHeight() + ", platformHeight="
                        + platform.getImage().getHeight());

                    // Hitung posisi yang TEPAT
                    int platformTop = platform.getY() - platform.getImage().getHeight() / 2;
                    int myHeight = getImage().getHeight();
                    int targetY = platformTop - (myHeight / 2);

                    System.out.println("  platformTop=" + platformTop + ", targetY=" + targetY);

                    // Adjust untuk shield
                    if (isShieldActive && isPositionLowered) {
                        targetY += shieldOffset;
                    }

                    // SNAP!
                    setLocation(getX(), targetY);
                    System.out.println("  SNAPPED to Y=" + getY());
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

    private void executeUltimate() {
        if (System.currentTimeMillis() - lastUltimateTime > 10000) {
            Player1 target = (Player1) getOneIntersectingObject(Player1.class);
            if (target != null)
                target.takeDamage(ultimateDamage, getX());
            lastUltimateTime = System.currentTimeMillis();
        }
    }

    private void animateUltimate() {
        ultimateFrameCounter++;
        if (ultimateFrameCounter < 20) {
            setImage(processImage(ult1)); // Gambar 1
        } else if (ultimateFrameCounter < 40) {
            setImage(processImage(ult2)); // Gambar 2
            // Kirim damage tepat saat gambar kedua muncul
            if (ultimateFrameCounter == 21) {
                Player1 target = (Player1) getOneIntersectingObject(Player1.class);
                if (target != null) target.takeDamage(ultimateDamage, getX());
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