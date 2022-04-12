package com.mazzouzi.memoryleak.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.databinding.FragmentMainBinding
import com.mazzouzi.memoryleak.ui.androidapi.leak.AndroidApiLeakFragment
import com.mazzouzi.memoryleak.ui.androidapi.solution.AndroidApiSolutionFragment
import com.mazzouzi.memoryleak.ui.anonymous.leak.AnonymousLeakFragment
import com.mazzouzi.memoryleak.ui.anonymous.solution.AnonymousSolutionFragment
import com.mazzouzi.memoryleak.ui.background.leak.BackgroundLeakFragment
import com.mazzouzi.memoryleak.ui.background.solution.BackgroundSolutionFragment
import com.mazzouzi.memoryleak.ui.basefragment.ScreenTypeEnum
import com.mazzouzi.memoryleak.ui.basefragment.SplitViewActivity
import com.mazzouzi.memoryleak.ui.singleton.leak.SingletonLeakActivity
import com.mazzouzi.memoryleak.ui.singleton.solution.SingletonSolutionActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    val viewModel = MainViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            viewModel = this@MainFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.scenarioPlayedState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { scenario ->
                    when (scenario) {
                        ScenarioEnum.BACKGROUND_LEAK -> {
                            startFragment(
                                BackgroundLeakFragment.newInstance(),
                                BackgroundLeakFragment.TAG
                            )
                        }
                        ScenarioEnum.BACKGROUND_SOLUTION -> {
                            startFragment(
                                BackgroundSolutionFragment.newInstance(),
                                BackgroundSolutionFragment.TAG
                            )
                        }
                        ScenarioEnum.SINGLETON_LEAK -> {
                            startActivity(SingletonLeakActivity::class.java)
                        }
                        ScenarioEnum.SINGLETON_SOLUTION -> {
                            startActivity(SingletonSolutionActivity::class.java)
                        }
                        ScenarioEnum.ANONYMOUS_LEAK -> {
                            startFragment(
                                AnonymousLeakFragment.newInstance(),
                                AnonymousLeakFragment.TAG
                            )
                        }
                        ScenarioEnum.ANONYMOUS_SOLUTION -> {
                            startFragment(
                                AnonymousSolutionFragment.newInstance(),
                                AnonymousSolutionFragment.TAG
                            )
                        }
                        ScenarioEnum.ANDROID_API_LEAK -> {
                            startFragment(
                                AndroidApiLeakFragment.newInstance(),
                                AndroidApiLeakFragment.TAG
                            )
                        }
                        ScenarioEnum.ANDROID_API_SOLUTION -> {
                            startFragment(
                                AndroidApiSolutionFragment.newInstance(),
                                AndroidApiSolutionFragment.TAG
                            )
                        }
                        ScenarioEnum.BASE_FRAGMENT_LEAK -> {
                            startActivity(
                                SplitViewActivity::class.java,
                                Pair(SplitViewActivity.SCREEN_KEY, ScreenTypeEnum.LEAK),
                            )
                        }
                        ScenarioEnum.BASE_FRAGMENT_SOLUTION -> {
                            startActivity(
                                SplitViewActivity::class.java,
                                Pair(SplitViewActivity.SCREEN_KEY, ScreenTypeEnum.SOLUTION),
                            )
                        }
                    }
                }
        }
    }

    private fun <T> startActivity(
        classToLoad: Class<T>,
        extras: Pair<String, ScreenTypeEnum>? = null
    ) {
        val intent = Intent(context, classToLoad)
        extras?.let {
            intent.putExtra(extras.first, extras.second)
        }
        context?.startActivity(intent)
    }

    private fun startFragment(fragment: Fragment, tag: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.addToBackStack(tag)
            ?.commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}