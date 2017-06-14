package io.github.malvadeza.sandbox.threads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.github.malvadeza.sandbox.R;

public class ThreadsActivity extends AppCompatActivity {
    private BroadcastReceiver mBcReceiver;
    private Button mStartButton;
    private Button mStopButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        ThreadsActivity.class.getName();

        Log.d("ThreadsActivity", "ThreadsActivity id -> " + Thread.currentThread().getId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mBcReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("SANDBOX_ACTION")) {
                    final long runnableThreadId = intent.getLongExtra("MY_RUNNABLE_THREAD_ID", 0);

                    Log.d("mBcReceiver", "Inside onReceive id -> " + Thread.currentThread().getId());
                    Log.d("mBcReceiver", "MyRunnable id -> " + runnableThreadId);
                }
            }
        };

        mStartButton = (Button) findViewById(R.id.start_service);
        mStopButton = (Button) findViewById(R.id.stop_service);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ThreadsActivity", "Start Button Clicked");
                Intent intent = new Intent(ThreadsActivity.this, ThreadsService.class);
                intent.setAction("START_THREAD");

                startService(intent);
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ThreadsActivity", "Stop Button Clicked");

                Intent intent = new Intent(ThreadsActivity.this, ThreadsService.class);
                intent.setAction("STOP_THREAD");

                startService(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter("SANDBOX_ACTION");

        LocalBroadcastManager.getInstance(this).registerReceiver(mBcReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBcReceiver);
    }

}
