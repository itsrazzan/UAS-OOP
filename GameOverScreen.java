import greenfoot.*;

/**
 * Modern Game Over Screen dengan tampilan pemenang
 */
public class GameOverScreen extends World {
    private String winner;
    private int animTimer = 0;
    private int fadeAlpha = 0;

    public GameOverScreen(String winnerName) {
        super(1600, 900, 1);
        this.winner = winnerName;

        // Stop game music dan mainkan victory sound
        BattleArena.stopGameMusic();
        Greenfoot.playSound("victory.mp3");

        // Background
        GreenfootImage bg = new GreenfootImage("background.gif");
        bg.scale(1600, 900);
        // Darken the background
        GreenfootImage overlay = new GreenfootImage(1600, 900);
        overlay.setColor(new Color(0, 0, 0, 150));
        overlay.fill();
        bg.drawImage(overlay, 0, 0);
        setBackground(bg);

        // Tambahkan tombol
        addObject(new NavButton("Main Menu") {
            public void act() {
                super.act();
                if (Greenfoot.mousePressed(this)) {
                    Greenfoot.setWorld(new MainMenu());
                }
            }
        }, 800, 700);
    }

    public void act() {
        animTimer++;

        // Fade in effect
        if (fadeAlpha < 255 && animTimer % 2 == 0) {
            fadeAlpha += 5;
            if (fadeAlpha > 255)
                fadeAlpha = 255;
            updateDisplay();
        }
    }

    private void updateDisplay() {
        GreenfootImage bg = new GreenfootImage("background.gif");
        bg.scale(1600, 900);

        // Dark overlay
        GreenfootImage overlay = new GreenfootImage(1600, 900);
        overlay.setColor(new Color(0, 0, 0, 150));
        overlay.fill();
        bg.drawImage(overlay, 0, 0);

        // Victory Panel
        int panelWidth = 800;
        int panelHeight = 350;
        int panelX = 400;
        int panelY = 200;

        // Panel background dengan gradient
        for (int y = 0; y < panelHeight; y++) {
            int alpha = Math.min(fadeAlpha, 220 - y / 3);
            if (alpha < 0)
                alpha = 0;
            bg.setColor(new Color(30, 30, 60, alpha));
            bg.drawLine(panelX, panelY + y, panelX + panelWidth, panelY + y);
        }

        // Border gold
        bg.setColor(new Color(255, 200, 50, Math.min(fadeAlpha, 255)));
        bg.drawRect(panelX, panelY, panelWidth, panelHeight);
        bg.drawRect(panelX + 1, panelY + 1, panelWidth - 2, panelHeight - 2);
        bg.drawRect(panelX + 2, panelY + 2, panelWidth - 4, panelHeight - 4);

        // VICTORY! text
        bg.setFont(new Font("SansSerif", true, false, 70));
        String victoryText = "VICTORY!";
        int textX = panelX + (panelWidth - victoryText.length() * 35) / 2;

        // Shadow
        bg.setColor(new Color(0, 0, 0, Math.min(fadeAlpha, 150)));
        bg.drawString(victoryText, textX + 3, panelY + 90);

        // Gold color
        bg.setColor(new Color(255, 215, 0, Math.min(fadeAlpha, 255)));
        bg.drawString(victoryText, textX, panelY + 87);

        // Winner text
        bg.setFont(new Font("SansSerif", true, false, 45));
        String winnerText = winner + " WINS!";
        int winnerX = panelX + (panelWidth - winnerText.length() * 22) / 2;

        // Shadow
        bg.setColor(new Color(0, 0, 0, Math.min(fadeAlpha, 150)));
        bg.drawString(winnerText, winnerX + 2, panelY + 180);

        // Winner color (Player 1 = Blue, Player 2 = Red)
        if (winner.contains("1")) {
            bg.setColor(new Color(100, 180, 255, Math.min(fadeAlpha, 255)));
        } else {
            bg.setColor(new Color(255, 100, 100, Math.min(fadeAlpha, 255)));
        }
        bg.drawString(winnerText, winnerX, panelY + 177);

        // Decorative crown icon (text-based)
        bg.setFont(new Font("SansSerif", true, false, 80));
        bg.setColor(new Color(255, 200, 50, Math.min(fadeAlpha, 255)));
        bg.drawString("♔", panelX + panelWidth / 2 - 30, panelY + 290);

        // Press any key text
        if (animTimer % 60 < 40) {
            bg.setFont(new Font("SansSerif", false, true, 22));
            bg.setColor(new Color(200, 200, 200, Math.min(fadeAlpha, 200)));
            String pressText = "Click 'Main Menu' to return";
            bg.drawString(pressText, 640, panelY + 330);
        }

        setBackground(bg);
    }
}
