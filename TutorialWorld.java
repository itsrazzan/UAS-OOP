import greenfoot.*;

/**
 * Tutorial Screen - Modern minimalist design showing game mechanics
 */
public class TutorialWorld extends World {
    private GreenfootImage[] frames = new GreenfootImage[30];
    private int frameIndex = 0;
    private int animTimer = 0;
    private GreenfootImage tutorialContent; // Cache tutorial content to avoid recreation
    
    public TutorialWorld() {
        super(1600, 900, 1);
        
        // Load animated background frames (same as MainMenu)
        for (int i = 0; i < 30; i++) {
            frames[i] = new GreenfootImage("bg_" + i + ".png");
            frames[i].scale(1600, 900);
        }
        
        // Create tutorial content ONCE and cache it
        createTutorialContent();
        updateBackground();
        
        // Add back button
        addObject(new NavButton("Back") {
            public void act() {
                super.act();
                if (Greenfoot.mousePressed(this)) {
                    Greenfoot.setWorld(new MainMenu());
                }
            }
        }, 800, 830);
    }
    
    public void act() {
        // Animate background
        animTimer++;
        if (animTimer % 21 == 0) {
            frameIndex = (frameIndex + 1) % 30;
            updateBackground();
        }
    }
    
    // Only update background, reuse cached tutorial content
    private void updateBackground() {
        GreenfootImage bg = new GreenfootImage(frames[frameIndex]);
        bg.drawImage(tutorialContent, 0, 0);
        setBackground(bg);
    }
    
