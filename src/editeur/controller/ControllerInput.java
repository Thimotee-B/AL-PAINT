package editeur.controller;

import editeur.model.geometry.Composite;
import editeur.model.geometry.IShape;
import editeur.model.geometry.base.Point;

import editeur.view.AbstractApplication;
import editeur.view.GUIFactory.GenericViewElements.GenericToolBar;


import java.util.Vector;

/**
 * The class ControllerInput implements IControllerInput.
 * The role of this class is to manage the different mouse or keyboard specifics control on this app.
 * Like moving a shape with the mouse, adding it to the toolbar ...
 * Works with the Mediator.
 */
public class ControllerInput implements  IControllerInput {
    private final AbstractApplication app;

    /**
     * Instantiates a new Controller input.
     *
     * @param app the application.
     */
    public ControllerInput(AbstractApplication app) {
        this.app = app;
    }

    /**
     * Mouse click event: manage the current mouseEvent.
     * Move the shape pointed by old point to to position.
     * Else select the shapes contains in a rectangle from old to to position.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseClickEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == Mediator.LEFT && old != null){
            IShape s;
            if(fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                Mediator.getInstance().setClickedShape(s);
                if (s != null) {
                    Point p = computeNewPos(s, old, to);
                    limit(s,p,true,false);
                    Mediator.getInstance().move(s, p.getX(), p.getY());
                }
            }

            else {
                s = this.app.getWhiteBoard().getShape(old);
                Mediator.getInstance().setClickedShape(s);
                if (s != null){
                    Point p = computeNewPos(s, old, to);
                    limit(s,p,false,false);
                    Mediator.getInstance().move(s, p.getX(), p.getY());
                }
                else {
                    Mediator.getInstance().setCoordinatesSelected(
                            this.AddToSelection(Mediator.getInstance().getSelectedShapes(),old, to));
                }
            }

        }

    }

    /**
     * Mouse click event for adding a tool ( a shape in the toolbar.)
     * Simply clone and add the cloned shape at old position if exists and add it to the to position.
     * Does nothing if from toolbar
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseClickEventAddTool(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == Mediator.LEFT && old != null){
            IShape s;
            if(!fromToolbar) {
                s = this.app.getWhiteBoard().getShape(old);
                if (s != null){
                    IShape tool  = s.clone();
                    scaleTool(tool, this.app.getToolBar());
                    Point  p     = computeNewPos(tool, old, to);
                    limit(tool,p,true,false);
                    int alignX = p.getX();
                    int alignY = p.getY();

                    tool.move(alignX, alignY);
                    boolean mustAlign = false;
                    if( tool instanceof Composite){
                        Composite c = (Composite) tool;
                        for (IShape myShape : c.getComponents())
                            if (!app.getToolBar().inToolBar(myShape.getPosition()))
                                mustAlign = true;
                        int minX      = c.minComponents()[0];
                        int minWidth  = c.minComponents()[1];
                        int minY      = c.minComponents()[2];
                        int minHeight = c.minComponents()[3];
                        if (minX < alignX)
                            alignX = alignX - minX + minWidth/2;
                        if (minY < alignY)
                            alignY = minY - minHeight;
                    }
                    if (mustAlign) {
                        Point align = new Point(alignX,alignY);
                        limit(tool,align,false,false);
                        tool.move(align.getX(), align.getY());
                    }
                    Mediator.getInstance().Notify();
                    if(app.getToolBar().inToolBar(to)) {
                        Mediator.getInstance().add(app.getToolBar().getComposite(), tool);
                    }
                }
            }

        }
    }

    /**
     * Mouse dragged event: Simply drag a shape from toolbar at old position and clone it to to position
     * in whiteboard.
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseDraggedEvent(boolean fromToolbar ,int clickSide,Point old, Point to) {
        if (clickSide == Mediator.LEFT && old != null){
            IShape s;
            if(fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                if (s != null){
                    Point  p     = computeNewPos(s, old, to);
                    IShape tool  = s.clone();
                    limit(tool,p,false,false);
                    tool.move(p.getX(), p.getY());
                    Mediator.getInstance().clearView();
                    Mediator.getInstance().Notify();
                    if(app.getWhiteBoard().inWhiteBoard(p))
                        Mediator.getInstance().add(app.getWhiteBoard().getComposite(), tool);
                }
            }

        }
    }

    /**
     * Mouse trash event : Handle the deletion of shape at old position put in trash
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param clickSide   the click side
     * @param old         the old position
     * @param to          the to position
     */
    @Override
    public void MouseTrashEvent(boolean fromToolbar, int clickSide, Point old, Point to) {
        if (clickSide == Mediator.LEFT && old != null) {
            IShape s;
            if (fromToolbar) {
                s = this.app.getToolBar().getShape(old);
                if (s != null)
                    Mediator.getInstance().delete(this.app.getToolBar().getComposite(), s);
            } else {
                s = this.app.getWhiteBoard().getShape(old);
                if (s != null)
                    Mediator.getInstance().delete(this.app.getWhiteBoard().getComposite(), s);
            }

        }
    }

