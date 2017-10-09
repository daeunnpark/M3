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
    //CanvasController canvasController = (CanvasController) app.getFileComponent();

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
        else if (method.equals("setCurrentFillColor")) { // 
            dataManager.setCurrentFillColor(shape, (Color) after);           
        } 
        else if (method.equals("setCurrentOutlineColor")) { 
            dataManager.setCurrentOutlineColor(shape, (Color) after);
            
        } else if (method.equals("setCurrentOutlineThickness")) {   // rethink
            dataManager.setCurrentOutlineThickness(shape, (int) after);
            
        } else if (method.equals("removeSelectedShape")) {
            dataManager.removeShape((Shape) before);
            
        } else if (method.equals("moveSelectedShapeToBack")) { 
            dataManager.moveSelectedShapeToBack(shape);

        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToFront(shape);

        } else if (method.equals("makeNewTextBox")) { // add img ellipse rect
            dataManager.makeNewTextBox((String) before);

        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox(shape, (String) after);

        } else if (method.equals("getBolded")) {
            dataManager.getBolded((Shape) shape);
            
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized((Shape) shape);

        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) after);
            
        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) after);
            
        } else if (method.equals("cloneShape")) {
            dataManager.cloneShape((Shape)before);
            
        } else if (method.equals("pasteShape")) {
            dataManager.pasteShape(shape);
        }
        else{
        System.out.println("NO REDO");
        }

    }

    public void undoTransaction() {
        if (method.equals("setBackgroundColor") ) { 
            dataManager.setBackgroundColor((Color) before);
        }
        else if (method.equals("setCurrentFillColor")) { 
            dataManager.setCurrentFillColor(shape,(Color) before);
        } 
        else if (method.equals("setCurrentOutlineColor")) { // A REFAIRE
            dataManager.setCurrentOutlineColor(shape, (Color) before);
            
        } else if (method.equals("setCurrentOutlineThickness")) {
            dataManager.setCurrentOutlineThickness(shape, (int) before);

        } else if (method.equals("removeSelectedShape")) {
            dataManager.pasteShape((Shape) before);

        } else if (method.equals("moveSelectedShapeToBack")) {
            dataManager.moveSelectedShapeToFront(shape);

        } else if (method.equals("moveSelectedShapeToFront")) {
            dataManager.moveSelectedShapeToBack(shape);

        } else if (method.equals("makeNewTextBox")) {
           // dataManager.removeShape(dataManager.getNewShape());

        } else if (method.equals("changeTextBox")) {
            dataManager.changeTextBox(shape, (String) before);

        } else if (method.equals("getBolded")) { // not working
            dataManager.getBolded((Shape) shape);
            
        } else if (method.equals("getItalicized")) {
            dataManager.getItalicized((Shape) shape);
            
        } else if (method.equals("changefont")) {
            dataManager.changefont(shape, (String) before);

        } else if (method.equals("changesize")) {
            dataManager.changesize(shape, (double) before);
            
        } else if (method.equals("cloneShape")) {
            //dataManager.cloneShape(null); // finalclone = null;
           // dataManager.getCanvasController().setCopy((Shape)before);
            
        } else if (method.equals("pasteShape")) {
            dataManager.removeSelectedShape(shape);
            
            //dataManager.changesize(shape, (double) before);
        } 
        
        
        else {
        System.out.println("NO UNDO");
        }

    }
    // hide after
    public String getmethodname(){
    return this.method;
    }

}

/*
public interface jTPS_Transaction {
    public void doTransaction();
    public void undoTransaction();
}
*/
