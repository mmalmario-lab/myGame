package com.example;

import java.awt.*;

public class RoleSelector {
    
    GamePanel gp;
    public int selected = 0;
    String[] roles = {"👨‍🍳 COOK", "🧍 SERVER", "💰 CASHIER", "🧹 CLEANER", "📋 MANAGER"};
    String[] desc = {
        "Focus on cooking speed & quality",
        "Serve customers & take orders",
        "Handle payments & order flow",
        "Clean tables & wash plates",
        "Prioritize & manage everything"
    };
    
    public RoleSelector(GamePanel gp) {
        this.gp = gp;
    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(new Color(30, 50, 40));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(Color.GREEN);
        g2.drawString("SELECT YOUR ROLE", 100, 80);
        
        for(int i = 0; i < roles.length; i++) {
            if(selected == i) g2.setColor(Color.YELLOW);
            else g2.setColor(Color.WHITE);
            
            g2.setFont(new Font("Arial", Font.BOLD, 25));
            g2.drawString(roles[i], 150, 150 + i * 50);
            
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.drawString(desc[i], 150, 170 + i * 50);
        }
        
        g2.setColor(Color.WHITE);
        g2.drawString("[W/S] SELECT   [ENTER] CONFIRM", 150, gp.SCREEN_HEIGHT - 50);
    }
}