    /**
     * Show dragged shape boolean : Draw the current dragged shape, disclaimer, a temporary clone is moved until we
     * reach destination.
     *
     * @param fromToolbar the boolean from toolbar indicates if the point is from toolbar.
     * @param old         the old position
     * @param to          the to position
     * @return the boolean : false if we dragged nothing.
     */
    @Override
    public boolean ShowDraggedShape(boolean fromToolbar, Point old, Point to){
        IShape s, clone;
        if (fromToolbar) {
            s = this.app.getToolBar().getShape(old);
            if (s == null) return false;

            clone = s.clone();
            Point p = computeNewPos(s, old, to);

            if (p.getX() < 0) p.setX(0);
            if (p.getY() < 0) p.setY(0);

            if (p.getX() + clone.getWidth() > this.app.getToolBar().getWidth() + this.app.getWhiteBoard().getWidth()) {
                p.setX( this.app.getToolBar().getWidth() + this.app.getWhiteBoard().getWidth()- clone.getWidth());
            }

            if (p.getY() + clone.getHeight() > this.app.getToolBar().getHeight() + this.app.getTrashButton().getHeight()) {
                clone.scale( (float)this.app.getTrashButton().getHeight() / (float) clone.getHeight() );
                p.setY(this.app.getToolBar().getHeight() + this.app.getTrashButton().getHeight() - clone.getHeight());
            }

            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getToolBar().get());
        }
        else {
            s = this.app.getWhiteBoard().getShape(old);
            if (s == null) return false;

            clone = s.clone();
            Mediator.getInstance().undoShapeAdd(s);
            Point p = computeNewPos(s, old, to);
            limit(clone,p,false,true);

            clone.move(p.getX(), p.getY());
            clone.draw(this.app.getDrawBridge(),this.app.getWhiteBoard().get());
        }
        return true;

    }

    /**
     * Add to selection int [ ] : Add the shapes contains in current selection in selected shapes.
     *
     * @param selectedShapes the selected shapes
     * @param p1             the p 1
     * @param p2             the p 2
     * @return the int [ ]
     */
    private int[] AddToSelection(Vector<IShape> selectedShapes, Point p1 , Point p2){
        selectedShapes.removeAllElements();
        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        for (int x = minX; x <= maxX ; x++)
            for (int y = minY ; y <= maxY ; y++)
                for (IShape s : app.getWhiteBoard().getComposite().getComponents())
                    if( s.isInside(new Point(x,y)) && !selectedShapes.contains(s))
                        selectedShapes.add(s);
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = 0;
        maxY = 0;
        int h = 0, w= 0;
        for (IShape s : selectedShapes){
            if (s.getPosition().getX() < minX)
                minX = s.getPosition().getX();
            if (s.getPosition().getX() + s.getWidth() > maxX)
                maxX = s.getPosition().getX() + s.getWidth();

            if (s.getPosition().getY() < minY)
                minY = s.getPosition().getY();
            if (s.getPosition().getY() + s.getHeight() > maxY) {
                maxY = s.getPosition().getY() + s.getHeight();
            }

        }

        int [] tab = { minX , minY , maxX -minX , maxY - minY};

        return tab;
    }

    /**
     * Scale a tool if can't be contains in the toolbar.
     *
     * @param tool    the tool
     * @param toolBar the tool bar
     */
    private void scaleTool(IShape tool, GenericToolBar toolBar){
        if (tool.getWidth() > toolBar.getWidth() && toolBar.getWidth() > 0
                || tool.getWidth() >= toolBar.getToolMaxSize() ){

            double factor = (double)toolBar.getToolMaxSize() / (double) tool.getWidth();
            tool.scale(factor);
            Mediator.getInstance().Notify();

        }
    }

    /**
     * Compute new pos point.
     *
     * @param s  the s
     * @param p1 the p 1
     * @param p2 the p 2
     * @return the point
     */
    private Point computeNewPos(IShape s, Point p1, Point p2) {
        Point oldPos = s.getPosition();
        int stepX = p1.getX() - oldPos.getX();
        int stepY = p1.getY() - oldPos.getY();
        return new Point(p2.getX()-stepX, p2.getY()-stepY);
    }

    /**
     * Limits the different object for not being able to goes out from dimension of application.
     *
     * @param s       the s
     * @param p       the p
     * @param toolbar the toolbar
     * @param addTool the add tool
     */
    private void limit(IShape s, Point p, boolean toolbar, boolean addTool){
        int width  = (toolbar) ? this.app.getToolBar().getWidth() : this.app.getWhiteBoard().getWidth();
        int height = (toolbar) ? this.app.getToolBar().getHeight() : this.app.getWhiteBoard().getHeight();
        if (p.getX() + s.getWidth() >= width)
            p.setX(width - s.getWidth());
        if ( !addTool && p.getX()<= 0)
            p.setX(0);
        if (p.getY() + s.getHeight() >= height)
            p.setY(height - s.getHeight());
        if (p.getY()<= 0)
            p.setY(0);

        if (addTool && p.getX() < - this.app.getToolBar().getWidth())
            p.setX(- this.app.getToolBar().getWidth());
    }
}
