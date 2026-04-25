package com.example;

import java.awt.*;
import java.util.ArrayList;

public class RequestQueue {
    
    GamePanel gp;
    public ArrayList<ExtraRequest> queue;
    public int currentIndex = 0;
    
    public RequestQueue(GamePanel gp) {
        this.gp = gp;
        queue = new ArrayList<>();
    }
    
    public void addRequest(String type, Customer customer) {
        queue.add(new ExtraRequest(type, customer));
    }
    
    public void update() {
        if(queue.isEmpty()) return;
        
        ExtraRequest current = queue.get(currentIndex);
        current.timeLeft--;
        
        // If time's up and not done -> Move to next (RR Logic!)
        if(current.timeLeft <= 0 && !current.isDone) {
            currentIndex++;
            if(currentIndex >= queue.size()) currentIndex = 0;
        }
    }
    
    public void handleCurrent() {
        if(!queue.isEmpty()) {
            ExtraRequest r = queue.get(currentIndex);
            r.isDone = true;
            gp.score += 5;
            gp.coins += 2;
            queue.remove(r);
            if(currentIndex > 0) currentIndex--;
        }
    }
    
    public void draw(Graphics2D g2) {
        // Draw small queue on side of screen
        int x = gp.SCREEN_WIDTH - 100;
        int y = 100;
        
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x-10, y-20, 90, 150);
        
        for(int i=0; i<queue.size(); i++) {
            ExtraRequest r = queue.get(i);
            
            if(i == currentIndex) g2.setColor(Color.YELLOW); // Current being processed
            else g2.setColor(Color.WHITE);
            
            String icon = "";
            if(r.type.equals("Rice")) icon = "🍚";
            if(r.type.equals("Drink")) icon = "🥤";
            if(r.type.equals("Dessert")) icon = "🍰";
            
            g2.drawString(icon + " " + r.type, x, y + i*25);
        }
    }
}
