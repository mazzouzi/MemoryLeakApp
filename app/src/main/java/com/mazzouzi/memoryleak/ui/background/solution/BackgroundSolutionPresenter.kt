package com.mazzouzi.memoryleak.ui.background.solution

import android.util.Log
import com.mazzouzi.memoryleak.ui.background.BackgroundLeakInterface
import kotlinx.coroutines.*

class BackgroundSolutionPresenter(val view: BackgroundLeakInterface) {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Log exception error to server
    }
    private val coroutineScope = CoroutineScope(Job() + exceptionHandler)

    fun doSomeIntensiveWork() {
        coroutineScope.launch {
            delay(120000) // simulate intensive work
            view.onIntensiveWorkDone()
        }.invokeOnCompletion {
            Log.d("Morad", "${it?.message}")
        }
    }

    /**
     * Cancelling the coroutineScope will in turn cancel the coroutine
     * which will clear any reference to the fragment -> no memory leak ✅
     */
    fun cancelCoroutineScope(message: String) {
        coroutineScope.cancel(message)
    }
}