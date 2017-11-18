package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by hasara on 11/19/17.
 */

public class DatabaseTransactionDAO implements TransactionDAO {

    SQLiteDatabase db;

    public DatabaseTransactionDAO(Context context) {
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseDefinition.T_COL_2, DatabaseDefinition.dateFormat.format(date));
        contentValues.put(DatabaseDefinition.T_COL_3,accountNo);
        contentValues.put(DatabaseDefinition.T_COL_4,expenseType.toString());
        contentValues.put(DatabaseDefinition.T_COL_5,amount);
        long result = db.insert("AC_Transaction",null,contentValues);
        if(result == -1){
            Log.d("MYACTIVITY","NOT_INSERTED");
        }
        else
            Log.d("MYACTIVITY","DATA_INSERTED");

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {

        List<Transaction> transactions = new LinkedList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy", Locale.getDefault());

        Cursor cursor = db.rawQuery("select * from AC_Transaction",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }

        else
        {
            if(cursor.moveToFirst()) {
                do {
                    try {


                        Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseDefinition.T_COL_2)));
                        String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.T_COL_3));
                        String expenceTypeString = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.T_COL_4));
                        double amount = cursor.getDouble(cursor.getColumnIndex(DatabaseDefinition.T_COL_5));
                        ExpenseType expenceType = null;
                        if (expenceTypeString.equals(ExpenseType.EXPENSE.toString())){
                            expenceType = ExpenseType.EXPENSE;
                        }
                        else{
                            expenceType = ExpenseType.INCOME;
                        }


                        Transaction transaction = new Transaction(date,acc_no,expenceType,amount);

                        // add transaction to list
                        transactions.add(transaction);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }while (cursor.moveToNext()) ;
            }

        }
        return transactions;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        List<Transaction> transactions = getAllTransactionLogs();

        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }

        return transactions.subList(size - limit, size);
    }
}


