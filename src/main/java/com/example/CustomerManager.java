package com.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class CustomerManager {
    
    GamePanel gp;
    public ArrayList<Customer> customerQueue;
    Random random;
    
    int spawnTimer = 0;
    int spawnInterval = 180; // Spawn new customer every 3 seconds (at 60fps)
    
    public CustomerManager(GamePanel gp) {
        this.gp = gp;
        customerQueue = new ArrayList<>();
        random = new Random();
    }
    
    public void update() {
        // Spawn new customer
        spawnTimer++;
        if(spawnTimer >= spawnInterval && customerQueue.size() < 5) { // Max 5 in line
            spawnCustomer();
            spawnTimer = 0;
        }
        
        // Update positions so they line up
        for(int i = 0; i < customerQueue.size(); i++) {
            Customer c = customerQueue.get(i);
            c.queueIndex = i;
            c.updatePosition(i);
            
            // Decrease patience over time
            if(!c.orderTaken) {
                if(i == 0) c.patience -= 0.3; // First in line loses patience faster
                else c.patience -= 0.1;
            }
            
            // If patience 0, customer leaves
            if(c.patience <= 0) {
                customerQueue.remove(c);
                break;
            }
        }
    }
    
    public void spawnCustomer() {
        int type = random.nextInt(3); // 0, 1, 2
        Customer c = new Customer(type);
        customerQueue.add(c);
    }
    
    public Customer getFirstInLine() {
        if(!customerQueue.isEmpty()) {
            return customerQueue.get(0);
        }
        return null;
    }
    
    public void removeFirst() {
        if(!customerQueue.isEmpty()) {
            customerQueue.remove(0);
        }
    }
    
    public void draw(Graphics2D g2) {
        for(Customer c : customerQueue) {
            c.draw(g2);
        }
    }
}
