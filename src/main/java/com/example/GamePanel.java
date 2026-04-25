package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    // Static reference for KeyHandler access
    public static GamePanel gp;

    // PLAYER & INPUT
    public Player player;
    KeyHandler keyH = new KeyHandler();
    
    // TILE & MAP
    public static TileManager tileM;
    
    // CUSTOMERS & QUEUE
    public CustomerManager customerM;
    
    // KITCHEN & ORDERS
    public KitchenStation cookingStation;
    public ArrayList<Order> activeOrders;

    // === SCREEN SETTINGS (PERFECT FOR PIXEL ART!) ===
    public static final int ORIGINAL_TILE_SIZE = 32; // 32x32px base pixel size
    public static int SCALE = 2; // Zoom in/out by changing this value!
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    // === GAME STATES ===
    public static final int STATE_MENU = 0;
    public static final int STATE_SETTINGS = 1;
    public static final int STATE_GAME = 2;
    public static final int STATE_ROUND_START = 3;
    public static final int STATE_ROUND_END = 4;
    public static final int STATE_SHOP = 5;
    public static final int STATE_ROLE_SELECT = 6;
    public int currentState = STATE_MENU; // Start at Menu

    // === MENU SELECTION ===
    public int menuOption = 0; // 0 = Start, 1 = Settings, 2 = Quit

    // === SETTINGS ===
    public static float zoomLevel = 1.0f; // Default zoom
    public boolean soundOn = true;
    
    // === SCORING ===
    public int score = 0;
    public int coins = 0;
    public Order carriedOrder = null;
    
    // === ROUND SYSTEM ===
    public int roundNumber = 1;
    public int roundTime = 180; // 3 minutes = 180 seconds
    public int timeLeft = roundTime;
    public int targetScore = 50;
    
    public boolean roundRunning = false;
    long timerStart = 0;
    
    // === UPGRADE STATS ===
    public float cookSpeedMultiplier = 1.0f;
    public int riceCapacity = 5;
    public int maxOrders = 5;
    
    // === SHOP & ROLES ===
    public Shop shop;
    public RoleSelector roleSelect;
    public int currentRole = 0;

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
        
        // Set static reference for KeyHandler
        GamePanel.gp = this;

        player = new Player(); // Create player object
        tileM = new TileManager(this); // Initialize tile manager
        customerM = new CustomerManager(this); // Initialize customer manager
        
        activeOrders = new ArrayList<>();
        // Place station near counter area
        cookingStation = new KitchenStation(GamePanel.TILE_SIZE * 12, GamePanel.TILE_SIZE * 6);
        
        shop = new Shop(this);
        roleSelect = new RoleSelector(this);
    }

    public void startGame() {
        gameLoop.start();
    }
    
    public void startRound() {
        // Reset game state
        timeLeft = roundTime;
        score = 0;
        activeOrders.clear();
        carriedOrder = null;
        player.hasItem = false;
        player.carrying = null;
        
        // Reset customers
        customerM.customerQueue.clear();
        
        roundRunning = true;
        timerStart = System.currentTimeMillis();
        currentState = STATE_GAME;
    }
    
    public void endRound() {
        roundRunning = false;
        currentState = STATE_ROUND_END;
        
        if(score >= targetScore) {
            // WIN!
            coins += roundNumber * 10; // Bonus coins
        } else {
            // LOSE
            // No penalty, just can upgrade before trying again
        }
    }

    // === UPDATE ALL GAME LOGIC HERE ===
    public void update() {
        
        if(currentState == STATE_GAME) {
            
            if(roundRunning) {
                // === GAME RUNNING ===
                player.up = keyH.up;
                player.down = keyH.down;
                player.left = keyH.left;
                player.right = keyH.right;
                player.update();
                
                customerM.update();
                cookingStation.update();
                
                for(Order o : activeOrders) o.update();
                
                // Countdown Timer
                if(System.currentTimeMillis() - timerStart >= 1000) {
                    timeLeft--;
                    timerStart = System.currentTimeMillis();
                    
                    // Check Time Up
                    if(timeLeft <= 0) {
                        endRound();
                    }
                }
                
                // Check Win Condition
                if(score >= targetScore) {
                    endRound();
                }
                
                // Interaction
                handleInteraction();
                
            } else {
                // Round not started yet - show "Press ENTER to Start"
            }
        }
        else if(currentState == STATE_ROUND_END) {
            // Show result screen
        }
    }
    
    private void handleInteraction() {
        if(keyH.interact) {
            Customer firstCustomer = customerM.getFirstInLine();
            
            // Take Order
            if(firstCustomer != null && !firstCustomer.orderTaken) {
                double dist = Math.sqrt(Math.pow(player.worldX - firstCustomer.worldX, 2) + 
                                        Math.pow(player.worldY - firstCustomer.worldY, 2));
                if(dist < TILE_SIZE * 1.5) {
                    firstCustomer.orderTaken = true;
                    firstCustomer.orderShown = true;
                    Dish dish = new Dish(firstCustomer.orderName, 5, 10);
                    Order newOrder = new Order(firstCustomer, dish);
                    activeOrders.add(newOrder);
                    cookingStation.addOrder(newOrder);
                    customerM.removeFirst();
                }
            }
            
            // Start Cooking
            double distToStove = Math.sqrt(Math.pow(player.worldX - cookingStation.worldX, 2) + 
                                          Math.pow(player.worldY - cookingStation.worldY, 2));
            if(distToStove < TILE_SIZE) {
                for(Order o : cookingStation.ordersHere) {
                    if(!o.isCooking) {
                        o.isCooking = true;
                    }
                    else if(o.isReady && carriedOrder == null) {
                        // Pick up food
                        carriedOrder = o;
                        player.hasItem = true;
                        player.carrying = o;
                    }
                }
            }
        }
        
        // === SERVE FOOD ===
        if(carriedOrder != null && keyH.interact) {
            Customer c = carriedOrder.customer;
            if(c != null && !c.served) {
                double dist = Math.sqrt(Math.pow(player.worldX - c.worldX, 2) + 
                                        Math.pow(player.worldY - c.worldY, 2));
                if(dist < TILE_SIZE * 1.5) {
                    // SUCCESS!
                    c.served = true;
                    score += 10;
                    coins += 5;
                    
                    // Remove from list
                    activeOrders.remove(carriedOrder);
                    cookingStation.ordersHere.remove(carriedOrder);
                    
                    carriedOrder = null;
                    player.hasItem = false;
                    player.carrying = null;
                }
            }
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
        else if (currentState == STATE_SHOP)
            shop.draw(g2);
        else if (currentState == STATE_ROLE_SELECT)
            roleSelect.draw(g2);
        else if (currentState == STATE_GAME)
            drawGame(g2);
        else if (currentState == STATE_ROUND_END)
            drawRoundEnd(g2);

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
        // Draw map FIRST
        tileM.draw(g2);
        // Draw customers
        customerM.draw(g2);
        // Draw kitchen station
        cookingStation.draw(g2);
        // Draw player ON TOP
        player.draw(g2);
        
        // === ROUND UI ===
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("ROUND " + roundNumber, 200, 30);
        g2.drawString("TIME: " + timeLeft + "s", 350, 30);
        g2.drawString("TARGET: " + targetScore, 500, 30);
        
        g2.drawString("SCORE: " + score, 10, 30);
        g2.drawString("COINS: " + coins, 10, 60);
    }
    
    // === ROUND END SCREEN ===
    private void drawRoundEnd(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        
        if(score >= targetScore) {
            g2.setColor(Color.GREEN);
            g2.drawString("YOU WIN!", 200, 150);
        } else {
            g2.setColor(Color.RED);
            g2.drawString("GAME OVER", 200, 150);
        }
        
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("SCORE: " + score + " / " + targetScore, 220, 220);
        g2.drawString("COINS EARNED: " + (roundNumber * 10), 220, 250);
        
        // === UPGRADE MENU ===
        g2.drawString("[ 1 ] 🛒 UPGRADE EQUIPMENT", 100, 320);
        g2.drawString("[ 2 ] 👥 HIRE / LEVEL UP STAFF", 100, 350);
        g2.drawString("[ 3 ] 🎮 CHANGE ROLE", 100, 380);
        g2.drawString("[ ENTER ] PLAY AGAIN", 100, 430);
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