package Tile;

import static helpers.Artist.*;

public class TileGrid {
    
    private Tile[][] arena;

    public TileGrid(int WIDTH, int HEIGHT, int tileSize) {
        arena = new Tile[WIDTH/tileSize][HEIGHT/tileSize];
        
        for (int x = 0; x < arena.length; x++) {
            for (int y = 0; y < arena[x].length; y++) {
                arena[x][y] = new Tile(x * tileSize, y * tileSize, tileSize, tileSize, TileType.Grass);
            }
        }
    }
    
    public void Draw() {
        for (int i = 0; i < arena.length; i++) {
            for (int j = 0; j < arena[i].length; j++) {
                arena[i][j].draw();
            }
        }
    }
    
}
