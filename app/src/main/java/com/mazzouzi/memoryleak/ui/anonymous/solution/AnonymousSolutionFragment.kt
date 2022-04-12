package com.mazzouzi.memoryleak.ui.anonymous.solution

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentAnonymousSolutionBinding

class AnonymousSolutionFragment : Fragment() {

    private var jobDone = false
    private var _binding: FragmentAnonymousSolutionBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        jobDone = true
        binding.result.text = "OK"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnonymousSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.reference_from_background,
            getString(R.string.no_memory_leak_instructions)
        )
        startRunnable()
    }

    private fun startRunnable() {
        handler.postDelayed(runnable, 120000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        /**
         * We make sure to cancel the execution of the runnable before the fragment
         * gets destroyed -> no memory leak ✅
         */
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    companion object {
        val TAG: String = AnonymousSolutionFragment::class.java.simpleName
        fun newInstance() = AnonymousSolutionFragment()
    }
}