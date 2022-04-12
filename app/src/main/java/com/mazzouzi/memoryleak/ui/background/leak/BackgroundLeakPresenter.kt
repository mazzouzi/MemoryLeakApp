package com.mazzouzi.memoryleak.ui.background.leak

import android.util.Log
import com.mazzouzi.memoryleak.ui.background.BackgroundLeakInterface
import kotlinx.coroutines.*

class BackgroundLeakPresenter(val view: BackgroundLeakInterface) {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Log exception error to server
    }
    private val coroutineScope = CoroutineScope(Job() + exceptionHandler)

    fun doSomeIntensiveWork() {
        coroutineScope.launch {
            delay(120000) // simulate intensive work

            /**
             * referencing the fragment from Dispatchers.Default
             * while coroutine never gets cancelled -> Memory leak ❌
             */
            view.onIntensiveWorkDone()
        }.invokeOnCompletion {
            Log.d("Morad", "${it?.message}")
        }
    }
}