package editeur.view;

import editeur.model.draw.DrawBridge;
import editeur.model.draw.JavaFxDrawBridge;

public class ApplicationConfigurator {
    
    public static GUIFactory selectFactory(String config) {
        if (config.compareToIgnoreCase("JavaFx") == 0)
            return new JavaFxFactory();
        else
            System.out.println("You have selected a Factory who doesn't exist");
        
        return null;
    }
    
    public static DrawBridge selectDrawBridgeImpl(String config) {
        if (config.compareToIgnoreCase("JavaFx") == 0)
            return new JavaFxDrawBridge();
        else
            System.out.println("You have selected a Bridge Implementation who doesn't exist");
        
        return null;
    }
    
    

}
