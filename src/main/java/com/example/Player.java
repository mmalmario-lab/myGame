package com.example;

import java.awt.*;

public class Player {

    // Position & Size
    public int worldX, worldY;
    public int size = GamePanel.TILE_SIZE; // Fits perfectly with our tile system

    // Movement Speed
    public int speed = 2;

    // Direction flags
    public boolean up, down, left, right;
    
    // Carrying
    public Order carrying = null;
    public boolean hasItem = false;

    public Player() {
        // Spawn player at center of screen first
        worldX = GamePanel.SCREEN_WIDTH / 2;
        worldY = GamePanel.SCREEN_HEIGHT / 2;
    }

    // Update position based on direction
    public void update() {
        // Calculate new position first
        int newX = worldX;
        int newY = worldY;
        
        if (up)
            newY -= speed;
        if (down)
            newY += speed;
        if (left)
            newX -= speed;
        if (right)
            newX += speed;
        
        // Only move if no collision!
        if(canMove(newX, worldY)) worldX = newX;
        if(canMove(worldX, newY)) worldY = newY;
    }
    
    public boolean canMove(int x, int y) {
        // Convert pixel position to tile coordinates
        int tileCol = x / GamePanel.TILE_SIZE;
        int tileRow = y / GamePanel.TILE_SIZE;
        
        // Check all 4 corners of player
        if(GamePanel.tileM.isCollision(tileCol, tileRow)) return false;
        if(GamePanel.tileM.isCollision(tileCol + (size/GamePanel.TILE_SIZE), tileRow)) return false;
        if(GamePanel.tileM.isCollision(tileCol, tileRow + (size/GamePanel.TILE_SIZE))) return false;
        if(GamePanel.tileM.isCollision(tileCol + (size/GamePanel.TILE_SIZE), tileRow + (size/GamePanel.TILE_SIZE))) return false;
        
        return true;
    }

    // Draw player (for now we use a placeholder, later replace with your pixel
    // art!)
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(200, 100, 100)); // Nice red color for player
        g2.fillRect(worldX, worldY, size, size);

        // Optional: Draw outline to make it pop
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(worldX, worldY, size, size);
        
        // Draw item being carried
        if(hasItem && carrying != null) {
            g2.setColor(Color.ORANGE);
            g2.fillOval(worldX + 10, worldY - 10, 12, 12);
        }
    }
}