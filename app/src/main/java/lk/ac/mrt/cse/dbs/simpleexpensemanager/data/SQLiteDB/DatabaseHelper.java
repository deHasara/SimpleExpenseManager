package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hasara on 11/16/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    public SQLiteDatabase db = null;

    public DatabaseHelper(Context context) {
        super(context, DatabaseDefinition.DB_Name,null,1);
        db = this.getWritableDatabase();

    }


    public static DatabaseHelper getInstance(Context context){
        if (instance == null)
        {
            synchronized(DatabaseHelper.class)
            {
                if (instance == null)
                { instance = new DatabaseHelper(context);	}
            }
        }
        return instance;
    }

    public SQLiteDatabase getSQLiteDB(){
        return db;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
