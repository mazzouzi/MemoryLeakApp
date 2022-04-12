package com.mazzouzi.memoryleak.ui.basefragment.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentTwoPanelsContainerBinding
import java.lang.ref.WeakReference

/**
 * We're now using WeakReference : A weak reference is a reference that isn't strong enough
 * to force an object to remain in memory. If the GC finds that an object is referenced by
 * weak references ONLY, it reclaims memory right away -> no memory leak ✅
 */
abstract class BaseTwoPanelsSolutionFragment(
    private val side: WeakReference<Fragment>,
    private val main: WeakReference<Fragment>
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
        childFragmentManager.beginTransaction().apply {
            side.get()?.let { replace(R.id.side_container, it) }
            main.get()?.let { replace(R.id.main_container, it) }
        }.commitNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}