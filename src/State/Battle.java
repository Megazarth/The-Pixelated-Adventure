package State;

import Asset.Slime;
import Asset.SlimeAttack;
import Asset.Character;
import Asset.Items.Item;
import Asset.Items.List.All_Items;
import Asset.Items.Potion;
import static Asset.World.*;
import static Boot.Boot.sfx;
import static helpers.Artist.*;
import static helpers.Clock.*;
import static helpers.ControllerSupport.axisMove;
import static helpers.ControllerSupport.controller;
import helpers.GameState;
import static helpers.RNG.*;
import static helpers.Text.DrawString;
import static helpers.Text.printText;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.opengl.GL11.glColor3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Battle implements State{
    
    private boolean first = false;
    private boolean battleEndTrigger = false;
    private long currentTime;
    private long lastTime;
    private long delay;
    
    private Texture battleBackground;
    private Texture battleEnd;
    
    private Item lootLeft;
    private Item lootRight;
    
    private Texture selectionAttack;
    private Texture selectionHeal;
    private Texture selectionRun;
    
    private Texture[] selectionMenu;
    private Texture[] slimeSelection;
    
    private int selectionIndex;
    
    private Slime attackSlime;
    private SlimeAttack leftDisplaySlime, rightDisplaySlime;
    private Character character;
    private All_Items allItems;
    
    private boolean firstMenu;
    private boolean attackMenu;
    
    private boolean leftSlimeDamaged = false;
    private boolean rightSlimeDamaged = false;
    
    //BATTLE BOOLEAN
    private boolean playerPhase = true;
    private boolean enemyPhase  = false;
    private boolean leftSlimeAttack = false;
    private boolean rightSlimeAttack = false;

    public Battle() {
        battleBackground = LoadTexture("src\\res\\Battle\\BattleBackground.png", "PNG");
        battleEnd = LoadTexture("src\\res\\Battle\\BattleEnd.png", "PNG");
        selectionAttack = LoadTexture("src\\res\\Battle\\SelectionAttack.png", "PNG");
        selectionHeal = LoadTexture("src\\res\\Battle\\SelectionHeal.png", "PNG");
        selectionRun = LoadTexture("src\\res\\Battle\\SelectionRun.png", "PNG");
        
        
        selectionIndex = 0;
        
        selectionMenu = new Texture[3];
        selectionMenu[0] = selectionAttack;
        selectionMenu[1] = selectionHeal;
        selectionMenu[2] = selectionRun;
        
        slimeSelection = new Texture[2];
        
        slimeSelection[0] =  LoadTexture("src\\res\\Battle\\SelectLeftSlime.png", "PNG");
        slimeSelection[1] =  LoadTexture("src\\res\\Battle\\SelectRightSlime.png", "PNG");
        
        leftDisplaySlime = new SlimeAttack(382, 237, 120, 120, 100, 10);
        rightDisplaySlime = new SlimeAttack(783, 237, 120, 120, 100, 10);
        
        firstMenu = true;
        attackMenu = false;
        
        delay = 50;
        
        allItems = new All_Items();
    }
    
    @Override
    public void start() {
        if (!battleEndTrigger) {
            sfx.battleBGM.play();
        }
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        DrawQuadTex(battleBackground, 0, 0, 2048, 1024);
        
        float percentageHP = (299f / 100) * character.getHealth();
        
        glColor3f(0f, 1.0f, 0f);
        DrawQuad(145f, 628f, percentageHP, 22f); //GREEN HP BAR
        
        printStats();
        
        glColor3f(1.0f, 1.0f, 1.0f);
        
        /*
        if (playerPhase) {
            if (firstMenu) DrawQuadTex(selectionMenu[selectionIndex], 0, 0, 2048, 1024);
            else if (attackMenu) DrawQuadTex(slimeSelection[selectionIndex], 0, 0, 2048, 1024);
        }*/

        //DrawString(32f);
        //firstRun();
        /*
        leftDisplaySlime.animation();
        rightDisplaySlime.animation();
        leftDisplaySlime.draw();
        rightDisplaySlime.draw();*/
        battleLogic();
        slimeAnimation();
        //setCharacter();
        //printText(640, 460, "Press Enter to Continue", Color.red);
        battleEnd();
        if (playerPhase || battleEndTrigger) input();
    }
    
    public void printStats() {
        
        float avgWeaponAtk = (character.getWeapon().getMaxDamage() - character.getWeapon().getMinDamage()) / 2 + character.getWeapon().getMinDamage();
        
        int atk = (int) (character.getStrenght() + avgWeaponAtk);
        int def = (int) (character.getDefend() + character.getArmor().getDefense() + character.getGlove().getDefense() + character.getHelmet().getDefense() + character.getShoe().getDefense());
        
        DrawString(45f);
        printText(165f, 664f, String.valueOf(atk), Color.black);
        printText(289f, 664f, String.valueOf(def), Color.black);
        printText(409f, 664f, String.valueOf(character.getPotion()), Color.black);
    }
    
    public void battleLogic() {
        if (playerPhase && !enemyPhase) {
            if (firstMenu) DrawQuadTex(selectionMenu[selectionIndex], 0, 0, 2048, 1024);
            else if (attackMenu) DrawQuadTex(slimeSelection[selectionIndex], 0, 0, 2048, 1024);
        }
        
        else if (!playerPhase && enemyPhase) {
            if (!leftSlimeAttack && leftDisplaySlime.isIsAlive()) {
                leftDisplaySlime.draw();
                lastTime = getTime();
                leftSlimeAttack = true;
                sfx.slimeAttack.stop();
                leftDisplaySlime.setEngaged(true);
            }
            else if (!leftDisplaySlime.isEngaged() && !rightSlimeAttack && rightDisplaySlime.isIsAlive()){
                rightDisplaySlime.draw();
                lastTime = getTime();
                rightSlimeAttack = true;
                sfx.slimeAttack.stop();
                rightDisplaySlime.setEngaged(true);
            }
            else if (!leftDisplaySlime.isEngaged() && !rightDisplaySlime.isEngaged()) {
                playerPhase = true;
                enemyPhase = false;
                firstMenu = true;
                attackMenu = false;
                leftSlimeAttack = false;
                rightSlimeAttack = false;
                
                sfx.charAttack.stop();
                sfx.charHeal.stop();
                sfx.slimeAttack.stop();
            } 
        }
    }
    
    public void slimeAnimation() {
        int delta = 1;
                
        leftDisplaySlime.animation(character);
        rightDisplaySlime.animation(character);
        
        //System.out.println("LeftSlimeDamaged = " + leftSlimeDamaged);
        
        if (!leftSlimeDamaged && !rightSlimeDamaged)  {
            if (!leftDisplaySlime.isIsAlive()) {
                leftDisplaySlime.setCurrentState(leftDisplaySlime.getDeath());
            }
            
            else if (!rightDisplaySlime.isIsAlive()) {
                rightDisplaySlime.setCurrentState(rightDisplaySlime.getDeath());
            }
            leftDisplaySlime.draw();
            rightDisplaySlime.draw();
        }
        
        else if (leftSlimeDamaged && !rightSlimeDamaged) {
            System.out.println("Left Damaged!");
            if (!rightDisplaySlime.isIsAlive()) {
                rightDisplaySlime.setCurrentState(rightDisplaySlime.getDeath());
            }
            rightDisplaySlime.draw();
            if (!first) {
                leftDisplaySlime.draw();
                lastTime = getTime();
                first = true;
            }
        
            else {
                currentTime = getTime();
                if ((currentTime - lastTime) >= delay*delta++ && (currentTime - lastTime) <= delay*delta++) {
                    leftDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+1 && (currentTime - lastTime) <= (delay*delta++)+1) {
                    leftDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+2 && (currentTime - lastTime) <= (delay*delta++)+2) {
                    leftDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+3 && (currentTime - lastTime) <= (delay*delta++)+3) {
                    leftDisplaySlime.draw();
                }
                else if ((currentTime - lastTime) >= (delay*delta++)+4 && (currentTime - lastTime) <= (delay*delta++)+4) {
                    leftDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+5) {
                    rightDisplaySlime.draw();
                    lastTime = currentTime;
                    first = false;
                    leftSlimeDamaged = false;
                    playerPhase = false;
                    enemyPhase = true;
                }
            } 
        }
        
        else if (!leftSlimeDamaged && rightSlimeDamaged) {
            System.out.println("Right Damage!");
            if (!leftDisplaySlime.isIsAlive()) {
                leftDisplaySlime.setCurrentState(leftDisplaySlime.getDeath());
            }
            leftDisplaySlime.draw();
            if (!first) {
                rightDisplaySlime.draw();
                lastTime = getTime();
                first = true;
            }
        
            else {
                currentTime = getTime();
                if ((currentTime - lastTime) >= delay*delta++ && (currentTime - lastTime) <= delay*delta++) {
                    rightDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+1 && (currentTime - lastTime) <= (delay*delta++)+1) {
                    rightDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+2 && (currentTime - lastTime) <= (delay*delta++)+2) {
                    rightDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+3 && (currentTime - lastTime) <= (delay*delta++)+3) {
                    rightDisplaySlime.draw();
                }
                else if ((currentTime - lastTime) >= (delay*delta++)+4 && (currentTime - lastTime) <= (delay*delta++)+4) {
                    rightDisplaySlime.draw();
                }

                else if ((currentTime - lastTime) >= (delay*delta++)+5) {
                    leftDisplaySlime.draw();
                    lastTime = currentTime;
                    first = false;
                    rightSlimeDamaged = false;
                    playerPhase = false;
                    enemyPhase = true;
                }
            } 
        }
    }
    
    public void battleEnd() {
        if (!leftDisplaySlime.isIsAlive() && !rightDisplaySlime.isIsAlive()) {
            sfx.battleBGM.stop();
            sfx.victoryBGM.play();
            System.out.println("Looping");
            battleEndTrigger= true;
            firstMenu = true;
            attackMenu = false;
            glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
            DrawQuadTex(battleBackground, 0, 0, 2048, 1024);
            leftDisplaySlime.setCurrentState(leftDisplaySlime.getDeath());
            rightDisplaySlime.setCurrentState(rightDisplaySlime.getDeath());
            leftDisplaySlime.draw();
            rightDisplaySlime.draw();
            lootLogic();
            DrawQuadTex(battleEnd, 0, 0, 2048, 1024);
            DrawQuadTex(lootLeft.getImage(), 440f, 208f, 170f, 170f);
            DrawQuadTex(lootRight.getImage(), 672f, 208f, 170f, 170f);
        }
    }
    
    public void lootLogic() {
        if (lootLeft == null && lootRight == null) {
            System.out.println("One Time Only!");
            allItems = new All_Items();
            roulette();
            
            if(lootLeft instanceof Potion) {
                character.setPotion(character.getPotion() + 1);
            }
            
            else if (lootLeft instanceof Potion) {
                character.setPotion(character.getPotion() + 1);
            }
            
            else {
                character.addInventory(lootLeft);
                character.addInventory(lootRight);
            }
        }
    }
    
    public void roulette() {
        if (lootLeft == null && lootRight == null || lootRight == lootLeft) {
            lootLeft = allItems.getRandomItem();
            lootRight = allItems.getRandomItem();
            roulette();
        }
    }

    @Override
    public void input() {
        if (firstMenu) {
            inputMenu();
        }
        
        else if (attackMenu) {
            inputAttack();
        }
    }
     
    public void inputMenu() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    if (selectionIndex == 0) selectionIndex = 2;
                    else selectionIndex--;
                }

                else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                    if (selectionIndex == 2) selectionIndex = 0;
                    else selectionIndex++;
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    if (battleEndTrigger) {
                        attackSlime.setIsAlive(false);
                        leftDisplaySlime = new SlimeAttack(382, 237, 120, 120, 100, 10);
                        rightDisplaySlime = new SlimeAttack(783, 237, 120, 120, 100, 10);
                        leftSlimeDamaged = false;
                        rightSlimeDamaged = false;
                        firstMenu = true;
                        attackMenu = false;
                        battleEndTrigger = false;
                        lootLeft = null;
                        lootRight = null;
                        sfx.victoryBGM.stop();
                        state = GameState.GAME;
                        System.out.println("State = " + state);
                    }
                    
                    else if (selectionIndex == 0) { //ATTACK
                        sfx.charHeal.stop();
                        attackMenu = true;
                        firstMenu = false;
                    }
                    
                    else if (selectionIndex == 1) { //HEAL
                        if (character.getPotion() > 0) {
                            sfx.charAttack.stop();
                            sfx.charHeal.play();
                            character.heal();
                            playerPhase = false;
                            enemyPhase = true;
                        }
                    }
                    
                    else if (selectionIndex == 2) {
                        attackSlime.setLocation(randInt(BLOCK_SIZE, WIDTH - BLOCK_SIZE), randInt(BLOCK_SIZE, HEIGHT - BLOCK_SIZE));
                        leftDisplaySlime = new SlimeAttack(382, 237, 120, 120, 100, 10);
                        rightDisplaySlime = new SlimeAttack(783, 237, 120, 120, 100, 10);
                        leftSlimeDamaged = false;
                        rightSlimeDamaged = false;
                        firstMenu = true;
                        attackMenu = false;
                        battleEndTrigger = false;
                        lootLeft = null;
                        lootRight = null;
                        state = GameState.GAME;
                    }
                }
            }   
        }
        
        while (Controllers.next()) {
            
            if (controller.getAxisValue(0) == -1.0 && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 0) selectionIndex = 2;
                    else selectionIndex--;
                }
                
            else if (controller.getAxisValue(0) == 1.0 && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 2) selectionIndex = 0;
                    else selectionIndex++;
                }
            
            else if (controller.getAxisValue(0) == 0) axisMove = false;
            
            if (Controllers.getEventButtonState()) {
                
                if (controller.isButtonPressed(0)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    if (battleEndTrigger) {
                        selectionIndex =0;
                        attackSlime.setIsAlive(false);
                        leftDisplaySlime = new SlimeAttack(382, 237, 120, 120, 100, 10);
                        rightDisplaySlime = new SlimeAttack(783, 237, 120, 120, 100, 10);
                        leftSlimeDamaged = false;
                        rightSlimeDamaged = false;
                        firstMenu = true;
                        attackMenu = false;
                        battleEndTrigger = false;
                        lootLeft = null;
                        lootRight = null;
                        sfx.victoryBGM.stop();
                        state = GameState.GAME;
                        System.out.println("State = " + state);
                    }
                    
                    else if (selectionIndex == 0) { //ATTACK
                        sfx.charHeal.stop();
                        attackMenu = true;
                        firstMenu = false;
                    }
                    
                    else if (selectionIndex == 1) { //HEAL
                        if (character.getPotion() > 0) {
                            sfx.charAttack.stop();
                            sfx.charHeal.play();
                            character.heal();
                            playerPhase = false;
                            enemyPhase = true;
                        }
                    }
                    
                    else if (selectionIndex == 2) {
                        attackSlime.setLocation(randInt(BLOCK_SIZE, WIDTH - BLOCK_SIZE), randInt(BLOCK_SIZE, HEIGHT - BLOCK_SIZE));
                        leftDisplaySlime = new SlimeAttack(382, 237, 120, 120, 100, 10);
                        rightDisplaySlime = new SlimeAttack(783, 237, 120, 120, 100, 10);
                        leftSlimeDamaged = false;
                        rightSlimeDamaged = false;
                        firstMenu = true;
                        attackMenu = false;
                        battleEndTrigger = false;
                        lootLeft = null;
                        lootRight = null;
                        state = GameState.GAME;
                    }
                }
            }
        }
    }
    
    public void inputAttack() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
                    if (selectionIndex == 0) selectionIndex = 1;
                    else selectionIndex--;
                }

                else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
                    if (selectionIndex == 1) selectionIndex = 0;
                    else selectionIndex++;
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    sfx.charAttack.play();
                    if (selectionIndex == 0 && leftDisplaySlime.isIsAlive()) {
                        characterAttack(leftDisplaySlime);
                        leftSlimeDamaged = true;
                    }
                    else if (selectionIndex == 1 & rightDisplaySlime.isIsAlive()) {
                        characterAttack(rightDisplaySlime);
                        rightSlimeDamaged = true;
                    }
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    selectionIndex = 0;
                    attackMenu = false;
                    firstMenu = true;
                }
                
                else if  (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                    leftDisplaySlime.setIsAlive(false);
                    rightDisplaySlime.setIsAlive(false);
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
                    state = GameState.GAME;
                }

            }   
        }
        
        while (Controllers.next()) {
            if (controller.getAxisValue(0) == -1.0 && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 0) selectionIndex = 1;
                    else selectionIndex--;
                }
                
            else if (controller.getAxisValue(0) == 1.0 && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 1) selectionIndex = 0;
                    else selectionIndex++;
                }
            
            else if (controller.getAxisValue(0) == 0) axisMove = false;
            
            if (Controllers.getEventButtonState()) {
                if (controller.isButtonPressed(0)) {
                    sfx.charAttack.play();
                    if (selectionIndex == 0 && leftDisplaySlime.isIsAlive()) {
                        characterAttack(leftDisplaySlime);
                        leftSlimeDamaged = true;
                    }
                    else if (selectionIndex == 1 & rightDisplaySlime.isIsAlive()) {
                        characterAttack(rightDisplaySlime);
                        rightSlimeDamaged = true;
                    }
                }
                
                else if (controller.isButtonPressed(1)) {
                    selectionIndex = 0;
                    attackMenu = false;
                    firstMenu = true;
                }
                
                else if  (controller.isButtonPressed(6)) {
                    leftDisplaySlime.setIsAlive(false);
                    rightDisplaySlime.setIsAlive(false);
                }
            }
        }
    }
    
    public void animation() {
        if (!first) {
            leftDisplaySlime.draw();
            lastTime = getTime();
        }
        
        else {
            currentTime = getTime();
            if ((currentTime - lastTime) >= delay && (currentTime - lastTime) <= delay*2) {
                
            }
            
            else if ((currentTime - lastTime) >= (delay*3)+1 && (currentTime - lastTime) <= (delay*4)+1) {
                leftDisplaySlime.draw();
            }
            
            else if ((currentTime - lastTime) >= (delay*5)+2 && (currentTime - lastTime) <= (delay*6)+2) {
            }
            
            else if ((currentTime - lastTime) >= (delay*7)+3 && (currentTime - lastTime) <= (delay*8)+3) {
                leftDisplaySlime.draw();
            }
            
            else if ((currentTime - lastTime) >= (delay*9)+4) {
                lastTime = currentTime;
                first = false;
                leftSlimeDamaged = false;
            }
        }
    }
    
    public void characterAttack(SlimeAttack slime) {
        float attackDamage = (float)character.getStrenght() + randFloat(character.getWeapon().getMinDamage(), character.getWeapon().getMaxDamage());
        slime.setHealth(slime.getHealth()-attackDamage);
    }
    
    public Slime getAttackSlime() {
        return attackSlime;
    }

    public void setAttackSlime(Slime attackSlime) {
        this.attackSlime = attackSlime;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
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
