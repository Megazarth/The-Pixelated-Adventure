package helpers;

import org.lwjgl.Sys;

public class Clock {
    
    private static boolean paused = false;
    private static long lastFrame, totalTime;
    private static float d = 0, multiplier = 1;
    
    public static long getTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution(); //MILLISECOND
    }
    
    public static float getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }
    
    public static float Delta() {
        if (paused) {
            return 0;
        }
        else {
            return d * multiplier;
        }
    }
    
    public static float TotalTime() {
        return totalTime;
    }
    
    public static float Multiplier() {
        return multiplier;
    }
    
    public static void update() {
        d = getDelta();
        //System.out.println("D = " + d);        
        totalTime += d;
        //System.out.println("Total time = " + totalTime);
    }
    
    public static void ChangeMultiplier(int change) {
        if (multiplier + change < -1 && multiplier + change > 7) {
            
        }
        else {
           multiplier += change;
        }
    }
    
    public static void Pause() {
        if (paused) {
            paused = false;
        }
        else {
            paused = false;
        }
    }
}
