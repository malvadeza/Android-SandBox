package io.github.malvadeza.sandbox.cardview.listview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import io.github.malvadeza.sandbox.R;
import io.github.malvadeza.sandbox.cardview.ListElement;

public class ListActivity extends AppCompatActivity {
    private ListCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_view);
        mAdapter = new ListCardAdapter(this);

        for (int i = 0 ; i < 20 ; ++i) {
            mAdapter.add(new ListElement("Text " + i, "Subtext " + i));
        }

        listView.setAdapter(mAdapter);
    }

}
