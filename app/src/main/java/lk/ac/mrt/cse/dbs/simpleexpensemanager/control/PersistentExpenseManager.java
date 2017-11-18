package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.util.Log;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB.DatabaseAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB.DatabaseTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;

/**
 * Created by hasara on 11/18/17.
 */

public class PersistentExpenseManager extends ExpenseManager {

    Context context;

    public PersistentExpenseManager(Context context)  {
        this.context = context;
        Log.d("11111111111111111","3");

        DatabaseHelper dbCreator= new DatabaseHelper(context);
        Log.d("11111111111111111","3");
        try {
            setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setup() throws ExpenseManagerException {

        TransactionDAO databaseTransactionDAO = new DatabaseTransactionDAO(context);
        setTransactionsDAO(databaseTransactionDAO);

        AccountDAO databaseAccountDAO = new DatabaseAccountDAO(context);
        setAccountsDAO(databaseAccountDAO);

        DatabaseHelper dbConnection = new DatabaseHelper(context);
    }

}
