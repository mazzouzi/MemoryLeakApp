package com.mazzouzi.memoryleak.ui.singleton.solutionfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.ActivitySingletonSolutionBinding
import kotlinx.coroutines.*

class SingletonSolutionFinalActivity: AppCompatActivity() {

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
         */
        DialogUtilSolutionFinal.showError(this, "News", errorMessage)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        _binding = null
        /**
         * Don't forget to clear the dialog to avoid another leak upon config change.
         */
        DialogUtilSolutionFinal.clearDialog()
        super.onDestroy()
    }
}







