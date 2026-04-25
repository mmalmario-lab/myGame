package com.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean up, down, left, right;
    public boolean interact;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // === CONTROLS FOR GAME SCREEN ===
        if (GamePanel.currentState == GamePanel.STATE_GAME) {
            if (code == KeyEvent.VK_W)
                up = true;
            if (code == KeyEvent.VK_S)
                down = true;
            if (code == KeyEvent.VK_A)
                left = true;
            if (code == KeyEvent.VK_D)
                right = true;
            if (code == KeyEvent.VK_E)
                interact = true; // INTERACT KEY
        }

        // === CONTROLS FOR MAIN MENU ===
        if (GamePanel.currentState == GamePanel.STATE_MENU) {
            if (code == KeyEvent.VK_W)
                GamePanel.menuOption--;
            if (code == KeyEvent.VK_S)
                GamePanel.menuOption++;

            // Keep selection within 0-2
            if (GamePanel.menuOption < 0)
                GamePanel.menuOption = 2;
            if (GamePanel.menuOption > 2)
                GamePanel.menuOption = 0;

            // Select option with Enter
            if (code == KeyEvent.VK_ENTER)
                selectMenuOption();
        }

        // === CONTROLS FOR SETTINGS ===
        if (GamePanel.currentState == GamePanel.STATE_SETTINGS) {
            // Zoom In/Out
            if (code == KeyEvent.VK_W)
                GamePanel.zoomLevel += 0.1f;
            if (code == KeyEvent.VK_S)
                GamePanel.zoomLevel -= 0.1f;
            if (GamePanel.zoomLevel < 0.5f)
                GamePanel.zoomLevel = 0.5f; // Min zoom
            if (GamePanel.zoomLevel > 2.0f)
                GamePanel.zoomLevel = 2.0f; // Max zoom

            // Toggle Sound
            if (code == KeyEvent.VK_ENTER)
                GamePanel.soundOn = !GamePanel.soundOn;

            // Back to Menu
            if (code == KeyEvent.VK_ESCAPE)
                GamePanel.currentState = GamePanel.STATE_MENU;
        }
        
        // === SHOP CONTROLS ===
        if(GamePanel.currentState == GamePanel.STATE_SHOP) {
            if(code == KeyEvent.VK_W) GamePanel.shop.selectedOption--;
            if(code == KeyEvent.VK_S) GamePanel.shop.selectedOption++;
            if(GamePanel.shop.selectedOption < 0) GamePanel.shop.selectedOption = 0;
            if(GamePanel.shop.selectedOption >= 4) GamePanel.shop.selectedOption = 3;
            
            if(code == KeyEvent.VK_ENTER) GamePanel.shop.buy();
            if(code == KeyEvent.VK_ESCAPE) GamePanel.currentState = GamePanel.STATE_ROUND_END;
        }
        
        // === ROLE SELECTION ===
        if(GamePanel.currentState == GamePanel.STATE_ROLE_SELECT) {
            if(code == KeyEvent.VK_W) GamePanel.roleSelect.selected--;
            if(code == KeyEvent.VK_S) GamePanel.roleSelect.selected++;
            if(GamePanel.roleSelect.selected < 0) GamePanel.roleSelect.selected = 0;
            if(GamePanel.roleSelect.selected >= 5) GamePanel.roleSelect.selected = 4;
            
            if(code == KeyEvent.VK_ENTER) {
                GamePanel.currentRole = GamePanel.roleSelect.selected;
                GamePanel.gp.startRound();
            }
        }
        
        // === ROUND END CONTROLS ===
        if(GamePanel.currentState == GamePanel.STATE_ROUND_END) {
            if(code == KeyEvent.VK_1) GamePanel.currentState = GamePanel.STATE_SHOP;
            if(code == KeyEvent.VK_3) GamePanel.currentState = GamePanel.STATE_ROLE_SELECT;
            if(code == KeyEvent.VK_ENTER) {
                GamePanel.roundNumber++;
                GamePanel.targetScore += 30;
                GamePanel.gp.startRound();
            }
        }
    }

    // === MENU SELECTION FUNCTION ===
    private void selectMenuOption() {
        switch (GamePanel.menuOption) {
            case 0: // START
                GamePanel.currentState = GamePanel.STATE_ROLE_SELECT;
                break;
            case 1: // SETTINGS
                GamePanel.currentState = GamePanel.STATE_SETTINGS;
                break;
            case 2: // QUIT
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W)
            up = false;
        if (code == KeyEvent.VK_S)
            down = false;
        if (code == KeyEvent.VK_A)
            left = false;
        if (code == KeyEvent.VK_E)
            interact = false;
        if (code == KeyEvent.VK_D)
            right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}