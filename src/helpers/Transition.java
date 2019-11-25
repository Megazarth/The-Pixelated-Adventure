/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glRectf;
import static Asset.World.state;

/**
 *
 * @author ysuta
 */
public class Transition {
    
    private static float fade = 0f;
    private static boolean loop = true;
    
    public static void Transition(GameState setState) {
        loop = true;
        while (loop) {            
            if (fade < 90) {
                fade +=1.5f;
            }
            else {
                fade = 0;
                glColor3f(0.5f, 0.5f, 1f);
                glRectf(-0.5f, -0.5f, 0.5f, 0.5f);
                state = setState;
                loop = false;
                }
            System.out.println("Fade before sin = " + fade);
            glColor4f(0.5f, 0.5f, 1f, (float) Math.sin(Math.toRadians(fade)));
            System.out.println("Fade after sin = " + fade);
            glRectf(-0.5f, -0.5f, 0.5f, 0.5f);
        }
        
    }
}
