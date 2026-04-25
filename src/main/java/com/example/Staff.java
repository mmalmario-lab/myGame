package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Staff {
    
    public int worldX, worldY;
    public int size = GamePanel.TILE_SIZE;
    public String role;
    public int speed;
    public int efficiency; // How fast they work
    public BufferedImage sprite;
    
    public boolean isWorking = false;
    
    public Staff(String role, int level) {
        this.role = role;
        this.speed = 1 + level;
        this.efficiency = level; // 1=Novice, 2=Experienced, 3=Expert
        
        loadSprite();
        setPosition();
    }
    
    public void loadSprite() {
        switch(role) {
            case "Chef": sprite = GamePanel.loadImage("sprites/staff/chef.png"); break;
            case "Server": sprite = GamePanel.loadImage("sprites/staff/server.png"); break;
            case "Washer": sprite = GamePanel.loadImage("sprites/staff/washer.png"); break;
        }
    }
    
    public void setPosition() {
        // Place them near their stations
        if(role.equals("Chef")) {
            worldX = GamePanel.TILE_SIZE * 11;
            worldY = GamePanel.TILE_SIZE * 6;
        } else if(role.equals("Washer")) {
            worldX = GamePanel.TILE_SIZE * 13;
            worldY = GamePanel.TILE_SIZE * 7;
        }
    }
    
    public void update() {
        
        if(role.equals("Chef")) {
            // Automatically start cooking orders
            for(Order o : GamePanel.gp.activeOrders) {
                if(!o.isCooking && !o.isReady) {
                    o.isCooking = true;
                    break;
                }
            }
        }
        
        if(role.equals("Washer")) {
            // Clean plates automatically
            GamePanel.gp.riceCapacity += efficiency;
        }
    }
    
    public void draw(Graphics2D g2) {
        if(sprite != null) {
            g2.drawImage(sprite, worldX, worldY, size, size, null);
        }
        
        // Level indicator
        g2.setColor(Color.CYAN);
        g2.drawString("Lv" + efficiency, worldX, worldY - 5);
    }
}
