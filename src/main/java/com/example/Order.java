package com.example;

public class Order {
    public Customer customer;
    public Dish dish;
    public int waitingTime = 0;
    public int cookTimeLeft;
    public boolean isCooking = false;
    public boolean isReady = false;
    public boolean isCarried = false;
    
    public int urgencyLevel = 0;
    
    public Order(Customer customer, Dish dish) {
        this.customer = customer;
        this.dish = dish;
        this.cookTimeLeft = dish.cookTime * 60; // Convert seconds to frames (60fps)
    }

    public void update() {
        if(isCooking && !isReady) {
            cookTimeLeft--;
            if(cookTimeLeft <= 0) {
                isReady = true;
            }
        }
        
        waitingTime++;
        updateUrgency();
    }
        
        if(ratio > 3.0) urgencyLevel = 3; // CRITICAL - FLASHING RED!
        else if(ratio > 2.0) urgencyLevel = 2; // URGENT - ORANGE
        else if(ratio > 1.2) urgencyLevel = 1; // BUSY - YELLOW
        else urgencyLevel = 0; // NORMAL - GREEN
        
        // Families get extra urgency!
        if(customer.type == 2 && urgencyLevel < 3) urgencyLevel++;
    }
}
