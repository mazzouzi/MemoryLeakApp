package com.mazzouzi.memoryleak.ui.singleton.solution

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.ActivitySingletonSolutionBinding
import kotlinx.coroutines.*

class SingletonSolutionActivity: AppCompatActivity() {

    private var _binding: ActivitySingletonSolutionBinding? = null
    private val binding get() = _binding!!

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Log exception error to server")
    }
    private val coroutineScope = CoroutineScope(Job() + exceptionHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySingletonSolutionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.instruction.text = getString(
            R.string.singleton_reference,
            getString(R.string.no_memory_leak_instructions)
        )

        binding.button.setOnClickListener {
            coroutineScope.launch(Dispatchers.IO) {
                delay(500) // simulate API call
                withContext(Dispatchers.Main) {
                    onError("An error occured")
                }
            }
        }
    }

    private fun onError(errorMessage: String) {
        /**
         * Here we pass a copy of the context as a parameter to every functions
         * of DialogUtilSolution singleton.
         * Once the function returns, the copy is popped off the stack
         * -> no memory leak ✅
         *
         * Be careful, this code will still leak if the user changes configuration while
         * the dialog is displayed because the dialog is not tied to the Activity's lifecycle upon
         * recreation. Please check "solutionfinal" package for complete solution.
         */
        DialogUtilSolution.showError(this, "News", errorMessage)
    }

    override fun onDestroy() {
        coroutineScope.cancel("Cancelling scope from SingletonSolutionActivity onDestroy()")
        _binding = null
        super.onDestroy()
    }
}