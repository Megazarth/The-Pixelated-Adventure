
package helpers;

import java.util.Random;

public class RNG {
    
    public static int randInt(int min, int max) {

        Random rand =  new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public static float randFloat(float min, float max) {

        Random rand =  new Random();

        float randomNum = rand.nextInt((int) ((max - min) + 1)) + min;

        return randomNum;
    }
}
