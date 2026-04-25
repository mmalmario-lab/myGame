package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    
    public TileManager(GamePanel gp) {
        this.gp = gp;
        
        tile = new Tile[10]; // Max 10 tile types for now
        mapTileNum = new int[gp.MAX_COL][gp.MAX_ROW];
        
        getTileImage();
        loadMap();
    }
    
    public void getTileImage() {
        try {
            // 0 = Floor
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("assets/tiles/floor.png"));
            
            // 1 = Wall / Boundary
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("assets/tiles/wall.png"));
            tile[1].collision = true;
            
            // 2 = Table
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("assets/tiles/table.png"));
            tile[2].collision = true;
            
            // 3 = Counter / Station
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("assets/tiles/counter.png"));
            tile[3].collision = true;
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadMap() {
        // This is your restaurant layout!
        // 0=floor, 1=wall, 2=table, 3=counter
        int map[][] = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,3,3,3,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,3,3,3,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
        
        // Copy to our map array
        for(int row=0; row<gp.MAX_ROW; row++) {
            for(int col=0; col<gp.MAX_COL; col++) {
                mapTileNum[col][row] = map[row][col];
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        for(int row=0; row<gp.MAX_ROW; row++) {
            for(int col=0; col<gp.MAX_COL; col++) {
                
                int tileX = col * gp.TILE_SIZE;
                int tileY = row * gp.TILE_SIZE;
                
                int num = mapTileNum[col][row];
                
                if(tile[num] != null && tile[num].image != null) {
                    g2.drawImage(tile[num].image, tileX, tileY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                }
            }
        }
    }
    
    // Check if tile has collision
    public boolean isCollision(int col, int row) {
        if(col >=0 && col < gp.MAX_COL && row >=0 && row < gp.MAX_ROW) {
            int num = mapTileNum[col][row];
            return tile[num].collision;
        }
        return true; // Out of bounds = collision
    }
}
