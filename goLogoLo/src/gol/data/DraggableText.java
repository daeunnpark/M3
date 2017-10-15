/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gol.data;

import java.awt.Font;
import java.awt.Graphics;
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

    double initialX;
    double initialY;

    public DraggableText() {
        // location of text
        setX(250);
        setY(400);
        setWrappingWidth(0);
        startX = 250;
        startY = 400;

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
        startX = initX;
        startY = initY;

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
        float width = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().computeStringWidth(this.getText(), this.getFont());
        return width;
    }

    @Override
    public double getHeight() {
        // not accurate
        Text text = this;
        double h = text.getBoundsInLocal().getHeight() - 100;
        return h;
    }

    @Override
    public void setXY(double x, double y) {
        xProperty().set(x);
        yProperty().set(y);
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
