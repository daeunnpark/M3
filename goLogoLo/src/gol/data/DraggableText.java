/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gol.data;

import javafx.scene.text.Text;

/**
 *
 * @author Daeun NOTHING WIHT SIZE SINCE ITS WITH FONT *
 */
public class DraggableText extends Text implements Draggable {

    double startX;
    double startY;
    double startDifX;
    double startDifY;
    double diffX;
    double diffY;
    double newX;
    double newY;

    public DraggableText() {
        setX(0.0);
        setY(0.0);;
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

    public void setStartDifX(double x) {
        startDifX = x;
    }

    public void setStartDifY(double y) {
        startDifY = y;
    }

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

}
