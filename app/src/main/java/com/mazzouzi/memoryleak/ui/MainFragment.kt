package com.mazzouzi.memoryleak.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mazzouzi.memoryleak.ui.background.solution.BackgroundSolutionFragment
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentMainBinding
import com.mazzouzi.memoryleak.ui.androidapi.leak.AndroidApiLeakFragment
import com.mazzouzi.memoryleak.ui.androidapi.solution.AndroidApiSolutionFragment
import com.mazzouzi.memoryleak.ui.anonymous.leak.AnonymousLeakFragment
import com.mazzouzi.memoryleak.ui.anonymous.solution.AnonymousSolutionFragment
import com.mazzouzi.memoryleak.ui.background.leak.BackgroundLeakFragment
import com.mazzouzi.memoryleak.ui.basefragment.ScreenTypeEnum
import com.mazzouzi.memoryleak.ui.basefragment.SplitViewActivity
import com.mazzouzi.memoryleak.ui.singleton.leak.SingletonLeakActivity
import com.mazzouzi.memoryleak.ui.singleton.solution.SingletonSolutionActivity

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * HOLDING REFERENCE FROM BACKGROUND a
         */

        binding.holdingReferenceFromBackgroundLeak.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, BackgroundLeakFragment.newInstance())
                ?.addToBackStack(BackgroundLeakFragment.TAG)
                ?.commit()
        }

        binding.holdingReferenceFromBackgroundSolution.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, BackgroundSolutionFragment.newInstance())
                ?.addToBackStack(BackgroundSolutionFragment.TAG)
                ?.commit()
        }

        /**
         * HOLDING REFERENCE FROM SINGLETON
         */

        binding.singletonReferenceLeak.setOnClickListener {
            val intent = Intent(context, SingletonLeakActivity::class.java)
            context?.startActivity(intent)
        }

        binding.singletonReferenceSolution.setOnClickListener {
            val intent = Intent(context, SingletonSolutionActivity::class.java)
            context?.startActivity(intent)
        }

        /**
         * HOLDING REFERENCE FROM ANONYMOUS CLASS
         */

        binding.anonymousReferenceLeak.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AnonymousLeakFragment.newInstance())
                ?.addToBackStack(AnonymousLeakFragment.TAG)
                ?.commit()
        }

        binding.anonymousReferenceSolution.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AnonymousSolutionFragment.newInstance())
                ?.addToBackStack(AnonymousSolutionFragment.TAG)
                ?.commit()
        }

        /**
         * HOLDING REFERENCE FROM ANDROID API
         */

        binding.androidApiReferenceLeak.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AndroidApiLeakFragment.newInstance())
                ?.addToBackStack(AndroidApiLeakFragment.TAG)
                ?.commit()
        }

        binding.androidApiReferenceSolution.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.container, AndroidApiSolutionFragment.newInstance())
                ?.addToBackStack(AndroidApiSolutionFragment.TAG)
                ?.commit()
        }

        /**
         * HOLDING REFERENCE FROM BASE FRAGMENT
         */

        binding.baseFragmentReferenceLeak.setOnClickListener {
            val intent = Intent(context, SplitViewActivity::class.java)
            intent.putExtra(SplitViewActivity.SCREEN_KEY, ScreenTypeEnum.LEAK)
            context?.startActivity(intent)
        }

        binding.baseFragmentReferenceSolution.setOnClickListener {
            val intent = Intent(context, SplitViewActivity::class.java)
            intent.putExtra(SplitViewActivity.SCREEN_KEY, ScreenTypeEnum.SOLUTION)
            context?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}