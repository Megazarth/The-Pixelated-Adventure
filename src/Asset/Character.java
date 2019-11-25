package Asset;

import Asset.Items.*;
import Entities.AbstractMoveableEntity;
import Entities.Entity;
import static helpers.Artist.*;
import static helpers.Clock.getTime;
import static helpers.ControllerSupport.axisMove;
import static helpers.ControllerSupport.controller;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class Character extends Entity{
    
    private float defend;
    private int potion;
    
    private Weapon weapon;
    private Helmet helmet;
    private Armor armor;
    private Glove glove;
    private Shoe shoe;
    
    private Potion potionObject;
    
    private ArrayList<Item> inventory;

    public Character(float x, float y, float width, float height, float health, float strenght, float defend) {
        super(x, y, width, height, health, strenght);
        
        super.currentState = LoadTexture("src\\Res\\Character\\Char_Idle_Down.png", "PNG");
        super.lastState   = currentState;
        super.lookUp      = LoadTexture("src\\Res\\Character\\Char_Idle_Up.png", "PNG");
        super.lookDown    = LoadTexture("src\\Res\\Character\\Char_Idle_Down.png", "PNG");
        super.lookLeft    = LoadTexture("src\\Res\\Character\\Char_Idle_Left.png", "PNG");
        super.lookRight   = LoadTexture("src\\Res\\Character\\Char_Idle_Right.png", "PNG");
        
        super.filePath = "src\\Res\\Character\\Char_Walk_";
        
        super.lastTime = getTime();
        super.delay = 100;
        
        this.defend = defend;
        this.potion = 5;
        
        this.weapon = new Weapon ("Hellblade", "Blade from the Demon himself.", 1, 5, LoadTexture("src\\res\\Equipment\\Weapon\\1.png", "PNG"));
        this.helmet = new Helmet("Basic Helmet", "Your starting helmet, weak but useful.", 1f, LoadTexture("src\\res\\Equipment\\Helmet\\1.png", "PNG"));
        this.armor  = new Armor("Basic Armor", "Your starting armor, weak but useful.", 1f, LoadTexture("src\\res\\Equipment\\Armor\\1.png", "PNG"));
        this.glove  = new Glove("Basic Glove", "Wear it outside!", 1f, LoadTexture("src\\res\\Equipment\\Glove\\1.png", "PNG"));
        this.shoe   = new Shoe("Basic Shoe", "Wear it outside!", 1f, LoadTexture("src\\res\\Equipment\\Shoe\\1.png", "PNG"));    
        this.potionObject = new Potion("A red Potion.", "Heals 15 HP.", LoadTexture("src\\Res\\Equipment\\Potion.png", "PNG"));
        
        inventory = new ArrayList<>();
        
        demoInventory();
    }
    
    public void demoInventory() {
        inventory.add (new Weapon
        ("Frostbane", "Blade imbued with Cold Magic.", 5f, 10f, LoadTexture("src\\res\\Equipment\\Weapon\\2.png", "PNG")
        ));
        inventory.add(new Helmet("Horned Helmet", "Helmet with horns, not for ramming.", 3f, LoadTexture("src\\res\\Equipment\\Helmet\\2.png", "PNG")
        ));
        inventory.add(new Armor("Advanced Armor", "Made of Titanium, durable.", 5f, LoadTexture("src\\res\\Equipment\\Armor\\2.png", "PNG")
        ));
        inventory.add(new Glove("Complete Glove", "Covers the fingertips", 3f, LoadTexture("src\\res\\Equipment\\Glove\\2.png", "PNG")
        ));
        inventory.add(new Shoe("Advanced Shoe", "Gives extra protection!", 2f, LoadTexture("src\\res\\Equipment\\Shoe\\2.png", "PNG")
        ));
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
    
    public void controllerInput(int WIDTH, int HEIGHT) {
        
        if (controller.getAxisValue(1) > 0 && controller.getAxisValue(1) < 1.0 && getX() <= WIDTH-getWidth() && controller.getAxisValue(0) < 0 && controller.getAxisValue(0) > -1.0 && getY() >= 0) {  // UP-RIGHT
            animation("Up");
            lastState = lookUp;
            setDX(2);
            setDY(-2);
        }
        
        else if (controller.getAxisValue(1) > 0 && controller.getAxisValue(1) < 1.0 && getX() <= WIDTH-getWidth() && controller.getAxisValue(0) > 0 && controller.getAxisValue(0) < 1.0 && getY() <= HEIGHT-getHeight()) { // DOWN-RIGHT
            animation("Down");
            lastState = lookDown;
            setDX(2);
            setDY(2);
        }
        
        else if (controller.getAxisValue(1) < 0 && controller.getAxisValue(1) > -1.0 && getX() >= 0 && controller.getAxisValue(0) < 0 && controller.getAxisValue(0) > -1.0 && getY() >= 0) { // UP-LEFT
            animation("Up");
            lastState = lookUp;
            setDX(-2);
            setDY(-2);
        }
        
        else if (controller.getAxisValue(1) < 0 && controller.getAxisValue(1) > - 1.0 && getX() >= 0 && controller.getAxisValue(0) > 0 && controller.getAxisValue(0) < 1.0 && getY() <= HEIGHT-getHeight()) { // DOWN-LEFT
            animation("Down");
            lastState = lookDown;
            setDX(-2);
            setDY(2);
        }
        
        else if (controller.getAxisValue(1) == 1.0 && getX() <= WIDTH-getWidth()) {
            animation("Right");
            lastState = lookRight;
            setDX(2);
            setDY(0);
        }
        
        else if (controller.getAxisValue(1) == -1.0 && getX() >= 0) {
            animation("Left");
            lastState = lookLeft;
            setDX(-2);
            setDY(0);
        }
        
        else if (controller.getAxisValue(0) == -1.0 && getY() >= 0) {
            animation("Up");
            lastState = lookUp;
            setDX(0);
            setDY(-2);
        }
        
        else if (controller.getAxisValue(0) == 1.0 && getY() <= HEIGHT-getHeight()) {
            animation("Down");
            lastState = lookDown;
            setDX(0);
            setDY(2);
        }
        
        else {
            first = true;
            currentState = lastState;
            setDX(0);
            setDY(0);
        }
        
    }
    
    public void keyboardInput(int WIDTH, int HEIGHT) {
        
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth() 
                && Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0) {  // UP-RIGHT
            animation("Up");
            lastState = lookUp;
            setDX(2);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth() 
                && Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight()) { // DOWN-RIGHT
            animation("Down");
            lastState = lookDown;
            setDX(2);
            setDY(2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0 
                && Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0) { // UP-LEFT
            animation("Up");
            lastState = lookUp;
            setDX(-2);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0
                && Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight()) { // DOWN-LEFT
            animation("Down");
            lastState = lookDown;
            setDX(-2);
            setDY(2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth()) {
            animation("Right");
            lastState = lookRight;
            setDX(2);
            setDY(0);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0) {
            animation("Left");
            lastState = lookLeft;
            setDX(-2);
            setDY(0);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0) {
            animation("Up");
            lastState = lookUp;
            setDX(0);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight()) {
            animation("Down");
            lastState = lookDown;
            setDX(0);
            setDY(2);
        }
        
        else {
            first = true;
            currentState = lastState;
            setDX(0);
            setDY(0);
        }
        
    }
    
    public void dualInput(int WIDTH, int HEIGHT) {
        
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth() 
                && Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0 || 
                controller.getAxisValue(1) > 0 && controller.getAxisValue(1) < 1.0 && 
                getX() <= WIDTH-getWidth() && controller.getAxisValue(0) < 0 && 
                controller.getAxisValue(0) > -1.0 && getY() >= 0) {  // UP-RIGHT
            animation("Up");
            lastState = lookUp;
            setDX(2);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth() 
                && Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight() ||
                controller.getAxisValue(1) > 0 && controller.getAxisValue(1) < 1.0 && 
                getX() <= WIDTH-getWidth() && controller.getAxisValue(0) > 0 && 
                controller.getAxisValue(0) < 1.0 && getY() <= HEIGHT-getHeight()) { // DOWN-RIGHT
            animation("Down");
            lastState = lookDown;
            setDX(2);
            setDY(2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0 
                && Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0 ||
                controller.getAxisValue(1) < 0 && controller.getAxisValue(1) > -1.0 && 
                getX() >= 0 && controller.getAxisValue(0) < 0 && 
                controller.getAxisValue(0) > -1.0 && getY() >= 0) { // UP-LEFT
            animation("Up");
            lastState = lookUp;
            setDX(-2);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0
                && Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight() ||
                controller.getAxisValue(1) < 0 && controller.getAxisValue(1) > - 1.0 && 
                getX() >= 0 && controller.getAxisValue(0) > 0 && controller.getAxisValue(0) < 1.0 
                && getY() <= HEIGHT-getHeight()) { // DOWN-LEFT
            animation("Down");
            lastState = lookDown;
            setDX(-2);
            setDY(2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && getX() <= WIDTH-getWidth() ||
                controller.getAxisValue(1) == 1.0 && getX() <= WIDTH-getWidth()) {
            animation("Right");
            lastState = lookRight;
            setDX(2);
            setDY(0);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && getX() >= 0 ||
                controller.getAxisValue(1) == -1.0 && getX() >= 0) {
            animation("Left");
            lastState = lookLeft;
            setDX(-2);
            setDY(0);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_UP) && getY() >= 0 ||
                controller.getAxisValue(0) == -1.0 && getY() >= 0) {
            animation("Up");
            lastState = lookUp;
            setDX(0);
            setDY(-2);
        }
        
        else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && getY() <= HEIGHT-getHeight() ||
                controller.getAxisValue(0) == 1.0 && getY() <= HEIGHT-getHeight()) {
            animation("Down");
            lastState = lookDown;
            setDX(0);
            setDY(2);
        }
        
        else {
            first = true;
            currentState = lastState;
            setDX(0);
            setDY(0);
        }
                
    }
    
    public void animate(int WIDTH, int HEIGHT) {
        /*
        if (controller.getName().equalsIgnoreCase("Controller (Xbox One For Windows)")) {
            controllerInput(WIDTH, HEIGHT);
        }
        keyboardInput(WIDTH, HEIGHT);*/
        
        
        if (controller.getName().equalsIgnoreCase("Controller (Xbox One For Windows)")) {
            dualInput(WIDTH, HEIGHT);
        }
        else keyboardInput(WIDTH, HEIGHT);
        
    }
    
    public void heal() {
        setHealth(getHealth() + potionObject.getHeal());
        potion--;
        if (getHealth() >= 100) {
            setHealth(100f);
        }
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
        if (this.health < 0) {
            setHealth(0);
        }
    }

    public float getStrenght() {
        return super.strenght;
    }

    public void setStrenght(float strenght) {
        this.strenght = strenght;
    }
    
    public float getDefend() {
        return defend;
    }

    public void setDefend(float defend) {
        this.defend = defend;
    }

    public int getPotion() {
        return potion;
    }

    public void setPotion(int potion) {
        this.potion = potion;
    }
    
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Helmet getHelmet() {
        return helmet;
    }

    public void setHelmet(Helmet helmet) {
        this.helmet = helmet;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Glove getGlove() {
        return glove;
    }

    public void setGlove(Glove glove) {
        this.glove = glove;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public void setCurrentState(Texture currentState) {
        this.currentState = currentState;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    
    public void addInventory(Item item) {
        this.inventory.add(item);
    }
}
