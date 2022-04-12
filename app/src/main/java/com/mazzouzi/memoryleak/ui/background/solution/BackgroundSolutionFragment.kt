package com.mazzouzi.memoryleak.ui.background.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentBackgroundSolutionBinding
import com.mazzouzi.memoryleak.ui.background.BackgroundLeakInterface

class BackgroundSolutionFragment : Fragment(), BackgroundLeakInterface {

    private var _binding: FragmentBackgroundSolutionBinding? = null
    private val binding get() = _binding!!
    private val presenter = BackgroundSolutionPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackgroundSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.reference_from_background,
            getString(R.string.no_memory_leak_instructions)
        )
        presenter.doSomeIntensiveWork()
    }

    override fun onIntensiveWorkDone() {
        // update UI accordingly
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        presenter.cancelCoroutineScope("Cancelling scope from BackgroundSolutionFragment onDestroy")
        super.onDestroy()
    }

    companion object {
        val TAG: String = BackgroundSolutionFragment::class.java.simpleName
        fun newInstance() = BackgroundSolutionFragment()
    }
}