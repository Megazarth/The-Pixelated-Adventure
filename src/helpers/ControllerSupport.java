package helpers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class ControllerSupport {
    
    public static Controller controller;
    
    public static boolean axisMove = false;
    
    public static void initController() {
        try {
            Controllers.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
        
        Controllers.poll();
        
        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            controller = Controllers.getController(i);
            //System.out.println(i + ") " + controller.getName());
            if (controller.getName().equalsIgnoreCase("Controller (Xbox One For Windows)")) {
                controller = Controllers.getController(i);
                break;
            }
        }
        System.out.println(controller.getName());
        //System.out.println("");
        //controller = Controllers.getController(5);
        /*
        System.out.println(controller.getName());
        for (int i = 0; i < controller.getButtonCount(); i++) {
            System.out.println(i + " ) " + controller.getButtonName(i));
        }*/
    }
}
