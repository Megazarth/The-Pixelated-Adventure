package Asset.Items;

import org.newdawn.slick.opengl.Texture;

public class Armor extends Item{
    
    private float defense;

    public Armor(String name, String desc, float defense, Texture image) {
        super(name, desc, image);
        this.defense = defense;
    }

    public float getDefense() {
        return defense;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }
    
}
