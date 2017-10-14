package gol;

import gol.data.golData;
import java.util.ArrayList;

/**
 *
 * @author McKillaGorilla
 */
public class jTPS {

    private ArrayList<jTPS_Transaction> transactions = new ArrayList<>();
    private int mostRecentTransaction = -1;

    public jTPS() {
    }

    public void addTransaction(jTPS_Transaction transaction) {
        // IS THIS THE FIRST TRANSACTION?

        if (mostRecentTransaction < 0) {
            // DO WE HAVE TO CHOP THE LIST?
            if (transactions.size() > 0) {
                transactions = new ArrayList<>();
            }
            transactions.add(transaction);

        } // ARE WE ERASING ALL THE REDO TRANSACTIONS?
        else if (mostRecentTransaction < (transactions.size() - 1)) {
            transactions.set(mostRecentTransaction + 1, transaction);
            transactions = new ArrayList<>(transactions.subList(0, mostRecentTransaction + 2));
        } // IS IT JUST A TRANSACTION TO APPEND TO THE END?
        else {
            transactions.add(transaction);
        }

        mostRecentTransaction++;
        setworkspace2(true); // undo
        setworkspace3(false); // redo

        System.out.println(transactions.size() + " ---size--");

        
        if (mostRecentTransaction == transactions.size() - 1) { // last action
            setworkspace3(false);
            System.out.println("NOoo");
        }
         
        //doTransaction();
        System.out.println(toString());
    }

    public void redoTransaction() {
        if (mostRecentTransaction < (transactions.size() - 1)) {
            jTPS_Transaction transaction = transactions.get(mostRecentTransaction + 1);

            transaction.redoTransaction();
            mostRecentTransaction++;
            setworkspace2(true);

            if (mostRecentTransaction < (transactions.size() - 1)) {
                setworkspace3(true);


            } else {
                setworkspace3(false);
            }

            System.out.println(toString());
        }
    }

    public void undoTransaction() {
        if (mostRecentTransaction >= 0) {
            jTPS_Transaction transaction = transactions.get(mostRecentTransaction);
            transaction.undoTransaction();
            mostRecentTransaction--;

            //if(transactions.size()>0){
                setworkspace3(true);
            //}
            if (mostRecentTransaction > -1) {
                setworkspace2(true);
            } else {
                setworkspace2(false);
            }
        }
        /*
        if (mostRecentTransaction == -1) {
            setworkspace2(false);
        } else {
            setworkspace2(true);
        }
        /*
        if ( mostRecentTransaction < transactions.size()-1 ) {
            System.out.println("active");
            setworkspace3(true);
        }
         */

        System.out.println(toString());
    }

    public void setworkspace2(boolean b) {
        // dummy
        jTPS_Transaction transaction;
        if (transactions.size() >= 1) {
            transaction = transactions.get(0);
        } else {
            transaction = transactions.get(mostRecentTransaction);
        }
        transaction.setworkspace2(b);
    }

    public void setworkspace3(boolean b) {
        // dummy
        jTPS_Transaction transaction;
        if (transactions.size() >= 1) {
            transaction = transactions.get(0);
        } else {
            transaction = transactions.get(mostRecentTransaction);
        }
        transaction.setworkspace3(b);
    }

    public String toString() {
        String text = "--Number of Transactions: " + transactions.size() + "\n";
        text += "--Current Index on Stack: " + mostRecentTransaction + "\n";
        text += "--Current Transaction Stack:\n";
        for (int i = 0; i <= mostRecentTransaction; i++) {
            jTPS_Transaction jT = transactions.get(i);
            text += "----" + jT.getmethodname() + "\n";
            //text += "----" + jT.toString() + "\n";
        }
        return text;
    }

}
