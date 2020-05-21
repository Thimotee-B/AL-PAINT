package editeur.view;

import editeur.model.draw.DrawBridge;
import editeur.model.draw.JavaFxDrawBridge;
import editeur.view.GUIFactory.GUIFactory;
import editeur.view.GUIFactory.JavaFxFactory;

/**
 * The type ApplicationConfigurator simply selects bridge and factory with config asked.
 */
public class ApplicationConfigurator {

    /**
     * Select the guifactory implementation.
     *
     * @param config the config
     * @return the gui factory
     */
    public static GUIFactory selectFactory(String config) {
        if (config.compareToIgnoreCase("JavaFx") == 0)
            return new JavaFxFactory();
        else
            System.out.println("You have selected a Factory who doesn't exist");
        
        return null;
    }

    /**
     * Select the draw bridge implementation.
     *
     * @param config the config
     * @return the draw bridge
     */
    public static DrawBridge selectDrawBridgeImpl(String config) {
        if (config.compareToIgnoreCase("JavaFx") == 0)
            return new JavaFxDrawBridge();
        else
            System.out.println("You have selected a Bridge Implementation who doesn't exist");
        
        return null;
    }
    

    
    

}
