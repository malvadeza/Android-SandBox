package io.github.malvadeza.sandbox.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.github.malvadeza.sandbox.R;

public class Handlers extends AppCompatActivity {

    MyHandlerThread mHandlerThread;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("onCreate", "Thread ID -> " + Thread.currentThread().getId());


        mHandlerThread = new MyHandlerThread("MyTestingThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()){

            // This doesn't run in the UI Thread
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.d("mHandler#handleMessage", "Received message -> " + msg.what);
                Log.d("mHandler#handleMessage", "Thread ID -> " + Thread.currentThread().getId());
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fabClickListener", "Thread ID -> " + Thread.currentThread().getId());

                mHandler.obtainMessage(20).sendToTarget();

                mHandler.post(new Runnable() {
                    // This doesn't run in the UI Thread
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(5);

                            Log.d("mHandler#post", "Slept, but now I'm awake");
                            Log.d("mHandler#post", "Thread ID -> " + Thread.currentThread().getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private static class MyHandlerThread extends HandlerThread {

        public MyHandlerThread(String name) {
            super(name);
        }
    }

}
