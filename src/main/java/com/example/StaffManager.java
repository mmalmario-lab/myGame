package com.example;

import java.awt.*;
import java.util.ArrayList;

public class StaffManager {
    
    GamePanel gp;
    public ArrayList<Staff> workers;
    
    // Hiring prices
    int[] chefPrices = {40, 80, 150};
    int[] serverPrices = {30, 70, 130};
    int[] washerPrices = {20, 50, 100};
    
    public StaffManager(GamePanel gp) {
        this.gp = gp;
        workers = new ArrayList<>();
    }
    
    public boolean hireChef() {
        if(gp.coins >= chefPrices[gp.chefLevel] && gp.chefLevel < 3) {
            gp.coins -= chefPrices[gp.chefLevel];
            workers.add(new Staff("Chef", gp.chefLevel + 1));
            gp.chefLevel++;
            return true;
        }
        return false;
    }
    
    public boolean hireServer() {
        if(gp.coins >= serverPrices[gp.serverLevel] && gp.serverLevel < 3) {
            gp.coins -= serverPrices[gp.serverLevel];
            workers.add(new Staff("Server", gp.serverLevel + 1));
            gp.serverLevel++;
            return true;
        }
        return false;
    }
    
    public boolean hireWasher() {
        if(gp.coins >= washerPrices[gp.washerLevel] && gp.washerLevel < 3) {
            gp.coins -= washerPrices[gp.washerLevel];
            workers.add(new Staff("Washer", gp.washerLevel + 1));
            gp.washerLevel++;
            return true;
        }
        return false;
    }
    
    public void updateAll() {
        for(Staff s : workers) {
            s.update();
        }
    }
    
    public void drawAll(Graphics2D g2) {
        for(Staff s : workers) {
            s.draw(g2);
        }
    }
}
