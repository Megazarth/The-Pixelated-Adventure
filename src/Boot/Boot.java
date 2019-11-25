package Boot;

import Asset.Character;
import Asset.SFX;
import static Asset.World.*;
import State.*;
import static helpers.Artist.*;
import static helpers.ControllerSupport.*;
import helpers.GameState;
import java.io.FileNotFoundException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class Boot {

    //private GameState state;
    private Character character;
    private boolean firstRun = true;
    public static SFX sfx;
    
    public Boot() throws FileNotFoundException{
        BeginSession("The Pixelated Adventure", WIDTH, HEIGHT);

        state = GameState.MAIN_MENU;
        MainMenu menu = new MainMenu();
        Game game = new Game();
        StatusScreen status = new StatusScreen();
        Battle battle = new Battle();
        
        sfx = new SFX();
        
        initController();
        
        while (!Display.isCloseRequested()) {
           glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
           controller.poll();
            if (null != state) switch (state) {
                case MAIN_MENU:
                    sfx.gameBGM.stop();
                    sfx.battleBGM.stop();
                    sfx.statusPop.stop();
                    sfx.introBGM.loop();
                    menu.start();
                    break;
                case GAME:
                    if (firstRun) {
                        game.start();
                        character = game.getCharacter();
                    }
                    else {
                        game.setCharacter(character);
                        game.start();
                        character = game.getCharacter();
                    }
                    sfx.gameBGM.loop();
                    sfx.introBGM.stop();
                    sfx.battleBGM.stop();
                    sfx.statusPop.stop();
                    break;
                case STATUS_SCREEN:
                    sfx.gameBGM.stop();
                    sfx.introBGM.stop();
                    sfx.battleBGM.stop();
                    sfx.statusPop.play();
                    status.start(character);
                    break;
                case BATTLE:
                    sfx.gameBGM.stop();
                    battle.setCharacter(character);
                    battle.setAttackSlime(game.attackSlime());
                    battle.start();
                    break;
                default:
                    break;
            }
            
            checkInput();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        AL.destroy();
        System.exit(0);
        
    }
    
    public final void checkInput() {
        //System.out.println("State = " + state);
        while (Keyboard.next()) {
            if(Keyboard.getEventKeyState()) {
                switch(state) {
                    case GAME:
                        if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
                            state = GameState.MAIN_MENU;
                        }
                        if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                            resetAlpha();
                            state = GameState.STATUS_SCREEN;
                        }
                        break;
                    case MAIN_MENU:
                        if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
                            state = GameState.GAME;
                        }
                        break;
                }
            }
        }
    }
}
