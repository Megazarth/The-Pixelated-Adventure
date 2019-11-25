package Asset;

import static Boot.Boot.sfx;
import Entities.Entity;
import static helpers.Artist.LoadTexture;
import static helpers.Clock.getTime;
import helpers.RNG;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;

public class SlimeAttack extends Entity{
    
    private Texture phase1;
    private Texture phase2;
    private Texture death;
    
    private boolean engaged = false;

    public SlimeAttack(float x, float y, float width, float height, float health, float strenght) {
        super(x, y, width, height, health, strenght);
        
        super.currentState = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Down.png", "PNG");
        this.phase1 = LoadTexture("src\\Res\\Enemies\\Slime_Attack_1.png", "PNG");
        this.phase2 = LoadTexture("src\\Res\\Enemies\\Slime_Attack_2.png", "PNG");
        this.death  = LoadTexture("src\\Res\\Enemies\\Slime_Death.png", "PNG");

        super.lastTime = getTime();
        super.delay = 300;
    }
   

    @Override
    public void draw() {
        currentState.bind();
        glLoadIdentity();
        glTranslatef((float) x,(float) y, 0);
        glBegin(GL_QUADS);
        if (currentState == phase2) {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(1, 0);
            glVertex2f(width, 0);
            glTexCoord2f(1, 1);
            glVertex2f(width, height*2);
            glTexCoord2f(0, 1);
            glVertex2f(0, height*2);
        }
        else {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(1, 0);
            glVertex2f(width, 0);
            glTexCoord2f(1, 1);
            glVertex2f(width, height);
            glTexCoord2f(0, 1);
            glVertex2f(0, height);
        }
        glEnd();
        glLoadIdentity();
    }
    
    public void animation(Character character) {
        if (!engaged) {
            currentState = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Down.png", "PNG");
            lastTime = getTime();
        }
        
        else {
            currentTime = getTime();
            if ((currentTime - lastTime) >= delay && (currentTime - lastTime) <= delay*3) {
                currentState = phase1;
            }
            
            else if ((currentTime - lastTime) >= (delay*3)+1 && (currentTime - lastTime) <= (delay*5)+1) {
                currentState = phase2;
            }
            
            else if ((currentTime - lastTime) >= (delay*5)+2) {
                lastTime = currentTime;
                character.setHealth(character.getHealth() - (super.strenght - character.getDefend()));
                
                if ((RNG.randInt(1, 10) % 2) == 0) {
                    sfx.slimeAttack.play();
                    sfx.slimeAttack.setIsPlaying(false);
                }
                else {
                    sfx.slimeAttackTwo.play();
                    sfx.slimeAttackTwo.setIsPlaying(false);
                }
                engaged = false;
            }
        }
    }

    public Texture getDeath() {
        return death;
    }

    public void setDeath(Texture death) {
        this.death = death;
    }

    public boolean isEngaged() {
        return engaged;
    }

    public void setEngaged(boolean engaged) {
        this.engaged = engaged;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        if (this.health < 0) {
            health = 0;
            setIsAlive(false);
        }
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Texture getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Texture currentState) {
        this.currentState = currentState;
    }
      
}
