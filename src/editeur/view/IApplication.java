package editeur.view;

public interface IApplication {

    void initFactoryElements();

    void start();

    void end();

    boolean save(String name);

    boolean load(String name, boolean onlyToolbar);
}
