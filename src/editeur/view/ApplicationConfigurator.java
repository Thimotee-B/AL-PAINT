package editeur.view;

public class ApplicationConfigurator {
    
    public static GUIFactory selectFactory(String config) {
        if (config.compareToIgnoreCase("JavaFx") == 0)
            return new JavaFxFactory();
        else
            System.out.println("You have selected a Factory who doesn't exist");
        
        return null;
    }
}
