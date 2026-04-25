package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Customer {
    
    public int worldX, worldY;
    public int size = GamePanel.TILE_SIZE;
    public int speed = 1; // Slower than player
    
    public BufferedImage sprite;
    public int type; // 0=Solo, 1=Barkada, 2=Family
    
    public int patience = 100;
    public boolean orderTaken = false;
    public boolean served = false;
    
    // Position in queue
    public int queueIndex = 0;
    
    // Order
    public String orderName;
    public boolean orderShown = false;

    public Customer(int type) {
        this.type = type;
        loadSprite();
        
        // Spawn position
        worldX = GamePanel.TILE_SIZE;
        worldY = GamePanel.TILE_SIZE;
        
        // Assign random order based on type
        generateOrder();
    }
    
    p
    
    public void generateOrder() {
        String[] menu = {"Chicken", "Spaghetti", "Burger Steak", "Palabok"};
        orderName = menu[(int)(Math.random() * menu.length)];
        
        // Family orders more!
        if(type == 2) {
            orderName += " (Family Size)";
        }
    }ublic void loadSprite() {
        // Later we will load different sprites for Solo/Barkada/Family
        sprite = GamePanel.loadImage("sprites/customer.png");
    }
        if(queueIndex == 0) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(worldX-2, worldY-2, size+4, size+4);
        }
        
        // Patience Bar
        g2.setColor(Color.RED);
        g2.fillRect(worldX, worldY - 10, size, 5);
        g2.setColor(Color.GREEN);
        g2.fillRect(worldX, worldY - 10, (int)(size * (patience / 100.0)), 5);
        
        // === ORDER BUBBLE ===
        if(orderShown) {
            // Draw bubble background
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(worldX - 10, worldY - 40, size + 20, 25, 10, 10);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(worldX - 10, worldY - 40, size + 20, 25, 10, 10);
            
            // Draw order text
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.setColor(Color.BLACK);
            g2.drawString(orderName, worldX - 5, worldY - 25);
        }
        
        // Highlight FIRST in line!
        if(queueIndex == 0) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(worldX-2, worldY-2, size+4, size+4);
        }
        
        // Draw Patience Bar
        g2.setColor(Color.RED);
        g2.fillRect(worldX, worldY - 10, size, 5);
        g2.setColor(Color.GREEN);
        g2.fillRect(worldX, worldY - 10, (int)(size * (patience / 100.0)), 5);
    }
}
