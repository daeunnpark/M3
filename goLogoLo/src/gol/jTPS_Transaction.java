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
        
        if (method.equals("setBackgroundColor")) {
            dataManager.setBackgroundColor((Color) after);
        } else if (method.equals("setCurrentFillColor")) { // if selected is a shape do with that
            //dataManager.setCurrentFillColor((Color) after);
            dataManager.setCurrentFillColor(shape, (Color) after);
        } else if (method.equals("setCurrentOutlineColor")) {
            dataManager.setCurrentOutlineColor(shape, (Color) after);
            
        } else if (method.equals("setCurrentOutlineThickness")) {
            dataManager.setCurrentOutlineThickness(shape, (int) after);
            
        } else if (method.equals("removeSelectedShape")) {
            dataManager.removeShape(shape);
            
        } else if (method.equals("moveSelectedShapeToBack")) { // selectshape HERE
            dataManager.moveSelectedShapeToBack(shape);
            
        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToFront(shape);
            
        //} else if (method.equals("makeNewTextBox")) { // add img ellipse rect
          //  dataManager.makeNewTextBox((String) before);
            
       } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox(shape, (String) after);
            
        } else if (method.equals("getBolded")) {
            dataManager.getBolded( shape);
            
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized( shape);
            
        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) after);
            
        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) after);
            
        } else if (method.equals("newShape")) {
            dataManager.pasteShape(shape);
        }
        
    }
    
    public void undoTransaction() {
        
        if (method.equals("setBackgroundColor")) { // SPLIT
            dataManager.setBackgroundColor((Color) before);
        } else if (method.equals("setCurrentFillColor")) { // A REFAIRE
            dataManager.setCurrentFillColor(shape, (Color) before);
        } else if (method.equals("setCurrentOutlineColor")) { // A REFAIRE
            dataManager.setCurrentOutlineColor(shape, (Color) before);
            
        } else if (method.equals("setCurrentOutlineThickness")) {
            dataManager.setCurrentOutlineThickness(shape, (int) before);
            
        } else if (method.equals("removeSelectedShape")) {
            dataManager.pasteShape(shape);
            
        } else if (method.equals("moveSelectedShapeToBack")) {
            dataManager.moveSelectedShapeToFront(shape);
            
        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToBack(shape);
            
        } else if (method.equals("makeNewTextBox")) {
            dataManager.removeShape(dataManager.getNewShape());
            
        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox(shape, (String) before);
            
        } else if (method.equals("getBolded")) { // not working
            dataManager.getBolded(shape);
            
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized( shape);
            
        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) before);
            
        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) before);
        } else if (method.equals("newShape")) {
            dataManager.removeShape(shape);
        }
        
    }
    
    String getmethodname() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        return method.toString();
    }
    
}

/*
public interface jTPS_Transaction {
    public void doTransaction();
    public void undoTransaction();
}
 */
