package Asset.Items.List;

import Asset.Items.Armor;
import static helpers.Artist.LoadTexture;
import java.util.ArrayList;

public class Armors {
    
    private ArrayList<Armor> armors;
    private int idx = 1;

    public Armors() {
        armors = new ArrayList<>();
        
        armors.add(new Armor("Basic Armor", "Your starting armor, weak but useful.", 1f, LoadTexture("src\\res\\Equipment\\Armor\\" + (idx++) + ".png", "PNG")
        ));
        armors.add(new Armor("Advanced Armor", "Made of Titanium, durable.", 5f, LoadTexture("src\\res\\Equipment\\Armor\\" + (idx++) + ".png", "PNG")
        ));
        armors.add(new Armor("Winged Advanced Armor", "With neck-defend add-on.", 10f, LoadTexture("src\\res\\Equipment\\Armor\\" + (idx++) + ".png", "PNG")
        ));
        armors.add(new Armor("Gold Armor", "Armor made of pure gold. Powerful.", 20f, LoadTexture("src\\res\\Equipment\\Armor\\" + (idx++) + ".png", "PNG")
        ));   
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }

    public void setArmors(ArrayList<Armor> armors) {
        this.armors = armors;
    }
    
}
