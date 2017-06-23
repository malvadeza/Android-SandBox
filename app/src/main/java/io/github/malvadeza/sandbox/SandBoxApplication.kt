package io.github.malvadeza.sandbox

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by tonho on 16/06/17.
 */
class SandBoxApplication : Application() {

    companion object {
        lateinit var instance: SandBoxApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)
        Timber.plant(Timber.DebugTree())
    }
}