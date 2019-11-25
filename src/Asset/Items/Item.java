package Asset.Items;

import org.newdawn.slick.opengl.Texture;

public abstract class Item{
    
    protected String name;
    protected String desc;
    protected Texture image;

    public Item(String name, String desc, Texture image) {
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }
    
}
