package com.mazzouzi.memoryleak.ui.singleton.leak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.ActivitySingletonLeakBinding
import kotlinx.coroutines.*

class SingletonLeakActivity: AppCompatActivity() {

    private var _binding: ActivitySingletonLeakBinding? = null
    private val binding get() = _binding!!

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Log exception error to server")
    }
    private val coroutineScope = CoroutineScope(Job() + exceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingletonLeakBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.instruction.text = getString(
            R.string.singleton_reference,
            getString(R.string.memory_leak_instructions)
        )

        binding.button.setOnClickListener {
            coroutineScope.launch(Dispatchers.IO) {
                delay(500) // simulate API call
                withContext(Dispatchers.Main) {
                    fetchLatestNews("An error occured")
                }
            }
        }
    }

    private fun fetchLatestNews(errorMessage: String) {
        /**
         * A static class outlives an Activity.
         * Passing "this" as a field to a static class -> memory leak ❌
         *
         * Even worst.. if you destroy this activity and reopen it again, next call to
         * showError function will fail as the previous instance of SingletonLeakActivity
         * already finished.
         */
        DialogUtilLeak.getInstance(this).showError("News", errorMessage)
    }

    override fun onDestroy() {
        coroutineScope.cancel("Cancelling scope from SingletonLeakActivity onDestroy()")
        _binding = null
        super.onDestroy()
    }
}