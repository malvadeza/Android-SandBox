package io.github.malvadeza.sandbox.threads;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ThreadsService extends Service {
    private LocalBroadcastManager mBcManager;
    private MyRunnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("ThreadService", "onCreate");

        mBcManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("ThreadService", "onStartCommand");

        switch (intent.getAction()) {
            case "START_THREAD": {
                Log.d("ThreadService", "Starting Thread");

                mRunnable = new MyRunnable(this);
                new Thread(mRunnable).start();

                break;
            }
            case "STOP_THREAD": {
                Log.d("ThreadService", "Stopping everything");

                mRunnable.stopThread();

                stopSelf();

                break;
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("ThreadsService", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static class MyRunnable implements Runnable {
        private final WeakReference<ThreadsService> serviceReference;
        private boolean isRunning = true;

        public MyRunnable(ThreadsService service) {
            serviceReference = new WeakReference<>(service);
        }

        @Override
        public void run() {
            while (shouldBeRunning()) {
                try {
                    TimeUnit.SECONDS.sleep(10);

                    Log.d("MyRunnable", "Tick");

                    ThreadsService service = serviceReference.get();
                    if (service != null) {
                        Intent intent = new Intent("SANDBOX_ACTION");
                        intent.putExtra("MY_RUNNABLE_THREAD_ID", Thread.currentThread().getId());

                        service.mBcManager.sendBroadcast(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private synchronized boolean shouldBeRunning() {
            return isRunning;
        }

        public synchronized void stopThread() {
            isRunning = false;
        }
    }
}
