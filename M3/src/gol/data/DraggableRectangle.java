package gol.data;

import javafx.scene.shape.Rectangle;

/**
 * This is a draggable rectangle for our goLogoLo application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 *
 */
public class DraggableRectangle extends Rectangle implements Draggable {

    double startX;
    double startY;
    double startDifX;
    double startDifY;
    double diffX;
    double diffY;
    double newX;
    double newY;
    String filepath;
    double initialX;
    double initialY;

    public DraggableRectangle() {
        setX(0.0);
        setY(0.0);
        setWidth(0.0);
        setHeight(0.0);
        setOpacity(1.0);
        startX = 0.0;
        startY = 0.0;
        startDifX = 0.0;
        startDifY = 0.0;
        filepath = null;
    }

    @Override
    public golState getStartingState() {
        return golState.STARTING_RECTANGLE;
    }

    @Override
    public void start(int x, int y) {
        xProperty().set(x);
        yProperty().set(y);
        startX = x;
        startY = y;
    }

    @Override
    public void drag(int x, int y) {

        diffX = x - startDifX; 
        diffY = y - startDifY; 
        newX = startX + diffX;
        newY = startY + diffY;

        xProperty().set(newX);
        yProperty().set(newY);

        startX = newX;
        startY = newY;

        setStartDifX(x);
        setStartDifY(y);
        
    }

    public String cT(double x, double y) {
        return "(x,y): (" + x + "," + y + ")";
    }

    @Override
    public void size(int x, int y) {
        double width = x - getX();
        widthProperty().set(width);
        double height = y - getY();
        heightProperty().set(height);
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        xProperty().set(initX);
        yProperty().set(initY);
        widthProperty().set(initWidth);
        heightProperty().set(initHeight);
 
        startX = initX;
        startY = initY;
    }

    @Override
    public String getShapeType() {
        return RECTANGLE;
    }

    public void setStartDifX(double x) {
        startDifX = x;
    }

    public void setStartDifY(double y) {
        startDifY = y;
    }

    @Override
    public void setXY(double x, double y) { 
        xProperty().set(x);
        yProperty().set(y);
    }

    public void setStartXY(double x, double y) {
        startX = x;
        startY = y;
    }

    public void setfilepath(String s) {
        this.filepath = s;
    }

    public String getfilepath() {
        return filepath;
    }

    @Override
    public void setInitialXY(double x, double y) {
        this.initialX = x;
        this.initialY = y;
        
    }

    @Override
    public double getInitialX() {
        return initialX;
    }

    @Override
    public double getInitialY() {
        return initialY;
    }

}
