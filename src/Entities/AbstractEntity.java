package Entities;

import java.awt.Rectangle;
import org.newdawn.slick.opengl.Texture;

public abstract class AbstractEntity implements DisplayEntity{
    
    protected float x,y, width, height;
    protected Rectangle hitbox = new Rectangle();
    
    protected String filePath;
   
    protected Texture image;    

    public AbstractEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    @Override
    public boolean intersects(DisplayEntity other) {
        hitbox.setBounds((int) x, (int) y, (int) width, (int) height);
        return hitbox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }
    
}
