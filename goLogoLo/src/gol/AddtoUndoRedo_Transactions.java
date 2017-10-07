package gol;

import gol.data.golData;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daeun
 */
public class AddtoUndoRedo_Transactions {//implements jTPS_Transaction {

    private golData dataManager;
    private String method;
    private Object before;
    private Object after;

    public AddtoUndoRedo_Transactions(golData dataManager, String s, Object before, Object after) {
        this.dataManager = dataManager;
        this.method = s;
        this.before = before;
        this.after = after;

    }

     public void doTransaction() {
        
        if (method.equals("setBackgroundColor")) {
            dataManager.setBackgroundColor((Color) after);
             System.out.println("REDO");
            
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }
     
      public void undoTransaction() {
        if (method.equals("setBackgroundColor")) {
            dataManager.setBackgroundColor((Color) before);
            System.out.println(before.toString() + "before");
            System.out.println("UNDO");
        }
      }
    


}
