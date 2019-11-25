package Asset.Items.List;

import Asset.Items.Shoe;
import static helpers.Artist.LoadTexture;
import java.util.ArrayList;

public class Shoes {
    
    private ArrayList<Shoe> shoes;
    private int idx = 1;

    public Shoes() {
        shoes = new ArrayList<>();
        
        shoes.add(new Shoe("Basic Shoe", "Wear it outside!", 1f, LoadTexture("src\\res\\Equipment\\Shoe\\" + (idx++) + ".png", "PNG")
        ));
        shoes.add(new Shoe("Advanced Shoe", "Gives extra protection!", 2f, LoadTexture("src\\res\\Equipment\\Shoe\\" + (idx++) + ".png", "PNG")
        ));
        shoes.add(new Shoe("Steel Shoe", "What knights wear.", 3f, LoadTexture("src\\res\\Equipment\\Shoe\\" + (idx++) + ".png", "PNG")
        ));
        shoes.add(new Shoe("Stormtrooper Gold Shoe", "It's so heavy!", 4f, LoadTexture("src\\res\\Equipment\\Shoe\\" + (idx++) + ".png", "PNG")
        ));
    }

    public ArrayList<Shoe> getShoes() {
        return shoes;
    }

    public void setShoes(ArrayList<Shoe> shoes) {
        this.shoes = shoes;
    }
    
}
