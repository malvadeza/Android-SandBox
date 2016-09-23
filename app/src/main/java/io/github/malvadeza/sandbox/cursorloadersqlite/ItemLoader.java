package io.github.malvadeza.sandbox.cursorloadersqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;

import java.util.List;

/**
 * Created by tonho on 23/09/16.
 */

public class ItemLoader extends CursorLoader {
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public ItemLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        SQLiteDatabase db = DbHelper.getInstance(getContext()).getReadableDatabase();
        Cursor cursor = db.query(DbContract.StringEntry.TABLE_NAME,
                new String[]{
                        DbContract.StringEntry._ID,
                        DbContract.StringEntry.CONTENT
                }, null, null, null, null, null
        );

        if (cursor != null) {
            cursor.getCount();
            cursor.registerContentObserver(mObserver);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), DbContract.URI);

        return cursor;
    }
}
