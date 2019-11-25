package Asset;

import static Asset.World.*;
import Entities.AbstractMoveableEntity;
import static helpers.Artist.*;
import static helpers.Clock.getTime;
import static helpers.RNG.*;
import java.awt.Rectangle;
import Entities.Entity;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import Entities.DisplayEntity;

public class Slime extends Entity{
    
    private Rectangle sight;
    private float xRad, yRad, widthRad, heightRad;
    private boolean isWalking = false;
    private int direction;
    private float duration;
    private float moveTime;
    //Texture radar = LoadTexture("src\\Res\\Radius.png", "PNG");

    public Slime(float x, float y, float width, float height, float health, float strenght) {
        super(x, y, width, height, health, strenght);
        
        super.currentState = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Down.png", "PNG");
        super.lastState   = currentState;
        super.lookUp      = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Up.png", "PNG");
        super.lookDown    = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Down.png", "PNG");
        super.lookLeft    = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Left.png", "PNG");
        super.lookRight   = LoadTexture("src\\Res\\Enemies\\Slime_Idle_Right.png", "PNG");
        
        super.filePath = "src\\Res\\Enemies\\Slime_Walk_";
        
        //System.out.println("X = " + super.x);
        //System.out.println("Y = " + super.y);
       
        sight = new Rectangle();
        
        super.lastTime = getTime();
        super.delay = 100;
    }

    @Override
    public void draw() {
        currentState.bind();
        glLoadIdentity();
        glTranslatef((float) x,(float) y, 0);
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(1, 0);
            glVertex2f(width, 0);
            glTexCoord2f(1, 1);
            glVertex2f(width, height);
            glTexCoord2f(0, 1);
            glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }
    
    @Override
    public void animation(String direction) {
        if (first) {
            currentState = LoadTexture(super.filePath + direction + "_2.png", "PNG");
            lastTime = getTime();
            first = false;
        }
        
        else {
            currentTime = getTime();
            if ((currentTime - lastTime) >= delay && (currentTime - lastTime) <= delay*3) {
                currentState = LoadTexture(super.filePath + direction + "_1.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*3)+1 && (currentTime - lastTime) <= (delay*5)+1) {
                currentState = LoadTexture(super.filePath + direction + "_2.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*5)+2) {
                lastTime = currentTime;
            }
        }
    }
    
    public boolean inSight(DisplayEntity player) {
        
        xRad = super.x - (BLOCK_SIZE * 2);
        yRad = super.y - (BLOCK_SIZE * 2);
        widthRad =  (float) (BLOCK_SIZE * 5);
        heightRad = (float) (BLOCK_SIZE * 5);
        
        sight.setBounds((int) xRad, (int) yRad, (int) widthRad, (int) heightRad);
        return sight.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight());
    }
    
    
    public void animate(DisplayEntity player) {
        if (inSight(player) && !(intersects(player))) {
            //System.out.println("The Slime saw You!");
            pursue(player);
        }
        
        else if (!(intersects(player))) {
            walking();
        }
        
        else {
            setDX(0);
            setDY(0);
        }
    }
    
    public void walking() {
        if (!isWalking) {
            direction = randInt(1, 4);
            duration = randInt(6, 8);
            isWalking = true;
            moveTime = getTime()/1000;
            duration += moveTime;
            //sSystem.out.println("Direction = " + direction);
        }
        
        else {
            if(moveTime <= duration) {
                switch(direction) {
                    case 1: {
                        if (getY() > 1) {
                            animation("Up");
                            moveUp();
                        }
                        else isWalking = false;
                    }
                        break;
                    case 2:{
                        if (getY() < HEIGHT-BLOCK_SIZE) {
                            animation("Down");
                            moveDown();
                        }
                        else isWalking = false;
                    }
                        break;
                    case 3:{
                        if (getX() < WIDTH-BLOCK_SIZE) {
                            animation("Right");
                            moveRight();
                        }
                        else isWalking = false;
                    }
                        break;
                    case 4:{
                        if (getX() > 1) {
                            animation("Left");
                            moveLeft();
                        }
                        else isWalking = false;
                    }
                        break;
                }
                moveTime = getTime()/1000;
                
            }
            else isWalking = false;
        }
    }
    
    public void pursue(DisplayEntity player) {
        if (super.getX() < player.getX() && super.getY() > player.getY()) {
            //System.out.println("TOP-RIGHT");
            animation("Up");
            lastState = lookUp;
            moveUpRight();
        }
        
        else if (super.getX() > player.getX() && super.getY() > player.getY()){
            //System.out.println("TOP-LEFT");
            animation("Up");
            lastState = lookUp;
            moveUpLeft();
        }
        
        else if (super.getX() > player.getX() && super.getY() < player.getY()){
            //System.out.println("BOTTOM-LEFT");
            animation("Down");
            lastState = lookDown;
            moveDownLeft();
        }
        
        else if (super.getX() < player.getX() && super.getY() < player.getY()){
            //System.out.println("BOTTOM-RIGHT");
            animation("Down");
            lastState = lookDown;
            moveDownRight();
        }
        
         else if (super.getX() == player.getX() && super.getY() > player.getY()){
            //System.out.println("Up");
            animation("Up");
            lastState = lookUp;
            moveUp();
        }
        
        else if (super.getX() == player.getX() && super.getY() < player.getY()){
            //System.out.println("Down");
            animation("Down");
            lastState = lookDown;
            moveDown();
        }
        
        else if (super.getX() < player.getX() && super.getY() == player.getY()){
            //System.out.println("Right");
            animation("Right");
            lastState = lookRight;
            moveRight();
        }
        
        else if (super.getX() > player.getX() && super.getY() == player.getY()){
            //System.out.println("Left");
            animation("Left");
            lastState = lookLeft;
            moveLeft();
        }
            
    }
    
    public boolean isAlive() {
        return super.isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    
}
