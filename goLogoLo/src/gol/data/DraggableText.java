/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gol.data;

import static gol.data.Draggable.RECTANGLE;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Daeun
 * NOTHING WIHT SIZE SINCE ITS WITH FONT 
 * **/
public class DraggableText extends Text implements Draggable {

    @Override
    public double getWidth() {
        return this.getText().length();
       
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getHeight() {
        return this.getFont().getSize();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    double startX;
    double startY;
    
    public DraggableText() {
	setX(0.0);
	setY(0.0);
        //setlength(0.0);
	//setWidth(0.0);
	//setHeight(0.0);
	//setOpacity(1.0);
        setWrappingWidth(0);
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
	double diffX = x - (getX() + (getWidth()/2));
	double diffY = y - (getY() + (getHeight()/2));
	double newX = getX() + diffX;
	double newY = getY() + diffY;
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
	//double width = x - getX();
        //this.setHeight(width);
//	widthProperty().set(width);
//double width = this.getFont().getSize();
	//double height = y - getY();
//	heightProperty().set(height);
//height =  this.getText().length();
    }
    
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	xProperty().set(initX);
	yProperty().set(initY);
        wrappingWidthProperty().set(initWidth);
	//widthProperty().set(initWidth);
        
	//heightProperty().set(initHeight);
    }
    
    @Override
    public String getShapeType() {
	return TEXT;
    }

  
    

   
    
}
