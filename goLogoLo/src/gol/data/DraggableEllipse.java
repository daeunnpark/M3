package gol.data;

import javafx.scene.shape.Ellipse;

/**
 * This is a draggable ellipse for our goLogoLo application.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DraggableEllipse extends Ellipse implements Draggable {

    double startX; // center
    double startY;
    double startDifX;
    double startDifY;
    double diffX;
    double diffY;
    double newX;
    double newY;

    double startCenterX;
    double startCenterY;
    
    double initialX;
    double initialY;

    public DraggableEllipse() {

        setCenterX(0.0);
        setCenterY(0.0);
        setRadiusX(0.0);
        setRadiusY(0.0);
        setOpacity(1.0);
        startDifX = 0.0;
        startDifY = 0.0;
    }

    @Override
    public golState getStartingState() {
        return golState.STARTING_ELLIPSE;
    }

    @Override
    public void start(int x, int y) {
        startCenterX = x;           // for size()
        startCenterY = y;
    }

    @Override
    public void drag(int x, int y) {
        diffX = x - startDifX;
        diffY = y - startDifY;
        newX = startX  + diffX;
        newY = startY + diffY;
        
        setCenterX(newX);
        setCenterY(newY);
        
        startX = newX;
        startY = newY;

        setStartDifX(x);
        setStartDifY(y);
    }

    @Override
    public void size(int x, int y) {
        double width = x - startCenterX;
        double height = y - startCenterY;
        double centerX = startCenterX + (width / 2);
        double centerY = startCenterY + (height / 2);
        setCenterX(centerX);
        setCenterY(centerY);
        setRadiusX(width / 2);
        setRadiusY(height / 2);

    }

    @Override
    public double getX() {
        return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
        return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
        return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
        return getRadiusY() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
       setCenterX(initX + (initWidth / 2));
       setCenterY(initY + (initHeight / 2));
       setRadiusX(initWidth / 2);
       setRadiusY(initHeight / 2);
       
        startCenterX = initX;           // for size()
        startCenterY = initY;
        
    }

    @Override
    public String getShapeType() {
        return ELLIPSE;
    }

    public void setStartDifX(double x) {
        startDifX = x;
    }

    public void setStartDifY(double y) {
        startDifY = y;
    }

    public void setstartCenterX(double x) {
        startX = x;
    }

    public void setstartCenterY(double y) {
        startY = y;
    }

    @Override
    public void setXY(double x, double y) {
        
       setCenterX(getRadiusX()+x);
       setCenterY(getRadiusY()+y);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setXY2(double x, double y) {
        
       setCenterX(x);
       setCenterY(y);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
