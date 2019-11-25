package helpers;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class Text {
    private static TrueTypeFont font;
    private static Font awtFont;
    
    public static void DrawString(float x, float y, String FontType, int textSize) {
        awtFont = new Font(FontType, Font.BOLD, textSize);
        font = new TrueTypeFont(awtFont, false);
    }
    
    public static void DrawString(float textSize) {
        InputStream inputStream = ResourceLoader.getResourceAsStream("src\\Res\\Font\\m5x7.ttf");

        try {
            awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        awtFont = awtFont.deriveFont(textSize); // set font size
        font = new TrueTypeFont(awtFont, false);
    }
    
    public static void printText(float x, float y, String text, Color color) {
        font.drawString(x, y, text, color);
    }
    
}
