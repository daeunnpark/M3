package gol;

import gol.data.golData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 *
 * @author McKillaGorilla
 */
public class jTPS_Transaction {

    private golData dataManager;
    private String method;
    private Object before;
    private Object after;
    private Shape shape;

    public jTPS_Transaction(golData dataManager, String s, Object before, Object after, Object shape) {
        this.dataManager = dataManager;
        this.method = s;
        this.before = before;
        this.after = after;
        this.shape = (Shape) shape;

    }

    public void redoTransaction() {
        if (method.equals("setBackgroundColor") ) { 
            dataManager.setBackgroundColor((Color) after);
        }
        else if (method.equals("setCurrentFillColor")) { // if selected is a shape do with that
            dataManager.setCurrentFillColor((Color) after);
        } 
        else if (method.equals("setCurrentOutlineColor")) { 
            dataManager.setCurrentOutlineColor((Color) after);
            
        } else if (method.equals("setCurrentOutlineThickness")) { 
            dataManager.setCurrentOutlineThickness((int) after);
            
        } else if (method.equals("removeSelectedShape")) {
            dataManager.removeShape((Shape) before);
            
        } else if (method.equals("moveSelectedShapeToBack")) { // selectshape HERE
            dataManager.moveSelectedShapeToBack();

        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToFront();

        } else if (method.equals("makeNewTextBox")) { // add img ellipse rect
            dataManager.makeNewTextBox((String) before);

        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox((String) after);

        } else if (method.equals("getBolded")) {
            dataManager.getBolded((Shape) shape);
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized((Shape) shape);

        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) after);
        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) after);
        }

    }

    public void undoTransaction() {
        if (method.equals("setBackgroundColor") ) { // SPLIT
            dataManager.setBackgroundColor((Color) before);
        }
        else if (method.equals("setCurrentFillColor")) { // A REFAIRE
            dataManager.setCurrentFillColor((Color) before);
        } 
        else if (method.equals("setCurrentOutlineColor")) { // A REFAIRE
            dataManager.setCurrentOutlineColor((Color) before);
            
        } else if (method.equals("setCurrentOutlineThickness")) {
            dataManager.setCurrentOutlineThickness((int) before);

        } else if (method.equals("removeSelectedShape")) {
            dataManager.pasteShape((Shape) before);

        } else if (method.equals("moveSelectedShapeToBack")) {
            dataManager.moveSelectedShapeToFront();

        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToBack();

        } else if (method.equals("makeNewTextBox")) {
            dataManager.removeShape(dataManager.getNewShape());

        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox((String) before);

        } else if (method.equals("getBolded")) { // not working
            dataManager.getBolded((Shape) shape);
            
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized((Shape) shape);
            
        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) before);

        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) before);
        }

    }
;
}

/*
public interface jTPS_Transaction {
    public void doTransaction();
    public void undoTransaction();
}
*/
