package io.github.malvadeza.sandbox.cursorloadersqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "SandBoxCursorLoaderSqlite.db";
    private static final int DB_VERSION = 1;

    private static DbHelper sInstance;

    public static DbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context);
        }

        return sInstance;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String createTableSql = "CREATE TABLE " + DbContract.StringEntry.TABLE_NAME + "("
                + DbContract.StringEntry._ID + " INTEGER PRIMARY KEY,"
                + DbContract.StringEntry.CONTENT + " TEXT NOT NULL"
                + ");";


        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.StringEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}
