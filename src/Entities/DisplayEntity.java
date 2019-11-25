package Entities;

public interface DisplayEntity {
    public void draw();
    public void update(float delta);
    public void setLocation(float x, float y);
    public void setX(float x);
    public void setY(float y);
    public void setWidth(float width);
    public void setHeight(float height);
    public float getX();
    public float getY();
    public float getHeight();
    public float getWidth();
    public boolean intersects(DisplayEntity other);
}
