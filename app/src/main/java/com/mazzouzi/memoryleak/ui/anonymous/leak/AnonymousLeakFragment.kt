package com.mazzouzi.memoryleak.ui.anonymous.leak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentAnonymousLeakBinding

class AnonymousLeakFragment : Fragment() {

    private var jobDone = false
    private var _binding: FragmentAnonymousLeakBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnonymousLeakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.reference_from_background,
            getString(R.string.memory_leak_instructions)
        )
        startRunnable()
    }

    private fun startRunnable() {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                /**
                 * In Handler, anonymous class referencing the outer class causes memory leak ❌
                 * The fact that this runnable will run in the Main Thread does not change
                 * anything as we are now referencing the fragment from a new GC root.
                 */
                jobDone = true
                binding.result.text = "OK"
            },
            120000
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = AnonymousLeakFragment::class.java.simpleName
        fun newInstance() = AnonymousLeakFragment()
    }
}