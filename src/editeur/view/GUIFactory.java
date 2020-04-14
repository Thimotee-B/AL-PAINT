package editeur.view;

public interface GUIFactory {
    
    GenericWhiteBoard createWhiteBoard();
    GenericToolBar    createToolBar();
    GenericTopBar     createTopBar();
    GenericButton     createButton(String name);
    
}
