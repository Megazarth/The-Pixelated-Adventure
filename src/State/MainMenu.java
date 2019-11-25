package State;

import static Asset.World.*;
import static Boot.Boot.sfx;
import static helpers.Artist.*;
import static helpers.Clock.getTime;
import static helpers.ControllerSupport.*;
import helpers.GameState;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.glColor3f;
import org.newdawn.slick.opengl.Texture;

public class MainMenu implements State{
    
    private Texture pressEnter, newGame, controls, exit;
    
    private Texture first, second, third;
    
    private Texture keyboard;
    
    private Texture[] menu;
    
    private Texture currentState;
    private Texture slimeState;
    private Texture RPGTitle;
    
    private float dx, dy,x ,y;
    
    private float firstX, secondX, thirdX;
    
    private boolean firstRun = false;
    private boolean showKeyboard = false;
    private long currentTime;
    private long lastTime;
    private long delay;
    
    private int selectionIndex = 0;
    private boolean enter = false;
    
    //PRESS ENTER
    private long thisTime;
    private long duration;
    
    public MainMenu() {
        pressEnter = LoadTexture("src\\res\\TitleScreen\\PressEnter.png", "PNG");
        newGame = LoadTexture("src\\res\\TitleScreen\\NewGame.png", "PNG");
        controls = LoadTexture("src\\res\\TitleScreen\\Controls.png", "PNG");
        exit = LoadTexture("src\\res\\TitleScreen\\Exit.png", "PNG");
        
        RPGTitle = LoadTexture("src\\res\\TitleScreen\\RPGTitle.png", "PNG");
        
        keyboard = LoadTexture("src\\res\\TitleScreen\\Keyboard.png", "PNG");
        
        menu = new Texture[3];
        
        menu[0] = newGame;
        menu[1] = controls;
        menu[2] = exit;
        
        first = LoadTexture("src\\res\\TitleScreen\\1.png", "PNG");
        second = LoadTexture("src\\res\\TitleScreen\\2.png", "PNG");
        third = LoadTexture("src\\res\\TitleScreen\\3.png", "PNG");
        
        currentState = LoadTexture("src\\Res\\Character\\Char_Walk_Right_1.png", "PNG");
        
        slimeState = LoadTexture("src\\Res\\Enemies\\Slime_Walk_Right_1.png", "PNG");
        
        firstX = 0;
        secondX = 1280;
        thirdX = 2560;
        
        x = 0;
        y = 0;
        dx = -5;
        dy = 0;
        
        delay = 100;
        
        thisTime = getTime();
        
        resetAlpha();
    }
    
    @Override
    public void start() {
        glColor3f(1.0f, 1.0f, 1.0f); //NEUTRALIZE DISPLAY
        
        transition(0.05f);
        //DrawQuadTex(titleScreen, x, y, 2048, 1024);
        DrawQuadTex(first, firstX, y, 2048, 1024);
        DrawQuadTex(second, secondX, y, 2048, 1024);
        DrawQuadTex(third, thirdX, y, 2048, 1024);
        //DrawString(WIDTH / 2 - 25, HEIGHT / 2, "Times New Roman", "Press Enter to Continue", 20);
        DrawQuadTex(RPGTitle, 0, 0, 2048, 1024);
        animation("Right");
        DrawQuadTex(currentState, 1040, 450, BLOCK_SIZE*2.5f, BLOCK_SIZE*2.5f);
        
        DrawQuadTex(slimeState, 120, 400, BLOCK_SIZE*2.5f, BLOCK_SIZE*2.5f);
        DrawQuadTex(slimeState, 240, 470, BLOCK_SIZE*2.5f, BLOCK_SIZE*2.5f);
        DrawQuadTex(slimeState, 170, 540, BLOCK_SIZE*2.5f, BLOCK_SIZE*2.5f);
        
        loopBackground();
        update(1);
        menu();
        input();
        
    }
    
    public void menu() {
        if (!enter) {
            pressEnter();
        }
        else {
            if (!showKeyboard) DrawQuadTex(menu[selectionIndex], 0, 0, 2048, 1024);
            else if (showKeyboard) DrawQuadTex(keyboard, 0, 0, 2048, 1024);
        }
    }
    
    
    public void pressEnter() {
        duration = getTime();
        
        if((duration - thisTime) <= 500){
            DrawQuadTex(pressEnter, 0, 0, 2048, 1024);
            
        }
        
        else if (duration - thisTime >= 1000) {
            thisTime = duration;
        }
    }
    
