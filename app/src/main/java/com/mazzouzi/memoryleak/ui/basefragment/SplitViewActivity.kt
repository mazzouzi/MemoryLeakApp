package com.mazzouzi.memoryleak.ui.basefragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazzouzi.memoryleak.R
import com.mazzouzi.memoryleak.ui.basefragment.leak.RoleManagementLeakFragment
import com.mazzouzi.memoryleak.ui.basefragment.solution.RoleManagementSolutionFragment

class SplitViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split_view)

        val value: ScreenTypeEnum = intent.getSerializableExtra(SCREEN_KEY) as ScreenTypeEnum

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.landscape_container,
                    when (value) {
                        ScreenTypeEnum.LEAK -> RoleManagementLeakFragment.newInstance()
                        ScreenTypeEnum.SOLUTION -> RoleManagementSolutionFragment.newInstance()
                    }
                )
                .commitNow()
        }
    }

    companion object {
        const val SCREEN_KEY = "SCREEN_KEY"
    }
}