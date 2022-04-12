package com.mazzouzi.memoryleak.ui.basefragment.leak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentTwoPanelsContainerBinding

abstract class BaseTwoPanelsLeakFragment(
    private val side: Fragment,
    private val main: Fragment
) : Fragment() {

    private var _binding: FragmentTwoPanelsContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwoPanelsContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * "FragmentTransaction.replace()" destroys the fragment.
         * After the user selects a new main fragment, the current one won't be
         * garbage collected because of BaseTwoPanelsLeakFragment referencing it
         * -> Memory leak ❌
         */
        childFragmentManager.beginTransaction().apply {
            replace(R.id.side_container, side)
            replace(R.id.main_container, main)
        }.commitNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}