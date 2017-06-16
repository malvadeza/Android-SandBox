package io.github.malvadeza.sandbox

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by tonho on 16/06/17.
 */
class SandBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
    }
}