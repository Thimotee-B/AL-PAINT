package tests;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.view.ApplicationFx;
import editeur.view.IApplication;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MediatorAddTest {
    IApplication app;
    @BeforeClass
    public static void initToolkit()
            throws InterruptedException
    {

        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        // That's a pretty reasonable delay... Right?
        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }

    @Test
    public void mediatorAdd() {
        app = new ApplicationFx();
        IShape shape = new Rectangle(10,10,20,20);
        IShape clone = shape.clone();
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),clone);

        int size = app.getWhiteBoard().getComposite().getComponents().size();
        assertEquals("Size test", 2, size);
    }
}