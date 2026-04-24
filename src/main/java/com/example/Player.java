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

    public Player() {
        // Spawn player at center of screen first
        worldX = GamePanel.SCREEN_WIDTH / 2;
        worldY = GamePanel.SCREEN_HEIGHT / 2;
    }

    // Update position based on direction
    public void update() {
        if (up)
            worldY -= speed;
        if (down)
            worldY += speed;
        if (left)
            worldX -= speed;
        if (right)
            worldX += speed;
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
    }
}