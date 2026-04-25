package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class KitchenStation {
    
    public int worldX, worldY;
    public int size = GamePanel.TILE_SIZE;
    public BufferedImage image;
    public ArrayList<Order> ordersHere;
    
    public KitchenStation(int x, int y) {
        this.worldX = x;
        this.worldY = y;
        ordersHere = new ArrayList<>();
        loadImage();
    }
    
    public void loadImage() {
        image = GamePanel.loadImage("sprites/stations/stove.png");
    }
    
    public void addOrder(Order order) {
        ordersHere.add(order);
    }
    
    public void update() {
        // Update all orders here
        for(Order o : ordersHere) {
            if(o.isCooking) {
                // Cooking logic here later
            }
            o.updateUrgency(); // Update priority
        }
    }
    
    public void draw(Graphics2D g2) {
        // Draw station
        if(image != null) {
            g2.drawImage(image, worldX, worldY, size, size, null);
        }

        int x = worldX + 5;
        for(Order o : ordersHere) {
            // Color indicator
            Color color;
            switch(o.urgencyLevel) {
                case 3: color = Color.RED; break;
                case 2: color = Color.ORANGE; break;
                case 1: color = Color.YELLOW; break;
                default: color = Color.GREEN; break;
            }
            g2.setColor(color);
            g2.fillRect(x, worldY - 20, 8, 8);

            // === PROGRESS BAR ===
            if(o.isCooking) {
                int totalWidth = 20;
                int progressWidth = (int)((double)(o.dish.cookTime * 60 - o.cookTimeLeft) / (o.dish.cookTime * 60) * totalWidth);
                
                g2.setColor(Color.DARK_GRAY);
                g2.fillRect(x, worldY - 30, totalWidth, 5);
                g2.setColor(Color.YELLOW);
                g2.fillRect(x, worldY - 30, progressWidth, 5);
            }

            // === READY SIGN ===
            if(o.isReady) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                g2.drawString("READY!", x, worldY - 35);
            }

            x += 15;
        }
        }
    }
}
