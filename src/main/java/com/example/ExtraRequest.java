package com.example;

public class ExtraRequest {
    public String type; // "Rice", "Drink", "Dessert"
    public Customer customer;
    public int timeLeft; // Time slice to handle this
    public boolean isDone = false;
    
    public ExtraRequest(String type, Customer customer) {
        this.type = type;
        this.customer = customer;
        this.timeLeft = 3; // 3 seconds to handle
    }
}
