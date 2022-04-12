package com.mazzouzi.memoryleak.ui.background.leak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentBackgroundLeakBinding
import com.mazzouzi.memoryleak.ui.background.BackgroundLeakInterface

class BackgroundLeakFragment : Fragment(), BackgroundLeakInterface {

    private var _binding: FragmentBackgroundLeakBinding? = null
    private val binding get() = _binding!!

    private val presenter = BackgroundLeakPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBackgroundLeakBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.instruction.text = getString(
            R.string.reference_from_background,
            getString(R.string.memory_leak_instructions)
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

    companion object {
        val TAG: String = BackgroundLeakFragment::class.java.simpleName
        fun newInstance() = BackgroundLeakFragment()
    }
}