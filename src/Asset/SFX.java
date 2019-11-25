package Asset;

import helpers.Sound;
import java.io.FileNotFoundException;

public class SFX {
    public final Sound introBGM;
    public final Sound battleBGM;
    public final Sound gameBGM;
    public final Sound victoryBGM;
    public final Sound defeatBGM;
    
    public final Sound statusPop;
    public final Sound confirm;
    public final Sound cancel;

    public final Sound charHeal;
    public final Sound charAttack;
    public final Sound slimeAttack;
    public final Sound slimeAttackTwo;

    public SFX() throws FileNotFoundException {
        introBGM = new Sound("BGM", "Skylark", 1.0f, "Intro");
        battleBGM = new Sound("BGM", "Blue_Reflection", 1.0f, "Battle");
        gameBGM = new Sound("BGM", "Game", 1.0f, "Game");
        victoryBGM = new Sound("BGM", "Victory", 1.0f, "Victory");
        defeatBGM = new Sound("BGM", "Defeat", 1.0f, "Defeat");
        
        statusPop = new Sound("SFX", "StatusMenu", 1.0f, "Status Pop");
        confirm = new Sound("SFX", "Decision", 1.0f, "Confirm");
        cancel = new Sound("SFX", "Cancel", 1.0f, "Cancel");
        
        charHeal = new Sound("SFX", "Char_Heal", 1.0f, "Heal");
        charAttack = new Sound("SFX", "Char_Attack", 1.0f, "Attack");
        
        slimeAttack = new Sound("SFX", "Slime_Attack_1", 1.0f, "Slime Attack");
        slimeAttackTwo = new Sound("SFX", "Slime_Attack_2", 1.0f, "Slime Attack Two");
    }
   
}
