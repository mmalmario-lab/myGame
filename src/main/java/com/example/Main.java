package com.example;

import javax.swing.*;

/**
 * Main - Game entry point
 * Initializes and launches the game window
 */
public class Main {
    public static void main(String[] args) {
        // Game Window
        JFrame window = new JFrame("Pinoy Fast Food Rush");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true); // Player can resize as they want
        window.setLocationRelativeTo(null); // Open window at center

        // Add game screen
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();
        window.setVisible(true);

        // Start the game!
        gamePanel.startGame();
    }
}