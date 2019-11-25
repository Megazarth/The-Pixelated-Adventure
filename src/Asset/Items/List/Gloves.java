package Asset.Items.List;

import Asset.Items.Glove;
import static helpers.Artist.LoadTexture;
import java.util.ArrayList;

public class Gloves {
    
    private ArrayList<Glove> gloves;
    private int idx = 1;

    public Gloves() {
        gloves = new ArrayList<>();
        
        gloves.add(new Glove("Basic Glove", "Wear it outside!", 1f, LoadTexture("src\\res\\Equipment\\Glove\\" + (idx++) + ".png", "PNG")
        ));
        gloves.add(new Glove("Complete Glove", "Covers the fingertips", 3f, LoadTexture("src\\res\\Equipment\\Glove\\" + (idx++) + ".png", "PNG")
        ));
        gloves.add(new Glove("Advanced Glove", "Won't feel a thing!!", 5f, LoadTexture("src\\res\\Equipment\\Glove\\" + (idx++) + ".png", "PNG")
        ));
        gloves.add(new Glove("Super Glove", "The best there is!!", 7f, LoadTexture("src\\res\\Equipment\\Glove\\" + (idx++) + ".png", "PNG")
        ));
    }

    public ArrayList<Glove> getGloves() {
        return gloves;
    }

    public void setGloves(ArrayList<Glove> gloves) {
        this.gloves = gloves;
    }
    
}
