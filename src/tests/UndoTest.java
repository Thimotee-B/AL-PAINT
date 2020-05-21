package tests;

import editeur.controller.IMediator;
import editeur.controller.Mediator;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Rectangle;
import editeur.view.ApplicationFx;
import editeur.view.IApplication;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type UndoTest.
 */
public class UndoTest {
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
     * Undo add.
     */
    @Test
    public void undoAdd() {
        app = new ApplicationFx();
        IShape shape = new Rectangle(10,10,20,20);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);

        int size = app.getWhiteBoard().getComposite().getComponents().size();
        Mediator.getInstance().undo();
        int aftersize = app.getWhiteBoard().getComposite().getComponents().size();
        assertTrue("ADD test", size - 1 == aftersize);

        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        IShape clone = shape.clone();
        Mediator.getInstance().move(shape,50,50);
        Mediator.getInstance().undo();
        assertEquals("Move test",shape,clone);

        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);
        Mediator.getInstance().reColor(shape,50,50,50);
        Mediator.getInstance().undo();
        assertEquals("RecolorUndo test",shape,clone);
    }
}