    public void loopBackground() {
        if (firstX <= -1280) {
            firstX = 2560;
        }
        
        else if (secondX <= -1280) {
            secondX = 2560;
        }
        
        else if (thirdX <= -1280) {
            thirdX = 2560;
        }
    }
    
    public void animation(String direction) {
        if (firstRun) {
            currentState = LoadTexture("src\\Res\\Character\\Char_Walk_" + direction + "_2.png", "PNG");
            slimeState = LoadTexture("src\\Res\\Enemies\\Slime_Walk_" + direction + "_2.png", "PNG");
            lastTime = getTime();
            firstRun = false;
        }
        
        else {
            currentTime = getTime();
            if ((currentTime - lastTime) >= delay && (currentTime - lastTime) <= delay*3) {
                currentState = LoadTexture("src\\Res\\Character\\Char_Walk_" + direction + "_1.png", "PNG");
                slimeState = LoadTexture("src\\Res\\Enemies\\Slime_Walk_" + direction + "_1.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*3)+1 && (currentTime - lastTime) <= (delay*5)+1) {
                currentState = LoadTexture("src\\Res\\Character\\Char_Walk_" + direction + "_2.png", "PNG");
                slimeState = LoadTexture("src\\Res\\Enemies\\Slime_Walk_" + direction + "_2.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*5)+2) {
                lastTime = currentTime;
            }
        }
    }
    
    @Override
    public void keyboardInput() {
        while(Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    if (!enter) enter = true;
                    else {
                        switch (selectionIndex) {
                            case 0:
                                state = GameState.GAME;
                                break;
                            case 1:
                                showKeyboard = !showKeyboard;
                                break;
                            case 2:
                                AL.destroy();
                                Display.destroy();
                                System.exit(1);
                                break;
                            default:
                                break;
                        }
                    }
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    if (enter && !showKeyboard) enter = false;
                    else if (enter && showKeyboard) {
                        showKeyboard = false;
                    }
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_UP)  && enter) {
                    if (selectionIndex == 0) selectionIndex = 2;
                    else selectionIndex--;
                }
                
                else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && enter) {
                    if (selectionIndex == 2) selectionIndex = 0;
                    else selectionIndex++;
                }
            }
        }        
    }
    
    @Override
    public void controllerInput() {
        while (Controllers.next()) {
            if (controller.getAxisValue(0) == -1.0 && enter && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 0) selectionIndex = 2;
                    else selectionIndex--;
                }
                
            else if (controller.getAxisValue(0) == 1.0 && enter && !axisMove) {
                    axisMove = true;
                    if (selectionIndex == 2) selectionIndex = 0;
                    else selectionIndex++;
                }
            
            else if (controller.getAxisValue(0) == 0) axisMove = false;
            
            if (Controllers.getEventButtonState()) {
                if (controller.isButtonPressed(7) || controller.isButtonPressed(0)) {
                    sfx.confirm.play();
                    sfx.confirm.setIsPlaying(false);
                    if (!enter) enter = true;
                    else {
                        switch (selectionIndex) {
                            case 0:
                                state = GameState.GAME;
                                break;
                            case 1:
                                showKeyboard = !showKeyboard;
                                break;
                            case 2:
                                AL.destroy();
                                Display.destroy();
                                System.exit(1);
                                break;
                            default:
                                break;
                        }
                    }
                }
                else if (controller.isButtonPressed(1)) {
                    sfx.cancel.play();
                    sfx.cancel.setIsPlaying(false);
                    if (enter && !showKeyboard) enter = false;
                    else if (enter && showKeyboard) {
                        showKeyboard = false;
                    }
                }
            }
        }        
    }
    
    @Override
    public void input() {
        keyboardInput();
        if (controller.getName().equalsIgnoreCase("Controller (Xbox One For Windows)")) {
            controllerInput();
        }

    }
    
    public void update(float delta) {
        this.firstX += delta * dx;
        this.secondX += delta * dx;
        this.thirdX += delta * dx;
        this.y += delta * dy;
    }
    
    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
    }
    
}
