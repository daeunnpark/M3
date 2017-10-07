package gol;

import gol.jTPS_Transaction;
import java.util.ArrayList;

/**
 *
 * @author McKillaGorilla
 */
public class jTPS {
    private ArrayList<jTPS_Transaction> transactions = new ArrayList<>();
    private int mostRecentTransaction = -1;
    
    public jTPS() {}
    
    public void addTransaction(jTPS_Transaction transaction) {
        // IS THIS THE FIRST TRANSACTION?
        System.out.println("ADDED");
        if (mostRecentTransaction < 0) {
            // DO WE HAVE TO CHOP THE LIST?
            if (transactions.size() > 0) {
                transactions = new ArrayList<>();
            }
            transactions.add(transaction);
            System.out.println("ADDED2");
        }
        // ARE WE ERASING ALL THE REDO TRANSACTIONS?
        else if (mostRecentTransaction < (transactions.size()-1)) {
            transactions.set(mostRecentTransaction+1, transaction);
            transactions = new ArrayList<>(transactions.subList(0, mostRecentTransaction+2));
        }
        // IS IT JUST A TRANSACTION TO APPEND TO THE END?
        else {
            transactions.add(transaction);
        }
         mostRecentTransaction++;
        //doTransaction();
        System.out.println(toString());
    }
    
    public void redoTransaction() {
        if (mostRecentTransaction < (transactions.size()-1)) {
            jTPS_Transaction transaction = transactions.get(mostRecentTransaction+1);
           
            transaction.redoTransaction();
            mostRecentTransaction++;
        }
        System.out.println(toString());
    }
    
    public void undoTransaction() {
        System.out.println(mostRecentTransaction + "mostRecentTransaction");
        if (mostRecentTransaction >= 0) {
            jTPS_Transaction transaction = transactions.get(mostRecentTransaction);
            transaction.undoTransaction();
            mostRecentTransaction--;
        }
        System.out.println(toString());
    }
    
    public String toString() {
        String text = "--Number of Transactions: " + transactions.size() + "\n";
        text += "--Current Index on Stack: " + mostRecentTransaction + "\n";
        text += "--Current Transaction Stack:\n";
        for (int i = 0; i <= mostRecentTransaction; i++) {
            jTPS_Transaction jT = transactions.get(i);
            text += "----" + jT.toString() + "\n";
        }
        return text;
    }

  
}