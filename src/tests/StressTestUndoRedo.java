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
import static org.junit.Assert.assertTrue;

/**
 * The type MediatorAddTest.
 */
public class StressTestUndoRedo {
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
     * Mediator add.
     */
    @Test
    public void StressTest() {
        IShape clone;
        int cmdStress = 1000;
        int stressIterations = 10000;
        app = new ApplicationFx();
        IShape shape = new Rectangle(10,10,20,20);
        Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape);

        //Add undo-redo stress Iterations
        for (int i = 0; i < stressIterations ; i++) {
            Mediator.getInstance().undo();
            Mediator.getInstance().redo();
        }
        assertTrue("Shape should be present",app.getWhiteBoard().getShape(0).myequals(shape));

        //Stress add + undo
        for (int i = 0; i < cmdStress ; i++)
            Mediator.getInstance().add(app.getWhiteBoard().getComposite(),shape.clone());
        for (int i = 0; i < cmdStress; i++)
            Mediator.getInstance().undo();
        assertTrue("Only one shape should be present",app.getWhiteBoard().getComposite().getComponents().size() == 1);

        //Move undo-redo stress Iterations
        Mediator.getInstance().move(shape,150,150);
        clone = shape.clone();
        for (int i = 0; i < stressIterations ; i++) {
            Mediator.getInstance().undo();
            Mediator.getInstance().redo();
        }
        assertTrue("should be equals",clone.myequals(app.getWhiteBoard().getShape(0)));
        //Recolor undo-redo stress Iterations
        Mediator.getInstance().reColor(shape,90,80,70);
        clone = shape.clone();
        for (int i = 0; i < stressIterations ; i++) {
            Mediator.getInstance().undo();
            Mediator.getInstance().redo();
        }
        assertTrue("should be equals",clone.myequals(app.getWhiteBoard().getShape(0)));

        //Stress Iterations on stress undo + stress redo
        cmdStress = 100;
        for (int i = 0; i < cmdStress ; i++)
            Mediator.getInstance().move(shape,10,10);

        clone = shape.clone();
        for (int i = 0; i < stressIterations ; i++) {
            for (int j = 0; j < cmdStress ; j++)
                Mediator.getInstance().undo();
            for (int j = 0; j < cmdStress ; j++)
                Mediator.getInstance().redo();
        }

        assertTrue("should be equals",clone.myequals(app.getWhiteBoard().getShape(0)));

    }
}