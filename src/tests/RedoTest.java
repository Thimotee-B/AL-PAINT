package tests;

import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
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
import static org.junit.Assert.assertTrue;

/**
 * The type RedoTest.
 */
public class RedoTest {
    /**
     * The App.
     */
    IApplication app;

    /**
     * Init toolkit.
     *
     * @throws InterruptedException the interrupted exception
     */
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

    /**
     * Redo add.
     */
    @Test
    public void redoAdd() {
        app = new ApplicationFx();
        IShape shape = new Rectangle(10,10,20,20);
        IShape clone = shape.clone();
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),clone);

        int size = app.getWhiteBoard().getComposite().getComponents().size();
        Mediator.getInstance().undo();
        Mediator.getInstance().redo();
        int aftersize = app.getWhiteBoard().getComposite().getComponents().size();
        assertEquals("Size test", size, aftersize);

        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),clone);
        Mediator.getInstance().move(shape,150,150);
        Mediator.getInstance().move(clone,150,150);
        Mediator.getInstance().undo();
        Mediator.getInstance().undo();
        Mediator.getInstance().redo();
        Mediator.getInstance().redo();
        assertTrue("Move test",shape.myequals(clone));

        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),clone);
        Mediator.getInstance().reColor(shape,50,50,50);
        Mediator.getInstance().reColor(clone,50,50,50);
        Mediator.getInstance().undo();
        Mediator.getInstance().undo();
        Mediator.getInstance().redo();
        Mediator.getInstance().redo();
        assertTrue("RecolorUndo test",shape.myequals(clone));
    }
}