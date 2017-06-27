package io.github.malvadeza.sandbox.cardview.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.malvadeza.sandbox.R;
import io.github.malvadeza.sandbox.SandBoxApplication;
import io.github.malvadeza.sandbox.cardview.ListElement;
import timber.log.Timber;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        List<ListElement> list = new ArrayList<>();
        for (int i = 0 ; i < 20 ; ++i) {
            list.add(new ListElement("TextOne " + i, "TextTwo " + i));
        }

        final ItemAdapter itemAdapter = new ItemAdapter(list);
        recyclerView.setAdapter(itemAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListElement newElement = new ListElement("New item", "New item!");
                itemAdapter.addItem(newElement);
            }
        });
    }

}
