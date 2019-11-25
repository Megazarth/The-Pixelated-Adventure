package Entities;

import static helpers.Artist.LoadTexture;
import static helpers.Clock.getTime;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author ysuta
 */
public abstract class AbstractMoveableEntity extends AbstractEntity implements MoveableEntity{

    protected float dx, dy;

    protected Texture lookUp;
    protected Texture lookDown;
    protected Texture lookLeft;
    protected Texture lookRight;
    protected Texture lastState;
    
    protected boolean first = false;
    protected long currentTime;
    protected long lastTime;
    protected long delay;
    
    protected String filePath;
   
    protected Texture currentState;    
    
    /*
    public AbstractMoveableEntity(float x, float y, float width, float height, float health) {
        super(x, y, width, height, health);
        this.dx = 0;
        this.dy = 0;
    }*/
    
    /*
    public AbstractMoveableEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
        this.lastTime = getTime();
    */

    public AbstractMoveableEntity(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
        this.lastTime = getTime();
    }
    
    @Override
    public void update(float delta) {
        this.x += delta * dx;
        this.y += delta * dy;
    }
    
    @Override
    public float getDX(){ //VELOCITY X
        return dx;
    }
    @Override
    public float getDY(){ //VELOCITY Y
        return dy;
    }
    @Override
    public void setDX(float dx) {
        this.dx = dx;
    }
    @Override
    public void setDY(float dy) {
        this.dy = dy;
    }
    
    public void animation(String direction) {
        if (first) {
            currentState = LoadTexture(filePath + direction + "_2.png", "PNG");
            lastTime = getTime();
            first = false;
        }
        
        else {
            currentTime = getTime();
            if ((currentTime - lastTime) >= delay && (currentTime - lastTime) <= delay*3) {
                currentState = LoadTexture(filePath + direction + "_1.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*3)+1 && (currentTime - lastTime) <= (delay*5)+1) {
                currentState = LoadTexture(filePath + direction + "_2.png", "PNG");
            }
            
            else if ((currentTime - lastTime) >= (delay*5)+2) {
                lastTime = currentTime;
            }
        }
    }
    
    protected void moveUp() {
        setDX(0);
        setDY(-2);
    }
    
    protected void moveDown() {
        setDX(0);
        setDY(2);
    }
    
    protected void moveLeft() {
        setDX(-2);
        setDY(0);
    }
    
    protected void moveRight() {
        setDX(2);
        setDY(0);
    }
    
    protected void moveUpRight() {
        setDX(2);
        setDY(-2);
    }
    
    protected void moveUpLeft() {
        setDX(-2);
        setDY(-2);
    }
    
    protected void moveDownRight() {
        setDX(2);
        setDY(2);
    }
    
    protected void moveDownLeft() {
        setDX(-2);
        setDY(2);
    }
     
}
