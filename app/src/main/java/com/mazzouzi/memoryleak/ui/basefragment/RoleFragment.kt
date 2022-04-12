package com.mazzouzi.memoryleak.ui.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mazzouzi.memoryleak.databinding.FragmentRoleBinding

class RoleFragment : Fragment() {

    var _binding: FragmentRoleBinding? = null
    val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoleBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = RoleFragment::class.simpleName

        @JvmStatic
        fun newInstance() = RoleFragment()
    }
}