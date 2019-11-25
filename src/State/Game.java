package State;

import Tile.TileGrid;
import Asset.Character;
import Asset.Slime;
import static Asset.World.*;
import static helpers.Artist.resetAlpha;
import static helpers.ControllerSupport.controller;
import helpers.GameState;
import static helpers.RNG.randInt;
import java.util.ArrayList;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.*;

public class Game implements State{

    private Character character;
    private Slime slime;
    private TileGrid grid;
    private int spawnX;
    private int spawnY;
    private int enemyCount = 5;
    private int attackSlimeID;
    
    private ArrayList<Slime> slimes;

    public Game() {
        
        //Initialize
        grid = new TileGrid(WIDTH, HEIGHT, BLOCK_SIZE);
        character = new Character((WIDTH/2 - BLOCK_SIZE), 0, BLOCK_SIZE, BLOCK_SIZE, 100, 5+50, 5);
        //slime = new Slime(spawnX, spawnY, BLOCK_SIZE, BLOCK_SIZE, 100, 5);
        slimes = new ArrayList<>();
        instanciateEnemies();
    }
    
    @Override
    public void start() {
        if (slimes.size() == 1) {
            instanciateEnemies();
        }
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        grid.Draw();
        /*
        slime.animate(character);
        slime.update((float) 0.5);
        slime.draw();*/
        spawnEnemies();
        character.animate(WIDTH, HEIGHT);
        character.update(1);
        character.draw();
        input();
        /*
        if (character.getX() != x || character.getY() != y) {
            System.out.println("Char Loc  = " + character.getX() + ", " + character.getY());
            x =(int) character.getX();
            y =(int) character.getY();
        }*/
        
    }
    
    public void instanciateEnemies() {
        for (int i = 1; i <= enemyCount; i++) {
            spawnX = randInt(BLOCK_SIZE, WIDTH - BLOCK_SIZE);
            spawnY = randInt(BLOCK_SIZE, HEIGHT - BLOCK_SIZE);
            
            slime = new Slime(spawnX, spawnY, BLOCK_SIZE, BLOCK_SIZE, 100, 5);
            
            slimes.add(slime);
        }
    }
    
    public void spawnEnemies() {
        for (int i = 0; i < slimes.size(); i++) {
            if (slimes.get(i).isAlive()) {
                slimes.get(i).animate(character);
                slimes.get(i).update((float) 0.5);
                slimes.get(i).draw();
                if (slimes.get(i).intersects(character)) {
                    attackSlimeID = i;
                    state = GameState.BATTLE;
                }
            }
            else slimes.remove(i);
        }
    }
    
    @Override
    public void input() {
          while (Keyboard.next()) {
            if(Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                    state = GameState.MAIN_MENU;
                }
                else if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                    resetAlpha();
                    state = GameState.STATUS_SCREEN;
                }
            }
        }
          
        while (Controllers.next()) {
            if (Controllers.getEventButtonState()) {
                if (controller.isButtonPressed(6)) {
                    state = GameState.MAIN_MENU;
                }
                else if (controller.isButtonPressed(7)) {
                    resetAlpha();
                    state = GameState.STATUS_SCREEN;
                }
            }
        }
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public ArrayList<Slime> getSlimes() {
        return slimes;
    }

    public void setSlimes(ArrayList<Slime> slimes) {
        this.slimes = slimes;
    }

    public Slime attackSlime() {
        return slimes.get(attackSlimeID);
    }

    @Override
    public void keyboardInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void controllerInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
