package com.mazzouzi.memoryleak.ui.basefragment.solution

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.ui.basefragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class RoleManagementSolutionFragment(
    side: WeakReference<Fragment>,
    main: WeakReference<Fragment>
) : BaseTwoPanelsSolutionFragment(side, main) {

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
                        UserEnum.UNDEFINED -> null
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
        fun newInstance() = RoleManagementSolutionFragment(
            WeakReference(RoleFragment.newInstance()),
            WeakReference(AdminFragment.newInstance())
        )
    }
}