package Asset.Items.List;

import Asset.Items.Helmet;
import static helpers.Artist.LoadTexture;
import java.util.ArrayList;


public class Helmets {
    
    private ArrayList<Helmet> helmets;
    private int idx = 1;

    public Helmets() {
        
        helmets = new ArrayList<>();
        
        helmets.add(new Helmet("Basic Helmet", "Your starting helmet, weak but useful.", 1f, LoadTexture("src\\res\\Equipment\\Helmet\\" + (idx++) + ".png", "PNG")
        ));
        helmets.add(new Helmet("Horned Helmet", "Helmet with horns, not for ramming.", 3f, LoadTexture("src\\res\\Equipment\\Helmet\\" + (idx++) + ".png", "PNG")
        ));
        helmets.add(new Helmet("Lined Helmet", "Helmet with Gold Lines.", 5f, LoadTexture("src\\res\\Equipment\\Helmet\\" + (idx++) + ".png", "PNG")
        ));
        helmets.add(new Helmet("Stormtrooper Gold Helmet", "Don't try to shoot with this one.", 7f, LoadTexture("src\\res\\Equipment\\Helmet\\" + (idx++) + ".png", "PNG")
        ));
    }

    public ArrayList<Helmet> getHelmets() {
        return helmets;
    }

    public void setHelmets(ArrayList<Helmet> helmets) {
        this.helmets = helmets;
    }
    
}
