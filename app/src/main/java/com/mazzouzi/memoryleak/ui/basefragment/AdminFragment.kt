package com.mazzouzi.memoryleak.ui.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentAdminBinding
import com.mazzouzi.memoryleak.ui.basefragment.leak.RoleManagementLeakFragment

class AdminFragment : Fragment() {

    var _binding: FragmentAdminBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.instruction.text = when (parentFragment) {
            is RoleManagementLeakFragment -> getString(
                R.string.base_fragment_reference,
                getString(R.string.memory_leak_instructions)
            )
            else -> getString(
                R.string.base_fragment_reference,
                getString(R.string.no_memory_leak_instructions)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = AdminFragment::class.simpleName

        @JvmStatic
        fun newInstance() = AdminFragment()
    }
}
