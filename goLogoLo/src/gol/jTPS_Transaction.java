package gol;

import gol.data.golData;
import javafx.scene.paint.Color;

/**
 *
 * @author McKillaGorilla
 */
public class jTPS_Transaction {

    private golData dataManager;
    private String method;
    private Object before;
    private Object after;

    public jTPS_Transaction(golData dataManager, String s, Object before, Object after) {
        this.dataManager = dataManager;
        this.method = s;
        this.before = before;
        this.after = after;

    }

    public void redoTransaction() {
        if (method.equals("setBackgroundColor")) {
            dataManager.setBackgroundColor((Color) after);
            System.out.println("REDO");
        }
    }

    ;
    public void undoTransaction() {
        if (method.equals("setBackgroundColor")) {
            dataManager.setBackgroundColor((Color) before);
          //  System.out.println(before.toString() + "before");
            System.out.println("UNDO");
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
