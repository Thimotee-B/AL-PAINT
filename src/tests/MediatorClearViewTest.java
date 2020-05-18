package tests;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.model.geometry.base.SimplePolygon;
import editeur.view.ApplicationFx;
import editeur.view.IApplication;
import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.attribute.standard.Media;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class MediatorClearViewTest {
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
    public void mediatorClearView() {
        app = new ApplicationFx();
        IShape shape = new Rectangle(10,10,20,20);
        IShape poly  = new SimplePolygon(90, 10, 8, 10);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),poly);

        Mediator.getInstance().delete(app.getWhiteBoard().getComposite(), shape);
        Mediator.getInstance().delete(app.getWhiteBoard().getComposite(), poly);

        int size = app.getWhiteBoard().getComposite().getComponents().size();
        assertEquals("Size test", 0, size);
    }
}