    // Create tutorial content ONCE
    private void createTutorialContent() {
        tutorialContent = new GreenfootImage(1600, 900);
        
        // Semi-transparent dark overlay for readability
        tutorialContent.setColor(new Color(0, 0, 0, 180));
        tutorialContent.fill();
        
        // Main content panel
        int panelX = 100;
        int panelY = 60;
        int panelWidth = 1400;
        int panelHeight = 720;
        
        // Panel background with subtle gradient
        for (int y = 0; y < panelHeight; y++) {
            int alpha = 200 - (y / 5);
            if (alpha < 100) alpha = 100;
            tutorialContent.setColor(new Color(20, 25, 40, alpha));
            tutorialContent.drawLine(panelX, panelY + y, panelX + panelWidth, panelY + y);
        }
        
        // Gold border
        tutorialContent.setColor(new Color(255, 200, 50));
        tutorialContent.drawRect(panelX, panelY, panelWidth, panelHeight);
        tutorialContent.drawRect(panelX + 1, panelY + 1, panelWidth - 2, panelHeight - 2);
        
        // Title
        tutorialContent.setFont(new Font("SansSerif", true, false, 48));
        tutorialContent.setColor(new Color(255, 215, 0));
        String title = "HOW TO PLAY";
        tutorialContent.drawString(title, 650, 130);
        
        // Divider line
        tutorialContent.setColor(new Color(255, 200, 50, 150));
        tutorialContent.drawLine(panelX + 50, 155, panelX + panelWidth - 50, 155);
        
        // === CONTROLS SECTION ===
        int sectionY = 190;
        tutorialContent.setFont(new Font("SansSerif", true, false, 28));
        tutorialContent.setColor(new Color(100, 180, 255));
        tutorialContent.drawString("CONTROLS", panelX + 50, sectionY);
        
        tutorialContent.setFont(new Font("SansSerif", false, false, 20));
        tutorialContent.setColor(new Color(220, 220, 220));
        
        // Player 1 controls
        tutorialContent.setColor(new Color(100, 180, 255));
        tutorialContent.drawString("Player 1:", panelX + 50, sectionY + 35);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("Move: W/A/S/D    Attack: F    Ultimate: R    Shield: S", panelX + 160, sectionY + 35);
        
        // Player 2 controls
        tutorialContent.setColor(new Color(255, 100, 100));
        tutorialContent.drawString("Player 2:", panelX + 50, sectionY + 65);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("Move: Arrow Keys    Attack: /    Ultimate: .    Shield: Down", panelX + 160, sectionY + 65);
        
        // === DIFFICULTY LEVELS SECTION ===
        sectionY = 300;
        tutorialContent.setFont(new Font("SansSerif", true, false, 28));
        tutorialContent.setColor(new Color(100, 255, 150));
        tutorialContent.drawString("DIFFICULTY LEVELS", panelX + 50, sectionY);
        
        // Table header
        tutorialContent.setFont(new Font("SansSerif", true, false, 18));
        tutorialContent.setColor(new Color(255, 200, 50));
        int tableX = panelX + 50;
        int tableY = sectionY + 35;
        tutorialContent.drawString("Level", tableX, tableY);
        tutorialContent.drawString("HP", tableX + 150, tableY);
        tutorialContent.drawString("Energy Bar", tableX + 280, tableY);
        tutorialContent.drawString("Items", tableX + 450, tableY);
        
        // Table rows
        tutorialContent.setFont(new Font("SansSerif", false, false, 18));
        tutorialContent.setColor(new Color(200, 200, 200));
        
        // Easy
        tutorialContent.setColor(new Color(100, 255, 100));
        tutorialContent.drawString("Easy", tableX, tableY + 30);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("100", tableX + 150, tableY + 30);
        tutorialContent.drawString("No", tableX + 280, tableY + 30);
        tutorialContent.drawString("No", tableX + 450, tableY + 30);
        
        // Medium
        tutorialContent.setColor(new Color(255, 200, 50));
        tutorialContent.drawString("Medium", tableX, tableY + 55);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("150", tableX + 150, tableY + 55);
        tutorialContent.drawString("Yes", tableX + 280, tableY + 55);
        tutorialContent.drawString("No", tableX + 450, tableY + 55);
        
        // Hard
        tutorialContent.setColor(new Color(255, 80, 80));
        tutorialContent.drawString("Hard", tableX, tableY + 80);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("200", tableX + 150, tableY + 80);
        tutorialContent.drawString("Yes", tableX + 280, tableY + 80);
        tutorialContent.drawString("Yes", tableX + 450, tableY + 80);
        
        // === COMBAT MECHANICS SECTION ===
        sectionY = 490;
        tutorialContent.setFont(new Font("SansSerif", true, false, 28));
        tutorialContent.setColor(new Color(255, 150, 100));
        tutorialContent.drawString("COMBAT MECHANICS", panelX + 50, sectionY);
        
        tutorialContent.setFont(new Font("SansSerif", false, false, 18));
        tutorialContent.setColor(new Color(200, 200, 200));
        
        int mechY = sectionY + 35;
        // Attack
        tutorialContent.setColor(new Color(255, 200, 50));
        tutorialContent.drawString("Attack:", panelX + 50, mechY);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("10 damage, 0.5s cooldown", panelX + 150, mechY);
        
        // Shield
        tutorialContent.setColor(new Color(255, 200, 50));
        tutorialContent.drawString("Shield:", panelX + 50, mechY + 30);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("Reduces damage by 50%, lasts 3 seconds", panelX + 150, mechY + 30);
        
        // Ultimate
        tutorialContent.setColor(new Color(255, 200, 50));
        tutorialContent.drawString("Ultimate:", panelX + 50, mechY + 60);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("30 damage, requires 100 energy (recharges in ~10 seconds)", panelX + 150, mechY + 60);
        
        // === ITEMS SECTION (Hard Mode) ===
        sectionY = 640;
        tutorialContent.setFont(new Font("SansSerif", true, false, 28));
        tutorialContent.setColor(new Color(200, 100, 255));
        tutorialContent.drawString("ITEMS (Hard Mode Only)", panelX + 50, sectionY);
        
        tutorialContent.setFont(new Font("SansSerif", false, false, 18));
        tutorialContent.setColor(new Color(200, 200, 200));
        
        int itemY = sectionY + 35;
        tutorialContent.setColor(new Color(100, 255, 100));
        tutorialContent.drawString("Heal Potion:", panelX + 50, itemY);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("+30 HP", panelX + 200, itemY);
        
        tutorialContent.setColor(new Color(255, 100, 100));
        tutorialContent.drawString("Damage Boost:", panelX + 350, itemY);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("+10 damage for 7s", panelX + 520, itemY);
        
        tutorialContent.setColor(new Color(100, 200, 255));
        tutorialContent.drawString("Speed Boost:", panelX + 750, itemY);
        tutorialContent.setColor(new Color(200, 200, 200));
        tutorialContent.drawString("+50% speed for 7s", panelX + 900, itemY);
        
        // Tip at bottom
        tutorialContent.setFont(new Font("SansSerif", false, true, 16));
        tutorialContent.setColor(new Color(150, 150, 150));
        tutorialContent.drawString("TIP: Double-tap jump key to perform a double jump!", panelX + 50, 750);
    }
}

