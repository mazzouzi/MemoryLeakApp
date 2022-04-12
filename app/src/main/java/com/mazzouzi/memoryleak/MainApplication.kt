package com.mazzouzi.memoryleak

import android.app.Application
import leakcanary.LeakCanary

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Force LeakCanary to dump the heap immediately once it detects a memory leak (default = 5)
        LeakCanary.config = LeakCanary.config.copy(retainedVisibleThreshold = 1)
    }
}