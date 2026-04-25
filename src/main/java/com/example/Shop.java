package com.example;

import java.awt.*;

public class Shop {
    
    GamePanel gp;
    public int selectedOption = 0;
    String[] items = {"Faster Stove (-50% time)", "Big Rice Dispenser", "Extra Counter Space", "Better Uniform (Speed+)"};
    int[] prices = {50, 30, 40, 35};
    
    public Shop(GamePanel gp) {
        this.gp = gp;
    }
    
    public void draw(Graphics2D g2) {
        // Background
        g2.setColor(new Color(40, 40, 60));
        g2.fillRect(50, 50, gp.SCREEN_WIDTH - 100, gp.SCREEN_HEIGHT - 100);
        
        // Title
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.YELLOW);
        g2.drawString("🏪 UPGRADE SHOP", 150, 90);
        
        // Coins
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("COINS: " + gp.coins, 100, 130);
        
        // Items List
        for(int i = 0; i < items.length; i++) {
            if(selectedOption == i) g2.setColor(Color.YELLOW);
            else g2.setColor(Color.WHITE);
            
            g2.drawString(items[i], 100, 180 + i * 40);
            g2.drawString("💰 " + prices[i], 450, 180 + i * 40);
        }
        
        // Instructions
        g2.setColor(Color.WHITE);
        g2.drawString("[W/S] SELECT   [ENTER] BUY   [ESC] BACK", 100, gp.SCREEN_HEIGHT - 50);
    }
    
    public void buy() {
        if(gp.coins >= prices[selectedOption]) {
            gp.coins -= prices[selectedOption];
            applyUpgrade(selectedOption);
        }
    }
    
    private void applyUpgrade(int id) {
        switch(id) {
            case 0: gp.cookSpeedMultiplier = 0.5; break; // Faster cooking
            case 1: gp.riceCapacity = 10; break;
            case 2: gp.maxOrders += 2; break;
            case 3: gp.player.speed += 1; break;
        }
    }
}
