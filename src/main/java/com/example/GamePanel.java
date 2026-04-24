package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {

    // PLAYER & INPUT
    public Player player;
    KeyHandler keyH = new KeyHandler();

    // === SCREEN SETTINGS (PERFECT FOR PIXEL ART!) ===
    public static final int ORIGINAL_TILE_SIZE = 32; // 32x32px base pixel size
    public static int SCALE = 2; // Zoom in/out by changing this value!
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    // === GAME STATES ===
    public static final int STATE_MENU = 0;
    public static final int STATE_SETTINGS = 1;
    public static final int STATE_GAME = 2;
    public int currentState = STATE_MENU; // Start at Menu

    // === MENU SELECTION ===
    public int menuOption = 0; // 0 = Start, 1 = Settings, 2 = Quit

    // === SETTINGS ===
    public static float zoomLevel = 1.0f; // Default zoom
    public boolean soundOn = true;

    // === FONT FOR PIXEL STYLE ===
    Font pixelFont = new Font("Monospaced", Font.BOLD, 20);

    // Screen size (16:9 ratio, perfect for top-down view)
    public static final int MAX_COL = 16;
    public static final int MAX_ROW = 9;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROW;

    // Game Loop
    GameLoop gameLoop;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(245, 222, 179)); // Warm restaurant background
        this.setDoubleBuffered(true); // Smooth rendering
        this.setFocusable(true); // Capture keyboard inputs

        gameLoop = new GameLoop(this);

        this.addKeyListener(keyH); // Detect keyboard presses

        player = new Player(); // Create player object
    }

    public void startGame() {
        gameLoop.start();
    }

    // === UPDATE ALL GAME LOGIC HERE ===
    public void update() {
        if (currentState == STATE_GAME) {
            // Only run game logic when in game screen
            player.up = keyH.up;
            player.down = keyH.down;
            player.left = keyH.left;
            player.right = keyH.right;
            player.update();
        }
    }

    // === DRAW EVERYTHING HERE ===
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Keep pixel art sharp
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // === DRAW DIFFERENT SCREENS ===
        if (currentState == STATE_MENU)
            drawMainMenu(g2);
        else if (currentState == STATE_SETTINGS)
            drawSettings(g2);
        else if (currentState == STATE_GAME)
            drawGame(g2);

        g2.dispose();
    }

    // === MAIN MENU SCREEN ===
    private void drawMainMenu(Graphics2D g2) {
        // Background
        g2.setColor(new Color(180, 90, 40)); // Warm brown
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Title
        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        String title = "PINOY FAST FOOD RUSH";
        int titleX = (SCREEN_WIDTH - title.length() * 25) / 2;
        g2.drawString(title, titleX, 100);

        // Menu Options
        g2.setFont(pixelFont);
        String[] options = { "[ START ]", "[ SETTINGS ]", "[ QUIT ]" };

        for (int i = 0; i < 3; i++) {
            if (menuOption == i) {
                g2.setColor(Color.YELLOW); // Highlight selected
            } else {
                g2.setColor(Color.WHITE);
            }
            int x = (SCREEN_WIDTH - options[i].length() * 15) / 2;
            int y = 200 + i * 60;
            g2.drawString(options[i], x, y);
        }
    }

    // === SETTINGS SCREEN ===
    private void drawSettings(Graphics2D g2) {
        g2.setColor(new Color(80, 50, 30));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2.setFont(new Font("Monospaced", Font.BOLD, 35));
        g2.setColor(Color.WHITE);
        g2.drawString("SETTINGS", (SCREEN_WIDTH - 120) / 2, 80);

        g2.setFont(pixelFont);
        // Zoom
        g2.drawString("ZOOM: " + (int) (zoomLevel * 100) + "%", 100, 180);
        g2.drawString("[ UP / DOWN ] to change", 100, 210);

        // Sound
        String soundText = soundOn ? "SOUND: ON 🔊" : "SOUND: OFF 🔇";
        g2.drawString(soundText, 100, 280);
        g2.drawString("[ ENTER ] to toggle", 100, 310);

        // Back
        g2.setColor(Color.YELLOW);
        g2.drawString("[ ESC ] BACK TO MENU", 100, 400);
    }

    // === GAME SCREEN ===
    private void drawGame(Graphics2D g2) {
        // Draw player and game objects here
        player.draw(g2);
    }

    // === IMAGE LOADER ===

    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File("assets/" + path));
        } catch (IOException e) {
            System.out.println("ERROR: Cannot load image at assets/" + path);
            return null;
        }
    }
}