package Asset.Items.List;

import Asset.Items.Item;
import Asset.Items.Potion;
import static helpers.Artist.LoadTexture;
import static helpers.RNG.randInt;
import java.util.ArrayList;

public class All_Items {
    private final Armors armors;
    private final Gloves gloves;
    private final Helmets helmets;
    private final Shoes shoes;
    private final Weapons weapons;
    private final Potion potion;
    private final ArrayList<Item> allItems;

    public All_Items() {
        armors = new Armors();
        gloves = new Gloves();
        helmets = new Helmets();
        shoes = new Shoes();
        weapons = new Weapons();
        potion = new Potion("A red Potion.", "Heals 15 HP.", LoadTexture("src\\Res\\Equipment\\Potion.png", "PNG"));
        
        allItems = new ArrayList<>();
        
        allItems.addAll(armors.getArmors());
        allItems.addAll(gloves.getGloves());
        allItems.addAll(helmets.getHelmets());
        allItems.addAll(shoes.getShoes());
        allItems.addAll(weapons.getWeapons());
        
        allItems.add(potion);
    }
    
    public Item getRandomItem() {
        int index = randInt(0, allItems.size()-1);
        return allItems.get(index);
    }
      
    public void removeItem(Item item) {
        allItems.remove(item);
    }
}
