package gol.data;

import javafx.scene.shape.Rectangle;

/**
 * This is a draggable rectangle for our goLogoLo application.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class DraggableRectangle extends Rectangle implements Draggable {
    double startX;
    double startY;
    
    public DraggableRectangle() {
	setX(0.0);
	setY(0.0);
	setWidth(0.0);
	setHeight(0.0);
	setOpacity(1.0);
	startX = 0.0; 
	startY = 0.0;
    }
    
    @Override
    public golState getStartingState() {
	return golState.SELECTING_SHAPE;
    }
    
    @Override
    public void start(int x, int y) {
	startX = x;
	startY = y;
	setX(x);
	setY(y);
    }
    
    @Override
    public void drag(int x, int y) {
	double diffX = x - startX ;
	double diffY = y - startY;
	double newX = getX() + diffX;
	double newY = getY() + diffY;
        //System.out.println(getX() + "THIS");
	xProperty().set(newX);
	yProperty().set(newY);
	startX = x;
	startY = y;
    }
    
    public String cT(double x, double y) {
	return "(x,y): (" + x + "," + y + ")";
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - startX ;
        double height = y - startY;
        double centerX = startX + (width/2);
        double centerY = startY + (height/2);
        xProperty().set(centerX);
	yProperty().set(centerY);
	widthProperty().set(width);
	heightProperty().set(height);	
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX + (initWidth/2));
	yProperty().set(initY + (initHeight/2));
	widthProperty().set(initWidth);
	heightProperty().set(initHeight);
    }
    
    @Override
    public String getShapeType() {
	return RECTANGLE;
    }
}
