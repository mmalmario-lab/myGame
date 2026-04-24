package com.example;

/**
 * GameLoop - 60 FPS timer
 * Maintains consistent game update and rendering at 60 frames per second
 */
public class GameLoop implements Runnable {

    private final int FPS = 60;
    private Thread gameThread;
    private GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                gamePanel.update(); // Update logic
                gamePanel.repaint(); // Draw screen
                delta--;
            }
        }
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}