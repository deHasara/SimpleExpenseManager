package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.SQLiteDB;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by hasara on 11/18/17.
 */

public class DatabaseDefinition {

    public static final String DB_Name = "SimpleExpenseManager_150104U.db";

    public static  final SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy", Locale.getDefault());


    public static final String T_COL_2 = "Date";
    public static final String T_COL_3 = "Account_No";
    public static final String T_COL_4 = "Expence_Type";
    public static final String T_COL_5 = "Amount";

    public static final String AC_COL_1 = "Account_No";
    public static final String AC_COL_2 = "Branch_Name";
    public static final String AC_COL_3 = "Account_Holder_Name";
    public static final String AC_COL_4 = "Balance";

    public static final String T_Type_Expense = "E";
    public static final String T_Type_Income = "I";
}
