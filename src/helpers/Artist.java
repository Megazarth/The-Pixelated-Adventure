package helpers;

import java.io.IOException;
import java.io.InputStream;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Artist {
    
    //public int WIDTH = 1280, HEIGHT = 720;
    
    private static float alpha = 0;

    public static void BeginSession(String title, int WIDTH, int HEIGHT) {
        try {  
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle(title);
            Display.create();
            Display.setFullscreen(true);
            AL.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
   
    public static void DrawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS);
            glVertex2f(x, y);                   //Top Left Corner
            glVertex2f(x + width, y);           // Top Right Corner
            glVertex2f(x + width, y + height);  // Bottom Right Corner
            glVertex2f(x, y +height);           // Bottom Left Corner
        glEnd();
    }
    
    public static void DrawQuadTex(Texture tex, float x, float y, float width, float height) {
        tex.bind();
        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(1, 0);
            glVertex2f(width, 0);
            glTexCoord2f(1, 1);
            glVertex2f(width, height);
            glTexCoord2f(0, 1);
            glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }
    
    public static Texture LoadTexture(String path, String fileType) {
        Texture tex = null;
        InputStream in =  ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tex;
    }
    
    public static Texture QuickLoad(String name) {
        Texture tex = null;
        tex = LoadTexture("src\\res\\terrain\\" + name + ".png", "PNG");
        return tex;
    }
    
    public static void transition(float delta) {
        glColor4f(1.0f, 1.0f, 1.0f, alpha);
        if (alpha != 1.0f) {
            alpha += delta;
        }
    }
    
    public static void resetAlpha() {
        alpha = 0;
        System.out.println("Alpha = " + alpha);
    }
    
}
