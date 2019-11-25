package Asset.Items;

import org.newdawn.slick.opengl.Texture;

public class Potion extends Item{
    
    private float heal;
    
    public Potion(String name, String desc, Texture image) {
        super(name, desc, image);
        heal = 15f;
    }

    public float getHeal() {
        return heal;
    }

    public void setHeal(float heal) {
        this.heal = heal;
    }
    
}
