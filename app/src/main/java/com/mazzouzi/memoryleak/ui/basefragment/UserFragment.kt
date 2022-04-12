package com.mazzouzi.memoryleak.ui.basefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    var _binding: FragmentUserBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = UserFragment::class.simpleName

        @JvmStatic
        fun newInstance() = UserFragment()
    }
}
