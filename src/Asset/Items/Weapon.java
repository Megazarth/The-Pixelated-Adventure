package Asset.Items;

import static helpers.Artist.LoadTexture;
import org.newdawn.slick.opengl.Texture;

public class Weapon extends Item{
    
    private float minDamage, maxDamage;
   
    public Weapon(String name, String desc,float minDamage, float maxDamage, Texture image) {
        super(name, desc, image);
        this. minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
    
    public float getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(float minDamage) {
        this.minDamage = minDamage;
    }

    public float getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(float maxDamage) {
        this.maxDamage = maxDamage;
    }
    
    
    
}
