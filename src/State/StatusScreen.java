package State;
import Asset.Items.*;
import Asset.Character;
import static Asset.World.*;
import static Boot.Boot.sfx;
import static helpers.Artist.*;
import static helpers.ControllerSupport.axisMove;
import static helpers.ControllerSupport.controller;
import helpers.GameState;
import static helpers.Text.printText;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.glColor3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import static helpers.Text.DrawString;
import java.text.DecimalFormat;
import org.lwjgl.input.Controllers;


public class StatusScreen implements State{
    
    private Texture statusScreen;
    private Texture selectBox;
    private Texture hoverEquipTex;
    private Texture hoverRemoveTex;
    
    private float boxX = 685f;
    private float boxY = 81f;
    private float boxSize = 85f;
    private float boxSpaceX = 31f + boxSize;
    private float boxSpaceY = 57f + boxSize;

    private float selectX = 0;
    private float selectY = 0;
    
    private float TextX = 500f;
    private float EquipBoxSpaceX = 44f + boxSize;
    
    private int index = 0;
    private DecimalFormat df;
    
    private boolean hoverEquip = true;
    private boolean hoverRemove = false;
    private boolean isSelected  = false;
    
    public StatusScreen() {
        statusScreen   = LoadTexture("src\\res\\StatusScreen\\StatusScreen.png", "PNG");
        selectBox      = LoadTexture("src\\res\\\\StatusScreen\\SelectBox.png", "PNG");
        hoverEquipTex  = LoadTexture("src\\res\\\\StatusScreen\\EquipSelected.png", "PNG");
        hoverRemoveTex = LoadTexture("src\\res\\\\StatusScreen\\RemoveSelected.png", "PNG");
        
        df = new DecimalFormat("#");
        
        DrawString(50f);
      
    }
        
    public void equipRemove() {
        if (isSelected && hoverEquip && !hoverRemove) {
            DrawQuadTex(hoverEquipTex, 0, 0, 2048, 1024);
            DrawString(67f);
            printText(689f, 626f, "Equip", Color.white);
        }
        
        else if (isSelected && !hoverEquip && hoverRemove) {
            DrawQuadTex(hoverRemoveTex, 0, 0, 2048, 1024);
            DrawString(60f);
            printText(869f, 632f, "Remove", Color.white);
        }
    }
    
