package com.example;

public class Dish {
    public String name;
    public int cookTime; // seconds
    public int price;
    
    public Dish(String name, int cookTime, int price) {
        this.name = name;
        this.cookTime = cookTime;
        this.price = price;
    }
}
