package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hasara on 11/16/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Account.db";
    public static final String TABLE_NAME = "account_table";
    public static final String COL_1 = "AccountNo";
    public static final String COL_2 = "BankName";
    public static final String COL_3 = "AccountHolderName";
    public static final String COL_4 = "Balance";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ DATABASE_NAME+ " (AccountNo TEXT PRIMARY KEY, BankName TEXT, AccountHolderName TEXT, Balance INTEGER);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String accountNo, String bankName, String accountHolderName, String balance){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, accountNo);
        contentValues.put(COL_2, bankName);
        contentValues.put(COL_3, accountHolderName);
        contentValues.put(COL_4, balance);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }
}