    public void start(Character character) {
        int idx = 0;
        
        DrawString(50f);
        
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        DrawQuadTex(statusScreen, 0, 0, 2048, 1024);
        
        /*
        glColor3f(1.0f, 0, 0);
        DrawQuad(68f, 339f, 572f, 23f); //RED HP BAR*/
        
        float percentageHP = 572f / 100 * character.getHealth();
        
        glColor3f(0, 1.0f, 0);
        DrawQuad(68f, 339f, percentageHP, 23f); //GREEN HP BAR
        
        input(character);
        //System.out.println("Inventory Empty = " + character.getInventory().isEmpty());
        if (!character.getInventory().isEmpty()) {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 5; x++) {
                    if (idx < character.getInventory().size() && character.getInventory().get(idx) != null) {
                        glColor3f(1.0f, 1.0f, 1.0f);
                        //DrawQuadTex(items.get(idx).getImage(), boxX + (boxSpaceX*x), boxY + (boxSpaceY*y), boxSize, boxSize);
                        DrawQuadTex(character.getInventory().get(idx).getImage(), boxX + (boxSpaceX*x), boxY + (boxSpaceY*y), boxSize, boxSize);
                        idx++;
                    }  
                }
            }
        }
        
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        DrawQuadTex(selectBox ,boxX + (boxSpaceX * selectX) - 5, boxY + (boxSpaceY * selectY) - 5, boxSize + 10, boxSize + 10);
        
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        drawEquipment(character);
        
        if (index < character.getInventory().size() && character.getInventory().get(index) != null) {
            printText(37f, 610f, character.getInventory().get(index).getName(), Color.gray);
            printText(37f, 650f, character.getInventory().get(index).getDesc(), Color.gray);
            Item item = character.getInventory().get(index);
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                printText(TextX, 610f, "ATK:", Color.red);
                printText(TextX + 50f, 610f, String.valueOf(df.format(weapon.getMinDamage())) + "-" + String.valueOf(df.format(weapon.getMaxDamage())), Color.black);
            }
            
            else if (item instanceof Helmet) {
                Helmet helmet = (Helmet) item;
                printText(TextX, 610f, "DEF:", Color.red);
                printText(TextX + 70f, 610f, String.valueOf(df.format(helmet.getDefense())), Color.black);
            }

            else if (item instanceof Armor) {
                Armor armor = (Armor) item;
                printText(TextX, 610f, "DEF:", Color.red);
                printText(TextX + 70f, 610f, String.valueOf(df.format(armor.getDefense())), Color.black);
            }

            else if (item instanceof Glove) {
                Glove glove = (Glove) item;
                printText(TextX, 610f, "DEF:", Color.red);
                printText(TextX + 70f, 610f, String.valueOf(df.format(glove.getDefense())), Color.black);
            }

            else if (item instanceof Shoe) {
                Shoe shoe = (Shoe) item;
                printText(TextX, 610f, "DEF:", Color.red);
                printText(TextX + 70f, 610f, String.valueOf(df.format(shoe.getDefense())), Color.black);
            }
        }
        
        equipRemove();
        
    }
    
    public void drawEquipment(Character character) {
        int idx = 0;
        
        DrawQuadTex(character.getWeapon().getImage() ,39f + (EquipBoxSpaceX*(idx++)), 507f, boxSize, boxSize);
        DrawQuadTex(character.getHelmet().getImage() ,39f + (EquipBoxSpaceX*(idx++)), 507f, boxSize, boxSize);
        DrawQuadTex(character.getArmor().getImage() ,39f + (EquipBoxSpaceX*(idx++)), 507f, boxSize, boxSize);
        DrawQuadTex(character.getGlove().getImage() ,39f + (EquipBoxSpaceX*(idx++)), 507f, boxSize, boxSize);
        DrawQuadTex(character.getShoe().getImage() ,39f + (EquipBoxSpaceX*(idx++)), 507f, boxSize, boxSize);
        
        float statsX = 155;
        float statsY = 382;
        float spaceX = 181;
        
        float avgWeaponAtk = (character.getWeapon().getMaxDamage() - character.getWeapon().getMinDamage()) / 2 + character.getWeapon().getMinDamage();
        
        int atk = (int) (character.getStrenght() + avgWeaponAtk);
        int def = (int) (character.getDefend() + character.getArmor().getDefense() + character.getGlove().getDefense() + character.getHelmet().getDefense() + character.getShoe().getDefense());
        
        DrawString(50f);
        printText(statsX, statsY, String.valueOf(atk), Color.black);
        printText(statsX + spaceX, statsY, String.valueOf(def), Color.black);
        printText(statsX + spaceX*2 + 10, statsY, String.valueOf(character.getPotion()), Color.black);
    }

    public void swapper(Character character) {
        Item item = character.getInventory().get(index);
        
        if (item instanceof Weapon) {
                Weapon temp = character.getWeapon();
                character.setWeapon((Weapon) item);
                character.getInventory().add(index, temp);
                character.getInventory().remove(index+1);
            }
        
        else if (item instanceof Helmet) {
                Helmet temp = character.getHelmet();
                character.setHelmet((Helmet) item);
                character.getInventory().add(index, temp);
                character.getInventory().remove(index+1);
        }
        
        else if (item instanceof Armor) {
                Armor temp = character.getArmor();
                character.setArmor((Armor) item);
                character.getInventory().add(index, temp);
                character.getInventory().remove(index+1);
        }
        
        else if (item instanceof Glove) {
                Glove temp = character.getGlove();
                character.setGlove((Glove) item);
                character.getInventory().add(index, temp);
                character.getInventory().remove(index+1);
        }
        
        else if (item instanceof Shoe) {
                Shoe temp = character.getShoe();
                character.setShoe((Shoe) item);
                character.getInventory().add(index, temp);
                character.getInventory().remove(index+1);
        }
    }
    
    public void input(Character character) {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    sfx.cancel.stop();
                    if (!isSelected && index < character.getInventory().size()) {
                        isSelected = true;
                    }
                    
                    else if (isSelected) {
                        if (hoverEquip && !hoverRemove) {
                            swapper(character);
                            isSelected = false;
                        }
                        
                        else if (!hoverEquip && hoverRemove) {
                            character.getInventory().remove(index);
                            isSelected = false;
                        }
                    }
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                    sfx.confirm.stop();
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    if (isSelected) {
                        isSelected = false;
                    }
                    else {
                        
                    }
                }
                
                else if (isSelected) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                        if (hoverEquip && !hoverRemove) {
                            hoverEquip  = false;
                            hoverRemove = true;
                        }
                        else {
                            hoverEquip  = true;
                            hoverRemove = false;
                        }
                    }
                    
                    else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                        if (hoverEquip && !hoverRemove) {
                            hoverEquip  = false;
                            hoverRemove = true;
                        }
                        else {
                            hoverEquip  = true;
                            hoverRemove = false;
                        }
                    }
                }
                
                else if (!isSelected) {
                    if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                        if (selectX == 4) {
                            selectX = 0;
                            index -=4;
                        }
                        else {
                            selectX++;
                            index++;
                        }
                    }

                    else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                        if (selectX == 0) {
                            selectX = 4;
                            index+=4;
                        }
                        else {
                            selectX--;
                            index--;
                        }
                    }

                    else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                        if (selectY == 0) {
                            selectY = 3;
                            index += 15;
                        }
                        else {
                            selectY--;
                            index-=5;
                        }
                    }

                    else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                        if (selectY == 3) {
                            selectY = 0;
                            index -=15;
                        }
                        else {
                            selectY++;
                            index+=5;
                        }
                    }

                    else if  (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                        sfx.cancel.play();
                        sfx.cancel.setIsPlaying(false);
                        state = GameState.GAME;
                        }
                    }

                else {
                }
            }   
        }
        
        while (Controllers.next()) {
            if (isSelected) {
                    if (controller.getAxisValue(1) == 1.0 && !axisMove) {
                        if (hoverEquip && !hoverRemove) {
                            hoverEquip  = false;
                            hoverRemove = true;
                            axisMove = true;
                            System.out.println("Axis = " + axisMove);
                        }
                        else {
                            hoverEquip  = true;
                            hoverRemove = false;
                            axisMove = true;
                            System.out.println("Axis = " + axisMove);
                        }
                    }
                    
                    else if (controller.getAxisValue(1) == -1.0 && !axisMove) {
                        if (hoverEquip && !hoverRemove) {
                            hoverEquip  = false;
                            hoverRemove = true;
                            axisMove = true;
                            System.out.println("Axis = " + axisMove);
                        }
                        else {
                            hoverEquip  = true;
                            hoverRemove = false;
                            axisMove = true;
                            System.out.println("Axis = " + axisMove);
                        }
                    }
                    
                    else if (controller.getAxisValue(1) == 0 && controller.getAxisValue(0) == 0) axisMove = false;

                }
                
                else if (!isSelected) {
                    if (controller.getAxisValue(1) == 1.0 && !axisMove) {
                        if (selectX == 4) {
                            selectX = 0;
                            index -=4;
                            axisMove = true;
                            System.out.println("Axis  Left = " + axisMove);
                        }
                        else {
                            selectX++;
                            index++;
                            axisMove = true;
                            System.out.println("Axis Left = " + axisMove);
                        }
                    }

                    else if (controller.getAxisValue(1) == -1.0 && !axisMove) {
                        if (selectX == 0) {
                            selectX = 4;
                            index+=4;
                            axisMove = true;
                            System.out.println("Axis Right = " + axisMove);
                        }
                        else {
                            selectX--;
                            index--;
                            axisMove = true;
                            System.out.println("Axis Right = " + axisMove);
                        }
                    }

                    else if (controller.getAxisValue(0) == -1.0 && !axisMove) {
                        if (selectY == 0) {
                            selectY = 3;
                            index += 15;
                            axisMove = true;
                            System.out.println("Axis Up = " + axisMove);
                        }
                        else {
                            selectY--;
                            index-=5;
                            axisMove = true;
                            System.out.println("Axis Up = " + axisMove);
                        }
                    }
                   
                    else if (controller.getAxisValue(0) == 1.0 && !axisMove) {
                        if (selectY == 3) {
                            selectY = 0;
                            index -=15;
                            axisMove = true;
                            System.out.println("Axis Down = " + axisMove);
                        }
                        else {
                            selectY++;
                            index+=5;
                            axisMove = true;
                            System.out.println("Axis Down = " + axisMove);
                        }
                    }
                    
                    else if (controller.getAxisValue(1) == 0 && controller.getAxisValue(0) == 0) axisMove = false;
                }
            
                else if (controller.getAxisValue(1) == 0 && controller.getAxisValue(0) == 0) axisMove = false;
            
            if (Controllers.getEventButtonState()) {
                if (controller.isButtonPressed(0)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    sfx.cancel.stop();
                    if (!isSelected && index < character.getInventory().size()) {
                        isSelected = true;
                    }
                    
                    else if (isSelected) {
                        if (hoverEquip && !hoverRemove) {
                            swapper(character);
                            isSelected = false;
                        }
                        
                        else if (!hoverEquip && hoverRemove) {
                            character.getInventory().remove(index);
                            isSelected = false;
                        }
                    }
                    
                    else {
                        
                    }
                }
                
                else if (controller.isButtonPressed(1)) {
                    sfx.confirm.stop();
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    if (isSelected) {
                        isSelected = false;
                    }
                    else {
                        
                    }
                }

                else if  (controller.isButtonPressed(7)) {
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    state = GameState.GAME;
                }
            }
        }
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void input() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


                
            
        
    
    

