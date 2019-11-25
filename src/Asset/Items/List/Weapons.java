package Asset.Items.List;

import Asset.Items.Weapon;
import static helpers.Artist.LoadTexture;
import java.util.ArrayList;

public class Weapons {
    
    private ArrayList<Weapon> weapons;
    private int idx = 1;
    
    public Weapons() {
        
        weapons = new ArrayList<>();
        
        weapons.add(new Weapon
        ("Hellblade", "Blade from the Demon himself.", 1, 5, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
        weapons.add(new Weapon
        ("Frostbane", "Blade imbued with Cold Magic.", 5, 10, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
        weapons.add(new Weapon
        ("Javelin", "Winged Spear.", 4, 7, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
        weapons.add(new Weapon
        ("Horsestab", "Used for horse competition.", 9, 12, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
        weapons.add(new Weapon
        ("Axe", "For chopping trees.", 5, 20, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
        weapons.add(new Weapon
        ("Winged Axe", "The wing allows for fast attacks.", 10, 30, LoadTexture("src\\res\\Equipment\\Weapon\\" + (idx++) + ".png", "PNG")
        ));
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }
            
}
