package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

    public int worldX, worldY;
    public int size = GamePanel.TILE_SIZE;
    public int speed = 2;

    public boolean up, down, left, right;

    // Player Sprite
    public BufferedImage sprite;

    public Player() {
        // Load your art!
        sprite = GamePanel.loadImage("sprites/player.png");

        // Spawn position
        worldX = GamePanel.SCREEN_WIDTH / 2;
        worldY = GamePanel.SCREEN_HEIGHT / 2;
    }

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

    public void draw(Graphics2D g2) {
        if (sprite != null) {
            // Draw your pixel art, sharp and crisp!
            g2.drawImage(sprite, worldX, worldY, size, size, null);
        } else {
            // Fallback: if image not found, draw red square
            g2.setColor(new Color(200, 100, 100));
            g2.fillRect(worldX, worldY, size, size);
        }
    }
}