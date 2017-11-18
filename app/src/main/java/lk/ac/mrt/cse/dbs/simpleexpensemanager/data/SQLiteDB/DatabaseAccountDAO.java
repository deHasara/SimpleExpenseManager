package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by hasara on 11/19/17.
 */

public class DatabaseAccountDAO implements AccountDAO{

    SQLiteDatabase db;

    public DatabaseAccountDAO(Context context) {
        db = DatabaseHelper.getInstance(context).getWritableDatabase();
    }


    @Override
    public List<String> getAccountNumbersList() {

        List<String> acc_NameList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select "+DatabaseDefinition.AC_COL_1 +" from Account",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }
        else {

            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_1));
                    acc_NameList.add(acc_no);
                } while (cursor.moveToNext());
            }
        }
        return acc_NameList;
    }

    @Override
    public List<Account> getAccountsList() {

        List<Account> account_list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from Account",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
            return null;
        }
        else {
            //iterate through result set
            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_1));
                    String bank_name = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_2));
                    String ac_Holder = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_3));
                    double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseDefinition.AC_COL_4));

                    Account account = new Account(acc_no,bank_name,ac_Holder,balance);
                    account_list.add(account);
                } while (cursor.moveToNext());
            }

        }
        return account_list;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {

        Account account = null;
        Cursor cursor = db.rawQuery("select * from Account where "+DatabaseDefinition.AC_COL_1+" = "+accountNo+";",null);

        if(cursor.getCount()==0){
            Log.d("MYACTIVITY","No Value");
        }
        else {

            if (cursor.moveToFirst()) {
                do {
                    String acc_no = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_1));
                    String bank_name = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_2));
                    String ac_Holder = cursor.getString(cursor.getColumnIndex(DatabaseDefinition.AC_COL_3));
                    double balance = cursor.getDouble(cursor.getColumnIndex(DatabaseDefinition.AC_COL_4));

                    account = new Account(acc_no,bank_name,ac_Holder,balance);

                } while (cursor.moveToNext());
            }
        }

        return account;
    }

    @Override
    public void addAccount(Account account) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DatabaseDefinition.AC_COL_1,account.getAccountNo());
        contentValues.put(DatabaseDefinition.AC_COL_2,account.getBankName());
        contentValues.put(DatabaseDefinition.AC_COL_3,account.getAccountHolderName());
        contentValues.put(DatabaseDefinition.AC_COL_4,account.getBalance());

        long result = db.insert("Account",null,contentValues);
        if(result == -1){
            Log.d("MYACTIVITY", "NOT INSERTED");
        }
        else
            Log.d("MYACTIVITY","INSERTED ACCOUNT");

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        Account account = getAccount(accountNo);
        if (account == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }

        int state = db.delete("Account",DatabaseDefinition.AC_COL_1+" = "+accountNo,null );

        if(state == -1){
            Log.d("MYACTIVITY","NOT DELETED");
        }
        else
            Log.d("MYACTIVITY","DELETED DATA");


    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {

        Account account = getAccount(accountNo);
        if (account == null) {

            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }


        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseDefinition.AC_COL_4, account.getBalance());

        // update query
        int state = db.update("Account",contentValues,DatabaseDefinition.AC_COL_1 + " = "+account.getAccountNo(),null);

        if(state!=-1){
            Log.d("MYACTIVITY","Updated");
        }
        else {
            Log.d("MYACTIVITY","error occoured");
        }
    }
}


