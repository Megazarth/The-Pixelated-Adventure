package Entities;

import Entities.AbstractMoveableEntity;
import org.newdawn.slick.opengl.Texture;

public abstract class Entity extends AbstractMoveableEntity{
    
    protected float health;
    protected float strenght;
    protected boolean isAlive;

    public Entity(float x, float y, float width, float height, float health, float strenght) {
        super(x, y, width, height);
        this.health = health;
        this.strenght = strenght;
        isAlive = true;
    }
    
}
