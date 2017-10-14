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

            dataManager.setCurrentFillColor(shape, (Color) after);
        } else if (method.equals("setCurrentOutlineColor")) {
            dataManager.setCurrentOutlineColor(shape, (Color) after);

        } else if (method.equals("setCurrentOutlineThickness")) {
            dataManager.setCurrentOutlineThickness(shape, (int) after);

        } else if (method.equals("removeShape")) {
            dataManager.removeShape(shape); // add to trans

        } else if (method.equals("moveSelectedShapeToBack")) { // selectshape HERE
            dataManager.moveSelectedShapeToBack(shape);

        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToFront(shape);

        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox(shape, (String) after);

        } else if (method.equals("getBolded")) {
            dataManager.getBolded(shape);

        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized(shape);

        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) after);

        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) after);

        } else if (method.equals("newShape")) {
            dataManager.pasteShape(shape);

        } else if (method.equals("copyShape")) {         
            // reset copy
            dataManager.copyShape(shape);
        } else if (method.equals("cutShape")) {
            dataManager.cutShape(shape);

        } else if (method.equals("pasteShape")) {
            dataManager.pasteShape(shape);
        }

    }

    public void undoTransaction() {

        if (method.equals("setBackgroundColor")) { // SPLIT
            dataManager.setBackgroundColor((Color) before);
            
        } else if (method.equals("setCurrentFillColor")) { 
            dataManager.setCurrentFillColor(shape, (Color) before);
            
        } else if (method.equals("setCurrentOutlineColor")) { 
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
            dataManager.getItalicized(shape);

        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) before);

        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) before);

        } else if (method.equals("newShape")) {
            dataManager.removeShape(shape);

        } else if (method.equals("copyShape")) {
            // reset copy   
            dataManager.setCopy((Shape) before);

        } else if (method.equals("cutShape")) {
            dataManager.setCopy((Shape) before);
            dataManager.pasteShape(shape);

        } else if (method.equals("pasteShape")) {
            dataManager.removeShape(shape);
            dataManager.setCopy((Shape) after);
        }

    }

    public void setworkspace2(boolean b){
       // if(dataManager.reloadworkspace2(b))
        dataManager.reloadworkspace2(b);
    }
    
    
    public void setworkspace3(boolean b){
       // if(dataManager.reloadworkspace2(b))
        System.out.println("hh");
        dataManager.reloadworkspace3(b);
    }
    String getmethodname() {
        return method.toString();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}

