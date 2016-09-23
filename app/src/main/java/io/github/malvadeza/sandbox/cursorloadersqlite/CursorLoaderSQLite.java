package io.github.malvadeza.sandbox.cursorloadersqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.github.malvadeza.sandbox.R;

public class CursorLoaderSQLite extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mEditText;
    private SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_loader_sqlite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditText = (EditText) findViewById(R.id.new_element);

        Button button = (Button) findViewById(R.id.add_element);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = mEditText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = DbHelper.getInstance(getApplicationContext()).getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DbContract.StringEntry.CONTENT, content);

                        db.insert(DbContract.StringEntry.TABLE_NAME, null, values);
                        getApplicationContext().getContentResolver().notifyChange(DbContract.URI, null);

                        Log.d("CursorLoaderSQLite", "Saved");
                    }
                }).start();
            }
        });

        mAdapter = new SimpleCursorAdapter(getBaseContext(), android.R.layout.simple_list_item_1, null,
                new String[]{DbContract.StringEntry.CONTENT}, new int[]{android.R.id.text1}, 0);

        ListView listView = (ListView) findViewById(R.id.element_list);
        listView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportLoaderManager().initLoader(200, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ItemLoader(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
