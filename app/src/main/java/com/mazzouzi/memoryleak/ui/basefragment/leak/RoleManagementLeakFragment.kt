package com.mazzouzi.memoryleak.ui.basefragment.leak

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.ui.basefragment.*
import com.mazzouzi.memoryleak.ui.basefragment.solution.RoleManagementSolutionFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RoleManagementLeakFragment(
    side: Fragment,
    main: Fragment
) : BaseTwoPanelsLeakFragment(side, main) {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                sharedViewModel.userState.collect { user ->
                    when (user) {
                        UserEnum.SUPER_USER -> SuperUserFragment()
                        UserEnum.ADMIN -> AdminFragment()
                        UserEnum.USER -> UserFragment()
                        else -> null
                    }?.let {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.main_container, it)
                            .commit()
                    }
                }
            }
        }
    }

    companion object {
        val TAG = RoleManagementSolutionFragment::class.simpleName

        @JvmStatic
        fun newInstance() = RoleManagementLeakFragment(
            RoleFragment.newInstance(),
            AdminFragment.newInstance()
        )
    }
}